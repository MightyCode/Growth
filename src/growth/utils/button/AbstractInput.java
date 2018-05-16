package growth.utils.button;

import growth.screen.Screen;

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

    protected final Screen screen;

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
    AbstractInput(int posX, int posY, int sizeX, int sizeY, Screen screen){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.posX = posX;
        this.posY = posY;
        this.screen = screen;
    }
}
