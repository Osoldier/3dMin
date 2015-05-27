package me.soldier.dmin.entities;

import me.soldier.dmin.models.*;

public class Terrain {

	private static final float SIZE = 800;
	private static final int VERTEX_COUNT = 128;
	
	private float x,z;
	private Model model;
	private Texture texture;
	
	public Terrain(int gridX, int gridZ, Loader loader, Texture texture) {
		this.texture = texture;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
	}
	
	
}
