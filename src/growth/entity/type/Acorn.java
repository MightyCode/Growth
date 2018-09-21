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

public class Acorn extends Edrawable {
    public Acorn(String[] att) {
        super(att[EntityManager.NAME]);
        this.pos = new Vec2(Float.parseFloat(att[EntityManager.POSX]),Float.parseFloat(att[EntityManager.POSY])).multiply(GameScreen.tileSize,true);
        this.size = new Vec2(0.8f).multiply(GameScreen.tileSize,true);
        animations.add(new Animation("/textures/game/entity/acorn/acorn.png"));
    }

    public void update(){
        if (EntityMethods.isCollidc(pos, size, GameScreen.entityManager.getPlayer())){
            GameScreen.entityManager.getPlayer().setAction(true);
            if (GameManager.inputsManager.inputPressed(7)) {
                if(!GameScreen.entityManager.getPlayer().getHoldAcorn().equals("")) return;
                XmlReader.changeValue(Window.config.getValue(Config.PART_PATH) + "temp/save.xml", "acorn", name, "player");
                GameScreen.entityManager.getPlayer().setHoldAcorn(name);
                GameScreen.entityManager.removeEntity(this);
                MapModifier.deleteEntity(name);
            }
        }
    }
}
