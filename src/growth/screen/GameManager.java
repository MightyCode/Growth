package growth.screen;

import growth.inputs.InputManager;
import growth.render.Camera;
import growth.render.text.StaticFonts;
import growth.screen.screens.GameScreen;
import growth.screen.screens.MenuScreen;
import growth.screen.screens.Screen;
import growth.inputs.KeyboardManager;
import growth.inputs.MouseManager;
import growth.sound.SoundManager;
import growth.util.TextManager;
import growth.util.XmlReader;

/**
 * ScreenManager class.
 * This class is the screenManager class used to run the game screens.
 *
 * @author MightyCode
 * @version 1.0
 */
public class GameManager {

    /**
     * Current screen.
     * This variable contains the current screen displayed.
     */
    private Screen currentScreen;

    /**
     * Screen's states.
     * These static final variable counting the different state of screen.
     */
    public static final int MENUSCREEN = 0;
    public static final int GAMESCREEN = 1;

    public static TextManager textManager;
    public static SoundManager soundManager;

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
    public GameManager(int[][] input) {
        // Instance of the different manager
        textManager = new TextManager();
        soundManager = new SoundManager();
        inputsManager = new InputManager(input);
        keyboardManager = new KeyboardManager();
        mouseManager = new MouseManager();

        // Load the fist screen
        currentScreen = (new MenuScreen(this));
    }

    /**
     * Update the current screen.
     */
    public void update() {
        currentScreen.update();
    }

    /**
     * Display the current screen.
     */
    public void display() {
        currentScreen.display();
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
                currentScreen = (new MenuScreen(this));
                break;
            case GAMESCREEN:
                currentScreen = (new GameScreen(this));
                break;
        }
    }

    /**
     * Unload the currentScreen.
     */
    private void currentScreenUnload() {
        currentScreen.unload();
    }

    public void focus(boolean b){ currentScreen.focus(b);}

    /**
     * Unload the game before leaving.
     */
    public void unload() {
        currentScreen.unload();
        currentScreen = null;
        StaticFonts.unload();
    }
}