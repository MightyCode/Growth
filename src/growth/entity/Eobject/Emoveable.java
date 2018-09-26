package growth.entity.Eobject;

import growth.entity.module.Module;
import growth.main.Window;
import growth.screen.screens.GameScreen;
import growth.util.math.Vec2;

import java.util.ArrayList;

public abstract class Emoveable extends Edrawable{

    protected boolean facing;

    protected Vec2 collisionBox;

    protected boolean falling;

    protected Vec2 posTemp;

    protected Vec2 speed;

    /**
     * Helpful values
     */
    protected Vec2 posPrevious, speedPrevious;

    public Emoveable(String name){
        super(name);
        posTemp = new Vec2();
        speed = new Vec2();
        posPrevious = new Vec2();
        speedPrevious = new Vec2();
    }

    public void disposeAfterUpdate(){
        posPrevious = pos.copy();
        speedPrevious = speed.copy();
        setPos(posTemp.copy());
    }

    public void display(){
        for(Module module : modules){
            module.display();
        }
    }

    public void died(){
        GameScreen.entityManager.removeEntity(this);
    }

    public Vec2 getSpeed() { return speed; }

    public void setSpeed(Vec2 speed) { this.speed = speed; }

    public boolean getFalling() {
        return falling;
    }

    public void setFalling(boolean newState){ falling = newState;}

    public boolean getFacing(){return facing;}

    public void setFacing(boolean newFace){
        facing = newFace;
    }

    public Vec2 getCollisionBox(){
        return collisionBox;
    }

    public Vec2 getPosTemp(){
        return posTemp;
    }

    public void setPosTemp(Vec2 posTemp){
        this.posTemp = posTemp;
    }
}
