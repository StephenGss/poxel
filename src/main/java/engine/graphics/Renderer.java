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

public class Renderer {
	private Shader shader;
	private Window window;
	
	public Renderer(Window window, Shader shader) {
		this.shader = shader;
		this.window = window;
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
		shader.setUniform("lightPosition", new Vector3f(10, 5, 0));
		shader.setUniform("lightColor", new Vector3f(1, 1, 1));
		GL11.glDrawElements(GL11.GL_TRIANGLES, obj.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
		shader.unbind();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glDisableVertexAttribArray(0);
		GL30.glDisableVertexAttribArray(1);
		GL30.glDisableVertexAttribArray(2);
		GL30.glDisableVertexAttribArray(3);
		GL30.glBindVertexArray(0);


//		glEnable(GL_TEXTURE_2D);
//		glClearColor(43f / 255f, 43f / 255f, 43f / 255f, 0f);
//
//		glPushMatrix();
////		glTranslatef(ww * 0.5f, wh * 0.5f, 0.0f);
////		glScalef(1, 1, 1f);
////		glTranslatef(-w * 0.5f, -h * 0.5f, 0.0f);
//		glBegin(GL_QUADS);
//		{
//			glTexCoord2f(0.0f, 0.0f);
//			glVertex2f(0.0f, 0.0f);
//
//			glTexCoord2f(1.0f, 0.0f);
//			glVertex2f(32, 0.0f);
//
//			glTexCoord2f(1.0f, 1.0f);
//			glVertex2f(32, 32);
//
//			glTexCoord2f(0.0f, 1.0f);
//			glVertex2f(0.0f, 32);
//		}
//		glEnd();
//		glPopMatrix();
//		glDisable(GL_TEXTURE_2D);
	}

}