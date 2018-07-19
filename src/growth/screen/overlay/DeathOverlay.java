package growth.screen.overlay;

import growth.main.Window;
import growth.util.math.Color4;
import growth.util.math.Vec2;
import growth.render.gui.GUIButton;
import growth.render.shape.ShapeRenderer;
import growth.render.text.FontRenderer;
import growth.render.text.StaticFonts;
import growth.screen.GameManager;
import growth.screen.screens.GameScreen;
import growth.screen.screens.Screen;

/**
 * Death Overlay class.
 * This class is the death overlay class used when the player die.
 *
 * @author MightyCode
 * @version 1.1
 */
public class DeathOverlay extends Overlay {

    /**
     * Lose title texture.
     * This variable contains the texture's "title"  of the overlay.
     */
    private FontRenderer loose;

    /**
     * GUIButtons.
     */
    private GUIButton retry, quitter;

    /**
     * Death overlay class constructor.
     * Instance the class and set overlay's variables.
     */
    public DeathOverlay(Screen screen) {
        super(screen);

        // Init variables
        // Title
        loose = new FontRenderer(10, StaticFonts.IBM, 60, new Vec2(), Color4.WHITE);
        loose.setPos(new Vec2(Window.width * 0.5f,Window.height * 0.20f));

        Vec2 size = new Vec2(350, 40);
        Color4 backgroundColor = new Color4(1.0f, 0.0f, 1.0f, 0.5f);
        Color4 hoverColor = new Color4(1.0f, 0.0f, 1.0f, 1.0f);
        Color4 textColor = new Color4(0.8f, 0.8f, 0.8f, 1.0f);
        Color4 hoverTextColor = Color4.WHITE;

        retry = new GUIButton(
                new Vec2(Window.width / 2, 300),
                size,
                11,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ) {
            @Override
            public void action() {
                Window.gameManager.setScreen(GameManager.GAMESCREEN);
            }
        };

        quitter = new GUIButton(
                new Vec2(Window.width / 2, 375),
                size,
                12,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ) {
            @Override
            public void action() {
                Screen.setState(GameScreen.STATE_NORMAL);
                Window.gameManager.setScreen(GameManager.MENUSCREEN);
            }
        };
    }

    /**
     * Update the overlay and its components.
     */
    public void update() {
        retry.update();
        quitter.update();
    }

    /**
     * Display the overlay.
     */
    public void display() {
        // Black rectangle
        ShapeRenderer.rectC(new Vec2(), new Vec2(Window.width, Window.height), new Color4(0.0f, 0.0f, 0.0f, 0.6f));
        ShapeRenderer.rectC(new Vec2(0.1f * Window.width, 0.15f * Window.height),
                new Vec2(0.8f * Window.width, 0.75f * Window.height), new Color4(0.0f, 0.0f, 0.0f, 0.5f));

        // Textures and button
        loose.render();

        retry.display();
        quitter.display();
    }

    /**
     * Unload the overlay.
     */
    public void unload() {
        retry.unload();
        quitter.unload();
        loose.unload();
    }
}