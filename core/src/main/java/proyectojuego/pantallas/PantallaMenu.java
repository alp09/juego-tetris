package proyectojuego.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import proyectojuego.Juego;

import java.io.*;
import java.util.ArrayList;

public class PantallaMenu extends Pantalla {

	private static final int 	PUNTUACIONES_MAXIMAS_MOSTRADAS = 5;
	private ArrayList<Integer>	listaMejoresPuntuaciones = new ArrayList<>();

	private TextureAtlas		textureAtlas;
	private final Sprite		spriteFondoJuego;

	private final Skin 			skin;
	private final Stage 		stage;
	private final Table			tablaMenu;
	private final TextButton	botonJugar;
	private final TextButton	botonOpciones;


// CONSTRUCTOR
    public PantallaMenu() {
        super();

        // CARGA LAS TEXTURAS Y LAS POSICIONA
        textureAtlas = assetManager.get("ui/texturas.atlas", TextureAtlas.class);
        spriteFondoJuego = new Sprite(textureAtlas.findRegion("FondoJuego"));
		spriteFondoJuego.setPosition(Juego.ANCHO_JUEGO * .5f - spriteFondoJuego.getWidth() * .5f, Juego.ALTO_JUEGO * .5f - spriteFondoJuego.getHeight() * .5f);


		// CARGA LA SKIN, QUE CONTIENE LA FUENTE DE TEXTO Y BOTONES
		skin = new Skin(Gdx.files.internal("uiskin.json"));


		// CREA EL STAGE, DONDE SE COLOCARÁ LA TABLA
		stage = new Stage(this.fitViewport);
		Gdx.input.setInputProcessor(stage);


		// CREA LA TABLA DONDE SE COLOCARÁN LOS BOTONES Y LA AÑADE AL STAGE
		tablaMenu = new Table();
		stage.addActor(tablaMenu);


		// CREA Y DEFINE LOS BOTONES
		botonJugar = new TextButton("Jugar", skin, "default");
		botonJugar.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				juego.setScreen(new PantallaJuego());
			}
		});

		botonOpciones = new TextButton("Opciones", skin, "default");
		botonOpciones.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				// ToDo: cambiar pantalla opciones
				// juego.setScreen(new PantallaOpciones());
			}
		});


		// ESTABLECE LOS LIMITES DE LA TABLA, COLOCA SUS ELEMENTOS ALINEADOS ARRIBA Y ESTABLECE EL TAMAÑO POR DEFECTO DE LAS CELDAS
		tablaMenu.center().top();
		tablaMenu.setBounds(spriteFondoJuego.getX(), spriteFondoJuego.getY(), spriteFondoJuego.getWidth(), spriteFondoJuego.getHeight());
		tablaMenu.defaults().width(spriteFondoJuego.getWidth() * .6f).height(spriteFondoJuego.getHeight() * .08f);


		// AÑADE LOS BOTONES A LA TABLA Y LES DA UNAS DIMENSIONES, DESPUES AÑADE LA TABLA AL STAGE
		tablaMenu.add(botonJugar).padTop(spriteFondoJuego.getHeight() * .1f);
		tablaMenu.row();
		tablaMenu.add(botonOpciones).padTop(spriteFondoJuego.getHeight() * .05f);


		// CARGA LAS PUNTUACIONES DEL ARCHIVO puntuaciones.bin
		try (DataInputStream lector = new DataInputStream(new FileInputStream("assets/files/puntuaciones.bin"))) {
			for (int i = 0; i < PUNTUACIONES_MAXIMAS_MOSTRADAS; i++) listaMejoresPuntuaciones.add(lector.readInt());
		} catch (EOFException e) {
			System.out.println("No hay mas puntuaciones registradas.");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("No se encontro el archivo.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error en la lectura del archivo.");
			e.printStackTrace();
		}


		// MUESTRA LAS PUNTUACIONES
		tablaMenu.row();
		tablaMenu.add(new Label("Mejores Puntuaciones", skin)).padTop(spriteFondoJuego.getHeight() * .1f).getActor().setAlignment(Align.center);

		if (listaMejoresPuntuaciones.size() == 0) {
			tablaMenu.row();
			tablaMenu.add(new Label("No se ha registrado ninguna puntuacion", skin)).getActor().setAlignment(Align.center);
		} else {
			for (Integer listaMejoresPuntuacione : listaMejoresPuntuaciones) {
				tablaMenu.defaults().height(spriteFondoJuego.getHeight() * .05f).row();
				tablaMenu.add(new Label(Integer.toString(listaMejoresPuntuacione), skin)).getActor().setAlignment(Align.right);
			}
		}

		tablaMenu.setDebug(false);
//		tablaMenu.setDebug(true);
    }


// METODOS
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
        spriteBatch.begin();
        spriteFondoJuego.draw(spriteBatch);
        spriteBatch.end();
		stage.draw();
    }

    @Override
    public void resize(int width, int height) {
		stage.getViewport().update(width, height);
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
		skin.dispose();
		stage.dispose();
    }
}
