package growth.screen.overlay;

import growth.main.Window;
import growth.render.Render;
import growth.render.texture.Texture;
import growth.screen.screens.GameScreen;
import growth.screen.screens.Screen;
import growth.screen.ScreenManager;
import growth.utils.button.ClickButton;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class PauseOverlay extends Overlay{

    Texture pause;
    private ClickButton continu, menu;

    public PauseOverlay(Screen screen){
        super(screen);
        // Init game textures
        pause = new Texture("/images/menu/Pause.png");

        // Init buttons
        continu = new ClickButton(Window.WIDTH*0.5,Window.HEIGHT*0.5,Window.WIDTH*0.12,Window.HEIGHT*0.11,"Continue",this){
            @Override
            public void action(){
                overlay.setState(GameScreen.NORMALSCREEN);
            }
        };

        menu = new ClickButton(Window.WIDTH*0.5,Window.HEIGHT*0.75,Window.WIDTH*0.15,Window.HEIGHT*0.11,"Return",this){
            @Override
            public void action(){
                overlay.setScreen(ScreenManager.MENUSCREEN);
            }
        };
    }

    public void update(){
        continu.update();
        menu.update();
        if(ScreenManager.KEY.keyPressed(GLFW_KEY_ESCAPE)) {
            screen.setState(GameScreen.NORMALSCREEN);
        }
    }

    public void display(){
        // Black rectangle
        Render.rect(0, 0, Window.WIDTH, Window.HEIGHT,0, (float)0.6);
        Render.rect(Window.WIDTH*0.1, Window.HEIGHT*0.15, Window.WIDTH*0.8, Window.HEIGHT*0.75 ,0, (float)0.5);

        // Textures and button
        Render.image(Window.WIDTH*0.40,Window.HEIGHT*0.05,Window.WIDTH*0.2,Window.HEIGHT*0.09, pause.getID(), 1);

        continu.display();
        menu.display();
    }
}