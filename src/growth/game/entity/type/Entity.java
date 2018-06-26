package growth.game.entity.type;
import growth.screen.ScreenManager;
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
	 * Identifier of the entity.
	 * This variable contains the identifier of the entity. Using by the entity Manager of the game.
	 */
	protected int id;

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
	 * Entity class surcharge constructor.
	 * Instance the class and set the entity's id and the entity'stype.
	 */
	public Entity(int id, int type){this.id = id; this.type = type;}

	/**
	 * Display the entity.
	 */
	public void display(){}

	/**
	 * Update the entity.
	 */
	public void update(){}

	/**
	 * Unload the entity.
	 */
	public void unload(){ GameScreen.ENTITY_MANAGER.removeEntity(id); }

	/**
	 * Test if the entity isn't on screen.
	 */
	public boolean isOnScreen() {
		return posX + ScreenManager.CAMERA.getPosX() + sizeX/2 < 0 ||
				posX + ScreenManager.CAMERA.getPosX() - sizeX/2 > Window.WIDTH ||
				posY + ScreenManager.CAMERA.getPosY() + sizeY/2 < 0 ||
				posY + ScreenManager.CAMERA.getPosY() - sizeY/2 > Window.HEIGHT;
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
	 * Set the if of the entity.
	 *
	 * @param id Id of the entity.
	 */
	public void setId(int id){this.id = id;}

	/**
	 * Set the type of the entity.
	 *
	 * @param type New type for the entity.
	 */
	public void setType(int type) {this.type = type;}
}