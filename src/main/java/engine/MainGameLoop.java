package engine;

import engine.render.Loader;
import engine.render.RawModel;
import engine.render.Renderer;

public class MainGameLoop {

    static Loader loader = new Loader();
    static Renderer renderer = new Renderer();

    static float[] vertices = {
            -0.5f, 0.5f, 0f,
            -0.5f, -0.5f, 0f,
            0.5f, -0.5f, 0f,
            0.5f, -0.5f, 0f,
            0.5f, 0.5f, 0f,
            -0.5f, 0.5f, 0f
    };

    static RawModel model = loader.loadToVAO(vertices);

    public static void update(){

    }

    public static void render(long window){
        renderer.prepare();
        renderer.render(model);
    }
}
