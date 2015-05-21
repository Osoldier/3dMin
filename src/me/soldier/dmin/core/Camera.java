package me.soldier.dmin.core;

import java.util.*;

/**
 * @author Osoldier
 * @since 28 janv. 2015
 * @project Galaxy
 */
public class Camera {

	public ViewMatrix vw_matrix;
	public ArrayList<Shader> UpdatableShaders = new ArrayList<Shader>();
	public Vector3f position = null;
	public float yaw = 0.0f;
	public float pitch = 0.0f;

	public Camera(float x, float y, float z) {
		position = new Vector3f(x, y, z);
		vw_matrix = new ViewMatrix();
	}

	public void yaw(float amount) {
		yaw += amount;
	}

	public void pitch(float amount) {
		pitch += amount;
	}

	public void walkForward(float distance) {
		position.x -= distance * (float) Math.sin(Math.toRadians(yaw));
		position.z += distance * (float) Math.cos(Math.toRadians(yaw));
	}

	public void walkBackwards(float distance) {
		position.x += distance * (float) Math.sin(Math.toRadians(yaw));
		position.z -= distance * (float) Math.cos(Math.toRadians(yaw));
	}

	public void strafeLeft(float distance) {
		position.x -= distance * (float) Math.sin(Math.toRadians(yaw - 90));
		position.z += distance * (float) Math.cos(Math.toRadians(yaw - 90));
	}

	public void strafeRight(float distance) {
		position.x -= distance * (float) Math.sin(Math.toRadians(yaw + 90));
		position.z += distance * (float) Math.cos(Math.toRadians(yaw + 90));
	}

	public void strafeUp(float distance) {
		position.y += distance;
	}
	
	public void strafeDown(float distance) {
		position.y -= distance;
	}
	
	public void lookThrough() {
		vw_matrix.Transform(position, new Vector3f(pitch, yaw, 0));
		for(Shader s : UpdatableShaders) {
			s.setUniformMat4f("vw_matrix", vw_matrix);
		}
	}
}
