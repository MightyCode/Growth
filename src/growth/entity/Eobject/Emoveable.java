package growth.entity.Eobject;

import growth.entity.module.Module;
import growth.util.math.Vec2;

import java.util.ArrayList;

public abstract class Emoveable extends Edrawable{

    protected boolean facing;

    protected boolean falling;

    protected Vec2 speed;

    public Emoveable(){
        super();
        speed = new Vec2();
    }

    public void update(){
        super.update();
    }

    public void display(){
        for(Module module : modules){
            module.display();
        }
    }

    public void died(){
        unload();
    }

    public Vec2 getSpeed() { return speed; }

    public void setSpeed(Vec2 speed) { this.speed = speed; }

    public boolean getFalling() {
        return falling;
    }

    public void setFalling(boolean newState){ falling = newState;}

    public boolean getFacing(){return facing;}
}
