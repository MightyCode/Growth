package growth.entity;

import growth.tilemap.Tile;
import growth.tilemap.TileMap;

/**
 * Moving entity class.
 * This class is the mother class of all entity who move.
 *
 *  @author MightyCode
 *  @version 1.0
 */
public class MovingEntity extends Entity {
    /**
     * Entity speed X.
     * This variable contains the speed X of the entity.
     */
    float speedX;

    /**
     * Entity speed Y.
     * This variable contains the speed Y of the entity.
     */
    float speedY;

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
     * Is jumping.
     * This variable contains the jumping's state.
     */
    protected boolean jumping;

    /**
     * Is falling.
     * This variable contains the falling's state.
     */
    protected boolean falling;

    /**
     * Moving Entity constructor.
     * Instance the class and set the tileMap.
     *
     * @param tileMap Add tileMap to the entity.
     */
    MovingEntity(TileMap tileMap) {
        super(tileMap);
        speedX = 0;
        speedY = 0;
    }

    /**
     * Calculate corners of the Entity.
     *
     * @param posX Position X.
     * @param posY Position Y.
     */
    private void calculateCorners(float posX, float posY) {

        int leftTile = (int) (posX - cX / 2) / tileSize;
        int rightTile = (int) (posX + cX / 2 - 1) / tileSize;
        int topTile = (int) (posY - cY / 2) / tileSize;
        int bottomTile = (int) (posY + cY / 2 - 1) / tileSize;

        if (bottomTile >= tileMap.getNumRows() ||
                rightTile >= tileMap.getNumCols()) {
            topLeft = topRight = bottomLeft = bottomRight = true;
            return;
        }

        int tl = tileMap.getType(topTile, leftTile);
        int tr = tileMap.getType(topTile, rightTile);
        int bl = tileMap.getType(bottomTile, leftTile);
        int br = tileMap.getType(bottomTile, rightTile);

        topLeft = tl == Tile.BLOCKED;
        topRight = tr == Tile.BLOCKED;
        bottomLeft = bl == Tile.BLOCKED;
        bottomRight = br == Tile.BLOCKED;
    }


    /**
     * Check the collision between the Entity and tileMap.
     */
    void checkTileMapCollision() {

        // Get position of player in the grid
        currCol = (int) posX / tileSize;
        currRow = (int) posY / tileSize;

        // Next position
        xDest = posX + speedX;
        yDest = posY + speedY;

        // Old position
        xTemp = posX;
        yTemp = posY;


        calculateCorners(posX, yDest);
        if (speedY < 0) {
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
        } else if (speedY > 0) {
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
     * Set the jump state.
     *
     * @param jumping New jump state.
     */
    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }
    public void setLeft(boolean left){
        this.left = left;
    }
    public void setRight(boolean right){
        this.right = right;
    }
    public void setSpeedX(float speedX){this.speedX = speedX;}
    public void setSpeedY(float speedY){this.speedY = speedY;}
    public void setFalling(boolean falling){this.falling = falling;}

    public float getSpeedX(){return speedX;}
    public float getSpeedY(){return speedY;}
    public boolean getFalling(){return falling;}
}
