package growth.screen;

import growth.render.Camera;
import growth.render.text.StaticFonts;
import growth.screen.screens.GameScreen;
import growth.screen.screens.MenuScreen;
import growth.screen.screens.OptionScreen;
import growth.screen.screens.Screen;
import growth.inputs.KeyboardManager;
import growth.inputs.MouseManager;

import static org.lwjgl.glfw.GLFW.*;

/**
 * ScreenManager class.
 * This class is the screenManager class used to run the game screens.
 *
 * @author MightyCode
 * @version 1.0
 */
public class ScreenManager {

    /**
     * Current screen.
     * This variable contains the current screen displayed.
     */
    private Screen ActualScreen;

    /**
     * Screen's states.
     * These static final variable counting the different state of screen.
     */
    public static final int MENUSCREEN = 0;
    public static final int GAMESCREEN = 1;
    public static final int OPTIONSCREEN = 2;

    /**
     * Window ID.
     * This variable contains the window ID of our game.
     */

    public static final KeyboardManager KEY = new KeyboardManager();
    public static final MouseManager MOUSE = new MouseManager();
    public static final Camera CAMERA = new Camera(0,0);

    /**
     * ScreenManager class constructor.
     * Instance the class and set the current screen.
     */
    public ScreenManager() {
        ActualScreen = (new MenuScreen(this));
        // Set the key
        KEY.setKey(0, GLFW_KEY_ESCAPE);
        KEY.setKey(1, GLFW_KEY_A);
        KEY.setKey(2, GLFW_KEY_W);
        KEY.setKey(3, GLFW_KEY_D);
        KEY.setKey(4, GLFW_KEY_S);
        KEY.setKey(5, GLFW_KEY_LEFT_SHIFT);
        KEY.setKey(6, GLFW_KEY_F);
        KEY.setKey(7, GLFW_KEY_Q);
        KEY.setKey(8, GLFW_KEY_KP_ADD);
        KEY.setKey(9, GLFW_KEY_KP_SUBTRACT);
        KEY.setKey(10, GLFW_KEY_F1);
        KEY.setKey(11, GLFW_KEY_F2);
        KEY.setKey(12, GLFW_KEY_F3);
        KEY.setKey(13, GLFW_KEY_F4);
    }

    /**
     * Update the current screen.
     */
    public void update() {
        ActualScreen.update();
    }

    /**
     * Display the current screen.
     */
    public void display() {
        ActualScreen.display();
    }

    /**
     * Change the current screen.
     *
     * @param screen Set the new current screen.
     */
    public void setScreen(int screen) {
        currentScreenUnload();
        switch (screen) {
            case MENUSCREEN:
                ActualScreen = (new MenuScreen(this));
                break;
            case GAMESCREEN:
                ActualScreen = (new GameScreen(this));
                break;
            case OPTIONSCREEN:
                ActualScreen = (new OptionScreen(this));
                break;
        }
    }

    /**
     * Unload the currentScreen.
     */
    private void currentScreenUnload() {
        System.out.println("\n -------------------------- \n");
        ActualScreen.unload();
        System.out.println("\n -------------------------- \n");
    }

    /**
     * Unload the game before leaving.
     */
    public void unload() {
        ActualScreen.unload();
        ActualScreen = null;
        StaticFonts.unload();
    }
}