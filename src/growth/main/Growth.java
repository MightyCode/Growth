package growth.main;

import growth.game.screen.ScreenManager;
import growth.game.render.Render;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Growth {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final long WINDOWID = createWindow();
    public static final ScreenManager SCREENMANAGER = new ScreenManager();
    public static final Exit EXIT = new Exit();

    private static final int TPS = 60;

    public static void main(String[] args) {
        new Growth().run();
    }

    private static long createWindow(){
        long window;

        //glfwSetKeyCallback(window, keyCallback = new KeyboardHandler());
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Growth", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

		/*
		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ENTER && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});
		 */

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (Objects.requireNonNull(vidmode).width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);

        // Enable v-sync
        glfwSwapInterval(0);

        // Make the window visible
        glfwShowWindow(window);
        createCapabilities();

        glViewport(0, 0, WIDTH, HEIGHT);

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        return window;
    }

    private void run() {
        loop();
        EXIT.exit();
    }


    private void loop() {
        // Set the clear color
        glClearColor(255, 255, 255, 255);
        Render.glEnable2D();

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        long start;
        long wait;
        int fps = 0;
        int tps = 0;
        long seconde = System.nanoTime();
        wait = 1000000000/TPS;
        start = 0;

        while (!glfwWindowShouldClose(WINDOWID)) {
            long now = System.nanoTime();
            if (now - start > wait) {
                SCREENMANAGER.update();
                start = System.nanoTime();
                tps++;
            }

            SCREENMANAGER.display();
            fps++;
            glfwSwapBuffers(WINDOWID); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
            if (seconde + 1000000000 < System.nanoTime()) {
                System.out.println("FPS : " + fps + " ,TPS : " + tps);
                fps = tps = 0;
                seconde = System.nanoTime();
            }
        }
    }
}
