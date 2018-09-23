package growth.entity.type;

import growth.entity.EntityManager;
import growth.main.Config;
import growth.main.Window;
import growth.screen.screens.GameScreen;
import growth.util.LoadPlayerTree;
import growth.util.XmlReader;
import growth.util.math.Vec2;

public class Monitor extends InteractObject {

    public Monitor(String[] att){
        super(att);
        this.pos = new Vec2(Float.parseFloat(att[EntityManager.POSX]),Float.parseFloat(att[EntityManager.POSY])).multiply(GameScreen.tileSize,true);
        this.size = new Vec2(Float.parseFloat(att[EntityManager.SIZEX]),Float.parseFloat(att[EntityManager.SIZEY])).multiply(GameScreen.tileSize,true);
    }

    @Override
    public void action(){
        Player player = GameScreen.entityManager.getPlayer();
        String acorn = player.getHoldAcorn();
        if(acorn.equals(""))return;
            player.setAcornNumber(player.getAcornNumber()+1);
            XmlReader.changeValue(Window.config.getValue(Config.PART_PATH) + "temp/save.xml", "acorn", "", "player");
            LoadPlayerTree.changeNodeValue(acorn.substring(2,acorn.length()));
            LoadPlayerTree.playerTree();
            player.setHoldAcorn("");
    }
}
