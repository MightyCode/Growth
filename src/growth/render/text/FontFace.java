package growth.render.text;

import growth.render.texture.Texture;

public class FontFace {

    /**
     * Font texture atlas.
     */
    private Texture fontAtlas;

    /**
     * Font atlas description file.
     */
    private FontFile fontFile;

    public FontFace(String name) {
        fontAtlas = new Texture();
        fontAtlas.load("/fonts/" + name + ".png");

        fontFile = new FontFile("/fonts/" + name + ".fnt");
    }

    /**
     * Get font atlas texture.
     *
     * @return fontAtlas.
     */
    public Texture getTexture() {
        return fontAtlas;
    }

    /**
     * Get font file.
     *
     * @return fontFile.
     */
    public FontFile getFontFile() {
        return fontFile;
    }
}
