package growth.game.entity.module.entity;

import growth.game.entity.module.Module;
import growth.game.entity.type.Entity;
import growth.render.shape.ShapeRenderer;

public class Entity_RectRenderer extends Module{

    /**
     * Player.
     * This variable contains the reference to the player who use this module.
     */
    private final Entity entity;

    private int[] color;

    private float posX, posY;
    private float sizeX, sizeY;

    /**
     * Jump module class constructor.
     * Instance the class and set the reference to the entity.
     *
     * @param entity Entity using the module.
     */
    public Entity_RectRenderer(Entity entity, int[] color){
        super(entity);

        // Init variables
        this.entity = entity;
        this.color = color;
        posX = entity.getPosX();
        posY = entity.getPosY();
        sizeX = entity.getSizeX();
        sizeY = entity.getSizeY();
    }

    /**
     * Update the module and the player's jump.
     */
    public void update(){

    }

    public void display(){
        ShapeRenderer.rect(posX,posY,sizeX,sizeY,color,1f);
    }

    public void newParam(){
        posX = entity.getPosX();
        posY = entity.getPosY();
        sizeX = entity.getSizeX();
        sizeY = entity.getSizeY();
    }
}
