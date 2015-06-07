package me.soldier.dmin.rendering;

import java.util.*;

import me.soldier.dmin.entities.*;
import me.soldier.dmin.math.*;
import me.soldier.dmin.models.*;
import me.soldier.dmin.shaders.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL11.*;

public class TerrainRenderer {

	private TerrainShader shader;
	
	public TerrainRenderer(TerrainShader shader, ProjectionMatrix pr_matrix) {
		this.shader = shader;
		this.shader.pr_matrix = pr_matrix;
	}

	public void Render(List<Terrain> terrains) {
		for(Terrain terrain : terrains) {
			prepareTerrain(terrain);
			shader.loadUniforms();
			glDrawElements(GL_TRIANGLES, terrain.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);
			unbindTexturedModel();
		}
	}
	
	private void prepareTerrain(Terrain terrain) {
		Model rawModel = terrain.getModel();
		glBindVertexArray(rawModel.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);
		glActiveTexture(GL_TEXTURE0);
		terrain.getTexture().bind();
		LoadTerrainTransformation(terrain);
	}
	
	private void unbindTexturedModel() {
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(3);
		glBindVertexArray(0);
	}
	
	private void LoadTerrainTransformation(Terrain terrain) {
		ModelMatrix ml_matrix = new ModelMatrix();
		ml_matrix.Translate(new Vector3f(terrain.getX(), 0, terrain.getZ()));
		shader.ml_matrix = ml_matrix;
	}
}
