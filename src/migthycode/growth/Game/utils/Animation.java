package migthycode.growth.Game.utils;

public class Animation {
	
	private int actual;
	private int delay;
	private int count;
	private int[] textID;
	
	public Animation(String path, int numbAnimation, int delay) {
		textID = new int[numbAnimation];
		for(int i = 0; i < numbAnimation; i++) {
			textID[i] = Texture.loadTexture((path+Integer.toString(i)+".png"));
		}
		this.delay = delay;
	}
	
	public void update() {
		count++;
		if(count > delay) { 
			count = 0;
			actual++;
			if(actual >= textID.length) {
				actual = 0;
			}
		}
	}
	
	public int getCurrentId() { return textID[actual]; }
	public int[] getTextID() {
		return textID;
	}
}
















