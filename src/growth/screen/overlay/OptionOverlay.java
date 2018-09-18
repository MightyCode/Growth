package growth.screen.overlay;

import growth.main.Config;
import growth.main.Window;
import growth.entity.gui.GUIButton;
import growth.entity.gui.GUISlider;
import growth.screen.render.text.FontRenderer;
import growth.sound.SoundManager;
import growth.util.TextManager;
import growth.util.math.Color4;
import growth.util.math.Vec2;
import growth.screen.render.Render;
import growth.entity.gui.GUICheckBox;
import growth.screen.render.text.StaticFonts;
import growth.screen.render.texture.Texture;
import growth.screen.render.texture.TextureRenderer;
import growth.screen.GameManager;
import growth.screen.screens.Screen;
import growth.util.XmlReader;

/**
 * Option Overlay class.
 * This class is use on game and on the main menu to change the options.
 *
 * @author MightyCode
 * @version 1.0
 */
public class OptionOverlay extends Overlay {

    /**
     * Textures use on the overlay.
     */
    private Texture option, background;

    /**
     * Font renderer to render the help text.
     */
    private FontRenderer help;

    /**
     * GUIButton to choose the categories
     */
    private GUIButton general, video, inputs;

    /**
     * Inputs for general
     */
    private GUICheckBox language;
    private GUISlider musicVolume;
    private GUISlider noiseVolume;

    /**
     * Inputs for video
     */

    private GUICheckBox fullscreen, frameRate;

    /**
     * Option overlay class constructor.
     * Instance the class and set overlay's variables.
     */
    public OptionOverlay(){
        super();
        background = new Texture("/textures/menu/bg.png");
        option = new Texture("/textures/menu/Option_title2.png");

        Vec2 size = new Vec2(Window.width / 4f, Window.height / 20f);
        Color4 backgroundColor = new Color4(0.0f, 0.0f, 0.0f, 0.0f);
        Color4 hoverColor = new Color4(0.0f, 0.0f, 0.0f, 0.2f);
        Color4 textColor = new Color4(0.2f, 0.2f, 0.2f, 1.0f);
        Color4 hoverTextColor = Color4.BLACK;

        general = new GUIButton(
                new Vec2(Window.width * 0.2f, Window.height * 0.3f), size,
                TextManager.OPTIONS,0, StaticFonts.monofonto, backgroundColor, hoverColor, textColor, hoverTextColor
        ) {
            @Override public void action() {
                lock = true;
                video.setLock(false);
                inputs.setLock(false);
                Screen.setOverlayState(0);
            }
        };

        general.setMouseOver(true);
        general.setLock(true);

        video = new GUIButton(
                new Vec2(Window.width * 0.5f, Window.height * 0.3f),
                size, TextManager.OPTIONS,1, StaticFonts.monofonto, backgroundColor, hoverColor, textColor, hoverTextColor
        ) {
            @Override public void action() {
                lock = true;
                general.setLock(false);
                inputs.setLock(false);
                Screen.setOverlayState(1);
            }
        };

        inputs = new GUIButton(
                new Vec2(Window.width * 0.8f, Window.height * 0.3f),
                size, TextManager.OPTIONS,2, StaticFonts.monofonto, backgroundColor, hoverColor, textColor, hoverTextColor
        ) {
            @Override public void action() {
                lock = true;
                video.setLock(false);
                video.setLock(false);
                Screen.setOverlayState(2);
            }
        };

        language = new GUICheckBox(
                new Vec2(Window.width*0.5f, Window.height*0.41f),
                new Vec2(Window.width*0.125f, Window.height*0.1f),
                TextManager.OPTIONS,4, StaticFonts.monofonto, textColor, hoverTextColor
        ){
            @Override
            public void action () {
                if(GUIState == 0){
                   GameManager.textManager.changeLanguage("fr");
                } else{
                    GameManager.textManager.changeLanguage("en");
                }
            }
        };
        language.setState(Window.config.getValue(Config.LANGUAGE).equals("en"));

        musicVolume = new GUISlider(
                new Vec2(Window.width*0.20f, Window.height*0.41f) ,
                new Vec2(Window.width*0.15f, Window.height*0.1f),
                TextManager.OPTIONS,6, StaticFonts.monofonto, textColor, hoverTextColor,0,100, Float.parseFloat(Window.config.getValue(Config.MUSIC_VOL))
        ){
            @Override
            public void action () {
                GameManager.soundManager.setMusicVolume((int)value);
            }
        };

        noiseVolume = new GUISlider(
                new Vec2(Window.width*0.20f, Window.height*0.55f) ,
                new Vec2(Window.width*0.15f, Window.height*0.1f),
                TextManager.OPTIONS,7, StaticFonts.monofonto, textColor, hoverTextColor,0,100, Float.parseFloat(Window.config.getValue(Config.NOISE_VOL))
        ){
            @Override
            public void action () {
                GameManager.soundManager.setNoiseVolume((int)value);
            }
        };

        fullscreen = new GUICheckBox(
                new Vec2(Window.width*0.5f, Window.height*0.41f),
                new Vec2(Window.width*0.14f, Window.height*0.1f),
                TextManager.OPTIONS,3, StaticFonts.monofonto, textColor, hoverTextColor
        ){
            @Override
            public void action () {
                if(GUIState == 0){
                    Window.config.setValue("0", Config.FULLSCREEN);
                } else{
                    Window.config.setValue("1", Config.FULLSCREEN);
                }
            }
        };
        fullscreen.setState(Window.config.getValue(Config.FULLSCREEN).equals("1"));

        frameRate = new GUICheckBox(
                new Vec2(Window.width*0.5f, Window.height*0.60f),
                new Vec2(Window.width*0.20f, Window.height*0.1f),
                TextManager.OPTIONS,8, StaticFonts.monofonto, textColor, hoverTextColor
        ){
            @Override
            public void action () {
                if(GUIState == 0){
                    Window.config.setValue("0", Config.LIMITED_FRAMERATE);
                } else{
                    Window.config.setValue("1", Config.LIMITED_FRAMERATE);
                }
            }
        };
        frameRate.setState(Window.config.getValue(Config.LIMITED_FRAMERATE).equals("1"));

        help = new FontRenderer(TextManager.OPTIONS,5, StaticFonts.IBM, Window.height*0.04f,
                new Vec2(Window.width * 0.5f, Window.height * 0.95f), Color4.BLACK);
    }

