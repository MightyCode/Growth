package growth.game.entity.type;

import growth.game.entity.module.Module;
import growth.render.texture.TextureRenderer;
import growth.screen.screens.GameScreen;
import growth.game.tilemap.Tile;
import growth.game.tilemap.TileMap;

import java.util.ArrayList;

/**
 * Moving entity class.
 * This class is the mother class of all entity who move.
 *
 *  @author MightyCode
 *  @version 1.0
 */
public class MovingEntity extends BasicEntity {

    /**
     * tileMap
     * This variable contains the reference to the
     */
    protected final TileMap tileMap;

    /**
     * Entity speed X.
     * This variable contains the speed X of the entity.
     */
    protected float speedX;

    /**
     * Entity speed Y.
     * This variable contains the speed Y of the entity.
     */
    protected float speedY;

    /**
     * Temporary position x.
     * This variable contains the temporary position in x.
     */
    protected float xTemp;

    /**
     * Temporary position y.
     * This variable contains the temporary position in y.
     */
    protected float yTemp;

    /**
     * Current row position.
     * This variable contains the row position.
     */
    private int currRow;

    /**
     * Current column position.
     * This variable contains the column position.
     */
    private int currCol;

    /**
     * Destination position of x.
     * This variable contains the next position x of the entity.
     */
    private float xDest;

    /**
     * Destination position of y.
     * This variable contains the next position y of the entity.
     */
    private float yDest;

    /**
     * Top-left status collision.
     * This variable contains the status of the top left tile.
     */
    private boolean topLeft;

    /**
     * Top-right status collision.
     * This variable contains the status of the top right tile.
     */
    private boolean topRight;

    /**
     * Bottom-left status collision.
     * This variable contains the status of the bottom left tile.
     */
    private boolean bottomLeft;

    /**
     * Bottom-left status collision.
     * This variable contains the status of the bottom right tile.
     */
    private boolean bottomRight;

    /**
     * Facing (true -> right // false -> left)
     * This variable contains the direction towards where the entity is "looking".
     */
    protected boolean facing;

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
     * Is falling.
     * This variable contains the falling's state.
     */
    private boolean falling;

    private int leftTile;
    private int rightTile;
    private int topTile;
    private int bottomTile;

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

    /**
     * Moving Entity constructor.
     * Instance the class and set the tileMap.
     *
     * @param gameScreen The reference to the game screen.
     * @param tileSize The size of the tile.
     * @param tileMap Add tileMap to the entity.
     */
    public MovingEntity(GameScreen gameScreen, int tileSize, TileMap tileMap) {
        super(gameScreen, tileSize);
        speedX = 0;
        speedY = 0;
        this.tileMap = tileMap;

    }

    public void update(){
        super.update();
        checkTileMapCollision();
        setPosition(xTemp, yTemp);

        // Direction
        if (speedX < 0) facing = false;
        else if (speedX > 0) facing = true;

        // And update chosen animation
        if(animations.size()>0) //TODO IF the entity hasn't animation.
        animations.get(animationPlayed).update(speed);
    }

    public void display(){
        if(animations.size()>0) {  //TODO IF the entity hasn't animation.
            if (facing) {
                TextureRenderer.image(
                        (posX - sizeX / 2),
                        (posY - sizeY / 2),
                        sizeX * 1f, sizeY * 1f,
                        animations.get(animationPlayed).getCurrentID(), 1f, 1f);
            } else {
                TextureRenderer.image(
                        (posX - sizeX / 2 + sizeX),
                        (posY - sizeY / 2),
                        -sizeX, sizeY,
                        animations.get(animationPlayed).getCurrentID(), 1f, 1f);
            }
        }
    }

    // When the entity died
    public void died(){
        unload();
    }

