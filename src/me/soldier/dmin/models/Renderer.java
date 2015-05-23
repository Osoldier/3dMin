package me.soldier.dmin.models;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

	public void prepare() {
		glClearColor(1, 0, 0, 1);
	}
	
	public void render(Model model) {
		model.Render();
	}
}
