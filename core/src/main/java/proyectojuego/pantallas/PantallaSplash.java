package proyectojuego.pantallas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class PantallaSplash extends Pantalla {

	/*
		AL USAR EL DELTA, LA ANIMACION DURA 1s PUESTO QUE VA SUMANDO ms A progresoAnimacion HASTA LLEGAR AL TOPE (1).
		SE PODRIA MODIFICAR MULTIPLICANDO progresoAnimacion, POR EJEMPLO, AL MULTIPLICARLO POR .5, TARDARÍA 2s EN LLEGAR AL TOPE DE 1.
	 */
	public static final int TIEMPO_MINIMO_ENTRE_ANIMACIONES		= 0;	// TIEMPO EN SEGUNDOS ENTRE LAS ANIMACIONES
	public static final int MULTIPLICADOR_ANIMACION_FADE_IN		= 4;	// <1 --> ANIMACION MAS LENTA
	public static final int MULTIPLICADOR_ANIMACION_FADE_OUT	= 4;	//	1 --> VELOCIDAD NORMAL
																		// >1 --> ANIMACION MAS RAPIDA

	private float	progresoAnimacionFadeIn 	= 0;					// LA ANIMACION COMIENZA CON EL VALOR ALPHA A 0.
	private float	progresoAnimacionFadeOut 	= 1;					// LA ANIMACION COMIENZA CON EL VALOR ALPHA A 1.
	private float	tiempoEntreAnimaciones		= 0;					// DEFINE EL TIEMPO QUE HA TRANSCURRIDO DESDE QUE LA ANIMACION FADE IN TERMINÓ.

	private final Sprite	spritePantallaSplash;


// CONSTRUCTOR
	public PantallaSplash() {

		assetManager.load("ui/imagenIntro.png", Texture.class);
		assetManager.finishLoading();

		spritePantallaSplash = new Sprite(assetManager.get("ui/imagenIntro.png", Texture.class));
		spritePantallaSplash.setAlpha(0);

		assetManager.load("ui/texturas.atlas", TextureAtlas.class);

	}


// METODOS
	@Override
	public void gestionarInput(float delta) {

	}

	@Override
	public void recalcularPantalla(float delta) {

		if (progresoAnimacionFadeIn < 1) {
			progresoAnimacionFadeIn += delta * MULTIPLICADOR_ANIMACION_FADE_IN;
			spritePantallaSplash.setAlpha(Math.min(1, progresoAnimacionFadeIn));
		} else if (assetManager.update() && tiempoEntreAnimaciones > TIEMPO_MINIMO_ENTRE_ANIMACIONES) {
			tiempoEntreAnimaciones += delta;
			progresoAnimacionFadeOut -= delta * MULTIPLICADOR_ANIMACION_FADE_OUT;
			spritePantallaSplash.setAlpha(Math.max(0, progresoAnimacionFadeOut));
			if (progresoAnimacionFadeOut < 0) juego.cambiarPantalla(this, new PantallaMenu());
		} else {
			tiempoEntreAnimaciones += delta;
		}

	}

	@Override
	public void dibujarPantalla(float delta) {
		spriteBatch.begin();
		spritePantallaSplash.draw(spriteBatch);
		spriteBatch.end();
	}

	@Override
	public void show() {

	}

	@Override
	public void resize(int width, int height) {

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
}
