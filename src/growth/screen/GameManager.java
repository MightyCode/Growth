package growth.screen;

import growth.inputs.InputManager;
import growth.screen.render.Camera;
import growth.screen.render.text.StaticFonts;
import growth.screen.render.texture.TextureManager;
import growth.screen.screens.GameScreen;
import growth.screen.screens.MenuScreen;
import growth.screen.screens.Screen;
import growth.inputs.KeyboardManager;
import growth.inputs.MouseManager;
import growth.sound.SoundManager;
import growth.util.TextManager;

/**
 * ScreenManager class.
 * This class is the screenManager class used to run the game screens.
 *
 * @author MightyCode
 * @version 1.1
 */
public class GameManager {

    /**
     * Current screen.
     * This variable contains the current screen displayed.
     */
    private static Screen currentScreen;

    /**
     * Screen's states.
     * These static final variable counting the different state of screen.
     */
    public static final int MENUSCREEN = 0;
    public static final int GAMESCREEN = 1;

    /**
     * Different managers of the game.
     */
    public static TextManager textManager;
    public static SoundManager soundManager;
    public static InputManager inputsManager;
    public static KeyboardManager keyboardManager;
    public static MouseManager mouseManager;
    public static TextureManager texManager;

    /**
     * The camera of the game.
     */
    public static Camera camera = new Camera(0,0);

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
        texManager = new TextureManager();

        // Load the fist screen
        currentScreen = (new MenuScreen());
    }

    /**
     * Update the current screen.
     */
    public void update() {
        if(inputsManager.inputPressed(15)) texManager.state();
        currentScreen.update();
        inputsManager.dispose();
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
    public static void setScreen(int screen) {
        currentScreenUnload();
        currentScreen = null;
        System.runFinalization();
        switch (screen) {
            case MENUSCREEN:
                currentScreen = new MenuScreen();
                break;
            case GAMESCREEN:
                currentScreen = new GameScreen();
                break;
            default:
                currentScreen = new MenuScreen();
                    break;
        }
    }

    public static void setState(int state) { currentScreen.setState(state); }

    public static Screen getScreen(){
        return currentScreen;
    }

    /**
     * Unload the currentScreen.
     */
    private static void currentScreenUnload() {
        currentScreen.unload();
    }

    /**
     * Method call when the focus of the game change.
     */
    public void focus(boolean b){ currentScreen.focus(b);}

    /**
     * Unload the game before leaving.
     */
    public void unload() {
        currentScreen.unload();
        currentScreen = null;
        StaticFonts.unload();
        soundManager.unload();
        texManager.endState();
    }
}