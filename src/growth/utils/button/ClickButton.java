package growth.utils.button;

import growth.render.texture.Texture;
import growth.render.texture.TextureRenderer;
import growth.screen.ScreenManager;
import growth.screen.overlay.Overlay;
import growth.screen.screens.Screen;
import growth.utils.MouseManager;

import static org.lwjgl.glfw.GLFW.*;

/**
 * ClickButton class.
 * This class is the structure of the click button.
 * The button have two state, one for mouse's hover and one for not.
 *
 * @author MightyCode
 * @version 1.0
 */
public class ClickButton extends AbstractInput {

    /**
     * Mouse position x.
     * This variable contains the detection of mouse's position x.
     */
    private float mouseX;

    /**
     * Mouse position y.
     * This variable contains the detection of mouse's position y.
     */
    private float mouseY;

    /**
     * Over size x.
     * This variable contains button's size x after mouse'hovering.
     */
    private final int overSizeX;

    /**
     * Over size y
     * This variable contains button's size y after mouse'hovering.
     */
    private final int overSizeY;

    /**
     * Texture idle.
     * This variable contains the texture of the idle's button.
     */
    private final Texture texIdle;

    /**
     * Texture over.
     * This variable contains the texture of the Over's button.
     */
    private final Texture texOver;

    /**
     * Mouse over.
     * This variable contains
     */
    private boolean mouseOver;

    /**
     * Button class constructor.
     * Instance the class and set the basic variables.
     *
     * @param posX Position x of the button.
     * @param posY Position y of the button.
     * @param sizeX Size x of the button.
     * @param sizeY Size y of the button.
     * @param name Name of the button to find the file and charge her textures.
     * @param screen The screen which have instance the input.
     */
    public ClickButton(double posX, double posY, double sizeX, double sizeY, String name, Screen screen) {
        // Call the mother class
        super(posX, posY, sizeX, sizeY, screen);

        // Set the button's variables
        // Size
        overSizeX = (int)(sizeX*1.1);
        overSizeY = (int)( sizeY*1.1);
        // Textures

        texIdle = new Texture("/images/menu/" + name + ".png");
        texOver= new Texture("/images/menu/" + name + "-hover.png");
        // Mouse interaction class
    }

    /**
     * Button class constructor.
     * Instance the class and set the basic variables.
     *
     * @param posX Position x of the button.
     * @param posY Position y of the button.
     * @param sizeX Size x of the button.
     * @param sizeY Size y of the button.
     * @param name Name of the button to find the file and charge her textures.
     * @param overlay The overlay which have instance the input.
     */
    public ClickButton(double posX, double posY, double sizeX, double sizeY, String name, Overlay overlay) {
        // Call the mother class
        super(posX, posY, sizeX, sizeY, overlay);

        // Set the button's variables
        // Size
        overSizeX = (int)(sizeX*1.1);
        overSizeY = (int)( sizeY*1.1);
        // Textures

        texIdle = new Texture("/images/menu/" + name + ".png");
        texOver= new Texture("/images/menu/" + name + "-hover.png");
        // Mouse interaction class
    }

    /**
     * Test if the mouse is over the button.
     */
    private boolean mouseOver() {
        return (mouseX > posX - sizeX/2 && mouseX < posX + sizeX/2) && (mouseY > posY - sizeY/2 && mouseY < posY + sizeY/2);
    }

    /**
     * Update the button.
     */
    public void update() {
        mouseX = MouseManager.mouseX();
        mouseY = MouseManager.mouseY();
        mouseOver = mouseOver();
        if(mouseOver && ScreenManager.MOUSE.mousePressed(GLFW_MOUSE_BUTTON_LEFT)){
            action();
        }
    }

    /**
     * Display the button.
     */
    public void display() {
        if (!mouseOver) {
            TextureRenderer.image(posX - (sizeX/2), posY - (sizeY/2), sizeX, sizeY, texIdle.getID(), 0,1f);
        } else {
            TextureRenderer.image(posX - overSizeX/2, posY - overSizeY/2, overSizeX, overSizeY, texOver.getID(), 0,1f);
        }
    }

    /**
     * Display the button.
     */
    public void displayC() {
        if (!mouseOver) {
            TextureRenderer.imageC(posX - (sizeX/2), posY - (sizeY/2), sizeX, sizeY, texIdle.getID(), 1, 1f);
        } else {
            TextureRenderer.imageC(posX - overSizeX/2, posY - overSizeY/2, overSizeX, overSizeY, texOver.getID(),1, 1f);
        }
    }

    /**
     * Override class for what does the button.
     */
    protected void action(){ }

    /**
     * Free the memory.
     */
    public void unload(){
        texOver.unload();
        texIdle.unload();
    }
}