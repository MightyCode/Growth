package growth.entity.type;

import growth.entity.Eobject.Edrawable;
import growth.entity.methods.EntityMethods;
import growth.main.Window;
import growth.screen.GameManager;
import growth.screen.render.Animation;
import growth.screen.render.shape.ShapeRenderer;
import growth.screen.render.texture.TextureRenderer;
import growth.screen.screens.GameScreen;
import growth.util.math.Color4;
import growth.util.math.Vec2;

public class Panel extends Edrawable {

    private int map;

    public Panel(String[] att){
        this.pos = new Vec2(Float.parseFloat(att[1]), Float.parseFloat(att[2])).multiply(GameScreen.tileSize,true);
        this.size = new Vec2(Float.parseFloat(att[3]), Float.parseFloat(att[4])).multiply(GameScreen.tileSize,true);
        animations.add(new Animation("/textures/game/entity/panel/panel.png"));
    }

    public void update() {
        super.update();
        if (EntityMethods.isCollidc(pos, size, GameScreen.entityManager.getPlayer())){
            GameScreen.entityManager.getPlayer().setAction(true);
            if (GameManager.inputsManager.inputPressed(7)) {

            }
        }
    }

    public void display(){
        animations.get(animationPlayed).bind();
        TextureRenderer.image(pos,size);
    }
}
