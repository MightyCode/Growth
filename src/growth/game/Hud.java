package growth.game;

import growth.main.Window;
import growth.screen.render.text.FontRenderer;
import growth.screen.render.text.StaticFonts;
import growth.screen.render.texture.Texture;
import growth.screen.render.texture.TextureRenderer;
import growth.util.TextManager;
import growth.util.math.Color4;
import growth.util.math.Math;
import growth.util.math.Vec2;

import static java.lang.Math.*;

/**
 * Class of the game's hud.
 *
 * @author MightyCode
 * @version of hud : 1.5
 */
public class Hud {

    /**
     * Health values
     */
    private int maxHealth;
    private int currentHealth;
    private int currentHeartUse;
    private int numHeart;
    private float sinCounter;

    /**
     * Heart's textures.
     */
    private Texture t_heart;
    private Texture t_halfHeart;
    private Texture t_deadHeart;
    private Texture[]heartType;

    /**
     * Heart position, size. Offset between two heart.
     */
    private Vec2[] heartPosDisplayed;
    private Vec2[] heartSizeDisplayed;
    private Vec2 heartSize;
    private Vec2 heartSizeT;
    private float spaceBetweenTwoHeart;


    /**
     * Acorn values and texture
     * This variable contains the texture use to display the acorn's counter.
     */
    private Texture acorn;
    private Vec2 acornPos;
    private Vec2 acornSize;
    private FontRenderer acornnb;

    /**
     * Location and zone system
     */
    private Texture zone, location;
    private int type;
    private int counter;
    private FontRenderer zoneFont, locationFont;

    private static final int TIME = (int)Window.TPS * 4;

    /**
     * Hud class constructor.
     * Charge the textures.
     * And set the param
     */
    public Hud(){
        super();
        // Set the size of heart and the size, position of the acorn counter
        heartSize = new Vec2(Window.height * 0.06f,Window.height * 0.06f);
        heartSizeT = new Vec2();

        acornSize = new Vec2(Window.height * 0.15f,Window.height * 0.15f);
        acornPos = new Vec2(Window.width * 0.05f,Window.height * 0.80f);

        acornnb = new FontRenderer("0", StaticFonts.monofonto, Window.width*0.03f,
                new Vec2(Window.width * 0.102f, Window.height * 0.885f), new Color4(0.0f, 0.0f, 0.0f, 1f));

        spaceBetweenTwoHeart = heartSize.getX()*1.4f;

        // Set the heart's bar
        currentHealth = 0;
        setMaxHealth(maxHealth = 0);
        sinCounter = 0;
        counter = TIME;

        // Set the textures
        t_heart = new Texture("/textures/game/hud/Heart.png");
        t_halfHeart = new Texture("/textures/game/hud/Heart-half.png");
        t_deadHeart = new Texture("/textures/game/hud/Heart-dead.png");
        acorn = new Texture("/textures/game/hud/Acorn-f.png");

        location = new Texture("/textures/game/hud/Leaf2.png");
        zone = new Texture("/textures/game/hud/Leaf1.png");

        zoneFont = new FontRenderer(4,0, StaticFonts.monofonto, Window.width*0.02f,
                new Vec2(Window.width * 0.128f, Window.height * 0.065f), Color4.BLACK.copy());

        locationFont = new FontRenderer(4,0, StaticFonts.monofonto, Window.width*0.02f,
                new Vec2(1.f), new Color4(0.2f,0.2f,0.2f,1f));
    }

