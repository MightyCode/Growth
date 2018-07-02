package growth.game;

import growth.main.Window;
import growth.render.texture.Texture;
import growth.render.texture.TextureRenderer;

/**
 * Class of the game's hud.
 *
 * @author MightyCode
 * @version of game : 1
 */
public class Hud {

    /**
     * Maximum health.
     * This variable contains the max health of the player.
     */
    private int maxHealth;

    /**
     * Current health.
     * This variable contains the current health of the player.
     */
    private int currentHealth;

    /**
     * Heart's textures.
     * These variables contain the texture for the 3 types of heart.
     */
    private Texture t_heart;
    private Texture t_halfHeart;
    private Texture t_deadHeart;

    /**
     * Heart id.
     * This table contains the type of each heart displayed.
     */
    private int[]heartType;

    /**
     * Heart position.
     * This table contains the position of each heart displayed.
     */
    private float[][]heartPos;

    /**
     * Heart size.
     * This table contains the size of each heart displayed.
     */
    private float[][]heartSize;

    /**
     * Current heart use.
     * This variable contains the number of the last useful heart.
     */
    private int currentHeartUse;

    /**
     * Heart size
     * These variables contain the base size of heart.
     */
    private final float heartSizeX;
    private final float heartSizeY;

    /**
     * Heart size
     * These variables contain the base size of heart.
     */
    private float heartSizeXT;
    private float heartSizeYT;

    /**
     * Space between two heart.
     * This variable contains the number of pixel between two heart.
     */
    private float spaceBetweenTwoHeart;

    /**
     * Number of heart..
     * This variable contains the number of displayed.
     */
    private int numHeart;

    /**
     * Sin counter.
     * This  variable contains the counter to apply the animation of the current heart use.
     */
    private float sinCounter;

    /**
     * Texture of the acorn counter.
     * This variable contains the texture use to display the acorn's counter.
     */
    private Texture acorn;

    /**
     * Texture of the acorn position.
     * These variables contain the position x and y of the acorn texture.
     */
    private final float acornPosX;
    private final float acornPosY;

    /**
     * Texture of the acorn size.
     * These variables contain the size x and y of the acorn texture.
     */
    private final float acornSizeX;
    private final float acornSizeY;

    /**
     * Hud class constructor.
     * Charge the textures.
     * And set the param
     */
    public Hud(){
        // Set the size of heart and the size, position of the acorn counter
        heartSizeX = Window.WIDTH * 0.035f;
        heartSizeY = Window.WIDTH * 0.035f;

        acornSizeX = Window.WIDTH * 0.07f;
        acornSizeY = Window.WIDTH * 0.07f;

        acornPosX = Window.WIDTH * 0.05f;
        acornPosY = Window.HEIGHT * 0.85f;

        spaceBetweenTwoHeart = heartSizeX*1.4f;

        // Set the heart's bar
        currentHealth = 0;
        maxHealth = 0;
        setMaxHealth(maxHealth);
        sinCounter = 0;
    }

    public void load(){
        // Set the textures
        t_heart = new Texture("/images/game/hud/Heart.png");
        t_halfHeart = new Texture("/images/game/hud/Heart-half.png");
        t_deadHeart = new Texture("/images/game/hud/Heart-dead.png");
        acorn = new Texture("/images/game/hud/Acorn-exemple-f.png");
    }

    /**
     * Update the Hud.
     */
    public void update(){
        float oldSizeX = heartSize[currentHeartUse][0];
        float oldSizeY = heartSize[currentHeartUse][1];

        heartSize[currentHeartUse][0] = heartSizeXT* ((float)Math.sin(sinCounter)*0.07f+1.15f);
        heartSize[currentHeartUse][1] = heartSizeYT* ((float)Math.sin(sinCounter)*0.07f+1.15f);

        heartPos[currentHeartUse][0] -= (heartSize[currentHeartUse][0] - oldSizeX)/2;
        heartPos[currentHeartUse][1] -= (heartSize[currentHeartUse][1] - oldSizeY)/2;

        sinCounter+= Math.PI*2/120;
        if(sinCounter > Math.PI*2)sinCounter = 0;
    }

    /**
     * Display the Hud.
     */
    public void display(){
        for(int i = 0; i < numHeart; i++) {
            TextureRenderer.imageC(heartPos[i][0], heartPos[i][1], heartSize[i][0], heartSize[i][1], heartType[i], 1f);
        }
        TextureRenderer.imageC(acornPosX,  acornPosY, acornSizeX, acornSizeY, acorn.getID(), 1f);
    }

    /**
     * Change display when the life of the player change.
     *
     * @param newHealth The new health point of the player.
     */
    public void setHearth(int newHealth){
        // Set the new current Health Point
        if(newHealth > maxHealth || newHealth < 0) return;
        currentHealth = newHealth;

        // Set the id of the hearth for each receptacle
        int i = 0;
        while(i < newHealth/2){
            heartType[i] = t_heart.getID();
            heartSize[i][0] = heartSizeXT;
            heartSize[i][1] = heartSizeYT;
            if(((double)newHealth)/2 == newHealth/2){
                if(i+1 == newHealth/2){
                    currentHeartUse = i;
                }
            }
            i++;
        }

        if(((double)newHealth)/2 != newHealth/2) {
            heartType[i] = t_halfHeart.getID();
            heartSize[i][0] = heartSizeXT;
            heartSize[i][1] = heartSizeYT;
            currentHeartUse = i;
            i++;
        }

        while(i < maxHealth/2){
            heartType[i] = t_deadHeart.getID();
            heartSize[i][0] = heartSizeXT * 0.9f;
            heartSize[i][1] = heartSizeYT * 0.9f;
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
                heartPos[a][0] = center - (heartSizeXT/2) + (a-(numHeart/2)) * spaceBetweenTwoHeart;
                heartPos[a][1] = Window.HEIGHT*0.02f;
            }
        }
    }

    /**
     * Change display when the life of the player change.
     *
     * @param newMaxHealth The new maximum health point of the player.
     */
    public void setMaxHealth(int newMaxHealth){
        if(newMaxHealth <= 0) return;

        heartSizeXT = heartSizeX * (1-newMaxHealth*0.01f);
        heartSizeYT = heartSizeY * (1-newMaxHealth*0.01f);
        spaceBetweenTwoHeart = heartSizeXT*1.4f;

        this.maxHealth = newMaxHealth;
        heartType = new int[(int) Math.ceil((double) newMaxHealth / 2)];
        heartPos = new float[(int) Math.ceil((double) newMaxHealth / 2)][2];
        heartSize = new float[(int) Math.ceil((double) newMaxHealth / 2)][2];
        if (currentHealth > maxHealth) currentHealth = maxHealth;
        setHearth(currentHealth);
    }

    /**
     * Unload the texture.
     */
    public void unload(){
        t_heart.unload();
        t_halfHeart.unload();
        t_deadHeart.unload();
        acorn.unload();
    }
}
