package growth.game.render.Texture;

import growth.main.Growth;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class TextureRender {

    public static void image(int posX, int posY, int sizeX, int sizeY, int textID, float alpha) {
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
