package growth.render.texture;

import growth.main.Window;

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
public abstract class TextureRenderer {

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
    private static void image(int posX, int posY, int sizeX, int sizeY, int textID, float color, float alpha){
        // Position y taking as a reference the top of the window
        int newPosY = (Window.HEIGHT - posY - sizeY);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textID);
        glColor4f(color, color, color, alpha);

        glBegin(GL_QUADS);
        glTexCoord2f(0.f, 1.f);
        glVertex2f(posX, newPosY);

        glTexCoord2f(0.f, 0.f);
        glVertex2f(posX, newPosY + sizeY);

        glTexCoord2f(1.f, 0.f);
        glVertex2f(posX + sizeX, newPosY + sizeY);

        glTexCoord2f(1.f, 1.f);
        glVertex2f(posX + sizeX, newPosY);
        glEnd();
    }


    /**
     * Surcharge of previous class, replace int position and size to double position and size.
     */
    public static void image(double posX, double posY, double sizeX, double sizeY, int textID, float  color ,float alpha) {
        image((int)posX, (int)posY, (int)sizeX, (int)sizeY, textID, color ,alpha);
    }

}
