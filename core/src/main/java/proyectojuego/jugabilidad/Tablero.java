package proyectojuego.jugabilidad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import proyectojuego.Juego;
import proyectojuego.pantallas.PantallaJuego;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tablero {

	private static final TextureAtlas TEXTURE_ATLAS = ((Juego) Gdx.app.getApplicationListener()).getAssetManager().get("ui/texturas.atlas", TextureAtlas.class);

	public	final int 	ALTO_TABLERO			= 23;			// Se añaden 3 filas más para que la pieza generada no aparezca fuera del tablero.
	public	final int 	ANCHO_TABLERO			= 10;			// Se usan en la clase PantallaJuego para determinar la posicionInicioPiezaJugable
	public	final int 	ALTURA_COMIENZO_PIEZA	= 20;			// Fila en la que la piezaJugable comienza

	private	static Tablero tableroJuego;						// Contiene la instancia del Tablero para el patron singleton

	private	int[][] 	contenidoTablero;						// El tablero en sí - cada indice contiene un int que define el contenido de esa casilla del tablero
	private int			filaOcupadaMasAlta = 0;

	// CONSTRUCTOR PRIVADO
	private Tablero() {
		contenidoTablero = new int[ANCHO_TABLERO][ALTO_TABLERO];
		for (int[] columna: contenidoTablero) Arrays.fill(columna, -1);		// Llena la matriz con el valor -1 para indicar los espacios vacios
	}


	// GET INSTANCE - PATRON SINGLETON
	public static Tablero getInstance() {
		if (tableroJuego == null) tableroJuego = new Tablero();
		return tableroJuego;
	}


	// METODO COORDENADAS
	// La posicion de cada bloque que compone la pieza se consigue sumando la variable Vector2 posicionPiezaJugable con cada indice de Vector2[] pieza.formaPieza
	// Con las coordenadas resultantes se puede comprobar el contenido o asignar un valor a un indice de contenidoTablero
	private Vector2[] traducirCoordenadasPiezaATablero(Vector2 posicionPieza, Pieza pieza) {

		Vector2[] posicionBloquesEnTablero = new Vector2[pieza.getFormaPieza().length];

		for (int i = 0; i < posicionBloquesEnTablero.length; i++) {
			posicionBloquesEnTablero[i] = new Vector2(pieza.getFormaPieza()[i].x + posicionPieza.x, pieza.getFormaPieza()[i].y + posicionPieza.y);
		}

		return posicionBloquesEnTablero;
	}


	// METODOS COLISION
	// Los metodos públicos pasan al método privado las coordenadas de la pieza si el movimiento que quiere realizar fuese posible
	// El método privado detectarColisiones devuelve false si las coordenadas proporcionadas no salen del tablero y estan libres, en otras palabras, no choca con las paredes ni otras piezas
	private boolean detectarColisiones(Vector2 posicionPieza, Pieza pieza) {

		Vector2[] posicionBloquesEnTablero = traducirCoordenadasPiezaATablero(posicionPieza, pieza);
		boolean hayColision = false;

		for (Vector2 posicionBloque: posicionBloquesEnTablero) {

			try {
				if (contenidoTablero[(int) posicionBloque.x][(int) posicionBloque.y] != -1) {
					hayColision = true;
					break;
				}
			} catch (ArrayIndexOutOfBoundsException exception) {
				// SI SE PRODUCE ESTA EXCEPCION SIGNIFICA QUE EL BLOQUE SE SALDRIA DEL TABLERO
				hayColision = true;
			}

		}

		return hayColision;
	}

	public boolean puedeMoverIzquierda(Vector2 posicionPieza, Pieza pieza) {
		return !this.detectarColisiones(posicionPieza.cpy().add(-1, 0).cpy(), pieza);
	}

	public boolean puedeMoverDerecha(Vector2 posicionPieza, Pieza pieza) {
		return !this.detectarColisiones(posicionPieza.cpy().add(1, 0), pieza);
	}

	public boolean puedeBajar(Vector2 posicionPieza, Pieza pieza) {
		return !this.detectarColisiones(posicionPieza.cpy().add(0, -1).cpy(), pieza);
	}

	public boolean puedeRotarSentidoReloj(Vector2 posicionPieza, Pieza pieza) {
		if (pieza.getTipoPieza() == ListaPiezas.CUADRADO) return true;
		Pieza piezaAuxiliar = new Pieza(pieza.getTipoPieza(), pieza.getFormaPieza());
		piezaAuxiliar.rotarSentidoReloj();
		return !this.detectarColisiones(posicionPieza.add(0, 0), piezaAuxiliar);
	}

	public boolean puedeRotarSentidoContrarioReloj(Vector2 posicionPieza, Pieza pieza) {
		if (pieza.getTipoPieza() == ListaPiezas.CUADRADO) return true;
		Pieza piezaAuxiliar = new Pieza(pieza.getTipoPieza(), pieza.getFormaPieza());
		piezaAuxiliar.rotarSentidoContraReloj();
		return !this.detectarColisiones(posicionPieza.add(0, 0), piezaAuxiliar);
	}


	// METODO DIBUJAR - RECORRE LA MATRIZ Y DIBUJA POR PANTALLA EL CONTENIDO DE ESTA A PARTIR DE posicionComienzoDibujo
	public void dibujarContenidoTablero(SpriteBatch spriteBatch, Vector2 posicionComienzoDibujo) {

		for (int i = 0; i < ALTO_TABLERO; i++) {
			for (int j = 0; j < ANCHO_TABLERO; j++) {
				switch (contenidoTablero[j][i]) {
					case -1: continue;
					case  0: spriteBatch.draw(TEXTURE_ATLAS.findRegion(ListaPiezas.values()[0].getSpriteBloquePieza()), posicionComienzoDibujo.x + j * PantallaJuego.PIXELES_BLOQUE_UI, posicionComienzoDibujo.y + i * PantallaJuego.PIXELES_BLOQUE_UI);	break;	// PIEZA CIAN
					case  1: spriteBatch.draw(TEXTURE_ATLAS.findRegion(ListaPiezas.values()[1].getSpriteBloquePieza()), posicionComienzoDibujo.x + j * PantallaJuego.PIXELES_BLOQUE_UI, posicionComienzoDibujo.y + i * PantallaJuego.PIXELES_BLOQUE_UI);	break;	// PIEZA AMARILLA
					case  2: spriteBatch.draw(TEXTURE_ATLAS.findRegion(ListaPiezas.values()[2].getSpriteBloquePieza()), posicionComienzoDibujo.x + j * PantallaJuego.PIXELES_BLOQUE_UI, posicionComienzoDibujo.y + i * PantallaJuego.PIXELES_BLOQUE_UI);	break;	// PIEZA MORADA
					case  3: spriteBatch.draw(TEXTURE_ATLAS.findRegion(ListaPiezas.values()[3].getSpriteBloquePieza()), posicionComienzoDibujo.x + j * PantallaJuego.PIXELES_BLOQUE_UI, posicionComienzoDibujo.y + i * PantallaJuego.PIXELES_BLOQUE_UI);	break;	// PIEZA NARANJA
					case  4: spriteBatch.draw(TEXTURE_ATLAS.findRegion(ListaPiezas.values()[4].getSpriteBloquePieza()), posicionComienzoDibujo.x + j * PantallaJuego.PIXELES_BLOQUE_UI, posicionComienzoDibujo.y + i * PantallaJuego.PIXELES_BLOQUE_UI);	break;	// PIEZA AZUL
					case  5: spriteBatch.draw(TEXTURE_ATLAS.findRegion(ListaPiezas.values()[5].getSpriteBloquePieza()), posicionComienzoDibujo.x + j * PantallaJuego.PIXELES_BLOQUE_UI, posicionComienzoDibujo.y + i * PantallaJuego.PIXELES_BLOQUE_UI);	break;	// PIEZA VERDE
					case  6: spriteBatch.draw(TEXTURE_ATLAS.findRegion(ListaPiezas.values()[6].getSpriteBloquePieza()), posicionComienzoDibujo.x + j * PantallaJuego.PIXELES_BLOQUE_UI, posicionComienzoDibujo.y + i * PantallaJuego.PIXELES_BLOQUE_UI);	break;	// PIEZA ROJA
				}
			}
		}
	}


	// METODO COLOCAR - ESTABLECE EL INDICE DE contenidoTablero DONDE CAYO LA PIEZA CON EL int CORRESPONDIENTE
	public boolean colocarPieza(Vector2 posicionPieza, Pieza pieza) {

		boolean				gameOver					= false;
		Vector2[] 			posicionBloquesEnTablero	= traducirCoordenadasPiezaATablero(posicionPieza, pieza);
		List<ListaPiezas>	listaBloques				= Arrays.asList(ListaPiezas.values());						// se pasa a List para usar el metodo indexOf()

		for (Vector2 posicionBloque: posicionBloquesEnTablero) {
			contenidoTablero[(int) posicionBloque.x][(int) posicionBloque.y] = listaBloques.indexOf(pieza.getTipoPieza());
			if (posicionBloque.y >= ALTURA_COMIENZO_PIEZA) gameOver = true;
			if (posicionBloque.y > filaOcupadaMasAlta) filaOcupadaMasAlta = (int) posicionBloque.y;
		}
		System.out.println(filaOcupadaMasAlta);

		return gameOver;
	}


	// METODO ELIMIAR - Elimina las filas completas del tablero
	public int eliminarFilasCompletas() {

		int bloquesOcupados;
		ArrayList<Integer> filasCompletas	= new ArrayList<>();
		int filasEliminadas					= 0;

		// AÑADE EL NUMERO DE LAS FILAS COMPLETAS AL ARRAYLIST filasCompletas
		for (int i = 0; i < filaOcupadaMasAlta; i++) {
			bloquesOcupados = 0;
			for (int j = 0; j < ANCHO_TABLERO / 2; j++) {
				if (contenidoTablero[j][i] == -1) break;
				if (contenidoTablero[ANCHO_TABLERO - 1 - j][i] == -1) break;
				bloquesOcupados += 2;
			}
			if (bloquesOcupados == ANCHO_TABLERO) filasCompletas.add(i);
		}

		// SI HAY ALGUNA FILA COMPLETA, EL TAMAÑO DEL ARRAYLIST SERA SUPERIOR A 0 Y ENTRA AQUI
		if (filasCompletas.size() > 0) {

			// POR CADA FILA AÑADIDA AL ARRALIST
			for (Integer indiceFila: filasCompletas) {

				// ESTABLECE EL VALOR DE LA FILA SUPERIOR EN LA FILA ACTUAL
				for (int i = indiceFila - filasEliminadas; i < filaOcupadaMasAlta + 1; i++) {
					for (int j = 0; j < ANCHO_TABLERO; j++) {
						contenidoTablero[j][i] = contenidoTablero[j][i + 1];
					}
				}

				// PUESTO QUE LAS FILAS BAJAN, SI HAY MAS DE UNA FILA COMPLETA SE DEBE ACTUALIZAR EL indiceFila
				filasEliminadas++;
			}
			filaOcupadaMasAlta -= filasEliminadas;
		}

		return filasEliminadas;
	}

}
