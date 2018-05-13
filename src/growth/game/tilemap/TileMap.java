package growth.game.tilemap;

import growth.main.Growth;
import growth.game.render.Render;
import growth.game.utils.XmlReader;

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
	 * This variable contains the current map.
	 */
	private int currentMap;
	private int[][] map;
	private final ArrayList<Map> maps = new ArrayList<>();
	private final int tileSize;
	private int numRows;
	private int numCols;
	private int sizeX;
	private int sizeY;

	// Tileset
	private final Tile[] tileSet;

	// Drawing
	private int rowOffset;
	private int colOffset;
	private final int numRowsToDraw;
	private final int numColsToDraw;

	public TileMap(int tileSize, String path2) {
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

		numRowsToDraw = Growth.HEIGHT / tileSize + 2;
		numColsToDraw = Growth.WIDTH / tileSize + 2;
		tween = 1;

		tileSet = XmlReader.createTileSet(path2);

		sizeX = numCols * tileSize;
		sizeY = numRows * tileSize;

		xMin = Growth.WIDTH - sizeX;
		xMax = 0;
		yMin = Growth.HEIGHT - sizeY;
		yMax = 0;
	}

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


	//
	// Setters
	//

	public double[] setActualMap(int side) {
		currentMap = maps.get(currentMap - 1).getNeighbour(side);
		map = maps.get(currentMap - 1).getMap();
		numCols = map[0].length;
		numRows = map.length;

		sizeX = numCols * tileSize;
		sizeY = numRows * tileSize;

		xMin = Growth.WIDTH - sizeX;
		yMin = Growth.HEIGHT - sizeY;

		double[] newPos = new double[2];
		if (side == 1) {
			newPos[0] = sizeX - (maps.get(currentMap - 1).getTileToComeX(side) * tileSize);
		} else newPos[0] = (maps.get(currentMap - 1).getTileToComeX(side) * tileSize);

		if (side == 4) newPos[1] = sizeY - (maps.get(currentMap - 1).getTileToComeY(side) * tileSize);
		else newPos[1] = maps.get(currentMap - 1).getTileToComeY(side) * tileSize;
		return newPos;
	}

	public void setTween(double d) {
		tween = d;
	}

	public void setPosition(double x, double y) {
		this.posX += (x - this.posX) * tween;
		this.posY += (y - this.posY) * tween;

		fixBounds();

		colOffset = (int) -this.posX / tileSize;
		rowOffset = (int) -this.posY / tileSize;
	}

	private void fixBounds() {
		if (posX < xMin) posX = xMin;
		if (posY < yMin) posY = yMin;
		if (posX > xMax) posX = xMax;
		if (posY > yMax) posY = yMax;
	}


	/*
	 * Getters
	 */

	public int getNumRows() {
		return numRows;
	}

	public int getNumCols() {
		return numCols;
	}

	public int getTileSize() {
		return tileSize;
	}

	public int getPosX() {
		return (int) posX;
	}

	public int getPosY() {
		return (int) posY;
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getNeighbour(int side) {
		return maps.get(currentMap - 1).getNeighbour(side);
	}

	public int getType(int row, int col) {
		if (map[row][col] == 0) return 0;
		int rc = map[row][col] - 1;
		return tileSet[rc].getType();
	}

	public void unload() {
		for (Tile tile : tileSet) {
			tile.getTexture().unload();
		}
	}
}



















