package growth.game.render.texture;

import growth.main.Growth;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

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
     */
    public static void image(int posX, int posY, int sizeX, int sizeY, int textID, float alpha) {
        // Position y taking as a reference the top of the window
        int newPosY = Growth.HEIGHT - posY - sizeY;
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textID);
        glColor4f(1.f, 1.f, 1.f, alpha);

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
}
