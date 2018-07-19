package growth.game;

import growth.main.Window;
import growth.render.texture.Texture;
import growth.render.texture.TextureRenderer;
import growth.util.math.Vec2;

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
    private Texture[]heartType;

    /**
     * Heart position.
     * This table contains the position of each heart displayed.
     */
    private Vec2[] heartPosDisplayed;

    /**
     * Heart size.
     * This table contains the size of each heart displayed.
     */
    private Vec2[] heartSizeDisplayed;

    /**
     * Current heart use.
     * This variable contains the number of the last useful heart.
     */
    private int currentHeartUse;

    /**
     * Heart size
     * These variables contain the base size of heart.
     */
    private Vec2 heartSize;

    /**
     * Heart size use on calcs.
     */
    private Vec2 heartSizeT;

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
    private Vec2 acornPos;

    /**
     * Texture of the acorn size.
     * These variables contain the size x and y of the acorn texture.
     */
    private Vec2 acornSize;

    /**
     * Hud class constructor.
     * Charge the textures.
     * And set the param
     */
    public Hud(){
        // Set the size of heart and the size, position of the acorn counter
        heartSize = new Vec2(Window.height * 0.06f,Window.height * 0.06f);
        heartSizeT = new Vec2();

        acornSize = new Vec2(Window.height * 0.07f,Window.height * 0.07f);
        acornPos = new Vec2(Window.width * 0.05f,Window.height * 0.85f);

        spaceBetweenTwoHeart = heartSize.getX()*1.4f;

        // Set the heart's bar
        currentHealth = 0;
        setMaxHealth(maxHealth = 0);
        sinCounter = 0;

        // Set the textures
        t_heart = new Texture("/textures/game/hud/Heart.png");
        t_halfHeart = new Texture("/textures/game/hud/Heart-half.png");
        t_deadHeart = new Texture("/textures/game/hud/Heart-dead.png");
        acorn = new Texture("/textures/game/hud/Acorn-exemple-f.png");
    }

    /**
     * Update the Hud.
     */
    public void update(){
        Vec2 oldSize = heartSizeDisplayed[currentHeartUse].copy();

        heartSizeDisplayed[currentHeartUse].equal(heartSizeT.multiply((float)Math.sin(sinCounter)*0.07f+1.15f,true));

        heartPosDisplayed[currentHeartUse].remove(heartSizeDisplayed[currentHeartUse].remove(oldSize, true).multiply(0.5f,true));

        sinCounter+= Math.PI*2/120;
        if(sinCounter > Math.PI*2)sinCounter = 0;
    }

    /**
     * Display the Hud.
     */
    public void display(){
        for(int i = 0; i < numHeart; i++) {
            heartType[i].bind();
            TextureRenderer.imageC(heartPosDisplayed[i], heartSizeDisplayed[i]);
        }
        acorn.bind();
        TextureRenderer.imageC(acornPos, acornSize);
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
            heartType[i] = t_heart;
            heartSizeDisplayed[i] = new Vec2();
            heartSizeDisplayed[i].equal(heartSizeT);

            if(((double)newHealth)/2 == newHealth/2){
                if(i+1 == newHealth/2){
                    currentHeartUse = i;
                }
            }
            i++;
        }

        if(((double)newHealth)/2 != newHealth/2) {
            heartType[i] = t_halfHeart;
            heartSizeDisplayed[i] = new Vec2();
            heartSizeDisplayed[i].equal(heartSizeT);
            currentHeartUse = i;
            i++;
        }

        while(i < maxHealth/2){
            heartType[i] = t_deadHeart;
            heartSizeDisplayed[i] = new Vec2();
            heartSizeDisplayed[i] = (heartSizeT.multiply(0.9f,true));
            i++;
        }

        // i count the number of heart
        numHeart = i;

        float center = Window.width/2;

        // If the number of heart is pair
        if(((double)i)/2 == i/2){
           for(int a = 0; a < numHeart; a++) {
               // Set position of each heart
               heartPosDisplayed[a] = new Vec2();
               heartPosDisplayed[a].setX(center + (a-(numHeart/2)) * spaceBetweenTwoHeart);
               heartPosDisplayed[a].setY(Window.height*0.02f);
           }
        } else {
            for(int a = 0; a < numHeart; a++) {
                // Set position of each heart
                heartPosDisplayed[a] = new Vec2();
                heartPosDisplayed[a].setX(center - (heartSize.getX()/2) + (a-(numHeart/2)) * spaceBetweenTwoHeart);
                heartPosDisplayed[a].setY(Window.height*0.02f);
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

        heartSizeT.equal(heartSize.multiply((1-newMaxHealth*0.01f),true));
        spaceBetweenTwoHeart = heartSizeT.getX()*1.4f;

        this.maxHealth = newMaxHealth;
        heartType = new Texture[(int) Math.ceil((double) newMaxHealth / 2)];
        heartPosDisplayed = new Vec2[(int) Math.ceil((double) newMaxHealth / 2)];
        heartSizeDisplayed = new Vec2[(int) Math.ceil((double) newMaxHealth / 2)];

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
