package proyectojuego;

import com.badlogic.gdx.math.Vector2;

public enum ListaPiezas {

	PALO		(new Vector2[] {new Vector2(-2, 0),	new Vector2(-1, 0),	new Vector2(0, 0),	new Vector2(1, 0)},	"BloqueCian", 		"PiezaCian"),
	CUADRADO	(new Vector2[] {new Vector2(-1, 0),	new Vector2(0, 0),	new Vector2(-1, -1),	new Vector2(0, -1)},	"BloqueAmarillo",	"PiezaAmarilla"),
	T			(new Vector2[] {new Vector2(-1, 0),	new Vector2(0, 0),	new Vector2(1, 0),	new Vector2(0, -1)},	"BloqueMorado",		"PiezaMorada"),
	L			(new Vector2[] {new Vector2(-1, -1),	new Vector2(-1, 0),	new Vector2(0, 0),	new Vector2(1, 0)},	"BloqueAzul", 		"PiezaAzul"),
	L_INVERSA	(new Vector2[] {new Vector2(-1, 0),	new Vector2(0, 0),	new Vector2(1, 0),	new Vector2(1, -1)},	"BloqueNaranja", 	"PiezaNaranja"),
	Z			(new Vector2[] {new Vector2(-1, 0),	new Vector2(0, -1),	new Vector2(0, 0),	new Vector2(1, 0)},	"BloqueRojo",		"PiezaRoja"),
	Z_INVERSA	(new Vector2[] {new Vector2(-1, -1),	new Vector2(0, -1),	new Vector2(0, 0),	new Vector2(1, 0)},	"BloqueVerde",		"PiezaVerde");

	public final Vector2[]	formaPieza;
	public final String		texturaBloquePieza;
	public final String		texturaPieza;

	ListaPiezas(Vector2[] formaPieza, String texturaBloquePieza, String texturaPieza) {
		this.formaPieza = formaPieza;
		this.texturaBloquePieza = texturaBloquePieza;
		this.texturaPieza = texturaPieza;
	}

}
