package engine;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;

import java.nio.FloatBuffer;

import com.sun.tools.javac.Main;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

public class Window {

    private static Window instance = null;

    private int width = 800;
    private int height = 600;

    private long window;

    private Window() {}

    public static Window get() {
        if (instance == null) {
            instance = new Window();
        }

        return instance;
    }

    public void run() {
        init();
        update();
    }

    public void init() {
        glfwInit();

        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);

        window = glfwCreateWindow(width, height, "Poxel Engine", 0, 0);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        glfwShowWindow(window);
    }

    public void update() {
        while (!glfwWindowShouldClose(window)) {
            glClearColor(1, 1, 1, 1);
            glClear(GL_COLOR_BUFFER_BIT);
            MainGameLoop.render(window);
            glfwSwapBuffers(window);
            glfwPollEvents();
        }

        close();
    }

    public void close(){
        glfwTerminate();
    }

}