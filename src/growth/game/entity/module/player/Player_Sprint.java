package growth.game.entity.module.player;

import growth.game.entity.type.Player;
import growth.game.entity.module.Module;
import growth.screen.ScreenManager;

/**
 * Player speed module class.
 * This class is the module use by the player to go faster.
 *
 * Important : This module need other player module :
 *  - Movement module.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Player_Sprint extends Module {

    /**
     * Player.
     * This variable contains the reference to the player who use this module.
     */
    private final Player player;

    /**
     * Movement module.
     * This variable contains the reference to the movement module using by the player.
     */
    private final Player_Movement movement;

    /**
     * Run speed coefficient.
     * This variable contains the multiplicative coefficient of the player speed x.
     */
    private final float runSpeed;

    /**
     * Jump module class constructor.
     * Instance the class and set the module variables.
     *
     * @param player Player using the module.
     * @param movement The movement right.
     * @param runSpeed The multiplicative coefficient of speed x.
     */
    public Player_Sprint(Player player, Player_Movement movement, float runSpeed){
        super(player);
        this.player = player;
        this.movement = movement;
        this.runSpeed = runSpeed;
    }

    /**
     * Update the module and the player.
     */
    public void update(){
        float speedX = player.getSpeedX();

        if (ScreenManager.KEY.key(5) ) {
            if (speedX > 0 && movement.getRight() && !movement.getLeft()) {
                speedX *= runSpeed;
            } else if (speedX < 0 && movement.getLeft() && !movement.getRight()) {
                speedX *= runSpeed;
            }
            player.setAnimationSpeed(1.5f);
        } else {
            player.setAnimationSpeed(1);
        }

        player.setSpeedX(speedX);
    }

    public void display(){}
}
