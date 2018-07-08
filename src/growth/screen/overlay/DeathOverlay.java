package growth.screen.overlay;

import growth.main.Window;
import growth.math.Color4;
import growth.math.Vec2;
import growth.render.gui.GUIButton;
import growth.render.shape.ShapeRenderer;
import growth.render.text.FontRenderer;
import growth.render.text.StaticFonts;
import growth.render.texture.Texture;
import growth.render.texture.TextureRenderer;
import growth.screen.ScreenManager;
import growth.screen.screens.GameScreen;
import growth.screen.screens.Screen;

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
    private FontRenderer loose;

    private GUIButton recommencer, quitter;

    /**
     * Death overlay class constructor.
     * Instance the class and set overlay's variables.
     */
    public DeathOverlay(Screen screen){
        super(screen);

        // Init variable
        // Title
        loose = new FontRenderer("Game Over", StaticFonts.IBM, 60, new Vec2(), Color4.WHITE);
        loose.setPos(new Vec2(Window.WIDTH / 2 - loose.getWidth() / 2, 0.18f * Window.HEIGHT));

        Vec2 size = new Vec2(350, 40);
        Color4 backgroundColor = new Color4(1.0f, 0.0f, 1.0f, 0.5f);
        Color4 hoverColor = new Color4(1.0f, 0.0f, 1.0f, 1.0f);
        Color4 textColor = new Color4(0.8f, 0.8f, 0.8f, 1.0f);
        Color4 hoverTextColor = Color4.WHITE;

        recommencer = new GUIButton(
                new Vec2(Window.WIDTH / 2, 300),
                size,
                "Recommencer",
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ){
            @Override
            public void action () {
                Window.screenManager.setScreen(ScreenManager.GAMESCREEN);
            }
        };

        quitter = new GUIButton(
                new Vec2(Window.WIDTH / 2, 375),
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
        recommencer.update();
        quitter.update();
    }

    /**
     * Update the overlay.
     */
    public void display(){
        // Black rectangle
        ShapeRenderer.rectC(new Vec2(), new Vec2(Window.WIDTH, Window.HEIGHT), new Color4(0.0f, 0.0f, 0.0f, 0.6f));
        ShapeRenderer.rectC(new Vec2(0.1f * Window.WIDTH, 0.15f * Window.HEIGHT), new Vec2(0.8f * Window.WIDTH, 0.75f * Window.HEIGHT), new Color4(0.0f, 0.0f, 0.0f, 0.5f));

        // Textures and button
        loose.render();

        recommencer.display();
        quitter.display();
    }

    public void unload(){}
}