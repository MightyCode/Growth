package migthycode.growth.Game.utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture {
	
	private BufferedImage image;
	private int tileId[];
	private int playerId[];
	
	public Texture(){
		
	}
	
	public void init(){
		// Set tiles texture
		tileId = new int[8];
		tileId[0] = charge("/images/tiles/dirt.png");
		tileId[1] = charge("/images/tiles/grass.png");
		tileId[2] = charge("/images/tiles/grassLeft.png");
		tileId[3] = charge("/images/tiles/grassRight.png");
		tileId[4] = charge("/images/tiles/dirtLeft.png");
		tileId[5] = charge("/images/tiles/dirtRight.png");
		tileId[6] = charge("/images/tiles/grassBorderLeft.png");
		tileId[7] = charge("/images/tiles/grassBorderRight.png");
		
		// Set player texture
		playerId = new int[8];
		playerId[0] = charge("/images/character/idle/idle.png");
		playerId[1] = charge("/images/character/walk/0.png");
		playerId[2] = charge("/images/character/walk/1.png");
		playerId[3] = charge("/images/character/walk/2.png");
		playerId[4] = charge("/images/character/walk/3.png");
		playerId[5] = charge("/images/character/walk/4.png");
		playerId[6] = charge("/images/character/walk/5.png");
		image = null;
	}
	
	public int charge(String path) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		int[] pixels = new int[image.getHeight()*image.getWidth()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getHeight()*image.getWidth()*4);
		
		for(int i = 0; i < image.getHeight(); i++) {
			for(int j = 0; j < image.getWidth(); j++) {
				int pixel = pixels[i * image.getWidth() + j];	
                buffer.put((byte) ((pixel >> 16) & 0xFF)); // RED
                buffer.put((byte) ((pixel >> 8) & 0xFF)); // GREEN
                buffer.put((byte) (pixel & 0xFF)); // BLUE
                buffer.put((byte) ((pixel >> 24) & 0xFF)); // ALPHA
			}
		}
		
		buffer.flip();
		/* Create Texture */
		
		int text = glGenTextures();
		//glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, text);
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
		return text;
	}
	
	public int getId(String object, int arg1) {
		int index = 0;
		switch (object) {
		case "tile":
			index = tileId[arg1];
			break;
		case "player":
			index = playerId[arg1];
			break;
		}
		return index;
	}
}
