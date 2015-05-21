package me.soldier.dmin.core;

import org.lwjgl.glfw.*;

public class ResizeHandler extends GLFWWindowSizeCallback {

	@Override
	public void invoke(long window, int width, int height) {
		Main.pr_matrix = new ProjectionMatrix(45, (float)width/(float)height, 0.001f, 100f);
	}
}
