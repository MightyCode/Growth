package growth.screen.overlay;

import growth.main.Window;
import growth.screen.screens.Screen;

/**
 * Overlay abstract class.
 * This class is the overlay class use on screen.
 *
 * @author MightyCode
 * @version 1.0
 */
public abstract class Overlay {

    /**
     * Screen.
     * This variable contains the screen which have instance this overlay.
     */
    Screen screen;

    /**
     * Overlay abstract class constructor.
     * Instance the child class and set overlay's child variables.
     */
    Overlay(Screen screen) {
        this.screen = screen;
    }

    /**
     * Update the overlay.
     */
    public void update(){ }

    /**
     * Update the overlay.
     */
    public void display(){}

    /**
     * Set the screen.
     */
    public void setScreen(int newScreen){
        Window.SCREENMANAGER.setScreen(newScreen);
    }

    /**
     * Set the state of the screen which have instance the overlay.
     */
    public void setState(int newsState){
        screen.setState(newsState);
    }

    /**
     * Free memory.
     */
    public void unload(){ }
}