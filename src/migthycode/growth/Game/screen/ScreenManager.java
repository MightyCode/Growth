package migthycode.growth.Game.screen;

public class ScreenManager {
	
	private long window;
	private Screen ActualScreen;
	
	public static final int MENUSCREEN = 0;
	public static final int GAMESCREEN = 1;
	
	public ScreenManager(long window) {
		this.window = window;
		ActualScreen = (new GameScreen(this));
	}
	
	// Methode 
	public void update() {
		// On update la fenêtre souhaitée
		ActualScreen.update();
	}
	
	public void display() {
		// On affiche la fenêtre souhaitée
		ActualScreen.display();
	}

	public void setScreen(int newScreen) {
		unload();
		switch(newScreen) {
		case MENUSCREEN:
			ActualScreen = (new MenuScreen(this));
			break;
		case GAMESCREEN:
			ActualScreen = (new GameScreen(this));	
			break;
		}
	}
	
	public long getWindow() {
		return window;
	}
	
	public void unload() {
		ActualScreen.unload();
		ActualScreen = null;
	}
}
