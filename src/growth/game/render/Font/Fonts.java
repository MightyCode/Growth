package growth.game.render.Font;

import growth.game.render.Font.GameFont;

public abstract class Fonts {

    public static final GameFont MONOFONTO = new GameFont("/font/monofonto.ttf");

    public static void unload(){
        MONOFONTO.unload();
    }
}
