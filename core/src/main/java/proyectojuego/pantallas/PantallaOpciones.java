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

	private final Sprite		spriteFondoJuego;			// CONTIENE EL SPRITE QUE SE MUESTRA DE FONDO

	// USADAS PARA CREAR EL MENU
	private final Skin			skin;						// CONTIENE LA SKIN USADA EN LOS BOTONES Y TEXTO
	private final Stage			stage;						// ALMACENA LA TABLA FINAL Y GESTIONA LOS INPUTS CON LOS ACTORES AÑADIDOS A ÉL
	ButtonGroup<TextButton>		listaBotonesControl;		// ALMACENA LOS BOTONES DE tablaOpciones

	// USADAS PARA LA DETECCION DE TECLAS
	private InputMultiplexer	inputMultiplexer;			// PERMITE TENER VARIOS InputProcessor - EN ESTE CASO (EN ORDEN): stage Y PantallaOpciones
	private Integer				teclaPulsada;				// ALMACENA LA ULTIMA TECLA PULSADA - ES RESETEADA A null CUANDO NO HAY NINGUN BOTON MARCADO


	// CONSTRUCTOR
	public PantallaOpciones(){
		super();

		// CARGA LA TEXTURA DE FONDO Y LA POSICIONA
		TextureAtlas textureAtlas = assetManager.get("ui/texturas.atlas", TextureAtlas.class);
		spriteFondoJuego = new Sprite(textureAtlas.findRegion("FondoJuego"));
		spriteFondoJuego.setPosition(Juego.ANCHO_JUEGO * .5f - spriteFondoJuego.getWidth() * .5f, Juego.ALTO_JUEGO * .5f - spriteFondoJuego.getHeight() * .5f);

		// CREA EL STAGE, DONDE SE COLOCARÁ LA tablaFinal
		stage = new Stage(this.fitViewport);

		// CREA LA SKIN
		skin = new Skin(Gdx.files.internal("uiskin.json"));

		// CREA UNA TABLA PARA LAS PREFERENCIAS Y CONTROLES DEL SCROLLPANE
		Table tablaOpciones = new Table();
		tablaOpciones.setBounds(spriteFondoJuego.getX(), spriteFondoJuego.getY(), spriteFondoJuego.getWidth(), spriteFondoJuego.getHeight());
		tablaOpciones.columnDefaults(0).width(spriteFondoJuego.getWidth() * .6f).align(Align.left);
		tablaOpciones.columnDefaults(1).width(spriteFondoJuego.getWidth() * .2f).align(Align.center);

		// CREA LOS WIDGET PARA LAS PREFERENCIAS
		Label		etiquetaPreferencias	= new Label("PREFERENCIAS", skin);
		CheckBox	checkBoxMusica			= new CheckBox("", skin);
		CheckBox	checkBoxEfectosSonido	= new CheckBox("", skin);

		// MODIFICA LOS WIDGET DE PREFERENCIAS - LOS CHECKBOX SE CARGAN CON EL ESTADO DE LA PREFENRECIA ACTUAL
		etiquetaPreferencias.setAlignment(Align.center);
		checkBoxMusica.setChecked(configurador.preferenciasUsuario.getBoolean(ListaPreferencias.HABILITAR_MUSICA.nombrePreferencia));
		checkBoxEfectosSonido.setChecked(configurador.preferenciasUsuario.getBoolean(ListaPreferencias.HABILITAR_EFECTOS_SONIDO.nombrePreferencia));

		// SI SE CAMBIA EL ESTADO DE LA CHECKBOX, EL VALOR GUARDADO EN EL CONFIGURADOR SE CAMBIA AL OPUESTO (true -> false y viceversa)
		checkBoxMusica.addCaptureListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				configurador.preferenciasUsuario.putBoolean(ListaPreferencias.HABILITAR_MUSICA.nombrePreferencia, !(configurador.preferenciasUsuario.getBoolean(ListaPreferencias.HABILITAR_MUSICA.nombrePreferencia)));
			}
		});
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
		Label etiquetaControles	= new Label("CONTROLES", skin);
		listaBotonesControl		= new ButtonGroup<>();

		// SE AÑADEN AL GRUPO DE BOTONES TANTOS BOTONES COMO ENTRADAS EN ListaControles, ORDENADOS EN EL ORDEN QUE ESTEN DEFINIDOS EN LA CLASE
		for (ListaControles control: ListaControles.values()) listaBotonesControl.add(new TextButton(Input.Keys.toString(configurador.controlesUsuario.getInteger(control.nombreControl)), skin));

		// ESTABLECE LAS PROPIEDADES DEL GRUPO DE BOTONES
		listaBotonesControl.setMaxCheckCount(1);
		listaBotonesControl.setMinCheckCount(0);
		listaBotonesControl.setUncheckLast(true);

		// MODIFICA LOS WIDGET DE CONTROLES
		etiquetaControles.setAlignment(Align.center);

		// AÑADE EL TITULO "Controles" ENCIMA DE LA LISTA DE CONTROLES
		tablaOpciones.row().padTop(spriteFondoJuego.getHeight() * .06f).padBottom(spriteFondoJuego.getHeight() * .04f);
		tablaOpciones.add(etiquetaControles).colspan(2).align(Align.center);

		// CREA UNA ENTRADA EN LA TABLA PARA CADA CONTROL - APROVECHANDO QUE ESTAN EN ORDEN, JUNTO CON CADA BOTON SE CREA UN LABEL CON EL TEXTO CORRESPONDIENTE
		for (int i = 0; i < ListaControles.values().length; i++) {
			tablaOpciones.row().padBottom(spriteFondoJuego.getHeight() * .02f).align(Align.center);
			tablaOpciones.add(new Label(ListaControles.values()[i].nombreControl, skin));
			tablaOpciones.add(listaBotonesControl.getButtons().get(i));
		}

		// CREA EL ScrollPane Y SE LE AÑADE LA tablaOpciones
		ScrollPane listaFinal = new ScrollPane(tablaOpciones);

		// CREA LA tablaFinal Y SE AÑADE AL stage
		Table tablaFinal = new Table();
		stage.addActor(tablaFinal);

		// POSICIONA LA tablaFinal
		tablaFinal.center().top();
		tablaFinal.setBounds(spriteFondoJuego.getX(), spriteFondoJuego.getY(), spriteFondoJuego.getWidth(), spriteFondoJuego.getHeight());

		// AÑADE EL ScrollPane A LA tablaFinal
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

		// AÑADE LOS DOS BOTONES A tablaFinal
		tablaFinal.row().padTop(spriteFondoJuego.getHeight() * .05f).width(spriteFondoJuego.getWidth() * .25f);
		tablaFinal.add(botonGuardar);
		tablaFinal.add(botonSalir);

		// INSTANCIA EL INPUTMULTIPLEXER, QUE CONTENDRÁ LOS DISTINTOS INPUTPROCESSORS
		inputMultiplexer = new InputMultiplexer(stage, this);
		Gdx.input.setInputProcessor(inputMultiplexer);

	}


	@Override
	public void show() {

	}

	@Override
	public void gestionarInput(float delta) {

		// SI NINGUN BOTON ESTA MARCADO, SE RESETEA LA VARIABLE teclaPulsada
		if (listaBotonesControl.getChecked() == null) {
			teclaPulsada = null;

		// EN CASO DE HABER UN BOTON MARCADO, NO SE CAMBIA EL CONTROL HASTA QUE PULSE UNA TECLA
		} else if (teclaPulsada != null) {
			cambiarControl(teclaPulsada);
		}

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


	// SE MODIFICO LA CLASE Pantalla PARA extends InputHandler, DE ESTA MANERA PODRÁ GESTIONAR LOS INPUTS COMO SE DEFINAN EN ELLA
	// AQUI SE MODIFICA EL METODO keyDown PARA QUE AL PULSAR UNA TECLA GUARDE EL VALOR Key.Input EN LA VARIABLE teclaPulsada
	// DESPUES DEVUELVE true PARA CONFIRMAR QUE SE GESTIONO LA ENTRADA Y ASI SE EVITE LLAMAR AL SIGUIENTE InputProcessor AÑADIDO AL InputMultiplexer
	// AUNQUE EN ESTE CASO, LA PANTALLA ESTA COMO ÚLTIMO InputProcessor
	@Override
	public boolean keyDown(int keycode) {
		teclaPulsada = keycode;
		return true;
	}

	// UNA VEZ HAYA UN BOTON MARCADO Y SE PULSE UNA TECLA, SE LLAMA A ESTE MÉTODO CON LA TECLA PULSADA
	private void cambiarControl(int teclaNueva) {

		// RECORRE LA LISTA DE CONTROLES DEL USUARIO
		for (Map.Entry<String, ?> entrada : configurador.controlesUsuario.get().entrySet()) {

			// SI SALE EL CONTROL QUE SE ESTA INTENTAND SOBRESCRIBE SU ENTRADA CON LA NUEVA TECLA Y ACTUALIZA EL TEXTO DEL BOTON
			if (entrada.getKey().equals(ListaControles.values()[listaBotonesControl.getCheckedIndex()].nombreControl)) {
				configurador.controlesUsuario.putInteger(entrada.getKey(), teclaNueva);
				listaBotonesControl.getChecked().setText(Input.Keys.toString(teclaNueva));
			}

			// SI LA TECLA ESTABA ASIGNADA A OTRA TECLA SE DEJA VACIA
			// ToDo: poruqe no se puede castear a Integer ???! -Abel 01:40
//			if ((Integer) entrada.getValue() == teclaNueva) configurador.controlesUsuario.putString(entrada.getKey(), "");
		}

		// DESMARCA EL BOTON SELECCIONADO
		listaBotonesControl.uncheckAll();
	}

}
