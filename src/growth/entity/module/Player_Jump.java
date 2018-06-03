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
    private Player player;

    /**
     * Jump start force.
     * This variable contains the strength of the player's jump.
     */
    private float jumpStart;

    /**
     * Fall speed force.
     * This variable contains the gravity.
     */
    private float fallSpeed;

    /**
     * Stop jump speed.
     * This variable contains the speed to stop the player after jumping.
     */
    private float stopJumpSpeed;
    private float maxFallSpeed;

    public Player_Jump(Player player, float jumpStart, float fallSpeed, float stopJumpSpeed, float maxFallSpeed){
        super(player);
        this.player = player;
        this.jumpStart = jumpStart;
        this.fallSpeed = fallSpeed;
        this.stopJumpSpeed = stopJumpSpeed;
        this. maxFallSpeed = maxFallSpeed;
    }

    public void update(){
        // Keys update
        boolean jumping = ScreenManager.KEY.key(2);
        player.setJumping(jumping);
        boolean falling = player.getFalling();

        float speedY = player.getSpeedY();

        // If Jumping
        if (jumping && !falling) {
            speedY = jumpStart;
            player.setFalling(falling = true);
        }

        // If Falling
        if (falling) {
            speedY += fallSpeed;

            if (speedY > 0) player.setJumping(false);
            else if (speedY < 0 && !jumping) speedY += stopJumpSpeed;

            if (speedY > maxFallSpeed) speedY = maxFallSpeed;
        }

        player.setSpeedY(speedY);
    }
}
