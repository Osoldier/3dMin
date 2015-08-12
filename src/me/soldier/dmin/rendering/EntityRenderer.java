package me.soldier.dmin.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.*;

import me.soldier.dmin.entities.*;
import me.soldier.dmin.math.*;
import me.soldier.dmin.shaders.*;

public class EntityRenderer {

	private ModelShader shader;

	public EntityRenderer(ModelShader shader, ProjectionMatrix pr_matrix) {
		this.shader = shader;
		this.shader.pr_matrix = pr_matrix;
	}

	public void render(List<Entity> entityList) {
		shader.start();


		for (Entity entity : entityList) {
			glActiveTexture(GL_TEXTURE0 + entity.getMaterial().getTexture().getId());
			entity.getMaterial().getTexture().bind();

			shader.setUniform("texID", entity.getMaterial().getTexture().getId());
			shader.setUniform("useTexture", 1);

			shader.shineDamper = entity.getMaterial().getShineDamper();
			shader.reflectivity = entity.getMaterial().getReflectivity();

			entity.ml_matrix.Identity();
			entity.ml_matrix.Rotate(entity.rotation.x, 1, 0, 0);
			entity.ml_matrix.Rotate(entity.rotation.y, 0, 1, 0);
			entity.ml_matrix.Rotate(entity.rotation.z, 0, 0, 1);
			entity.ml_matrix.Translate(entity.position);
			
			shader.ml_matrix = entity.ml_matrix;

			shader.loadUniforms();

			glBindVertexArray(entity.getModel().getVaoID());
			glEnableVertexAttribArray(0);
			glEnableVertexAttribArray(2);
			glEnableVertexAttribArray(3);
			glDrawElements(GL_TRIANGLES, entity.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);
			glDisableVertexAttribArray(0);
			glDisableVertexAttribArray(2);
			glDisableVertexAttribArray(3);
			glBindVertexArray(0);
		}

		shader.stop();
	}
}
