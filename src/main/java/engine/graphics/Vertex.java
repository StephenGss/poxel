package engine.graphics;

import engine.maths.Vector2f;
import engine.maths.Vector3f;

public class Vertex {
	private Vector3f position, color;
	private Vector2f textureCoord;
	private Vector3f normalVector;

	public Vertex(Vector3f position) {
		this.position = position;
		this.color = new Vector3f(0, 0, 0);
		this.textureCoord = new Vector2f(0, 0);
	}

	public Vertex(Vector3f position, Vector3f color) {
		this.position = position;
		this.color = color;
		this.textureCoord = new Vector2f(0, 0);
	}

	public Vertex(Vector3f position, Vector2f textureCoord, Vector3f normalVector) {
		this.position = position;
		this.color = new Vector3f(0, 0, 0);
		this.textureCoord = textureCoord;
		this.normalVector = normalVector;
	}

	public Vertex(Vector3f position, Vector2f textureCoord) {
		this.position = position;
		this.color = new Vector3f(0, 0, 0);
		this.textureCoord = textureCoord;
	}

	public Vertex(Vector3f position, Vector3f color, Vector2f textureCoord) {
		this.position = position;
		this.color = color;
		this.textureCoord = textureCoord;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getColor() {
		return color;
	}

	public Vector2f getTextureCoord() {
		return textureCoord;
	}

	public Vector3f getNormalVector() {
		return normalVector;
	}
}