package growth.screen.screens;

import growth.main.Window;
import growth.math.Color4;
import growth.math.Vec2;
import growth.render.Render;
import growth.render.gui.GUIButton;
import growth.render.text.StaticFonts;
import growth.render.texture.Texture;
import growth.render.texture.TextureRenderer;
import growth.screen.ScreenManager;
import growth.util.XmlReader;

public class OptionScreen extends Screen{

    private int state;

    private final Texture option;

    private GUIButton test;

    private Texture background;

    /**
     * MenuScreen class constructor.
     * Instance the class and set all of the MenuScreen's variables.
     *
     * @param screenManager Add screenManager to change the global screen.
     */
    public OptionScreen(ScreenManager screenManager) {
        super(screenManager);

        option = new Texture("/textures/menu/Option_title2.png");

        Vec2 size = new Vec2(350, 35);
        Color4 backgroundColor = new Color4(0.0f, 0.0f, 0.0f, 0.0f);
        Color4 hoverColor = new Color4(0.0f, 0.0f, 0.0f, 0.2f);
        Color4 textColor = new Color4(0.2f, 0.2f, 0.2f, 1.0f);
        Color4 hoverTextColor = Color4.BLACK;

        test = new GUIButton(
                new Vec2(Window.width / 2, 300),
                size,
                "Change the fullscreen",
                StaticFonts.monofonto,
                backgroundColor,
                hoverColor,
                textColor,
                hoverTextColor
        ){
            @Override
            public void action () {
                XmlReader.changeValue("/config/config.xml","window","fullscreen","0");
                System.out.println(XmlReader.getValue("/config/config.xml","window","fullscreen"));
            }
        };

        background = new Texture();
        background.load("/textures/menu/bg.png");
    }

    /**
     * Update the menu.
     */
    public void update() {
        if(ScreenManager.inputsManager.inputPressed(0)) {
            screenManager.setScreen(ScreenManager.MENUSCREEN);
        }
        test.update();
    }

    /**
     * Display the menu.
     */
    public void display() {
        Render.clear();

        background.bind();
        TextureRenderer.imageC(0, 0, Window.width, Window.height);

        TextureRenderer.imageC( Window.width*0.35f, Window.height * 0.02f ,
                Window.width*0.30f,Window.height*0.20f, option.getID(), 1f);
        test.display();
    }

    /**
     * Unload the textures in menu to free memory.
     */
    public void unload() {
        System.out.println("\n-------------------------- \n");
        option.unload();
        test.unload();
    }
}
