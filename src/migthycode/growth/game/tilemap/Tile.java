package migthycode.growth.game.tilemap;

import migthycode.growth.game.utils.Texture;

import java.awt.image.BufferedImage;

public class Tile {
    // tile types
    public static final int NORMAL = 0;
    public static final int BLOCKED = 1;
    private int textId;
    private int type;

    public Tile(BufferedImage image, int type) {
        this.type = type;
    }

    public Tile(String path, int type) {
        this.type = type;
        textId = Texture.loadTexture(path);
    }

    public int getText() {
        return textId;
    }

    public int getType() {
        return type;
    }

}
