package proyectojuego.jugabilidad;

import com.badlogic.gdx.math.Vector2;

public class Tablero {

	public final static int ALTO_TABLERO	= 21;
	public final static int ANCHO_TABLERO	= 10;

	private Pieza 		piezaAuxiliar;				//pieza
	private boolean[][] tablero;					//Matriz tablero de juego


	//Se crea el tablero con la nueva pieza colocada en el centro
	public Tablero() {
		tablero=new boolean[ANCHO_TABLERO][ALTO_TABLERO];
	}



	////Comprobacion posibilidad de movimientos///
	public boolean puedeMoverIzquierda(Vector2 posicionPieza, Pieza pieza) {
		return this.detectarColisiones(posicionPieza.cpy().add(-1, 0).cpy(), pieza);
	}

	public boolean puedeMoverDerecha(Vector2 posicionPieza, Pieza pieza) {
		return this.detectarColisiones(posicionPieza.cpy().add(1, 0), pieza);
	}

	public boolean puedeBajar(Vector2 posicionPieza, Pieza pieza) {
		return this.detectarColisiones(posicionPieza.cpy().add(0, -1).cpy(), pieza);
	}

	public boolean puedeRotarSentidoReloj(Vector2 posicionPieza, Pieza pieza) {
		piezaAuxiliar = new Pieza(pieza.getTipoPieza(), pieza.getFormaPieza());
		piezaAuxiliar.rotarSentidoReloj();
		return this.detectarColisiones(posicionPieza.add(0, 0), piezaAuxiliar);
	}

	public boolean puedeRotarSentidoContrarioReloj(Vector2 posicionPieza, Pieza pieza) {
		piezaAuxiliar = new Pieza(pieza.getTipoPieza(), pieza.getFormaPieza());
		piezaAuxiliar.rotarSentidoContraReloj();
		return this.detectarColisiones(posicionPieza.add(0, 0), piezaAuxiliar);
	}

	private boolean detectarColisiones(Vector2 posicionPieza, Pieza formaPieza) {
		return true;
	}

}
