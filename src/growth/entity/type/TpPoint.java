package growth.entity.type;

import growth.entity.Eobject.Eobject;
import growth.screen.GameManager;
import growth.screen.render.shape.ShapeRenderer;
import growth.screen.screens.GameScreen;
import growth.util.math.Color4;
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

    public TpPoint(String[] att){
        this.pos = new Vec2(Float.parseFloat(att[1]),Float.parseFloat(att[2])).multiply(GameScreen.tileSize,true);
        this.size = new Vec2(Float.parseFloat(att[3]),Float.parseFloat(att[4])).multiply(GameScreen.tileSize,true);
        this.map = Integer.parseInt(att[5])-1;
        this.point = Integer.parseInt(att[6])-1;
    }

    public void update() {
        float posX = GameScreen.entityManager.getPos().getX();
        float posY = GameScreen.entityManager.getPos().getY();

        if(GameManager.inputsManager.inputPressed(7)){
            if(posX > pos.getX() && posX < pos.getX()+size.getX()){
                if(posY > pos.getY() && posY < pos.getY() + size.getY()){
                    GameScreen.tileMap.changeMap(map,point);
                }
            }
        }
    }

    public void display(){
        ShapeRenderer.rect(pos,size,new Color4(0,0,0,0.5f));
    }
}
