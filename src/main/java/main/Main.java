package main;

import engine.graphics.*;
import engine.graphics.model.OBJLoader;
import engine.maths.Vector2f;
import engine.object.Camera;
import engine.object.GameObject;
import engine.object.Light;
import org.lwjgl.glfw.GLFW;

import engine.io.Input;
import engine.io.Window;
import engine.maths.Vector3f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class Main implements Runnable {
	public Thread game;
	public Window window;
	public Renderer renderer;
	public Shader shader;
	public final int WIDTH = 1280, HEIGHT = 760;
	public boolean isThirdPerson = false;
	public List<Integer> textures = new ArrayList<Integer>();


//	public GameObject object = new GameObject(new Vector3f(0,0,0), new Vector3f(0,0,0), new Vector3f(1,1,1), OBJLoader.loadObjModel("treesub"));
	public ArrayList<GameObject> objects = new ArrayList<GameObject>();

	public Camera camera = new Camera(new Vector3f(0,0,1), new Vector3f(0,0,0));

	Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(1, 1, 1));
	
	public void start() {
		game = new Thread(this, "game");
		game.start();
	}
	
	public void init() {
		window = new Window(WIDTH, HEIGHT, "Game");
		shader = new Shader("src/main/resources/shaders/mainVertex.glsl", "src/main/resources/shaders/mainFragment.glsl");
		renderer = new Renderer(window, shader);
		window.setBackgroundColor(0.50f, 0.5f, 0.5f);
		window.create();
		objects.add(new GameObject(new Vector3f(10,0,0), new Vector3f(0,0,0), new Vector3f(1,1,1), OBJLoader.loadObjModel("treesub")));
		objects.add(new GameObject(new Vector3f(5,0,-8), new Vector3f(0,0,0), new Vector3f(1,1,1), OBJLoader.loadObjModel("stall")));
		objects.add(new GameObject(new Vector3f(-10,0,0), new Vector3f(0,0,0), new Vector3f(1,1,1), OBJLoader.loadObjModel("tree")));
		for(GameObject object: objects)
			object.create();
		shader.create();

	}
	
	public void run() {
		init();
		while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			update();
			render();
			if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
		}

		for(int texture:textures)
			GL11.glDeleteTextures(texture);
		close();
	}
	
	private void update() {
		window.update();
		for(GameObject object: objects)
			object.update();
		if(isThirdPerson)
			for(GameObject object: objects)
				camera.update(object);
		else
			camera.update();
		if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
			window.mouseState(true);
//			camera.getPosition().setZ(camera.getPosition().getZ()+0.1f);
		}
		if(Input.isKeyDown(GLFW.GLFW_KEY_F5)){
			if(isThirdPerson)
				isThirdPerson = false;
			else
				isThirdPerson = true;
		}

	}
	
	private void render() {
		for(GameObject object: objects)
			renderer.renderObject(object, camera);
		window.swapBuffers();
	}

	private void close(){
		window.destroy();
//		mesh.destroy();
		shader.destroy();
	}
	
	public static void main(String[] args) {
		new Main().start();
	}
}