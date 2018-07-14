package growth.render.gui;

import growth.inputs.MouseManager;
import growth.math.Vec2;

public abstract class GUIComponents {

    protected int state;
    protected Vec2 pos;
    protected Vec2 size;
    protected boolean lock;

    public GUIComponents(){
    }

    public GUIComponents(Vec2 size){
        this.size = size;
    }
    public GUIComponents(Vec2 pos, Vec2 size){
        this.pos = pos;
        this.size = size;
    }

    /**
     * Override class for what does the button.
     */
    protected void action(){}

    /**
     * Test if the mouse is over the button.
     */
    protected boolean mouseOver() {
        return  (MouseManager.mouseX() > pos.getX() &&
                MouseManager.mouseX() < pos.getX() + size.getX()) &&
                (MouseManager.mouseY() > pos.getY() &&
                        MouseManager.mouseY() < pos.getY() + size.getY());
    }

    public void setLock(boolean newState){
        lock = newState;
    }

    public void setState(int newState){
        state = newState;
    }

    public void setState(boolean newState){
       state = (newState)? 1 : 0;
    }

    public void setPos(Vec2 pos) {
        this.pos = new Vec2(pos.getX() - (size.getX() / 2), pos.getY() - (size.getY() / 2));
    }
}
