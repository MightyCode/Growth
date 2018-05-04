package migthycode.growth.Game.screen;

import java.util.ArrayList;
import java.util.Objects;

import migthycode.growth.Game.utils.Render;

public class ScreenManager {
	
	private int screenDisplayed;
	private long window;
	private ArrayList <Screen> screen = new ArrayList<Screen>();
	
	public static final int MENUSCREEN = 0;
	public static final int GAMESCREEN = 1;
	
	public ScreenManager(long window) {
		this.window = window;
		init();
	}
	
	public void init() {
		screen.add(new MenuScreen(this));
		screen.add(new GameScreen(this));
		
		setScreen(GAMESCREEN);
	}
	
	// Methode 
	public void update() {
		// On update la fenêtre souhaitée
		screen.get(screenDisplayed).update();
	}
	
	public void display(Render render) {
		// On affiche la fenêtre souhaitée
		screen.get(screenDisplayed).display(render);
	}

	public void setScreen(int newScreen) {
		screenDisplayed = newScreen;	
		screen.get(screenDisplayed).init();
	}
	
	public long getWindow() {
		return window;
	}
}
