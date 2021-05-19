package proyectojuego.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import proyectojuego.Juego;
import proyectojuego.jugabilidad.Pieza;
import proyectojuego.jugabilidad.Tablero;



public class PantallaJuego extends Pantalla {

	/** Variables usadas en los controles del juego */
	private static final float	DELAY_ENTRE_MOVIMIENTOS		= .2f;								// El valor indicado aqui será el delay inicial
	private static final float	MAXIMO_CASILLAS_POR_SEGUNDO	= 1 / 20f;							// 1 / N; siendo N el numero de casillas que podrá moverse la pieza como máximo en un segundo

	private final Tablero		tableroJuego;
	private int					direccionUltimoMovimientoHorizontal;							// Guarda cual fue el ultimo movimiento horizontal (-1 = Izquierda o 1 = derecha) para resetear el tiempoDesdeMovimientoHorizontal y asi aplicar el DELAY_ENTRE_MOVIMIENTOS
	private float				tiempoDesdeMovimientoHorizontal	= 0;							// Guarda el tiempo una tecla de movimiento horizontal ha estado pulsada. Su valor inicial es 0 para aplicarle el delay inicial.
	private float				tiempoDesdeMovimientoVertical	= DELAY_ENTRE_MOVIMIENTOS;		// Guarda el tiempo una tecla de movimiento vertical ha estado pulsada. Su valor incial es DELAY_ENTRE_MOVIMIENTOS para saltarse el delay inicial en los movimientos verticales
	private boolean 			seIntercambioPiezaJugable		= false;						// Guarda si se guardo una pieza para asi prevenir que la que se esta jugando pueda ser guardada de nuevo

	/** Variables usadas en las actualizaciones del juego */
	private final float			DELAY_MAXIMO_BAJADA = .05f;			// Establece el tiempo mínimo entre bajada y bajada: Usado para capar la dificultad del juego
	private final float			DELAY_ENTRE_BAJADA	= 1;			// Establece el delay en el que una pieza baja automaticamente
	private final float			TIEMPO_COLOCACION	= .4f;			// Contiene el tiempo necesario que debe transcurrir para que una pieza se coloque en el tablero

	private float				multiplicadorVelocidad	= 1;		// Multiplicador del DELAY_ENTRE_BAJADA: Con el tiempo aumenta para que la velocidad a la que bajan las piezas aumente
	private float				tiempoDesdeUltimaBajada = 0;		// Guarda el tiempo desde la ultima vez que la pieza bajo
	private float				tiempoParaFijarATablero = 0;		// Guarda el tiempo que la pieza ha pasado sin poder bajar mas casillas
	private float 				tiempoNecesitadoParaColocar;		// Tiempo necesitado por el jugador para colocar la pieza

	/** Variables usadas para las puntuciones */
	private final int			PUNTOS_COLOCAR_PIEZA	= 100;		// Puntuacion base por colocar una pieza
	private final int			PUNTOS_COMPLETAR_LINEA	= 1000;		// Puntuacion base por completar una linea
	private int 				puntucionTotal;						// Almacena la puntuacion obtenida durante la partida
	private boolean				seTerminoPartida = false;			// Determina si la partida termino

	/** Variables usadas en la UI del juego */
	public	static final int	PIXELES_BLOQUE_UI		= 32;		// Tamaño en px de un bloque - Se usa establecer un espacio entre las coordenadas cuando se va a dibujar un bloque
	private final int 			ESCALA_PIEZA_UI			= 96;		// Tamaño en px de las piezas mostradas en los laterales

	public TextureAtlas			textureAtlas;						// Contiene información de donde se localizan las texturas en la imagen texturas.png
	private Skin				skin;								// Skin que contiene los estilos del texto puntuacionTotal
	private Table				tablaIndicadorPuntuacion;			// Contenedor que encapsula el Label indicadorPuntuacionTotal
	private Label				textoPuntuacionTotal;				// Label que contiene la palabra puntuacion
	private Label				indicadorPuntuacionTotal;			// Label que contiene la variable puntuacionTotal
	private final Sprite		spriteFondoJuego;					// Sprite para el fondo del tablero
	private final Sprite		spriteFondoPiezaGuardada;			// Sprite que se coloca de fondo para la pieza guardada
	private final Sprite		spriteFondoPrimeraPieza;			// Sprite que se coloca de fondo para la primera pieza en la lista de siguientes
	private final Sprite		spriteFondoSegundaPieza;			// Sprite que se coloca de fondo para la segunda pieza en la lista de siguientes
	private final Sprite		spriteFondoTerceraPieza;			// Sprite que se coloca de fondo para la tercera pieza en la lista de siguientes
	private final Sprite		spriteGridZonaJuego;				// Contiene la malla que se coloca sobre el spriteFondoJuego

