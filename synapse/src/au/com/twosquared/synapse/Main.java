package au.com.twosquared.synapse;

import au.com.twosquared.synapse.game.Synapse;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Synapse";
		cfg.useGL20 = true;
		cfg.width = 960;
		cfg.height = 600;
		cfg.samples = 4;
		
		new LwjglApplication(new Synapse(), cfg);
	}
}
