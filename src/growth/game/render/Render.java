package growth.game.render;

import growth.game.render.shape.ShapeRenderer;
import growth.game.render.texture.TextureRenderer;

import static org.lwjgl.opengl.GL11.*;

public abstract class Render {

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
		TextureRenderer.image(posX, posY, sizeX, sizeY, textID, alpha);
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
	public static void rect(int posX, int posY, int sizeX, int sizeY, int color, float alpha) {
		ShapeRenderer.rect(posX,posY,sizeX,sizeY,color,alpha);
	}

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
	public static void rect(int posX, int posY, int sizeX, int sizeY, int[] color, float alpha) {
		ShapeRenderer.rect(posX,posY,sizeX,sizeY,color,alpha);
	}

	/**
	 * Set the 2D view.
	*/
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

	/**
	 * Set the 3D view.
	*/
	public static void glDisable2D() {
		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
		glPopMatrix();
	}
}
