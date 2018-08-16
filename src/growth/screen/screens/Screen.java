package growth.screen.screens;

import growth.screen.GameManager;
import growth.screen.overlay.Overlay;

/**
 * Screen class.
 * This class is the basic architecture of all screens.
 *
 * @author MightyCode
 * @version 1.1
 */
public abstract class Screen {

    protected static Overlay currentOverlay;

    /**
     * Screen state.
     * This variable contains the different states of game.
     */
    protected int screenState;

    /**
     * Screen class constructor.
     * Instance the class and set the screenManager reference.
     */
    public Screen() {
        screenState = 0;
        currentOverlay = new Overlay();
    }

    /**
     * Base architecture of displaying method
     */
    public abstract void display();
    /**
     * Base architecture of updating method
     */
    public abstract void update();

    /**
     * Base architecture of setting screen method
     */
    public void setScreen(int newScreen){
        GameManager.setScreen(newScreen);
    }

    /**
     * Base architecture of setting state method
     */
    public void setState(int newState){
        screenState = newState;
    }

    public static void setOverlayState(int newState){
        currentOverlay.setState(newState);
    }

    /**
     * Call the screen if the focus of the window change.
     * @param b The focus.
     */
    public void focus(boolean b){}

    /**
     * Base architecture of unloading method
     */
    public void unload() {
    }
}