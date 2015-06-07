package me.soldier.dmin.rendering;

import java.util.*;

import me.soldier.dmin.core.*;
import me.soldier.dmin.entities.*;
import me.soldier.dmin.shaders.*;

public class MasterRenderer {

	ModelShader shader = new ModelShader("src/shaders/models/model.vert", "src/shaders/models/model.frag");
	TerrainShader terrainShader = new TerrainShader();
	
	public List<Entity> entities = new ArrayList<Entity>();
	public List<Terrain> terrains = new ArrayList<Terrain>();
	
	private EntityRenderer entityRenderer;
	private TerrainRenderer terrainRenderer;
	
	public MasterRenderer() {
		entityRenderer = new EntityRenderer(shader, Main.pr_matrix);
		terrainRenderer = new TerrainRenderer(terrainShader, Main.pr_matrix);
	}
	
	public void render(Light sun, Camera camera) {
		shader.start();
		shader.loadLight(sun);
		shader.vw_matrix = camera.vw_matrix;
		entityRenderer.render(entities);
		shader.stop();
		
		terrainShader.start();
		terrainShader.loadLight(sun);
		terrainShader.vw_matrix = camera.vw_matrix;
		terrainRenderer.Render(terrains);
		terrainShader.stop();
	}
	
	
}
