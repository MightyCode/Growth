package growth.screen.overlay;

import growth.main.Window;
import growth.render.shape.ShapeRenderer;
import growth.render.texture.Texture;
import growth.render.texture.TextureRenderer;
import growth.screen.screens.GameScreen;
import growth.screen.screens.Screen;
import growth.screen.ScreenManager;
import growth.utils.button.ClickButton;

/**
 * Pause Overlay class.
 * This class is the pause overlay class use in the game.
 *
 * @author MightyCode
 * @version 1.0
 */
public class PauseOverlay extends Overlay{

    /**
     * Pause title texture.
     * This variable contains the texture's "title"  of the overlay.
     */
    private final Texture pause;

    /**
     * Click buttons.
     * These variables contain buttons to make the overlay work.
     */
    private final ClickButton resume;
    private final ClickButton menu;

    /**
     * Pause overlay class constructor.
     * Instance the class and set overlay's variables.
     */
    public PauseOverlay(Screen screen){
        super(screen);
        // Init variable

        // Title
        pause = new Texture("/images/menu/Pause.png");

        // Buttons
        resume = new ClickButton(Window.WIDTH*0.5,Window.HEIGHT*0.5,Window.WIDTH*0.12,Window.HEIGHT*0.11,"Continue",this){
            @Override
            public void action(){
                overlay.setState(GameScreen.NORMALSCREEN);
            }
        };
        menu = new ClickButton(Window.WIDTH*0.5,Window.HEIGHT*0.75,Window.WIDTH*0.15,Window.HEIGHT*0.11,"Return",this){
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
        resume.update();
        menu.update();
        if(ScreenManager.KEY.keyPressed(0)) {
            screen.setState(GameScreen.NORMALSCREEN);
        }
    }

    /**
     * Update the overlay.
     */
    public void display(){
        // Black rectangle
        ShapeRenderer.rectC(0, 0, Window.WIDTH, Window.HEIGHT,0, (float)0.6);
        ShapeRenderer.rectC(Window.WIDTH*0.1f, Window.HEIGHT*0.15f, Window.WIDTH*0.8f, Window.HEIGHT*0.75f ,0, 0.5f);

        // Textures and button
        TextureRenderer.imageC(Window.WIDTH*0.40f,Window.HEIGHT*0.05f,Window.WIDTH*0.2f,Window.HEIGHT*0.09f, pause.getID(), 1,1f);

        resume.displayC();
        menu.displayC();
    }

    public void unload(){
        resume.unload();
        menu.unload();
        pause.unload();
    }
}