package growth.game;

import growth.main.Window;
import growth.render.shape.ShapeRenderer;
import growth.render.texture.Texture;
import growth.render.texture.TextureRenderer;

public class Hud {

    private int maxHealth;

    private int currentHealth;

    private int heart;

    private int halfHeart;

    private int deadHeart;

    private Texture t_heart, t_halfHeart, t_deadHeart;

    public Hud(){
        t_heart = new Texture("/images/game/hud/Heart.png");
        t_halfHeart = new Texture("/images/game/hud/Heart-half.png");
        t_deadHeart = new Texture("/images/game/hud/Heart-dead.png");

        // Tests
        currentHealth = 4;
        maxHealth = 4;
        heart = 2;
        halfHeart = 0;
        deadHeart = 0;
    }

    public void update(){

    }

    public void display(){
        ShapeRenderer.rectC(Window.WIDTH*0.015f,Window.HEIGHT*0.015f,Window.WIDTH*0.045f * currentHealth, Window.WIDTH*0.050f,0,0.2f);
        for(int i = 0; i < heart; i++){
            TextureRenderer.imageC(Window.WIDTH*0.03f*2*i + Window.WIDTH*0.03f,Window.HEIGHT*0.03f,Window.WIDTH*0.045f,Window.WIDTH*0.045f,t_heart.getID(),1f,1f);
        }
    }

    public void setHearth(int newNumber){
        if(Math.floor(newNumber/2) == newNumber){
            heart = newNumber/2;
            deadHeart = maxHealth - newNumber/2;
            halfHeart = 0;
        } else{
            heart = (newNumber-1)/2;
            deadHeart = maxHealth - (newNumber-1)/2;
            halfHeart = 1;
        }
    }

    public void setMaxHealth(int newMaxHealth){
        this.maxHealth = newMaxHealth;
        setHearth(currentHealth);
    }

    public void unload(){
        t_heart.unload();
        t_halfHeart.unload();
        t_deadHeart.unload();
    }
}
