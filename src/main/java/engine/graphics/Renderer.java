package engine.graphics;

import engine.io.Window;
import engine.maths.Matrix4f;
import engine.maths.Vector3f;
import engine.object.Camera;
import engine.object.GameObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.util.List;
import java.util.Map;

public class Renderer {
	private Shader shader;
	private Window window;
	
	public Renderer(Window window, Shader shader) {
		this.shader = shader;
		this.window = window;
	}

	public void render(Map<Mesh, List<GameObject>> gameObjects, Camera camera){
		for(Mesh mesh: gameObjects.keySet()){
			prepareMesh(mesh);
			List<GameObject> batch = gameObjects.get(mesh);
			for(GameObject object: batch){
				prepareInstance(object, camera);

				GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
			}
			unbindMesh();
		}
	}

	private void prepareMesh(Mesh mesh){
		GL30.glBindVertexArray(mesh.getVAO());
		GL30.glEnableVertexAttribArray(0);
		GL30.glEnableVertexAttribArray(1);
		GL30.glEnableVertexAttribArray(2);
		GL30.glEnableVertexAttribArray(3);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL13.glBindTexture(GL11.GL_TEXTURE_2D, mesh.getMaterial().getTextureID());
		shader.bind();
		shader.setUniform("shineDamper", mesh.getMaterial().getShineDamper());
		shader.setUniform("reflectivity", mesh.getMaterial().getReflectivity());
	}

	private void unbindMesh(){
		shader.unbind();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glDisableVertexAttribArray(0);
		GL30.glDisableVertexAttribArray(1);
		GL30.glDisableVertexAttribArray(2);
		GL30.glDisableVertexAttribArray(3);
		GL30.glBindVertexArray(0);
	}

	private void prepareInstance(GameObject gameObject, Camera camera){
		shader.setUniform("transform", Matrix4f.transform(gameObject.getPosition(), gameObject.getRotation(), gameObject.getScale()));
		shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
		shader.setUniform("projection", window.getProjectionMatrix());
		// TODO: finish lighting to be based on light object
		shader.setUniform("lightPosition", new Vector3f(10, 5, 0));
		shader.setUniform("lightColor", new Vector3f(1, 1, 1));
	}

	public void renderObject(GameObject obj, Camera camera) {
		GL30.glBindVertexArray(obj.getMesh().getVAO());
		GL30.glEnableVertexAttribArray(0);
		GL30.glEnableVertexAttribArray(1);
		GL30.glEnableVertexAttribArray(2);
		GL30.glEnableVertexAttribArray(3);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, obj.getMesh().getIBO());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL13.glBindTexture(GL11.GL_TEXTURE_2D, obj.getMesh().getMaterial().getTextureID());
		shader.bind();

		shader.setUniform("transform", Matrix4f.transform(obj.getPosition(), obj.getRotation(), obj.getScale()));
		shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
		shader.setUniform("projection", window.getProjectionMatrix());
		// TODO: finish lighting to be based on light object
		shader.setUniform("lightPosition", new Vector3f(10, 5, 0));
		shader.setUniform("lightColor", new Vector3f(1, 1, 1));

		shader.setUniform("shineDamper", obj.getMesh().getMaterial().getShineDamper());
		shader.setUniform("reflectivity", obj.getMesh().getMaterial().getReflectivity());

		GL11.glDrawElements(GL11.GL_TRIANGLES, obj.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
		shader.unbind();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glDisableVertexAttribArray(0);
		GL30.glDisableVertexAttribArray(1);
		GL30.glDisableVertexAttribArray(2);
		GL30.glDisableVertexAttribArray(3);
		GL30.glBindVertexArray(0);
	}

}