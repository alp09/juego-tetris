package proyectojuego.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PantallaMenu extends Pantalla {

	private TextureAtlas	textureAtlas;
	private final Sprite	spriteFondoJuego;
	private final Vector2	posicionBotonJugar;

    private Skin skin;
    private TextButton boton;
    private Stage stage;


// CONSTRUCTOR
    public PantallaMenu() {
        super();

        textureAtlas = assetManager.get("ui/texturas.atlas", TextureAtlas.class);
        spriteFondoJuego = new Sprite(textureAtlas.findRegion("FondoJuego"));
		spriteFondoJuego.setPosition(Gdx.graphics.getWidth() * .5f - spriteFondoJuego.getWidth() * .5f, Gdx.graphics.getHeight() * .5f - spriteFondoJuego.getHeight() * .5f);

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("uiskin.json"));

		boton = new TextButton("Jugar", skin, "default");
		boton.setWidth(spriteFondoJuego.getWidth() * .4f);
		boton.setHeight(50);
		posicionBotonJugar = new Vector2(spriteFondoJuego.getX() + spriteFondoJuego.getWidth() * .5f - boton.getWidth() * .5f, spriteFondoJuego.getY() + spriteFondoJuego.getHeight() * .8f);
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
        spriteFondoJuego.draw(spriteBatch);
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
