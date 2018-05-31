package growth.screen.screens;

import growth.main.Growth;
import growth.render.Render;
import growth.screen.ScreenManager;
import growth.utils.button.ClickButton;
import growth.main.Window;

public class MenuScreen extends Screen {

    private ClickButton but1;
    private ClickButton but2;
    private final ClickButton but3;

    public MenuScreen(ScreenManager screenManager) {
        super(screenManager);
        System.out.println("\n-------------------------- \n");

        but1 = new ClickButton(Window.WIDTH/2, (int)(Window.HEIGHT*0.45),200,100,"Play", this){
            @Override
            public void action(){
                screen.screenManager.setScreen(ScreenManager.GAMESCREEN);
            }
        };
        but2 = new ClickButton(Window.WIDTH/2, (int)(Window.HEIGHT*0.65),200,100,"Options", this){
            @Override
            public void action(){
                screen.screenManager.setScreen(ScreenManager.GAMESCREEN);
            }
        };

        but3 = new ClickButton(Window.WIDTH/2, (int)(Window.HEIGHT*0.85),200,100,"Quit", this){
            @Override
            public void action(){
               // glfwDestroyWindow(Growth.WINDOWID);
                Growth.WINDOW.exit();
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
        System.out.println("\n-------------------------- \n");
        but1.unload();
        but2.unload();
        but3.unload();
    }
}
