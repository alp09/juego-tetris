package proyectojuego.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public final class PantallaSplash extends Pantalla {


	// AL USAR EL DELTA, LA ANIMACION DURA 1s PUESTO QUE VA SUMANDO ms A progresoAnimacion HASTA LLEGAR AL TOPE (1).
	// SE PODRIA MODIFICAR MULTIPLICANDO progresoAnimacion, POR EJEMPLO, AL MULTIPLICARLO POR .5, TARDARÍA 2s EN LLEGAR AL TOPE DE 1.

	public static final float TIEMPO_MINIMO_ENTRE_ANIMACIONES	= 2;	// TIEMPO EN SEGUNDOS MINIMO ENTRE LAS ANIMACIONES
	public static final float MULTIPLICADOR_ANIMACION_FADE_IN	= 1;	// <1 --> ANIMACION MAS LENTA
	public static final float MULTIPLICADOR_ANIMACION_FADE_OUT	= 1;	//	1 --> VELOCIDAD NORMAL
																		// >1 --> ANIMACION MAS RAPIDA

	private float 	progresoAnimacionFadeIn 	= 0;					// LA ANIMACION FADE IN COMIENZA CON EL VALOR ALPHA A 0.
	private float 	progresoAnimacionFadeOut 	= 1;					// LA ANIMACION FADE OUT COMIENZA CON EL VALOR ALPHA A 1.
	private float 	tiempoEntreAnimaciones		= 0;					// DEFINE EL TIEMPO QUE HA TRANSCURRIDO DESDE QUE LA ANIMACION FADE IN TERMINÓ.

	private final	Sprite	spritePantallaSplash;
	private	final	Sound	sonidoSplash;


	// CONSTRUCTOR
	public PantallaSplash() {
		super();

		// CARGA LA IMAGEN MOSTRADA AL INICIO DEL JUEGO
		assetManager.load("ui/imagenIntro.png", Texture.class);
		assetManager.load("sounds/sonidoSplash.ogg", Sound.class);
		assetManager.finishLoading();

		// SE ASIGNA LA TEXTURA AL SPRITE Y SE ESTABLECE SU ALPHA A 0 PARA LA ANIMACION
		spritePantallaSplash = new Sprite(assetManager.get("ui/imagenIntro.png", Texture.class));
		spritePantallaSplash.setAlpha(0);

		// ASIGNA Y REPRODUCE LA MUSICA
		sonidoSplash = assetManager.get("sounds/sonidoSplash.ogg", Sound.class);
		sonidoSplash.play();
		sonidoSplash.setLooping(1,true);

		// AQUI VAN TODOS LOS ASSETS A CARGAR
		assetManager.load("ui/texturas.atlas", TextureAtlas.class);
		assetManager.load("uiskin.json", Skin.class);
		assetManager.load("sounds/sonidoPieza.ogg", Sound.class);
		assetManager.load("sounds/sonidoFilaCompleta.ogg", Sound.class);
		assetManager.load("sounds/sonidoGameOver.ogg", Sound.class);
		assetManager.load("sounds/musicaMenu.ogg", Music.class);
		assetManager.load("sounds/musicaJuego.ogg", Music.class);
		assetManager.load("sounds/musicaOpciones.ogg", Music.class);

	}


	// METODOS
	@Override
	public void show() {

	}

	@Override
	public void gestionarInput(float delta) {

		if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) && assetManager.update()) {
			sonidoSplash.stop();
			juego.setScreen(new PantallaMenu());
		}

	}

	@Override
	public void recalcularPantalla(float delta) {

		if (progresoAnimacionFadeIn < 1) {
			progresoAnimacionFadeIn += delta * MULTIPLICADOR_ANIMACION_FADE_IN;
			spritePantallaSplash.setAlpha(Math.min(1, progresoAnimacionFadeIn));
		} else if (tiempoEntreAnimaciones > TIEMPO_MINIMO_ENTRE_ANIMACIONES && assetManager.update()) {
			tiempoEntreAnimaciones += delta;
			progresoAnimacionFadeOut -= delta * MULTIPLICADOR_ANIMACION_FADE_OUT;
			spritePantallaSplash.setAlpha(Math.max(0, progresoAnimacionFadeOut));
			if (progresoAnimacionFadeOut < 0) juego.setScreen(new PantallaMenu());
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
		this.dispose();
	}

	@Override
	public void dispose() {
		spritePantallaSplash.getTexture().dispose();
		sonidoSplash.dispose();
	}
}
