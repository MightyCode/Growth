package growth.render.gui;

import growth.screen.overlay.Overlay;
import growth.screen.screens.Screen;

/**
 * Basic abstract input class.
 * This class is the structure of the different input's child.
 *
 * @author MightyCode
 * @version 1.0
 */
public abstract class AbstractInput {
    /**
     * Input size x and y.
     * This variable contains the input size x and y use to rendering the input.
     */
    final float sizeX;
    final float sizeY;

    /**
     * Input position x and y.
     * This variable contains the input position x and y use to rendering the input.
     */
    final float posX;
    final float posY;

    /**
     * Screen.
     * This variable contains the screen which have instance this input.
     */
    protected Screen screen;

    /**
     * Overlay.
     * This variable contains the overlay which have instance this input.
     */
    protected Overlay overlay;

    /**
     * Input abstract class constructor.
     * Instance the class and set the basic variables.
     *
     * @param posX Position x of the input.
     * @param posY Position y of the input.
     * @param sizeX Size x of the input.
     * @param sizeY Size y of the input.
     * @param screen The screen which have instance the input.
     */
    AbstractInput(double posX, double posY, double sizeX, double sizeY, Screen screen){
        this.sizeX = (int)sizeX;
        this.sizeY = (int)sizeY;
        this.posX = (int)posX;
        this.posY = (int)posY;
        this.screen = screen;
    }

    /**
     * Input abstract class constructor.
     * Instance the class and set the basic variables.
     *
     * @param posX Position x of the input.
     * @param posY Position y of the input.
     * @param sizeX Size x of the input.
     * @param sizeY Size y of the input.
     * @param overlay The overlay which have instance the input.
     */
    AbstractInput(double posX, double posY, double sizeX, double sizeY, Overlay overlay){
        this.sizeX = (int)sizeX;
        this.sizeY = (int)sizeY;
        this.posX = (int)posX;
        this.posY = (int)posY;
        this.overlay = overlay;
    }
}