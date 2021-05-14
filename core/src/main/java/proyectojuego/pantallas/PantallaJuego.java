package proyectojuego.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import proyectojuego.Juego;
import proyectojuego.jugabilidad.Pieza;


public class PantallaJuego extends Pantalla {

	public static final int ESCALA_PIEZA_UI = 96;

	private TextureAtlas	textureAtlas;

	private final Sprite	spriteFondoJuego;
	private final Sprite	spriteFondoPiezaGuardada;
	private final Sprite	spriteFondoPrimeraPieza;
	private final Sprite	spriteFondoSegundaPieza;
	private final Sprite	spriteFondoTerceraPieza;
	private final Sprite	spriteGridZonaJuego;

	private Pieza			piezaJugable;
	private Pieza			piezaGuardada;
	private Pieza 			primeraPieza;
	private Pieza 			segundaPieza;
	private Pieza 			terceraPieza;

	private Vector2			posicionPiezaJugable;			// Posicion actual de la pieza
	private Vector2			posicionInicioPiezaJugable;		// Posicion desde donde la pieza jugable aparece al comienzo
	private Vector2			posicionPiezaGuardada;			// Posicion en la UI de la pieza guardada
	private Vector2			posicionPrimeraPieza;			// Posicion en la UI de la primera pieza de la lista
	private Vector2			posicionSegundaPieza;			// Posicion en la UI de la segunda pieza de la lista
	private Vector2			posicionTerceraPieza;			// Posicion en la UI de la tercera pieza de la lista


// CONSTRUCTOR
	public PantallaJuego() {
		super();

		textureAtlas = assetManager.get("ui/texturas.atlas");

		spriteFondoJuego 			= new Sprite(textureAtlas.findRegion("FondoJuego"));
		spriteGridZonaJuego			= new Sprite(textureAtlas.findRegion("Grid"));
		spriteFondoPiezaGuardada	= new Sprite(textureAtlas.findRegion("FondoPieza"));
		spriteFondoPrimeraPieza 	= new Sprite(textureAtlas.findRegion("FondoPieza"));
		spriteFondoSegundaPieza 	= new Sprite(textureAtlas.findRegion("FondoPieza"));
		spriteFondoTerceraPieza		= new Sprite(textureAtlas.findRegion("FondoPieza"));

		spriteFondoJuego.setPosition(Juego.ANCHO_JUEGO * .5f - spriteFondoJuego.getWidth() * .5f, Juego.ALTO_JUEGO * .5f - spriteFondoJuego.getHeight() * .5f);
		spriteGridZonaJuego.setPosition(spriteFondoJuego.getX(), spriteFondoJuego.getY());
		spriteFondoPiezaGuardada.setPosition(spriteFondoJuego.getX() - 20 - spriteFondoPiezaGuardada.getWidth(), spriteFondoJuego.getY() + spriteFondoJuego.getHeight() - spriteFondoPiezaGuardada.getHeight());
		spriteFondoPrimeraPieza.setPosition(spriteFondoJuego.getX() + spriteFondoJuego.getWidth() + 20, spriteFondoJuego.getY() + spriteFondoJuego.getHeight() - spriteFondoPrimeraPieza.getHeight());
		spriteFondoSegundaPieza.setPosition(spriteFondoJuego.getX() + spriteFondoJuego.getWidth() + 20, spriteFondoJuego.getY() + spriteFondoJuego.getHeight() - spriteFondoSegundaPieza.getHeight() * 2);
		spriteFondoTerceraPieza.setPosition(spriteFondoJuego.getX() + spriteFondoJuego.getWidth() + 20, spriteFondoJuego.getY() + spriteFondoJuego.getHeight() - spriteFondoTerceraPieza.getHeight() * 3);

		piezaJugable				= new Pieza();
		piezaGuardada				= null;
		primeraPieza 				= new Pieza();
		segundaPieza 				= new Pieza();
		terceraPieza 				= new Pieza();

		posicionInicioPiezaJugable	= new Vector2(1,1); // Centro del tablero, arriba del tablero
		posicionPiezaGuardada		= new Vector2(spriteFondoPiezaGuardada.getX() + spriteFondoPiezaGuardada.getWidth() * .5f - ESCALA_PIEZA_UI * .5f, spriteFondoPiezaGuardada.getY() + spriteFondoPiezaGuardada.getWidth() * .5f - ESCALA_PIEZA_UI * .5f);
		posicionPrimeraPieza		= new Vector2(spriteFondoPrimeraPieza.getX() + spriteFondoPrimeraPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f, spriteFondoPrimeraPieza.getY() + spriteFondoPrimeraPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f);
		posicionSegundaPieza		= new Vector2(spriteFondoSegundaPieza.getX() + spriteFondoSegundaPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f, spriteFondoSegundaPieza.getY() + spriteFondoSegundaPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f);
		posicionTerceraPieza		= new Vector2(spriteFondoTerceraPieza.getX() + spriteFondoTerceraPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f, spriteFondoTerceraPieza.getY() + spriteFondoTerceraPieza.getWidth() * .5f - ESCALA_PIEZA_UI * .5f);

	}


// METODOS
	@Override
	public void show() {

	}

