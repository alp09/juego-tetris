package proyectojuego.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

	private final Texture	texturaFondoJuego;
	private final Texture	texturaFondoPieza;
	private final Texture	texturaGridZonaJuego;

	private final Vector2 	posicionZonaJuego;
	private final Vector2 	posicionFondoPiezaGuardada;
	private final Vector2 	posicionFondoPrimeraPieza;
	private final Vector2 	posicionFondoSegundaPieza;
	private final Vector2	posicionFondoTerceraPieza;
	private final Vector2	posicionPiezaAmarillaGuardada;
	private final Vector2	posicionPiezaAmarillaPrimera;
	private final Vector2	posicionPiezaAmarillaSegunda;
	private final Vector2	posicionPiezaAmarillaTercera;
	private final Vector2	posicionPiezaCianGuardada;
	private final Vector2	posicionPiezaCianPrimera;
	private final Vector2	posicionPiezaCianSegunda;
	private final Vector2	posicionPiezaCianTercera;
	private final Vector2	posicionRestoPiezasGuardada;
	private final Vector2	posicionRestoPiezasPrimera;
	private final Vector2	posicionRestoPiezasSegunda;
	private final Vector2	posicionRestoPiezasTercera;

	private final Sprite	spriteFondoJuego;
	private final Sprite	spriteFondoPiezaGuardada;
	private final Sprite	spriteFondoPrimeraPieza;
	private final Sprite	spriteFondoSegundaPieza;
	private final Sprite	spriteFondoTerceraPieza;
	private final Sprite	spriteGridZonaJuego;


