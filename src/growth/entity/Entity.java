package growth.entity;

import growth.entity.module.Module;
import growth.render.Animation;
import growth.screen.ScreenManager;
import growth.screen.screens.GameScreen;

import java.awt.*;
import java.util.ArrayList;

/**
 * Basic entity class.
 * This class is the mother class of all entity in game, including player, enemies or moving platform.
 */
public abstract class Entity {

	/**
	 * Identifier of the entities.
	 * This variable contains the identifier of the entity. Using by the entity Manager of the game.
	 */
	private int id;

	/**
	 * Game screen.
	 * This variable contains the reference to the game screen to interact with it.
	 */
	GameScreen gameScreen;

	/**
	 * TileSize.
	 * This variable contains the tileSize to interact with it.
	 */
	int tileSize;

	/**
	 * Entity position x.
	 * This variable contains the position x of the entity.
	 */
	float posX;

	/**
	 * Entity position Y.
	 * This variable contains the position y of the entity.
	 */
	float posY;

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
	 * Temporary position x.
	 * This variable contains the temporary position in x.
	 */
	float xTemp;

	/**
	 * Temporary position y.
	 * This variable contains the temporary position in y.
	 */
	float yTemp;

	/**
	 * Current priority.
	 * This variable contains the priority of the current animation played.
	 */
	protected int priority;

	/**
	 * Animations table.
	 * This ArrayList contains all of the animations use by the entity.
	 */
	ArrayList<Animation> animations;

	/**
	 * Speed play of the animation.
	 * This variable contains the speed play of the current animation.
	 */
	protected float speed;

	/**
	 * Animations played.
	 * This variable contains the number of the animation played.
	 */
	int animationPlayed;

	/**
	 * Modules.
	 * This list contains the module using by the entity.
	 */
	ArrayList<Module> modules;

	/**
	 * Entity class constructor.
	 * Instance the class and set the tileMap.
	 *
	 * @param gameScreen Add tileMap to the entity.
	 * @param tileSize The size of the tile.
	 * @param id The id of the entity.
	 */
	Entity(GameScreen gameScreen, int tileSize, int id) {
		this.gameScreen = gameScreen;
		this.tileSize = tileSize;
		this.id = id;
		load();
	}

	/**
	 * Loading the param of the entity
	 */
	public void load(){
		priority = 0;
		speed = 1;
		posX = 0;
		posY = 0;
		sizeX = 0;
		sizeY = 0;
		cX = 0;
		cY = 0;
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
	public void setPosition(float posX, float posY) {
		this.posX = posX;
		this.posY = posY;
	}

	/**
	 * Test if the entity isn't on screen.
	 */
	public boolean notOnScreen() {
		return posX + ScreenManager.CAMERA.getPosX() + sizeX < 0 ||
				posX + ScreenManager.CAMERA.getPosX() - sizeX > Window.WIDTH ||
				posY + ScreenManager.CAMERA.getPosY() + sizeY < 0 ||
				posY + ScreenManager.CAMERA.getPosY() - sizeY > Window.HEIGHT;
	}

	/**
	 * Set the new animation if the priority of the new animation is
	 * better than the priority of the current animation.
	 *
	 * @param animationID The ID of the maybe new animation.
	 * @param priorityValue The value.
	 */
	public void setAnimations(int animationID, int priorityValue){
		if(priorityValue > priority){
			animationPlayed = animationID;
		}
	}

	/**
	 * Set the read speed of the animation.
	 *
	 * @param speed The new speed of the current animation played.
	 */
	public void setAnimationSpeed(float speed){ this.speed = speed;}

	/**
	 * Delete the entity's textures contain in array list animations.
	 */
	public void unload() {
		for(Animation animation: animations) {
			animation.unload();
		}
	}
}