package proyectojuego.pantallas;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import proyectojuego.Juego;
import proyectojuego.ListaControles;
import proyectojuego.ListaPreferencias;

import java.util.*;

public class PantallaOpciones extends Pantalla {

	private InputMultiplexer	inputMultiplexer;
	private final Skin			skin;
	private final Stage			stage;
	private final Sprite		spriteFondoJuego;


	// CONSTRUCTOR
	public PantallaOpciones(){
		super();

		// CARGA LA TEXTURA DE FONDO Y LA POSICIONA
		TextureAtlas textureAtlas = assetManager.get("ui/texturas.atlas", TextureAtlas.class);
		spriteFondoJuego = new Sprite(textureAtlas.findRegion("FondoJuego"));
		spriteFondoJuego.setPosition(Juego.ANCHO_JUEGO * .5f - spriteFondoJuego.getWidth() * .5f, Juego.ALTO_JUEGO * .5f - spriteFondoJuego.getHeight() * .5f);

		// CREA EL STAGE, DONDE SE COLOCARÁ LA TABLA
		stage = new Stage(this.fitViewport);
		Gdx.input.setInputProcessor(stage);

		// CREA LA SKIN
		skin = new Skin(Gdx.files.internal("uiskin.json"));

		// CREA UNA TABLA PARA LAS PREFERENCIAS Y OPCIONES DEL SCROLLPANE
		Table tablaOpciones = new Table();
		tablaOpciones.setBounds(spriteFondoJuego.getX(), spriteFondoJuego.getY(), spriteFondoJuego.getWidth(), spriteFondoJuego.getHeight());
		tablaOpciones.columnDefaults(0).width(spriteFondoJuego.getWidth() * .6f).align(Align.left);
		tablaOpciones.columnDefaults(1).width(spriteFondoJuego.getWidth() * .2f).align(Align.center);

		// CREA LOS WIDGET PARA LAS PREFERENCIAS
		Label		etiquetaPreferencias	= new Label("PREFERENCIAS", skin);
		CheckBox	checkBoxMusica			= new CheckBox("", skin);
		CheckBox	checkBoxEfectosSonido	= new CheckBox("", skin);

		// MODIFICA LOS WIDGET DE PREFERENCIAS
		etiquetaPreferencias.setAlignment(Align.center);
		checkBoxMusica.setChecked(configurador.preferenciasUsuario.getBoolean(ListaPreferencias.HABILITAR_MUSICA.nombrePreferencia));
		checkBoxMusica.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				configurador.preferenciasUsuario.putBoolean(ListaPreferencias.HABILITAR_MUSICA.nombrePreferencia, !(configurador.preferenciasUsuario.getBoolean(ListaPreferencias.HABILITAR_MUSICA.nombrePreferencia)));
			}
		});

		checkBoxEfectosSonido.setChecked(configurador.preferenciasUsuario.getBoolean(ListaPreferencias.HABILITAR_EFECTOS_SONIDO.nombrePreferencia));
		checkBoxEfectosSonido.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				configurador.preferenciasUsuario.putBoolean(ListaPreferencias.HABILITAR_EFECTOS_SONIDO.nombrePreferencia, !(configurador.preferenciasUsuario.getBoolean(ListaPreferencias.HABILITAR_EFECTOS_SONIDO.nombrePreferencia)));
			}
		});

		// AÑADE EL TITULO "Preferencias" ENCIMA DE LA LISTA DE CONTROLES
		tablaOpciones.row().padTop(spriteFondoJuego.getHeight() * .06f).padBottom(spriteFondoJuego.getHeight() * .04f);
		tablaOpciones.add(etiquetaPreferencias).colspan(2).align(Align.center);

		// CREA UNA ENTRADA EN LA TABLA PARA CADA UNA DE LAS PREFERENCIAS
		tablaOpciones.row().padBottom(spriteFondoJuego.getHeight() * .02f).align(Align.center);
		tablaOpciones.add(new Label(ListaPreferencias.HABILITAR_MUSICA.nombrePreferencia, skin));
		tablaOpciones.add(checkBoxMusica);
		tablaOpciones.row().padBottom(spriteFondoJuego.getHeight() * .02f).align(Align.center);
		tablaOpciones.add(new Label(ListaPreferencias.HABILITAR_EFECTOS_SONIDO.nombrePreferencia, skin));
		tablaOpciones.add(checkBoxEfectosSonido);

		// CREA LOS WIDGET PARA LOS CONTROLES
		Label		etiquetaControles		= new Label("CONTROLES", skin);
		TextButton	botonMoverIzquierda		= new TextButton(Input.Keys.toString(configurador.controlesUsuario.getInteger(ListaControles.MOVER_IZQUIERDA.nombreControl)), skin);
		TextButton	botonMoverDerecha		= new TextButton(Input.Keys.toString(configurador.controlesUsuario.getInteger(ListaControles.MOVER_DERECHA.nombreControl)), skin);
		TextButton	botonBajarPieza			= new TextButton(Input.Keys.toString(configurador.controlesUsuario.getInteger(ListaControles.BAJAR_PIEZA.nombreControl)), skin);
		TextButton	botonColocarPieza		= new TextButton(Input.Keys.toString(configurador.controlesUsuario.getInteger(ListaControles.COLOCAR_PIEZA.nombreControl)), skin);
		TextButton	botonRotarSentidoReloj	= new TextButton(Input.Keys.toString(configurador.controlesUsuario.getInteger(ListaControles.ROTAR_SENTIDO_RELOJ.nombreControl)), skin);
		TextButton	botonRotarContraReloj	= new TextButton(Input.Keys.toString(configurador.controlesUsuario.getInteger(ListaControles.ROTAR_SENTIDO_CONTRARIO_RELOJ.nombreControl)), skin);
		TextButton	botonGuardarPieza		= new TextButton(Input.Keys.toString(configurador.controlesUsuario.getInteger(ListaControles.GUARDAR_PIEZA.nombreControl)), skin);
		TextButton	botonPausarPartida		= new TextButton(Input.Keys.toString(configurador.controlesUsuario.getInteger(ListaControles.PAUSAR.nombreControl)), skin);
		TextButton	botonReiniciarPartida	= new TextButton(Input.Keys.toString(configurador.controlesUsuario.getInteger(ListaControles.REINICIAR.nombreControl)), skin);
		TextButton	botonControlarMusica	= new TextButton(Input.Keys.toString(configurador.controlesUsuario.getInteger(ListaControles.CONTROLAR_MUSICA.nombreControl)), skin);

		// MODIFICA LOS WIDGET DE CONTROLES
		etiquetaControles.setAlignment(Align.center);
		botonMoverIzquierda.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);

			}
		});

		// AÑADE EL TITULO "Controles" ENCIMA DE LA LISTA DE CONTROLES
		tablaOpciones.row().padTop(spriteFondoJuego.getHeight() * .06f).padBottom(spriteFondoJuego.getHeight() * .04f);
		tablaOpciones.add(etiquetaControles).colspan(2).align(Align.center);

		// CREA UNA ENTRADA EN LA TABLA PARA CADA CONTROL
		tablaOpciones.row().padBottom(spriteFondoJuego.getHeight() * .02f).align(Align.center);
		tablaOpciones.add(new Label(ListaControles.MOVER_IZQUIERDA.nombreControl, skin));
		tablaOpciones.add(botonMoverIzquierda);
		tablaOpciones.row().padBottom(spriteFondoJuego.getHeight() * .02f).align(Align.center);
		tablaOpciones.add(new Label(ListaControles.MOVER_DERECHA.nombreControl, skin));
		tablaOpciones.add(botonMoverDerecha);
		tablaOpciones.row().padBottom(spriteFondoJuego.getHeight() * .02f).align(Align.center);
		tablaOpciones.add(new Label(ListaControles.BAJAR_PIEZA.nombreControl, skin));
		tablaOpciones.add(botonBajarPieza);
		tablaOpciones.row().padBottom(spriteFondoJuego.getHeight() * .02f).align(Align.center);
		tablaOpciones.add(new Label(ListaControles.COLOCAR_PIEZA.nombreControl, skin));
		tablaOpciones.add(botonColocarPieza);
		tablaOpciones.row().padBottom(spriteFondoJuego.getHeight() * .02f).align(Align.center);
		tablaOpciones.add(new Label(ListaControles.ROTAR_SENTIDO_RELOJ.nombreControl, skin));
		tablaOpciones.add(botonRotarSentidoReloj);
		tablaOpciones.row().padBottom(spriteFondoJuego.getHeight() * .02f).align(Align.center);
		tablaOpciones.add(new Label(ListaControles.ROTAR_SENTIDO_CONTRARIO_RELOJ.nombreControl, skin));
		tablaOpciones.add(botonRotarContraReloj);
		tablaOpciones.row().padBottom(spriteFondoJuego.getHeight() * .02f).align(Align.center);
		tablaOpciones.add(new Label(ListaControles.GUARDAR_PIEZA.nombreControl, skin));
		tablaOpciones.add(botonGuardarPieza);
		tablaOpciones.row().padBottom(spriteFondoJuego.getHeight() * .02f).align(Align.center);
		tablaOpciones.add(new Label(ListaControles.PAUSAR.nombreControl, skin));
		tablaOpciones.add(botonPausarPartida);
		tablaOpciones.row().padBottom(spriteFondoJuego.getHeight() * .02f).align(Align.center);
		tablaOpciones.add(new Label(ListaControles.REINICIAR.nombreControl, skin));
		tablaOpciones.add(botonReiniciarPartida);
		tablaOpciones.row().padBottom(spriteFondoJuego.getHeight() * .02f).align(Align.center);
		tablaOpciones.add(new Label(ListaControles.CONTROLAR_MUSICA.nombreControl, skin));
		tablaOpciones.add(botonControlarMusica);

		// CREA EL SCROLLPANE Y LE AÑADE LA tablaOpciones
		ScrollPane listaFinal = new ScrollPane(tablaOpciones);

		// CREA LA TABLA FINAL Y LA AÑADE AL STAGE
		Table tablaFinal = new Table();
		stage.addActor(tablaFinal);

		// POSICIONA LA TABLA FINAL
		tablaFinal.center().top();
		tablaFinal.setBounds(spriteFondoJuego.getX(), spriteFondoJuego.getY(), spriteFondoJuego.getWidth(), spriteFondoJuego.getHeight());

		// AÑADE EL SCROLLPANE A LA TABLA
		tablaFinal.row().height(spriteFondoJuego.getHeight() * .8f);
		tablaFinal.add(listaFinal).colspan(2);

		// CREA LOS BOTONES PARA GUARDAR LOS CAMBIOS Y SALIR
		TextButton botonGuardar = new TextButton("Guardar", skin);
		botonGuardar.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				// ToDo: Guardar cambios
			}
		});

		TextButton botonSalir = new TextButton("Salir", skin);
		botonSalir.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				// ToDo: comprobar si se hicieron cambios y salir
			}
		});

		// AÑADE DOS BOTONES AL FINAL PARA GUARDAR Y SALIR
		tablaFinal.row().padTop(spriteFondoJuego.getHeight() * .05f).width(spriteFondoJuego.getWidth() * .25f);
		tablaFinal.add(botonGuardar);
		tablaFinal.add(botonSalir);

	}


	@Override
	public void show() {

	}

	@Override
	public void gestionarInput(float delta) {

	}

	@Override
	public void recalcularPantalla(float delta) {

	}

	@Override
	public void dibujarPantalla(float delta) {
		stage.act();
		spriteBatch.begin();
		spriteFondoJuego.draw(spriteBatch);
		spriteBatch.end();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		fitViewport.update(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
		this.dispose();
	}

	@Override
	public void dispose() {

	}

//	private void detectarTeclaPulsada(String controlAModificar, int teclaPulsada) {
//
//		Dialog ventanaCambioTecla = new Dialog("Cambiar tecla", skin) {
//			@Override
//			protected void result(Object object) {
//
//			}
//		};
//		ventanaCambioTecla.setPosition(Juego.ANCHO_JUEGO * .5f - ventanaCambioTecla.getWidth() * .5f, Juego.ANCHO_JUEGO * .5f - ventanaCambioTecla.getHeight() * .5f);
//		ventanaCambioTecla.text("Elige una tecla");
//		ventanaCambioTecla.show(stage);
//	}

}
