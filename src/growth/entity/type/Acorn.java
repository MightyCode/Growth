package growth.entity.type;

import growth.entity.EntityManager;
import growth.entity.Eobject.Echaracter;
import growth.entity.Eobject.Edrawable;
import growth.game.tilemap.TileMap;
import growth.screen.render.Animation;
import growth.screen.screens.GameScreen;
import growth.util.math.Vec2;

public class Acorn extends Edrawable {
    public Acorn(String[] att) {
        super(att[EntityManager.NAME]);
        this.pos = new Vec2(Float.parseFloat(att[EntityManager.POSX]),Float.parseFloat(att[EntityManager.POSY])).multiply(GameScreen.tileSize,true);
        this.size = new Vec2(0.8f).multiply(GameScreen.tileSize,true);
        animations.add(new Animation("/textures/game/entity/acorn/acorn.png"));
    }
}
