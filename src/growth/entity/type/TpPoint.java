package growth.entity.type;

import growth.entity.Eobject.Eobject;
import growth.screen.GameManager;
import growth.screen.screens.GameScreen;
import growth.util.math.Vec2;

public class TpPoint extends Eobject {

    private int map;

    private int point;

    public TpPoint(Vec2 pos, Vec2 size, int map, int point){
        this.pos = pos.multiply(GameScreen.tileSize,true);
        this.size = size.multiply(GameScreen.tileSize,true);
        this.map = map;
        this.point = point;
    }

    public void update() {
        float posX = GameScreen.entityManager.getEntity(0).getPos().getX();
        float posY = GameScreen.entityManager.getEntity(0).getPos().getY();

        if(GameManager.inputsManager.inputPressed(7)){
            System.out.println("touch pressed");
            if(posX > pos.getX() && posX < pos.getX()+size.getX()){
                System.out.println("pos x good");
                if(posY > pos.getY() && posY < pos.getY() + size.getY()){
                    GameScreen.tileMap.changeMap(map,point);
                }
            }
        }
    }
}
