package growth.game.render;

import growth.game.render.texture.Texture;

/**
 * Animation class.
 * This class is use by entity to store textures.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Animation {

	/**
	 * Current texture displayed.
	 * This variable contains the number of the displayed texture.
	 */
	private int current;

	/**
	 * Delay.
	 * This variable contains the delay (number of frame) between two frame.
	 */
	private final int delay;

	/**
	 * Counter.
	 * This variable is a counter to update animation and the texture's delay.
	 */
	private int count;

	/**
	 * Textures.
	 * This variable contains all of the texture use by animation.
	 */
	private final Texture[] textures;

	/**
	 * Animation class constructor.
	 *
	 * @param path Init the textures from the path.
	 * @param numbAnimation Number of textures that will be played.
	 * @param delay Temp before moving on to the next texture.
	 */
	public Animation(String path, int numbAnimation, int delay) {
		textures = new Texture[numbAnimation];
		for (int i = 0; i < numbAnimation; i++) {
			textures[i] = new Texture((path + Integer.toString(i) + ".png"));
			// Exemple: load(\IAmAFile\1.png)
		}
		this.delay = delay;
	}

	/**
	 * Update the animation's state.
	 */
	public void update() {
		count++;
		if (count > delay) {
			count = 0;
			current++;
			if (current >= textures.length) {
				current = 0;
			}
		}
	}

	/**
	 * Get the id of the current texture.
	 *
	 * @return current texture id
	 */
	public int getCurrentID() {
		return textures[current].getID();
	}

	public Texture[] getTextures() {
		return textures;
	}
}