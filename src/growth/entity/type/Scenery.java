package growth.entity.type;

import growth.entity.EntityManager;
import growth.entity.Eobject.Edrawable;
import growth.screen.render.Animation;
import growth.screen.render.texture.TextureRenderer;
import growth.screen.screens.GameScreen;
import growth.util.math.Vec2;

public class Scenery extends Edrawable {

    public static final int SCENERY_FILEPATH = 6;

    public Scenery(String[] att){
        super(att[EntityManager.NAME]);
        animations.add(new Animation(att[SCENERY_FILEPATH]));
        this.pos = new Vec2(Float.parseFloat(att[EntityManager.POSX]),Float.parseFloat(att[EntityManager.POSY])).multiply(GameScreen.tileSize,true);
        this.size = new Vec2(Float.parseFloat(att[EntityManager.SIZEX]),Float.parseFloat(att[EntityManager.SIZEY])).multiply(GameScreen.tileSize,true);
    }
}
