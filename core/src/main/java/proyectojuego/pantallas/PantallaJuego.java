package proyectojuego.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import proyectojuego.EstadoAplicacion;
import proyectojuego.Juego;
import proyectojuego.ajustes.ListaControles;
import proyectojuego.ajustes.ListaPreferencias;
import proyectojuego.jugabilidad.ListaPiezas;
import proyectojuego.jugabilidad.Pieza;
import proyectojuego.jugabilidad.Tablero;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;


public final class PantallaJuego extends Pantalla {

	// Variables usadas en los controles del juego
	private static final float	DELAY_ENTRE_MOVIMIENTOS		= .2f;								// El valor indicado aqui será el delay inicial
	private static final float	MAXIMO_CASILLAS_POR_SEGUNDO	= 1 / 20f;							// 1 / N; siendo N el numero de casillas que podrá moverse la pieza como máximo en un segundo

	private Tablero				tableroJuego;
	private int					direccionUltimoMovimientoHorizontal;							// Guarda cual fue el ultimo movimiento horizontal (-1 = Izquierda o 1 = derecha) para resetear el tiempoDesdeMovimientoHorizontal y asi aplicar el DELAY_ENTRE_MOVIMIENTOS
	private float				tiempoDesdeMovimientoHorizontal	= 0;							// Guarda el tiempo una tecla de movimiento horizontal ha estado pulsada. Su valor inicial es 0 para aplicarle el delay inicial.
	private float				tiempoDesdeMovimientoVertical	= DELAY_ENTRE_MOVIMIENTOS;		// Guarda el tiempo una tecla de movimiento vertical ha estado pulsada. Su valor incial es DELAY_ENTRE_MOVIMIENTOS para saltarse el delay inicial en los movimientos verticales
	private boolean 			seIntercambioPiezaJugable		= false;						// Guarda si se guardo una pieza para asi prevenir que la que se esta jugando pueda ser guardada de nuevo

	// Variables usadas en las actualizaciones del juego
	private final float			DELAY_MAXIMO_BAJADA = .05f;			// Establece el tiempo mínimo entre bajada y bajada: Usado para capar la dificultad del juego
	private final float			DELAY_ENTRE_BAJADA	= 1;			// Establece el delay en el que una pieza baja automaticamente
	private final float			TIEMPO_COLOCACION	= .4f;			// Contiene el tiempo necesario que debe transcurrir para que una pieza se coloque en el tablero

	private float				multiplicadorVelocidad	= 1;		// Multiplicador del DELAY_ENTRE_BAJADA: Con el tiempo aumenta para que la velocidad a la que bajan las piezas aumente
	private float				tiempoDesdeUltimaBajada = 0;		// Guarda el tiempo desde la ultima vez que la pieza bajo
	private float				tiempoParaFijarATablero = 0;		// Guarda el tiempo que la pieza ha pasado sin poder bajar mas casillas
	private float 				tiempoNecesitadoParaColocar;		// Tiempo necesitado por el jugador para colocar la pieza

	// Variables usadas para las puntuciones
	private final int			PUNTOS_COLOCAR_PIEZA	= 50;		// Puntuacion base por colocar una pieza
	private final int			PUNTOS_COMPLETAR_LINEA	= 500;		// Puntuacion base por completar una linea
	private int 				puntucionTotal;						// Almacena la puntuacion obtenida durante la partida
	private boolean				seTerminoPartida = false;			// Determina si la partida termino

	// Variables usadas en la UI del juego
	public	static final int	PIXELES_BLOQUE_UI		= 32;		// Tamaño en px de un bloque - Se usa establecer un espacio entre las coordenadas cuando se va a dibujar un bloque
	private final int 			ESCALA_PIEZA_UI			= 96;		// Tamaño en px de las piezas mostradas en los laterales

	private final Skin			skin;								// Skin que contiene los estilos del texto puntuacionTotal
	private final Table			tablaIndicadorPuntuacion;			// Contenedor que encapsula el Label indicadorPuntuacionTotal
	private final Label			textoPuntuacionTotal;				// Label que contiene la palabra puntuacion
	private final Label			indicadorPuntuacionTotal;			// Label que contiene la variable puntuacionTotal
	private final Label			mensajePantallaPausa;				// Mensaje que aparece cuando el juego esta pausado
	private Stage				stage;								// Stage para el cuadro de dialogo
	private Dialog				ventanaGameOver;					// Cuadro de dialogo mostrado al finalizar la partida

