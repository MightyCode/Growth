package growth.game.entity.type;

import growth.game.entity.module.entity.Entity_Fall;
import growth.game.entity.module.player.*;
import growth.main.Config;
import growth.main.Growth;
import growth.render.Animation;
import growth.screen.screens.GameScreen;
import growth.game.tilemap.TileMap;
import growth.screen.screens.Screen;
import growth.util.XmlReader;

/**
 * Player class.
 * This class is the class of player controlled by the played.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Player extends MovingEntity{

	/**
	 * Player's states.
	 * These static final variable counting the different state of player.
	 */
	public static final int WALKING = 1;
	public static final int JUMPING = 2;
	public static final int FALLING = 3;

	/**
	 * Player's animations priority.
	 * These static final variable counting the different state of player.
	 */
	public static final int WALKING_P = 1;
	public static final int FALLING_P = 3;
	public static final int JUMPING_P = 4;

	/**
	 * Player class constructor.
	 * Firstly call the mother class constructor.
	 * Init all of player variable.
	 *
	 * @param tileMap Add tileMap to the player.
	 * @param sizeX Add sizeX to the player.
	 * @param sizeY Add sizeY to the player.
	 */
	public Player(GameScreen gameScreen, TileMap tileMap, int sizeX, int sizeY) {
		// Call mother constructor
		super(gameScreen ,tileMap);

		/* Init player's variables */
		// Size, and boxSize
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		cX = (int) (sizeX * 0.65);
		cY = sizeY;

		int tileSize = GameScreen.tileSize;
		// Value
		float walkSpeed = tileSize/25.6f;
		float maxSpeed = tileSize/10f;
		float stopSpeed = tileSize/89f;
		float fallSpeed = tileSize/110f;
		float maxFallSpeed = tileSize - 2f;
		float jumpStart = tileSize/-4.8f;
		float stopJumpSpeed = tileSize/130f;

		// Coefficient
		float runSpeed = 1.45f;

		// Add the modules of action to the player
		modules.add(new Player_Movement(this,walkSpeed, maxSpeed, stopSpeed));
		modules.add(new Entity_Fall(this, fallSpeed, maxFallSpeed));
		modules.add(new Player_Jump(this, jumpStart, stopJumpSpeed));
		modules.add(new Player_Sprint(this,(Player_Movement) modules.get(0), runSpeed));
		if(Growth.ADMIN){
			modules.add(new Admin_Layer(this));
			modules.add(new Admin_PlayerHealth(this));
		}

		System.out.println(Config.getPartyPath());
		setMaxHealthPoint(Integer.parseInt(XmlReader.getValue(Config.getPartyPath() , "maxLife", "life")));
		setHealthPoint(Integer.parseInt(XmlReader.getValue(Config.getPartyPath() , "life", "life")));

		// Sprite and Animation
		facing = true;

		// Load animation and animationFrame
		animations.add(new Animation("/textures/game/entity/player/idle/", 1, 100));
		animations.add(new Animation("/textures/game/entity/player/walk/", 10, 4));
		animations.add(new Animation("/textures/game/entity/player/jump/", 1, 100));
		animations.add(new Animation("/textures/game/entity/player/fall/", 1, 100));
	}


	/**
	 * Change the current health value.
	 * @param newValue New current health value.
	 */
	public void setHealthPoint(int newValue){
		super.setHealthPoint(newValue);
		GameScreen.hud.setHearth(healthPoint);
	}

	/**
	 * Change the max value of health.
	 * @param newValue New maximum health value.
	 */
	public void setMaxHealthPoint(int newValue){
		super.setMaxHealthPoint(newValue);
		GameScreen.hud.setMaxHealth(maxHealthPoint);
	}

	public void update(){
		super.update();

		// Check border player collision to change the map
		if (posX - cX / 2 <= 0) {
			GameScreen.tileMap.changeMap(0, posX, posY);
		} else if (posX + cX / 2 >= tileMap.getSizeX()) {
			GameScreen.tileMap.changeMap(2, posX, posY);
		} else if(posY + cY/ 2 >= tileMap.getSizeY()){
			System.out.println("nandate");
			if(!GameScreen.tileMap.changeMap(3, posX, posY)){
				died();
				GameScreen.setState(GameScreen.DEATHSCREEN);
			}
		}
	}

	/**
	 * When the player die
	 */
	public void died(){
		Screen.setState(GameScreen.DEATHSCREEN);
		super.died();
	}
}