package growth.game.entity.module.player;

import growth.game.entity.type.Player;
import growth.game.entity.module.Module;
import growth.screen.GameManager;

/**
 * Player movement module class.
 * This class is the module use by the player to go right and left.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Player_Movement extends Module {

    /**
     * Player.
     * This variable contains the reference to the player who use this module.
     */
    private final Player player;

    /**
     * Right and left.
     * These variables contain the state of the player movements.
     */
    private boolean right, left;

    /**
     * Walk speed.
     * This variable contains the speed of walking added to the speed of the player when it moves.
     */
    private final float walkSpeed;

    /**
     * Max speed.
     * This variable contains the maw walk speed of the player.
     */
    private final float maxSpeed;

    /**
     * Stop speed.
     * This variable contains the speed remove from the player speed x after it moves.
     */
    private final float stopSpeed;

    /**
     * Movement module class constructor.
     * Instance the class and set the module variables.
     *
     * @param player Player using the module.
     * @param walkSpeed The walk speed.
     * @param maxSpeed The max walk speed.
     * @param stopSpeed The speed remove after moving.
     */
    public Player_Movement(Player player, float walkSpeed, float maxSpeed, float stopSpeed){
        super(player);

        // Init variables
        this.player = player;
        this.walkSpeed = walkSpeed;
        this.maxSpeed = maxSpeed;
        this.stopSpeed = stopSpeed;
    }

    /**
     * Update the player and the module.
     */
    public void update(){
        // Keys update
        left = GameManager.inputsManager.input(1);
        right = GameManager.inputsManager.input(2);

        float speedX = player.getSpeedX();

        if (right && !left) {
            player.setRight(true);
            speedX += walkSpeed;
            if (speedX > maxSpeed) {
                speedX = maxSpeed;
            }
            player.setAnimations(Player.WALKING, Player.WALKING_P);
        } else if (left && !right) {
            player.setLeft(true);
            speedX -= walkSpeed;
            if (speedX < -maxSpeed) {
                speedX = -maxSpeed;
            }
            player.setAnimations(Player.WALKING, Player.WALKING_P);
        } else {
            player.setLeft(false);
            player.setRight(false);
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

    /**
     * Get the right state.
     *
     * @return Right state
     */
    public boolean getRight(){return right;}

    /**
     * Get the left state.
     *
     * @return Left state
     */
    boolean getLeft(){return left;}
}
