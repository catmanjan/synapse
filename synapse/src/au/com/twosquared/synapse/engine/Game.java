package au.com.twosquared.synapse.engine;

import au.com.twosquared.synapse.engine.entity.AnimatedInstance;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.utils.Array;

public class Game implements ApplicationListener {

	public float width;
	public float height;
	public float aspectRatio;

	public boolean loading;
	public boolean touchpadVisible;

	public AssetManager assets;
	public ModelBatch modelBatch;
	public SpriteBatch spriteBatch;

	public PerspectiveCamera camera;
	public Environment environment;
	public Array<ModelInstance> instances;

	public OrthographicCamera camera2d;
	public Stage stage;
	public Touchpad touchpad;

	public void init() {

	}

	public void loaded() {

	}

	public void update(float delta) {

	}

	public void draw() {

	}

	protected void loadModel(String path) {
		assets.load(path, Model.class);
		loading = true;
	}

	@Override
	public void create() {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		aspectRatio = width / height;

		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f,
				0.5f, 0.5f, 1f));
		environment.add(new DirectionalLight().set(1, 1, 1, -1f, -1f, -1f));
		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		camera.near = 0.1f;
		camera.far = 100f;
		assets = new AssetManager();
		instances = new Array<ModelInstance>();

		spriteBatch = new SpriteBatch();
		camera2d = new OrthographicCamera();
		camera2d.setToOrtho(false, 1 * aspectRatio, 1);

		Skin skin = new Skin();
		skin.add("touchBackground", new Texture("data/ui/touchBackground.png"));
		skin.add("touchKnob", new Texture("data/ui/touchKnob.png"));

		TouchpadStyle touchpadStyle = new TouchpadStyle();
		touchpadStyle.background = skin.getDrawable("touchBackground");
		touchpadStyle.knob = skin.getDrawable("touchKnob");

		touchpad = new Touchpad(10, touchpadStyle);
		touchpad.setSize(160, 160);

		stage = new Stage(600 * aspectRatio, 600, true, spriteBatch);
		stage.addActor(touchpad);

		Gdx.input.setInputProcessor(stage);

		init();
	}

	@Override
	public void render() {
		if (loading && assets.update()) {
			loaded();
			loading = false;
		}

		if (Gdx.input.justTouched()) {
			float xOffset = touchpad.getWidth() / 2;
			float yOffset = -touchpad.getHeight() / 2;

			Vector2 screenPosition = new Vector2();
			screenPosition.set(Gdx.input.getX() - xOffset, Gdx.input.getY()
					- yOffset);

			Vector2 localPosition = new Vector2();
			localPosition.set(screenPosition);
			localPosition = touchpad.getParent().screenToLocalCoordinates(
					localPosition);

			touchpad.setVisible(true);
			touchpad.setPosition(localPosition.x, localPosition.y);
			touchpadVisible = true;

			// Trigger a fake touch event on touch pad
			Vector2 stagePosition = touchpad.getStage()
					.screenToStageCoordinates(screenPosition);

			InputEvent propagate = new InputEvent();
			propagate.setType(Type.touchDown);
			propagate.setStageX(stagePosition.x);
			propagate.setStageY(stagePosition.y);
			touchpad.fire(propagate);
		}

		if (!Gdx.input.isTouched()) {
			touchpad.setVisible(false);
			touchpadVisible = false;
		}

		for (ModelInstance instance : instances) {
			if (instance instanceof AnimatedInstance) {
				((AnimatedInstance) instance).update(Gdx.graphics
						.getDeltaTime());
			}
		}

		if (!loading) {
			update(Gdx.graphics.getDeltaTime());
		}

		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glViewport(0, 0, (int) width, (int) height);
		Gdx.gl.glDisable(GL20.GL_CULL_FACE);
		Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		camera.update();
		modelBatch.begin(camera);
		modelBatch.render(instances, environment);
		modelBatch.end();

		draw();

		camera2d.update();
		spriteBatch.begin();
		spriteBatch.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void dispose() {
		modelBatch.dispose();
		instances.clear();
		assets.dispose();
	}

	@Override
	public void resume() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

}
