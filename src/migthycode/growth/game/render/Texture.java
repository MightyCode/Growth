package migthycode.growth.game.render;

import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.stackPush;

/**
 * Basic texture class.
 * This class is the most basic texture class possible. It holds a OpenGL Texture.
 * <p>
 * Warning : Don't forget to use the clean() function when you do not use that texture anymore.
 *
 * @author yoctoctet
 * created on 2018/05/08
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
            // TODO: Handle Exception
        }
    }

    /**
     * Load a texture from a file.
     */
    public void load(String path) throws RuntimeException {
        id = glGenTextures();
        loaded = true;

        ByteBuffer image;

        try (MemoryStack stack = stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            stbi_set_flip_vertically_on_load(true);

            image = stbi_load(getClass().getResource(path).getFile(), w, h, comp, 4);

            if (image == null) {
                throw new RuntimeException("Failed to load a texture file!" + System.lineSeparator() + stbi_failure_reason());
            }

            stbi_image_free(image);

            this.width = w.get();
            this.height = h.get();

            setParam(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
            setParam(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
            setParam(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            setParam(GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            upload(image);
        }
    }

    /**
     * Upload the image to OpenGL.
     *
     * @param image Image to upload.
     */
    private void upload(ByteBuffer image) {
        if (isTextureLoaded()) {
            bind();
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
        } else {
            // TODO: Handle Exception
        }
    }

    /**
     * Set OpenGL Texure Parameter.
     *
     * @param param Parameter name.
     * @param value Parameter value.
     */
    private void setParam(int param, int value) {
        if (isTextureLoaded()) {
            bind();
            glTexParameteri(GL_TEXTURE_2D, param, value);
        } else {
            // TODO: Handle Exception
        }
    }

    /**
     * Delete the texture.
     */
    public void unload() {
        if (isTextureLoaded()) {
            glDeleteTextures(id);
            loaded = false;
        } else {
            // TODO: Handle Exception
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
