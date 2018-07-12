package growth.screen.overlay;

import growth.main.Window;
import growth.math.Color4;
import growth.math.Vec2;
import growth.render.gui.GUIButton;
import growth.render.shape.ShapeRenderer;
import growth.render.text.FontRenderer;
import growth.render.text.StaticFonts;
import growth.screen.screens.GameScreen;
import growth.screen.screens.Screen;
import growth.screen.ScreenManager;

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
    private FontRenderer pause;

    private GUIButton continuer, quitter;

    /**
     * Pause overlay class constructor.
     * Instance the class and set overlay's variables.
     */
    public PauseOverlay(Screen screen){
        super(screen);
        // Init variable

        // Title
        pause = new FontRenderer("Pause", StaticFonts.IBM, 60, new Vec2(), Color4.WHITE);
        pause.setPos(new Vec2(Window.width / 2 - pause.getWidth() / 2, 0.18f * Window.height));

        Vec2 size = new Vec2(350, 40);
        Color4 backgroundColor = new Color4(1.0f, 0.0f, 1.0f, 0.5f);
        Color4 hoverColor = new Color4(1.0f, 0.0f, 1.0f, 1.0f);
        Color4 textColor = new Color4(0.8f, 0.8f, 0.8f, 1.0f);
        Color4 hoverTextColor = Color4.WHITE;

        continuer = new GUIButton(
                new Vec2(Window.width / 2, 300),
                size,
                "Continuer à jouer",
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ){
            @Override
            public void action () {
                screen.setState(GameScreen.NORMALSCREEN);
            }
        };

        quitter = new GUIButton(
                new Vec2(Window.width / 2, 375),
                size,
                "Quitter vers le menu",
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ){
            @Override
            public void action () {
                Window.screenManager.setScreen(ScreenManager.MENUSCREEN);
            }
        };
    }

    /**
     * Update the overlay and its components.
     */
    public void update(){
        if(ScreenManager.inputsManager.inputPressed(0)) {
            screen.setState(GameScreen.NORMALSCREEN);
        }

        continuer.update();
        quitter.update();
    }

    /**
     * Update the overlay.
     */
    public void display(){
        // Black rectangle
        ShapeRenderer.rectC(new Vec2(), new Vec2(Window.width, Window.height), new Color4(0.0f, 0.0f, 0.0f, 0.6f));
        ShapeRenderer.rectC(new Vec2(0.1f * Window.width, 0.15f * Window.height),
                new Vec2(0.8f * Window.width, 0.75f * Window.height), new Color4(0.0f, 0.0f, 0.0f, 0.5f));

        // Textures and button
        pause.render();

        continuer.display();
        quitter.display();
    }

    public void unload(){}
}