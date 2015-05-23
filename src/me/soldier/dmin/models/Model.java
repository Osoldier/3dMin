package me.soldier.dmin.models;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.*;

import me.soldier.dmin.core.*;
import me.soldier.dmin.math.*;
import me.soldier.dmin.rendering.*;

public class Model {
	int textureBufferID;
	int normalBufferID;
	int vertexBufferID;
	int indicesBufferID;
	int elmntCount;
	private Shader shader;
	private ModelMatrix ml_matrix;
	private Texture texture;
	
	public Model(float[] vertices, float[] normals, float[] textureCoords, int[] indices, Texture tex) {
		this.ml_matrix = new ModelMatrix();
		shader = Main.shapeShader;
		initBuffers(VBOUtil.createFloatBuffer(vertices), VBOUtil.createFloatBuffer(normals), VBOUtil.createFloatBuffer(textureCoords), VBOUtil.createIntBuffer(indices));
		this.texture = tex;
	}
	
	public void Render() {
		shader.useShader();
		glActiveTexture(GL_TEXTURE0+texture.getId());
		texture.bind();
		shader.setUniform("texID", texture.getId());
		shader.setUniform("useTexture", true);

		ml_matrix.Identity();
		ml_matrix.Translate(new Vector3f(0, 0, -10));
		shader.setUniformMat4f("ml_matrix", ml_matrix);
		//Vertex
		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferID);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(shader.getAttributeLocation("vertex"), 3, GL_FLOAT, false, 0, 0);
		//Normal
		glBindBuffer(GL_ARRAY_BUFFER, normalBufferID);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(shader.getAttributeLocation("normal"), 3, GL_FLOAT, false, 0, 0);
		glDisableVertexAttribArray(1);
		//Texture
		glBindBuffer(GL_ARRAY_BUFFER, textureBufferID);
		glEnableVertexAttribArray(2);
		glVertexAttribPointer(shader.getAttributeLocation("texCoord"), 2, GL_FLOAT, true, 0, 0);
		//Index
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesBufferID);
		glDrawElements(GL_TRIANGLES, elmntCount, GL_UNSIGNED_INT, 0);	
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);
		shader.releaseShader();
	}
	
	public void initBuffers(FloatBuffer vertices, FloatBuffer normals, FloatBuffer textures, IntBuffer indices) {
		vertexBufferID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferID);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

		normalBufferID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, normalBufferID);
		glBufferData(GL_ARRAY_BUFFER, normals, GL_STATIC_DRAW);
		
		textureBufferID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, textureBufferID);
		glBufferData(GL_ARRAY_BUFFER, textures, GL_STATIC_DRAW);

		indicesBufferID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesBufferID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

		elmntCount = indices.capacity();
	}

	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}

}
