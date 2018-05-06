package migthycode.growth.game.entity;

import migthycode.growth.game.tilemap.Tile;
import migthycode.growth.game.tilemap.TileMap;
import migthycode.growth.game.utils.Animation;
import migthycode.growth.game.utils.Render;
import migthycode.growth.game.utils.Texture;
import migthycode.growth.main.main;

import java.awt.*;
import java.util.ArrayList;

public abstract class Entity {

	// Tile stuff
	protected TileMap tileMap;
	protected int tileSize;
	protected double xMap;
	protected double yMap;

	// Position and vector
	protected double posX;
	protected double posY;
	protected double speedX;
	protected double speedY;

	// Dimensions of entity
	protected int sizeX;
	protected int sizeY;

	// Collision box
	protected int cX;
	protected int cY;

	// Collision
	protected int currRow;
	protected int currCol;
	protected double xDest;
	protected double yDest;
	protected double xTemp;
	protected double yTemp;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;

	// Animation
	protected ArrayList<Animation> animationFrames;
	protected int currentAction;
	protected int previousAction;
	protected boolean facingRight;
	protected boolean dCollisionBox;

	// Movement
	protected boolean left;
	protected boolean right;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;
	protected boolean sprint;

	// Movement attributes
	protected double walkSpeed;
	protected double runSpeed; // Coefficient
	protected double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;

	// Constructor
	public Entity(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize();
	}

	// Test if a Entity meet another Entity
	public boolean intersects(Entity o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		// Call the function intersects of Rectangle
		return r1.intersects(r2);
	}

	// Check the collision between the Entity and tileMap
	public void checkTileMapCollision() {

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
				yTemp = 0 + cY / 2;
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
				xTemp = 0 + cX / 2;
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
	public void calculateCorners(double x, double y) {

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

	// Getting methods
	public int getPosX() {
		return (int) posX;
	}

	public int getPosY() {
		return (int) posY;
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public int getCX() {
		return cX;
	}

	public int getCY() {
		return cY;
	}

	// Get the rectangle of Entity
	public Rectangle getRectangle() {
		return new Rectangle((int) posX - cX, (int) posY - cY, cX, cY);
	}

	// Setting methods
	public void setPosition(double posX, double posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public void setSpeed(double speedX, double speedY) {
		this.speedX = speedX;
		this.speedY = speedY;
	}

	public void setMapPosition() {
		xMap = tileMap.getx();
		yMap = tileMap.gety();
	}

	public void setLeft(boolean b) {
		left = b;
	}

	public void setRight(boolean b) {
		right = b;
	}

	public void setDown(boolean b) {
		down = b;
	}

	public void setJumping(boolean b) {
		jumping = b;
	}

	public void setSprint(boolean b) {
		sprint = b;
	}

	// Test if the Entity is on screen
	public boolean notOnScreen() {
		return posX + xMap + sizeX < 0 ||
				posX + xMap - sizeX > main.WIDTH ||
				posY + yMap + sizeY < 0 ||
				posY + yMap - sizeY > main.HEIGHT;
	}

	// Animation method to draw a collision box
	public void displayCollisionBox(Render render) {
		Render.rect((int) (posX - cX / 2 + xMap), (int) (posY - cY / 2 + yMap), cX, cY, 0, 1);
	}

	public void unload() {
		for (int i = 0; i < animationFrames.size(); i++) {
			int[] textID = animationFrames.get(i).getTextID();
			for (int j = 0; j < textID.length; j++) {
				Texture.unload(textID[j]);
			}
		}
	}
}
















