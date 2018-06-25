package growth.entity.module.player;

import growth.entity.Player;
import growth.entity.module.Module;
import growth.screen.ScreenManager;

public class Admin_Layer extends Module{
    /**
     * Player.
     * This variable contains the reference to the player who use this module.
     */
    private final Player player;

    public Admin_Layer(Player player){
        super(player);

        // Init variables
        this.player = player;
    }

    /**
     * Update the module and the player's jump.
     */
    public void update(){
        if(ScreenManager.KEY.keyPressed(8)) {
           player.tileMap.upLayer();
        }

        if(ScreenManager.KEY.keyPressed(9)) {
            player.tileMap.downLayer();
        }
    }
}
