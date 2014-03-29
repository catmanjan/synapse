package au.com.twosquared.synapse.game;

import java.util.ArrayList;
import java.util.List;

import au.com.twosquared.synapse.engine.Game;
import au.com.twosquared.synapse.engine.entity.TopDownEntity;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.g3d.Model;

public class Synapse extends Game implements ApplicationListener {

	TopDownEntity player;

	List<TopDownEntity> enemies;
	List<TopDownEntity> npcs;

	@Override
	public void init() {
		camera.position.set(0f, 20f, 15f);
		camera.lookAt(0, 0, 0);
		camera.update();

		loadModel("data/soldier.g3db");
		loadModel("data/spider.g3db");
		loadModel("data/city.g3db");
	}

	@Override
	public void loaded() {
		npcs = new ArrayList<TopDownEntity>();
		enemies = new ArrayList<TopDownEntity>();

		player = new TopDownEntity(assets.get("data/soldier.g3db", Model.class));
		player.speed = 10f;
		player.rotationOffset = 180;
		player.setAnimation(1);
		instances.add(player);

		for (int i = 0; i < 2; i++) {
			TopDownEntity npc = new TopDownEntity(assets.get(
					"data/soldier.g3db", Model.class));
			npc.speed = 10f;
			npc.rotationOffset = 180;
			npc.setAnimation(1);
			npc.setTranslation(-i * 5f + 2.5f, -4f);

			npcs.add(npc);
			instances.add(npc);
		}

		for (int i = 0; i < 3; i++) {
			float x = (float) (Math.random() * 40) - 20;
			float z = (float) (Math.random() * 40) - 20;
			float r = (float) (Math.random() * 360);

			TopDownEntity enemy = new TopDownEntity(assets.get(
					"data/spider.g3db", Model.class));
			enemy.rotationOffset = 90;
			enemy.radius = 2f;
			enemy.speed = 3f;
			enemy.setAnimation(1);
			enemy.setTranslation(x, z);
			enemy.rotate(r);

			enemies.add(enemy);
			instances.add(enemy);
		}
		
		TopDownEntity city = new TopDownEntity(assets.get("data/city.g3db", Model.class));
		city.transform.translate(0, 5, 0);
		instances.add(city);
	}

	@Override
	public void update(float delta) {
		for (TopDownEntity enemy : enemies) {
			enemy.rotate(0.3f);
			enemy.walk(delta);

			if (enemy.x < -50) {
				enemy.setTranslation(50, enemy.z);
			} else if (enemy.x > 50) {
				enemy.setTranslation(-50, enemy.z);
			}

			if (enemy.z < -50) {
				enemy.setTranslation(enemy.x, 50);
			} else if (enemy.z > 50) {
				enemy.setTranslation(enemy.x, -50);
			}
		}

		float tdx = touchpad.getKnobPercentX();
		float tdy = touchpad.getKnobPercentY();

		if (touchpad.isTouched() && tdx != 0f && tdy != 0f) {
			float angle = (float) Math.toDegrees(Math.atan2(tdy, tdx)) - 90;

			player.setAnimation(1);
			player.play();
			player.setRotation(angle);
			player.walk(delta);
		} else {
			player.pause();
		}

		camera.position.set(player.x, 20f, player.z + 15f);

		TopDownEntity last = player;

		for (TopDownEntity npc : npcs) {
			float dx = last.x - npc.x;
			float dz = last.z - npc.z;
			float d = (float) Math.sqrt(dx * dx + dz * dz);
			float angle = (float) Math.toDegrees(Math.atan2(-dz, dx)) - 90;

			if (d > 5f) {
				npc.setAnimation(1);
				npc.play();
				npc.setRotation(angle);
				npc.walk(delta);
			} else {
				npc.pause();
			}

			last = npc;
		}
	}

	@Override
	public void draw() {

	}

}
