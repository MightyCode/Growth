package migthycode.growth.game.tilemap;

import migthycode.growth.game.utils.Texture;

public class Tile {

    // Tile types
    public static final int BLOCKED = 1;
    private final int textId;
    private final int type;

    public Tile(String path, int type) {
        this.type = type;
        textId = Texture.loadTexture(path);
    }

    int getText() {
        return textId;
    }

    int getType() {
        return type;
    }
}
