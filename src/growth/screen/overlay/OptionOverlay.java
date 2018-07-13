package growth.screen.overlay;

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
    private GUICheckBox test;
    private Texture background;

    public OptionOverlay(Screen screen){
        super(screen);

        background = new Texture("/textures/menu/bg.png");
        option = new Texture("/textures/menu/Option_title2.png");

        Vec2 size = new Vec2(350, 35);
        Color4 backgroundColor = new Color4(0.0f, 0.0f, 0.0f, 0.0f);
        Color4 hoverColor = new Color4(0.0f, 0.0f, 0.0f, 0.2f);
        Color4 textColor = new Color4(0.2f, 0.2f, 0.2f, 1.0f);
        Color4 hoverTextColor = Color4.BLACK;

        test = new GUICheckBox(
                new Vec2(Window.width / 2, 300),
                size,
                "Langue française",
                StaticFonts.monofonto,
                textColor,
                hoverTextColor
        ){
            @Override
            public void action () {
                if(this.state){
                    XmlReader.changeValue("/config/config.xml", "general","language","fr");
                } else{
                    XmlReader.changeValue("/config/config.xml", "general","language","en");
                }
            }
        };
    }

    public void update() {
        if(ScreenManager.inputsManager.inputPressed(0)) {
            quit();
        }
        test.update();
    }


    public void display() {
        Render.clear();
        background.bind();
        TextureRenderer.imageC(0, 0, Window.width, Window.height);
        option.bind();
        TextureRenderer.imageC( Window.width*0.35f, Window.height * 0.02f , Window.width*0.30f,Window.height*0.20f);
        test.display();
    }

    public void quit(){
    }

    public void unload() {
        System.out.println("\n-------------------------- \n");
        option.unload();
        test.unload();
        background.unload();
    }
}
