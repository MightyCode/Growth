package growth.entity.Eobject;

import growth.game.tilemap.Tile;
import growth.game.tilemap.TileMap;
import growth.screen.screens.GameScreen;
import growth.util.math.Vec2;

public abstract class Echaracter extends Emoveable {

    protected GameScreen gameScreen;

    protected TileMap tileMap;

    protected Vec2 collisionBox;

    protected Vec2 posTemp;

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
        posTemp = new Vec2();
        gameScreen = gm;
        tileMap = tm;
    }

    public void update(){
        super.update();
        checkTileMapCollision();
        setPos(posTemp.copy());
        // Direction
        if (speed.getX() < 0) facing = false;
        else if (speed.getX() > 0) facing = true;
    }

    /**
     * Calculate corners of the Entity.
     *
     * @param posX Position X.
     * @param posY Position Y.
     */
    private void calculateCorners(float posX, float posY) {
        int leftTile = (int) (posX - collisionBox.getX() / 2) / GameScreen.tileSize;
        int rightTile = (int) (posX + collisionBox.getX() / 2 - 1) / GameScreen.tileSize;
        int topTile = (int) ((posY - collisionBox.getY() / 2) / GameScreen.tileSize);
        int bottomTile = (int) (posY + collisionBox.getY() / 2 - 1) / GameScreen.tileSize;

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
        int currCol = (int) pos.getX() / GameScreen.tileSize;
        int currRow = (int) pos.getY() / GameScreen.tileSize;

        // Next position
        Vec2 dest = new Vec2(pos.getX() + speed.getX(), pos.getY() + speed.getY());

        // Old position
        posTemp = pos.copy();

        calculateCorners(posTemp.getX(), dest.getY());
        if (speed.getY() <= 0) {
            if (topLeft || topRight) {
                speed.setY(0);
                posTemp.setY(currRow * GameScreen.tileSize + collisionBox.getY() / 2);
            } else {
                posTemp.setY(posTemp.getY() + speed.getY());
            }

            if (dest.getY() - collisionBox.getY() / 2 < 0) {
                posTemp.setY( collisionBox.getY() / 2);
                speed.setY(0);
            }
        } else {
            if (bottomLeft || bottomRight) {
                speed.setY(0);
                falling = false;
                posTemp.setY((currRow + 1) * GameScreen.tileSize - collisionBox.getY() / 2);
            } else {
                posTemp.setY(posTemp.getY() + speed.getY());
            }
        }

        // Calculate translation of X movement
        calculateCorners(dest.getX(), pos.getY());
        if (speed.getX() < 0) {
            if (topLeft || bottomLeft) {
                speed.setX(0);
                posTemp.setX(currCol * GameScreen.tileSize + collisionBox.getX() / 2);
                if(speed.getY()>0){
                    speed.setY(speed.getY() * 0.96f);
                }
            } else {
                posTemp.setX(posTemp.getX() + speed.getX());
            }

            if (dest.getX() - collisionBox.getX() / 2 < 0) {
                posTemp.setX(collisionBox.getX()/2);
                speed.setX(0);
            }

        } else if (speed.getX() > 0) {
            if (topRight || bottomRight) {
                speed.setX(0);
                posTemp.setX((currCol + 1) * GameScreen.tileSize - collisionBox.getX() / 2);
                if(speed.getY()>0){
                    speed.setY(speed.getY() * 0.96f);
                }
            } else {
                posTemp.setX(posTemp.getX() + speed.getX());
            }
        }

        if (!falling) {
            calculateCorners(pos.getX(), dest.getY() + 1);
            if (!bottomLeft && !bottomRight) {
                falling = true;
            }
        }
    }

    /**
     * To take damage
     *
     * @param damage The number of the damage
     */
    public void takeDamage(int damage) {
        healthPoint -= damage;
        if (healthPoint <= 0) {
            died();
        }
    }

    /**
     * Change the current health value.
     *
     * @param newValue New current health value.
     */
    public void setHealthPoint(int newValue) {
        if (maxHealthPoint >= newValue && newValue >= 0) {
            healthPoint = newValue;
            if (healthPoint <= 0) {
                died();
            }
        }
    }

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
     * Change the max value of health.
     *
     * @param newValue New maximum health value.
     */
    public void setMaxHealthPoint(int newValue) {
        if (newValue > 0) maxHealthPoint = newValue;
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
}
