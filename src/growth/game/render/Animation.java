package growth.game.render;

import growth.game.render.texture.Texture;

public class Animation {

	private int actual;
	private final int delay;
	private int count;
	private final Texture[] textures;

	public Animation(String path, int numbAnimation, int delay) {
		textures = new Texture[numbAnimation];
		for (int i = 0; i < numbAnimation; i++) {
			textures[i] = new Texture();
			textures[i].load((path + Integer.toString(i) + ".png"));
		}
		this.delay = delay;
	}

	public void update() {
		count++;
		if (count > delay) {
			count = 0;
			actual++;
			if (actual >= textures.length) {
				actual = 0;
			}
		}
	}

	public int getCurrentID() {
		return textures[actual].getID();
	}

	public Texture[] getTextures() {
		return textures;
	}
}