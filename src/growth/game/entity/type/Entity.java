package growth.game.entity.type;
import growth.screen.GameManager;
import growth.screen.screens.GameScreen;

import java.awt.*;

/**
 * Entity class.
 * This class is the mother class of all entity in game, including player, enemies or moving platform.
 *
 * @author MightyCode
 * @version 1.1
 */
public class Entity {

	/**
	 * Entity position x.
	 * This variable contains the position x of the entity.
	 */
	protected float posX;

	/**
	 * Entity position Y.
	 * This variable contains the position y of the entity.
	 */
	protected float posY;

	/**
	 * Entity size X.
	 * This variable contains the width of the entity.
	 */
	protected int sizeX;

	/**
	 * Entity size Y.
	 * This variable contains the height of the entity.
	 */
	protected int sizeY;

	/**
	 * Player's type.
	 * This variable contains the type of the entity (null or player for example).
	 */
	protected int type;

	/**
	 * Game screen.
	 * This variable contains the reference to the game screen to interact with it.
	 */
	protected GameScreen gameScreen;

	/**
	 * Entity class constructor.
	 * Instance the class.
	 */
	public Entity(){ }

	/**
	 * Entity class surcharge constructor.
	 * Instance the class and set the entity'stype.
	 */
	public Entity(int type){this.type = type; }


	/**
	 * Display the entity.
	 */
	public void display(){
	}

	/**
	 * Update the entity.
	 */
	public void update(){}

	/**
	 * Unload the entity.
	 */
	public void unload(){ GameScreen.entityManager.removeEntity(this); }

	/**
	 * Test if the entity isn't on screen.
	 */
	public boolean isOnScreen() {
		return posX + GameManager.CAMERA.getPosX() + sizeX/2 < 0 ||
				posX + GameManager.CAMERA.getPosX() - sizeX/2 > Window.WIDTH ||
				posY + GameManager.CAMERA.getPosY() + sizeY/2 < 0 ||
				posY + GameManager.CAMERA.getPosY() - sizeY/2 > Window.HEIGHT;
	}

	/**
	 * Get the type of the entity.
	 *
	 * @return Type.
	 */
	public int getType(){
		return type;
	}


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
	 * Return size x.
	 *
	 * @return sizeX
	 */
	public int getSizeX(){return sizeX;}

	/**
	 * Return position y.
	 *
	 * @return posY
	 */
	public int getSizeY(){return sizeY;}

	/**
	 * Set the type of the entity.
	 *
	 * @param type New type for the entity.
	 */
	public void setType(int type) {this.type = type;}

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
}