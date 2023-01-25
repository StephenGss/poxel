package engine.render;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL46;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    private final List<Integer> vaos = new ArrayList<Integer>();
    private final List<Integer> vbos = new ArrayList<Integer>();

    public RawModel loadToVAO(float[] positions){
        int vaoID = createVAO();
        storeDataInAttributeList(0, positions);
        unbindVAO();
        return new RawModel(vaoID, positions.length/3);
    }

    private int createVAO(){
        int vaoID = GL46.glGenVertexArrays();
        vaos.add(vaoID);
        GL46.glBindVertexArray(vaoID);
        return vaoID;
    }

    private void storeDataInAttributeList(int attributeNumber, float[] data){
        int vboID = GL46.glGenBuffers();
        vbos.add(vboID);
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL46.glBufferData(GL46.GL_ARRAY_BUFFER, buffer, GL46.GL_STATIC_DRAW);
        GL46.glVertexAttribPointer(attributeNumber, 3, GL46.GL_FLOAT, false, 0, 0);
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0); //unbind current vbo by binding 0
    }

    private void unbindVAO(){
        GL30.glBindVertexArray(0);
    }

    // called when closing the game to delete everything
    public void cleanUp(){
        for(int vao:vaos)
            GL46.glDeleteVertexArrays(vao);
        for(int vbo:vbos)
            GL46.glDeleteBuffers(vbo);
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
