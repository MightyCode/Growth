package growth.entity.type;

import growth.entity.Eobject.Edrawable;
import growth.screen.render.Animation;
import growth.screen.render.texture.TextureRenderer;
import growth.screen.screens.GameScreen;
import growth.util.math.Vec2;

public class Scenery extends Edrawable {

    public Scenery(String[] att){
        animations.add(new Animation(att[1]));
        this.pos = new Vec2(Float.parseFloat(att[2]),Float.parseFloat(att[3])).multiply(GameScreen.tileSize,true);
        this.size = new Vec2(Float.parseFloat(att[4]),Float.parseFloat(att[5])).multiply(GameScreen.tileSize,true);
    }
}
