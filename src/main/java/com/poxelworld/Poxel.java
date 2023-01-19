package com.poxelworld;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 * This is the Main Class of your Game. It should boot up your game and do initial initialisation
 * Move your Logic into AppStates or Controls or other java classes
 */
public class Poxel extends SimpleApplication {

    public static void main(String[] args) {
        Poxel app = new Poxel();
        app.setShowSettings(false); //Settings dialog not supported on mac
        app.start();
    }

    @Override
    public void simpleInitApp() {
        for(int x=1; x<32; x++){
            for(int y=1; y<32; y++){
                for(int z=1; z<32; z++){
                    Box b = new Box(x, y, x);
                    Geometry geom = new Geometry("Box", b);

                    Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                    mat.setColor("Color", new ColorRGBA(x/32.0f, 0/32.0f, z/32.0f, 1f));
                    geom.setMaterial(mat);

                    rootNode.attachChild(geom);
                }
            }
        }
    }

    @Override
    public void simpleUpdate(float tpf) {
        //this method will be called every game tick and can be used to make updates
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //add render code here (if any)
    }
}
