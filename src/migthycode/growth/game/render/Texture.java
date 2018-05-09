/*
 * Copyright (c) 2018. yoctoctet - All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the
 * GNU GPLv3 License.
 *
 * You should have received a copy of the GNU GPLv3 License with this
 * file. If not, please visit  https://www.gnu.org/licenses/gpl-3.0.fr.html.
 */

package migthycode.growth.game.render;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;

/**
 * Basic texture class.
 * This class is the most basic texture class possible. It holds a OpenGL Texture.
 *
 * Warning : Don't forget to use the clean() function when you do not use that texture anymore.
 *
 * @author yoctoctet
 * @version 1.0
 */
public class Texture {
    /**
     * Texture ID.
     * This variable contains the OpenGL texture ID, generated in the class constructor.
     */
    private int id;

    /**
     * Texture Width.
     * This variable contains the width of the loaded texture.
     */
    private int width;

    /**
     * Texture Width.
     * This variable contains the width of the loaded texture.
     */
    private int height;

    /**
     * Is texture loaded ?
     * This variable stores a boolean value that is false if the texture hasn't been loaded yet or has been unloaded and true if it has been loaded without error.
     */
    private boolean loaded;

    /**
     * Texture class constructor. Empty.
     */
    public Texture() {

    }

    /**
     * Bind the loaded texture.
     */
    public void bind() {
        if (isTextureLoaded()) {
            glBindTexture(GL_TEXTURE_2D, id);
        } else {
            System.err.println("[Error] Texture::bind() Binding a unloaded texture.");
        }
    }

    /**
     * Load a texture from a file.
     */
    public void load(String path) {
        id = glGenTextures();
        loaded = true;

        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
            int[] pixels = new int[image.getHeight() * image.getWidth()];

            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

            ByteBuffer buffer = BufferUtils.createByteBuffer(image.getHeight() * image.getWidth() * 4);

            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    int pixel = pixels[i * image.getWidth() + j];
                    buffer.put((byte) ((pixel >> 16) & 0xFF)); // RED
                    buffer.put((byte) ((pixel >> 8) & 0xFF)); // GREEN
                    buffer.put((byte) (pixel & 0xFF)); // BLUE
                    buffer.put((byte) ((pixel >> 24) & 0xFF)); // ALPHA
                }
            }

            buffer.flip();

            this.width = image.getWidth();
            this.height = image.getHeight();

            setParam(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
            setParam(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
            setParam(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            setParam(GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            upload(buffer);
        } catch (Exception e) {
            e.printStackTrace();
            unload();
        }
    }

    /**
     * Upload the image to OpenGL.
     *
     * @param image Image to upload.
     */
    private void upload(ByteBuffer image) {
        bind();
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
    }

    /**
     * Set OpenGL Texure Parameter.
     *
     * @param param Parameter name.
     * @param value Parameter value.
     */
    private void setParam(int param, int value) {
        bind();
        glTexParameteri(GL_TEXTURE_2D, param, value);
    }

    /**
     * Delete the texture.
     */
    public void unload() {
        if (isTextureLoaded()) {
            glDeleteTextures(id);
            loaded = false;
        } else {
            System.err.println("[Error] Texture::unload() Unloading an already unloaded texture.");
        }
    }

    /**
     * Return if the texture already has been loaded.
     *
     * @return loaded
     */
    public boolean isTextureLoaded() {
        return loaded;
    }

    /**
     * Return texture ID.
     *
     * @return id
     */
    public int getID() {
        return id;
    }
}
