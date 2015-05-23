package me.soldier.dmin.world;

import java.util.*;

import me.soldier.dmin.core.*;
import me.soldier.dmin.models.*;

public class World {

	public ArrayList<Model> objects;
	public MousePicker picker;
	Loader loader = new Loader();
	Renderer renderer = new Renderer();
	Model model;
	
	public World() {
		model = OBJLoader.loadObjModel("stall.obj", "stallTexture.png", loader);
		model.setShader(Main.shapeShader);
		objects = new ArrayList<Model>();
		objects.add(model);
		picker = new MousePicker(Main.camera.vw_matrix, Main.pr_matrix);

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
		picker.Update((float)Main.x.get(0), (float)Main.y.get(0));
		//System.out.println(picker.getCurrentRay().x+" "+picker.getCurrentRay().y+" "+picker.getCurrentRay().z);
	}
	
}
