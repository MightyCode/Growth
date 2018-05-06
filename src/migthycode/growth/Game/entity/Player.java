package migthycode.growth.Game.entity;

import java.util.ArrayList;

import migthycode.growth.Game.screen.GameScreen;
import migthycode.growth.Game.tilemap.TileMap;
import migthycode.growth.Game.utils.Animation;
import migthycode.growth.Game.utils.Render;

public class Player extends Entity {
	// player stuff
	//private boolean dead;
	
	// Animation actions
	private int animationPlayed;
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    /*private static final int ATTACKING = 0;
    private static final int FIRING = 0;*/
	
	public Player(TileMap tm, int sizeX, int sizeY) {
		// Call mother constructor
		super(tm);
		
		/* Init player's variables */
			// Size, and boxSize
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		cX = (int)(sizeX*0.7);
		cY = sizeY;
		
			// Movement 
		walkSpeed = 2.25;
		runSpeed = 1.5;
		maxSpeed = 5.5;
		stopSpeed = 0.5;
		fallSpeed = 0.4;
		maxFallSpeed = GameScreen.TILESIZE-2;
		jumpStart = -15.2;
		stopJumpSpeed = 0.3;
		
			// Sprite and Animation
		facingRight = true;
		dCollisionBox = false;
		animationPlayed = 0;
		
		// Load animation and animationFrame
		animationFrames = new ArrayList <Animation>();
		animationFrames.add(new Animation("/images/character/idle/",1,100));
		animationFrames.add(new Animation("/images/character/walk/",6,5));
		animationFrames.add(new Animation("/images/character/idle/",1,10));
		animationFrames.add(new Animation("/images/character/idle/",1,10));
	}
	
	private void getNextPosition() {
		// Movement
		if(right) {
			speedX += walkSpeed;
			if(speedX > maxSpeed) {
				speedX = maxSpeed;
			}
		} else if(left) {
			speedX -= walkSpeed;
			if(speedX < -maxSpeed) {
				speedX = -maxSpeed;
			}
		} else {
			if(speedX > 0) {
				speedX -= stopSpeed;
				if(speedX < 0) {
					speedX = 0;
				}
			} else if(speedX < 0) {
				speedX += stopSpeed;
				if(speedX > 0) {
					speedX = 0;
				}
			}
		}
		
		// If Jumping
		if(jumping && !falling) {
			speedY = jumpStart;
			falling = true;	
		}
		
		// If Falling
		if(falling) {
			speedY += fallSpeed;
			
			if(speedY > 0) jumping = false;
			else if(speedY < 0 && !jumping) speedY += stopJumpSpeed;
			
			if(speedY > maxFallSpeed) speedY = maxFallSpeed;	
		}
		
		if(sprint && (left || right)) {
			if(speedX > 0 && right) {
				speedX = maxSpeed * runSpeed; 
			} else if(speedX < 0 && left) {
				speedX = -maxSpeed * runSpeed; 
			}
		}
	}
	
	public void update() {
		// Update position
		getNextPosition();

		checkTileMapCollision();
		setPosition(xTemp, yTemp);

        // Direction
        if (left) facingRight = false;
        if (right) facingRight = true;
        
		// Set the good animation
        if(speedY > 0) {
        	if(animationPlayed != FALLING) {
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
        animationFrames.get(animationPlayed).update();
	}
	
	public void display() {
		
		// Set the map position 
		setMapPosition();
		// Draw animation left to right if the player go the the right and invert if the player go to the invert direction
		if(facingRight) {
			Render.image(
					(int)(posX + xMap - sizeX/2), 
					(int)(posY + yMap - sizeY/2), 
					sizeX, sizeY, 
					animationFrames.get(animationPlayed).getCurrentId(), 1);
		} else {
			Render.image(
					(int)(posX + xMap - sizeX / 2 + sizeX), 
					(int)(posY + yMap - sizeY/2), 
					-sizeX, sizeY,
					animationFrames.get(animationPlayed).getCurrentId(), 1);		
		}	
	}	
}

















