package growth.game.entity;

import growth.game.entity.type.Entity;

import java.util.ArrayList;

public class EntityManager {

    private ArrayList<Entity> entity;

    public EntityManager(){
        entity = new ArrayList<>();
    }

    public void update(){
        for(Entity entities : entity){
            entities.update();
        }
    }

    public void display(){
        for(Entity entities : entity){
            if(entities.notOnScreen())
            entities.display();
        }
    }

    public void addEntity(Entity newEntity){
        int result = checkPlace();
        if(result == -1){
            entity.add(newEntity);
            entity.get(entity.size()-1).setId(entity.size()-1);
        } else{
            entity.remove(checkPlace());
            entity.add(checkPlace()-1,newEntity);
            entity.get(checkPlace()).setId(checkPlace());
        }
    }

    public void removeEntity(int id){
        entity.remove(id);
        entity.add(id, (new Entity(1,0)));
    }

    private int checkPlace(){
        for(int i = 0; i < entity.size(); i++){
            if(entity.get(i).getType() == 0) return i;
        }
        return -1;
    }

    public void removeAll(){
        entity = new ArrayList<>();
    }
}
