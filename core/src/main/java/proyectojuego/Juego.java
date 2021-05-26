package proyectojuego;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import proyectojuego.pantallas.PantallaSplash;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Juego extends Game {

	public static final int 	ANCHO_JUEGO		= 750;
	public static final int 	ALTO_JUEGO		= 900;
	public static final String 	NOMBRE_JUEGO	= "Tetris";

	private EstadoAplicacion	estadoAplicacion;
	private OrthographicCamera	orthographicCamera;
	private FitViewport 		fitViewport;
	private AssetManager		assetManager;
	private SpriteBatch 		spriteBatch;


	// CONSTRUCTOR
	public Juego() {

	}


	// GETTERS
	public EstadoAplicacion getEstadoAplicacion() {
		return estadoAplicacion;
	}
	public FitViewport getFitViewport() {
		return fitViewport;
	}
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	public AssetManager getAssetManager() {
		return assetManager;
	}


	// METODOS
	@Override
	public void create() {
		estadoAplicacion	= EstadoAplicacion.EJECUTANDO;
		orthographicCamera	= new OrthographicCamera();
		fitViewport		 	= new FitViewport(ANCHO_JUEGO, ALTO_JUEGO, orthographicCamera);
		assetManager		= new AssetManager();
		spriteBatch 		= new SpriteBatch();

		setScreen(new PantallaSplash());
	}

	@Override
	public void resize(int width, int height) {
		this.fitViewport.update(width, height, true);
	}

	@Override
	public void dispose() {
		super.dispose();
		spriteBatch.dispose();
		assetManager.dispose();
	}
}