package migthycode.growth.Game.tilemap;

public class Tile {
	private int type;
	
	// tile types
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	
	public Tile(int type) {
		this.type = type;
	}
	
	public Tile(String path, int type) {
		this.type = type;
	}
	
	public int getType() { return type; }
}
