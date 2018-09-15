package growth.entity.type;

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
    private float counter;
    private float duration;

    public Platform(String[] att){
        counter = 0;
        this.pos = new Vec2(Float.parseFloat(att[1]),Float.parseFloat(att[2])).multiply(GameScreen.tileSize,true);
        initPos = pos.copy();
        this.size = new Vec2(Float.parseFloat(att[3]),Float.parseFloat(att[4])).multiply(GameScreen.tileSize,true);
        this.range = new Vec2(Float.parseFloat(att[5])/2,Float.parseFloat(att[6])/2).multiply(GameScreen.tileSize,true);;
        duration = Float.parseFloat(att[7]) * Window.TPS;
    }

    public void update(){
        pos.setX(initPos.getX() + ((float) Math.sin(counter)) * range.getX());
        pos.setY(initPos.getY() + ((float) Math.sin(counter)) * range.getY());
        counter+= 360/(Window.TPS*duration);
        if(counter > 360) counter = 0;
    }

    @Override
    public void display(){
        ShapeRenderer.rect(pos, size, Color4.RED);
    }
}
