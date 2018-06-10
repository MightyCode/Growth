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

    private final int texX;

    private final int texY;

    private final String name;
    /**
     * Tile class constructor.
     * Instance the class, set the texture with the path and init the tile's type.
     *
     * @param name Name of the texture.
     * @param type Type of the tile.
     */
    public Tile(String name, int type, int texX, int texY, int size) {
        this.type = type;
        texture = new Texture();
        this.name = name;

        this.texX = texX * size;
        this.texY = texY * size;
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
     * Return the tile's texture ID.
     *
     * @return texture ID
     */
    int getTextureID() {
        return texture.getID();
    }

    /**
     * Return the tile's type.
     *
     * @return type
     */
    int getType() {
        return type;
    }

    int getTexX() {
        return texX;
    }

    int getTexY() {
        return texY;
    }
}