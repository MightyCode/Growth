package migthycode.growth.game.screen;

public class ScreenManager {

    public static final int MENUSCREEN = 0;
    private static final int GAMESCREEN = 1;
    private final long window;
    private Screen ActualScreen;

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
    }
}
