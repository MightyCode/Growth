package growth.entity.module.player;

import growth.entity.type.Player;
import growth.entity.module.Module;
import growth.screen.GameManager;
import growth.screen.screens.GameScreen;

/**
 * Administrator layer module class.
 * This class is the module use by a administrator-player to change the current layer.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Admin_Layer extends Module{
    /**
     * Administrator player module class constructor.
     * Instance the class and set the reference to the entity.
     *
     * @param player Player using the module.
     */
    public Admin_Layer(Player player){
        super(player);
    }

    /**
     * Update the module.
     */
    public void update(){
        if(GameManager.inputsManager.inputPressed(9)) {
            GameScreen.tileMap.setLayer(1);
        }

        if(GameManager.inputsManager.inputPressed(10)) {
            GameScreen.tileMap.setLayer(-1);
        }
    }
}
