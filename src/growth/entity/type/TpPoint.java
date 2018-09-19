package growth.entity.type;

import growth.entity.EntityManager;
import growth.entity.Eobject.Eobject;
import growth.entity.methods.EntityMethods;
import growth.screen.GameManager;
import growth.screen.render.shape.ShapeRenderer;
import growth.screen.screens.GameScreen;
import growth.util.math.Color4;
import growth.util.math.Vec2;

public class TpPoint extends Eobject {

    private int map;
    private int point;

    public static final int TP_MAP = 6;
    public static final int TP_POINT = 7;

    public TpPoint(Vec2 pos, Vec2 size, int map, int point){
        super("Tppoint null");
        this.pos = pos.multiply(GameScreen.tileSize,true);
        this.size = size.multiply(GameScreen.tileSize,true);
        this.map = map;
        this.point = point;
    }

    public TpPoint(String[] att){
        super(att[EntityManager.NAME]);
        this.pos = new Vec2(Float.parseFloat(att[EntityManager.POSX]),Float.parseFloat(att[EntityManager.POSY])).multiply(GameScreen.tileSize,true);
        this.size = new Vec2(Float.parseFloat(att[EntityManager.SIZEX]),Float.parseFloat(att[EntityManager.SIZEY])).multiply(GameScreen.tileSize,true);
        this.map = Integer.parseInt(att[TP_MAP])-1;
        this.point = Integer.parseInt(att[TP_POINT])-1;
    }

    public void update() {
        if (EntityMethods.isCollidc(pos, size, GameScreen.entityManager.getPlayer())){
            GameScreen.entityManager.getPlayer().setAction(true);
            if (GameManager.inputsManager.inputPressed(7)) {
                GameScreen.tileMap.changeMap(map, point);
            }
        }
    }

    public void display(){
       // ShapeRenderer.rect(pos,size,new Color4(0,0,0,0.5f));
    }

    public void unload(){}
}
