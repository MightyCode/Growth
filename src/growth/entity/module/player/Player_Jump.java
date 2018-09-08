package growth.entity.module.player;

import growth.entity.type.Player;
import growth.entity.module.Module;
import growth.screen.GameManager;
import growth.util.math.Vec2;

/**
 * Player jump module class.
 * This class is the module use by the player to jump.
 *
 * @author MightyCode
 * @version 1.0
 */

public class Player_Jump extends Module {

    /**
     * Player.
     * This variable contains the reference to the player who use this module.
     */
    private final Player player;

    /**
     * Jump start force.
     * This variable contains the strength of the player's jump.
     */
    private final float jumpStart;

    /**
     * Stop jump speed.
     * This variable contains the speed to stop the player after jumping.
     */
    private final float stopJumpSpeed;

    /**
     * Jump module class constructor.
     * Instance the class and set the reference to the entity.
     *
     * @param player Player using the module.
     * @param jumpStart The strength of the player's jump.
     * @param stopJumpSpeed The value using by this module.
     */
    public Player_Jump(Player player, float jumpStart, float stopJumpSpeed){
        super(player);

        // Init variables
        this.player = player;
        this.jumpStart = jumpStart;
        this.stopJumpSpeed = stopJumpSpeed;
    }

    /**
     * Update the module and the player's jump.
     */
    public void update(){
        // Keys update
        boolean jumping = GameManager.inputsManager.input(5);
        boolean falling = player.getFalling();

        float speedY = player.getSpeed().getY();

        // If Jumping
        if(!falling) {
            if (jumping) {
                speedY = jumpStart;
                player.setFalling(true);
            }
        } else{
        // If Falling
            if (speedY <= 0) {
                if(!jumping) speedY += stopJumpSpeed;
                player.setAnimations(Player.JUMPING, Player.JUMPING_P);
            }
        }

        player.setSpeed(new Vec2(player.getSpeed().getX(),speedY));
    }
}
