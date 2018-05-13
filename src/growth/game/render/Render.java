package growth.game.render;

import growth.game.render.Font.FontRender;
import growth.game.render.Font.GameFont;
import growth.game.render.Shape.ShapeRender;
import growth.game.render.Texture.TextureRender;

import static org.lwjgl.opengl.GL11.*;

public class Render {


	/**
	 * Definition of methods call in different ...Render
	 */
	public static void image(int posX, int posY, int sizeX, int sizeY, int textID, float alpha) {
		TextureRender.image(posX, posY, sizeX, sizeY, textID, alpha);
	}

	public static void rect(int posX, int posY, int sizeX, int sizeY, int color, float alpha) {
		ShapeRender.rect(posX,posY,sizeX,sizeY,color,alpha);
	}

	public static void rect(int posX, int posY, int sizeX, int sizeY, int[] color, float alpha) {
		ShapeRender.rect(posX,posY,sizeX,sizeY,color,alpha);
	}

	public static void text(String text, GameFont font, int color, int alpha){
		FontRender.text(text, font, color, alpha);
	}

	public static void text(String text, GameFont font, int[] color, int alpha){
		FontRender.text(text, font, color, alpha);
	}

	public static void glEnable2D() {
		int[] vPort = new int[4];

		glGetIntegerv(GL_VIEWPORT, vPort);

		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();

		glOrtho(0, vPort[2], 0, vPort[3], -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		glLoadIdentity();
	}

	public static void glDisable2D() {
		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
		glPopMatrix();
	}
}