    /**
     * Update the Hud.
     */
    public void update(){
        Vec2 oldSize = heartSizeDisplayed[currentHeartUse].copy();

        heartSizeDisplayed[currentHeartUse].equal(heartSizeT.multiply((float)sin(sinCounter)*0.07f+1.15f,true));
        heartPosDisplayed[currentHeartUse].remove(heartSizeDisplayed[currentHeartUse].remove(oldSize, true).multiply(0.5f,true));

        sinCounter+= PI*2/120;
        if(sinCounter > PI*2)sinCounter = 0;

        if(counter<TIME) counter++;
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
        TextureRenderer.imageC(acornPos, acornSize,0.9f);
        acornnb.renderC();

        if(counter < TIME){
            if(type == 0){
                location.bind();
                if(counter < TIME / 2){
                    TextureRenderer.imageC(Window.width*0.05f,Window.height*0.025f,
                            Window.width*0.15f,Window.height*0.09f, Math.map(counter, 0, TIME / 2, 0, 2f));
                } else{
                    TextureRenderer.imageC(Window.width * 0.05f,Window.height*0.025f,
                            Window.width*0.15f,Window.height*0.09f, Math.map(counter, TIME / 2, TIME, 2f, 0));
                    locationFont.setOpacity(Math.map(counter, TIME / 2, TIME, 2f, 0));
                }
                locationFont.renderC();

            } else if (type == 1){
                location.bind();
                if(counter < TIME / 2){
                    TextureRenderer.imageC(Window.width * 0.05f,Window.height*0.12f,
                            Window.width*0.15f,Window.height*0.09f, Math.map(counter, 0, TIME / 2, 0, 2.5f));
                    zone.bind();
                    TextureRenderer.imageC(Window.width * 0.05f,Window.height*0.025f,
                            Window.width*0.15f,Window.height*0.09f, Math.map(counter, 0, TIME / 2, 0, 2f));
                } else {
                    TextureRenderer.imageC(Window.width * 0.05f, Window.height * 0.12f,
                            Window.width * 0.15f, Window.height * 0.09f, Math.map(counter, TIME / 2, TIME, 2.5f, 0));
                    zone.bind();
                    TextureRenderer.imageC(Window.width * 0.05f, Window.height * 0.025f,
                            Window.width * 0.15f, Window.height * 0.09f, Math.map(counter, TIME / 2, TIME, 2f, 0));
                    locationFont.setOpacity(Math.map(counter, TIME / 2, TIME, 2f, 0));
                    zoneFont.setOpacity(Math.map(counter, TIME / 2, TIME, 2.5f, 0));
                }
                locationFont.renderC();
                zoneFont.renderC();
            }
        }
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
        heartType = new Texture[(int) ceil((double) newMaxHealth / 2)];
        heartPosDisplayed = new Vec2[(int) ceil((double) newMaxHealth / 2)];
        heartSizeDisplayed = new Vec2[(int) ceil((double) newMaxHealth / 2)];

        if (currentHealth > maxHealth) currentHealth = maxHealth;
        setHearth(currentHealth);
    }

    public void setLocation(String location){
        counter = 0;
        type = 0;
        locationFont.setOpacity(1);
        locationFont.setWordNumber(TextManager.LOCATION,Integer.parseInt(location));
        locationFont.setPos(new Vec2(Window.width * 0.128f, Window.height * 0.065f));
    }

    public void setZone(String zone, String location){
        counter = 0;
        type = 1;
        locationFont.setOpacity(1);
        locationFont.setWordNumber(TextManager.LOCATION, Integer.parseInt(location));
        locationFont.setPos(new Vec2(Window.width * 0.128f, Window.height * 0.16f));
        zoneFont.setOpacity(1);
        zoneFont.setWordNumber(TextManager.ZONE, Integer.parseInt(zone));
    }

    public void setAcorn(boolean state){
        if(state) acorn = new Texture("/textures/game/hud/Acorn-fl2.png");
        else acorn = new Texture("/textures/game/hud/Acorn-f.png");
    }

    public void setAcornNb(int acornNb){
        this.acornnb.setText(String.valueOf(acornNb));
    }


    /**
     * Unload the texture.
     */
    public void unload(){
        t_heart.unload();
        t_halfHeart.unload();
        t_deadHeart.unload();
        acorn.unload();
        location.unload();
        zone.unload();
    }
}
