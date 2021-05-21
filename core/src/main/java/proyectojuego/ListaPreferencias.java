package proyectojuego;

public enum ListaPreferencias {

	HABILITAR_MUSICA				("HABILITAR_MUSICA", true),
	HABILITAR_EFECTOS_SONIDO		("HABILITAR_EFECTOS_SONIDO", true),
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

	private final String nombrePreferencia;
	private final Object valorDefecto;

	ListaPreferencias(String nombrePreferencia, Object valorDefecto) {
		this.nombrePreferencia = nombrePreferencia;
		this.valorDefecto = valorDefecto;
	}

	public String getNombrePreferencia() {
		return nombrePreferencia;
	}
	public Object getValorDefecto() {
		return valorDefecto;
	}

}
