package growth.screen.overlay;

import growth.main.Window;
import growth.screen.GameManager;
import growth.screen.screens.Screen;

/**
 * Overlay abstract class.
 * This class is the overlay class use on screen.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Overlay {

    /**
     * The state of the overlay.
     */
    public int overlayState;

    /**
     * Overlay abstract class constructor.
     * Instance the child class and set overlay's child variables.
     */
    public Overlay() {
        overlayState = 0;
    }

    /**
     * Update the overlay.
     */
    public void update(){}

    /**
     * Update the overlay.
     */
    public void display(){}

    /**
     * Set the screen.
     */
    public void setScreen(int newScreen){
        GameManager.setScreen(newScreen);
    }

    /**
     * Change the state of the overlay.
     * @param newState The new state.
     */
    public void setState(int newState){overlayState = newState;}

    /**
     * Free memory.
     */
    public void unload(){ }
}