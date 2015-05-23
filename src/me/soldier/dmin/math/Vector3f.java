package me.soldier.dmin.math;

public class Vector3f {

	public float x, y, z;

	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3f() {
		this(0, 0, 0);
	}

	public void normalise() {
		float l = length();
		this.x = this.x / l;
		this.y = this.y / l;
		this.z = this.z /l;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

}
