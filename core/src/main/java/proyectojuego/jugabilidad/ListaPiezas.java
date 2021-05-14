package proyectojuego.jugabilidad;

import com.badlogic.gdx.math.Vector2;

public enum ListaPiezas {

	PALO		(new Vector2[] {new Vector2(-2, 0),	new Vector2(-1, 0),	new Vector2(0, 0),	new Vector2(1, 0)},	"PiezaCian",		"BloqueCian"),
	CUADRADO	(new Vector2[] {new Vector2(-1, 0),	new Vector2(0, 0),	new Vector2(-1, -1),	new Vector2(0, -1)},	"PiezaAmarilla",	"BloqueAmarillo"),
	T			(new Vector2[] {new Vector2(-1, 0),	new Vector2(0, 0),	new Vector2(1, 0),	new Vector2(0, -1)},	"PiezaMorada",		"BloqueMorado"),
	L			(new Vector2[] {new Vector2(-1, -1),	new Vector2(-1, 0),	new Vector2(0, 0),	new Vector2(1, 0)},	"PiezaNaranja", 	"BloqueNaranja"),
	L_INVERSA	(new Vector2[] {new Vector2(-1, 0),	new Vector2(0, 0),	new Vector2(1, 0),	new Vector2(1, -1)},	"PiezaAzul", 		"BloqueAzul"),
	Z			(new Vector2[] {new Vector2(-1, 0),	new Vector2(0, -1),	new Vector2(0, 0),	new Vector2(1, 0)},	"PiezaRoja",		"BloqueRojo"),
	Z_INVERSA	(new Vector2[] {new Vector2(-1, -1),	new Vector2(0, -1),	new Vector2(0, 0),	new Vector2(1, 0)},	"PiezaVerde",		"BloqueVerde");

	public final Vector2[]	formaPieza;
	public final String		spritePieza;
	public final String		spriteBloquePieza;

	ListaPiezas(Vector2[] formaPieza, String spritePieza, String spriteBloquePieza) {
		this.formaPieza 		= formaPieza;
		this.spritePieza 		= spritePieza;
		this.spriteBloquePieza 	= spriteBloquePieza;
	}

}
