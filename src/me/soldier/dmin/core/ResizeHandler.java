package me.soldier.dmin.core;

import me.soldier.dmin.math.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

public class ResizeHandler extends GLFWWindowSizeCallback {

	@Override
	public void invoke(long window, int width, int height) {
		Main.pr_matrix = new ProjectionMatrix(45, (float)width/(float)height, 0.001f, 200f);
		Main.modelShader.setUniformMat4f("pr_matrix", Main.pr_matrix);
		Main.gridShader.setUniformMat4f("pr_matrix", Main.pr_matrix);
		Main.width = width;
		Main.height = height;
		GL11.glViewport(0, 0, width, height);
	}
}
