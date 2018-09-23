package growth.entity.type;

import growth.entity.EntityManager;
import growth.entity.Eobject.Echaracter;
import growth.entity.Eobject.Edrawable;
import growth.entity.methods.EntityMethods;
import growth.game.tilemap.TileMap;
import growth.main.Config;
import growth.main.Window;
import growth.screen.GameManager;
import growth.screen.render.Animation;
import growth.screen.screens.GameScreen;
import growth.util.MapModifier;
import growth.util.XmlReader;
import growth.util.math.Vec2;
import growth.util.math.Math;

public class Acorn extends InteractObject {
    private Scenery acorn;
    public Acorn(String[] att) {
        super(Math.addValue(att, "0.8f", "0.8f"));
        acorn = new Scenery(Math.addValue(att, "0.8f", "0.8f", "/textures/game/entity/acorn/acorn.png"));
        GameScreen.entityManager.addEntity(acorn);
    }

    @Override
    public void action(){
        if(!GameScreen.entityManager.getPlayer().getHoldAcorn().equals("")) return;
        GameScreen.entityManager.getPlayer().setHoldAcorn(name);
        GameScreen.entityManager.removeEntity(acorn);
        GameScreen.entityManager.removeEntity(this);
        MapModifier.deleteEntity(name);
    }

    public void unload() {
        super.unload();
    }
}
