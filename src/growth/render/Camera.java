package growth.render;

import growth.entity.MovingEntity;
import growth.main.Window;

import static org.lwjgl.opengl.GL11.glTranslatef;

/**
 * Screen class.
 * This class is the basic architecture of all screens.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Camera {

    /**
     * Map position x.
     * This variable contains the position y of the beginning of the rendering of the map.
     */
    private int posX;

    /**
     * Map position y.
     * This variable contains the position y of the beginning of the rendering of the map.
     */
    private int posY;

    /**
     * Tween.
     * This variable contains the smooth movement of camera
     * 1 -> rigid and immediate set new camera position.
     */
    private float tweenX;

    /**
     * Tween.
     * This variable contains the smooth movement of camera
     * 1 -> rigid and immediate set new camera position.
     */
    private float tweenY;


    /**
     * Add pixels to camera position.
     * This variable contains the number of pixels add or remove of the position of camera.
     */
    private int addCamera;

    /**
     * Max offset between the entity and the middle of screen.
     */
    private final int maxOffset = Window.WIDTH/10;

    /**
     * The entity that will follow the camera.
     */
    private MovingEntity entity;


    /**
     * Minimal position x of camera.
     * This variable contains the minimal position x of the camera use to stop the scrolling.
     */
    private int xMin;
    /**
     * Minimal position y of camera.
     * This variable contains the minimal position y of the camera use to stop the scrolling.
     */

    private int yMin;

    /**
     * Maximal position x of camera.
     * This variable contains the maximal position x of the camera use to stop the scrolling.
     */
    private int xMax;

    /**
     * Maximal position y of camera.
     * This variable contains the maximal position y of the camera use to stop the scrolling.
     */
    private int yMax;

    public Camera(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Set new origin position of map.
     *
     * @param isTween Apply the tween (true) or no (false).
     */
    public void setPosition(boolean isTween){
        int posX = Window.WIDTH / 2 - entity.getPosX();
        int posY = Window.HEIGHT / 2 - entity.getPosY();
        float speedX = entity.getSpeedX();

        if(speedX > 0) {
            addCamera -=4;
            if(-maxOffset > addCamera) addCamera = -maxOffset;
        } else if(speedX < 0){
            addCamera +=4;
            if(addCamera > maxOffset) addCamera = maxOffset;
        } else{
           addCamera/= 1.06;
        }

        if (!isTween){
            addCamera = 0;
        }

        float newTweenX = (isTween)? tweenX : 1;
        float newTweenY = (isTween)? tweenY : 1;

        glTranslatef((int)((posX - this.posX + addCamera) * newTweenX),(int)((this.posY - posY) * newTweenY),0);

        this.posX += (int)((posX - this.posX + addCamera) * newTweenX);
        this.posY += (int)((posY - this.posY) * newTweenY);
        fixBounds();
    }

    /**
     * Set the corner of the map.
     */
    private void fixBounds() {
        if(posX > xMin){
            glTranslatef(xMin - posX,0,0);
            posX = xMin;
        } else if (posX < xMax){
            glTranslatef(xMax - posX,0,0);
            posX = xMax;
        }

        if(posY > yMin){
            glTranslatef(0,yMin + posY,0);
            posY = yMin;
        }

        if (posY < yMax){
            glTranslatef(0, -yMax + posY ,0);
            posY = yMax;
        }


    }

    /**
     * Set the camera tween.
     *
     * @param tweenX Set the new tween in width.
     * @param tweenY Set the new tween in height.
     */
    public void setTween(float tweenX, float tweenY) {
        this.tweenX = tweenX;
        this.tweenY = tweenY;
    }

    /**
     * Change the entity that will follow the camera.
     *
     * @param entity The entity that will follow the camera.
     */
    public void setEntityToCamera(MovingEntity entity){
        this.entity = entity;
    }

    public void transition(int color, float alpha){
        Render.rect(-posX, -posY, Window.WIDTH, Window.HEIGHT, color, alpha);
    }

    public void setBoundMax(int xMax, int yMax){
        this.xMax = xMax;
        this.yMax = yMax;
    }

    public void setBoundMin(int xMin, int yMin){
        this.xMin = xMin;
        this.yMin = yMin;
    }

    /**
     * Return the map position x.
     *
     * @return position x
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Return the map position y.
     *
     * @return position y
     */
    public int getPosY() {
        return posY;
    }
}
