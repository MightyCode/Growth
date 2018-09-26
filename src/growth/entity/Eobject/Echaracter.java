package growth.entity.Eobject;

import growth.game.tilemap.Tile;
import growth.game.tilemap.TileMap;
import growth.screen.screens.GameScreen;
import growth.util.math.Vec2;

public abstract class Echaracter extends Emoveable {

    protected GameScreen gameScreen;

    protected TileMap tileMap;

    protected boolean topLeft , topRight , bottomLeft , bottomRight;

    /**
     * Is left.
     * This variable contains if the entity goes to the left.
     */
    protected boolean left;

    /**
     * Is right.
     * This variable contains if the entity goes to the right.
     */
    protected boolean right;

    /**
     * Is down.
     * This variable contains if the entity goes to the down.
     */
    protected boolean down;

    /**
     * Health point.
     * This variable contains the health point of the entity.
     */
    protected int healthPoint;

    /**
     * Max health point.
     * This variable contains the maximum health point of the entity.
     */
    protected int maxHealthPoint;

    public Echaracter(String name, GameScreen gm, TileMap tm) {
        super(name);
        gameScreen = gm;
        tileMap = tm;
        healthPoint = 1;
        maxHealthPoint = 1;
    }

    public void update(){
        super.update();
    }

    public void disposeAfterUpdate(){
        super.disposeAfterUpdate();
        // Direction
        if (speed.getX() < 0) facing = false;
        else if (speed.getX() > 0) facing = true;
    }

    /**
     * To take damage
     *
     * @param damage The number of the damage
     */
    public void takeDamage(int damage) {
        setHealthPoint(healthPoint - damage);
    }

    /**
     * Change the current health value.
     *
     * @param newValue New current health value.
     */
    public void setHealthPoint(int newValue) {
        if (newValue > maxHealthPoint) newValue = maxHealthPoint;

        healthPoint = newValue;
        if (healthPoint <= 0) {
            died();
        }
    }

    /**
     * Change the max value of health.
     *
     * @param newValue New maximum health value.
     */
    public void setMaxHealthPoint(int newValue) {
        if (newValue > 0 && newValue <= 40){
            maxHealthPoint = newValue;
            setHealthPoint(healthPoint);
        }
    }

    /**
     * Get the current health point of the entity.
     * @return The value.
     */
    public int getHealthPoint(){ return healthPoint;}

    /**
     * Get the maximum health point of the entity.
     * @return The value.
     */
    public int getMaxHealthPoint(){ return maxHealthPoint;}

    /**
     * Set the left state.
     *
     * @param left New left state.
     */
    public void setLeft(boolean left){
        this.left = left;
    }

    /**
     * Set the right state.
     *
     * @param right New right state.
     */
    public void setRight(boolean right){
        this.right = right;
    }
}
