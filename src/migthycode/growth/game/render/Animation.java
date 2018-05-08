package migthycode.growth.game.render;

import migthycode.growth.game.render.Texture;

public class Animation {

	private int actual;
	private final int delay;
	private int count;
	private final int[] textID;

	public Animation(String path, int numbAnimation, int delay) {
		textID = new int[numbAnimation];
		for (int i = 0; i < numbAnimation; i++) {
			textID[i] = Texture.loadTexture((path + Integer.toString(i) + ".png"));
		}
		this.delay = delay;
	}

	public void update() {
		count++;
		if (count > delay) {
			count = 0;
			actual++;
			if (actual >= textID.length) {
				actual = 0;
			}
		}
	}

	public int getCurrentId() {
		return textID[actual];
	}

	public int[] getTextID() {
		return textID;
	}
}















