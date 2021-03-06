package me.soldier.dmin.entities;

import static org.lwjgl.opengl.GL15.*;

import java.nio.*;

import me.soldier.dmin.math.*;
import me.soldier.dmin.rendering.*;

public class Cube extends Entity {

	int colorBufferID;
	int normalBufferID;
	int vertexBufferID;
	int indicesBufferID;
	private int elementCount = 0;
	public ModelMatrix ml_matrix;

	static float[] cubeVertexData = new float[] {
			-1.0f, -1.0f, 1.0f,
			1.0f, -1.0f, 1.0f,
			1.0f, 1.0f, 1.0f,
			-1.0f, 1.0f, 1.0f,
			-1.0f, -1.0f, -1.0f,
			1.0f, -1.0f, -1.0f,
			1.0f, 1.0f, -1.0f,
			-1.0f, 1.0f, -1.0f
	};

	static float[] cubeNormalData = new float[] {
			-1.0f, -1.0f, 1.0f,
			1.0f, -1.0f, 1.0f,
			1.0f, 1.0f, 1.0f,
			-1.0f, 1.0f, 1.0f,
			-1.0f, -1.0f, -1.0f,
			1.0f, -1.0f, -1.0f,
			1.0f, 1.0f, -1.0f,
			-1.0f, 1.0f, -1.0f
	};

	static int[] indicesVboData = new int[] {
			0, 1, 2, 2, 3, 0,
			3, 2, 6, 6, 7, 3,
			7, 6, 5, 5, 4, 7,
			4, 0, 3, 3, 7, 4,
			0, 1, 5, 5, 4, 0,
			1, 5, 6, 6, 2, 1,
			2, 3, 4, 4, 3, 5
	};

	public static FloatBuffer vertices;
	public static FloatBuffer normals;
	public static IntBuffer indices;

	static {
		vertices = VBOUtil.createFloatBuffer(cubeVertexData);
		normals = VBOUtil.createFloatBuffer(cubeNormalData);
		indices = VBOUtil.createIntBuffer(indicesVboData);
	}

	public Cube() {
		ml_matrix = new ModelMatrix();
		initBuffers();
	}

	public void Update() {

	}
	
	public void initBuffers() {
		vertexBufferID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferID);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

		normalBufferID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, normalBufferID);
		glBufferData(GL_ARRAY_BUFFER, normals, GL_STATIC_DRAW);

		indicesBufferID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesBufferID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

		elementCount = indicesVboData.length;
	}

}
