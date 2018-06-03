package growth.entity.module;

import growth.entity.Player;
import growth.screen.ScreenManager;

public class Player_Saut extends Module{
    private Player player;
    private boolean falling;
    private float jumpStart;
    private float fallSpeed;
    private float stopJumpSpeed;
    private float maxFallSpeed;

    public Player_Saut(Player player, float jumpStart, float fallSpeed, float stopJumpSpeed, float maxFallSpeed){
        super(player);
        this.player = player;
        this.jumpStart = jumpStart;
        this.fallSpeed = fallSpeed;
        this.stopJumpSpeed = stopJumpSpeed;
        this. maxFallSpeed = maxFallSpeed;
    }

    public void update(){
        // Keys update
        player.setJumping(ScreenManager.KEY.key(2));
        boolean jumping = ScreenManager.KEY.key(2);
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

            if (speedY > 0) player.setJumping(jumping = false);
            else if (speedY < 0 && !jumping) speedY += stopJumpSpeed;

            if (speedY > maxFallSpeed) speedY = maxFallSpeed;
        }

        player.setSpeedY(speedY);
    }
}
