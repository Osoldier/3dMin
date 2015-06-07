package me.soldier.dmin.entities;

import me.soldier.dmin.math.*;
import me.soldier.dmin.models.*;

public class Dragon extends Entity {

	private static Model DragonModel;
	
	static {
		DragonModel = OBJLoader.loadObjModel("models/dragon.obj");
	}
	
	public Dragon() {
		this.position = new Vector3f();
		this.scale = new Vector3f(1, 1, 1);
		this.rotation = new Vector3f();
		this.setModel(DragonModel);
		this.setMaterial(Material.GOLD);
		this.ml_matrix = new ModelMatrix();
	}
}