	public	TextureAtlas		textureAtlas;						// Contiene información de donde se localizan las texturas en la imagen texturas.png
	private Pieza				piezaJugable;						// Guarda la pieza que se está jugando
	private Pieza				piezaFantasma;						// Guarda la pieza fantasma, que aparecerá abajo en el tablero, indicando donde caera la piezaJugable
	private Pieza				piezaGuardada;						// Guarda la pieza que el jugador guardó
	private Pieza 				primeraPieza;						// Guarda la primera pieza en la lista de siguientes
	private Pieza 				segundaPieza;						// Guarda la segunda pieza en la lista de siguientes
	private Pieza 				terceraPieza;						// Guarda la tercera pieza en la lista de siguientes

	private final Sprite		spriteFondoJuego;					// Sprite para el fondo del tablero
	private final Sprite		spriteFondoPiezaGuardada;			// Sprite que se coloca de fondo para la pieza guardada
	private final Sprite		spriteFondoPrimeraPieza;			// Sprite que se coloca de fondo para la primera pieza en la lista de siguientes
	private final Sprite		spriteFondoSegundaPieza;			// Sprite que se coloca de fondo para la segunda pieza en la lista de siguientes
	private final Sprite		spriteFondoTerceraPieza;			// Sprite que se coloca de fondo para la tercera pieza en la lista de siguientes
	private final Sprite		spriteGridZonaJuego;				// Contiene la malla que se coloca sobre el spriteFondoJuego

	private final Vector2		posicionTablero;					// Esquina inferior derecha del tablero
	private Vector2				posicionPiezaJugable;				// Posicion actual de la pieza
	private Vector2				posicionPiezaFantasma;				// Posicion actual de la pieza fantasma
	private final Vector2		posicionInicioPiezaJugable;			// Posicion desde donde la pieza jugable aparece al comienzo
	private final Vector2		posicionPiezaGuardada;				// Esquina inferior derecha en la UI de la pieza guardada
	private final Vector2		posicionPrimeraPieza;				// Esquina inferior derecha en la UI de la primera pieza de la lista
	private final Vector2		posicionSegundaPieza;				// Esquina inferior derecha en la UI de la segunda pieza de la lista
	private final Vector2		posicionTerceraPieza;				// Esquina inferior derecha en la UI de la tercera pieza de la lista

	// Variables usadas para los sonidos del juego
	private boolean 			musicaEstaHabilitada;				// Determina si la musica se reproduce
	private boolean				efectosSonidoEstanHabilitados;		// Determina si los efectos de sonido se reproducen
	private final Music			musicaPantallajuego;				// Almacena la musica de PantallaJuego
	private final Sound			sonidoPiezaColocada;				// Almacena el efecto de sonido piezaColocada
	private final Sound 		sonidoFilaCompleta;					// Almacena el efecto de sonido filaCompletada
	private final Sound			sonidoGameOver;						// Almacena el efecto de sonido GameOver


