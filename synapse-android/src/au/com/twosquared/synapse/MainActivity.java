package au.com.twosquared.synapse;

import android.os.Bundle;
import au.com.twosquared.synapse.game.Synapse;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = true;
		//cfg.numSamples = 2;

		initialize(new Synapse(), cfg);
	}
}