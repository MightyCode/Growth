package growth.screen.overlay;

import growth.main.Window;
import growth.render.Render;
import growth.render.texture.Texture;
import growth.screen.ScreenManager;
import growth.screen.screens.Screen;
import growth.utils.button.ClickButton;

public class DeathOverlay extends Overlay{

    private ClickButton menu, restart;

    private Texture lose;


    public DeathOverlay(Screen screen){
        super(screen);

        lose = new Texture("/images/menu/Lose.png");


        restart = new ClickButton(Window.WIDTH*0.5,Window.HEIGHT*0.45,Window.WIDTH*0.12,Window.HEIGHT*0.11,"Restart",this){
            @Override
            public void action(){
                overlay.setScreen(ScreenManager.GAMESCREEN);
            }
        };

        menu = new ClickButton(Window.WIDTH*0.5,Window.HEIGHT*0.71,Window.WIDTH*0.15,Window.HEIGHT*0.11,"Return",this){
            @Override
            public void action(){
                overlay.setScreen(ScreenManager.MENUSCREEN);
            }
        };
    }

    public void update(){
        restart.update();
        menu.update();
    }

    public void display(){
        // Black rectangle
        Render.rect(0, 0, Window.WIDTH, Window.HEIGHT,0, (float)0.6);
        Render.rect(Window.WIDTH*0.1, Window.HEIGHT*0.15, Window.WIDTH*0.8, Window.HEIGHT*0.751 ,0, (float)0.5);

        // Textures and button
        Render.image(Window.WIDTH*0.40,Window.HEIGHT*0.05,Window.WIDTH*0.2,Window.HEIGHT*0.09, lose.getID(), 1);

        restart.display();
        menu.display();
    }
}
