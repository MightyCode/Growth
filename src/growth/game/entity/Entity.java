package growth.game.entity;

import growth.game.render.Animation;
import growth.game.render.texture.Texture;
import growth.game.tilemap.Tile;
import growth.game.tilemap.TileMap;
import growth.main.Growth;

import java.awt.*;
import java.util.ArrayList;

/**
 * Basic entity class.
 * This class is the mother class of all entity in game, included player, enemies or moving platform.
 */
public abstract class Entity {

	/**
	 * TileMap.
	 * This variable contains the tileMap to interact with it.
	 */
	private final TileMap tileMap;

	/**
	 * TileSize.
	 * This variable contains the tileSize to interact with it.
	 */
	private final int tileSize;

	/**
	 * Map position x.
	 * This variable contains the position x of the beginning of the rendering of the map.
	 */
	double xMap;

	/**
	 * Map position y.
	 * This variable contains the position y of the beginning of the rendering of the map.
	 */
	double yMap;

	/**
	 * Entity position x.
	 * This variable contains the position x of the entity.
	 */
	double posX;

	/**
	 * Entity position Y.
	 * This variable contains the position y of the entity.
	 */
	double posY;

	/**
	 * Entity speed X.
	 * This variable contains the speed X of the entity.
	 */
	double speedX;

	/**
	 * Entity speed Y.
	 * This variable contains the speed Y of the entity.
	 */
	double speedY;

	/**
	 * Entity size X.
	 * This variable contains the width of the entity.
	 */
	int sizeX;

	/**
	 * Entity size Y.
	 * This variable contains the height of the entity.
	 */
	int sizeY;

	/**
	 * Entity collision box size X.
	 * This variable contains the width of the collision's box entity.
	 */
	int cX;

	/**
	 * Entity collision box size Y.
	 * This variable contains the height of the collision's box entity.
	 */
	int cY;

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
	private double xDest;

	/**
	 * Destination position of y.
	 * This variable contains the next position y of the entity.
	 */
	private double yDest;

	/**
	 * Temporary position x.
	 * This variable contains the temporary position in x.
	 */
	double xTemp;

	/**
	 * Temporary position y.
	 * This variable contains the temporary position in y.
	 */
	double yTemp;

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
	 * Animations table.
	 * This ArrayList contains all of the animations use by the entity.
	 */
	ArrayList<Animation> animations;

	/**
	 * Animations played.
	 * This variable contains the number of the animation played.
	 */
	int animationPlayed;

	/**
	 * Facing (true -> right // false -> left)
	 * This variable contains the direction towards where the entity is "looking".
	 */
	boolean facing;

	/**
	 * Is left.
	 * This variable contains if the entity goes to the left.
	 */
	boolean left;

	/**
	 * Is right.
	 * This variable contains if the entity goes to the right.
	 */
	boolean right;

	/**
	 * Is down.
	 * This variable contains if the entity goes to the down.
	 */
	boolean down;

	/**
	 * Is jumping.
	 * This variable contains the jumping's state.
	 */
	boolean jumping;

	/**
	 * Is falling.
	 * This variable contains the falling's state.
	 */
	boolean falling;

	/**
	 * Is sprint.
	 * This variable contains the sprint's state.
	 */
	boolean sprint;

	/**
	 * Walk speed.
	 * This variable contains the speed of walking in pixel per frame.
	 */
	double walkSpeed;

	/**
	 * Run speed.
	 * This variable contains the multiplier applied to the walking speed.
	 */
	double runSpeed;

	/**
	 * Run speed.
	 * This variable contains the max speed in pixel per frame.
	 */
	double maxSpeed;

	/**
	 * Stop speed.
	 * This variable contains the speed removed at each frame at walking speed when the entity isn't walking.
	 */
	double stopSpeed;

	/**
	 * Fall speed.
	 * This variable contains the based fall speed in pixel per frame.
	 */
	double fallSpeed;

	/**
	 * Max falling speed.
	 * This variable contains the fall speed limit.
	 */
	double maxFallSpeed;

	/**
	 * Jump start.
	 * This variable contains the jump force added to speed y.
	 */
	double jumpStart;

	/**
	 * Stop jump start.
	 * This variable contains jump speed limit.
	 */
	double stopJumpSpeed;

	/**
	 * Entity class constructor.
	 * Instance the class and set the tileMap.
	 *
	 * @param tileMap Add tileMap to the entity.
	 */
	Entity(TileMap tileMap) {
		this.tileMap = tileMap;
		this.tileSize = tileMap.getTileSize();
	}

	/**
	 * Test if th Entity meet another Entity.
	 *
	 * @param entity Another entity.
	 */
	public boolean intersects(Entity entity) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = entity.getRectangle();
		// Call the function intersects of Rectangle
		return r1.intersects(r2);
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
	 * Calculate corners of the Entity.
	 *
	 * @param posX Position X.
	 * @param posY Position Y.
	 */
	private void calculateCorners(double posX, double posY) {

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

	/*
	  Getters
	 */

	/**
	 * Return position x.
	 *
	 * @return posX
	 */
	public int getPosX() { return (int) posX; }

	/**
	 * Return position y.
	 *
	 * @return posY
	 */
	public int getPosY() { return (int) posY; }

	/**
	 * Return collision's box size x.
	 *
	 * @return cX
	 */
	public int getCX() { return cX; }

	/**
	 * Return collision's box size y.
	 *
	 * @return cY
	 */
	public int getCY() { return cY; }

	/**
	 * Return the rectangle of collision's box.
	 *
	 * @return rectangle
	 */
	private Rectangle getRectangle() {
		return new Rectangle((int) posX - cX, (int) posY - cY, cX, cY);
	}

	/*
	 * Setters
	 */

	/**
	 * Set the entity's position.
	 *
	 * @param posX New position x.
	 * @param posY New position y.
	 */
	public void setPosition(double posX, double posY) {
		this.posX = posX;
		this.posY = posY;
	}

	/**
	 * Set the entity's speed.
	 *
	 * @param speedX New speed x.
	 * @param speedY New speed y.
	 */
	public void setSpeed(double speedX, double speedY) {
		this.speedX = speedX;
		this.speedY = speedY;
	}

	/**
	 * Set the map's position.
	 */
	void setMapPosition() {
		xMap = tileMap.getPosX();
		yMap = tileMap.getPosY();
	}

	/**
	 * Set the entity's variable left.
	 *
	 * @param newState New state of left variable.
	 */
	public void setLeft(boolean newState) { left = newState; }

	/**
	 * Set the entity's variable right.
	 *
	 * @param newState New state of right variable.
	 */
	public void setRight(boolean newState) { right = newState; }

	/**
	 * Set the entity's variable down.
	 *
	 * @param newState New state of down variable.
	 */
	public void setDown(boolean newState) { down = newState; }

	/**
	 * Set the entity's variable jumping.
	 *
	 * @param newState New state of jumping variable.
	 */
	public void setJumping(boolean newState) { jumping = newState; }

	/**
	 * Set the entity's variable sprint.
	 *
	 * @param newState New state of sprint variable.
	 */
	public void setSprint(boolean newState) { sprint = newState; }

	/**
	 * Test if the entity isn't on screen.
	 */
	public boolean notOnScreen() {
		return posX + xMap + sizeX < 0 ||
				posX + xMap - sizeX > Growth.WIDTH ||
				posY + yMap + sizeY < 0 ||
				posY + yMap - sizeY > Growth.HEIGHT;
	}

	/**
	 * Delete the entity's textures contain in array list animations.
	 */
	public void unload() {
		for(Animation animation: animations) {
			animation.unload();
		}
	}
}