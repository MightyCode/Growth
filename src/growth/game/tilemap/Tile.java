package growth.game.tilemap;

import growth.util.math.Vec2;

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
     * Tile's type.
     * This variable contains state of block.
     */
    private final int type;

    /**
     * Begin Vector position.
     * This variable contains the vector of the position of the beginning of the tile on the texture.
     */
    private Vec2 from;

    /**
     * End Vector position.
     * This variable contains the vector of the end position of the tile on the texture.
     */
    private Vec2 to;

    /**
     * The tile's name.
     */
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
        this.name = name;

        from = new Vec2((float)texX * size / tileSet_width,(float)texY * size / tileSet_height);
        to = new Vec2(from.getX()+(float)size/tileSet_width,from.getY()+(float)size/tileSet_height);
    }

    /**
     * Return the tile's type.
     *
     * @return type
     */
    public int getType() {
        return type;
    }

    /**
     * Get the begging position of the tile on the texture.
     * @return Vec2 to
     */
    public Vec2 getTo() {
        return to;
    }

    /**
     * Get the end position of the tile on the texture.
     * @return Vec2 from
     */
    public Vec2 getFrom() { return from;}
}