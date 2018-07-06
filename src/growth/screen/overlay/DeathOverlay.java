package growth.screen.overlay;

import growth.main.Window;
import growth.render.shape.ShapeRenderer;
import growth.render.texture.Texture;
import growth.render.texture.TextureRenderer;
import growth.screen.ScreenManager;
import growth.screen.screens.Screen;
import growth.gui.ClickButton;

/**
 * Death Overlay class.
 * This class is the death overlay class used when the player die.
 *
 * @author MightyCode
 * @version 1.0
 */
public class DeathOverlay extends Overlay{

    /**
     * Lose title texture.
     * This variable contains the texture's "title"  of the overlay.
     */
    private final Texture lose;

    /**
     * Click buttons.
     * These variables contain buttons to make the overlay work.
     */
    private final ClickButton menu;
    private final ClickButton restart;

    /**
     * Death overlay class constructor.
     * Instance the class and set overlay's variables.
     */
    public DeathOverlay(Screen screen){
        super(screen);

        // Init variable
        // Title
        lose = new Texture("/textures/menu/Lose.png");

        // Buttons
        restart = new ClickButton(Window.WIDTH*0.5,Window.HEIGHT*0.45,Window.WIDTH*0.12,Window.HEIGHT*0.11,"Restart",this){
            @Override
            public void action(){
                overlay.setScreen(ScreenManager.GAMESCREEN);
            }
        };
        menu = new ClickButton(Window.WIDTH*0.5,Window.HEIGHT*0.71,Window.WIDTH*0.15,Window.HEIGHT*0.11,"Return",this){
            @Override
            public void action(){
                overlay.setScreen(ScreenManager.MENUSCREEN);
            }
        };
    }

    /**
     * Update the overlay and its components.
     */
    public void update(){
        restart.update();
        menu.update();
    }

    /**
     * Update the overlay.
     */
    public void display(){
        // Black rectangle
        ShapeRenderer.rectC(0, 0, Window.WIDTH, Window.HEIGHT,0, 0.6f);
        ShapeRenderer.rectC( Window.WIDTH*0.1f, Window.HEIGHT*0.15f, Window.WIDTH*0.8f, Window.HEIGHT*0.751f ,0, 0.5f);

        // Textures and button
        TextureRenderer.imageC(Window.WIDTH*0.40f,Window.HEIGHT*0.05f,Window.WIDTH*0.2f,Window.HEIGHT*0.09f, lose.getID(), 1, 1f);

        restart.displayC();
        menu.displayC();
    }

    public void unload(){
        restart.unload();
        menu.unload();
        lose.unload();
    }
}