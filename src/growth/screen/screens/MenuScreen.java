package growth.screen.screens;

import growth.main.Config;
import growth.main.Growth;
import growth.screen.overlay.Overlay;
import growth.util.Party;
import growth.util.TextManager;
import growth.util.math.Color4;
import growth.util.math.Vec2;
import growth.screen.render.Render;
import growth.screen.render.text.FontRenderer;
import growth.screen.render.text.StaticFonts;
import growth.screen.render.texture.Texture;
import growth.screen.render.texture.TextureRenderer;
import growth.screen.GameManager;
import growth.entity.gui.GUIButton;
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
    private FontRenderer title, admin;

    /**
     * GUIButtons used on the menu.
     */
    private GUIButton goToGame, newGame, chargeGame, options, quit;

    private Texture background;

    /**
     * Menu screen class constructor.
     * Instance the menu and set the menu screen's variables.
     */
    public MenuScreen() {
        super();

        // Load the screen
        Render.setClearColor(1f, 1f);

        background = new Texture();
        background.load("/textures/menu/bg.png");

        title = new FontRenderer(TextManager.MENU,0, StaticFonts.monofonto, Window.width*0.06f,
                new Vec2(Window.width * 0.5f, Window.height * 0.13f), Color4.BLACK);

        if(Growth.admin) admin = new FontRenderer("Mode admin", StaticFonts.monofonto, Window.width*0.02f,
                new Vec2(Window.width * 0.5f, Window.height * 0.90f), new Color4(0.2f,0.2f,0.2f,0.9f));

        else admin = new FontRenderer("", StaticFonts.monofonto, 0,
                new Vec2(0, 0), new Color4(0,0,0,0));

        Vec2 size = new Vec2(Window.width / 4f, Window.height / 20f);
        Color4 backgroundColor = new Color4(0.0f, 0.0f, 0.0f, 0.0f);
        Color4 hoverColor = new Color4(0.0f, 0.0f, 0.0f, 0.2f);
        Color4 textColor = new Color4(0.2f, 0.2f, 0.2f, 1.0f);
        Color4 hoverTextColor = Color4.BLACK;

        goToGame = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.40f),
                size,
                TextManager.MENU,1,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ) {
            @Override
            public void action() {
                Party.checkParty();
            }
        };

        if(Window.config.getValue(Config.PARTY_NB).equals( "-1")){
            goToGame.setLock(true);
        }

        chargeGame = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.48f),
                size,
                TextManager.MENU,2,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        );

        newGame = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.56f),
                size,
                TextManager.MENU,3,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ){
            @Override
            public void action() {
                Party.createParty();
                GameManager.setScreen(GameManager.GAMESCREEN);
            }
        };

        options = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.64f),
                size,
                TextManager.MENU,4,
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ) {
            @Override
            public void action() {
                GameManager.setState(1);
            }
        };

        quit = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.72f),
                size,
                TextManager.MENU,5,
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
        GameManager.camera.setPosition(0,0,false);
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
                currentOverlay.update();
                break;
        }
    }

    /**
     * Display the menu.
     */
    public void display() {
            if(screenState == 0){
                Render.clear();
                background.bind();
                TextureRenderer.imageC(0, 0, Window.width, Window.height);
                title.render();
                admin.render();
                goToGame.display();
                newGame.display();
                chargeGame.display();
                options.display();
                quit.display();
            }
            else
            currentOverlay.display();
    }

    @Override
    public void setState(int newState){
        currentOverlay.unload();
        super.setState(newState);
        if(screenState == 0){
            currentOverlay = new Overlay();
        } else {
            currentOverlay = new OptionOverlay() {
                @Override
                public void quit() {
                    GameManager.setState(0);
                }
            };
        }
    }

    /**
     * Unload resources in menu to free memory.
     */
    public void unload() {
        currentOverlay.unload();
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
    }
}