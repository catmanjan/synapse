package au.com.twosquared.synapse.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ShaderFactory {

	public static ShaderProgram getShader(String name) {
		String vertPath = "data/shaders/" + name + "-vert.glsl";
		String fragPath = "data/shaders/" + name + "-frag.glsl";

		String vert = Gdx.files.internal(vertPath).readString();
		String frag = Gdx.files.internal(fragPath).readString();

		return new ShaderProgram(vert, frag);
	}

}
