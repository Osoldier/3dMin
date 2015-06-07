package me.soldier.dmin.shaders;

public class ComponentShader extends Shader {

	public ComponentShader() {
		super("src/shaders/comp/comp.vert", "src/shaders/comp/comp.frag");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "vertex");
	}

	@Override
	public void loadUniforms() {
		
	}

}
