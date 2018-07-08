package growth.screen.screens;

import growth.main.Window;
import growth.render.Render;
import growth.render.texture.Texture;
import growth.render.texture.TextureRenderer;
import growth.screen.ScreenManager;

public class OptionScreen extends Screen{

    private int state;

    private final Texture option;

    /**
     * MenuScreen class constructor.
     * Instance the class and set all of the MenuScreen's variables.
     *
     * @param screenManager Add screenManager to change the global screen.
     */
    public OptionScreen(ScreenManager screenManager) {
        super(screenManager);
        Render.setClearColor(0.365f, 0.906f, 0.784f, 1f);
        option = new Texture("/textures/menu/Option_title.png");
    }

    /**
     * Update the menu.
     */
    public void update() {
        if(ScreenManager.KEY.keyPressed(0)) {
            saveConfiguration();
        }
    }

    /**
     * Display the menu.
     */
    public void display() {
        Render.clear();
        TextureRenderer.imageC( Window.width*0.35f, Window.height *0.0f ,
                Window.width*0.30f,Window.height*0.20f, option.getID(),1f, 1f);
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