// CONSTRUCTOR
	public PantallaJuego() {
		super();

		texturaFondoJuego 				= new Texture("ui\\FondoJuego.png");
		texturaGridZonaJuego 			= new Texture("ui\\Grid.png");
		texturaFondoPieza 				= new Texture("ui\\FondoPieza.png");

		posicionZonaJuego 				= new Vector2(Gdx.graphics.getWidth() * .5f - texturaFondoJuego.getWidth() * .5f, Gdx.graphics.getHeight() * .5f - texturaFondoJuego.getHeight() * .5f);
		posicionFondoPiezaGuardada		= new Vector2(posicionZonaJuego.x - 20 - texturaFondoPieza.getWidth(), posicionZonaJuego.y + texturaFondoJuego.getHeight() - texturaFondoPieza.getHeight());
		posicionFondoPrimeraPieza		= new Vector2(posicionZonaJuego.x + texturaFondoJuego.getWidth() + 20, posicionZonaJuego.y + texturaFondoJuego.getHeight() - texturaFondoPieza.getHeight());
		posicionFondoSegundaPieza		= new Vector2(posicionZonaJuego.x + texturaFondoJuego.getWidth() + 20, posicionZonaJuego.y + texturaFondoJuego.getHeight() - texturaFondoPieza.getHeight() * 2);
		posicionFondoTerceraPieza		= new Vector2(posicionZonaJuego.x + texturaFondoJuego.getWidth() + 20, posicionZonaJuego.y + texturaFondoJuego.getHeight() - texturaFondoPieza.getHeight() * 3);
		posicionPiezaAmarillaGuardada	= new Vector2(posicionFondoPiezaGuardada.x + texturaFondoPieza.getWidth() * .5f - piezaAmarilla.getWidth() * .5f, posicionFondoPiezaGuardada.y + texturaFondoPieza.getHeight() * .5f - piezaAmarilla.getHeight() * .5f);
		posicionPiezaAmarillaPrimera	= new Vector2(posicionFondoPrimeraPieza.x + texturaFondoPieza.getWidth() * .5f - piezaAmarilla.getWidth() * .5f, posicionFondoPrimeraPieza.y + texturaFondoPieza.getHeight() * .5f - piezaAmarilla.getHeight() * .5f);
		posicionPiezaAmarillaSegunda	= new Vector2(posicionFondoSegundaPieza.x + texturaFondoPieza.getWidth() * .5f - piezaAmarilla.getWidth() * .5f, posicionFondoSegundaPieza.y + texturaFondoPieza.getHeight() * .5f - piezaAmarilla.getHeight() * .5f);
		posicionPiezaAmarillaTercera	= new Vector2(posicionFondoTerceraPieza.x + texturaFondoPieza.getWidth() * .5f - piezaAmarilla.getWidth() * .5f, posicionFondoTerceraPieza.y + texturaFondoPieza.getHeight() * .5f - piezaAmarilla.getHeight() * .5f);
		posicionPiezaCianGuardada		= new Vector2(posicionFondoPiezaGuardada.x + texturaFondoPieza.getWidth() * .5f - piezaCian.getWidth() * .5f, posicionFondoPiezaGuardada.y + texturaFondoPieza.getHeight() * .5f - piezaCian.getHeight() * .5f);
		posicionPiezaCianPrimera		= new Vector2(posicionFondoPrimeraPieza.x + texturaFondoPieza.getWidth() * .5f - piezaCian.getWidth() * .5f, posicionFondoPrimeraPieza.y + texturaFondoPieza.getHeight() * .5f - piezaCian.getHeight() * .5f);
		posicionPiezaCianSegunda		= new Vector2(posicionFondoSegundaPieza.x + texturaFondoPieza.getWidth() * .5f - piezaCian.getWidth() * .5f, posicionFondoSegundaPieza.y + texturaFondoPieza.getHeight() * .5f - piezaCian.getHeight() * .5f);
		posicionPiezaCianTercera		= new Vector2(posicionFondoTerceraPieza.x + texturaFondoPieza.getWidth() * .5f - piezaCian.getWidth() * .5f, posicionFondoTerceraPieza.y + texturaFondoPieza.getHeight() * .5f - piezaCian.getHeight() * .5f);
		posicionRestoPiezasGuardada		= new Vector2(posicionFondoPiezaGuardada.x + texturaFondoPieza.getWidth() * .5f - piezaAzul.getWidth() * .5f, posicionFondoPiezaGuardada.y + texturaFondoPieza.getHeight() * .5f - piezaAzul.getHeight() * .5f);
		posicionRestoPiezasPrimera		= new Vector2(posicionFondoPrimeraPieza.x + texturaFondoPieza.getWidth() * .5f - piezaAzul.getWidth() * .5f, posicionFondoPrimeraPieza.y + texturaFondoPieza.getHeight() * .5f - piezaAzul.getHeight() * .5f);
		posicionRestoPiezasSegunda		= new Vector2(posicionFondoSegundaPieza.x + texturaFondoPieza.getWidth() * .5f - piezaAzul.getWidth() * .5f, posicionFondoSegundaPieza.y + texturaFondoPieza.getHeight() * .5f - piezaAzul.getHeight() * .5f);
		posicionRestoPiezasTercera		= new Vector2(posicionFondoTerceraPieza.x + texturaFondoPieza.getWidth() * .5f - piezaAzul.getWidth() * .5f, posicionFondoTerceraPieza.y + texturaFondoPieza.getHeight() * .5f - piezaAzul.getHeight() * .5f);

		spriteFondoJuego 				= new Sprite(texturaFondoJuego);
		spriteGridZonaJuego				= new Sprite(texturaGridZonaJuego);
		spriteFondoPiezaGuardada	 	= new Sprite(texturaFondoPieza);
		spriteFondoPrimeraPieza 		= new Sprite(texturaFondoPieza);
		spriteFondoSegundaPieza 		= new Sprite(texturaFondoPieza);
		spriteFondoTerceraPieza			= new Sprite(texturaFondoPieza);

		spriteFondoJuego.setPosition(posicionZonaJuego.x, posicionZonaJuego.y);
		spriteGridZonaJuego.setPosition(posicionZonaJuego.x, posicionZonaJuego.y);
		spriteFondoPiezaGuardada.setPosition(posicionFondoPiezaGuardada.x, posicionFondoPiezaGuardada.y);
		spriteFondoPrimeraPieza.setPosition(posicionFondoPrimeraPieza.x, posicionFondoPrimeraPieza.y);
		spriteFondoSegundaPieza.setPosition(posicionFondoSegundaPieza.x, posicionFondoSegundaPieza.y);
		spriteFondoTerceraPieza.setPosition(posicionFondoTerceraPieza.x, posicionFondoTerceraPieza.y);

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

		// DIBUJA EL TABLERO DE JUEGO Y EL FONDO DEL TABLERO
		spriteFondoJuego.draw(spriteBatch);
		spriteGridZonaJuego.draw(spriteBatch);

		// DIBUJA EL FONDO DE LA PIEZA GUARDADA Y LA PIEZA EN SÍ CAMBIAR piezaPrueba POR LA PIZA QUE ESTE GUARDADA
		spriteFondoPiezaGuardada.draw(spriteBatch);
		spriteBatch.draw(piezaRoja, posicionRestoPiezasGuardada.x, posicionRestoPiezasGuardada.y);

		// DIBUJA EL FONDO DE LAS PIEZAS SIGUIENTES Y LA PIEZA EN SÍ SUSTITUIR LAS PIEZAS POR PIEZAS GENERADAS ALEATORIAMENTE
		spriteFondoPrimeraPieza.draw(spriteBatch);
		spriteFondoSegundaPieza.draw(spriteBatch);
		spriteFondoTerceraPieza.draw(spriteBatch);
		spriteBatch.draw(piezaVerde, posicionRestoPiezasPrimera.x, posicionRestoPiezasPrimera.y);
		spriteBatch.draw(piezaNaranja, posicionRestoPiezasSegunda.x, posicionRestoPiezasSegunda.y);
		spriteBatch.draw(piezaAmarilla, posicionPiezaAmarillaTercera.x, posicionPiezaAmarillaTercera.y);

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
