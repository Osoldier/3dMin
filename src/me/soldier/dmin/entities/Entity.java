package me.soldier.dmin.entities;

import me.soldier.dmin.math.*;
import me.soldier.dmin.models.*;
import me.soldier.dmin.rendering.*;

public abstract class Entity{
	
	public int id;

	public Vector3f rotation;
	public Vector3f position;
	public Vector3f scale;
	
	public ModelMatrix ml_matrix;
	private Material material;
	private Model model;
	
	protected int vaoID;

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
	
}
