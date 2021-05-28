package proyectojuego.jugabilidad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import proyectojuego.Juego;

import java.util.Arrays;
import java.util.Objects;


public final class Pieza {

	private static final TextureAtlas	TEXTURE_ATLAS = ((Juego) Gdx.app.getApplicationListener()).getAssetManager().get("ui/texturas.atlas", TextureAtlas.class);

	private ListaPiezas		tipoPieza;
	private Vector2[]		formaPieza;
	public	final Sprite	spritePieza;
	public	final Sprite	spriteBloquePieza;


	// CONSTRUCTORES
	// CREA UNA PIEZA DEL TIPO ESPECIFICADO EN tipoPieza Y CON LA FORMA ESPECIFICADA EN formaPieza
	// USADO EN Tablero PARA CREAR COPIAS DE LA PIEZA CON UNA FORMA ESPECIFICA (PIEZA YA ROTADA) Y HACER PRUEBAS DE COLISIONES
	public Pieza(ListaPiezas tipoPieza, Vector2[] formaPieza) {
		this.tipoPieza			= tipoPieza;
		this.formaPieza			= formaPieza;
		this.spritePieza		= new Sprite(TEXTURE_ATLAS.findRegion(tipoPieza.spritePieza));
		this.spriteBloquePieza	= new Sprite(TEXTURE_ATLAS.findRegion(tipoPieza.spriteBloquePieza));
	}

	// DEVUELVE LA PIEZA ESPECIFICADA EN tipoPieza
	// USADO PARA CREAR PIEZAS ESPECIFICAS
	public Pieza(ListaPiezas tipoPieza) {
		this(tipoPieza, tipoPieza.getFormaPieza());
	}

	// DEVUELVE UNA PIEZA ALEATORIA DE ListaPiezas
	// USADO EN PantallaJuego PARA CREAR NUEVAS PIEZAS
	public Pieza() {
		this(ListaPiezas.values()[(int) (Math.random() * (ListaPiezas.values().length))]);
	}


	//EQUALS & HASHCODE
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Pieza pieza = (Pieza) o;
		return tipoPieza == pieza.tipoPieza && Arrays.equals(formaPieza, pieza.formaPieza);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(tipoPieza);
		result = 31 * result + Arrays.hashCode(formaPieza);
		return result;
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


	// METODOS
	//Rota la pieza en el sentido de las agujas del reloj
	 public void rotarSentidoReloj() {
	 	if(tipoPieza != ListaPiezas.CUADRADO){
			for (Vector2 vector2 : formaPieza) {
				vector2.rotate90(-1);
			}
		}
	 }

	//Rota la pieza en sentido contrario a las agujas del reloj
	public void rotarSentidoContraReloj() {
		if(tipoPieza != ListaPiezas.CUADRADO){
			for (Vector2 vector2 : formaPieza) {
				vector2.rotate90(1);
			}
		}
	}
}
