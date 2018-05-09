package growth.game.tilemap;

import growth.game.render.Texture;

public class Tile {

    // Tile types
    public static final int BLOCKED = 1;
    private final Texture texture;
    private final int type;

    public Tile(String path, int type) {
        this.type = type;
        texture = new Texture();
        texture.load(path);
    }

    Texture getTexture() {
        return texture;
    }

    int getTextureID() {
        return texture.getID();
    }

    int getType() {
        return type;
    }
}
