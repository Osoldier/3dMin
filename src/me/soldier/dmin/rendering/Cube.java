package me.soldier.dmin.rendering;

import java.nio.*;

import me.soldier.dmin.core.*;
import me.soldier.dmin.math.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Cube extends Shape {

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

	@Override
	public void Render() {
		Main.shapeShader.useShader();

		ml_matrix.Identity();
		ml_matrix.Translate(new Vector3f(0, 0, -10));
		Main.shapeShader.setUniformMat4f("ml_matrix", ml_matrix);
		//Vertex
		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferID);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(Main.shapeShader.getAttributeLocation("vertex"), 3, GL_FLOAT, false, 0, 0);
		//Normal
//		glBindBuffer(GL_ARRAY_BUFFER, normalBufferID);
//		glEnableVertexAttribArray(1);
//		glVertexAttribPointer(Main.shapeShader.getAttributeLocation("normal"), 3, GL_FLOAT, false, 0, 0);
		//Index
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesBufferID);
		glDrawElements(GL_TRIANGLES, elementCount, GL_UNSIGNED_INT, 0);	
		
		Main.shapeShader.releaseShader();
	}

	public void Update() {

	}
	
	@Override
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
