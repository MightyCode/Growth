package migthycode.growth.Game.utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import migthycode.growth.main.main;

public class Render {
	
	private byte[] color;
	private double alpha;
	private Texture texture;
	
	public Render(Texture texture) {
		alpha = 1;
		//Set the color
		color = new byte[3];
		color[0] = (byte) 0;
		color[1] = (byte) 0;
		color[2] = (byte) 0;
		this.texture = texture;
		this.texture.init();
	}
	
	public void fill(int color) {
		this.color[0] = this.color[1] = this.color[2] = (byte)color;
	}
	
	public void fill(int color1,int color2, int color3) {
		color[0] = (byte)color1;
		color[1] = (byte)color2;
		color[2] = (byte)color3;
	}
	
	public void rect(int posX, int posY, int sizeX, int sizeY, int textId , String object) {
			int text = texture.getId(object, textId);
			//System.out.println(textId + " ," + text);
			int	newPosY = main.HEIGHT-posY - sizeY;
			glActiveTexture(GL_TEXTURE0);
	        glBindTexture(GL_TEXTURE_2D, text);
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
	
	public void rect(int posX, int posY, int sizeX, int sizeY) {
		int	newPosY = main.HEIGHT-posY - sizeY;
		glColor4f(color[0], color[1], color[2], (float)alpha);
		glBegin(GL_QUADS);
			glVertex2d(posX, newPosY);
		    glVertex2d(posX+sizeX, newPosY);
			glVertex2d(posX+sizeX, newPosY + sizeY);
			glVertex2d(posX, newPosY + sizeY);
		glEnd();
	}
	
	public void image(int posX, int posY, int sizeX, int sizeY, String object, int textID) {
		rect(posX,posY,sizeX,sizeY, textID, object);
	}
	
	public void glEnable2D() {
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

	public void glDisable2D() {
	   glMatrixMode(GL_PROJECTION);
	   glPopMatrix();   
	   glMatrixMode(GL_MODELVIEW);
	   glPopMatrix();	
	}
	
	public void setAlpha(double i) { alpha = i; }
}
