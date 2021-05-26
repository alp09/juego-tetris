package proyectojuego;

import com.badlogic.gdx.Input;

public enum ListaControles {

	CONTROLAR_MUSICA				("Controlar musica", Input.Keys.M),
	PAUSAR							("Pausar", Input.Keys.P),
	REINICIAR						("Reiniciar", Input.Keys.R),
	GUARDAR_PIEZA					("Guardar pieza", Input.Keys.W),
	MOVER_IZQUIERDA					("Mover izquierda", Input.Keys.LEFT),
	MOVER_DERECHA					("Mover derecha", Input.Keys.RIGHT),
	BAJAR_PIEZA						("Bajar pieza", Input.Keys.DOWN),
	COLOCAR_PIEZA					("Colocar pieza", Input.Keys.SPACE),
	ROTAR_SENTIDO_RELOJ				("Rotar sentido reloj", Input.Keys.E),
	ROTAR_SENTIDO_CONTRARIO_RELOJ	("Rotar Contrareloj", Input.Keys.Q);

	public final String	nombreControl;
	public final int		valorDefecto;


// CONSTRUCTOR
	ListaControles(String nombrePreferencia, int valorDefecto) {
		this.nombreControl		= nombrePreferencia;
		this.valorDefecto		= valorDefecto;
	}

}
