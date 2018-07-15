package growth.screen;

import growth.inputs.InputManager;
import growth.render.Camera;
import growth.render.text.StaticFonts;
import growth.screen.screens.GameScreen;
import growth.screen.screens.MenuScreen;
import growth.screen.screens.Screen;
import growth.inputs.KeyboardManager;
import growth.inputs.MouseManager;
import growth.util.XmlReader;

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

    private static String[] words;

    /**
     * Window ID.
     * This variable contains the window ID of our game.
     */

    public static KeyboardManager keyboardManager;
    public static MouseManager mouseManager;
    public static InputManager inputsManager;
    public static final Camera CAMERA = new Camera(0,0);

    /**
     * ScreenManager class constructor.
     * Instance the class and set the current screen.
     */
    public ScreenManager(int[][] input) {
        loadWord();
        ActualScreen = (new MenuScreen(this));
        inputsManager = new InputManager(input);
        keyboardManager = new KeyboardManager();
        mouseManager = new MouseManager();
        // Set the key
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
        }
    }

    /**
     * Unload the currentScreen.
     */
    private void currentScreenUnload() {
        ActualScreen.unload();
    }

    public void focus(boolean b){ ActualScreen.focus(b);}

    public void loadWord(){
        words = XmlReader.loadWord();
    }

    public static String getWord(int number){
        return words[number];
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