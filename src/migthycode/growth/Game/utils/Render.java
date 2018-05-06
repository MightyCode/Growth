package migthycode.growth.Game.utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import migthycode.growth.main.main;

public class Render {	
	
	public static void image(int posX, int posY, int sizeX, int sizeY, int textID, float alpha) {
			//System.out.println(textId + " ," + text);
			int	newPosY = main.HEIGHT-posY - sizeY;
			glActiveTexture(GL_TEXTURE0);
	        glBindTexture(GL_TEXTURE_2D, textID);
	        glColor4f(1.f,1.f,1.f,(float)alpha);
	        
	        glBegin(GL_QUADS);
	        glTexCoord2f(0.f,1.f);
	        glVertex2f(posX,newPosY);

	        glTexCoord2f(0.f,0.f);
	        glVertex2f(posX,newPosY+sizeY);

	        glTexCoord2f(1.f,0.f);
	        glVertex2f(posX+sizeX,newPosY + sizeY);

	        glTexCoord2f(1.f,1.f);
	        glVertex2f(posX+sizeX,newPosY);
	       glEnd();
	}
	
	public static void rect(int posX, int posY, int sizeX, int sizeY, int color, float alpha) {
		int	newPosY = main.HEIGHT-posY - sizeY;
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_TEXTURE);
		glColor4f(color, color, color, alpha);
		glBegin(GL_QUADS);
			glVertex2d(posX, newPosY);
		    glVertex2d(posX+sizeX, newPosY);
			glVertex2d(posX+sizeX, newPosY + sizeY);
			glVertex2d(posX, newPosY + sizeY);
		glEnd();
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_TEXTURE);
	}
	
	public static void rect(int posX, int posY, int sizeX, int sizeY, int[] color, float alpha) {
		int	newPosY = main.HEIGHT-posY - sizeY;
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_TEXTURE);
		glColor4f(color[0], color[1], color[2], alpha);
		glBegin(GL_QUADS);
			glVertex2d(posX, newPosY);
		    glVertex2d(posX+sizeX, newPosY);
			glVertex2d(posX+sizeX, newPosY + sizeY);
			glVertex2d(posX, newPosY + sizeY);
		glEnd();
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_TEXTURE);
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