	@Override
	public void gestionarInput(float delta) {

		if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {

			Pieza piezaAuxiliar;

			if (piezaGuardada == null) {
				this.piezaGuardada	= this.piezaJugable;
				jugarSiguientePieza();
			} else {
				piezaAuxiliar		= this.piezaGuardada;
				this.piezaGuardada	= this.piezaJugable;
				this.piezaJugable	= piezaAuxiliar;
			}

		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			posicionInicioPiezaJugable.add(new Vector2(-1,0));
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			posicionInicioPiezaJugable.add(new Vector2(1,0));
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			posicionInicioPiezaJugable.add(new Vector2(0,1));
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
			piezaJugable.rotarReloj();
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
			piezaJugable.rotarContraReloj();
		}

	}

	@Override
	public void recalcularPantalla(float delta) {
		// ToDo: calcular el movimento de las piezas, actualizar puntuación, etc
	}

	@Override
	public void dibujarPantalla(float delta) {
		spriteBatch.begin();

		// DIBUJA EL TABLERO DE JUEGO Y EL FONDO DEL TABLERO
		spriteFondoJuego.draw(spriteBatch);
		spriteGridZonaJuego.draw(spriteBatch);

		// DIBUJA EL FONDO DE LA PIEZA GUARDADA Y LA PIEZA EN SÍ CAMBIAR piezaPrueba POR LA PIEZA QUE ESTE GUARDADA
		spriteFondoPiezaGuardada.draw(spriteBatch);
		if (piezaGuardada != null) spriteBatch.draw(piezaGuardada.spritePieza, posicionPiezaGuardada.x, posicionPiezaGuardada.y, ESCALA_PIEZA_UI, ESCALA_PIEZA_UI);

		// DIBUJA EL FONDO DE LAS PIEZAS SIGUIENTES Y LA PIEZA EN SÍ SUSTITUIR LAS PIEZAS POR PIEZAS GENERADAS ALEATORIAMENTE
		spriteFondoPrimeraPieza.draw(spriteBatch);
		spriteFondoSegundaPieza.draw(spriteBatch);
		spriteFondoTerceraPieza.draw(spriteBatch);
		spriteBatch.draw(primeraPieza.spritePieza, posicionPrimeraPieza.x, posicionPrimeraPieza.y, ESCALA_PIEZA_UI, ESCALA_PIEZA_UI);
		spriteBatch.draw(segundaPieza.spritePieza, posicionSegundaPieza.x, posicionSegundaPieza.y, ESCALA_PIEZA_UI, ESCALA_PIEZA_UI);
		spriteBatch.draw(terceraPieza.spritePieza, posicionTerceraPieza.x, posicionTerceraPieza.y, ESCALA_PIEZA_UI, ESCALA_PIEZA_UI);

		// ToDo: Dibujar la pieza que esta moviendo el jugador y las que estaban en el tablero
		for(Vector2 vectores:piezaJugable.tipoPieza.formaPieza){
			System.out.println(vectores);
		}

		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		fitViewport.update(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

	private void jugarSiguientePieza() {
		this.piezaJugable = this.primeraPieza;
		this.primeraPieza = this.segundaPieza;
		this.segundaPieza = this.terceraPieza;
		this.terceraPieza = new Pieza();
	}

}
