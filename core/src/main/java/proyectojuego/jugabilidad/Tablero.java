package proyectojuego.jugabilidad;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Tablero {

	private final static int ALTO_TABLERO = 20;	//Ver dimensiones Screen
	private final static int ANCHO_TABLERO = 10;

	private Pieza 		piezaNueva;					//pieza
	private Vector2 	posicionPiezaNuevaTablero;	//coordenadas pieza en tablero
	private boolean[][] tablero;					//Matriz tablero de juego


	//Se crea el tablero con la nueva pieza colocada en el centro
	public Tablero() {
		tablero=new boolean[ANCHO_TABLERO][ALTO_TABLERO];
		nuevaPieza();
	}

	//Pieza centrada
	private void nuevaPieza(){
		piezaNueva= new Pieza();
		//Centramos la pieza
		posicionPiezaNuevaTablero = new Vector2(ANCHO_TABLERO * .5f, ALTO_TABLERO -1);
		//Comprobar si la pieza choca con otra. Estas muerto.
	}

	//Calcula coordenadas de la pieza en el tablero (de sus bloques)
	public static Array<Vector2> coordPiezaTablero(Pieza pieza, Vector2 posicionTablero){
		Array<Vector2> coordTablero= new Array<Vector2>();
		Vector2[] coordPieza=  pieza.formaPieza;  	//coordenadas de bloques de la pieza
		for(Vector2 cp: coordPieza){
			coordTablero.add(cp.add(posicionTablero));			//Sumo la posicion de cada bloque a la posicion en tablero
		}
		return coordTablero;
	}

	//Compruebo si me puedo mover(Colisión con bloque adyacente)
	private boolean colisionPieza(Vector2 desplazamiento){
		Array<Vector2> coorPieza=coordPiezaTablero(piezaNueva, posicionPiezaNuevaTablero); 	//Coordenadas de la pieza
		for(Vector2 cp: coorPieza){															//cp=coordenada de cada bloque
			cp.add(desplazamiento); 														//sumo la posicion siguiente a la pieza
			//Comprobacion colisión con laterales y suelo.
			if(cp.x < 0 || cp.y <0 || cp.x > ANCHO_TABLERO -1){								//Devuelve true cuando choca
				return true;
			}
			//Comprobacion colision con otras piezas
			if(cp.y < ALTO_TABLERO && tablero[Math.round(cp.x)][Math.round(cp.y)])
				return true;																//True si la posicion en tablero esta ocupada
		}
		return false;

	}
	////Comprobacion posibilidad de movimientos///

	private boolean puedeMoverDerecha(){
		return !colisionPieza(new Vector2(1,0));
	}

	private boolean puedeMoverIzquierda(){
		return !colisionPieza(new Vector2(-1,0));
	}

	private boolean puedeCaer(){
		return !colisionPieza(new Vector2(0,1));
	}

	private boolean puedeRotarComoReloj(){
		piezaNueva.rotarReloj();									//Roto la pieza
		boolean comprobacion=colisionPieza(new Vector2(0,0));	//Compruebo colision
		piezaNueva.rotarContraReloj();								//La devuelvo a su posicion
		return !comprobacion;										//Si hay colision devuelvo false, No puede rotar
	}

	private boolean puedeRotarContraReloj(){
		piezaNueva.rotarContraReloj();
		boolean comprobacion=colisionPieza((new Vector2(0,0)));
		piezaNueva.rotarReloj();
		return !comprobacion;
	}


	////////////////Movimientos///////////////////

	public void moverDerecha(){
		if(puedeMoverDerecha()){
			posicionPiezaNuevaTablero.add(new Vector2(1,0));
		}
	}

	public void moverIzquierda(){
		if(puedeMoverIzquierda()){
			posicionPiezaNuevaTablero.add(new Vector2(-1,0));
		}
	}

	public void moverAbajo(){

	}
	/////TODO: bajar
	///TODO: Duda, ¿Se modifica un vector si operamos dentro de un bucle?

}
