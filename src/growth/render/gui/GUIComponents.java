package growth.render.gui;

import growth.math.Vec2;

public abstract class GUIComponents {

    protected Vec2 pos;
    protected Vec2 size;
    protected boolean lock;

    public GUIComponents(Vec2 pos, Vec2 size){
        this.pos = pos;
        this.size = size;
    }

    /**
     * Override class for what does the button.
     */
    protected void action(){}

    public void setLock(boolean newState){
        lock = newState;
    }
}
