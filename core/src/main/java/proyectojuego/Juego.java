package proyectojuego;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import proyectojuego.pantallas.Pantalla;
import proyectojuego.pantallas.PantallaJuego;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Juego extends Game {

	public static final int ANCHO_JUEGO = 750;
	public static final int ALTO_JUEGO = 900;
	public static final String 	NOMBRE_JUEGO = "Prueba";

	private OrthographicCamera orthographicCamera;
	private FitViewport fitViewport;
	private SpriteBatch spriteBatch;


// CONSTRUCTOR
	public Juego() {

	}


// GETTERS
	public FitViewport getFitViewport() {
		return fitViewport;
	}
	public SpriteBatch getSpriteBatch() { return spriteBatch; }


// METODOS
	@Override
	public void create() {
		orthographicCamera = new OrthographicCamera();
		fitViewport = new FitViewport(ANCHO_JUEGO, ALTO_JUEGO, orthographicCamera);
		spriteBatch = new SpriteBatch();

		setScreen(new PantallaJuego());
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