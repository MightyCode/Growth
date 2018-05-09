package growth.game.entity;

import growth.game.render.Animation;
import growth.game.render.Texture;
import growth.game.tilemap.Tile;
import growth.game.tilemap.TileMap;

import java.util.ArrayList;

public abstract class Entity {

	// Tile stuff
	private final TileMap tileMap;
	private final int tileSize;
	double xMap;
	double yMap;

	// Position and vector
	double posX;
	double posY;
	double speedX;
	double speedY;

	// Dimensions of entity
	int sizeX;
	int sizeY;

	// Collision box
	int cX;
	int cY;

	// Collision
	private int currRow;
	private int currCol;
	private double xDest;
	private double yDest;
	double xTemp;
	double yTemp;
	private boolean topLeft;
	private boolean topRight;
	private boolean bottomLeft;
	private boolean bottomRight;

	// Animation
	ArrayList<Animation> animationFrames;
	int animationPlayed;
	boolean facingRight;
	boolean dCollisionBox;

	// Movement
	boolean left;
	boolean right;
	private boolean down;
	boolean jumping;
	boolean falling;
	boolean sprint;

	// Movement attributes
	double walkSpeed;
	double runSpeed; // Coefficient
	double maxSpeed;
	double stopSpeed;
	double fallSpeed;
	double maxFallSpeed;
	double jumpStart;
	double stopJumpSpeed;

	// Constructor
	Entity(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize();
	}

	// Test if a Entity meet another Entity
	/*public boolean intersects(Entity o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		// Call the function intersects of Rectangle
		return r1.intersects(r2);
	}*/

	// Check the collision between the Entity and tileMap
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

	// Calculate corners of the Entity
	private void calculateCorners(double x, double y) {

		int leftTile = (int) (x - cX / 2) / tileSize;
		int rightTile = (int) (x + cX / 2 - 1) / tileSize;
		int topTile = (int) (y - cY / 2) / tileSize;
		int bottomTile = (int) (y + cY / 2 - 1) / tileSize;

		if (topTile < 0 || bottomTile >= tileMap.getNumRows() ||
                leftTile < 0 || rightTile >= tileMap.getNumCols()) {
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
	* Getters
	 */
	public int getPosX() { return (int) posX; }
	public int getPosY() { return (int) posY; }
	public int getCX() { return cX; }
	public int getCY() { return cY; }

	// Get the rectangle of Entity
	/*private Rectangle getRectangle() {
		return new Rectangle((int) posX - cX, (int) posY - cY, cX, cY);
	}*/

	// Setting methods
	public void setPosition(double posX, double posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public void setSpeed(double speedX, double speedY) {
		this.speedX = speedX;
		this.speedY = speedY;
	}

	void setMapPosition() {
		xMap = tileMap.getPosX();
		yMap = tileMap.getPosY();
	}
	/*
	*Setters
	 */
	public void setLeft(boolean b) { left = b; }
	public void setRight(boolean b) { right = b; }
	public void setDown(boolean b) { down = b; }
	public void setJumping(boolean b) { jumping = b; }
	public void setSprint(boolean b) { sprint = b; }

	// Test if the Entity is on screen
	/*public boolean notOnScreen() {
		return posX + xMap + sizeX < 0 ||
				posX + xMap - sizeX > Growth.WIDTH ||
				posY + yMap + sizeY < 0 ||
				posY + yMap - sizeY > Growth.HEIGHT;
	}*/

	public void unload() {
		for(Animation animation: animationFrames) {
			Texture[] textures = animation.getTextures();
			for(Texture texture: textures) {
				texture.unload();
			}
		}
	}
}
















