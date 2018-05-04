package migthycode.growth.Game.screen;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_V;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

import migthycode.growth.Game.utils.Render;

public class MenuScreen extends Screen{
	
	public MenuScreen(ScreenManager screenManager) {
		super(screenManager);
	}
	
	public void update() {
    	if(glfwGetKey(screenManager.getWindow(), GLFW_KEY_V) == 1)
    		System.out.println("Space Key Pressed");
    	
	}
	
	public void display(Render render) {
		
	}
}