	private Pieza				piezaJugable;						// Guarda la pieza que se está jugando
	private Pieza				piezaGuardada;						// Guarda la pieza que el jugador guardó
	private Pieza 				primeraPieza;						// Guarda la primera pieza en la lista de siguientes
	private Pieza 				segundaPieza;						// Guarda la segunda pieza en la lista de siguientes
	private Pieza 				terceraPieza;						// Guarda la tercera pieza en la lista de siguientes

	private Vector2				posicionPiezaJugable;				// Posicion actual de la pieza
	private final Vector2		posicionInicioPiezaJugable;			// Posicion desde donde la pieza jugable aparece al comienzo
	private final Vector2		posicionPiezaGuardada;				// Posicion en la UI de la pieza guardada
	private final Vector2		posicionPrimeraPieza;				// Posicion en la UI de la primera pieza de la lista
	private final Vector2		posicionSegundaPieza;				// Posicion en la UI de la segunda pieza de la lista
	private final Vector2		posicionTerceraPieza;				// Posicion en la UI de la tercera pieza de la lista

	private Music musica;
	private Sound sonidoPieza;
	private Sound sonidoGameOver;
	private boolean musicaEncendida;


// CONSTRUCTOR
	public PantallaJuego() {
		super();

		//CARGAMOS LA MUSICA Y LOS SONIDOS Y LOS INICIAMOS
		musica = Gdx.audio.newMusic(Gdx.files.internal("musicaJuego.ogg"));
		sonidoPieza = Gdx.audio.newSound(Gdx.files.internal("sonidoPieza.ogg"));
		sonidoPieza.setVolume(0,1);
		sonidoGameOver = Gdx.audio.newSound(Gdx.files.internal("GameOver.ogg"));
		// CARGA LAS TEXUTRAS DEL ASSETMANAGER
		textureAtlas = assetManager.get("ui/texturas.atlas");

		// CREA UNA INSTANCIA DEL TABLERO DE JUEGO
		tableroJuego = Tablero.getInstance();

		// CREA LA TABLA Y LOS LABEL QUE CONTENDRÁN LA PUNTUACION, DESPUES ALINEA LA TABLA ABAJO A LA DERECHA
		skin							= new Skin(Gdx.files.internal("uiskin.json"));
		tablaIndicadorPuntuacion		= new Table();
		textoPuntuacionTotal			= new Label("Puntuacion", skin);
		indicadorPuntuacionTotal		= new Label(Integer.toString(puntucionTotal), skin, "default");
		tablaIndicadorPuntuacion.bottom().right();

		// AÑADE LOS LABEL A LA TABLA
		tablaIndicadorPuntuacion.defaults().align(Align.right);
		tablaIndicadorPuntuacion.add(textoPuntuacionTotal);
		tablaIndicadorPuntuacion.row();
		tablaIndicadorPuntuacion.add(indicadorPuntuacionTotal);

		// CARGA LOS SPRITES CORRESPONDIENTES DEL TEXTUREATLAS
		spriteFondoJuego 			= new Sprite(textureAtlas.findRegion("FondoJuego"));
		spriteGridZonaJuego			= new Sprite(textureAtlas.findRegion("Grid"));
		spriteFondoPiezaGuardada	= new Sprite(textureAtlas.findRegion("FondoPieza"));
		spriteFondoPrimeraPieza 	= new Sprite(textureAtlas.findRegion("FondoPieza"));
		spriteFondoSegundaPieza 	= new Sprite(textureAtlas.findRegion("FondoPieza"));
		spriteFondoTerceraPieza		= new Sprite(textureAtlas.findRegion("FondoPieza"));

		// ESTABLECE LA POSICION DE CADA SPRITE
		spriteFondoJuego.setPosition(Juego.ANCHO_JUEGO * .5f - spriteFondoJuego.getWidth() * .5f, Juego.ALTO_JUEGO * .5f - spriteFondoJuego.getHeight() * .5f);
		spriteGridZonaJuego.setPosition(spriteFondoJuego.getX(), spriteFondoJuego.getY());
		spriteFondoPiezaGuardada.setPosition(spriteFondoJuego.getX() - 20 - spriteFondoPiezaGuardada.getWidth(), spriteFondoJuego.getY() + spriteFondoJuego.getHeight() - spriteFondoPiezaGuardada.getHeight());
		spriteFondoPrimeraPieza.setPosition(spriteFondoJuego.getX() + spriteFondoJuego.getWidth() + 20, spriteFondoJuego.getY() + spriteFondoJuego.getHeight() - spriteFondoPrimeraPieza.getHeight());
		spriteFondoSegundaPieza.setPosition(spriteFondoJuego.getX() + spriteFondoJuego.getWidth() + 20, spriteFondoJuego.getY() + spriteFondoJuego.getHeight() - spriteFondoSegundaPieza.getHeight() * 2);
		spriteFondoTerceraPieza.setPosition(spriteFondoJuego.getX() + spriteFondoJuego.getWidth() + 20, spriteFondoJuego.getY() + spriteFondoJuego.getHeight() - spriteFondoTerceraPieza.getHeight() * 3);
		tablaIndicadorPuntuacion.setPosition(spriteFondoJuego.getX() - 20 - tablaIndicadorPuntuacion.getWidth(), spriteFondoJuego.getY());

		// ESTABLECE EL VALOR INICIAL DE CADA PIEZA
		piezaJugable	= new Pieza();
		piezaGuardada	= null;
		primeraPieza 	= new Pieza();
		segundaPieza 	= new Pieza();
		terceraPieza 	= new Pieza();

		// GUARDA UNAS COORDENADAS QUE SE USARÁN A MENUDO DURANTE LA PARTIDA
		posicionInicioPiezaJugable			= new Vector2(tableroJuego.ANCHO_TABLERO * .5f, tableroJuego.ALTURA_COMIENZO_PIEZA);
		posicionPiezaJugable				= new Vector2(posicionInicioPiezaJugable.x, posicionInicioPiezaJugable.y);
		posicionPiezaGuardada				= new Vector2(spriteFondoPiezaGuardada.getX() + spriteFondoPiezaGuardada.getWidth() * .5f - ESCALA_PIEZA_UI * .5f, spriteFondoPiezaGuardada.getY() + spriteFondoPiezaGuardada.getWidth() * .5f - ESCALA_PIEZA_UI * .5f);
		posicionPrimeraPieza				= new Vector2(spriteFondoPrimeraPieza.getX() + spriteFondoPrimeraPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f, spriteFondoPrimeraPieza.getY() + spriteFondoPrimeraPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f);
		posicionSegundaPieza				= new Vector2(spriteFondoSegundaPieza.getX() + spriteFondoSegundaPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f, spriteFondoSegundaPieza.getY() + spriteFondoSegundaPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f);
		posicionTerceraPieza				= new Vector2(spriteFondoTerceraPieza.getX() + spriteFondoTerceraPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f, spriteFondoTerceraPieza.getY() + spriteFondoTerceraPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f);

	}


// METODOS
	@Override
	public void show() {

	}

