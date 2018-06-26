package growth.game.entity.type;

import growth.game.entity.module.Module;
import growth.render.Animation;
import growth.render.texture.TextureRenderer;
import growth.screen.screens.GameScreen;

import java.awt.*;
import java.util.ArrayList;

public class BasicEntity extends Entity{

    /**
     * TileSize.
     * This variable contains the tileSize to interact with it.
     */
    protected final int tileSize;

    /**
     * Entity collision box size X.
     * This variable contains the width of the collision's box entity.
     */
    protected int cX;

    /**
     * Entity collision box size Y.
     * This variable contains the height of the collision's box entity.
     */
    protected int cY;


    public static final int IDLE = 0;
    public static final int IDLE_P = 0;

    /**
     * Current priority.
     * This variable contains the priority of the current animation played.
     */
    protected int priority;

    /**
     * Animations table.
     * This ArrayList contains all of the animations use by the entity.
     */
    protected ArrayList<Animation> animations;

    /**
     * Speed play of the animation.
     * This variable contains the speed play of the current animation.
     */
    protected float speed;

    /**
     * Animations played.
     * This variable contains the number of the animation played.
     */
    protected int animationPlayed;

    /**
     * Modules.
     * This list contains the module using by the entity.
     */
    protected ArrayList<Module> modules;

    /**
     * Entity class constructor.
     * Instance the class and set the tileMap.
     *
     * @param gameScreen Add tileMap to the entity.
     * @param tileSize The size of the tile.
     */
    public BasicEntity(GameScreen gameScreen, int tileSize) {
        this.gameScreen = gameScreen;
        this.tileSize = tileSize;
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
    public boolean intersects(BasicEntity entity) {
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

    public void update(){}
    public void display(){
        TextureRenderer.image(
                (posX - sizeX / 2),
                (posY - sizeY / 2),
                sizeX*1f, sizeY*1f,
                animations.get(animationPlayed).getCurrentID(),1f ,1f);
    }

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
        super.unload();
    }
}
