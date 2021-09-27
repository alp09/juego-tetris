package proyectojuego.ajustes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;


public class Configurador {

	private final String ARCHIVO_PREFERENCIAS = "assets\\files\\preferencias.json";
	private final String ARCHIVO_CONTROLES = "assets\\files\\controles.json";

	private static Configurador		configurador	= null;

	public LinkedHashMap<String, Boolean> preferenciasUsuario;
	public LinkedHashMap<String, Integer> controlesUsuario;


	// CONSTRUCTOR
	private Configurador() {

		// RECOGE LAS PREFERENCIAS GUARDADAS
		preferenciasUsuario = recogerPreferencias();

		// COMPRUEBA QUE LAS PREFERENCIAS EXISTAN Y TENGAN UN VALOR ASIGNADO
		if (comprobarPreferencias(preferenciasUsuario)) guardarPreferencias();

		// RECOGE LOS CONTROLES GUARDADOS
		controlesUsuario = recogerControles();

		// COMPRUEBA QUE LOS CONTROLES EXISTAN Y TENGAN UN VALOR ASIGNADO
		if (comprobarControles(controlesUsuario)) guardarControles();

	}


	// GET INSTANCE - PATRON SINGLETON
	public static Configurador getInstance() {
		if (configurador == null) configurador = new Configurador();
		return configurador;
	}


	// METODOS
	@SuppressWarnings("unchecked")
	private LinkedHashMap<String, Boolean> recogerPreferencias() {
		return (LinkedHashMap<String, Boolean>) recogerAjustes(ARCHIVO_PREFERENCIAS, preferenciasUsuario);
	}

	@SuppressWarnings("unchecked")
	private LinkedHashMap<String, Integer> recogerControles() {
		return (LinkedHashMap<String, Integer>) recogerAjustes(ARCHIVO_CONTROLES, controlesUsuario);
	}

	public LinkedHashMap<String, ?> recogerAjustes(String archivo, LinkedHashMap<String, ?> ajustesUsuario) {
		try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {

			Gson lectorJson = new GsonBuilder().create();
			Type tipoObjeto = new TypeToken<LinkedHashMap<String, Boolean>>() {}.getType();
			ajustesUsuario = lectorJson.fromJson(lector, tipoObjeto);

		} catch (JsonSyntaxException ex) {
			System.out.println("Error en la sintaxis del archivo " + archivo + " - Abortando...");
		} catch (FileNotFoundException ex) {
			System.out.println("No se encontró el archivo " + archivo + ".");
		} catch (IOException ex) {
			System.out.println("Error en la lectura del archivo.");
		}

		return ajustesUsuario;
	}

	// COMPRUEBA QUE CADA ELEMENTO DE LA CLASE ENUM CORRESPONDIENTE ESTE PRESENTE, SINO LO RESTABLECE POR DEFECTO
	private boolean comprobarPreferencias(LinkedHashMap<String, Boolean> preferenciasUsuario) {
		boolean seRealizoCambios = false;

		for (ListaPreferencias preferencia: ListaPreferencias.values()) {
			if (preferenciasUsuario.get(preferencia.nombrePreferencia) == null) {
				preferenciasUsuario.put(preferencia.nombrePreferencia, preferencia.valorDefecto);
				seRealizoCambios = true;
			}
		}

		return seRealizoCambios;
	}

	private boolean comprobarControles(LinkedHashMap<String, Integer> ajustesUsuario) {
		boolean seRealizoCambios = false;

		for (ListaControles control: ListaControles.values()) {
			if (ajustesUsuario.get(control.nombreControl) == null) {
				ajustesUsuario.put(control.nombreControl, control.valorDefecto);
				seRealizoCambios = true;
			}
		}

		return seRealizoCambios;
	}

	// GUARDA EL CONTENIDO DEL LINKEDHASHMAP EN EL ARCHIVO QUE CORRESPONDA
	public void guardarPreferencias() {
		guardarAjustes(ARCHIVO_PREFERENCIAS, preferenciasUsuario);
	}

	public void guardarControles() {
		guardarAjustes(ARCHIVO_CONTROLES, controlesUsuario);
	}

	private void guardarAjustes(String archivo, LinkedHashMap<String, ?> ajustesUsuario) {
		try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo))) {

			Gson escritorJson = new GsonBuilder()
				.setPrettyPrinting()
				.serializeNulls()
				.create();

			escritorJson.toJson(ajustesUsuario, escritor);

		} catch (FileNotFoundException ex) {
			System.out.println("No se encontró el archivo " + archivo + ", creando uno nuevo...");
		} catch (IOException ex) {
			System.out.println("Error en la escritura del archivo");
		}
	}

	// GENERA UNA COPIA DE LOS AJUSTES ACTUALES
	@SuppressWarnings("unchecked")
	public LinkedHashMap<String, Boolean> copiarPreferencias() {
		return (LinkedHashMap<String, Boolean>) copiarAjuste(preferenciasUsuario);
	}

	@SuppressWarnings("unchecked")
	public LinkedHashMap<String, Integer> copiarControles() {
		return (LinkedHashMap<String, Integer>) copiarAjuste(controlesUsuario);
	}

	private LinkedHashMap<String, ?> copiarAjuste(LinkedHashMap<String, ?> ajustesUsuario) {
		String preferenciasJson = new Gson().toJson(ajustesUsuario);
		Type tipoObjeto 		= new TypeToken<LinkedHashMap<String, Integer>>() {}.getType();
		return new Gson().fromJson(preferenciasJson, tipoObjeto);
	}

}