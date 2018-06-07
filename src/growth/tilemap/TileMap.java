package growth.tilemap;

import growth.main.*;
import growth.render.Render;
import growth.screen.ScreenManager;
import growth.utils.XmlReader;
import java.util.ArrayList;

/**
 * TileMap class.
 * This class is use to store the game structure.
 *
 * @author MightyCode
 * @version 1.1
 */
public class TileMap {

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
	 * Current layer.
	 * This variable contains the layer where the player is.
	 * This variable is using to do the collision with the good layer.
	 */
	private int currentLayer;

	/**
	 * Number of map.
	 * This variable contains the number of variable charged on a xml file.
	 */
	private int nbMap;

	/**
	 * Tilemap class constructor.
	 * Instance the class and set the tile's textures of tile set with the path.
	 *
	 * @param tileSize Get tile size.
	 * @param path Path to file's xml to load.
	 */
	public TileMap(int tileSize, String path) {
		// Init variables
		this.tileSize = tileSize;

		nbMap = XmlReader.options_nbMap();

		System.out.println("\n -------------------------- \n");

		for(int i = 1; i < nbMap; i++){
			maps.add(XmlReader.createMap("map"+i+".xml"));
		}

		System.out.println("\n -------------------------- \n");

		currentMap = 0;

		currentLayer = 3;
		chargeMap();
		numCols = map[0].length;
		numRows = map.length;

		numRowsToDraw = Window.HEIGHT / tileSize + 2;
		numColsToDraw = Window.WIDTH / tileSize + 2;

		tileSet = XmlReader.createTileSet(path);

		sizeX = numCols * tileSize;
		sizeY = numRows * tileSize;

		ScreenManager.CAMERA.setBoundMax(Window.WIDTH - sizeX, Window.HEIGHT  - sizeY);
		ScreenManager.CAMERA.setBoundMin(0, 0);
	}

	/**
	 * Display the current map.
	 */
	public void display(boolean pos) {
		System.out.println("lol");
		colOffset = -ScreenManager.CAMERA.getPosX() / tileSize;
		rowOffset = -ScreenManager.CAMERA.getPosY() / tileSize;

		int begin = (pos)? 0: currentLayer;
		int end = (pos)? currentLayer : 6;

		int maxRow = (rowOffset + numRowsToDraw > numRows)? numRows : rowOffset + numRowsToDraw;
		int maxCol = (colOffset + numColsToDraw > numCols)? numCols : colOffset + numColsToDraw;

		// For each layer
		for(int i  =  begin; i < end ; i++){
			float alpha = (i == currentLayer-1)? 1: (float)0.9;
			int[][] map = maps.get(currentMap).getMap(i);

			// For each row
			for (int row = rowOffset; row < maxRow; row++) {

				//For each col
				for (int col = colOffset; col < maxCol; col++) {

					if (map[row][col] == 0) continue;

					if(i != currentLayer-1) {
						Render.image(
								col * tileSize,
								+ row * tileSize,
								tileSize, tileSize, tileSet[map[row][col] - 1].getTextureID(), 0.80f ,alpha
						);
					} else {
						Render.image(
								col * tileSize,
								row * tileSize,
								tileSize, tileSize, tileSet[map[row][col] - 1].getTextureID(), 1.0f,alpha
						);
					}
				}
			}
		}
	}

	/**
	 * Check if there are a next map for the player's position.
	 *
	 * @param side The side to change the map.
	 * @param x The player position x.
	 * @param y The player position y.
	 *
	 * @return The new map id and the spawn point.
	 */
	public int[] isMap(int side, int x, int y) {
		float posX = x / tileSize;
		float posY = y / tileSize;

		float[][] neighbour = maps.get(currentMap).getExitPoints(side);
		int[] table = new int[2];

		for(int counter = 0; counter < neighbour.length; counter++){
			if(side == 0 || side == 2){
				if(neighbour[counter][2] < posY && posY < neighbour[counter][3]){

					table[0] = (int)neighbour[counter][0];
					table[1] = counter;
					return table;
				}
			} else if (side == 1|| side == 3){
				if(neighbour[counter][2] < posX && posX < neighbour[counter][3]){
					table[0] = (int)neighbour[counter][0];
					table[1] = counter;
					return table;
				}
			}
		}
		return table;
	}

	/*
	 * Setters methods
	 */

	/**
	 * Change the map.
	 *
	 * @param side By which side the map will be changed.
	 * @param point Where the next new map will spawn the player.
	 *
	 * @return The next player position on the new map.
	 */
	public float[] changeMap(int side, int point) {
		float[][] data = maps.get(currentMap).getExitPoints(side);
		currentMap = (int)data[point][0]-1;
		chargeMap();
		numCols = map[0].length;
		numRows = map.length;

		sizeX = numCols * tileSize;
		sizeY = numRows * tileSize;

		ScreenManager.CAMERA.setBoundMax(Window.WIDTH - sizeX, Window.HEIGHT  - sizeY);
		ScreenManager.CAMERA.setBoundMin(0, 0);

		float[] newPos = new float[2];
		newPos[0] = (float)(maps.get(currentMap).getTileToComeX((int)data[point][1]) * tileSize);

		newPos[1] = (float)maps.get(currentMap).getTileToComeY((int)data[point][1]) * tileSize;
		return newPos;
	}

	/*
	 * Getters methods
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
		return (int) 0;
	}

	/**
	 * Return the map position y.
	 *
	 * @return position y
	 */
	public int getPosY() {
		return (int) 0;
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

	/**
	 * Change the layer to a higher layer.
	 */
	public void upLayer(){
		currentLayer++;
		chargeMap();
	}

	/**
	 * Change the layer to a lower layer.
	 */
	public void downLayer(){
		currentLayer--;
		chargeMap();
	}

	/**
	 * Charge the current layer for collision and another features.
	 */
	private void chargeMap(){
		map = maps.get(currentMap).getMap(currentLayer-1);
	}

}