package proyectojuego.jugabilidad;

import com.badlogic.gdx.math.Vector2;

public enum ListaPiezas {

	PALO		(new Vector2[] {new Vector2(-2, 0),	new Vector2(-1, 0),	new Vector2(0, 0),	new Vector2(1, 0)},	"PiezaCian",		"BloqueCian"),
	CUADRADO	(new Vector2[] {new Vector2(-1, 0),	new Vector2(0, 0),	new Vector2(-1, -1),	new Vector2(0, -1)},	"PiezaAmarilla",	"BloqueAmarillo"),
	T			(new Vector2[] {new Vector2(-1, 0),	new Vector2(0, 0),	new Vector2(1, 0),	new Vector2(0, -1)},	"PiezaMorada",	"BloqueMorado"),
	L			(new Vector2[] {new Vector2(-1, -1),	new Vector2(-1, 0),	new Vector2(0, 0),	new Vector2(1, 0)},	"PiezaAzul", 		"BloqueAzul"),
	L_INVERSA	(new Vector2[] {new Vector2(-1, 0),	new Vector2(0, 0),	new Vector2(1, 0),	new Vector2(1, -1)},	"PiezaNaranja", 	"BloqueNaranja"),
	Z			(new Vector2[] {new Vector2(-1, 0),	new Vector2(0, -1),	new Vector2(0, 0),	new Vector2(1, 0)},	"PiezaRoja",		"BloqueRojo"),
	Z_INVERSA	(new Vector2[] {new Vector2(-1, -1),	new Vector2(0, -1),	new Vector2(0, 0),	new Vector2(1, 0)},	"PiezaVerde",		"BloqueVerde");

	public final Vector2[]	formaPieza;
	public final String		texturaPieza;
	public final String		texturaBloquePieza;

	ListaPiezas(Vector2[] formaPieza, String texturaPieza, String texturaBloquePieza) {
		this.formaPieza 		= formaPieza;
		this.texturaPieza 		= texturaPieza;
		this.texturaBloquePieza = texturaBloquePieza;
	}

}
