package proyectojuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Configurador {

	private static Configurador configurador 		= null;

	public Preferences 			preferenciasUsuario	= Gdx.app.getPreferences("preferenciasUsuario.xml");
	public Preferences 			controlesUsuario	= Gdx.app.getPreferences("controlesUsuario.xml");


	// CONSTRUCTOR
	private Configurador() {

		boolean	seHizoCambiosPreferencias	= false;
		boolean	seHizoCambiosControles		= false;

		// COMPRUEBA CADA CONTROL
		for (ListaPreferencias preferencias: ListaPreferencias.values()) {
			// SI NO CONTIENE UNA ENTRADA SOBRE ESA PREFERENCIA O ESTA VACIA LA CREA
			if (!preferenciasUsuario.contains(preferencias.nombrePreferencia)) {
				preferenciasUsuario.putBoolean(preferencias.nombrePreferencia, preferencias.valorDefecto);
				seHizoCambiosPreferencias = true;
			}
		}

		// COMPRUEBA CADA CONTROL
		for (ListaControles control: ListaControles.values()) {
			// SI NO CONTIENE UNA ENTRADA SOBRE ESA PREFERENCIA O ESTA VACIA LA CREA
			if (!controlesUsuario.contains(control.nombreControl)) {
				controlesUsuario.putInteger(control.nombreControl, control.valorDefecto);
				seHizoCambiosControles = true;
			}
		}

		if (seHizoCambiosPreferencias) guardarCambiosPreferencias();
		if (seHizoCambiosControles) guardarCambiosControles();
	}


	// GET INSTANCE - PATRON SINGLETON
	public static Configurador getInstance() {
		if (configurador == null) configurador = new Configurador();
		return configurador;
	}


	// METODOS
	public void guardarCambiosPreferencias() {
		preferenciasUsuario.flush();
	}
	public void guardarCambiosControles() {
		controlesUsuario.flush();
	}

}