    /**
     * Update the overlay and its components.
     */
    public void update() {
        if(GameManager.inputsManager.inputPressed(0))  quit();

        general.update();
        video.update();
        inputs.update();

        switch (overlayState){
            case 0:
                language.update();
                musicVolume.update();
                noiseVolume.update();
                break;
            case 1:
                fullscreen.update();
                frameRate.update();
                break;
            case 2:
                break;
        }
    }

    /**
     * Display the overlay.
     */
    public void display() {
        Render.clear();

        background.bind();
        TextureRenderer.imageC(0, 0, Window.width, Window.height);
        option.bind();
        TextureRenderer.imageC( Window.width*0.35f, Window.height * 0.02f , Window.width*0.30f,Window.height*0.20f);

        general.display();
        video.display();
        inputs.display();

        switch (overlayState){
            case 0:
                language.display();
                musicVolume.display();
                noiseVolume.display();
                break;
            case 1:
                fullscreen.display();
                frameRate.display();
                break;
            case 2:
                break;
        }

        help.renderC();
    }

    /**
     * Override method to write what to do when you quit the overlay.
     */
    public void quit(){
    }

    /**
     * Unload the overlay.
     */
    public void unload() {
        Window.console.println("\n-------------------------- \n");
        // Textures
        option.unload();
        background.unload();

        // Main part buttons
        general.unload();
        video.unload();
        inputs.unload();

        // Language
        language.unload();
        musicVolume.unload();
        noiseVolume.unload();

        // Video
        fullscreen.unload();
        frameRate.unload();
        // Control

        // Font renderer
        help.unload();
    }
}
