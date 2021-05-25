package proyectojuego;

public enum ListaPreferencias {

	HABILITAR_MUSICA				("HABILITAR_MUSICA", true),
	HABILITAR_EFECTOS_SONIDO		("HABILITAR_EFECTOS_SONIDO", true);


	private final String	nombrePreferencia;
	private final boolean	valorDefecto;

	ListaPreferencias(String nombrePreferencia, boolean valorDefecto) {
		this.nombrePreferencia = nombrePreferencia;
		this.valorDefecto = valorDefecto;
	}

	public String getNombrePreferencia() {
		return nombrePreferencia;
	}
	public boolean getValorDefecto() {
		return valorDefecto;
	}

}
