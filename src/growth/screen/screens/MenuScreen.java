package growth.screen.screens;

import growth.math.Color4;
import growth.math.Vec2;
import growth.render.Render;
import growth.render.text.FontRenderer;
import growth.render.text.StaticFonts;
import growth.render.texture.Texture;
import growth.render.texture.TextureRenderer;
import growth.screen.ScreenManager;
import growth.render.gui.GUIButton;
import growth.main.Window;
import growth.screen.overlay.OptionOverlay;

/**
 * Menu class.
 * This class is the menu screen.
 *
 * @author MightyCode
 * @version 1.0
 */
public class MenuScreen extends Screen {

    private FontRenderer title;
    private GUIButton continuer, nouvelle, charger, options, quitter;

    private OptionOverlay option;

    private Texture background;

    public MenuScreen(ScreenManager screenManager) {
        super(screenManager);

        // Load the screen
        Render.setClearColor(1f, 1f);

        background = new Texture();
        background.load("/textures/menu/bg.png");

        title = new FontRenderer(ScreenManager.getWord(0), StaticFonts.IBM, 100, new Vec2(), Color4.BLACK);
        title.setPos(new Vec2(Window.width / 2 - title.getWidth() / 2, 100));

        Vec2 size = new Vec2(Window.width/4, Window.height/20);
        Color4 backgroundColor = new Color4(0.0f, 0.0f, 0.0f, 0.0f);
        Color4 hoverColor = new Color4(0.0f, 0.0f, 0.0f, 0.2f);
        Color4 textColor = new Color4(0.2f, 0.2f, 0.2f, 1.0f);
        Color4 hoverTextColor = Color4.BLACK;

        continuer = new GUIButton(
                new Vec2(Window.width / 2, 300),
                size,
                ScreenManager.getWord(1),
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

        nouvelle = new GUIButton(
                new Vec2(Window.width / 2, 350),
                size,
                ScreenManager.getWord(2),
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

        charger = new GUIButton(
                new Vec2(Window.width / 2, 400),
                size,
                ScreenManager.getWord(3),
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        );

        options = new GUIButton(
                new Vec2(Window.width / 2, 450),
                size,
                ScreenManager.getWord(4),
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ){
            @Override
            public void action () {
                state = 1;
            }
        };

        quitter = new GUIButton(
                new Vec2(Window.width / 2, 500),
                size,
                ScreenManager.getWord(5),
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ){
            @Override
            public void action () {
                Window.exit();
            }
        };

        // Load the option Overlay
        option = new OptionOverlay(this){
            @Override
            public void quit () {
                screen.setState(0);
            }
        };
    }

    /**
     * Update the menu.
     */
    public void update() {
        switch (state){
            case 0:
                continuer.update();
                nouvelle.update();
                charger.update();
                options.update();
                quitter.update();
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
        switch(state){
            case 0:
                Render.clear();

                TextureRenderer.imageC(0, 0, Window.width, Window.height, background.getID(),1f);

                title.render();

                continuer.display();
                nouvelle.display();
                charger.display();
                options.display();
                quitter.display();
                break;
            case 1:
                option.display();
                break;
        }
    }

    /**
     * Unload the textures in menu to free memory.
     */
    public void unload(){
        // Unload the background
        background.unload();

        // Unload buttons
        continuer.unload();
        nouvelle.unload();
        charger.unload();
        options.unload();
        quitter.unload();

        // Unload the overlay
        option.unload();
    }
}