package growth.entity.type;

import growth.entity.module.entity.Entity_Fall;
import growth.entity.module.player.*;
import growth.entity.Eobject.Echaracter;
import growth.main.Config;
import growth.main.Growth;
import growth.main.Window;
import growth.screen.GameManager;
import growth.screen.render.Animation;
import growth.screen.render.texture.Texture;
import growth.screen.render.texture.TextureRenderer;
import growth.screen.screens.GameScreen;
import growth.game.tilemap.TileMap;
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

	private boolean action;
	private Texture actionT;
	private Vec2 actionSize;

	public int[][] tree;
	public int form;

	/**
	 * Player's states.
	 * These static final variables counting the different state of player.
	 */
	public static final int WALKING = 1;
	public static final int JUMPING = 2;
	public static final int FALLING = 3;
	public static final int ATTACK = 4;

	/**
	 * Player's animations priority.
	 * These static final variables counting the different animation's priority of player.
	 */
	public static final int WALKING_P = 1;
	public static final int FALLING_P = 3;
	public static final int JUMPING_P = 4;
	public static final int ATTACK_P = 5;

	/**
	 * Player class constructor.
	 * Firstly call the mother class constructor.
	 * Init all of player variable.
	 *
	 * @param tileMap Add tileMap to the player.
	 */
	public Player(GameScreen gameScreen, TileMap tileMap, Vec2 size) {
		// Call mother constructor
		super("player", gameScreen,tileMap);

		/* Init player's variables */
		// Size, and boxSize
		this.pos = new Vec2();
		this.size = size.copy();
		collisionBox = new Vec2(this.size.getX() * 0.65f, size.getY());

		int tileSize = GameScreen.tileSize;

		// Add the modules of action to the player
		float walkSpeed = tileSize/25.6f;
		float maxSpeed = tileSize/10f;
		float stopSpeed = tileSize/89f;
		modules.add(new Player_Movement(this,walkSpeed, maxSpeed, stopSpeed));

		float fallSpeed = tileSize/110f;
		float maxFallSpeed = tileSize - 2f;
		modules.add(new Entity_Fall(this, fallSpeed, maxFallSpeed));

		float runSpeed = 1.45f;
		modules.add(new Player_Sprint(this,(Player_Movement) modules.get(0), runSpeed));

		/*int attackTime = 20;
		Vec2 attackSize = size.copy();
		modules.add(new Player_Attack(this, attackTime, attackSize));*/
		if(Growth.admin){
			modules.add(new Admin_Layer(this));
			modules.add(new Admin_PlayerHealth(this));
		}

		// Sprite and Animation
		facing = true;

		// Load animation and animationFrame
		animations.add(new Animation("/textures/game/entity/player/idle/", 1, 5));
		animations.add(new Animation("/textures/game/entity/player/walk/", 10, 4));
		animations.add(new Animation("/textures/game/entity/player/jump/", 1, 100));
		animations.add(new Animation("/textures/game/entity/player/fall/", 1, 100));
		animations.add(new Animation("/textures/game/entity/player/fall/", 1, 5));

		actionT = new Texture("/textures/game/entity/player/other/action.png");
		actionSize = new Vec2(Window.height*0.04f);

		form = -1;
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
				GameManager.setState(GameScreen.STATE_DEATH);
			}
		}

		animations.get(animationPlayed).update();
	}

	@Override
	public void display() {
		super.display();
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

		if(action){
			actionT.bind();
			TextureRenderer.image(new Vec2(pos.getX() + size.getX() * 0.2f,pos.getY() - size.getY() * 0.8f), actionSize);
		}
		if(animationPlayed != oldAnimation) animations.get(oldAnimation).reset();
	}

	public void dispose(){
		action = false;
	}

	public void setAction(boolean newState){
		action = newState;
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

	public void setForm(int newForm){
		if(newForm >=1 && newForm <= 3) form = newForm;
	}

	public int getForm(){
		return form;
	}

	public void setTree(int[][] tree){
		this.tree = tree;
	}

	/**
	 * When the player die
	 */
	public void died(){
		GameManager.setState(GameScreen.STATE_DEATH);
	}
	public void unload(){
		super.unload();
		actionT.unload();
	}
}