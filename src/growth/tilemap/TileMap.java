package growth.tilemap;

import growth.main.Window;
import growth.render.Render;
import growth.utils.XmlReader;
import java.util.ArrayList;

/**
 * TileMap class.
 * This class is use to store the game structure.
 *
 * @author MightyCode
 * @version 1.0
 */
public class TileMap {

	/**
	 * Map position x.
	 * This variable contains the position y of the beginning of the rendering of the map.
	 */
	private double posX;

	/**
	 * Map position y.
	 * This variable contains the position y of the beginning of the rendering of the map.
	 */
	private double posY;

	private int xMin;
	private int yMin;
	private final int xMax;
	private final int yMax;

	/**
	 * Tween.
	 * This variable contains the smooth movement of camera (1 -> rigid and immediate).
	 */
	private double tween;

	/**
	 * Current.
	 * This variable contains the number of the current map.
	 */
	private int currentMap;
	
	/**
	 * Map.
	 * This variable contains the current map.
	 */
	private int[][] map;
	
	/**
	 * Array list of map.
	 * This variable contains the every maps.
	 */
	private final ArrayList<Map> maps = new ArrayList<>();
	
	/**
	 * Tile size.
	 * This variable contains the size of tiles.
	 */
	private final int tileSize;
	
	/**
	 * Number of row.
	 * This variable contains the number of row in the current map.
	 */
	private int numRows;
	
	/**
	 * Number of column.
	 * This variable contains the number of column in the current map.
	 */
	private int numCols;

	/**
	 * Map size x.
	 * This variable contains the map's size x.
	 */
	private int sizeX;

	/**
	 * Map size y.
	 * This variable contains the map's size y.
	 */
	private int sizeY;

	/**
	 * Map tile set.
	 * This variable contains tile set.
	 */
	private final Tile[] tileSet;

	/**
	 * Row off set.
	 * This variable contains the number of row where the player is.
	 */
	private int rowOffset;

	/**
	 * Column off set.
	 * This variable contains the number of column where the player is.
	 */
	private int colOffset;

	/**
	 * Number rows to draw.
	 * This variable contains the number of row to draw.
	 */
	private final int numRowsToDraw;

	/**
	 * Number columns to draw.
	 * This variable contains the number of column to draw.
	 */
	private final int numColsToDraw;

	/**
	 * Tilemap class constructor.
	 * Instance the class and set the tile's textures of tile set with the path.
	 *
	 * @param tileSize Get tile size.
	 * @param path Path to file's xml to load.
	 */
	public TileMap(int tileSize, String path) {
		this.tileSize = tileSize;

		// Hard code to add and configure game's panels
		// Path to map file, and the leftIdPanel, upIdPanel, rightIdPanel and downIdPanel
		// Id of translate (1 to left, 2 to top, 3 to right and 4 to bottom)
		// The second and third parameter is tile position X and tile position Y (double)
		maps.add(new Map("map1.xml", 0, 0, 2, 0));

		maps.get(0).setTileToCome(1, 0.5, 6);

		maps.add(new Map("map2.xml", 1, 0, 3, 0));
		maps.get(1).setTileToCome(3, 0.5, 6);
		maps.get(1).setTileToCome(1, 0.5, 5);

		maps.add(new Map("map3.xml", 2, 0, 0, 0));
		maps.get(2).setTileToCome(3, 0.5, 14);

		currentMap = 1;

		// Init variables

		map = maps.get(currentMap - 1).getMap();
		numCols = map[0].length;
		numRows = map.length;

		numRowsToDraw = Window.HEIGHT / tileSize + 2;
		numColsToDraw = Window.WIDTH / tileSize + 2;
		tween = 1;

		tileSet = XmlReader.createTileSet(path);

		sizeX = numCols * tileSize;
		sizeY = numRows * tileSize;

		xMin = Window.WIDTH - sizeX;
		xMax = 0;
		yMin = Window.HEIGHT - sizeY;
		yMax = 0;
	}

