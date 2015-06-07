package me.soldier.dmin.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.*;

import me.soldier.dmin.components.*;
import me.soldier.dmin.math.*;
import me.soldier.dmin.shaders.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

public class ToolWindow implements Runnable {
	
	public long window;
	private int width = 150;
	private int height = 900;
	private Input keyCallback;
	private ResizeHandler sizeCallback;
	private MouseHandler mouseCallback;
	
	ComponentShader compShader;
	
	private GLButton btn1;
	public void run() {
		init();
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		
		btn1 = new GLButton(new Vector2f(), new Vector2f(10, 10), "res/gold.png");
		
		while (Main.running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1.0) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " ups, " + frames + " fps (tools)");
				updates = 0;
				frames = 0;
			}
		}
		keyCallback.release();
		sizeCallback.release();
		mouseCallback.release();
	}
	
	private void render() {
		glfwSwapBuffers(window);
		btn1.Render();
	}

	private void update() {
		glfwPollEvents();
	}

	private void init() {
		window = glfwCreateWindow(width, height, "Tools", NULL, NULL);

		if (window == NULL) {
			System.err.println("Could not create GLFW window!");
			return;
		}
		
		// Init window
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (40), (GLFWvidmode.height(vidmode) - height) / 2);

		glfwSetKeyCallback(window, keyCallback = new Input());
		glfwSetWindowSizeCallback(window, sizeCallback = new ResizeHandler());
		glfwSetMouseButtonCallback(window, mouseCallback = new MouseHandler());
		glfwMakeContextCurrent(window);
		glfwSwapInterval(4);
		glfwShowWindow(window);

		GLContext.createFromCurrent();

		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glEnable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		ProjectionMatrix pr_matrix = new ProjectionMatrix(0, width, height, 0, -1, 1);
		compShader = new ComponentShader();
		compShader.setUniformMat4f("pr_matrix", pr_matrix);
//		File f = new File("cursor.png");
//		ByteBuffer image;
//		try {
//			image = GLFWimage.malloc(100, 100, VBOUtil.createByteBuffer(Files.readAllBytes(f.toPath())));
//			long cursor = glfwCreateCursor(image, 0, 0);
//			glfwSetCursor(window, cursor);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
	}
}
