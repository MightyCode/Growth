package growth.main;

import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class Exit {
    public Exit(){}
    public void exit(){
        Growth.SCREENMANAGER.unload();
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(Growth.WINDOWID);
        glfwDestroyWindow(Growth.WINDOWID);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
        System.exit(0);
    }
}
