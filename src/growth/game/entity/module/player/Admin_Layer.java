package growth.game.entity.module.player;

import growth.game.entity.type.Player;
import growth.game.entity.module.Module;
import growth.screen.ScreenManager;

/**
 * Administrator layer module class.
 * This class is the module use by a administrator-player to change the current layer.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Admin_Layer extends Module{
    /**
     * Player.
     * This variable contains the reference to the player who use this module.
     */
    private final Player player;

    /**
     * Administrator player module class constructor.
     * Instance the class and set the reference to the entity.
     *
     * @param player Player using the module.
     */
    public Admin_Layer(Player player){
        super(player);

        // Init variables
        this.player = player;
    }

    /**
     * Update the module.
     */
    public void update(){
        if(ScreenManager.KEY.keyPressed(8)) {
           player.upLayer();
        }

        if(ScreenManager.KEY.keyPressed(9)) {
            player.downLayer();
        }
    }

    public void display(){}
}
