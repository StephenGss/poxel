package engine.maths;

import java.util.Objects;

public class Vector3f {
	private float x, y, z;
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static Vector3f add(Vector3f vec1, Vector3f vec2){
		return new Vector3f(vec1.getX() + vec2.getX(), vec1.getY() + vec2.getY(), vec1.getZ() + vec2.getZ());
	}

	public static Vector3f subtract(Vector3f vec1, Vector3f vec2){
		return new Vector3f(vec1.getX() - vec2.getX(), vec1.getY() - vec2.getY(), vec1.getZ() - vec2.getZ());
	}

	public static Vector3f multiply(Vector3f vec1, Vector3f vec2){
		return new Vector3f(vec1.getX() * vec2.getX(), vec1.getY() * vec2.getY(), vec1.getZ() * vec2.getZ());
	}

	public static Vector3f divide(Vector3f vec1, Vector3f vec2){
		return new Vector3f(vec1.getX() / vec2.getX(), vec1.getY() / vec2.getY(), vec1.getZ() / vec2.getZ());
	}

	public static float length(Vector3f vector) {
		return (float) Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY() + vector.getZ() * vector.getZ());
	}

	public static Vector3f normalize(Vector3f vector) {
		float len = Vector3f.length(vector);
		return Vector3f.divide(vector, new Vector3f(len, len, len));
	}

	public static float dot(Vector3f vector1, Vector3f vector2) {
		return vector1.getX() * vector2.getX() + vector1.getY() * vector2.getY() + vector1.getZ() * vector2.getZ();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Vector3f vector3f)) return false;
		return Float.compare(vector3f.getX(), getX()) == 0 && Float.compare(vector3f.getY(), getY()) == 0 && Float.compare(vector3f.getZ(), getZ()) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getX(), getY(), getZ());
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
}