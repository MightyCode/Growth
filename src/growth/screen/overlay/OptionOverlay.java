package growth.screen.overlay;

import growth.main.Config;
import growth.main.Window;
import growth.render.gui.GUIButton;
import growth.util.math.Color4;
import growth.util.math.Vec2;
import growth.render.Render;
import growth.render.gui.GUICheckBox;
import growth.render.text.StaticFonts;
import growth.render.texture.Texture;
import growth.render.texture.TextureRenderer;
import growth.screen.GameManager;
import growth.screen.screens.Screen;
import growth.util.XmlReader;

public class OptionOverlay extends Overlay {

    private Texture option, background;

    // Button to choose the categories
    public GUIButton general, video, inputs;
    // Button for general
    private GUICheckBox fullscreen, language;
    // Button for video

    // Button for control

    protected OptionOverlay(Screen screen){
        super(screen);

        background = new Texture("/textures/menu/bg.png");
        option = new Texture("/textures/menu/Option_title2.png");

        Vec2 size = new Vec2(Window.width / 4f, Window.height / 20f);
        Color4 backgroundColor = new Color4(0.0f, 0.0f, 0.0f, 0.0f);
        Color4 hoverColor = new Color4(0.0f, 0.0f, 0.0f, 0.2f);
        Color4 textColor = new Color4(0.2f, 0.2f, 0.2f, 1.0f);
        Color4 hoverTextColor = Color4.BLACK;

        general = new GUIButton(
                new Vec2(Window.width * 0.2f, Window.height * 0.3f),
                size, 13, StaticFonts.monofonto, backgroundColor, hoverColor, textColor, hoverTextColor
        ) {
            @Override public void action() {
                System.out.println(lock);
                lock = true;
                video.setLock(false);
                inputs.setLock(false);
                Overlay.setState(0);
            }
        };

        video = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.3f),
                size, 14, StaticFonts.monofonto, backgroundColor, hoverColor, textColor, hoverTextColor
        ) {
            @Override public void action() {
                lock = true;
                general.setLock(false);
                inputs.setLock(false);
                Overlay.setState(1);
            }
        };

        inputs = new GUIButton(
                new Vec2(Window.width * 0.8f, Window.height * 0.3f),
                size, 15, StaticFonts.monofonto, backgroundColor, hoverColor, textColor, hoverTextColor
        ) {
            @Override public void action() {
                lock = true;
                video.setLock(false);
                video.setLock(false);
                Overlay.setState(2);
            }
        };

        fullscreen = new GUICheckBox(
                new Vec2(Window.width*0.5f, Window.height*0.41f),
                new Vec2(Window.width*0.125f, Window.height*0.1f),
                16, StaticFonts.monofonto, textColor, hoverTextColor
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

        fullscreen.setState(Config.getFullscreen());

        language = new GUICheckBox(
                new Vec2(Window.width*0.5f, Window.height*0.41f),
                new Vec2(Window.width*0.125f, Window.height*0.1f),
                17, StaticFonts.monofonto, textColor, hoverTextColor
        ){
            @Override
            public void action () {
                if(state == 0){
                   GameManager.textManager.changeLanguage("fr");
                } else{
                    GameManager.textManager.changeLanguage("en");
                }
            }
        };

        language.setState(Config.getLanguage().equals("en"));
    }

    public void update() {
        if(GameManager.inputsManager.inputPressed(0)) {
            quit();
        }

        general.update();
        video.update();
        inputs.update();

        switch (state){
            case 0:
                language.update();
                break;
            case 1:
                fullscreen.update();
                break;
            case 2:
                break;
        }
    }


    public void display() {
        Render.clear();

        background.bind();
        TextureRenderer.imageC(0, 0, Window.width, Window.height);
        option.bind();
        TextureRenderer.imageC( Window.width*0.35f, Window.height * 0.02f , Window.width*0.30f,Window.height*0.20f);

        general.display();
        video.display();
        inputs.display();

        switch (state){
            case 0:
                language.display();
                break;
            case 1:
                fullscreen.display();
                break;
            case 2:
                break;
        }
    }

    public void quit(){
    }

    public void unload() {
        System.out.println("\n-------------------------- \n");
        option.unload();
        background.unload();

        general.unload();
        video.unload();
        inputs.unload();
        
        language.unload();
        
        fullscreen.unload();
        
    }
}
