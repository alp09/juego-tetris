package proyectojuego.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import proyectojuego.Juego;
import proyectojuego.jugabilidad.Pieza;
import proyectojuego.jugabilidad.Tablero;


public class PantallaJuego extends Pantalla {


	/** Variables usadas en los controles del juego */
	public static final float	DELAY_ENTRE_MOVIMIENTOS = .2f;					// El valor indicado aqui será el delay inicial
	public static final float	MAXIMO_CASILLAS_POR_SEGUNDO = 1 / 20f;			// 1 / N; siendo N el numero de casillas que podrá moverse la pieza como máximo en un segundo

	public Tablero	tableroJuego;
	public int		direccionUltimoMovimientoHorizontal;							// Guarda cual fue el ultimo movimiento horizontal (Izquierda o derecha) para resetear el tiempoDesdeMovimientoHorizontal y asi aplicar el DELAY_ENTRE_MOVIMIENTOS
	public float	tiempoDesdeMovimientoHorizontal	= 0;							// Guarda el tiempo una tecla de movimiento horizontal ha estado pulsada. Su valor inicial es 0 para aplicarle el delay inicial.
	public float	tiempoDesdeMovimientoVertical	= DELAY_ENTRE_MOVIMIENTOS;		// Guarda el tiempo una tecla de movimiento vertical ha estado pulsada. Su valor incial es DELAY_ENTRE_MOVIMIENTOS para saltarse el delay inicial en los movimientos verticales


	/** Variables usadas en las actualizaciones del juego */
	public static final float	DELAY_MAXIMO_BAJADA = .2f;
	public static final float	DELAY_ENTRE_BAJADA	= 1;

	public static float			multiplicadorVelocidad	= 1;
	public static float			tiempoDesdeUltimaBajada = 0;


	/** Variables usadas en la UI del juego */
	public static final int 	ESCALA_PIEZA_UI = 96;

	private TextureAtlas		textureAtlas;
	private final Sprite		spriteFondoJuego;
	private final Sprite		spriteFondoPiezaGuardada;
	private final Sprite		spriteFondoPrimeraPieza;
	private final Sprite		spriteFondoSegundaPieza;
	private final Sprite		spriteFondoTerceraPieza;
	private final Sprite		spriteGridZonaJuego;

	private Pieza				piezaJugable;
	private Pieza				piezaGuardada;
	private Pieza 				primeraPieza;
	private Pieza 				segundaPieza;
	private Pieza 				terceraPieza;

