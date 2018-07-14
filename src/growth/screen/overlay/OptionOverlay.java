package growth.screen.overlay;

import growth.main.Config;
import growth.main.Window;
import growth.math.Color4;
import growth.math.Vec2;
import growth.render.Render;
import growth.render.gui.GUICheckBox;
import growth.render.text.StaticFonts;
import growth.render.texture.Texture;
import growth.render.texture.TextureRenderer;
import growth.screen.ScreenManager;
import growth.screen.screens.Screen;
import growth.util.XmlReader;

public class OptionOverlay extends Overlay {

    private final Texture option;
    private GUICheckBox fullscreen, language;
    private Texture background;

    public OptionOverlay(Screen screen){
        super(screen);

        background = new Texture("/textures/menu/bg.png");
        option = new Texture("/textures/menu/Option_title2.png");

        Vec2 size = new Vec2(Window.width*0.27f, Window.height*0.032f);
        Color4 backgroundColor = new Color4(0.0f, 0.0f, 0.0f, 0.0f);
        Color4 hoverColor = new Color4(0.0f, 0.0f, 0.0f, 0.2f);
        Color4 textColor = new Color4(0.2f, 0.2f, 0.2f, 1.0f);
        Color4 hoverTextColor = Color4.BLACK;

        fullscreen = new GUICheckBox(
                new Vec2(Window.width*0.5f, Window.height*0.41f),
                new Vec2(Window.width*0.125f, Window.height*0.1f),
                ScreenManager.getWord(13),
                StaticFonts.monofonto,
                textColor,
                hoverTextColor
        ){
            @Override
            public void action () {
                if(state == 0){
                    XmlReader.changeValue(Config.CONFIG_PATH, "fullscreen","0","window");
                } else{
                    XmlReader.changeValue(Config.CONFIG_PATH, "fullscreen","1","window");
                    
                }
            }
        };

        fullscreen.setState(Window.config.getFullscreen());

        language = new GUICheckBox(
                new Vec2(Window.width*0.5f, Window.height*0.55f),
                new Vec2(Window.width*0.125f, Window.height*0.1f),
                ScreenManager.getWord(14),
                StaticFonts.monofonto,
                textColor,
                hoverTextColor
        ){
            @Override
            public void action () {
                if(state == 0){
                    XmlReader.changeValue(Config.CONFIG_PATH, "language","fr","general");
                } else{
                    XmlReader.changeValue(Config.CONFIG_PATH, "language","en","general");
                }
            }
        };

        language.setState(Window.config.getLanguage().equals("en"));
    }

    public void update() {
        if(ScreenManager.inputsManager.inputPressed(0)) {
            quit();
        }

        fullscreen.update();
        language.update();
    }


    public void display() {
        Render.clear();
        background.bind();
        TextureRenderer.imageC(0, 0, Window.width, Window.height);
        option.bind();
        TextureRenderer.imageC( Window.width*0.35f, Window.height * 0.02f , Window.width*0.30f,Window.height*0.20f);
        fullscreen.display();
        language.display();
    }

    public void quit(){
    }

    public void unload() {
        System.out.println("\n-------------------------- \n");
        option.unload();
        fullscreen.unload();
        language.unload();
        background.unload();
    }
}
