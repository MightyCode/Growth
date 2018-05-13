package growth.game.screen;

/**
 * Screen class.
 * This class is the basic architecture of all screens.
 *
 * @author MightyCode
 * @version 1.0
 */
abstract class Screen {

    /**
     * ScreenManger.
     * This variable contains the screen manager to interact with it.
     */
    final ScreenManager screenManager;

    /**
     * Screen class constructor.
     * Instance the class and set the screenManager reference.
     *
     * @param screenManager reference of screenManager.
     */
    Screen(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    /**
     * Base architecture of displaying method
     */
    public void display() {
    }

    /**
     * Base architecture of updating method
     */
    public void update() {
    }

    /**
     * Base architecture of unloading method
     */
    public void unload() {
    }
}
