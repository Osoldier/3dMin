package me.soldier.dmin.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.*;

import me.soldier.dmin.rendering.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

public class Main implements Runnable {

	//BASE
	public static int width = 1600;
	public static int height = 900;

	private Thread thread;
	private boolean running = false;

	private long window;
	// FIX Callback ClosureError;
	private GLFWKeyCallback keyCallback;
	
	//BASE
	public void start() {
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}
	
	Cube cube;
	ProjectionMatrix pr_matrix;
	public static Shader shapeShader;
	
	private void init() {
		if (glfwInit() != GL_TRUE) {
			System.err.println("Could not initialize GLFW!");
			return;
		}

		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		window = glfwCreateWindow(width, height, "VBO-3D", NULL, NULL);
		if (window == NULL) {
			System.err.println("Could not create GLFW window!");
			return;
		}

		//Init window
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - width) / 2, (GLFWvidmode.height(vidmode) - height) / 2);

		glfwSetKeyCallback(window, keyCallback = new Input());
		glfwMakeContextCurrent(window);
		glfwWindowHint(GLFW_REFRESH_RATE, 1500);
		glfwSwapInterval(0);
		glfwShowWindow(window);
		
		GLContext.createFromCurrent();
		System.out.println("OpenGL: " + glGetString(GL_VERSION));

		//init shaders
		pr_matrix = new ProjectionMatrix(60, 1280.0f/720.0f, 0.001f, 100f);
//		pr_matrix = new ProjectionMatrix(-10, 10, -10, 10, -10, 10);
		System.out.println(pr_matrix);
		shapeShader = new Shader("shape.vert", "shape.frag");
		shapeShader.setUniformMat4f("pr_matrix", pr_matrix);
		
		//legacy working
//		glMatrixMode(GL_PROJECTION);
//		glOrtho(-10, 10, -10, 10, -10, 10);
//		glMatrixMode(GL_MODELVIEW);
		cube = new Cube();
	}

	public void run() {
		init();

		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (running) {
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
				System.out.println(updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
			if (glfwWindowShouldClose(window) == GL_TRUE)
				running = false;
		}
		keyCallback.release();
		glfwDestroyWindow(window);
		glfwTerminate();
	}
	
	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		cube.Render();
		int error = glGetError();
		if (error != GL_NO_ERROR)
			System.out.println("Error " + error);
		glfwSwapBuffers(window);
	}
	private void update() {
		glfwPollEvents();
		cube.Update();
	}

	

	public static void main(String[] args) {
		new Main().start();
	}
}
