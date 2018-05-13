package growth.game.render.Shape;

import growth.game.render.Render;
import growth.main.Growth;

import static org.lwjgl.opengl.GL11.*;

public class ShapeRender{

    public static void rect(int posX, int posY, int sizeX, int sizeY, int color, float alpha) {
        int newPosY = Growth.HEIGHT - posY - sizeY;
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

    public static void rect(int posX, int posY, int sizeX, int sizeY, int[] color, float alpha) {
        int newPosY = Growth.HEIGHT - posY - sizeY;
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
