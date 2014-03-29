package au.com.twosquared.synapse.engine.entity;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

public class AnimatedInstance extends ModelInstance {

	AnimationController animationController;

	boolean paused = false;

	public AnimatedInstance(Model model) {
		super(model);

		animationController = new AnimationController(this);
	}

	public void update(float delta) {
		animationController.update(delta);
	}

	public void setAnimation(int id) {
		animationController.setAnimation(animations.get(id).id, -1);
	}

	public void play() {
		if (animationController.current != null) {
			String id = animationController.current.animation.id;

			animationController.setAnimation(id, -1);
		}

		paused = false;
	}

	public void pause() {
		if (animationController.current != null) {
			String id = animationController.current.animation.id;

			animationController.setAnimation(id, -1, 0, null);
		}

		paused = true;
	}

}
