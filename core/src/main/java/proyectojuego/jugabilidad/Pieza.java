package proyectojuego.jugabilidad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
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
	 public void rotarReloj(){
	 	if(tipoPieza != ListaPiezas.CUADRADO){
	 		for (int i=0;i<tipoPieza.formaPieza.length;i++){
				tipoPieza.formaPieza[i].add(tipoPieza.formaPieza[i].y,-tipoPieza.formaPieza[i].x);
			}
		}
	 }

	//Rotamos la pieza en sentido contrario a las agujas del reloj
	public void rotarContraReloj(){
		if(tipoPieza != ListaPiezas.CUADRADO){
			for (int i=0;i<tipoPieza.formaPieza.length;i++){
				tipoPieza.formaPieza[i].add(-tipoPieza.formaPieza[i].y,tipoPieza.formaPieza[i].x);
			}
		}
	}

	//Obtener pieza

//	public Array<Vector2> getPiezaElegida() {
//		return new Array<Vector2>(piezaElegida);
//	}
}
