package growth.game;

import growth.main.Window;
import growth.render.shape.ShapeRenderer;
import growth.render.texture.Texture;
import growth.render.texture.TextureRenderer;

public class Hud {

    private int maxHealth;

    private int currentHealth;

    private Texture t_heart, t_halfHeart, t_deadHeart;
    private int[]heartID;
    private float[][]heartPos;
    private float heartSizeX, heartSizeY;
    private float spaceBetweenTwoHeart;
    private int numHeart;

    private Texture acorn;
    private float acornPosX, acornPosY;
    private float acornSizeX, acornSizeY;


    public Hud(){
        t_heart = new Texture("/images/game/hud/Heart.png");
        t_halfHeart = new Texture("/images/game/hud/Heart-half.png");
        t_deadHeart = new Texture("/images/game/hud/Heart-dead.png");

        acorn = new Texture("/images/game/hud/Acorn-exemple-f.png");

        heartSizeX = Window.WIDTH * 0.035f;
        heartSizeY = Window.WIDTH * 0.035f;

        acornSizeX = Window.WIDTH * 0.07f;
        acornSizeY = Window.WIDTH * 0.07f;

        acornPosX = Window.WIDTH * 0.05f;
        acornPosY = Window.HEIGHT * 0.85f;

        spaceBetweenTwoHeart = heartSizeX*1.4f;

        // Tests
        currentHealth = 3;
        maxHealth = 6;
        setMaxHealth(maxHealth);
    }

    public void update(){

    }

    public void display(){

        for(int i = 0; i < numHeart; i++) {
            TextureRenderer.imageC(heartPos[i][0], heartPos[i][1], heartSizeX, heartSizeY, heartID[i], 1f);
        }

        TextureRenderer.imageC(acornPosX,  acornPosY, acornSizeX, acornSizeY, acorn.getID(), 1f);
    }

    public void setHearth(int newNumber){
        // Set the new current Health Point
        currentHealth = newNumber;

        // Set the id of the hearth for each receptacle
        int i = 0;
        while(i < newNumber/2){
            heartID[i] = t_heart.getID();
            i++;
        }

        if(((double)newNumber)/2 != newNumber/2) {
            heartID[i] = t_halfHeart.getID();
            i++;
        }

        while(i < maxHealth/2){
            heartID[i] = t_deadHeart.getID();
            i++;
        }

        // i count the number of heart
        numHeart = i;

        float center = Window.WIDTH/2;
        if(((double)i)/2 == i/2){
           for(int a = 0; a < numHeart; a++) {
               heartPos[a][0] = center + (a-(numHeart/2)) * spaceBetweenTwoHeart;
               heartPos[a][1] = Window.HEIGHT*0.01f;
           }
        } else {
            for(int a = 0; a < numHeart; a++) {
                heartPos[a][0] = center - (heartSizeX/2) + (a-(numHeart/2)) * spaceBetweenTwoHeart;
                heartPos[a][1] = Window.HEIGHT*0.01f;
            }
        }
    }

    public void setMaxHealth(int newMaxHealth){
        this.maxHealth = newMaxHealth;
        heartID = new int[(int)Math.ceil((double)newMaxHealth/2)];
        heartPos = new float[(int)Math.ceil((double)newMaxHealth/2)][2];
        setHearth(currentHealth);
    }

    public void unload(){
        t_heart.unload();
        t_halfHeart.unload();
        t_deadHeart.unload();
        acorn.unload();
    }
}