	@Override
	public void gestionarInput(float delta) {

		// GUARDA LA PIEZA JUGABLE O LA CAMBIA POR LA PIEZA GUARDADA
		if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {

			// EVITA QUE EL JUGADOR CAMBIE CONSTANTEMENTE DE PIEZA, RESETEANDO posicionPiezaJugable
			if (!seIntercambioPiezaJugable) {
				// SI NO HAY NINGUNA PIEZA GUARDADA, GUARDA LA PIEZA JUGABLE Y SACA LA SIGUIENTE PIEZA DE LA LISTA
				if (piezaGuardada == null) {
					piezaGuardada	= piezaJugable;
					jugarSiguientePieza();

				// EN CASO DE QUE HAYA UNA PIEZA GUARDADA, LAS INTERCAMBIA
				} else {
					Pieza piezaAuxiliar = piezaGuardada;
					piezaGuardada		= piezaJugable;
					piezaJugable		= piezaAuxiliar;
				}
				posicionPiezaJugable	= posicionInicioPiezaJugable.cpy();
			}
			// INDICA QUE SE GUARDO LA PIEZA - SE RESETEA UNA VEZ SE COLOQUE EN EL TABLERO UNA PIEZA
			seIntercambioPiezaJugable	= true;
		}

		// MUEVE LA PIEZA A LA IZQUIERDA O DERECHA (MUTUAMENTE EXCLUSIVO)
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

			// AQUI SE DISTINGUE SI SE ESTA MOVIENDO A LA IZQUIERDA O DERECHA
			int 	movimientoHorizontal;
			boolean puedeMoverseHorizontal;

			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				movimientoHorizontal	= -1;
				puedeMoverseHorizontal	= tableroJuego.puedeMoverIzquierda(posicionPiezaJugable, piezaJugable);
			} else {
				movimientoHorizontal	= 1;
				puedeMoverseHorizontal	= tableroJuego.puedeMoverDerecha(posicionPiezaJugable, piezaJugable);
			}

