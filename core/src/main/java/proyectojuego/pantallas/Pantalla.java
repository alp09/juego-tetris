package proyectojuego.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import proyectojuego.Juego;

public abstract class Pantalla implements Screen {

	protected Juego			juego;
	protected FitViewport	fitViewport;
	protected AssetManager	assetManager;
	protected SpriteBatch	spriteBatch;


// CONSTRUCTOR
	public Pantalla() {
		this.juego 			= (Juego) Gdx.app.getApplicationListener();
		this.fitViewport	= this.juego.getFitViewport();
		this.assetManager	= this.juego.getAssetManager();
		this.spriteBatch	= this.juego.getSpriteBatch();
	}


// METODOS
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

		gestionarInput(delta);
		recalcularPantalla(delta);
		dibujarPantalla(delta);
	}

	public abstract void gestionarInput(float delta);
	public abstract void recalcularPantalla(float delta);
	public abstract void dibujarPantalla(float delta);

}
