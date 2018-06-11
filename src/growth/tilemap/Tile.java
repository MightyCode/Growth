package growth.tilemap;

import growth.render.texture.Texture;

/**
 * Tile class.
 * This class is use to store all tiles of tileSet and these characteristic.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Tile {

    /**
     * Is blocker.
     * This variable contains number of block state.
     */
    public static final int BLOCKED = 1;

    /**
     * Tile's texture.
     * This variable contains the texture of the tile.
     */
    private final Texture texture;

    /**
     * Tile's type.
     * This variable contains state of block.
     */
    private final int type;

    private final float texX;

    private final float texY;

    private final float texToX;

    private final float texToY;

    private final String name;
    /**
     * Tile class constructor.
     * Instance the class, set the texture with the path and init the tile's type.
     *
     * @param name Name of the texture.
     * @param type Type of the tile.
     */
    public Tile(String name, int type, int texX, int texY, int size, int tileSet_width, int tileSet_height) {
        this.type = type;
        texture = new Texture();
        this.name = name;

        this.texX = (float) texX * size / tileSet_width;
        this.texY = (float) texY * size / tileSet_height;

        texToX = this.texX + ((float)size/tileSet_width);
        texToY = this.texY + ((float)size/tileSet_height);
    }

    /**
     * Return the tile's texture.
     *
     * @return texture
     */
    Texture getTexture() {
        return texture;
    }

    /**
     * Return the tile's type.
     *
     * @return type
     */
    int getType() {
        return type;
    }

    float getTexX() {
        return texX;
    }

    float getTexY() {
        return texY;
    }

    float getTexToX() {
        return texToX;
    }

    float getTexToY() {
        return texToY;
    }
}