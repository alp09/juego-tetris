package proyectojuego;

public enum ListaControles {

	CONTROLAR_MUSICA				("CONTROLAR_MUSICA", 41),
	PAUSAR							("PAUSAR", 44),
	REINICIAR						("REINICIAR", 46),
	GUARDAR_PIEZA					("GUARDAR_PIEZA", 51),
	MOVER_IZQUIERDA					("MOVER_IZQUIERDA", 21),
	MOVER_DERECHA					("MOVER_DERECHA", 22),
	BAJAR_PIEZA						("BAJAR_PIEZA", 20),
	COLOCAR_PIEZA					("COLOCAR_PIEZA", 62),
	ROTAR_SENTIDO_RELOJ				("ROTAR_SENTIDO_RELOJ", 33),
	ROTAR_SENTIDO_CONTRARIO_RELOJ	("ROTAR_SENTIDO_CONTRARIO_RELOJ", 45);

	private final String	nombreControl;
	private final int		valorDefecto;

	ListaControles(String nombrePreferencia, int valorDefecto) {
		this.nombreControl = nombrePreferencia;
		this.valorDefecto = valorDefecto;
	}

	public String getNombreControl() {
		return nombreControl;
	}
	public int getValorDefecto() {
		return valorDefecto;
	}

}
