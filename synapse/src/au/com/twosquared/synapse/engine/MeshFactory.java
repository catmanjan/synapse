package au.com.twosquared.synapse.engine;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class MeshFactory {

	public static Mesh makeTextureMesh() {
		Mesh mesh = new Mesh(true, 4, 4, new VertexAttribute(Usage.Position, 3,
				ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(
				Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE));

		mesh.setVertices(new float[] { -10, 0, 10, 10, 0, 10, 10, 0, -10, -10,
				0, -10 });
		mesh.setIndices(new short[] { 3, 2, 1, 0 });

		return mesh;
	}

	public static Mesh makeMesh() {
		Mesh mesh = new Mesh(true, 4, 4, new VertexAttribute(Usage.Position, 3,
				ShaderProgram.POSITION_ATTRIBUTE));
		mesh.setVertices(new float[] { -10, -1, 10, 10, -1, 10, 10, -1, -10,
				-10, -1, -10 });
		mesh.setIndices(new short[] { 3, 2, 1, 0 });

		return mesh;
	}

}
