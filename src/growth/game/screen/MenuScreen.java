package growth.game.screen;

import growth.game.render.Render;
import growth.game.utils.button.ClickButton;
import growth.main.Growth;

import static org.lwjgl.glfw.GLFW.*;

public class MenuScreen extends Screen {

    ClickButton but1, but2, but3;

    public MenuScreen(ScreenManager screenManager) {
        super(screenManager);

        but1 = new ClickButton(Growth.WIDTH/2, (int)(Growth.HEIGHT*0.45),200,100,"Play", this){
            @Override
            public void action(){
                screen.screenManager.setScreen(ScreenManager.GAMESCREEN);
            }
        };
        but2 = new ClickButton(Growth.WIDTH/2, (int)(Growth.HEIGHT*0.65),200,100,"Options", this){
            @Override
            public void action(){
                screen.screenManager.setScreen(ScreenManager.GAMESCREEN);
            }
        };

        but3 = new ClickButton(Growth.WIDTH/2, (int)(Growth.HEIGHT*0.85),200,100,"Quit", this){
            @Override
            public void action(){
               // glfwDestroyWindow(Growth.WINDOWID);
                Growth.EXIT.exit();
            }
        };
    }

    public void update() {
        but1.update();
        but2.update();
        but3.update();
    }

    public void display() {
        Render.clear();
        but1.display();
        but2.display();
        but3.display();
    }

    public void unload(){
        but1.unload();
        but2.unload();
        but3.unload();
    }
}
