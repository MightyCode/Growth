package growth.entity.type;

import growth.entity.EntityManager;
import growth.entity.Eobject.Eobject;
import growth.entity.methods.EntityMethods;
import growth.screen.GameManager;
import growth.screen.render.shape.ShapeRenderer;
import growth.screen.screens.GameScreen;
import growth.util.math.Color4;
import growth.util.math.Vec2;

public class TpPoint extends InteractObject {

    private int map;
    private int point;

    public static final int TP_MAP = 6;
    public static final int TP_POINT = 7;

    public TpPoint(String[] att){
        super(att);
        this.map = Integer.parseInt(att[TP_MAP])-1;
        this.point = Integer.parseInt(att[TP_POINT])-1;
    }

    @Override
    public void action() {
        GameScreen.tileMap.changeMap(map, point);
    }
}
