package growth.entity;

import growth.entity.Eobject.Edrawable;
import growth.entity.Eobject.Emoveable;
import growth.entity.Eobject.Eobject;
import growth.entity.type.Player;
import growth.main.Window;
import growth.screen.GameManager;
import growth.screen.render.texture.Texture;
import growth.screen.render.texture.TextureRenderer;
import growth.util.math.Vec2;

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
    private ArrayList<Eobject> objects;

    private Player player;

    private ArrayList<Eobject> toRemove;

    public static final int TYPE = 0;
    public static final int NAME = 1;
    public static final int POSX = 2;
    public static final int POSY = 3;
    public static final int SIZEX = 4;
    public static final int SIZEY = 5;

    /**
     * Entity manager class constructor.
     * Instance the class and set the new array list.
     */
    public EntityManager(){
        objects = new ArrayList<>();
        toRemove = new ArrayList<>();
    }

    /**
     * Update entities on screen.
     */
    public void update(){
        entityDispose();
        for(Eobject object : objects){
            object.update();
        }
        player.update();
    }

    public void dispose(){
        for(Eobject object: toRemove){
            objects.remove(object);
        }

        toRemove = new ArrayList<>();
    }

    /**
     * Display entities on the screen.
     */
    public void display(){
        for (Eobject object : objects) {
            object.display();
        }
        player.display();
    }

    public void entityDispose(){
        player.dispose();
    }

    /**
     * Add an entity to the Array list.
     *
     * @param newEntity The new entity to add.
     */
    public void addEntity(Eobject newEntity){ objects.add(newEntity);
    }

    /**
     * Remove an entity from the Array list.
     */
    public void removeEntity(Eobject entity){
        toRemove.add(entity);
    }

    /**
     * Remove all of the entity.
     */
    public void removeAll(){
        Window.console.println("\nClear " + objects.size() + " entities.\n");
        while(objects.size()>0){
            objects.get(0).unload();
            objects.remove(0);
        }
        objects = new ArrayList<>();
    }

    /**
     * Set the position of an entity
     * @param x New position x.
     * @param y New position y.
     * @param id The id of the entity.
     */
    public void setPosition(float x, float y, int id){
        objects.get(id).setPos(new Vec2(x,y));
    }

    public void setPosition(float x, float y){
        player.setPos(new Vec2(x,y));
    }
    public void setPosition(Vec2 newPos){
        player.setPos(newPos);
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    /**
     * Focus the camera to a new entity.
     * @param id The id of the entity.
     */
    public void setCamera(int id){
        GameManager.camera.setEntityToCamera((Emoveable)objects.get(id));
    }

    /**
     * Set the speed of an entity.
     * @param x New position x.
     * @param y New position Y.
     * @param id The id of the entity.
     */
    public void setSpeed(float x, float y, int id){
        ((Emoveable)objects.get(id)).setSpeed(new Vec2(x,y));
    }

    /**
     * Get an entity.
     * @param id The id of the entity.
     */
    public Eobject getEntity(int id){
        return objects.get(id);
    }

    public Player getPlayer(){
        return player;
    }

    /**
     * Get the position x of an entity.
     * @param id The id of the entity.
     * @return posX
     */
    public Vec2 getPos(int id){
        return (objects.get(id)).getPos();
    }

    public void setSpeed(int i, int i1) {
        player.setSpeed(new Vec2(i,i1));
    }

    public Vec2 getPos() {
        return player.getPos();
    }
}
