package me.soldier.dmin.world;

import me.soldier.dmin.core.*;
import me.soldier.dmin.math.*;

public class MousePicker {
	
	private Vector3f currentRay;
	
	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private float mouseX, mouseY;
	
	public MousePicker(Matrix4f viewMatrix, Matrix4f projectionMatrix) {
		this.viewMatrix = viewMatrix;
		this.projectionMatrix = projectionMatrix;
	}
	
	public Vector3f getCurrentRay() {
		return currentRay;
	}
	
	public void Update(float x, float y) {
		currentRay = calculateMouseRay();
		this.mouseX = x;
		this.mouseY = y;
	}
	
	private Vector3f calculateMouseRay() {
		//Viewport -> Normalized device
		Vector2f normalizedCoords = getNormalizedDeviceCoords(mouseX, mouseY);
		//Normalized Coords -> Homogenous clip space
		Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1f, 1f);
		//Homogenous coords -> eye space (view)
		Vector4f eyeCoords = toEyeCoords(clipCoords);
		//Eye space -> World space
		Vector3f worldRay = toWorldCoords(eyeCoords);
		return worldRay;
	}
	
	private Vector3f toWorldCoords(Vector4f eyeCoords) {
		Matrix4f invertedView = Matrix4f.invert(viewMatrix, null);
		Vector4f rayWorld = Matrix4f.transform(invertedView, eyeCoords, null);
		Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
		mouseRay.normalise();
		return mouseRay;
	}
	
	private Vector4f toEyeCoords(Vector4f clipCoords) {
		Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix, null);
		Vector4f eyeCoords = Matrix4f.transform(invertedProjection, clipCoords, null);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}
	
	private Vector2f getNormalizedDeviceCoords(float x, float y) {
		return new Vector2f((2*x) / Main.width - 1, ((2*-y) / Main.height - 1));
	}
	
}
