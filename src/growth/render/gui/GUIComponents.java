package growth.render.gui;

import growth.math.Color4;
import growth.math.Vec2;

public abstract class GUIComponents {

    protected Vec2 pos;
    protected Vec2 size;
    protected boolean lock;
    protected static final Color4 defaultLockColor = new Color4(0.3f, 0.3f, 0.3f, 1.0f);
    protected static final Color4 defaultHoverColor = Color4.BLACK;

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
