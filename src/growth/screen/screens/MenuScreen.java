package growth.screen.screens;

import growth.main.Growth;
import growth.render.Render;
import growth.render.texture.Texture;
import growth.render.texture.TextureRenderer;
import growth.screen.ScreenManager;
import growth.utils.button.ClickButton;
import growth.main.Window;

/**
 * Menu class.
 * This class is the menu screen.
 *
 * @author MightyCode
 * @version 1.0
 */
public class MenuScreen extends Screen {

    /**
     * Button 1.
     * This variable contains the button to play game.
     */
    private final ClickButton but1;

    /**
     * Button 2.
     * This variable contains the button to go to options screen.
     */
    private final ClickButton but2;

    /**
     * Button 3.
     * This variable contains the button to quit the game.
     */
    private final ClickButton but3;

    /**
     * Title.
     * This variable contains the texture of title of the game.
     */
    private final Texture title;

    /**
     * MenuScreen class constructor.
     * Instance the class and set all of the MenuScreen's variables.
     *
     * @param screenManager Add screenManager to change the global screen.
     */
    public MenuScreen(ScreenManager screenManager) {
        super(screenManager);
        Render.setClearColor(1f,1f);
        System.out.println("\n-------------------------- \n");

        title = new Texture("/images/menu/Title.png");

        but1 = new ClickButton(Window.WIDTH / 2, (int) (Window.HEIGHT * 0.45), 200, 100, "Play", this) {
            @Override
            public void action() {
                screen.screenManager.setScreen(ScreenManager.GAMESCREEN);
            }
        };
        but2 = new ClickButton(Window.WIDTH / 2, (int) (Window.HEIGHT * 0.65), 200, 100, "Options", this) {
            @Override
            public void action() {
                screen.screenManager.setScreen(ScreenManager.GAMESCREEN);
            }
        };

        but3 = new ClickButton(Window.WIDTH / 2, (int) (Window.HEIGHT * 0.85), 200, 100, "Quit", this) {
            @Override
            public void action() {
                Growth.WINDOW.exit();
            }
        };
    }

    /**
     * Update the menu.
     */
    public void update() {
        but1.update();
        but2.update();
        but3.update();
    }

    /**
     * Display the menu.
     */
    public void display() {
        Render.clear();
        TextureRenderer.imageC( Window.WIDTH*0.35f, Window.HEIGHT *0.0f , Window.WIDTH*0.30f,Window.HEIGHT*0.30f, title.getID(),1, 1f);

        but1.displayC();
        but2.displayC();
        but3.displayC();
    }

    /**
     * Unload the textures in menu to free memory.
     */
    public void unload() {
        System.out.println("\n-------------------------- \n");
        but1.unload();
        but2.unload();
        but3.unload();
    }
}