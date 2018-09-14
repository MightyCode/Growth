package growth.entity.Eobject;

import growth.entity.module.Module;
import growth.screen.screens.GameScreen;
import growth.util.math.Vec2;

import java.util.ArrayList;

public abstract class Eobject {

    protected Vec2 pos, size;

    /**
     * Modules.
     * This list contains the module using by the entity.
     */
    protected ArrayList<Module> modules;

    public Eobject(){
        pos = new Vec2();
        size = new Vec2();
        modules = new ArrayList<>();
    }

    public void update(){
        for(Module module : modules){
            module.update();
        }
    }

    public abstract void display();

    public Vec2 getPos() { return pos; }

    public void setPos(Vec2 pos) { this.pos = pos; }

    public Vec2 getSize() { return size; }

    public void setSize(Vec2 size) { this.size = size; }

    public abstract void unload();
}
