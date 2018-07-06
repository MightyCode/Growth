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

    private Texture bg;

    public MenuScreen(ScreenManager screenManager) {
        super(screenManager);
        Render.setClearColor(1f, 1f);

        bg = new Texture();
        bg.load("/textures/menu/bg.png");

        title = new FontRenderer("Growth", StaticFonts.IBM, 100, new Vec2(), Color4.BLACK);
        title.setPos(new Vec2(Window.WIDTH / 2 - title.getWidth() / 2, 100));

        Vec2 size = new Vec2(350, 35);
        Color4 backgroundColor = new Color4(0.0f, 0.0f, 0.0f, 0.0f);
        Color4 hoverColor = new Color4(0.0f, 0.0f, 0.0f, 0.2f);
        Color4 textColor = new Color4(0.2f, 0.2f, 0.2f, 1.0f);
        Color4 hoverTextColor = Color4.BLACK;

        continuer = new GUIButton(
                new Vec2(Window.WIDTH / 2, 300),
                size,
                "Continuer la partie",
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
                new Vec2(Window.WIDTH / 2, 350),
                size,
                "Nouvelle partie",
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        );

        charger = new GUIButton(
                new Vec2(Window.WIDTH / 2, 400),
                size,
                "Charger une partie",
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        );

        options = new GUIButton(
                new Vec2(Window.WIDTH / 2, 450),
                size,
                "Options du jeu",
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ){
            @Override
            public void action () {
                Window.screenManager.setScreen(ScreenManager.OPTIONSCREEN);
            }
        };

        quitter = new GUIButton(
                new Vec2(Window.WIDTH / 2, 500),
                size,
                "Quitter",
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
    }

    /**
     * Update the menu.
     */
    public void update() {
        continuer.update();
        nouvelle.update();
        charger.update();
        options.update();
        quitter.update();
    }

    /**
     * Display the menu.
     */
    public void display() {
        Render.clear();

        bg.bind();
        TextureRenderer.image(0, 0, Window.WIDTH, Window.HEIGHT);

        title.render();

        continuer.display();
        nouvelle.display();
        charger.display();
        options.display();
        quitter.display();
    }

    /**
     * Unload the textures in menu to free memory.
     */
    public void unload(){
        continuer.unload();
        nouvelle.unload();
        charger.unload();
        options.unload();
        quitter.unload();
    }
}