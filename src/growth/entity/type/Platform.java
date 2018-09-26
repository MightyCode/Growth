package growth.entity.type;

import growth.entity.EntityManager;
import growth.entity.Eobject.Emoveable;
import growth.entity.methods.EntityMethods;
import growth.main.Window;
import growth.screen.render.shape.ShapeRenderer;
import growth.screen.screens.GameScreen;
import growth.util.math.Color4;
import growth.util.math.Vec2;

public class Platform extends Emoveable {
    private Vec2 range;
    private Vec2 initPos;
    private Vec2 counter;
    private Vec2 duration;

    public static final int RANGEX = 6;
    public static final int RANGEY = 7;
    public static final int DURATIONX = 8;
    public static final int DURATIONY = 9;

    public Platform(String[] att){
        super(att[EntityManager.NAME]);
        counter = new Vec2(0);
        this.pos = new Vec2(Float.parseFloat(att[EntityManager.POSX]),Float.parseFloat(att[EntityManager.POSY])).multiply(GameScreen.tileSize,true);
        initPos = pos.copy();
        this.size = new Vec2(Float.parseFloat(att[EntityManager.SIZEX]),Float.parseFloat(att[EntityManager.SIZEY])).multiply(GameScreen.tileSize,true);
        this.range = new Vec2(Float.parseFloat(att[RANGEX])/2,Float.parseFloat(att[RANGEY])/2).multiply(GameScreen.tileSize,true);
        this.duration = new Vec2((float)(2*Math.PI)/(Window.TPS * Float.parseFloat(att[DURATIONX])),(float)(2*Math.PI)/(Window.TPS * Float.parseFloat(att[DURATIONY])));
    }

    public void update(){
        posTemp.setX(initPos.getX() + ((float) Math.sin(counter.getX())) * range.getX());
        posTemp.setY(initPos.getY() + ((float) Math.sin(counter.getY())) * range.getY());
        counter.setX(counter.getX() + duration.getX());
        counter.setY(counter.getY() + duration.getY());
        if(counter.getX() > 2*Math.PI) counter.setX(0);
        if(counter.getY() > 2*Math.PI) counter.setY(0);
    }

    @Override
    public void display(){
        ShapeRenderer.rect(pos, size, Color4.RED);
    }
}
