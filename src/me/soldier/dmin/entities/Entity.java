package me.soldier.dmin.entities;

import me.soldier.dmin.math.*;
import me.soldier.dmin.models.*;
import me.soldier.dmin.rendering.*;
import me.soldier.dmin.shaders.*;

public abstract class Entity implements Renderable{
	
	public int id;

	public Vector3f rotation;
	public Vector3f position;
	public Vector3f scale;
	
	protected ModelShader shader;
	protected ModelMatrix ml_matrix;
	protected Texture texture;
	
	
	protected int vaoID;
	
	
}
