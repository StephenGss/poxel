package engine.graphics;

import engine.object.Camera;
import engine.object.GameObject;
import engine.object.Light;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterRenderer {

    private Shader shader;
    private Renderer renderer;

    private Map<Mesh, List<GameObject>> gameObjects = new HashMap<Mesh, List<GameObject>>();

    public MasterRenderer(Shader shader, Renderer renderer){
        this.shader = shader;
        this.renderer = renderer;
        shader.create();
    }

    public void render(Light sun, Camera camera){
//        shader.loadLight(sun);
        renderer.render(gameObjects, camera);
        gameObjects.clear();
    }

    public void processEntity(GameObject object){
        Mesh mesh = object.getMesh();
        List<GameObject> batch = gameObjects.get(mesh);
        if(batch!=null){
            batch.add(object);
        }else{
            List<GameObject> newBatch = new ArrayList<GameObject>();
            newBatch.add(object);
            gameObjects.put(mesh, newBatch);
        }
    }

    public void cleanUp(){
        shader.destroy();
    }

}
