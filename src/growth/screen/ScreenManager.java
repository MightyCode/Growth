package growth.screen;

import growth.screen.screens.GameScreen;
import growth.screen.screens.MenuScreen;
import growth.screen.screens.Screen;
import growth.utils.KeyboardManager;
import growth.utils.MouseManager;

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

    /**
     * Window ID.
     * This variable contains the window ID of our game.
     */

    public static final KeyboardManager KEY = new KeyboardManager();
    public static final MouseManager MOUSE = new MouseManager();

    /**
     * ScreenManager class constructor.
     * Instance the class and set the current screen.
     */
    public ScreenManager() {
        ActualScreen = (new MenuScreen(this));
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

    /**
     * Unload the game before leaving.
     */
    public void unload() {
        ActualScreen.unload();
        ActualScreen = null;
    }
}
