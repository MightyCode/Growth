package growth.utils.button;

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
    protected int sizeX, sizeY;

    /**
     * Input position x and y.
     * This variable contains the input position x and y use to rendering the input.
     */
    protected int posX, posY;

    protected Screen screen;

    protected Overlay overlay;

    /**
     * Input abstract class constructor.
     * Instance the class and set the basic variables.
     *
     * @param posX Position x of the input.
     * @param posY Position y of the input.
     * @param sizeX Size x of the input.
     * @param sizeY Size y of the input.
     *
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
     *
     */
    AbstractInput(double posX, double posY, double sizeX, double sizeY, Overlay overlay){
        this.sizeX = (int)sizeX;
        this.sizeY = (int)sizeY;
        this.posX = (int)posX;
        this.posY = (int)posY;
        this.overlay = overlay;
    }
}
