package me.soldier.dmin.models;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.*;

import me.soldier.dmin.rendering.*;


public class Loader {
	
	public static Model createModelVAO(float[] pos, float[] normals, float[] tex, int[] indices) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, 3, pos);
		storeDataInAttributeList(2, 3, normals);
		storeDataInAttributeList(3, 2, tex);
		bindIndicesBuffer(indices);
		unbindVAO();
		return new Model(vaoID, indices.length);
	}
	
	private static List<Integer> vaos = new ArrayList<Integer>();
	private static List<Integer> vbos = new ArrayList<Integer>();
	
	private static int createVAO() {
		int vaoID = glGenVertexArrays();
		vaos.add(vaoID);
		glBindVertexArray(vaoID);
		return vaoID;
	}
	
	public static Model loadToVAO(float[] positions, int dimensions) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, dimensions, positions);
		unbindVAO();
		return new Model(vaoID, positions.length / dimensions);
	}
	
	private static void storeDataInAttributeList(int attributeNumber, int size, float[] data) {
		int vboID = glGenBuffers();
		vbos.add(vboID);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, VBOUtil.createFloatBuffer(data), GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNumber, size, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	private static void bindIndicesBuffer(int[]indices) {
		int vboID = glGenBuffers();
		vbos.add(vboID);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, VBOUtil.createIntBuffer(indices), GL_STATIC_DRAW);;
	}
	
	private static void unbindVAO() {
		glBindVertexArray(0);
	}
	
	public static void cleanUp() {
		for(int i : vaos) glDeleteVertexArrays(i);
		for(int i : vbos) glDeleteBuffers(i);
	}
	
}
