package growth.entity.type;

import growth.entity.EntityManager;
import growth.entity.Eobject.Edrawable;
import growth.entity.Eobject.Eobject;
import growth.entity.methods.EntityMethods;
import growth.main.Config;
import growth.main.Window;
import growth.screen.GameManager;
import growth.screen.render.Animation;
import growth.screen.screens.GameScreen;
import growth.util.LoadPlayerTree;
import growth.util.XmlReader;
import growth.util.math.Vec2;

import javax.xml.bind.annotation.XmlRootElement;

public class Monitor extends Eobject {

    public Monitor(String[] att){
        super(att[EntityManager.NAME]);
        this.pos = new Vec2(Float.parseFloat(att[EntityManager.POSX]),Float.parseFloat(att[EntityManager.POSY])).multiply(GameScreen.tileSize,true);
        this.size = new Vec2(Float.parseFloat(att[EntityManager.SIZEX]),Float.parseFloat(att[EntityManager.SIZEY])).multiply(GameScreen.tileSize,true);
    }

    public void update(){
        if (EntityMethods.isCollidc(pos, size, GameScreen.entityManager.getPlayer())){
            GameScreen.entityManager.getPlayer().setAction(true);
            if (GameManager.inputsManager.inputPressed(7)) {
                Player player = GameScreen.entityManager.getPlayer();
                String acorn = player.getHoldAcorn();
                if(!acorn.equals("")){
                    player.setAcornNumber(player.getAcornNumber()+1);
                    XmlReader.changeValue(Window.config.getValue(Config.PART_PATH) + "temp/save.xml", "acorn", "", "player");
                    LoadPlayerTree.changeNodeValue(acorn.substring(2,acorn.length()));
                    LoadPlayerTree.playerTree();
                    player.setHoldAcorn("");
                }
            }
        }
    }
}
