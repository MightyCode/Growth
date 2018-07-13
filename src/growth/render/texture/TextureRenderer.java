package growth.render.texture;

import growth.math.Vec2;
import growth.screen.ScreenManager;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

/**
 * Texture rendering abstract class.
 * This class is only call by the Render class to display texture with gl11 technicals.
 *
 * @author MightyCode
 * @version 1.0
 */
public class TextureRenderer {

    /**
     * Display an image.
     *
     * @param posX Position x of the top-left corner image.
     * @param posY Position y of the top-left corner image.
     * @param sizeX Image's width.
     * @param sizeY Image's height.
     * @param textID ID of the image.
     * @param alpha Opacity of the image.
     * @param color The color of the image.
     */


    public static void image(float posX, float posY, float sizeX, float sizeY, int textID, float color, float alpha){
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textID);
        glColor4f(color, color, color, alpha);

        glBegin(GL_QUADS);
        glTexCoord2f(0.f, 0.f);
        glVertex2f(posX, posY);

        glTexCoord2f(0.f, 1.f);
        glVertex2f(posX, posY + sizeY);

        glTexCoord2f(1.f, 1.f);
        glVertex2f(posX + sizeX, posY + sizeY);

        glTexCoord2f(1.f, 0.f);
        glVertex2f(posX + sizeX, posY);
        glEnd();
    }

    public static void image(float posX, float posY, float sizeX, float sizeY){
        glActiveTexture(GL_TEXTURE0);
        glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        glBegin(GL_QUADS);
        glTexCoord2f(0.f, 0.f);
        glVertex2f(posX, posY);

        glTexCoord2f(0.f, 1.f);
        glVertex2f(posX, posY + sizeY);

        glTexCoord2f(1.f, 1.f);
        glVertex2f(posX + sizeX, posY + sizeY);

        glTexCoord2f(1.f, 0.f);
        glVertex2f(posX + sizeX, posY);
        glEnd();
    }

    /**
     * Display an image without the color.
     *
     * @param posX Position x of the top-left corner image.
     * @param posY Position y of the top-left corner image.
     * @param sizeX Image's width.
     * @param sizeY Image's height.
     * @param textID ID of the image.
     * @param alpha Opacity of the image.
     */
    public static void image(float posX, float posY, float sizeX, float sizeY, float textID, float alpha){
        image(posX, posY, sizeX, sizeY, (int)textID, 1f, alpha);
    }

    /**
     * Display an image.
     *
     * @param posX Position x of the top-left corner image.
     * @param posY Position y of the top-left corner image.
     * @param sizeX Image's width.
     * @param sizeY Image's height.
     * @param textID ID of the image.
     * @param alpha Opacity of the image.
     * @param color The color of the image.
     */
    public static void imageC(float posX, float posY, float sizeX, float sizeY, int textID, float color, float alpha){
        image(posX - ScreenManager.CAMERA.getPosX(), posY - ScreenManager.CAMERA.getPosY(), sizeX, sizeY, textID, color, alpha);
    }

    public static void imageC(float posX, float posY, float sizeX, float sizeY){
        image(posX - ScreenManager.CAMERA.getPosX(), posY - ScreenManager.CAMERA.getPosY(), sizeX, sizeY);
    }

    /**
     * Display an image.
     *
     * @param posX Position x of the top-left corner image.
     * @param posY Position y of the top-left corner image.
     * @param sizeX Image's width.
     * @param sizeY Image's height.
     * @param textID ID of the image.
     * @param alpha Opacity of the image.
     */
    public static void imageC(float posX, float posY, float sizeX, float sizeY, int textID, float alpha){
        image(posX - ScreenManager.CAMERA.getPosX(), posY - ScreenManager.CAMERA.getPosY(), sizeX, sizeY, textID, 1f, alpha);
    }

    /**
     * Display an image.
     *
     * @param posX Position x of the top-left corner image.
     * @param posY Position y of the top-left corner image.
     * @param sizeX Image's width.
     * @param sizeY Image's height.
     * @param textID ID of the image.
     * @param alpha Opacity of the image.
     * @param color The color of the image.
     */
    public static void image(float posX, float posY, float sizeX, float sizeY, float fromX, float fromY, float toX, float toY, int textID, float color, float alpha){
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textID);
        glColor4f(color, color, color, alpha);

        glBegin(GL_QUADS);
        glTexCoord2f(fromX, fromY);
        glVertex2f(posX, posY);

        glTexCoord2f(fromX, toY);
        glVertex2f(posX, posY + sizeY);

        glTexCoord2f(toX, toY);
        glVertex2f(posX + sizeX, posY + sizeY);

        glTexCoord2f(toX, fromY);
        glVertex2f(posX + sizeX, posY);
        glEnd();
    }

    public static void imageC(Vec2 pos, Vec2 size, float alpha){
        image(pos.getX() - ScreenManager.CAMERA.getPosX(), pos.getY() - ScreenManager.CAMERA.getPosY(), size.getX(), size.getY(), 1f, alpha);
    }

    public static void imageC(Vec2 pos, Vec2 size,int textID, float alpha){
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textID);
        image( pos.getX() - ScreenManager.CAMERA.getPosX(), pos.getY() - ScreenManager.CAMERA.getPosY(), size.getX(), size.getY(), 1f, alpha);
    }
}
