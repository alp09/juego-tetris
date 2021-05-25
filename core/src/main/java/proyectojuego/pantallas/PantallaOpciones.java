package proyectojuego.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class PantallaOpciones extends Pantalla {

	private Music musica;

	public PantallaOpciones(){
		super();

		//CARGAMOS LA MUSICA Y LA INICIAMOS
		musica = Gdx.audio.newMusic(Gdx.files.internal("sounds/musicaOpciones.ogg"));
		musica.setVolume(0.25f);
		musica.setLooping(true);
		musica.play();

	}


	@Override
	public void show() {

	}

	@Override
	public void gestionarInput(float delta) {

	}

	@Override
	public void recalcularPantalla(float delta) {

	}

	@Override
	public void dibujarPantalla(float delta) {

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
		musica.dispose();
	}
}
