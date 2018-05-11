package growth.game.screen;

import growth.game.render.GameFont;

public class ScreenManager {

    private Screen ActualScreen;
    public static final int MENUSCREEN = 0;
    public static final int GAMESCREEN = 1;

    public static final GameFont MONOFONTO = new GameFont("/font/monofonto.ttf",16,0);

    private final long window;

    public ScreenManager(long window) {
        this.window = window;
        ActualScreen = (new GameScreen(this));
    }

    public void update() {
        // Update the good window
        ActualScreen.update();
    }

    public void display() {
        // Display the good window
        ActualScreen.display();
    }

    public void setScreen(int newScreen) {
        unload();
        switch (newScreen) {
            case MENUSCREEN:
                ActualScreen = (new MenuScreen(this));
                break;
            case GAMESCREEN:
                ActualScreen = (new GameScreen(this));
                break;
        }
    }

    long getWindow() {
        return window;
    }

    public void unload() {
        ActualScreen.unload();
        ActualScreen = null;
        MONOFONTO.unload();
    }
}
