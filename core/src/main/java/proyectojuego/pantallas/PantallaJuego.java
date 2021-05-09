package proyectojuego.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import proyectojuego.Juego;

import java.sql.Statement;

public class PantallaJuego extends Pantalla {

	private Stage stage;

	Texture fondoJuego			= new Texture("ui\\fondoJuego.png");
	Texture gridZonaJuego		= new Texture("ui\\grid.png");
	Vector2 posicionZonaJuego	= new Vector2(Gdx.graphics.getWidth() / 2 - fondoJuego.getWidth() / 2, Gdx.graphics.getHeight() / 2 - fondoJuego.getHeight() / 2);

	public PantallaJuego() {
		super();
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

		spriteBatch.draw(fondoJuego, posicionZonaJuego.x, posicionZonaJuego.y);
		spriteBatch.draw(gridZonaJuego, posicionZonaJuego.x, posicionZonaJuego.y);

		spriteBatch.end();
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
		stage.dispose();
	}
}
