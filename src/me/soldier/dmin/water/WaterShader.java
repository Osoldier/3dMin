package me.soldier.dmin.water;

import me.soldier.dmin.math.*;
import me.soldier.dmin.shaders.*;

public class WaterShader extends Shader {

	private final static String VERTEX_FILE = "src/shaders/statics/water.vert";
	private final static String FRAGMENT_FILE = "src/shaders/statics/water.frag";

	public ProjectionMatrix pr_matrix;
	public ModelMatrix ml_matrix;
	public ViewMatrix vw_matrix;

	public WaterShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
	}

	@Override
	public void loadUniforms() {
		this.setUniformMat4f("projectionMatrix", pr_matrix);
		this.setUniformMat4f("viewMatrix", vw_matrix);
		this.setUniformMat4f("modelMatrix", ml_matrix);
	}

}
