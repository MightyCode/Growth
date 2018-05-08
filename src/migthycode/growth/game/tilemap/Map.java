package migthycode.growth.game.tilemap;

import migthycode.growth.game.utils.XmlReader;

class Map {

	private final int[] idMapNeighbour = new int[4];
	private final double[][] tileToCome = new double[4][2];

	private final int[][] map;

	Map(String path, int left, int up, int right, int down) {
		map = XmlReader.createMap("/maps/" + path);
		idMapNeighbour[0] = left;
		idMapNeighbour[1] = up;
		idMapNeighbour[2] = right;
		idMapNeighbour[3] = down;
	}

	/**
	 * Setters
	 */

	void setTileToCome(int side, double beginX, double beginY) {
		tileToCome[side - 1][0] = beginX;
		tileToCome[side - 1][1] = beginY;
	}

	/**
	 * Getters
	 **/
	int getNeighbour(int side) {
		return idMapNeighbour[side - 1];
	}

	int[][] getMap() {
		return map;
	}

	double getTileToComeX(int side) {
		return tileToCome[side - 1][0];
	}

	double getTileToComeY(int side) {
		return tileToCome[side - 1][1];
	}
}
