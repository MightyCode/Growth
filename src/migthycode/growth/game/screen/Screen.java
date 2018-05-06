package migthycode.growth.game.screen;

public abstract class Screen {

    protected ScreenManager screenManager;


    Screen(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    public void load() {
    }

    public void display() {
    }

    public void update() {
    }

    public void unload() {
    }
}
