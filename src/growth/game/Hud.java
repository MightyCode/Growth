package growth.game;

import growth.main.Window;
import growth.render.texture.Texture;
import growth.render.texture.TextureRenderer;

public class Hud {

    private int maxHealth;
    private int currentHealth;

    private Texture t_heart, t_halfHeart, t_deadHeart;
    private int[]heartID;
    private float[][]heartPos;
    private float[][]heartSize;
    private int currentHeartUse;
    private float heartSizeX, heartSizeY;
    private float spaceBetweenTwoHeart;
    private int numHeart;
    private float counter;

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
        counter = 0;
    }

    public void update(){
        float oldSizeX = heartSize[currentHeartUse][0];
        float oldSizeY = heartSize[currentHeartUse][1];

        heartSize[currentHeartUse][0] = heartSizeX* ((float)Math.sin(counter)*0.07f+1.15f);
        heartSize[currentHeartUse][1] = heartSizeY* ((float)Math.sin(counter)*0.07f+1.15f);

        heartPos[currentHeartUse][0] -= (heartSize[currentHeartUse][0] - oldSizeX)/2;
        heartPos[currentHeartUse][1] -= (heartSize[currentHeartUse][1] - oldSizeY)/2;

        counter+= Math.PI*2/120;
        if(counter > Math.PI*2)counter = 0;
    }

    public void display(){
        for(int i = 0; i < numHeart; i++) {
            TextureRenderer.imageC(heartPos[i][0], heartPos[i][1], heartSize[i][0], heartSize[i][1], heartID[i], 1f);
        }
        TextureRenderer.imageC(acornPosX,  acornPosY, acornSizeX, acornSizeY, acorn.getID(), 1f);
    }

    public void setHearth(int newNumber){
        // Set the new current Health Point
        if(newNumber > maxHealth || newNumber < 0) return;
        currentHealth = newNumber;

        // Set the id of the hearth for each receptacle
        int i = 0;
        while(i < newNumber/2){
            heartID[i] = t_heart.getID();
            heartSize[i][0] = heartSizeX;
            heartSize[i][1] = heartSizeY;
            if(((double)newNumber)/2 == newNumber/2){
                if(i+1 == newNumber/2){
                    currentHeartUse = i;
                }
            }
            i++;
        }

        if(((double)newNumber)/2 != newNumber/2) {
            heartID[i] = t_halfHeart.getID();
            heartSize[i][0] = heartSizeX;
            heartSize[i][1] = heartSizeY;
            currentHeartUse = i;
            i++;
        }

        while(i < maxHealth/2){
            heartID[i] = t_deadHeart.getID();
            heartSize[i][0] = heartSizeX * 0.9f;
            heartSize[i][1] = heartSizeY * 0.9f;
            i++;
        }

        // i count the number of heart
        numHeart = i;

        float center = Window.WIDTH/2;

        // If the number of heart is pair
        if(((double)i)/2 == i/2){
           for(int a = 0; a < numHeart; a++) {
               // Set position of each heart
               heartPos[a][0] = center + (a-(numHeart/2)) * spaceBetweenTwoHeart;
               heartPos[a][1] = Window.HEIGHT*0.02f;
           }
        } else {
            for(int a = 0; a < numHeart; a++) {
                // Set position of each heart
                heartPos[a][0] = center - (heartSizeX/2) + (a-(numHeart/2)) * spaceBetweenTwoHeart;
                heartPos[a][1] = Window.HEIGHT*0.02f;
            }
        }
    }

    public void setMaxHealth(int newMaxHealth){
        this.maxHealth = newMaxHealth;
        heartID = new int[(int)Math.ceil((double)newMaxHealth/2)];
        heartPos = new float[(int)Math.ceil((double)newMaxHealth/2)][2];
        heartSize = new float[(int)Math.ceil((double)newMaxHealth/2)][2];
        if(currentHealth > maxHealth)currentHealth = maxHealth;
        setHearth(currentHealth);
    }

    public void unload(){
        t_heart.unload();
        t_halfHeart.unload();
        t_deadHeart.unload();
        acorn.unload();
    }
}
