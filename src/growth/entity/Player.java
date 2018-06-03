package growth.entity;

import growth.render.Animation;
import growth.render.Render;
import growth.screen.ScreenManager;
import growth.screen.screens.GameScreen;
import growth.tilemap.TileMap;
import growth.utils.KeyboardManager;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Player class.
 * This class is the class of player controlled by the played.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Player extends Entity {

	/**
	 * Player's states.
	 * These static final variable counting the different state of player.
	 */
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;

	/**
	 * Player class constructor.
	 * Firstly call the mother class constructor.
	 * Init all of player variable.
	 *
	 * @param tileMap Add tileMap to the player.
	 * @param sizeX Add sizeX to the player.
	 * @param sizeY Add sizeY to the player.
	 */
	public Player(TileMap tileMap, int sizeX, int sizeY) {
		// Call mother constructor
		super(tileMap);

		/* Init player's variables */
			// Size, and boxSize
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		cX = (int) (sizeX * 0.7);
		cY = sizeY;

			// Movement
		walkSpeed = 2.25;
		runSpeed = 1.5;
		maxSpeed = 5.5;
		stopSpeed = 0.5;
		fallSpeed = 0.4;
		maxFallSpeed = GameScreen.TILESIZE - 2;
		jumpStart = -15.2;
		stopJumpSpeed = 0.3;

			// Sprite and Animation
		facing = true;
		animationPlayed = 0;

			// Load animation and animationFrame
		animations = new ArrayList<>();
		animations.add(new Animation("/images/character/idle/", 1, 100));
		animations.add(new Animation("/images/character/walk/", 10, 4));
		animations.add(new Animation("/images/character/jump/", 1, 100));
		animations.add(new Animation("/images/character/fall/", 1, 100));
	}

	/**
	 * Update the player's position, states and this current animation.
	 */
	public void update() {
		// Update position
		checkKeys();
		getNextPosition();

		checkTileMapCollision();
		setPosition(xTemp, yTemp);

		// Direction
		if (speedX<0) facing = false;
		else if (speedX > 0) facing = true;

		// Set the good animation
		if (speedY > 0) {
			if (animationPlayed != FALLING) {
				animationPlayed = FALLING;
			}
		} else if (speedY < 0) {
			if (animationPlayed != JUMPING) {
				animationPlayed = JUMPING;
			}
		} else if (left || right) {
			if (animationPlayed != WALKING) {
				animationPlayed = WALKING;
			}
		} else {
			if (animationPlayed != IDLE) {
				animationPlayed = IDLE;
			}
		}

		// And update chosen animation
		animations.get(animationPlayed).update();
	}

	/**
	 * Update the keys
	 */
	private void checkKeys(){
		// Key update
		// Key update
		jumping = ScreenManager.KEY.key(2);

		down = ScreenManager.KEY.key(4);

		left = ScreenManager.KEY.key(1);

		right = ScreenManager.KEY.key(3);

		sprint = ScreenManager.KEY.key(5);
	}

	/**
	 * Get the next position of player taking into account the player's orders.
	 */
	private void getNextPosition() {
		// Movement
		if (right) {
			speedX += walkSpeed;
			if (speedX > maxSpeed) {
				speedX = maxSpeed;
			}
		} else if (left) {
			speedX -= walkSpeed;
			if (speedX < -maxSpeed) {
				speedX = -maxSpeed;
			}
		} else {
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

		// If Jumping
		if (jumping && !falling) {
			speedY = jumpStart;
			falling = true;
		}

		// If Falling
		if (falling) {
			speedY += fallSpeed;

			if (speedY > 0) jumping = false;
			else if (speedY < 0 && !jumping) speedY += stopJumpSpeed;

			if (speedY > maxFallSpeed) speedY = maxFallSpeed;
		}

		if (sprint && (left || right)) {
			if (speedX > 0 && right) {
				speedX = maxSpeed * runSpeed;
			} else if (speedX < 0 && left) {
				speedX = -maxSpeed * runSpeed;
			}
		}
	}

	/**
	 * Display the player with his good animation.
	 */
	public void display() {

		// Set the map position
		setMapPosition();
		// Draw animation left to right if the player go the the right and invert if the player go to the invert direction
		if (facing) {
			Render.image(
					(int) (posX + xMap - sizeX / 2),
					(int) (posY + yMap - sizeY / 2),
					sizeX, sizeY,
					animations.get(animationPlayed).getCurrentID(), 1);
		} else {
			Render.image(
					(int) (posX + xMap - sizeX / 2 + sizeX),
					(int) (posY + yMap - sizeY / 2),
					-sizeX, sizeY,
					animations.get(animationPlayed).getCurrentID(), 1);
		}
	}
}