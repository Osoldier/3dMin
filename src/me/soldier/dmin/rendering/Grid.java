package me.soldier.dmin.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.*;

import me.soldier.dmin.core.*;
import me.soldier.dmin.math.*;

public class Grid {

	private int vertexBufferID;
	private ModelMatrix ml_matrix;

	static float[] VertexData = new float[] {
			-1.0f, 0.0f, -1.0f,
			1.0f, 0.0f, -1.0f,
			1.0f, 0.0f, 1.0f,
			-1.0f, 0.0f, 1.0f,
	};
	
	private static FloatBuffer vertices;
	
	static {
		vertices = VBOUtil.createFloatBuffer(VertexData);
	}
	
	public Grid() {
		ml_matrix = new ModelMatrix();
		Main.gridShader.ml_matrix = ml_matrix;
		initBuffers();
	}

	public void Render() {
		Main.gridShader.start();
		ml_matrix.Identity();
		ml_matrix.Scale(new Vector3f(10, 0, 10));
		
		for (int x = -100; x <= 100; x += 10) {
			for (int z = -100; z <= 100; z += 10) {
				ml_matrix.Translate(new Vector3f(x, -20, z));
				Main.gridShader.loadUniforms();
				glBindBuffer(GL_ARRAY_BUFFER, vertexBufferID);
				glEnableVertexAttribArray(0);
				glVertexAttribPointer(Main.gridShader.getAttributeLocation("vertex"), 3, GL_FLOAT, false, 0, 0);
				glDrawArrays(GL_LINE_LOOP, 0, 4);
			}
		}
		Main.gridShader.stop();
	}

	private void initBuffers() {
		vertexBufferID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferID);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
	}
}
