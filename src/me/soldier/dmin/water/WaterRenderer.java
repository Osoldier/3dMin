package me.soldier.dmin.water;

import java.util.*;

import me.soldier.dmin.core.*;
import me.soldier.dmin.math.*;
import me.soldier.dmin.models.*;

import org.lwjgl.opengl.*;

public class WaterRenderer {

	private Model quad;
	private WaterShader shader;

	public WaterRenderer(WaterShader shader, ProjectionMatrix projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.pr_matrix = projectionMatrix;
		shader.stop();
		setUpVAO();
	}

	public void render(List<WaterTile> water, Camera camera) {
		prepareRender(camera);
		for (WaterTile tile : water) {
			shader.ml_matrix = new ModelMatrix();
			shader.ml_matrix.Transform(new Vector3f(tile.getX(), tile.getHeight(), tile.getZ()), 0, new Vector3f(), new Vector3f(WaterTile.TILE_SIZE, 1, WaterTile.TILE_SIZE));
			shader.loadUniforms();
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, quad.getVertexCount());
		}
		unbind();
	}

	private void prepareRender(Camera camera) {
		shader.start();
		shader.vw_matrix = camera.vw_matrix;
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
	}

	private void unbind() {
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}

	private void setUpVAO() {
		// Just x and z vectex positions here, y is set to 0 in v.shader
		float[] vertices = { -1, -1, -1, 1, 1, -1, 1, -1, -1, 1, 1, 1 };
		quad = Loader.loadToVAO(vertices, 2);
	}

}
