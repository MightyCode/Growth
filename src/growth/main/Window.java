package growth.main;

import growth.render.Render;
import growth.screen.ScreenManager;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Class of the game's main structure.
 *
 * @author MightyCode
 * @version of game : 2.2
 */
@SuppressWarnings("InfiniteLoopStatement")
public class Window {

    public Window(){}

    /**
     * Window id.
     * This global variable contains the id of window using for many things.
     */
    public static final long WINDOWID = createWindow();

    /**
     * Screen manager.
     * This global variable contains the manager of game'screens.
     */
    public static final ScreenManager SCREENMANAGER = new ScreenManager();

    /**
     * Width window size.
     * This global variable contains the width window size.
     */
    public static final int WIDTH = 1280;

    /**
     * Height window size.
     * This global variable contains the height window size.
     */
    public static final int HEIGHT = 720;

    /**
     * Update per second .
     * This global variable contains the refresh rate per second.
     */
    private static final int TPS = 60;

    /**
     * Create the window and return the window'id into the global variable WINDOW_ID.
     * @return windowID
     */
    private static long createWindow(){
        // Setup an error callback.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will be resizable

        // Create the window
        long windowID = glfwCreateWindow(WIDTH, HEIGHT, "Growth", NULL, NULL);
        if (windowID == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(windowID, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    windowID,
                    (Objects.requireNonNull(vidmode).width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(windowID);

        // Enable v-sync
        glfwSwapInterval(0);

        // Make the window visible
        glfwShowWindow(windowID);
        createCapabilities();

        glViewport(0, 0, WIDTH, HEIGHT);

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        return windowID;
    }

    /**
     * Method call by main class to begin the game.
     */
    void run() {
        loop();
        exit();
    }

    /**
     * Main method of game.
     */
    private void loop() {
        // Set render parameters
        Render.setClearColor(225,255);
        Render.glEnable2D();

        // System of tps clock, tps and fps counters
        long start = 0;
        long wait = 1000000000/TPS;
        long second = System.nanoTime();
        int fps = 0;
        int tps = 0;

        while(true){
            long now = System.nanoTime();

            // Update system
            if (now - start > wait) {
                SCREENMANAGER.update();
                start = System.nanoTime();
                tps++;
            }

            // Display system
            SCREENMANAGER.display();
            fps++;

            // Swap the color buffers
            glfwSwapBuffers(WINDOWID);

            if (second + 1000000000 < System.nanoTime()) {
                System.out.println("\n-_-_-_-_-_-_-_-_-_-_-_-_-\n \033[92m FPS : " + fps + "   TPS : " + tps + "\033[0m");
                fps = tps = 0;
                second = System.nanoTime();
            }

            glfwPollEvents();
            if (glfwWindowShouldClose(WINDOWID)) {
                exit();
            }
        }
    }

    /**
     * Exit the game.
     */
    public void exit() {
        SCREENMANAGER.unload();
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(WINDOWID);
        glfwDestroyWindow(WINDOWID);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
        System.out.println("\n-------------------------- \n");
        System.out.println("Good Bye !!! \nGame proposed by\033[93m Bazin Maxence\033[0m,\033[93m Bouin Alexandre" +
                "\033[0m and\033[93m Rehel Amaury. \n\n       \033[92m Growth \033[0m");
        System.out.println("\n-------------------------- \n");

        System.exit(0);
    }
}
