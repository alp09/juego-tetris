package proyectojuego;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Pieza {
	//Piezas que componen el juego
	public  enum tipoDePieza {T,O,I,L,Z,J,S};

	private static Vector2[][] piezas = {

		//T
		{new Vector2(-1,0), new Vector2(0,0), new Vector2(1,0), new Vector2(0,-1)},
		//O
		{new Vector2(-1,0), new Vector2(0,0), new Vector2(-1,-1), new Vector2(0,-1)},
		//I
		{new Vector2(-2,0), new Vector2(-1,0), new Vector2(0,0), new Vector2(1,0)},
		//L
		{new Vector2(-1,-1), new Vector2(-1,0), new Vector2(0,0), new Vector2(1,0)},
		//Z
		{new Vector2(-1,0), new Vector2(0,-1), new Vector2(0,0), new Vector2(1,0)},
		//J
		{new Vector2(-1,0), new Vector2(0,0), new Vector2(1,0), new Vector2(1,-1)},
		//S
		{new Vector2(-1,-1), new Vector2(0,-1), new Vector2(0,0), new Vector2(1,0)}
	};

	private Vector2[] piezaElegida;
	private tipoDePieza tipo;

	 //////////Constructores///////////

	 public Pieza(tipoDePieza pieza){
	 	tipo=pieza;
	 	piezaElegida=piezas[pieza.ordinal()];  //Devuelve la posición del enum
	 }

	 public Pieza(){
		 this(tipoDePieza.values()[(int)(Math.random()*(tipoDePieza.values().length))]);
	 }

	//Rotamos la pieza en el sentido de las agujas del reloj

	 public void rotarReloj(){
	 	if(tipo != tipoDePieza.O){
	 		for (int i=0;i<piezaElegida.length;i++){
	 			piezaElegida[i]=new Vector2(piezaElegida[i].y,-piezaElegida[i].x);
			}
		}
	 }

	//Rotamos la pieza en sentido contrario a las agujas del reloj

	public void rotarContraReloj(){
		if(tipo != tipoDePieza.O){
			for (int i=0;i<piezaElegida.length;i++){
				piezaElegida[i]=new Vector2(-piezaElegida[i].y,piezaElegida[i].x);
			}
		}
	}

	//Obtener pieza

//	public Array<Vector2> getPiezaElegida() {
//		return new Array<Vector2>(piezaElegida);
//	}
}
