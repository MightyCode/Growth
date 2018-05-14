package growth.game.tilemap;

import growth.game.utils.XmlReader;

/**
 * Map class.
 * This class is use to store a map with the layers and entities.
 *
 * @author MightyCode
 * @version 1.0
 */
class Map {

	/**
	 * Map's neighbour id.
	 * This variable contains the map's neighbour id.
	 */
	private final int[] idMapNeighbour = new int[4];

	/**
	 * Map's neighbour beginning tile.
	 * This variable contains the map's neighbour beginning tile.
	 */
	private final double[][] tileToCome = new double[4][2];

	/**
	 * Map's tile id.
	 * This variable contains the map's tile id.
	 */
	private final int[][] map;

	/**
	 * Map class constructor.
	 * Instance the class and set the map's neighbour id with the path.
	 *
	 * @param path Path to file's xml to load the map.
	 * @param left Map left id.
	 * @param up Map top id.
	 * @param right Map right id.
	 * @param down Map bottom id.
	 */
	Map(String path, int left, int up, int right, int down) {
		map = XmlReader.createMap("/map/" + path);
		idMapNeighbour[0] = left;
		idMapNeighbour[1] = up;
		idMapNeighbour[2] = right;
		idMapNeighbour[3] = down;
	}


	/*
	 * Setters
	 */

	/**
	 * Set the beginning of map in relation to the arrival side.
	 *
	 * @param side The side of beginning.
	 * @param beginX The tile x to begin.
	 * @param beginY The tile y to begin.
	 */
	void setTileToCome(int side, double beginX, double beginY) {
		tileToCome[side - 1][0] = beginX;
		tileToCome[side - 1][1] = beginY;
	}


	/*
	 * Getters
	 */

	/**
	 * Get the map's neighbour id.
	 *
	 * @param side The side of map's neighbour.
	 *
	 * @return idMapNeighbour
	 */
	int getNeighbour(int side) {
		return idMapNeighbour[side - 1];
	}

	/**
	 * Get the map's tile id.
	 *
	 * @return map
	 */
	int[][] getMap() {
		return map;
	}

	/**
	 * Get the tile to come in x with the map's side neighbour.
	 *
	 * @return begin tile x
	 */
	double getTileToComeX(int side) {
		return tileToCome[side - 1][0];
	}

	/**
	 * Get the tile to come in y with the map's side neighbour.
	 *
	 * @return begin tile y
	 */
	double getTileToComeY(int side) {
		return tileToCome[side - 1][1];
	}
}
