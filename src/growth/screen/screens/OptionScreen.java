package growth.screen.screens;

import growth.main.Window;
import growth.render.Render;
import growth.render.texture.Texture;
import growth.render.texture.TextureRenderer;
import growth.screen.ScreenManager;

public class OptionScreen extends Screen{

    private int state;

    private final Texture option;

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

        background = new Texture();
        background.load("/textures/menu/bg.png");
    }

    /**
     * Update the menu.
     */
    public void update() {
        if(ScreenManager.inputsManager.inputPressed(0)) {
            saveConfiguration();
        }
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
    }

    /**
     * Unload the textures in menu to free memory.
     */
    public void unload() {
        System.out.println("\n-------------------------- \n");
        option.unload();
    }

    public void saveConfiguration(){
        screenManager.setScreen(ScreenManager.MENUSCREEN);
    }
}