	// CONSTRUCTOR
	public PantallaJuego() {
		super();

		// CARGA LAS TEXUTRAS DEL ASSETMANAGER
		textureAtlas = assetManager.get("ui/texturas.atlas");

		// CREA UNA INSTANCIA DEL TABLERO DE JUEGO
		tableroJuego = new Tablero();

		// CREA LA TABLA Y LOS LABEL QUE CONTENDRÁN LA PUNTUACION, DESPUES ALINEA LA TABLA ABAJO A LA DERECHA
		skin						= new Skin(Gdx.files.internal("uiskin.json"));
		tablaIndicadorPuntuacion	= new Table();
		textoPuntuacionTotal		= new Label("Puntuacion", skin);
		indicadorPuntuacionTotal	= new Label(Integer.toString(puntucionTotal), skin, "default");
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
		this.generarNuevasPiezas();

		// ESTABLECE EL VALOR INICIAL DE LA PIEZA FANTASMA, DE ESTA FORMA NO DA NULL EN LA PRIMERA COMPROBACION
		piezaFantasma = piezaJugable;

		// GUARDA UNAS COORDENADAS QUE SE USARÁN A MENUDO DURANTE LA PARTIDA
		posicionTablero				= new Vector2(spriteFondoJuego.getX(), spriteFondoJuego.getY());
		posicionInicioPiezaJugable	= new Vector2(tableroJuego.ANCHO_TABLERO * .5f, tableroJuego.ALTURA_COMIENZO_PIEZA);
		posicionPiezaJugable		= new Vector2(posicionInicioPiezaJugable.x, posicionInicioPiezaJugable.y);
		posicionPiezaGuardada		= new Vector2(spriteFondoPiezaGuardada.getX() + spriteFondoPiezaGuardada.getWidth() * .5f - ESCALA_PIEZA_UI * .5f, spriteFondoPiezaGuardada.getY() + spriteFondoPiezaGuardada.getWidth() * .5f - ESCALA_PIEZA_UI * .5f);
		posicionPrimeraPieza		= new Vector2(spriteFondoPrimeraPieza.getX() + spriteFondoPrimeraPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f, spriteFondoPrimeraPieza.getY() + spriteFondoPrimeraPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f);
		posicionSegundaPieza		= new Vector2(spriteFondoSegundaPieza.getX() + spriteFondoSegundaPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f, spriteFondoSegundaPieza.getY() + spriteFondoSegundaPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f);
		posicionTerceraPieza		= new Vector2(spriteFondoTerceraPieza.getX() + spriteFondoTerceraPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f, spriteFondoTerceraPieza.getY() + spriteFondoTerceraPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f);
		posicionPiezaFantasma		= posicionPiezaJugable.cpy();

		// ESTABLECE LA POSICION INICIAL DE LA PIEZA FANTASMA
		while (tableroJuego.puedeBajar(posicionPiezaFantasma, piezaFantasma)) {
			posicionPiezaFantasma.y -= 1;
		}

		//CARGA MUSICA Y LOS SONIDOS
		musicaPantallajuego = assetManager.get("sounds/musicaJuego.ogg");
		sonidoPiezaColocada = assetManager.get("sounds/sonidoPieza.ogg");
		sonidoFilaCompleta	= assetManager.get("sounds/sonidoFilaCompleta.ogg");
		sonidoGameOver		= assetManager.get("sounds/sonidoGameOver.ogg");

		// ESTABLECE LAS PROPIEDADES DE LA MUSICA
		musicaPantallajuego.setVolume(.06f);
		musicaPantallajuego.setLooping(true);

		// EMPIEZA LA REPRODUCCION DE MUSICA SI ESTA HABILITADA
		if (musicaEstaHabilitada = configurador.preferenciasUsuario.get(ListaPreferencias.HABILITAR_MUSICA.nombrePreferencia)) {
			musicaPantallajuego.play();
		}

		// ESTABLECE SI EL USUARIO QUIERE O NO EFECTOS DE SONIDO
		efectosSonidoEstanHabilitados = configurador.preferenciasUsuario.get(ListaPreferencias.HABILITAR_EFECTOS_SONIDO.nombrePreferencia);

		// CREA EL MENSAJE DE PAUSA
		mensajePantallaPausa = new Label("Pausa", skin);
		mensajePantallaPausa.setAlignment(Align.center);
		mensajePantallaPausa.setPosition(spriteFondoJuego.getX() + spriteFondoJuego.getWidth() * .5f - mensajePantallaPausa.getWidth() * .5f, spriteFondoJuego.getY() + spriteFondoJuego.getHeight() * .5f - mensajePantallaPausa.getHeight() * .5f);

	}


	// METODOS
	@Override
	public void show() {

	}

