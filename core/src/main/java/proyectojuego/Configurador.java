package proyectojuego;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;

public class Configurador {

	private final String ARCHIVO_PREFERENCIAS	= "assets\\files\\preferencias.json";
	private final String ARCHIVO_CONTROLES		= "assets\\files\\controles.json";

	private static Configurador configurador 	= null;

	public HashMap<String, Boolean> preferenciasUsuario = new HashMap<>();
	public HashMap<String, Integer> controlesUsuario	= new HashMap<>();

	// CONSTRUCTOR
	private Configurador() {

		boolean	seHizoCambiosPreferencias	= false;
		boolean	seHizoCambiosControles		= false;

		// RECOGE LAS PREFERENCIAS GUARDADAS
		try (BufferedReader lector = new BufferedReader(new FileReader(ARCHIVO_PREFERENCIAS))) {

			Gson lectorJson = new GsonBuilder().create();
			Type tipoObjeto = new TypeToken<HashMap<String, Boolean>>() {}.getType();
			preferenciasUsuario = lectorJson.fromJson(lector, tipoObjeto);

		} catch (JsonSyntaxException ex) {
			System.out.println("Error en la sintaxis del archivo " + ARCHIVO_PREFERENCIAS + " - Abortando...");
		} catch (FileNotFoundException ex) {
			System.out.println("No se encontró el archivo " + ARCHIVO_PREFERENCIAS + ".");
		} catch (IOException e) {
			System.out.println("Error en la lectura del archivo.");
		}

		// COMPRUEBA QUE LAS PREFERENCIAS SEAN CORRECTAS
		for (ListaPreferencias preferencia: ListaPreferencias.values()) {
			if (preferenciasUsuario.get(preferencia.nombrePreferencia) == null) {
					preferenciasUsuario.put(preferencia.nombrePreferencia, preferencia.valorDefecto);
					seHizoCambiosPreferencias = true;
			}
		}

		// RECOGE LOS CONTROLES GUARDADOS
		try (BufferedReader lector = new BufferedReader(new FileReader(ARCHIVO_CONTROLES))) {

			Gson lectorJson = new GsonBuilder().create();
			Type tipoObjeto = new TypeToken<HashMap<String, Integer>>() {}.getType();
			controlesUsuario = lectorJson.fromJson(lector, tipoObjeto);

		} catch (JsonSyntaxException ex) {
			System.out.println("Error en la sintaxis del archivo " + ARCHIVO_CONTROLES + " - Abortando...");
		}catch (FileNotFoundException ex) {
			System.out.println("No se encontró el archivo " + ARCHIVO_CONTROLES + ".");
		} catch (IOException e) {
			System.out.println("Error en la lectura del archivo.");
		}

		// COMPRUEBA QUE LAS PREFERENCIAS SEAN CORRECTAS
		for (ListaControles control: ListaControles.values()) {
			if (controlesUsuario.get(control.nombreControl) == null) {
				controlesUsuario.put(control.nombreControl, control.valorDefecto);
				seHizoCambiosControles = true;
			}
		}

		System.out.println(seHizoCambiosPreferencias);
		System.out.println(seHizoCambiosControles);

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
		try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARCHIVO_PREFERENCIAS))) {

			Gson escritorJson = new GsonBuilder()
				.setPrettyPrinting()
				.serializeNulls()
				.create();

			escritorJson.toJson(preferenciasUsuario, escritor);

		} catch (FileNotFoundException ex) {
			System.out.println("No se encontró el archivo " + ARCHIVO_PREFERENCIAS + ", creando uno nuevo...");
		} catch (IOException e) {
			System.out.println("Error en la escritura del archivo");
		}
	}

	public void guardarCambiosControles() {
		try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARCHIVO_CONTROLES))) {

			Gson escritorJson = new GsonBuilder()
				.setPrettyPrinting()
				.serializeNulls()
				.create();

			escritorJson.toJson(controlesUsuario, escritor);

		} catch (FileNotFoundException ex) {
			System.out.println("No se encontró el archivo " + ARCHIVO_CONTROLES + ", creando uno nuevo...");
		} catch (IOException e) {
			System.out.println("Error en la escritura del archivo");
		}
	}

	public HashMap<String, Boolean> copiarPrefenrecias(HashMap<String, Boolean> copiaPreferencias) {
		String preferenciasJson = new Gson().toJson(preferenciasUsuario);
		Type tipoObjeto = new TypeToken<HashMap<String, Boolean>>() {}.getType();
		copiaPreferencias = new Gson().fromJson(preferenciasJson, tipoObjeto);
		return copiaPreferencias;
	}

}
