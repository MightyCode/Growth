package growth.game.tilemap;

import growth.main.Window;
import growth.render.texture.Texture;
import growth.render.texture.TextureRenderer;
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
@SuppressWarnings("SameReturnValue")
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

	private final Texture tileSetT;

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
	private final int nbMap;

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

		// Init tileSet
		tileSetT = new Texture("/images/game/tiles/Tileset.png");
		tileSet = XmlReader.createTileSet(path);

		// Init map
		nbMap = XmlReader.options_nbMap();
		System.out.println("\n -------------------------- \n");
		for(int i = 1; i < nbMap; i++){
			maps.add(XmlReader.createMap("map"+i+".xml"));
		}
		System.out.println("\n -------------------------- \n");
		currentMap = 0;

		// Set layer
		for(int i = 0; i < 3; i++){
			maps.get(currentMap).setColor(i, 0.9f);
		}

		currentLayer = 1;
		maps.get(currentMap).setColor(currentLayer, 1f);
		chargeMap();

		// Init current map variables
		numCols = map[0].length;
		numRows = map.length;
		numRowsToDraw = Window.HEIGHT / tileSize + 2;
		numColsToDraw = Window.WIDTH / tileSize + 2;
		sizeX = numCols * tileSize;
		sizeY = numRows * tileSize;

		// Init camera
		ScreenManager.CAMERA.setBoundMax(Window.WIDTH - sizeX, Window.HEIGHT  - sizeY);
		ScreenManager.CAMERA.setBoundMin(0, 0);
	}

	/**
	 * Display the current map.
	 */
	public void display(boolean pos) {
		colOffset = -ScreenManager.CAMERA.getPosX() / tileSize;
		rowOffset = -ScreenManager.CAMERA.getPosY() / tileSize;

		int begin = (pos)? 0: currentLayer+1;
		int end = (pos)? currentLayer+1 : 4;

		int maxRow = (rowOffset + numRowsToDraw > numRows)? numRows : rowOffset + numRowsToDraw;
		int maxCol = (colOffset + numColsToDraw > numCols)? numCols : colOffset + numColsToDraw;

		// For each layer
		for(int i  =  begin; i < end ; i++){

			int[][] map = maps.get(currentMap).getMap(i);
			float color = 1f;//maps.get(currentMap).getColor(i);

			// For each row
			for (int row = rowOffset; row < maxRow; row++) {

				//For each col
				for (int col = colOffset; col < maxCol; col++) {

					if(map[row][col]==0)continue;
					TextureRenderer.image(
							col * tileSize,
							+ row * tileSize,
							tileSize, tileSize,
							tileSet[map[row][col]].getTexX(),
							tileSet[map[row][col]].getTexY(),
							tileSet[map[row][col]].getTexToX(),
							tileSet[map[row][col]].getTexToY(),
							tileSetT.getID(), color , 1f
					);
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
		int rc = map[row][col];
		return tileSet[rc].getType();
	}

	/**
	 * Unload the tile's texture to free memory.
	 */
	public void unload() {
		tileSetT.unload();
	}

	/**
	 * Change the layer to a higher layer.
	 */
	public void upLayer(){
	    if(currentLayer < 2) {
            maps.get(currentMap).setColor(currentLayer, 0.9f);
            currentLayer++;
            System.out.println("Change the current layer to " + currentLayer);
            maps.get(currentMap).setColor(currentLayer, 1f);
            chargeMap();
        }
	}

	/**
	 * Change the layer to a lower layer.
	 */
	public void downLayer(){
	    if(currentLayer > 0) {
            maps.get(currentMap).setColor(currentLayer, 0.9f);
            currentLayer--;
            System.out.println("Change the current layer to " + currentLayer);
            maps.get(currentMap).setColor(currentLayer, 1f);
            chargeMap();
        }
	}

	/**
	 * Charge the current layer for collision and another features.
	 */
	private void chargeMap(){
		map = maps.get(currentMap).getMap(currentLayer);
	}

}