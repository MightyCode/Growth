package growth.game.entity;

import growth.game.entity.type.Entity;
import growth.game.entity.type.MovingEntity;
import growth.screen.GameManager;

import java.util.ArrayList;

/**
 * Entity manager class.
 * This class is the class which manages the entities of the game.
 *
 * @author MightyCode
 * @version 1.1
 */
public class EntityManager {

    /**
     * Array list of entities.
     * This array list contains the entities of the game.
     */
    private ArrayList<Entity> entity;

    /**
     * Entity manager class constructor.
     * Instance the class and set the new array list.
     */
    public EntityManager(){
        entity = new ArrayList<>();
        toRemove = new ArrayList<>();
    }

    private ArrayList<Entity> toRemove;

    /**
     * Update entities on screen.
     */
    public void update(){
        for(Entity entities : entity){
            entities.update();
        }
    }

    public void dispose(){
        for(Entity entities: toRemove){
            entity.remove(entities);
        }

        toRemove = new ArrayList<>();
    }

    /**
     * Display entities on the screen.
     */
    public void display(){
        for (Entity anEntity : entity) {
            anEntity.display();
        }
    }

    /**
     * Add an entity to the Array list.
     *
     * @param newEntity The new entity to add.
     */
    public void addEntity(Entity newEntity){
        entity.add(newEntity);
    }

    /**
     * Remove an entity from the Array list.
     */
    public void removeEntity(Entity entity){
        toRemove.add(entity);
    }

    /**
     * Remove all of the entity.
     */
    public void removeAll(){
        System.out.println("Clear " + entity.size() + " entities.");
        for(int i = 0; i < entity.size(); i++){
            entity.get(0).unload();
            entity.remove(0);
        }
        entity = new ArrayList<>();
    }

    /**
     * Set the position of an entity
     * @param x New position x.
     * @param y New position y.
     * @param id The id of the entity.
     */
    public void setPosition(float x, float y, int id){
        entity.get(id).setPosition(x,y);
    }

    /**
     * Focus the camera to a new entity.
     * @param id The id of the entity.
     */
    public void setCamera(int id){
        GameManager.CAMERA.setEntityToCamera((MovingEntity)entity.get(id));
    }

    /**
     * Set the speed of an entity.
     * @param x New position x.
     * @param y New position Y.
     * @param id The id of the entity.
     */
    public void setSpeed(float x, float y, int id){
        ((MovingEntity)entity.get(id)).setSpeed(x,y);
    }

    /**
     * Get an entity.
     * @param id The id of the entity.
     */
    public Entity getEntity(int id){
        return entity.get(id);
    }

    /**
     * Get the position x of an entity.
     * @param id The id of the entity.
     * @return posX
     */
    public int getPosX(int id){
        return (entity.get(id)).getPosX();
    }

    /**
     * Get the position y of an entity.
     * @param id The id of the entity.
     * @return posY
     */
    public int getPosY(int id){
        return (entity.get(id)).getPosY();
    }
}
