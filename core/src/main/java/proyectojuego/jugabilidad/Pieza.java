package proyectojuego.jugabilidad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import proyectojuego.Juego;

public class Pieza {

	private static TextureAtlas	textureAtlas = ((Juego) Gdx.app.getApplicationListener()).getAssetManager().get("ui/texturas.atlas", TextureAtlas.class);

	private ListaPiezas		tipoPieza;
	private Vector2[]		formaPieza;
	public final Sprite		spritePieza;
	public final Sprite		spriteBloquePieza;


	 // Constructores
	public Pieza(ListaPiezas tipoPieza, Vector2[] formaPieza) {
		this.tipoPieza			= tipoPieza;
		this.formaPieza			= formaPieza;
		this.spritePieza		= new Sprite(textureAtlas.findRegion(tipoPieza.getSpritePieza()));
		this.spriteBloquePieza	= new Sprite(textureAtlas.findRegion(tipoPieza.getSpriteBloquePieza()));
	}

	public Pieza(ListaPiezas tipoPieza) {
		this(tipoPieza, tipoPieza.getFormaPieza());
	}

	public Pieza() {
		this(ListaPiezas.values()[(int) (Math.random() * (ListaPiezas.values().length))]);
	}


	 // GETTERS
	public ListaPiezas getTipoPieza() {
		return tipoPieza;
	}
	public Vector2[] getFormaPieza() {
		Vector2[] copiaFormaPieza = new Vector2[formaPieza.length];
		for (int i = 0; i < formaPieza.length; i++) {
			copiaFormaPieza[i] = formaPieza[i].cpy();
		}
		return copiaFormaPieza;
	}


	//Rotamos la pieza en el sentido de las agujas del reloj
	 public void rotarSentidoReloj() {
	 	if(tipoPieza != ListaPiezas.CUADRADO){
			for (Vector2 vector2 : formaPieza) {
				vector2.rotate90(-1);
			}
		}
	 }


	//Rotamos la pieza en sentido contrario a las agujas del reloj
	public void rotarSentidoContraReloj() {
		if(tipoPieza != ListaPiezas.CUADRADO){
			for (Vector2 vector2 : formaPieza) {
				vector2.rotate90(1);
			}
		}
	}
}
