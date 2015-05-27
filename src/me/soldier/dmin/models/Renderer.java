package me.soldier.dmin.models;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
	
	public Renderer() {
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}
	
	
	public void render(Model model) {
		model.Render();
	}
}
