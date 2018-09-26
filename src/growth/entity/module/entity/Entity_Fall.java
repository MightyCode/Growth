package growth.entity.module.entity;

import growth.entity.Eobject.Emoveable;
import growth.entity.type.Player;
import growth.entity.module.Module;
import growth.util.math.Vec2;

/**
 * Entity fall module class.
 * This class is the module use by the Entity to fall.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Entity_Fall extends Module {

    /**
     * Entity.
     * This variable contains the reference to the moving entity who use this module.
     */
    private final Emoveable entity;

    /**
     * Fall speed force.
     * This variable contains the gravity.
     */
    private final float fallSpeed;

    /**
     * Max fall speed.
     * This variable contains the max falling speed.
     */
    private final float maxFallSpeed;

    /**
     * Fall module class constructor.
     * Instance the class and set the reference to the entity.
     *
     * @param movingEntity Entity using the module.
     * @param fallSpeed The gravity.
     * @param maxFallSpeed The max falling speed.
     */
    public Entity_Fall(Emoveable movingEntity, float fallSpeed, float maxFallSpeed){
        super(movingEntity);

        // Init variables
        this.entity = movingEntity;
        this.fallSpeed = fallSpeed;
        this. maxFallSpeed = maxFallSpeed;
    }

    /**
     * Update the module and the player's fall.
     */
    public void update(){
        boolean falling = entity.getFalling();

        float speedY = entity.getSpeed().getY();

        // If Jumping
        if(falling){// If Falling
            speedY += fallSpeed;
            if (speedY >= 0){
                if (speedY > maxFallSpeed) speedY = maxFallSpeed;
                entity.setAnimations(Player.FALLING, Player.FALLING_P);
            }
        }

        entity.setPosTemp(new Vec2(entity.getPosTemp().getX(), entity.getPosTemp().getX() + speedY));
        entity.setSpeed(new Vec2(entity.getSpeed().getX(),speedY));
    }
}
