package growth.screen.screens;

import growth.screen.ScreenManager;

/**
 * Screen class.
 * This class is the basic architecture of all screens.
 *
 * @author MightyCode
 * @version 1.0
 */
public abstract class Screen {

    /**
     * ScreenManger.
     * This variable contains the screen manager to interact with it.
     */
    final ScreenManager screenManager;

    /**
     * Screen state.
     * This variable contains the different states of game.
     */
    protected int state;

    /**
     * Screen class constructor.
     * Instance the class and set the screenManager reference.
     *
     * @param screenManager Reference of screenManager.
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

    /**
     * Base architecture of setting screen method
     */
    public void setScreen(int newScreen){
        screenManager.setScreen(newScreen);
    }

    /**
     * Base architecture of setting state method
     */
    public void setState(int newState){
        state = newState;
    }
}