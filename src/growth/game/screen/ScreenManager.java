package growth.game.screen;

public class ScreenManager {

    private Screen ActualScreen;
    public static final int MENUSCREEN = 0;
    public static final int GAMESCREEN = 1;

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
    }
}
