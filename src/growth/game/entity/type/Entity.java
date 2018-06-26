package growth.game.entity.type;
import growth.screen.ScreenManager;
import growth.screen.screens.GameScreen;

import java.awt.*;

/**
 * Basic entity class.
 * This class is the mother class of all entity in game, including player, enemies or moving platform.
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
	 * Identifier of the entities.
	 * This variable contains the identifier of the entity. Using by the entity Manager of the game.
	 */
	protected int id;

	protected int type;

	/**
	 * Game screen.
	 * This variable contains the reference to the game screen to interact with it.
	 */
	protected GameScreen gameScreen;

	public Entity(){ }

	public Entity(int id, int type){this.id = id; this.type = type;}

	public Entity(int type){this.type = type; }

	public void display(){}

	public void update(){}

	public void unload(){ GameScreen.ENTITY_MANAGER.removeEntity(id); }

	/**
	 * Test if the entity isn't on screen.
	 */
	public boolean notOnScreen() {
		return posX + ScreenManager.CAMERA.getPosX() + sizeX < 0 ||
				posX + ScreenManager.CAMERA.getPosX() - sizeX > Window.WIDTH ||
				posY + ScreenManager.CAMERA.getPosY() + sizeY < 0 ||
				posY + ScreenManager.CAMERA.getPosY() - sizeY > Window.HEIGHT;
	}

	public int getType(){
		return type;
	}


	public void setId(int id){this.id = id;}

	public void setType(int type) {this.type = type;}
}