package growth.entity.module;

import growth.entity.Entity;

public abstract class Module {

    protected Entity entity;

    public Module(Entity entity){
        this.entity = entity;
    }

    public void update(){

    }
}
