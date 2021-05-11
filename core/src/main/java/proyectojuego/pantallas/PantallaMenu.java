package proyectojuego.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PantallaMenu extends Pantalla {

    private final Texture fondoJuego;
    private final Vector2 posicionZonaJuego;
	private final Vector2 posicionBotonJugar;

    private Skin skin;
    private TextButton boton;
    private Stage stage;


// CONSTRUCTOR
    public PantallaMenu() {
        super();

        fondoJuego = new Texture("ui\\fondoJuego.png");
        posicionZonaJuego = new Vector2(Gdx.graphics.getWidth() / 2 - fondoJuego.getWidth() / 2, Gdx.graphics.getHeight() / 2 - fondoJuego.getHeight() / 2);

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("uiskin.json"));

		boton = new TextButton("Jugar", skin, "default");
		boton.setWidth(fondoJuego.getWidth() * .4f);
		boton.setHeight(50);
		posicionBotonJugar = new Vector2(posicionZonaJuego.x + fondoJuego.getWidth() * .5f - boton.getWidth() * .5f, posicionZonaJuego.y + fondoJuego.getHeight() * .8f);
		boton.setPosition(posicionBotonJugar.x, posicionBotonJugar.y);
		boton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				juego.setScreen(new PantallaJuego());
			}
		});

		stage.addActor(boton);

    }


// METODOS
    @Override
    public void gestionarInput(float delta) {

    }

    @Override
    public void recalcularPantalla(float delta) {

    }

    @Override
    public void dibujarPantalla(float delta) {
        spriteBatch.begin();
        spriteBatch.draw(fondoJuego, posicionZonaJuego.x, posicionZonaJuego.y);
        spriteBatch.end();
		stage.draw();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
