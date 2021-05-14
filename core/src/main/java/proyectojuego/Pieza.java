package proyectojuego;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Pieza {

	private ListaPiezas tipoPieza;


	 //////////Constructores///////////
	 public Pieza(ListaPiezas tipoPieza){
		 this.tipoPieza = tipoPieza;
	 }

	 public Pieza(){
		this(ListaPiezas.values()[(int) (Math.random() * (ListaPiezas.values().length))]);
	 }

	public ListaPiezas getTipoPieza() {
		return tipoPieza;
	}

	//Rotamos la pieza en el sentido de las agujas del reloj
//	 public void rotarReloj(){
//	 	if(tipo != tipoDePieza.O){
//	 		for (int i=0;i<piezaElegida.length;i++){
//	 			piezaElegida[i]=new Vector2(piezaElegida[i].y,-piezaElegida[i].x);
//			}
//		}
//	 }

	//Rotamos la pieza en sentido contrario a las agujas del reloj
//	public void rotarContraReloj(){
//		if(tipo != tipoDePieza.O){
//			for (int i=0;i<piezaElegida.length;i++){
//				piezaElegida[i]=new Vector2(-piezaElegida[i].y,piezaElegida[i].x);
//			}
//		}
//	}

	//Obtener pieza

//	public Array<Vector2> getPiezaElegida() {
//		return new Array<Vector2>(piezaElegida);
//	}
}
