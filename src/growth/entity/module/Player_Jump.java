package growth.entity.module;

import growth.entity.Player;
import growth.screen.ScreenManager;

/**
 * Player jump module class.
 * This class is the module use by the player to jump.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Player_Jump extends Module{

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
     * Fall speed force.
     * This variable contains the gravity.
     */
    private final float fallSpeed;

    /**
     * Stop jump speed.
     * This variable contains the speed to stop the player after jumping.
     */
    private final float stopJumpSpeed;

    /**
     * Max fall speed.
     * This variable contains the max falling speed.
     */
    private final float maxFallSpeed;

    /**
     * Jump module class constructor.
     * Instance the class and set the reference to the entity.
     *
     * @param player Player using the module.
     * @param jumpStart The strength of the player's jump.
     * @param fallSpeed The gravity.
     * @param stopJumpSpeed The value using by this module.
     * @param maxFallSpeed The max falling speed.
     */
    public Player_Jump(Player player, float jumpStart, float fallSpeed, float stopJumpSpeed, float maxFallSpeed){
        super(player);

        // Init variables
        this.player = player;
        this.jumpStart = jumpStart;
        this.fallSpeed = fallSpeed;
        this.stopJumpSpeed = stopJumpSpeed;
        this. maxFallSpeed = maxFallSpeed;
    }

    /**
     * Update the module and the player jump.
     */
    public void update(){
        // Keys update
        boolean jumping = ScreenManager.KEY.key(2);
        player.setJumping(jumping);
        boolean falling = player.getFalling();

        float speedY = player.getSpeedY();

        // If Jumping
        if (jumping){
            player.setAnimations(Player.JUMPING, Player.JUMPING_P);
            if( !falling) {
                speedY = jumpStart;
                player.setFalling(falling = true);
            }
        }

        // If Falling
        if (falling) {
            speedY += fallSpeed;

            if (speedY > 0){
                player.setJumping(false);
                player.setAnimations(Player.FALLING, Player.FALLING_P);
            }

            else if (speedY < 0 && !jumping){
                speedY += stopJumpSpeed;
            }

            if (speedY > maxFallSpeed) speedY = maxFallSpeed;

        }

        player.setSpeedY(speedY);
    }
}
