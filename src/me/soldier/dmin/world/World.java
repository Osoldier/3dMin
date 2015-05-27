package me.soldier.dmin.world;

import java.util.*;

import me.soldier.dmin.core.*;
import me.soldier.dmin.entities.*;
import me.soldier.dmin.math.*;
import me.soldier.dmin.models.*;

public class World {

	public ArrayList<Model> objects;
	public MousePicker picker;
	Loader loader = new Loader();
	Renderer renderer = new Renderer();
	Model model;
	Light light0;
	
	public World() {
		model = OBJLoader.loadObjModel("models/dragon.obj", "models/gold.png");
		model.setShader(Main.shapeShader);
		objects = new ArrayList<Model>();
		objects.add(model);
		picker = new MousePicker(Main.camera.vw_matrix, Main.pr_matrix);
		//light
		light0 = new Light(new Vector3f(10, 5, -5), new Vector3f(1, 1, 1));
		Main.shapeShader.loadLight(light0);
	}
	
	public void Render() {
		for(Model shp : objects) {
			shp.Render();
		}
		renderer.render(model);
	}
	
	public void Update() {
		if(MouseHandler.isButtonDown(0)) {
			
		}
		model.rotation.y += 1f;

		picker.Update((float)Main.x.get(0), (float)Main.y.get(0));
		//System.out.println(picker.getCurrentRay().x+" "+picker.getCurrentRay().y+" "+picker.getCurrentRay().z);
	}
	
}
