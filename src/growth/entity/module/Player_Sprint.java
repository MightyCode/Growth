package growth.entity.module;

import growth.entity.Player;
import growth.screen.ScreenManager;

public class Player_Sprint extends Module{

    private Player player;
    private Player_Deplacement deplacement;
    private boolean sprint;
    private float runSpeed;

    public Player_Sprint(Player player, Player_Deplacement deplacement, float runSpeed){
        super(player);
        this.player = player;
        this.deplacement = deplacement;
        this.runSpeed = runSpeed;
    }

    public void update(){
        // Keys update
        sprint = ScreenManager.KEY.key(5);

        float speedX = player.getSpeedX();

        if (sprint && (deplacement.getLeft() || deplacement.getRight())) {
            if (speedX > 0 && deplacement.getRight()) {
                speedX = deplacement.getMaxSpeed() * runSpeed;
            } else if (speedX < 0 && deplacement.getLeft()) {
                speedX = -deplacement.getMaxSpeed() * runSpeed;
            }
        }

        player.setSpeedX(speedX);
    }
}