    /**
     * Calculate corners of the Entity.
     *
     * @param posX Position X.
     * @param posY Position Y.
     */
    private void calculateCorners(float posX, float posY) {
        leftTile = (int) (posX - cX / 2) / tileSize;
        rightTile = (int) (posX + cX / 2 - 1) / tileSize;
        topTile = (int) ((posY - cY / 2) / tileSize);
        bottomTile = (int) (posY + cY / 2 - 1) / tileSize;

        if (bottomTile >= tileMap.getNumRows() || rightTile >= tileMap.getNumCols()) {
            topLeft = topRight = bottomLeft = bottomRight = true;
            return;
        }

        int tl = tileMap.getType(topTile, leftTile);
        int tr = tileMap.getType(topTile, rightTile);
        int bl = tileMap.getType(bottomTile, leftTile);
        int br = tileMap.getType(bottomTile, rightTile);

        topLeft = (tl == Tile.BLOCKED);
        topRight = (tr == Tile.BLOCKED);
        bottomLeft = (bl == Tile.BLOCKED);
        bottomRight = (br == Tile.BLOCKED);
    }


    /**
     * Check the collision between the Entity and tileMap.
     */
    private void checkTileMapCollision() {

        // Get position of player in the grid
        currCol = (int) posX / tileSize;
        currRow = (int) posY / tileSize;

        // Next position
        xDest = posX + speedX;
        yDest = posY + speedY;

        // Old position
        xTemp = posX;
        yTemp = posY;


        calculateCorners(xTemp, yDest);
        if (speedY <= 0) {
            if (topLeft || topRight) {
                speedY = 0;
                yTemp = currRow * tileSize + cY / 2;
            } else {
                yTemp += speedY;
            }

            if (yDest - cY / 2 < 0) {
                yTemp = cY / 2;
                speedY = 0;
            }
        } else {
            if (bottomLeft || bottomRight) {
                speedY = 0;
                falling = false;
                yTemp = (currRow + 1) * tileSize - cY / 2;
            } else {
                yTemp += speedY;
            }
        }

        // Calculate translation of X movement
        calculateCorners(xDest, posY);
        if (speedX < 0) {
            if (topLeft || bottomLeft) {
                speedX = 0;
                xTemp = currCol * tileSize + cX / 2;
                speedY*=0.96;
            } else {
                xTemp += speedX;
            }

            if (xDest - cX / 2 < 0) {
                xTemp = cX / 2;
                speedX = 0;
            }

        } else if (speedX > 0) {
            if (topRight || bottomRight) {
                speedX = 0;
                xTemp = (currCol + 1) * tileSize - cX / 2;
                speedY*=0.96;
            } else {
                xTemp += speedX;
            }
        }

        if (!falling) {
            calculateCorners(posX, yDest + 1);
            if (!bottomLeft && !bottomRight) {
                falling = true;
            }
        }
    }

    /*
    * Setters methods
     */

    /**
     * Set the entity's speed.
     *
     * @param speedX New speed x.
     * @param speedY New speed y.
     */
    public void setSpeed(float speedX, float speedY) {
        this.speedX = speedX;
        this.speedY = speedY;
    }

    /**
     * Set the fall state.
     *
     * @param falling New falling state.
     */
    public void setFalling(boolean falling){this.falling = falling;}

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

    /**
     * Set the entity's speed x.
     *
     * @param speedX New speed x.
     */
    public void setSpeedX(float speedX){this.speedX = speedX;}

    /**
     * Set the entity's speed y.
     *
     * @param speedY New speed y.
     */
    public void setSpeedY(float speedY){this.speedY = speedY;}

    /**
     * To take damage
     * @param damage The number of the damage
     */
    public void takeDamage(int damage){
        healthPoint-=damage;
        if(healthPoint<=0){died();}
    }

    /**
     * Change the current health value.
     * @param newValue New current health value.
     */
    public void setHealthPoint(int newValue){
        if(maxHealthPoint >= newValue && newValue >= 0){
            healthPoint = newValue;
            if(healthPoint<=0){died();}
        }
    }

    /**
     * Change the max value of health.
     * @param newValue New maximum health value.
     */
    public void setMaxHealthPoint(int newValue){
        if(newValue > 0) maxHealthPoint = newValue;
        if(healthPoint > maxHealthPoint) healthPoint = maxHealthPoint;
    }

    /*
    * Getters methods
     */

    /**
     * Get the entity's speed x.
     *
     * @return The speed x.
     */
    public float getSpeedX(){return speedX;}

    /**
     * Get the entity's speed y.
     *
     * @return The speed y.
     */
    public float getSpeedY(){return speedY;}

    /**
     * Get the falling state.
     *
     * @return The falling state.
     */
    public boolean getFalling(){return falling;}

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
}
