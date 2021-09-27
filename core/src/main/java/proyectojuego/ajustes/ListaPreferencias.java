package proyectojuego.ajustes;


public enum ListaPreferencias {

	HABILITAR_MUSICA			("Habilitar musica",				true),
	HABILITAR_EFECTOS_SONIDO	("Habilitar efectos de sonido",	true);

	public final String nombrePreferencia;
	public final boolean	valorDefecto;


	// CONSTRUCTOR
	ListaPreferencias(String nombrePreferencia, boolean valorDefecto) {
		this.nombrePreferencia	= nombrePreferencia;
		this.valorDefecto		= valorDefecto;
	}

}
