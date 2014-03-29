package au.com.twosquared.synapse.engine.entity;

import com.badlogic.gdx.graphics.g3d.Model;

public class TopDownEntity extends AnimatedInstance {

	public float x;
	public float z;
	public float rotation;
	public float rotationOffset;
	public float speed;
	public float radius = 1f;

	public TopDownEntity(Model model) {
		super(model);
	}

	public void scale(float scale) {
		transform.scale(scale, scale, scale);
	}

	public void rotate(float dd) {
		rotation += dd;

		transform.rotate(0, 1, 0, dd);
	}

	public void setRotation(float degrees) {
		float dd = degrees - rotation;

		transform.rotate(0, 1, 0, dd);

		rotation = degrees;
	}

	public void translate(float dx, float dz) {
		x += dx;
		z += dz;

		transform.trn(dx, 0, dz);
	}

	public void setTranslation(float x, float z) {
		float dx = x - this.x;
		float dz = z - this.z;

		transform.trn(dx, 0, dz);

		this.x = x;
		this.z = z;
	}

	public void walk(float delta) {
		float radians = (rotation + rotationOffset) * (float) (Math.PI / 180);
		float dx = (float) Math.sin(radians) * speed * delta;
		float dz = (float) Math.cos(radians) * speed * delta;

		translate(dx, dz);
	}

	public boolean collides(TopDownEntity other) {
		float dx = x - other.x;
		float dz = z - other.z;
		float dr = radius * radius + other.radius * radius;
		float dist = dx * dx + dz * dz;

		return dist < dr;
	}

}
