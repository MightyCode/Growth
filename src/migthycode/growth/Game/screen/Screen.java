package migthycode.growth.Game.screen;

import migthycode.growth.Game.utils.Render;

public abstract class Screen{
	
	protected ScreenManager screenManager;

	
	Screen(ScreenManager screenManager){
		this.screenManager = screenManager;
	}
	
	public void init() {}
	
	public void display(Render render) {}
	
	public void update() {}
}
