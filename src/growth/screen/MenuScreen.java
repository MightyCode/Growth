package growth.screen;

import growth.render.Render;
import growth.utils.button.ClickButton;
import growth.main.Window;

import static growth.main.Growth.WINDOW;

public class MenuScreen extends Screen {

    private ClickButton but1;
    ClickButton but2;
    final ClickButton but3;

    public MenuScreen(ScreenManager screenManager) {
        super(screenManager);

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
                WINDOW.exit();
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
