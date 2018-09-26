package growth.game.tilemap;

import growth.entity.Eobject.Emoveable;
import growth.game.tilemap.Tile;
import growth.screen.screens.GameScreen;
import growth.util.math.Vec2;

import java.util.ArrayList;
import java.util.Arrays;

public class MapCollision {

    ArrayList<Emoveable> collision;

    public MapCollision(){
        collision = new ArrayList<>();
    }

    public void applyCollision(){
        for(Emoveable en : collision) {
            Vec2 postemp = checkTileMapCollision(en);
            en.setPosTemp(postemp.copy());
        }
    }

    public void addEntity(Emoveable obj){
        collision.add(obj);
    }

    public void removeEntity(Emoveable obj){
        collision.remove(obj);
    }

    public void removeAll(){
        collision = new ArrayList<>();
    }

    /**
     * Calculate corners of the Entity.
     *  @param posX Position X.
     * @param posY Position Y.
     */
    private boolean[] calculateCorners(Emoveable em, float posX, float posY) {
        boolean[] stcor = new boolean[4];
        Vec2 collisionBox = em.getCollisionBox();
        int leftTile = (int) (posX - collisionBox.getX() / 2) / GameScreen.tileSize;
        int rightTile = (int) (posX + collisionBox.getX() / 2 - 1) / GameScreen.tileSize;
        int topTile = (int) ((posY - collisionBox.getY() / 2) / GameScreen.tileSize);
        int bottomTile = (int) (posY + collisionBox.getY() / 2 - 1) / GameScreen.tileSize;

        if (bottomTile >= GameScreen.tileMap.getNumRows() || rightTile >= GameScreen.tileMap.getNumCols()){
            Arrays.fill(stcor, true);
            return stcor;
        }

        int tl = GameScreen.tileMap.getType(topTile, leftTile);
        int tr = GameScreen.tileMap.getType(topTile, rightTile);
        int bl = GameScreen.tileMap.getType(bottomTile, leftTile);
        int br = GameScreen.tileMap.getType(bottomTile, rightTile);

        stcor[0] = (tl == Tile.BLOCKED);
        stcor[1] = (tr == Tile.BLOCKED);
        stcor[2] =(bl == Tile.BLOCKED);
        stcor[3] =	(br == Tile.BLOCKED);
        return stcor;
    }


    /**
     * Check the collision between the Entity and tileMap.
     */
    private Vec2 checkTileMapCollision(Emoveable em) {
        // Get position of player in the grid
        Vec2 pos = em.getPos();
        Vec2 speed = em.getSpeed();
        Vec2 collisionBox = em.getCollisionBox();

        int currCol = (int) pos.getX() / GameScreen.tileSize;
        int currRow = (int) pos.getY() / GameScreen.tileSize;

        boolean[] stcor;

        // Next position
        Vec2 dest = new Vec2(pos.getX() + speed.getX(), pos.getY() + speed.getY());

        // Old position
        Vec2 posTemp = pos.copy();

        stcor = calculateCorners(em, posTemp.getX(), dest.getY());
        if (speed.getY() <= 0) {
            if (stcor[0] || stcor[1]) {
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
            if (stcor[2] || stcor[3]) {
                speed.setY(0);
                em.setFalling(false);
                posTemp.setY((currRow + 1) * GameScreen.tileSize - collisionBox.getY() / 2);
            } else {
                posTemp.setY(posTemp.getY() + speed.getY());
            }
        }

        // Calculate translation of X movement
        stcor = calculateCorners(em, dest.getX(), pos.getY());
        if (speed.getX() < 0) {
            if (stcor[0] || stcor[2]) {
                speed.setX(0);
                posTemp.setX(currCol * GameScreen.tileSize + collisionBox.getX() / 2);
                if(speed.getY()>0){
                    speed.setY(speed.getY() * 0.95f);
                }
            } else {
                posTemp.setX(posTemp.getX() + speed.getX());
            }

            if (dest.getX() - collisionBox.getX() / 2 < 0) {
                posTemp.setX(collisionBox.getX()/2);
                speed.setX(0);
            }

        } else if (speed.getX() > 0) {
            if (stcor[1] || stcor[3]) {
                speed.setX(0);
                posTemp.setX((currCol + 1) * GameScreen.tileSize - collisionBox.getX() / 2);
                if(speed.getY()>0){
                    speed.setY(speed.getY() * 0.95f);
                }
            } else {
                posTemp.setX(posTemp.getX() + speed.getX());
            }
        }

        if (!em.getFalling()) {
            stcor = calculateCorners(em, pos.getX(), dest.getY() + 1);
            if (!stcor[2] && !stcor[3]) {
                em.setFalling(true);
            }
        }
        return posTemp;
    }

}
