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

	public	static final int 	ALTO_TABLERO	= 23;	// Se añaden 3 filas más para que la pieza generada no aparezca fuera del tablero.
	public	static final int 	ANCHO_TABLERO	= 10;

	private	static Tablero 		tableroJuego;
	private static TextureAtlas textureAtlas = ((Juego) Gdx.app.getApplicationListener()).getAssetManager().get("ui/texturas.atlas", TextureAtlas.class);

	private	int[][] 	contenidoTablero;
	private Pieza 		piezaAuxiliar;


	// CONSTRUCTOR PRIVADO
	private Tablero() {
		contenidoTablero = new int[ANCHO_TABLERO][ALTO_TABLERO];
		for (int[] columna: contenidoTablero) Arrays.fill(columna, -1);
	}


	// GET INSTANCE - PATRON SINGLETON
	public static Tablero getInstance() {
		if (tableroJuego == null) tableroJuego = new Tablero();
		return tableroJuego;
	}


	// METODOS COORDENADAS
	private Vector2[] traducirCoordenadasPiezaATablero(Vector2 posicionPieza, Pieza pieza) {

		Vector2[] posicionBloquesEnTablero = new Vector2[pieza.getFormaPieza().length];

		for (int i = 0; i < posicionBloquesEnTablero.length; i++) {
			posicionBloquesEnTablero[i] = new Vector2(pieza.getFormaPieza()[i].x + posicionPieza.x, pieza.getFormaPieza()[i].y + posicionPieza.y);
		}

		return posicionBloquesEnTablero;
	}


	// METODOS COLISION
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
		piezaAuxiliar = new Pieza(pieza.getTipoPieza(), pieza.getFormaPieza());
		piezaAuxiliar.rotarSentidoReloj();
		return !this.detectarColisiones(posicionPieza.add(0, 0), piezaAuxiliar);
	}

	public boolean puedeRotarSentidoContrarioReloj(Vector2 posicionPieza, Pieza pieza) {
		piezaAuxiliar = new Pieza(pieza.getTipoPieza(), pieza.getFormaPieza());
		piezaAuxiliar.rotarSentidoContraReloj();
		return !this.detectarColisiones(posicionPieza.add(0, 0), piezaAuxiliar);
	}

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


	// METODO DIBUJAR - RECORRE LA MATRIZ Y DIBUJA POR PANTALLA EL CONTENIDO DE ESTA A PARTIR DE posicionComienzoDibujo
	public void dibujarContenidoTablero(SpriteBatch spriteBatch, Vector2 posicionComienzoDibujo) {

		for (int i = 0; i < ALTO_TABLERO; i++) {
			for (int j = 0; j < ANCHO_TABLERO; j++) {
				switch (contenidoTablero[j][i]) {
					case -1: continue;
					case  0: spriteBatch.draw(textureAtlas.findRegion(ListaPiezas.values()[0].getSpriteBloquePieza()), posicionComienzoDibujo.x + j * PantallaJuego.PIXELES_BLOQUE_UI, posicionComienzoDibujo.y + i * PantallaJuego.PIXELES_BLOQUE_UI);	break;	// PIEZA CIAN
					case  1: spriteBatch.draw(textureAtlas.findRegion(ListaPiezas.values()[1].getSpriteBloquePieza()), posicionComienzoDibujo.x + j * PantallaJuego.PIXELES_BLOQUE_UI, posicionComienzoDibujo.y + i * PantallaJuego.PIXELES_BLOQUE_UI);	break;	// PIEZA AMARILLA
					case  2: spriteBatch.draw(textureAtlas.findRegion(ListaPiezas.values()[2].getSpriteBloquePieza()), posicionComienzoDibujo.x + j * PantallaJuego.PIXELES_BLOQUE_UI, posicionComienzoDibujo.y + i * PantallaJuego.PIXELES_BLOQUE_UI);	break;	// PIEZA MORADA
					case  3: spriteBatch.draw(textureAtlas.findRegion(ListaPiezas.values()[3].getSpriteBloquePieza()), posicionComienzoDibujo.x + j * PantallaJuego.PIXELES_BLOQUE_UI, posicionComienzoDibujo.y + i * PantallaJuego.PIXELES_BLOQUE_UI);	break;	// PIEZA NARANJA
					case  4: spriteBatch.draw(textureAtlas.findRegion(ListaPiezas.values()[4].getSpriteBloquePieza()), posicionComienzoDibujo.x + j * PantallaJuego.PIXELES_BLOQUE_UI, posicionComienzoDibujo.y + i * PantallaJuego.PIXELES_BLOQUE_UI);	break;	// PIEZA AZUL
					case  5: spriteBatch.draw(textureAtlas.findRegion(ListaPiezas.values()[5].getSpriteBloquePieza()), posicionComienzoDibujo.x + j * PantallaJuego.PIXELES_BLOQUE_UI, posicionComienzoDibujo.y + i * PantallaJuego.PIXELES_BLOQUE_UI);	break;	// PIEZA VERDE
					case  6: spriteBatch.draw(textureAtlas.findRegion(ListaPiezas.values()[6].getSpriteBloquePieza()), posicionComienzoDibujo.x + j * PantallaJuego.PIXELES_BLOQUE_UI, posicionComienzoDibujo.y + i * PantallaJuego.PIXELES_BLOQUE_UI);	break;	// PIEZA ROJA
				}
			}
		}
	}


	// METODO COLOCAR - ESTABLECE EL VALOR CORRESPONDIENTE DE ListaPiezas EN LAS COORDENADAS DE LA PIEZA JUGABLE
	public void colocarPieza(Vector2 posicionPieza, Pieza pieza) {

		Vector2[] posicionBloquesEnTablero = traducirCoordenadasPiezaATablero(posicionPieza, pieza);
		List listaBloques = Arrays.asList(ListaPiezas.values());

		for (Vector2 posicionBloque: posicionBloquesEnTablero) {
			contenidoTablero[(int) posicionBloque.x][(int) posicionBloque.y] = listaBloques.indexOf(pieza.getTipoPieza());
		}

	}


}