			// COMPRUEBA LAS COLISIONES
			if (puedeMoverseHorizontal) {
				// COMPRUEBA SI LA DIRECCION A LA QUE SE QUIERE MOVER LA PIEZA ES DISTINTA QUE LA VEZ ANTERIOR PARA RESETEAR EL tiempoDesdeMovimientoHorizontal
				if (direccionUltimoMovimientoHorizontal != movimientoHorizontal) {
					direccionUltimoMovimientoHorizontal = movimientoHorizontal;
					tiempoDesdeMovimientoHorizontal = 0;
				}
				// SE MUEVE LA PIEZA INMEDIATAMENTE SI LA TECLA SE PULSO
				if (tiempoDesdeMovimientoHorizontal == 0) {
					posicionPiezaJugable.add(movimientoHorizontal, 0);

				// SI SE MANTUVO PULSADA LA TECLA, NO VUELVE A MOVERLA HASTA PASADO UN TIEMPO
				} else if (tiempoDesdeMovimientoHorizontal >= DELAY_ENTRE_MOVIMIENTOS) {
					posicionPiezaJugable.add(movimientoHorizontal, 0);

					// SE LE RESTA AL tiempoDesdeMovimientoHorizontal UNA PEQUEÑA CANTIDAD DE TIEMPO PARA EVITAR QUE ENTRE EN EL if ANTERIOR EN CADA LLAMADA AL RENDER
					tiempoDesdeMovimientoHorizontal -= MAXIMO_CASILLAS_POR_SEGUNDO;
				}
				tiempoDesdeMovimientoHorizontal += delta;
			}
		// SI LA PIEZA NO SE ESTA MOVIENDO, SE RESETEA LA VARIABLE PARA ASI APLICAR EL DELAY INICIAL LA PROXIMA VEZ
		} else {
			tiempoDesdeMovimientoHorizontal = 0;
		}

		// MUEVE LA PIEZA HACIA ABAJO - FUNCIONA IGUAL QUE EL DE MOVIMIENTO HORIZONTAL SIN EL DELAY INICIAL
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN))	{
			if (tableroJuego.puedeBajar(posicionPiezaJugable, piezaJugable)) {
				if (tiempoDesdeMovimientoVertical >= DELAY_ENTRE_MOVIMIENTOS) {
					posicionPiezaJugable.add(0, -1);
					tiempoDesdeMovimientoVertical -= MAXIMO_CASILLAS_POR_SEGUNDO;
				}
				tiempoDesdeMovimientoVertical += delta;
			}
		} else {
			tiempoDesdeMovimientoVertical = DELAY_ENTRE_MOVIMIENTOS;
		}

		// ENCAJA LA PIEZA JUSTO DEBAJO INSTANTÁNEAMENTE
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			while (tableroJuego.puedeBajar(posicionPiezaJugable, piezaJugable)) {
				posicionPiezaJugable.y -= 1;
			}

			fijarPiezaAlTablero(TIEMPO_COLOCACION);
		}

		// ROTA LA PIEZA EN EL SENTIDO DE LAS AGUJAS DEL REJOJ
		if (Gdx.input.isKeyJustPressed(Input.Keys.E) && tableroJuego.puedeRotarSentidoReloj(posicionPiezaJugable, piezaJugable)) {
			piezaJugable.rotarSentidoReloj();
		}

		// ROTA LA PIEZA EN EL SENTIDO OPUESTO A LAS AGUJAS DEL RELOJ
		if (Gdx.input.isKeyJustPressed(Input.Keys.Q) && tableroJuego.puedeRotarSentidoContrarioReloj(posicionPiezaJugable, piezaJugable)) {
			piezaJugable.rotarSentidoContraReloj();
		}

		//
		if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
			// ToDo: pausar/reanudar el juego
		}

		if (Gdx.input.isKeyJustPressed((Input.Keys.M))){
			if(musicaEncendida){
				musica.stop();
				musicaEncendida=false;
			} else {
				musica.play();
				musicaEncendida=true;
			}

		}

	}

	@Override
	public void recalcularPantalla(float delta) {

		// BAJA LA PIEZA AUTOMÁTICAMENTE Y LA FIJA PASADO UN TIEMPO
		if (tiempoDesdeUltimaBajada > Math.max(DELAY_ENTRE_BAJADA / multiplicadorVelocidad, DELAY_MAXIMO_BAJADA)) {
			if (tableroJuego.puedeBajar(posicionPiezaJugable, piezaJugable)) {
				posicionPiezaJugable.add(0, -1);
				tiempoDesdeUltimaBajada = 0;
				tiempoParaFijarATablero = 0;
			}
		} else {
			tiempoDesdeUltimaBajada += delta;
		}

		// MANTIENE ACTUALIZADO EL TIEMPO QUE LLEVA LA PIEZA JUGABLE EN JUEGO
		tiempoNecesitadoParaColocar += delta;

		// SI LA PIEZA NO PUEDE BAJAR, COMIENZO EL CONTADOR PARA FIJARSE AL TABLERO
		if (!tableroJuego.puedeBajar(posicionPiezaJugable, piezaJugable)) fijarPiezaAlTablero(delta);

		// ACTUALIZA EL LABEL CON LA PUNTUACION ACTUAL
		indicadorPuntuacionTotal.setText(Integer.toString(puntucionTotal));

		// CUANDO LA PARTIDA

		if (seTerminoPartida){
			sonidoGameOver.play();
			juego.setScreen(new PantallaMenu());
		}

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

		// DIBUJA EL TABLERO
		tableroJuego.dibujarContenidoTablero(spriteBatch, new Vector2(spriteFondoJuego.getX(), spriteFondoJuego.getY()));

		// DIBUJA LA PIEZA JUGABLE
		for (Vector2 bloquePieza: piezaJugable.getFormaPieza()) {
			spriteBatch.draw(piezaJugable.spriteBloquePieza, spriteFondoJuego.getX() + (posicionPiezaJugable.x + bloquePieza.x) * PIXELES_BLOQUE_UI, spriteFondoJuego.getY() + (posicionPiezaJugable.y + bloquePieza.y) * PIXELES_BLOQUE_UI);
		}

		// DIBUJA EL LABEL CON LA PUNTUACION ACTUAL
		tablaIndicadorPuntuacion.draw(spriteBatch, 1);

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
		musica.dispose();
		sonidoPieza.dispose();
		sonidoGameOver.dispose();
	}

	private void fijarPiezaAlTablero(float delta) {
		tiempoParaFijarATablero += delta;

		// SI SUPERA EL TIEMPO DE COLOCACION SIN PODER BAJAR MAS SE EJECUTAN LAS SIGUIENTES ACCIONES
		if (tiempoParaFijarATablero >= TIEMPO_COLOCACION) {
			//SONIDO DE CHOQUE ENTRE PIEZAS
			sonidoPieza.play();
			// SI LA PIEZA SE COLOCA EN UNA POSICION y > 19 LA PARTIDA TERMINA
			seTerminoPartida = tableroJuego.colocarPieza(posicionPiezaJugable, piezaJugable);

			// CALCULA LA PUNTUACION OBTENIDA
			int lineasEliminadas;
			if ((lineasEliminadas = tableroJuego.eliminarFilasCompletas()) > 0) puntucionTotal += sumarPuntosCompletarLinea(lineasEliminadas);
			puntucionTotal += sumarPuntosColocarPieza(tiempoNecesitadoParaColocar);

			// ACTUALIZA EL MULTIPLICADOR DE VELOCIDAD A LA QUE LA PIEZA BAJA
			multiplicadorVelocidad 		= 1 + puntucionTotal / 25000f;

			// SACA LA SIGUIENTE PIEZA, LA POSICIONA ARRIBA DEL TABLERO Y ESTABLECE UNA SERIE DE VARIABLES A SU VALOR POR DEFECTO
			jugarSiguientePieza();
			posicionPiezaJugable		= posicionInicioPiezaJugable.cpy();
			tiempoNecesitadoParaColocar = 0;
			tiempoParaFijarATablero		= 0;
			seIntercambioPiezaJugable	= false;
		}
	}

	private int sumarPuntosColocarPieza(float tiempoDeColocacion) {
		return (int) (PUNTOS_COLOCAR_PIEZA * (1 + (1 / tiempoDeColocacion)));
	}

	private int sumarPuntosCompletarLinea(int lineasCompletadas) {
		return PUNTOS_COMPLETAR_LINEA * lineasCompletadas * lineasCompletadas;
	}

	private void jugarSiguientePieza() {
		piezaJugable = primeraPieza;
		primeraPieza = segundaPieza;
		segundaPieza = terceraPieza;
		terceraPieza = new Pieza();
	}

}
