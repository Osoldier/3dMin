package me.soldier.dmin.shaders;

import me.soldier.dmin.entities.*;
import me.soldier.dmin.math.*;

public class ModelShader extends Shader {

	public ProjectionMatrix pr_matrix;
	public ViewMatrix vw_matrix;
	public ModelMatrix ml_matrix;

	public ModelShader(String pVertexShader, String pFragmentShader) {
		super(pVertexShader, pFragmentShader);
	}

	@Override
	public void loadUniforms() {
		this.setUniformMat4f("pr_matrix", pr_matrix);
		this.setUniformMat4f("vw_matrix", vw_matrix);
		this.setUniformMat4f("ml_matrix", ml_matrix);
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
