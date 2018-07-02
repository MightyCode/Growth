package growth.game.entity;

import growth.game.entity.type.Entity;

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
    }

    /**
     * Update entities on screen.
     */
    public void update(){
        for(Entity entities : entity){
            entities.update();
        }
    }

    /**
     * Display entities on the screen.
     */
    public void display(){
        for(int i = 0; i < entity.size(); i++){
            if(entity.get(i).isOnScreen()) entity.get(i).display();
        }
    }

    /**
     * Add an entity to the Array list.
     *
     * @param newEntity The new entity to add.
     */
    public void addEntity(Entity newEntity){
        // Check if a place is free
        int result = checkPlace();

        // If not
        if(result == -1){
            entity.add(newEntity);
            entity.get(entity.size()-1).setId(entity.size()-1);
        } else{
            entity.remove(checkPlace());
            entity.add(checkPlace()-1,newEntity);
            entity.get(checkPlace()).setId(checkPlace());
        }
    }

    /**
     * Remove an entity from the Array list.
     *
     * @param id The id of the entity to suppress.
     */
    public void removeEntity(int id){
        entity.remove(id);
        // Add an NULL entity
        entity.add(id, (new Entity(1,0)));
    }

    /**
     * Check if a place is free for a new entity.
     *
     * @return The identifier.
     */
    private int checkPlace(){
        for(int i = 0; i < entity.size(); i++){
            if(entity.get(i).getType() == -1) return i;
        }
        return -1;
    }

    /**
     * Remove all of the entity.
     */
    public void removeAll(){
        for(int i = 0; i < entity.size(); i++){
            entity.get(i).unload();
        }
        System.out.println("Clear " + entity.size() + " entities.");
        entity = new ArrayList<>();

    }
}
