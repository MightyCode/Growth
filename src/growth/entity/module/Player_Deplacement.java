package growth.entity.module;

import growth.entity.Player;
import growth.screen.ScreenManager;

public class Player_Deplacement extends Module{

    private Player player;
    private boolean right, left;
    private float walkSpeed;
    private float maxSpeed;
    private float stopSpeed;

    public Player_Deplacement(Player player, float walkSpeed, float maxSpeed, float stopSpeed){
        super(player);
        this.player = player;
        this.walkSpeed = walkSpeed;
        this.maxSpeed = maxSpeed;
        this.stopSpeed = stopSpeed;
    }

    public void update(){
        // Keys update
        player.setJumping(ScreenManager.KEY.key(2));
        player.setLeft(ScreenManager.KEY.key(1));
        left = ScreenManager.KEY.key(1);
        player.setRight(ScreenManager.KEY.key(3));
        right = ScreenManager.KEY.key(3);

        float speedX = player.getSpeedX();

        if (right) {
            speedX += walkSpeed;
            if (speedX > maxSpeed) {
                speedX = maxSpeed;
            }
        } else if (left) {
            speedX -= walkSpeed;
            if (speedX < -maxSpeed) {
                speedX = -maxSpeed;
            }
        } else {
            if (speedX > 0) {
                speedX -= stopSpeed;
                if (speedX < 0) {
                    speedX = 0;
                }
            } else if (speedX < 0) {
                speedX += stopSpeed;
                if (speedX > 0) {
                    speedX = 0;
                }
            }
        }

        player.setSpeedX(speedX);
    }

    public boolean getRight(){return right;}
    boolean getLeft(){return left;}
    float getMaxSpeed(){return maxSpeed;}
}
