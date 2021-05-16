package proyectojuego.jugabilidad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import proyectojuego.Juego;

public class Tablero {

	public	static final int 	ALTO_TABLERO	= 24;
	public	static final int 	ANCHO_TABLERO	= 10;
	private	static Tablero		TABLERO_JUEGO;

	private static TextureAtlas textureAtlas = ((Juego) Gdx.app.getApplicationListener()).getAssetManager().get("ui/texturas.atlas", TextureAtlas.class);

	private	int[][] 	contenidoTablero;
	private Pieza 		piezaAuxiliar;


	// CONSTRUCTOR PRIVADO
	private Tablero() {
		contenidoTablero = new int[ANCHO_TABLERO][ALTO_TABLERO];
		for (int i = 0; i < ALTO_TABLERO / 2; i++) {
			for (int j = 0; j < ANCHO_TABLERO; j++) {
				contenidoTablero[j][i] = (int) (Math.random() * 7);
			}
		}
	}


	// GET INSTANCE - PATRON SINGLETON
	public static Tablero getInstance() {
		if (TABLERO_JUEGO == null) TABLERO_JUEGO = new Tablero();
		return TABLERO_JUEGO;
	}


	// METODO DIBUJAR
	public void dibujarContenidoTablero(SpriteBatch spriteBatch, Vector2 posicionComienzoDibujo) {
		for (int i = 0; i < ALTO_TABLERO; i++) {
			for (int j = 0; j < ANCHO_TABLERO; j++) {
				switch (contenidoTablero[j][i]) {

					case 0: continue;
					case 1: spriteBatch.draw(textureAtlas.findRegion(ListaPiezas.values()[1].getSpriteBloquePieza()), posicionComienzoDibujo.x + j * 32, posicionComienzoDibujo.y + i * 32);	break;	// PIEZA CIAN
					case 2: spriteBatch.draw(textureAtlas.findRegion(ListaPiezas.values()[2].getSpriteBloquePieza()), posicionComienzoDibujo.x + j * 32, posicionComienzoDibujo.y + i * 32);	break;	// PIEZA AMARILLA
					case 3: spriteBatch.draw(textureAtlas.findRegion(ListaPiezas.values()[3].getSpriteBloquePieza()), posicionComienzoDibujo.x + j * 32, posicionComienzoDibujo.y + i * 32);	break;	// PIEZA MORADA
					case 4: spriteBatch.draw(textureAtlas.findRegion(ListaPiezas.values()[4].getSpriteBloquePieza()), posicionComienzoDibujo.x + j * 32, posicionComienzoDibujo.y + i * 32);	break;	// PIEZA NARANJA
					case 5: spriteBatch.draw(textureAtlas.findRegion(ListaPiezas.values()[5].getSpriteBloquePieza()), posicionComienzoDibujo.x + j * 32, posicionComienzoDibujo.y + i * 32);	break;	// PIEZA AZUL
					case 6: spriteBatch.draw(textureAtlas.findRegion(ListaPiezas.values()[6].getSpriteBloquePieza()), posicionComienzoDibujo.x + j * 32, posicionComienzoDibujo.y + i * 32);	break;	// PIEZA VERDE
					case 7: spriteBatch.draw(textureAtlas.findRegion(ListaPiezas.values()[7].getSpriteBloquePieza()), posicionComienzoDibujo.x + j * 32, posicionComienzoDibujo.y + i * 32);	break;	// PIEZA ROJA

				}
			}
		}
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

	private boolean detectarColisiones(Vector2 posicionPieza, Pieza piezaAuxiliar) {

		Vector2[] posicionBloquesEnTablero = new Vector2[piezaAuxiliar.getFormaPieza().length];
		for (int i = 0; i < posicionBloquesEnTablero.length; i++) {
			posicionBloquesEnTablero[i] = new Vector2(piezaAuxiliar.getFormaPieza()[i].x + posicionPieza.x, piezaAuxiliar.getFormaPieza()[i].y + posicionPieza.y);
		}

		boolean hayColision = false;

		for (Vector2 posicionBloque: posicionBloquesEnTablero) {

			// SI CHOCA CON LAD PAREDES
			if (posicionBloque.x < 0 || posicionBloque.x >= ANCHO_TABLERO) {
				hayColision = true;
				break;
			}

			// SI TOCA FONDO
			if (posicionBloque.y < 0) {
				hayColision = true;
				break;
			}

			// SI LA CASILLA YA ESTA OCUPADA
			if (contenidoTablero[(int) posicionBloque.x][(int) posicionBloque.y] != 0) {
				hayColision = true;
				break;
			}
		}

		return hayColision;
	}

}