	@Override
	public void gestionarInput(float delta) {

		// SI LA PARTIDA NO HA TERMINADO
		if (estadoAplicacion != EstadoAplicacion.GAME_OVER) {

			// LLAMA AL METODO QUE CORRESPONDE DEPENDIENDO DEL ESTADO DE LA APLICACION
			if (Gdx.input.isKeyJustPressed(configurador.controlesUsuario.get(ListaControles.PAUSAR.nombreControl))) {
				if (estadoAplicacion == EstadoAplicacion.EJECUTANDO) {
					this.pause();
				} else {
					this.resume();
				}
			}

			// RESETEA LA PARTIDA
			if (Gdx.input.isKeyJustPressed(configurador.controlesUsuario.get(ListaControles.REINICIAR.nombreControl))) {
				this.nuevaPartida();
			}

			// SI LA APLICACION ESTA PAUSADA, ESTOS CONTROLES NO RESPONDEN
			if (estadoAplicacion == EstadoAplicacion.EJECUTANDO) {

				// GUARDA LA PIEZA JUGABLE O LA CAMBIA POR LA PIEZA GUARDADA
				if (Gdx.input.isKeyJustPressed(configurador.controlesUsuario.get(ListaControles.GUARDAR_PIEZA.nombreControl))) {

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
				if (Gdx.input.isKeyPressed(configurador.controlesUsuario.get(ListaControles.MOVER_IZQUIERDA.nombreControl)) ||
					Gdx.input.isKeyPressed(configurador.controlesUsuario.get(ListaControles.MOVER_DERECHA.nombreControl))) {

					// AQUI SE DISTINGUE SI SE ESTA MOVIENDO A LA IZQUIERDA O DERECHA
					int 	movimientoHorizontal;
					boolean puedeMoverseHorizontal;

					if (Gdx.input.isKeyPressed(configurador.controlesUsuario.get(ListaControles.MOVER_IZQUIERDA.nombreControl))) {
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
				if (Gdx.input.isKeyPressed(configurador.controlesUsuario.get(ListaControles.BAJAR_PIEZA.nombreControl))) {
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
				if (Gdx.input.isKeyJustPressed(configurador.controlesUsuario.get(ListaControles.COLOCAR_PIEZA.nombreControl))) {
					while (tableroJuego.puedeBajar(posicionPiezaJugable, piezaJugable)) {
						posicionPiezaJugable.y -= 1;
					}

					fijarPiezaAlTablero(TIEMPO_COLOCACION);
				}

				// ROTA LA PIEZA EN EL SENTIDO DE LAS AGUJAS DEL REJOJ
				if (Gdx.input.isKeyJustPressed(configurador.controlesUsuario.get(ListaControles.ROTAR_SENTIDO_RELOJ.nombreControl)) && tableroJuego.puedeRotarSentidoReloj(posicionPiezaJugable, piezaJugable)) {
					piezaJugable.rotarSentidoReloj();
				}

				// ROTA LA PIEZA EN EL SENTIDO OPUESTO A LAS AGUJAS DEL RELOJ
				if (Gdx.input.isKeyJustPressed(configurador.controlesUsuario.get(ListaControles.ROTAR_SENTIDO_CONTRARIO_RELOJ.nombreControl)) && tableroJuego.puedeRotarSentidoContrarioReloj(posicionPiezaJugable, piezaJugable)) {
					piezaJugable.rotarSentidoContraReloj();
				}

				// HABILITA / DESHABILITA LA MUSICA EN MITAD DE LA PARTIDA
				if (Gdx.input.isKeyJustPressed(configurador.controlesUsuario.get(ListaControles.CONTROLAR_MUSICA.nombreControl))){
					if (musicaEstaHabilitada){
						musicaPantallajuego.pause();
						musicaEstaHabilitada = false;
						configurador.preferenciasUsuario.put(ListaPreferencias.HABILITAR_MUSICA.nombrePreferencia, false);
					} else {
						musicaPantallajuego.play();
						musicaEstaHabilitada = true;
						configurador.preferenciasUsuario.put(ListaPreferencias.HABILITAR_MUSICA.nombrePreferencia, true);
					}
					configurador.guardarCambiosPreferencias();
				}

			}
		}
	}

	@Override
	public void recalcularPantalla(float delta) {

		// SI LA APLICACION NO ESTA EN EJECUCION (ESTA PAUSADA O TERMINO) NO SE ACTUALIZA
		if (estadoAplicacion == EstadoAplicacion.EJECUTANDO) {

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

			// MANTIENE ACTUALIZADO EL TIEMPO QUE LLEVA LA PIEZA JUGABLE EN JUEGO (USADO PARA CALCULAR PUNTUACION POR COLOCAR PIEZA)
			tiempoNecesitadoParaColocar += delta;

			// SI LA PIEZA NO PUEDE BAJAR, COMIENZO EL CONTADOR PARA FIJARSE AL TABLERO
			if (!tableroJuego.puedeBajar(posicionPiezaJugable, piezaJugable)) fijarPiezaAlTablero(delta);

			// MANTIENE ACTUALIZADA LA PIEZA FANTASMA PARA QUE SEA IGUAL QUE LA PIEZA JUGABLE
			actualizaPiezaFantasma();

			// ACTUALIZA EL LABEL CON LA PUNTUACION ACTUAL
			indicadorPuntuacionTotal.setText(Integer.toString(puntucionTotal));

			// CUANDO LA PARTIDA ACABA
			if (seTerminoPartida) {

				// ESTABLECE EL estadoAplicacion a GAME_OVER
				estadoAplicacion = EstadoAplicacion.GAME_OVER;

				// REPRODUCE EL SONIDO DE GAMEOVER Y PARA LA MUSICA DEL JUEGO
				if (musicaEstaHabilitada) {
					musicaPantallajuego.stop();
					musicaEstaHabilitada = false;
				}
				if (efectosSonidoEstanHabilitados) sonidoGameOver.play();

				// GUARDA LA PUNTUACION AL ARCHIVO puntuaciones.bin
				this.guardarPuntuacion();

				// CREA EL CUADRO DE DIALOGO MOSTRADO EN EL GAMEOVER
				stage = new Stage(this.fitViewport);
				Gdx.input.setInputProcessor(stage);

				ventanaGameOver = new Dialog("GAME OVER", skin) {
					@Override
					protected void result(Object object) {
						switch (object.toString()) {
							case "0": nuevaPartida();						break;
							case "1": juego.setScreen(new PantallaMenu());	break;
						}
					}
				};
				ventanaGameOver.setPosition(Juego.ANCHO_JUEGO * .5f - ventanaGameOver.getWidth() * .5f, Juego.ALTO_JUEGO - ventanaGameOver.getHeight() * .5f);
				ventanaGameOver.text("Puntuacion obtenida: " + puntucionTotal);
				ventanaGameOver.button("VOLVER A JUGAR", 0);
				ventanaGameOver.button("SALIR", 1);
				ventanaGameOver.show(stage);

			}
		}
	}


	@Override
	public void dibujarPantalla(float delta) {
		spriteBatch.begin();

		// SI LA APLICACION ESTA PAUSADA SE DIBUJA LO SIGUIENTE
		if (estadoAplicacion == EstadoAplicacion.PAUSADO) {

			spriteFondoJuego.draw(spriteBatch);
			mensajePantallaPausa.draw(spriteBatch, 1);

		// EN CASO CONTRARIO (ESTA EN EJECUCION O SE TERMINO) SE DIBUJA ESTO OTRO
		} else {

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
			dibujarContenidoTablero();

			// DIBUJA LA PIEZA FANTASMA
			for (Vector2 bloquePieza: piezaFantasma.getFormaPieza()) {
				piezaFantasma.spriteBloquePieza.setPosition(spriteFondoJuego.getX() + (posicionPiezaFantasma.x + bloquePieza.x) * PIXELES_BLOQUE_UI, spriteFondoJuego.getY() + (posicionPiezaFantasma.y + bloquePieza.y) * PIXELES_BLOQUE_UI);
				piezaFantasma.spriteBloquePieza.draw(spriteBatch, .3f);
			}

			// DIBUJA LA PIEZA JUGABLE
			for (Vector2 bloquePieza: piezaJugable.getFormaPieza()) {
				spriteBatch.draw(piezaJugable.spriteBloquePieza, spriteFondoJuego.getX() + (posicionPiezaJugable.x + bloquePieza.x) * PIXELES_BLOQUE_UI, spriteFondoJuego.getY() + (posicionPiezaJugable.y + bloquePieza.y) * PIXELES_BLOQUE_UI);
			}

			// DIBUJA EL LABEL CON LA PUNTUACION ACTUAL
			tablaIndicadorPuntuacion.draw(spriteBatch, 1);

			// SI LA PARTIDA TERMINO, DIBUJA EL CUADRO DE DIALOGO ENCIMA DEL TABLERO
			if (estadoAplicacion == EstadoAplicacion.GAME_OVER) stage.draw();

		}

		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		fitViewport.update(width, height);
	}

	@Override
	public void pause() {
		estadoAplicacion = EstadoAplicacion.PAUSADO;
	}

	@Override
	public void resume() {
		estadoAplicacion = EstadoAplicacion.EJECUTANDO;
	}

	@Override
	public void hide() {
		this.dispose();
	}

	@Override
	public void dispose() {
		musicaPantallajuego.dispose();
	}

	private void fijarPiezaAlTablero(float delta) {
		tiempoParaFijarATablero += delta;

		// SI SUPERA EL TIEMPO DE COLOCACION SIN PODER BAJAR MAS SE EJECUTAN LAS SIGUIENTES ACCIONES
		if (tiempoParaFijarATablero >= TIEMPO_COLOCACION) {

			//SONIDO DE CHOQUE ENTRE PIEZAS
			if (efectosSonidoEstanHabilitados) sonidoPiezaColocada.play();

			// SI LA PIEZA SE COLOCA EN UNA POSICION y > 19 LA PARTIDA TERMINA
			seTerminoPartida = tableroJuego.colocarPieza(posicionPiezaJugable, piezaJugable);

			// SI LA PARTIDA NO TERMINO, HACE LAS SIGUIENTES ACCIONES:
			if (!seTerminoPartida) {
				// CALCULA LA PUNTUACION OBTENIDA
				int lineasEliminadas;
				if ((lineasEliminadas = tableroJuego.detectarFilasCompletas()) > 0) {
					puntucionTotal += sumarPuntosCompletarLinea(lineasEliminadas);
					if (efectosSonidoEstanHabilitados) sonidoFilaCompleta.play();
				}
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
	}

	private void dibujarContenidoTablero() {

		for (int i = 0; i < tableroJuego.ALTO_TABLERO; i++) {
			for (int j = 0; j < tableroJuego.ANCHO_TABLERO; j++) {
				switch (tableroJuego.contenidoTablero[j][i]) {
					case -1: continue;
					case  0: spriteBatch.draw(textureAtlas.findRegion(ListaPiezas.values()[0].spriteBloquePieza), posicionTablero.x + j * PantallaJuego.PIXELES_BLOQUE_UI, posicionTablero.y + i * PantallaJuego.PIXELES_BLOQUE_UI);	break;	// PIEZA CIAN
					case  1: spriteBatch.draw(textureAtlas.findRegion(ListaPiezas.values()[1].spriteBloquePieza), posicionTablero.x + j * PantallaJuego.PIXELES_BLOQUE_UI, posicionTablero.y + i * PantallaJuego.PIXELES_BLOQUE_UI);	break;	// PIEZA AMARILLA
					case  2: spriteBatch.draw(textureAtlas.findRegion(ListaPiezas.values()[2].spriteBloquePieza), posicionTablero.x + j * PantallaJuego.PIXELES_BLOQUE_UI, posicionTablero.y + i * PantallaJuego.PIXELES_BLOQUE_UI);	break;	// PIEZA MORADA
					case  3: spriteBatch.draw(textureAtlas.findRegion(ListaPiezas.values()[3].spriteBloquePieza), posicionTablero.x + j * PantallaJuego.PIXELES_BLOQUE_UI, posicionTablero.y + i * PantallaJuego.PIXELES_BLOQUE_UI);	break;	// PIEZA NARANJA
					case  4: spriteBatch.draw(textureAtlas.findRegion(ListaPiezas.values()[4].spriteBloquePieza), posicionTablero.x + j * PantallaJuego.PIXELES_BLOQUE_UI, posicionTablero.y + i * PantallaJuego.PIXELES_BLOQUE_UI);	break;	// PIEZA AZUL
					case  5: spriteBatch.draw(textureAtlas.findRegion(ListaPiezas.values()[5].spriteBloquePieza), posicionTablero.x + j * PantallaJuego.PIXELES_BLOQUE_UI, posicionTablero.y + i * PantallaJuego.PIXELES_BLOQUE_UI);	break;	// PIEZA VERDE
					case  6: spriteBatch.draw(textureAtlas.findRegion(ListaPiezas.values()[6].spriteBloquePieza), posicionTablero.x + j * PantallaJuego.PIXELES_BLOQUE_UI, posicionTablero.y + i * PantallaJuego.PIXELES_BLOQUE_UI);	break;	// PIEZA ROJA
				}
			}
		}
	}

	private int sumarPuntosColocarPieza(float tiempoDeColocacion) {
		return (int) (PUNTOS_COLOCAR_PIEZA * (1 + (1 / tiempoDeColocacion)));
	}

	private int sumarPuntosCompletarLinea(int lineasCompletadas) {
		return PUNTOS_COMPLETAR_LINEA * lineasCompletadas * lineasCompletadas;
	}

	private void generarNuevasPiezas() {
		piezaJugable	= new Pieza();
		piezaGuardada	= null;
		primeraPieza 	= new Pieza();
		segundaPieza 	= new Pieza();
		terceraPieza 	= new Pieza();
	}

	private void jugarSiguientePieza() {
		piezaJugable = primeraPieza;
		primeraPieza = segundaPieza;
		segundaPieza = terceraPieza;
		terceraPieza = new Pieza();
	}

	private void guardarPuntuacion() {

		ArrayList<Integer>	mejoresPuntuaciones = new ArrayList<>();

		// LEE LAS MEJORES PUNTUACIONES
		try (DataInputStream lector	= new DataInputStream(new FileInputStream("assets/files/puntuaciones.bin"))) {
			for (int i = 0; i < PantallaMenu.PUNTUACIONES_MAXIMAS_MOSTRADAS; i++) mejoresPuntuaciones.add(lector.readInt());
		} catch (EOFException e) {
			System.out.println("Fin de archivo");
		} catch (FileNotFoundException e) {
			System.out.println("Archivo no encontrado");
		} catch (IOException e) {
			System.out.println("Error en la lectura del archivo");
		}

		// ORDENA LAS PUNTUACIONES DE MEJOR A PEOR
		mejoresPuntuaciones.add(puntucionTotal);
		mejoresPuntuaciones.sort(Collections.reverseOrder());

		// ESCRIBE LAS 5 MEJORES PUNTUACIONES EN EL ARCHIVO puntuaciones.bin
		try (DataOutputStream escritor = new DataOutputStream(new FileOutputStream("assets/files/puntuaciones.bin"))) {
			for (int i = 0; i < PantallaMenu.PUNTUACIONES_MAXIMAS_MOSTRADAS && i < mejoresPuntuaciones.size(); i++) escritor.writeInt(mejoresPuntuaciones.get(i));
		} catch (FileNotFoundException e) {
			System.out.println("Archivo no encontrado - Creando uno nuevo...");
		} catch (IOException e) {
			System.out.println("Error en la escritura del archivo");
		}

	}

	private void nuevaPartida() {
		// Resetea el tablero
		tableroJuego		= new Tablero();
		seTerminoPartida	= false;
		estadoAplicacion	= EstadoAplicacion.EJECUTANDO;

		// Resetea la puntuacion y la velocidad de la pieza
		multiplicadorVelocidad	= 1;
		puntucionTotal			= 0;

		// Inicia la musica de nuevo
		if (musicaEstaHabilitada = configurador.preferenciasUsuario.get(ListaPreferencias.HABILITAR_MUSICA.nombrePreferencia)) {
			musicaPantallajuego.play();
		}

		// Genera nuevas piezas y restablece la pieza jugable
		posicionPiezaJugable = posicionInicioPiezaJugable.cpy();
		this.generarNuevasPiezas();
	}

	// ESTE METODO ES LLAMADO EN CADA EJECUCION DEL RENDER
	// COMPRUEBA QUE LA FORMA DE LA piezaJugable NO HAYA CAMBIADO Y DE HACERLO LA COPIA
	// DESPUES COMPRUEBA SI LA piezaJugable SE HA MOVIDO Y DE HACERLO COPIA SU POSICION Y CALCULA LA CAIDA DE NUEVO
	private void actualizaPiezaFantasma() {

		// COMPRUEBA SI LA PIEZAFANTASMA ES IGUAL QUE LA PIEZA JUGABLE
		if (!piezaFantasma.equals(piezaJugable)) piezaFantasma = piezaJugable;

		// SI LA POSICION HA VARIADO, LA COPIA PARA VOLVER A CALCULAR DONDE CAERÍA
		if (!posicionPiezaFantasma.equals(posicionPiezaJugable)) {
			posicionPiezaFantasma = posicionPiezaJugable.cpy();

			// MANTIENE ACTUALIZADA LA POSICION DE LA PIEZA FANTASMA
			while (tableroJuego.puedeBajar(posicionPiezaFantasma, piezaFantasma)) {
				posicionPiezaFantasma.y -= 1;
			}
		}
	}

}
