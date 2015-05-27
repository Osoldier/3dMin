package me.soldier.dmin.models;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import me.soldier.dmin.core.*;
import me.soldier.dmin.entities.*;
import me.soldier.dmin.math.*;
import me.soldier.dmin.shaders.*;

public class Model extends Entity {
	
	private int vaoID, vertexCount;
	
	public Model(int vaoID, int vertexCount, Texture tex) {
		this.ml_matrix = new ModelMatrix();
		shader = Main.shapeShader;
		this.texture = tex;
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		
		this.position = new Vector3f(0, 0, -5);
		this.rotation = new Vector3f(0, 0, 0);
		this.scale = new Vector3f();
	}
	
	public void Render() {
		shader.start();
		glActiveTexture(GL_TEXTURE0+texture.getId());
		texture.bind();
		shader.setUniform("texID", texture.getId());
		shader.setUniform("useTexture", 1);

		ml_matrix.Identity();
		ml_matrix.Rotate(this.rotation.x, 1, 0, 0);
		ml_matrix.Rotate(this.rotation.y, 0, 1, 0);
		ml_matrix.Rotate(this.rotation.z, 0, 0, 1);
		ml_matrix.Translate(this.position);

		shader.ml_matrix = ml_matrix;
		shader.loadUniforms();
		
		glBindVertexArray(getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);
		glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(3);
		glBindVertexArray(0);
		shader.stop();
	}

	public Shader getShader() {
		return shader;
	}

	public void setShader(ModelShader shader) {
		this.shader = shader;
	}

	public int getVaoID() {
		return vaoID;
	}

	public void setVaoID(int vaoID) {
		this.vaoID = vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}

}
