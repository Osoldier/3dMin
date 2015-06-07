package me.soldier.dmin.shaders;

import me.soldier.dmin.entities.*;
import me.soldier.dmin.math.*;

public class TerrainShader extends Shader {

	public ProjectionMatrix pr_matrix;
	public ViewMatrix vw_matrix;
	public ModelMatrix ml_matrix;
	
	public TerrainShader() {
		super("src/shaders/models/terrain.vert", "src/shaders/models/terrain.frag");
	}

	@Override
	public void loadUniforms() {
		this.setUniformMat4f("pr_matrix", pr_matrix);
		this.setUniformMat4f("vw_matrix", vw_matrix);
		this.setUniformMat4f("ml_matrix", ml_matrix);
		this.setUniform("texID", 0);
	}
	
	public void loadLight(Light light) {
		this.setUniform("lightPos", light.getPosition());
		this.setUniform("lightColor", light.getColor());
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "vertex");
		bindAttribute(2, "normal");
		bindAttribute(3, "texCoord");
	}
	
	

}
