package migthycode.growth.game.tilemap;

import migthycode.growth.game.utils.Render;
import migthycode.growth.game.utils.Texture;
import migthycode.growth.game.utils.XmlReader;
import migthycode.growth.main.Growth;

import java.util.ArrayList;


public class TileMap {

	// position
	private double posX;
	private double posY;

	// bounds
	private int xMin;
	private int yMin;
	private final int xMax;
	private final int yMax;

	private double tween;

	// map
	private int actualMap;
	private int[][] map;
	private final ArrayList<Map> maps = new ArrayList<>();
	private final int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;

	// tileset
	private final Tile[] tileSet;

	// drawing
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

		actualMap = 1;

		// Init variables

		map = maps.get(actualMap - 1).getMap();
		numCols = map[0].length;
		numRows = map.length;

		numRowsToDraw = Growth.HEIGHT / tileSize + 2;
		numColsToDraw = Growth.WIDTH / tileSize + 2;
		tween = 1;

		tileSet = XmlReader.createTileSet(path2);

		width = numCols * tileSize;
		height = numRows * tileSize;

		xMin = Growth.WIDTH - width;
		xMax = 0;
		yMin = Growth.HEIGHT - height;
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
						tileSize, tileSize, tileSet[map[row][col] - 1].getText(), 1
				);
			}
		}
	}

	/**
	 * Setters
	 **/
	public double[] setActualMap(int side) {
		actualMap = maps.get(actualMap - 1).getNeighbour(side);
		map = maps.get(actualMap - 1).getMap();
		numCols = map[0].length;
		numRows = map.length;

		width = numCols * tileSize;
		height = numRows * tileSize;

		xMin = Growth.WIDTH - width;
		yMin = Growth.HEIGHT - height;

		double[] newPos = new double[2];
		if (side == 1) {
			newPos[0] = width - (maps.get(actualMap - 1).getTileToComeX(side) * tileSize);
		} else newPos[0] = (maps.get(actualMap - 1).getTileToComeX(side) * tileSize);

		if (side == 4) newPos[1] = height - (maps.get(actualMap - 1).getTileToComeY(side) * tileSize);
		else newPos[1] = maps.get(actualMap - 1).getTileToComeY(side) * tileSize;
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

	/**
	 * Getters
	 **/
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

	public int getWidth() {
		return width;
	}

	public int getNeighbour(int side) {
		return maps.get(actualMap - 1).getNeighbour(side);
	}

	public int getType(int row, int col) {
		if (map[row][col] == 0) return 0;
		int rc = map[row][col] - 1;
		return tileSet[rc].getType();
	}

	public void unload() {
		for (Tile set : tileSet) {
			Texture.unload(set.getText());
		}
	}
}



















