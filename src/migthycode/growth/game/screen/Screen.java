package migthycode.growth.game.screen;

abstract class Screen {

    protected final ScreenManager screenManager;

    Screen(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    public void display() {
    }

    public void update() {
    }

    public void unload() {
    }
}
