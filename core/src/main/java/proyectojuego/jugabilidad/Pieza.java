package proyectojuego.jugabilidad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import proyectojuego.Juego;

public class Pieza {

	public static TextureAtlas	textureAtlas = ((Juego) Gdx.app.getApplicationListener()).getAssetManager().get("ui/texturas.atlas", TextureAtlas.class);

	public final ListaPiezas	tipoPieza;
	public final Sprite			spritePieza;


	 //////////Constructores///////////
	 public Pieza(ListaPiezas tipoPieza){
		 this.tipoPieza		= tipoPieza;
		 this.spritePieza	= new Sprite(textureAtlas.findRegion(tipoPieza.spritePieza));
	 }

	 public Pieza(){
		this(ListaPiezas.values()[(int) (Math.random() * (ListaPiezas.values().length))]);
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
