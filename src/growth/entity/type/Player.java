package growth.entity.type;

import growth.entity.module.entity.Entity_Fall;
import growth.entity.module.player.*;
import growth.entity.Eobject.Echaracter;
import growth.main.Config;
import growth.main.Growth;
import growth.screen.render.Animation;
import growth.screen.render.texture.TextureRenderer;
import growth.screen.screens.GameScreen;
import growth.game.tilemap.TileMap;
import growth.screen.screens.Screen;
import growth.util.XmlReader;
import growth.util.math.Vec2;

/**
 * Player class.
 * This class is the class of player controlled by the played.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Player extends Echaracter {

	/**
	 * Player's states.
	 * These static final variables counting the different state of player.
	 */
	public static final int WALKING = 1;
	public static final int JUMPING = 2;
	public static final int FALLING = 3;

	/**
	 * Player's animations priority.
	 * These static final variables counting the different animation's priority of player.
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
	 */
	public Player(GameScreen gameScreen, TileMap tileMap, Vec2 size) {
		// Call mother constructor
		super(gameScreen,tileMap);

		/* Init player's variables */
		// Size, and boxSize
		this.pos = new Vec2();
		this.size = size.copy();
		collisionBox = new Vec2(this.size.getX()*0.65f, size.getY());

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
		if(Growth.admin){
			modules.add(new Admin_Layer(this));
			modules.add(new Admin_PlayerHealth(this));
		}

		System.out.println(Config.getPartyPath());
		setMaxHealthPoint(Integer.parseInt(XmlReader.getValue(Config.getPartyPath() , "maxLife", "life")));
		setHealthPoint(Integer.parseInt(XmlReader.getValue(Config.getPartyPath() , "life", "life")));

		// Sprite and Animation
		facing = true;

		// Load animation and animationFrame
		animations.add(new Animation("/textures/game/entity/player/idle/", 1, 5));
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

	/**
	 * Update the player.
	 */
	public void update(){
		super.update();

		// Check border player collision to change the map
		if (pos.getX() - collisionBox.getX() / 2 <= 0) {
			GameScreen.tileMap.changeMap(0,pos.getX(), pos.getY());
		} else if (pos.getX() + collisionBox.getX() / 2 >= tileMap.getSizeX()) {
			GameScreen.tileMap.changeMap(2, pos.getX(), pos.getY());
		} else if(pos.getY() + collisionBox.getY()/ 2 >= tileMap.getSizeY()){
			if(!GameScreen.tileMap.changeMap(3, pos.getX(), pos.getY())){
				died();
				GameScreen.setState(GameScreen.STATE_DEATH);
			}
		}

		animations.get(animationPlayed).update();
	}

	public void display() {
		animations.get(animationPlayed).bind();
		if(animations.size()>0) {
			if (facing) {
				TextureRenderer.image(
						(pos.getX() - size.getX() / 2),
						(pos.getY() - size.getY() / 2),
						size.getX() * 1f, size.getY() * 1f);
			} else {
				TextureRenderer.image(
						(pos.getX() +  size.getX() * 0.5f),
						(pos.getY() - size.getY() / 2),
						- size.getX(), size.getY());
			}
		}
	}

	/**
	 * When the player die
	 */
	public void died(){
		Screen.setState(GameScreen.STATE_DEATH);
		super.died();
	}
}