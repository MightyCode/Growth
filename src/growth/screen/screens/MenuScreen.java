package growth.screen.screens;

import growth.util.math.Color4;
import growth.util.math.Vec2;
import growth.render.Render;
import growth.render.text.FontRenderer;
import growth.render.text.StaticFonts;
import growth.render.texture.Texture;
import growth.render.texture.TextureRenderer;
import growth.screen.GameManager;
import growth.render.gui.GUIButton;
import growth.main.Window;
import growth.screen.overlay.OptionOverlay;

/**
 * Menu class.
 * This class is the menu screen.
 *
 * @author MightyCode
 * @version 1.1
 */
public class MenuScreen extends Screen {

    /**
     * The font renderer for the menu title.
     */
    private FontRenderer title;

    /**
     * GUIButtons used on the menu.
     */
    private GUIButton goToGame, newGame, chargeGame, options, quit;

    /**
     * The option overlay to change the current parameters.
     */
    private OptionOverlay option;

    private Texture background;

    /**
     * Menu screen class constructor.
     * Instance the menu and set the menu screen's variables.
     *
     * @param gameManager Add gameManager to change the global screen.
     */
    public MenuScreen(GameManager gameManager) {
        super(gameManager);
        // Load the screen
        Render.setClearColor(1f, 1f);

        background = new Texture();
        background.load("/textures/menu/bg.png");

        title = new FontRenderer(0, StaticFonts.IBM, 100,
                new Vec2(Window.width / 2f, Window.height / 7f), Color4.BLACK);

        Vec2 size = new Vec2(Window.width / 4f, Window.height / 20f);
        Color4 backgroundColor = new Color4(0.0f, 0.0f, 0.0f, 0.0f);
        Color4 hoverColor = new Color4(0.0f, 0.0f, 0.0f, 0.2f);
        Color4 textColor = new Color4(0.2f, 0.2f, 0.2f, 1.0f);
        Color4 hoverTextColor = Color4.BLACK;

        goToGame = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.5f),
                size,
                1,
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

        newGame = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.58f),
                size,
                2,
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

        chargeGame = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.66f),
                size,
                3,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        );

        options = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.74f),
                size,
                4,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ) {
            @Override
            public void action() {
                MenuScreen.setState(1);
            }
        };

        quit = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.82f),
                size,
                5,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ) {
            @Override
            public void action() {
                Window.exit();
            }
        };

        // Load the option Overlay
        option = new OptionOverlay(this) {
            @Override
            public void quit() {
                Screen.setState(0);
            }
        };
    }

    /**
     * Update the menu.
     */
    public void update() {
        switch (screenState) {
            case 0:
                goToGame.update();
                newGame.update();
                chargeGame.update();
                options.update();
                quit.update();
                break;
            case 1:
                option.update();
                break;
        }

    }

    /**
     * Display the menu.
     */
    public void display() {
        switch (screenState) {
            case 0:
                Render.clear();

                background.bind();
                TextureRenderer.imageC(0, 0, Window.width, Window.height);

                title.render();

                goToGame.display();
                newGame.display();
                chargeGame.display();
                options.display();
                quit.display();
                break;
            case 1:
                option.display();
                break;
        }
    }

    /**
     * Unload resources in menu to free memory.
     */
    public void unload() {
        // Unload the background
        background.unload();

        // Unload buttons
        goToGame.unload();
        newGame.unload();
        chargeGame.unload();
        options.unload();
        quit.unload();
        title.unload();
        // Unload the overlay
        option.unload();
    }
}