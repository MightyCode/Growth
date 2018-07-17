package growth.game.entity.type;

import growth.game.entity.module.entity.Entity_RectRenderer;
import growth.screen.screens.GameScreen;

public class DummyEntity extends BasicEntity{

   public DummyEntity(GameScreen gameScreen, int posX, int posY, int sizeX, int sizeY){
       super(gameScreen);
       /* Init player's variables */
       // Size, and boxSize
       this.posX = posX;
       this.posY = posY;
       this.sizeX = sizeX;
       this.sizeY = sizeY;
       ((Entity_RectRenderer)modules.get(0)).newParam();
   }

    public void load(){
       int[] color = new int[3];
       color[0] = 255;
       color[1] = 0;
       color[2] = 0;
       modules.add(new Entity_RectRenderer(this, color));
    }
}
