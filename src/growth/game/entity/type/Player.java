package growth.game.entity.type;

import growth.game.entity.module.entity.Entity_Fall;
import growth.game.entity.module.player.*;
import growth.main.Growth;
import growth.render.Animation;
import growth.screen.screens.GameScreen;
import growth.game.tilemap.TileMap;

import java.util.ArrayList;

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
		super(gameScreen, tileMap.getTileSize() ,tileMap);

		/* Init player's variables */
		// Size, and boxSize
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		cX = (int) (sizeX * 0.65);
		cY = sizeY;
		// Load the player
		load();
	}

	/**
	 * Load the player
	 */
	public void load(){
		// Movement
		float walkSpeed = 2.5f;
		float maxSpeed = 6f;
		float stopSpeed = 0.5f;
		float fallSpeed = 0.4f;
		float maxFallSpeed = GameScreen.TILESIZE - 2;
		float jumpStart = -13.5f;
		float stopJumpSpeed = 0.2f;
		float runSpeed = 1.45f;

		setMaxHealthPoint(2);
		setHealthPoint(2);


		// Add the modules of action to the player
		modules = new ArrayList<>();
		modules.add(new Player_Movement(this,walkSpeed, maxSpeed, stopSpeed));
		modules.add(new Entity_Fall(this, fallSpeed, maxFallSpeed));
		modules.add(new Player_Jump(this, jumpStart, stopJumpSpeed));
		modules.add(new Player_Sprint(this,(Player_Movement) modules.get(0), runSpeed));
		if(Growth.ADMIN){
			modules.add(new Admin_Layer(this));
			modules.add(new Admin_PlayerHealth(this));
		}

		// Sprite and Animation
		facing = true;
		animationPlayed = 0;

		// Load animation and animationFrame
		animations = new ArrayList<>();
		animations.add(new Animation("/images/game/entity/character/idle/", 1, 100));
		animations.add(new Animation("/images/game/entity/character/walk/", 10, 4));
		animations.add(new Animation("/images/game/entity/character/jump/", 1, 100));
		animations.add(new Animation("/images/game/entity/character/fall/", 1, 100));
	}

	/**
	 * Change the current health value.
	 * @param newValue New current health value.
	 */
	public void setHealthPoint(int newValue){
		super.setHealthPoint(newValue);
		GameScreen.HUD.setHearth(healthPoint);
	}

	/**
	 * Change the max value of health.
	 * @param newValue New maximum health value.
	 */
	public void setMaxHealthPoint(int newValue){
		super.setMaxHealthPoint(newValue);
		GameScreen.HUD.setMaxHealth(maxHealthPoint);
	}

	/**
	 * When the player die
	 */
	public void died(){
		gameScreen.setState(GameScreen.DEATHSCREEN);
		super.died();
	}
}