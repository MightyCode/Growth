package growth.tilemap;

import java.util.ArrayList;

/**
 * Map class.
 * This class is use to store a map with the layers and entities.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Map {

	private final int mapID;

	/**
	 * Map's neighbour id.
	 * This variable contains the map's neighbour id.
	 */
	private final int[] idMapNeighbour = new int[4];

	/**
	 * Map's neighbour beginning tile.
	 * This variable contains the map's neighbour beginning tile.
	 */
	private float[][] spawnTile;

	private final ArrayList[] outPoint = new ArrayList[4];

	/**
	 * Layers.
	 * This variable contains the different layer using in the map.
	 */
	private Layer layer[];

	/**
	 * Maps width.
	 * This variable contains the width of the map.
	 */
	private int width;

	/**
	 * Maps height.
	 * This variable contains the height of the map.
	 */
	private int height;

	/**
	 * Map class constructor.
	 * Instance the class and set the map id.
	 *
	 * @param mapID Map id.
	 */

	public Map(int mapID, int numberSpawns) {
		this.mapID = mapID;
		layer = new Layer[5];
		spawnTile= new float[numberSpawns][4];
	}

	/**
	 * Set a layer on the map.
	 */
	public void setLayer(int layerID, int[][] map){
		layer[layerID] = new Layer(map);
		width = layer[layerID].getWidth();
		height = layer[layerID].getHeight();
	}

	/*
	 * Setters
	 */

	/**
	 * Set the beginning of map in relation to the arrival side.
	 *
	 * @param number The spawn point number.
	 * @param beginX The tile x to begin.
	 * @param beginY The tile y to begin.
	 */
	public void setSpawnTile(int number, float beginX, float beginY) {
		spawnTile[number - 1][0] = beginX;
		spawnTile[number - 1][1] = beginY;
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
	public int getNeighbour(int side) {
		return idMapNeighbour[side - 1];
	}

	/**
	 * Get the map's tile id.
	 *
	 * @return map
	 */
	int[][] getMap(int currentLayer) {
		try{
			return layer[currentLayer].getMap();
		} catch(Exception e){
			return new int[height][width];
		}
	}

	/**
	 * Get the tile to come in x with the map's side neighbour.
	 *
	 * @return begin tile x
	 */
	double getTileToComeX(int side) {
		return spawnTile[side - 1][0];
	}

	/**
	 * Get the tile to come in y with the map's side neighbour.
	 *
	 * @return begin tile y
	 */
	double getTileToComeY(int side) {
		return spawnTile[side - 1][1];
	}
}
