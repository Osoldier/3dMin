package me.soldier.dmin.models;

public class Loader {
	
	public Model createModelVBO(float[] pos, float[] normals, float[] tex, int[] indices, Texture t) {
		return new Model(pos, normals, tex, indices, t);
	}

}