	/**
	 * Display the current map.
	 */
	public void display() {
		for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {

			if (row >= numRows) break;
			for (int col = colOffset; col < colOffset + numColsToDraw; col++) {

				if (col >= numCols) break;
				if (map[row][col] == 0) continue;

				Render.image(
						(int) posX + col * tileSize,
						(int) posY + row * tileSize,
						tileSize, tileSize, tileSet[map[row][col] - 1].getTextureID(), 1
				);
			}
		}
	}

	/*
	 * Setters
	 */

	/**
	 * Change the map.
	 *
	 * @param side Where the map will be changed.
	 */
	public double[] changeMap(int side) {
		currentMap = maps.get(currentMap - 1).getNeighbour(side);
		map = maps.get(currentMap - 1).getMap();
		numCols = map[0].length;
		numRows = map.length;

		sizeX = numCols * tileSize;
		sizeY = numRows * tileSize;

		xMin = Window.WIDTH - sizeX;
		yMin = Window.HEIGHT - sizeY;

		double[] newPos = new double[2];
		if (side == 1) {
			newPos[0] = sizeX - (maps.get(currentMap - 1).getTileToComeX(side) * tileSize);
		} else newPos[0] = (maps.get(currentMap - 1).getTileToComeX(side) * tileSize);

		if (side == 4) newPos[1] = sizeY - (maps.get(currentMap - 1).getTileToComeY(side) * tileSize);
		else newPos[1] = maps.get(currentMap - 1).getTileToComeY(side) * tileSize;
		return newPos;
	}

	/**
	 * Set the camera tween.
	 *
	 * @param tween Set the new tween.
	 */
	public void setTween(double tween) {
		this.tween = tween;
	}

	/**
	 * Set new origin position of map.
	 *
	 * @param posX Set the new origin position x.
	 * @param posY Set the new origin position y.
	 */
	public void setPosition(double posX, double posY) {
		this.posX += (posX - this.posX) * tween;
		this.posY += (posY - this.posY) * tween;

		fixBounds();

		colOffset = (int) -this.posX / tileSize;
		rowOffset = (int) -this.posY / tileSize;
	}

	/**
	 * Set the corner of the map.
	 */
	private void fixBounds() {
		if (posX < xMin) posX = xMin;
		if (posY < yMin) posY = yMin;
		if (posX > xMax) posX = xMax;
		if (posY > yMax) posY = yMax;
	}

	/*
	 * Getters
	 */

	/**
	 * Return the number of map'row.
	 *
	 * @return the number of row
	 */
	public int getNumRows() {
		return numRows;
	}

	/**
	 * Return the number of map'column.
	 *
	 * @return the number of column
	 */
	public int getNumCols() {
		return numCols;
	}

	/**
	 * Return the tile size.
	 *
	 * @return the tile size
	 */
	public int getTileSize() {
		return tileSize;
	}

	/**
	 * Return the map position x.
	 *
	 * @return position x
	 */
	public int getPosX() {
		return (int) posX;
	}

	/**
	 * Return the map position y.
	 *
	 * @return position y
	 */
	public int getPosY() {
		return (int) posY;
	}

	/**
	 * Return the map size x.
	 *
	 * @return size x
	 */
	public int getSizeX() {
		return sizeX;
	}

	/**
	 * Return the map size y.
	 *
	 * @return size y
	 */
	public int getSizeY() {
		return sizeY;
	}

	/**
	 * Return the id of the choose map.
	 *
	 * @param side To choose the map neighbour.
	 *
	 * @return the id neighbour
	 */
	public int getNeighbour(int side) {
		return maps.get(currentMap - 1).getNeighbour(side);
	}

	/**
	 * Return type of block.
	 *
	 * @param row Number of row to choose the tile's number.
	 * @param col Number of col to choose the tile's number.
	 *
	 * @return tile's type
	 */
	public int getType(int row, int col) {
		if (map[row][col] == 0) return 0;
		int rc = map[row][col] - 1;
		return tileSet[rc].getType();
	}

	/**
	 * Unload the tile's texture to free memory.
	 */
	public void unload() {
		for (Tile tile : tileSet) {
			tile.getTexture().unload();
		}
	}
}



















