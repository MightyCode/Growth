package migthycode.growth.main;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import migthycode.growth.Game.screen.ScreenManager;
import migthycode.growth.Game.utils.Render;
import migthycode.growth.Game.utils.Texture;

public class main {

	// The window handle
	private long window;
	
	private ScreenManager screenManager;
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int SCALE = 1;
	
	private int TPS = 60;
	private long targetTime = 1000 / TPS;
	
	public Render render;
	
	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private void init() {
		//glfwSetKeyCallback(window, keyCallback = new KeyboardHandler());	
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(WIDTH, HEIGHT, "Growth",NULL,NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");
		
		/*
		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ENTER && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});
		 */
		
		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
		createCapabilities();
		
		glViewport(0, 0, WIDTH, HEIGHT);
		
		screenManager = new ScreenManager(window);
		
		glEnable(GL_TEXTURE_2D);
		render = new Render(new Texture());
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); 
	}
	
	private void loop() {
		

		// Set the clear color
		glClearColor(255,255,255,255);

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		long start;
		long elapsed;
		long wait;
		int fps;
		int tps;
		
		render.glEnable2D();
		
		int counter = 0;
		start = System.nanoTime();
		while ( !glfwWindowShouldClose(window) ) {
			//if()
			screenManager.update();
			screenManager.display(render);
			glfwSwapBuffers(window); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
			
			elapsed = System.nanoTime() - start;
			if(elapsed > 1000000000) {
				System.out.println(counter);
				start = System.nanoTime();
				counter = 0;
			}
			
			counter++;
		}
	}

	public static void main(String[] args) {
		new main().run();
	}
}
