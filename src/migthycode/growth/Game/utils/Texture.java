package migthycode.growth.Game.utils;

import static org.lwjgl.opengl.GL11.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;

public class Texture {
	
	public static int loadTexture(String path) {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(Texture.class.getResourceAsStream(path));
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
	
	public static void unload(int textId) {
		glDeleteTextures(textId);
	}
}
