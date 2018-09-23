package growth.entity.type;

import growth.entity.EntityManager;
import growth.entity.Eobject.Eobject;
import growth.entity.methods.EntityMethods;
import growth.main.Window;
import growth.screen.GameManager;
import growth.screen.screens.GameScreen;
import growth.util.MapModifier;
import growth.util.math.Vec2;

public class InteractObject extends Eobject {
    public InteractObject(String att[]) {
        super(att[EntityManager.NAME]);
        this.pos = new Vec2(Float.parseFloat(att[EntityManager.POSX]), Float.parseFloat(att[EntityManager.POSY])).multiply(GameScreen.tileSize,true);
        this.size = new Vec2(Float.parseFloat(att[EntityManager.SIZEX]), Float.parseFloat(att[EntityManager.SIZEY])).multiply(GameScreen.tileSize,true);
    }

    public void update(){
        if (EntityMethods.isCollidc(pos, size, GameScreen.entityManager.getPlayer())){
            GameScreen.entityManager.getPlayer().setAction(true);
            if (GameManager.inputsManager.inputPressed(7)) {
                action();
            }
        }
    }

    public void action(){

    }
}
