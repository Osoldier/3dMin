package me.soldier.dmin.shaders;

import me.soldier.dmin.math.*;

public class GridShader extends Shader {

	public ProjectionMatrix pr_matrix;
	public ViewMatrix vw_matrix;
	public ModelMatrix ml_matrix;
	
	public GridShader() {
		super("src/shaders/grid.vert", "src/shaders/grid.frag");
	}

	@Override
	public void loadUniforms() {
		this.setUniformMat4f("pr_matrix", pr_matrix);
		this.setUniformMat4f("vw_matrix", vw_matrix);
		this.setUniformMat4f("ml_matrix", ml_matrix);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "vertex");
	}

}
