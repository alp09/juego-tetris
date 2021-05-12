package proyectojuego;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;
import proyectojuego.pantallas.Pantalla;
import proyectojuego.pantallas.PantallaJuego;
import proyectojuego.pantallas.PantallaMenu;
import proyectojuego.pantallas.PantallaSplash;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Juego extends Game {

	public static final int 	ANCHO_JUEGO		= 750;
	public static final int 	ALTO_JUEGO		= 900;
	public static final String 	NOMBRE_JUEGO	= "Prueba";

	private OrthographicCamera	orthographicCamera;
	private FitViewport 		fitViewport;
	private AssetManager		assetManager;
	private SpriteBatch 		spriteBatch;
	private TextureAtlas		textureAtlas;


// CONSTRUCTOR
	public Juego() {

	}


// GETTERS
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
		orthographicCamera	= new OrthographicCamera();
		fitViewport 		= new FitViewport(ANCHO_JUEGO, ALTO_JUEGO, orthographicCamera);
		assetManager		= new AssetManager();
		spriteBatch 		= new SpriteBatch();

		setScreen(new PantallaSplash());
		//setScreen(new PantallaMenu());
		//setScreen(new PantallaJuego());
	}

	public void cambiarPantalla(Pantalla pantallaAntigua, Pantalla pantallaNueva) {
		pantallaAntigua.dispose();
		setScreen(pantallaNueva);
	}

	@Override
	public void resize(int width, int height) {
		this.fitViewport.update(width, height, true);
	}
}