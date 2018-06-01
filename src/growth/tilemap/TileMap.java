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
	 * This variable contains the smooth movement of camera
	 * 1 -> rigid and immediate set new camera position.
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
	public void display(boolean pos) {
		int begin = (pos)? 0: currentLayer;
		int end = (pos)? currentLayer : 6;

			for(int i  =  begin; i < end ; i++){
				float alpha = (i == currentLayer-1)? 1: (float)0.9;
				int[][] map = maps.get(currentMap).getMap(i);

				for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {

					if (row >= numRows) break;
					for (int col = colOffset; col < colOffset + numColsToDraw; col++) {

						if (col >= numCols) break;
						if (map[row][col] == 0) continue;

						if(i != currentLayer-1) {
							Render.image(
									(int) posX + col * tileSize,
									(int) posY + row * tileSize,
									tileSize, tileSize, tileSet[map[row][col] - 1].getTextureID(), 0.85f ,alpha
							);
						} else {
							Render.image(
									(int) posX + col * tileSize,
									(int) posY + row * tileSize,
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
	public double[] changeMap(int side, int point) {
		float[][] data = maps.get(currentMap).getExitPoints(side);
		currentMap = (int)data[point][0]-1;
		chargeMap();
		numCols = map[0].length;
		numRows = map.length;

		sizeX = numCols * tileSize;
		sizeY = numRows * tileSize;

		xMin = Window.WIDTH - sizeX;
		yMin = Window.HEIGHT - sizeY;

		double[] newPos = new double[2];
		newPos[0] = (maps.get(currentMap).getTileToComeX((int)data[point][1]) * tileSize);

		newPos[1] = maps.get(currentMap).getTileToComeY((int)data[point][1]) * tileSize;
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
	 * @param isTween Apply the tween (true) or no (false).
	 */
	public void setPosition(double posX, double posY, boolean isTween) {
		this.posX += (isTween)?(posX - this.posX) * tween : (posX - this.posX);
		this.posY += (isTween)?(posY - this.posY) * tween : (posY - this.posY);

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