	private Vector2				posicionPiezaJugable;			// Posicion actual de la pieza
	private final Vector2		posicionInicioPiezaJugable;		// Posicion desde donde la pieza jugable aparece al comienzo
	private final Vector2		posicionPiezaGuardada;			// Posicion en la UI de la pieza guardada
	private final Vector2		posicionPrimeraPieza;			// Posicion en la UI de la primera pieza de la lista
	private final Vector2		posicionSegundaPieza;			// Posicion en la UI de la segunda pieza de la lista
	private final Vector2		posicionTerceraPieza;			// Posicion en la UI de la tercera pieza de la lista


// CONSTRUCTOR
	public PantallaJuego() {
		super();

		tableroJuego = new Tablero();
		textureAtlas = assetManager.get("ui/texturas.atlas");

		spriteFondoJuego 			= new Sprite(textureAtlas.findRegion("FondoJuego"));
		spriteGridZonaJuego			= new Sprite(textureAtlas.findRegion("Grid"));
		spriteFondoPiezaGuardada	= new Sprite(textureAtlas.findRegion("FondoPieza"));
		spriteFondoPrimeraPieza 	= new Sprite(textureAtlas.findRegion("FondoPieza"));
		spriteFondoSegundaPieza 	= new Sprite(textureAtlas.findRegion("FondoPieza"));
		spriteFondoTerceraPieza		= new Sprite(textureAtlas.findRegion("FondoPieza"));

		spriteFondoJuego.setPosition(Juego.ANCHO_JUEGO * .5f - spriteFondoJuego.getWidth() * .5f, Juego.ALTO_JUEGO * .5f - spriteFondoJuego.getHeight() * .5f);
		spriteGridZonaJuego.setPosition(spriteFondoJuego.getX(), spriteFondoJuego.getY());
		spriteFondoPiezaGuardada.setPosition(spriteFondoJuego.getX() - 20 - spriteFondoPiezaGuardada.getWidth(), spriteFondoJuego.getY() + spriteFondoJuego.getHeight() - spriteFondoPiezaGuardada.getHeight());
		spriteFondoPrimeraPieza.setPosition(spriteFondoJuego.getX() + spriteFondoJuego.getWidth() + 20, spriteFondoJuego.getY() + spriteFondoJuego.getHeight() - spriteFondoPrimeraPieza.getHeight());
		spriteFondoSegundaPieza.setPosition(spriteFondoJuego.getX() + spriteFondoJuego.getWidth() + 20, spriteFondoJuego.getY() + spriteFondoJuego.getHeight() - spriteFondoSegundaPieza.getHeight() * 2);
		spriteFondoTerceraPieza.setPosition(spriteFondoJuego.getX() + spriteFondoJuego.getWidth() + 20, spriteFondoJuego.getY() + spriteFondoJuego.getHeight() - spriteFondoTerceraPieza.getHeight() * 3);

		piezaJugable				= new Pieza();
		piezaGuardada				= null;
		primeraPieza 				= new Pieza();
		segundaPieza 				= new Pieza();
		terceraPieza 				= new Pieza();

		posicionInicioPiezaJugable	= new Vector2(Tablero.ANCHO_TABLERO * .5f, Tablero.ALTO_TABLERO); // Centro del tablero, arriba del tablero
		posicionPiezaJugable		= new Vector2(posicionInicioPiezaJugable.x, posicionInicioPiezaJugable.y); // Coloca la primera pieza en la posicion de inicio
		posicionPiezaGuardada		= new Vector2(spriteFondoPiezaGuardada.getX() + spriteFondoPiezaGuardada.getWidth() * .5f - ESCALA_PIEZA_UI * .5f, spriteFondoPiezaGuardada.getY() + spriteFondoPiezaGuardada.getWidth() * .5f - ESCALA_PIEZA_UI * .5f);
		posicionPrimeraPieza		= new Vector2(spriteFondoPrimeraPieza.getX() + spriteFondoPrimeraPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f, spriteFondoPrimeraPieza.getY() + spriteFondoPrimeraPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f);
		posicionSegundaPieza		= new Vector2(spriteFondoSegundaPieza.getX() + spriteFondoSegundaPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f, spriteFondoSegundaPieza.getY() + spriteFondoSegundaPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f);
		posicionTerceraPieza		= new Vector2(spriteFondoTerceraPieza.getX() + spriteFondoTerceraPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f, spriteFondoTerceraPieza.getY() + spriteFondoTerceraPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f);

	}


// METODOS
	@Override
	public void show() {

	}

	@Override
	public void gestionarInput(float delta) {

		// GUARDA LA PIEZA JUGABLE O LA CAMBIA POR LA PIEZA GUARDADA
		if (Gdx.input.isKeyJustPressed(Input.Keys.W))
		{
			Pieza piezaAuxiliar;

			if (piezaGuardada == null) {
				this.piezaGuardada	= this.piezaJugable;
				jugarSiguientePieza();
			} else {
				piezaAuxiliar		= this.piezaGuardada;
				this.piezaGuardada	= this.piezaJugable;
				this.piezaJugable	= piezaAuxiliar;
			}
		}

		// MUEVE LA PIEZA A LA IZQUIERDA O DERECHA (MUTUAMENTE EXCLUSIVO)
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
		{
			int movimientoHorizontal		= 0;
			boolean puedeMoverseHorizontal	= false;

			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				movimientoHorizontal	= -1;
				puedeMoverseHorizontal	= tableroJuego.puedeMoverIzquierda(posicionPiezaJugable, piezaJugable);
			} else {
				movimientoHorizontal	= 1;
				puedeMoverseHorizontal	= tableroJuego.puedeMoverDerecha(posicionPiezaJugable, piezaJugable);
			}

			if (puedeMoverseHorizontal) {
				if (direccionUltimoMovimientoHorizontal != movimientoHorizontal) {
					direccionUltimoMovimientoHorizontal = movimientoHorizontal;
					tiempoDesdeMovimientoHorizontal = 0;
				}

				if (tiempoDesdeMovimientoHorizontal == 0) {
					posicionPiezaJugable.add(movimientoHorizontal, 0);
				} else if (tiempoDesdeMovimientoHorizontal >= DELAY_ENTRE_MOVIMIENTOS) {
					posicionPiezaJugable.add(movimientoHorizontal, 0);
					tiempoDesdeMovimientoHorizontal -= MAXIMO_CASILLAS_POR_SEGUNDO;
				}
				tiempoDesdeMovimientoHorizontal += delta;
			}

		} else {
			tiempoDesdeMovimientoHorizontal = 0;
		}

