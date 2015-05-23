package me.soldier.dmin.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.*;

import me.soldier.dmin.math.*;
import me.soldier.dmin.rendering.*;
import me.soldier.dmin.world.*;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

public class Main implements Runnable {

	// BASE
	public static int width = 1600;
	public static int height = 900;

	private Thread thread;
	private boolean running = false;

	private long window;
	// FIX Callback ClosureError;
	private GLFWKeyCallback keyCallback;
	private GLFWWindowSizeCallback sizeCallback;
	private GLFWMouseButtonCallback mouseCallback;

	// BASE
	public void start() {
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}

	World world;
	Grid grid;
	public static Camera camera;
	public static ProjectionMatrix pr_matrix;
	public static Shader shapeShader;
	public static Shader gridShader;

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

		// Init window
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - width) / 2, (GLFWvidmode.height(vidmode) - height) / 2);

		glfwSetKeyCallback(window, keyCallback = new Input());
		glfwSetWindowSizeCallback(window, sizeCallback = new ResizeHandler());
		glfwSetMouseButtonCallback(window, mouseCallback = new MouseHandler());
		glfwMakeContextCurrent(window);
		glfwWindowHint(GLFW_REFRESH_RATE, 1500);
		glfwSwapInterval(0);
		glfwShowWindow(window);

		GLContext.createFromCurrent();
		System.out.println("OpenGL: " + glGetString(GL_VERSION));

//		glEnable(GL_LIGHTING);
//		glEnable(GL_LIGHT0);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glEnable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);

		// init shaders
		camera = new Camera(0, 0, 0);
		pr_matrix = new ProjectionMatrix(45, (float)width/(float)height, 0.001f, 200f);
		shapeShader = new Shader("shape.vert", "shape.frag");
		shapeShader.setUniformMat4f("pr_matrix", pr_matrix);

		gridShader = new Shader("grid.vert", "grid.frag");
		gridShader.setUniformMat4f("pr_matrix", pr_matrix);

		camera.UpdatableShaders.add(gridShader);
		camera.UpdatableShaders.add(shapeShader);

		world = new World();
		grid = new Grid();
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
		sizeCallback.release();
		mouseCallback.release();
		glfwDestroyWindow(window);
		glfwTerminate();
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		camera.lookThrough();
		world.Render();
		grid.Render();
		int error = glGetError();
		if (error != GL_NO_ERROR)
			System.out.println("Error " + error);
		glfwSwapBuffers(window);
	}

	float speed = 0.3f;

	public static DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
	public static DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
	double newX, newY, prevX, prevY;

	private void update() {
		glfwPollEvents();
		if (Input.isKeyDown(GLFW_KEY_W)) {
			camera.Forward(speed);
		}
		if (Input.isKeyDown(GLFW_KEY_S)) {
			camera.Backwards(speed);
		}
		if (Input.isKeyDown(GLFW_KEY_A)) {
			camera.Left(speed);
		}
		if (Input.isKeyDown(GLFW_KEY_D)) {
			camera.Right(speed);
		}
		if (Input.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
			camera.Up(speed);
		}
		if (Input.isKeyDown(GLFW_KEY_LEFT_CONTROL)) {
			camera.Down(speed);
		}
		if (MouseHandler.isButtonDown(0)) {
			glfwGetCursorPos(window, x, y);

			newX = x.get(0);
			newY = y.get(0);

			double deltaX = newX - prevX;
			double deltaY = newY - prevY;

			camera.yaw((float) (deltaX) * 0.1f);
			camera.pitch((float) (deltaY) * 0.1f);

			prevX = newX;
			prevY = newY;
		} else {
			glfwGetCursorPos(window, x, y);

			prevX = x.get(0);
			prevY = y.get(0);
		}
		world.Update();
	}

	public static void main(String[] args) {
		new Main().start();
	}
}
