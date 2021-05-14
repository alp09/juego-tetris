package proyectojuego.jugabilidad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import proyectojuego.Juego;

public class Pieza {

	public static TextureAtlas	textureAtlas = ((Juego) Gdx.app.getApplicationListener()).getAssetManager().get("ui/texturas.atlas", TextureAtlas.class);

	public ListaPiezas		tipoPieza;
	public Vector2[]		formaPieza;
	public final Sprite		spritePieza;
	public final Sprite		spriteBloquePieza;


	 //////////Constructores///////////
	 public Pieza(ListaPiezas tipoPieza){
	 	this.tipoPieza			= tipoPieza;
		 this.formaPieza		= tipoPieza.getFormaPieza();
		 this.spritePieza		= new Sprite(textureAtlas.findRegion(tipoPieza.getSpritePieza()));
		 this.spriteBloquePieza	= new Sprite(textureAtlas.findRegion(tipoPieza.getSpriteBloquePieza()));
	 }

	 public Pieza(){
		this(ListaPiezas.values()[(int) (Math.random() * (ListaPiezas.values().length))]);
	 }


	//Rotamos la pieza en el sentido de las agujas del reloj
	 public void rotarReloj(){
	 	if(tipoPieza != ListaPiezas.CUADRADO){
			for (Vector2 vector2 : formaPieza) {
				vector2.rotateDeg(-90);
			}
		}
	 }

	//Rotamos la pieza en sentido contrario a las agujas del reloj
	public void rotarContraReloj(){
		if(tipoPieza != ListaPiezas.CUADRADO){
			for (Vector2 vector2 : formaPieza) {
				vector2.rotateDeg(90);
			}
		}
	}

	//Obtener pieza
//	public Array<Vector2> getPiezaElegida() {
//		return new Array<Vector2>(piezaElegida);
//	}
}