		// MUEVE LA PIEZA HACIA ABAJO
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && tableroJuego.puedeBajar(posicionPiezaJugable, piezaJugable))
		{

			if (tiempoDesdeMovimientoVertical >= DELAY_ENTRE_MOVIMIENTOS) {
				posicionPiezaJugable.add(0, -1);
				tiempoDesdeMovimientoVertical -= MAXIMO_CASILLAS_POR_SEGUNDO;
			}
			tiempoDesdeMovimientoVertical += delta;

		} else {
			tiempoDesdeMovimientoVertical = DELAY_ENTRE_MOVIMIENTOS;
		}

		// ENCAJA LA PIEZA JUSTO DEBAJO INSTANTÁNEAMENTE
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			// ToDo: se coloca abajo del todo
		}

		// ROTA LA PIEZA EN EL SENTIDO DE LAS AGUJAS DEL REJOJ
		if (Gdx.input.isKeyJustPressed(Input.Keys.E) && tableroJuego.puedeRotarSentidoReloj(posicionPiezaJugable, piezaJugable))
		{
			piezaJugable.rotarSentidoReloj();
		}

		// ROTA LA PIEZA EN EL SENTIDO OPUESTO A LAS AGUJAS DEL RELOJ
		if (Gdx.input.isKeyJustPressed(Input.Keys.Q) && tableroJuego.puedeRotarSentidoContrarioReloj(posicionPiezaJugable, piezaJugable))
		{
			piezaJugable.rotarSentidoContraReloj();
		}

		// Para hacer test - quitar despues
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			jugarSiguientePieza();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			posicionPiezaJugable.x = posicionInicioPiezaJugable.x;
			posicionPiezaJugable.y = posicionInicioPiezaJugable.y;
		}

	}

	@Override
	public void recalcularPantalla(float delta) {
		// ToDo: calcular el movimento de las piezas, actualizar puntuación, etc
//		if (tiempoDesdeUltimaBajada > Math.max(DELAY_ENTRE_BAJADA * multiplicadorVelocidad, DELAY_MAXIMO_BAJADA)) {
//			posicionPiezaJugable.add(0, -1);
//			tiempoDesdeUltimaBajada = 0;
//		} else {
//			tiempoDesdeUltimaBajada += delta;
//		}

	}

	@Override
	public void dibujarPantalla(float delta) {
		spriteBatch.begin();

		// DIBUJA EL TABLERO DE JUEGO Y EL FONDO DEL TABLERO
		spriteFondoJuego.draw(spriteBatch);
		spriteGridZonaJuego.draw(spriteBatch);

		// DIBUJA EL FONDO DE LA PIEZA GUARDADA Y LA PIEZA EN SÍ CAMBIAR piezaPrueba POR LA PIEZA QUE ESTE GUARDADA
		spriteFondoPiezaGuardada.draw(spriteBatch);
		if (piezaGuardada != null) spriteBatch.draw(piezaGuardada.spritePieza, posicionPiezaGuardada.x, posicionPiezaGuardada.y, ESCALA_PIEZA_UI, ESCALA_PIEZA_UI);

		// DIBUJA EL FONDO DE LAS PIEZAS SIGUIENTES Y LA PIEZA EN SÍ SUSTITUIR LAS PIEZAS POR PIEZAS GENERADAS ALEATORIAMENTE
		spriteFondoPrimeraPieza.draw(spriteBatch);
		spriteFondoSegundaPieza.draw(spriteBatch);
		spriteFondoTerceraPieza.draw(spriteBatch);
		spriteBatch.draw(primeraPieza.spritePieza, posicionPrimeraPieza.x, posicionPrimeraPieza.y, ESCALA_PIEZA_UI, ESCALA_PIEZA_UI);
		spriteBatch.draw(segundaPieza.spritePieza, posicionSegundaPieza.x, posicionSegundaPieza.y, ESCALA_PIEZA_UI, ESCALA_PIEZA_UI);
		spriteBatch.draw(terceraPieza.spritePieza, posicionTerceraPieza.x, posicionTerceraPieza.y, ESCALA_PIEZA_UI, ESCALA_PIEZA_UI);

		// DIBUJA LA PIEZA JUGABLE
		for (Vector2 bloquePieza: piezaJugable.getFormaPieza()) {
			spriteBatch.draw(piezaJugable.spriteBloquePieza, spriteFondoJuego.getX() + (posicionPiezaJugable.x + bloquePieza.x) * 32, spriteFondoJuego.getY() + (posicionPiezaJugable.y + bloquePieza.y) * 32);
		}

		// ToDo: Dibujar laS piezas que estaban en el tablero



		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		fitViewport.update(width, height);
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

	private void jugarSiguientePieza() {
		this.piezaJugable = this.primeraPieza;
		this.primeraPieza = this.segundaPieza;
		this.segundaPieza = this.terceraPieza;
		this.terceraPieza = new Pieza();
	}

}
