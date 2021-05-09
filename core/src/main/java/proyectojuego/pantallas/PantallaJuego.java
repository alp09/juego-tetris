package proyectojuego.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;


public class PantallaJuego extends Pantalla {

	/*
		ESTAS VARIABLES ESTAN PARA HACER PRUEBAS PERO MAS TARDE SE CARGARAN DINAMICAMENTE
		EN VARIABLES COMO: piezaJugable, piezaGuardada, primeraPieza, segundaPieza y terceraPieza
	 */
	private Texture piezaRoja = new Texture("piezas\\PiezaRojaRecortada.png");
	private Texture piezaNaranja = new Texture("piezas\\PiezaNaranjaRecortada.png");
	private Texture piezaAmarilla = new Texture("piezas\\PiezaAmarillaRecortada.png");
	private Texture piezaAzul = new Texture("piezas\\PiezaAzulRecortada.png");
	private Texture piezaCian = new Texture("piezas\\PiezaCianRecortada.png");
	private Texture piezaMorada = new Texture("piezas\\PiezaMoradaRecortada.png");
	private Texture piezaVerde = new Texture("piezas\\PiezaVerdeRecortada.png");

	private Texture fondoJuego		= new Texture("ui\\FondoJuego.png");
	private Texture gridZonaJuego	= new Texture("ui\\Grid.png");
	private Texture fondoPieza		= new Texture("ui\\FondoPieza.png");

	private final Vector2 posicionZonaJuego				= new Vector2(Gdx.graphics.getWidth() / 2 - fondoJuego.getWidth() / 2, Gdx.graphics.getHeight() / 2 - fondoJuego.getHeight() / 2);
	private final Vector2 posicionFondoPiezaGuardada	= new Vector2(posicionZonaJuego.x - fondoPieza.getWidth() - 20, posicionZonaJuego.y + fondoJuego.getHeight() - fondoPieza.getHeight());
	private final Vector2 posicionFondoPrimeraPieza		= new Vector2(posicionZonaJuego.x + fondoJuego.getWidth() + 20, posicionZonaJuego.y + fondoJuego.getHeight() - fondoPieza.getHeight());
	private final Vector2 posicionFondoSegundaPieza		= new Vector2(posicionZonaJuego.x + fondoJuego.getWidth() + 20, posicionZonaJuego.y + fondoJuego.getHeight() - fondoPieza.getHeight() * 2);
	private final Vector2 posicionFondoTereceraPieza	= new Vector2(posicionZonaJuego.x + fondoJuego.getWidth() + 20, posicionZonaJuego.y + fondoJuego.getHeight() - fondoPieza.getHeight() * 3);


// CONSTRUCTOR
	public PantallaJuego() {
		super();
	}


// METODOS
	@Override
	public void gestionarInput(float delta) {
		// ToDo: comprobar pulsaciones de teclas
	}

	@Override
	public void recalcularPantalla(float delta) {
		// ToDo: calcular el movimento de las piezas, actualizar puntuación, etc
	}

	@Override
	public void dibujarPantalla(float delta) {
		spriteBatch.begin();

		/*
		*  DIBUJA EL TABLERO DE JUEGO Y EL FONDO DEL TABLERO
		*/
		spriteBatch.draw(fondoJuego, posicionZonaJuego.x, posicionZonaJuego.y);
		spriteBatch.draw(gridZonaJuego, posicionZonaJuego.x, posicionZonaJuego.y);

		/*
		*	DIBUJA EL FONDO DE LA PIEZA GUARDADA Y LA PIEZA EN SÍ
		*	CAMBIAR piezaPrueba POR LA PIZA QUE ESTE GUARDADA
		*/
		spriteBatch.draw(fondoPieza, posicionFondoPiezaGuardada.x, posicionFondoPiezaGuardada.y);
		spriteBatch.draw(piezaRoja, posicionFondoPiezaGuardada.x + fondoPieza.getWidth() / 2 - piezaRoja.getWidth() / 2, posicionFondoPiezaGuardada.y + fondoPieza.getHeight() / 2 - piezaRoja.getHeight() / 2);

		/*
		*	DIBUJA EL FONDO DE LAS PIEZAS SIGUIENTES Y LA PIEZA EN SÍ
		*	SUSTITUIR LAS PIEZAS POR PIEZAS GENERADAS ALEATORIAMENTE
		*/
		spriteBatch.draw(fondoPieza, posicionFondoPrimeraPieza.x, posicionFondoPrimeraPieza.y);
		spriteBatch.draw(fondoPieza, posicionFondoSegundaPieza.x, posicionFondoSegundaPieza.y);
		spriteBatch.draw(fondoPieza, posicionFondoTereceraPieza.x, posicionFondoTereceraPieza.y);
		spriteBatch.draw(piezaVerde, posicionFondoPrimeraPieza.x + fondoPieza.getWidth() / 2 - piezaVerde.getWidth() / 2, posicionFondoPrimeraPieza.y + fondoPieza.getHeight() / 2 - piezaVerde.getHeight() / 2);
		spriteBatch.draw(piezaNaranja, posicionFondoSegundaPieza.x + fondoPieza.getWidth() / 2 - piezaNaranja.getWidth() / 2, posicionFondoSegundaPieza.y + fondoPieza.getHeight() / 2 - piezaNaranja.getHeight() / 2);
		spriteBatch.draw(piezaAmarilla, posicionFondoTereceraPieza.x + fondoPieza.getWidth() / 2 - piezaAmarilla.getWidth() / 2, posicionFondoTereceraPieza.y + fondoPieza.getHeight() / 2 - piezaAmarilla.getHeight() / 2);

		// ToDo: Dibujar la pieza que esta moviendo el jugador y las que estaban en el tablero

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

	}
}
