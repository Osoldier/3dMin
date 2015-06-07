package me.soldier.dmin.world;

import me.soldier.dmin.core.*;
import me.soldier.dmin.entities.*;
import me.soldier.dmin.math.*;
import me.soldier.dmin.models.*;
import me.soldier.dmin.rendering.*;

public class World {

	public MousePicker picker;
	Loader loader = new Loader();
	MasterRenderer renderer = new MasterRenderer();
	Model model;
	Light light0;
	Terrain test;

	public World() {
		picker = new MousePicker(Main.camera.vw_matrix, Main.pr_matrix);
		// light
		light0 = new Light(new Vector3f(500, 5000, 0), new Vector3f(1, 1, 1));
		test = new Terrain(0, 0, Material.GOLD.getTexture());
		renderer.entities.add(new Dragon());
		renderer.terrains.add(test);
	}

	public void Render() {
		renderer.render(light0, Main.camera);
	}

	public void Update() {
		if (MouseHandler.isButtonDown(0)) {

		}
		picker.Update((float) Main.x.get(0), (float) Main.y.get(0));
		// System.out.println(picker.getCurrentRay().x+" "+picker.getCurrentRay().y+" "+picker.getCurrentRay().z);
	}

}
