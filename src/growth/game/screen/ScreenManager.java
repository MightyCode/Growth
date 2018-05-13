package growth.game.screen;

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
    private final long windowID;

    /**
     * ScreenManager class constructor.
     * Instance the class and set the current screen.
     *
     * @param windowID init the window's variable.
     */
    public ScreenManager(long windowID) {
        this.windowID = windowID;
        ActualScreen = (new GameScreen(this));
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
     * @param screen set the new current screen.
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
     * Get the window id.
     *
     * @return windowID
     */
    long getWindowID() {
        return windowID;
    }

    /**
     * Unload the currentScreen.
     */
    public void currentScreenUnload() {
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
