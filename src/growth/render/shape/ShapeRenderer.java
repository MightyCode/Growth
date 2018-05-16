package growth.render.shape;

import growth.main.Window;

import static org.lwjgl.opengl.GL11.*;

/**
 * Shape rendering abstract class.
 * This class is only call by the Render class to display different shape
 *
 * @author MightyCode
 * @version 1.0
 */
public abstract class ShapeRenderer {

    /**
     * Display an multicolour rectangle.
     *
     * @param posX Position x of the top-left corner image.
     * @param posY Position y of the top-left corner image.
     * @param sizeX Image's width.
     * @param sizeY Image's height.
     * @param color Colour of the rectangle.
     * @param alpha Opacity of the image.
     */
    public static void rect(int posX, int posY, int sizeX, int sizeY, int color, float alpha) {
        int newPosY = Window.HEIGHT - posY - sizeY;
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_TEXTURE);
        glColor4f(color, color, color, alpha);
        glBegin(GL_QUADS);
            glVertex2d(posX, newPosY);
            glVertex2d(posX + sizeX, newPosY);
            glVertex2d(posX + sizeX, newPosY + sizeY);
            glVertex2d(posX, newPosY + sizeY);
        glEnd();
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_TEXTURE);
    }

    /**
     * Display an black shades rectangle.
     *
     * @param posX Position x of the top-left corner image.
     * @param posY Position y of the top-left corner image.
     * @param sizeX Image's width.
     * @param sizeY Image's height.
     * @param color Black shades colour.
     * @param alpha Opacity of the image.
     */
    public static void rect(int posX, int posY, int sizeX, int sizeY, int[] color, float alpha) {
        int newPosY = Window.HEIGHT - posY - sizeY;
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_TEXTURE);
        glColor4f(color[0], color[1], color[2], alpha);
        glBegin(GL_QUADS);
        glVertex2d(posX, newPosY);
        glVertex2d(posX + sizeX, newPosY);
        glVertex2d(posX + sizeX, newPosY + sizeY);
        glVertex2d(posX, newPosY + sizeY);
        glEnd();
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_TEXTURE);
    }
}
