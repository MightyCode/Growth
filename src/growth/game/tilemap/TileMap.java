package growth.game.tilemap;

import growth.entity.type.Player;
import growth.main.Config;
import growth.main.Window;
import growth.screen.render.Render;
import growth.screen.render.texture.Texture;
import growth.screen.render.texture.TextureRenderer;
import growth.screen.GameManager;
import growth.screen.screens.GameScreen;
import growth.util.XmlReader;
import growth.util.math.Vec2;

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
	private int mapID;

	/**
	 * Map.
	 * This variable contains the current map.
	 */
	private int[][] map;

	/**
	 * Array list of map.
	 * This variable contains the every maps.
	 */
	private Map curMap;

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
	 * Map variables
	 */
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	private int currentLayer;

	/**
	 * Number of map.
	 * This variable contains the number of variable charged on a xml file.
	 */
	private int nbMap;

	private Player player;

	private int[] saveValues;

	public static MapCollision mapCollision;

	/**
	 * Tilemap class constructor.
	 * Instance the class and set the tile's textures of tile set with the path.
	 *
	 * @param path Path to file's xml to load.
	 */
	public TileMap(String path) {
		// Init tileSet
		mapCollision = new MapCollision();
		tileSetT = new Texture("/textures/game/tiles/Tileset.png");
		tileSet = XmlReader.createTileSet(path);

		mapID = 0;
		currentLayer = 1;

		// Init current map variables
		numRowsToDraw = Window.height / GameScreen.tileSize + 2;
		numColsToDraw = Window.width / GameScreen.tileSize + 2;
		saveValues = new int[2];
		curMap = new Map(0,0);
	}

	/**
	 * The player associated to the tilemap.
	 * @param player The player.
	 */
	public void setEntity(Player player){
		this.player = player;
	}

	/**
	 * Display the current map.
	 */
	public void display(boolean pos) {
		tileSetT.bind();

		colOffset = -GameManager.camera.getPosX() / GameScreen.tileSize;
		rowOffset = -GameManager.camera.getPosY() / GameScreen.tileSize;

		int begin = (pos)? 0: currentLayer+1;
		int end = (pos)? currentLayer+1 : 4;

		int maxRow = (rowOffset + numRowsToDraw > numRows)? numRows : rowOffset + numRowsToDraw;
		int maxCol = (colOffset + numColsToDraw > numCols)? numCols : colOffset + numColsToDraw;

		// For each layer
		for(int i  =  begin; i < end ; i++){
			int[][] map = curMap.getMap(i);

			// For each row
			for (int row = rowOffset; row < maxRow; row++) {

				//For each col
				for (int col = colOffset; col < maxCol; col++) {

					if(row < 0 || col < 0 || map[row][col]== 0)continue;
					TextureRenderer.image(
							new Vec2(col * GameScreen.tileSize, row * GameScreen.tileSize),
							new Vec2(GameScreen.tileSize, GameScreen.tileSize),
							tileSet[map[row][col]].getFrom(),
							tileSet[map[row][col]].getTo()
					);
				}
			}
		}
	}

	/**
	 * Change the map with side.
	 * @param point The side of the window.
	 * @param posX Player's position x.
	 * @param posY Player's position y.
	 *
	 * @return isMap or not
	 */
	public boolean changeMap(int point, float posX, float posY){
		int[] result = isMap(Math.abs(point-2), posX, posY);
		if(result[0] == 1){
			changeMap(result[1], result[2]);
			return true;
		}
		return false;
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
    private int[] isMap(int side, float x, float y) {
        float posX = x / GameScreen.tileSize;
        float posY = y / GameScreen.tileSize;

        float[][] neighbour = curMap.getExitPoints(Math.abs(side-2));

        for (float[] aNeighbour : neighbour) {
            if (side == 0 || side == 2) {
                if (aNeighbour[2] < posY && posY < aNeighbour[3]) {
                    return new int[]{1, (int) aNeighbour[0] - 1, (int)aNeighbour[1]};
                }
            } else if (side == 1 || side == 3) {
                if (aNeighbour[2] < posX && posX < aNeighbour[3]) {
                    return new int[]{1, (int) aNeighbour[0] - 1, (int)aNeighbour[1]};
                }
            }
        }
        return new int[]{0};
    }

	/**
	 * Change the map with mapID.
	 * @param mapID The new map.
	 * @param point The point to come.
	 */
	public void changeMap(int mapID, int point){
		GameManager.setState(GameScreen.STATE_TRANSITION);
		saveValues[0] = mapID;
		saveValues[1] = point;
	}

	/**
	 * Set the new map and give the position to the player.
	 */
	public void doTransition(){
		GameScreen.entityManager.dispose();
		chargeMap();
		String location = curMap.getLocation(), zone = curMap.getZone();
		if(!zone.equals(curMap.getZone()))GameScreen.hud.setZone(curMap.getZone() , curMap.getLocation());
		else if(!location.equals(curMap.getLocation()))GameScreen.hud.setLocation( curMap.getLocation());
		GameManager.camera.setPosition(false);
	}

	/**
	 * Charge the current layer for collision and another features.
	 */
	private void chargeMap(){
		mapID = this.saveValues[0];
		curMap = XmlReader.createMap(Window.config.getValue(Config.PART_PATH) + "temp/map" + (mapID+1) + ".xml");
		float[] saveValues = curMap.saveValues(this.saveValues[1]);

		player.setPos(new Vec2(saveValues[0] * GameScreen.tileSize
				, saveValues[1] * GameScreen.tileSize - player.getSize().getY()/2));
		player.setPosTemp(new Vec2(saveValues[0] * GameScreen.tileSize
				, saveValues[1] * GameScreen.tileSize - player.getSize().getY()/2));
		if(saveValues[2] != -1) GameScreen.entityManager.getPlayer().setFacing(saveValues[2] == 1);
		map = curMap.getMap(1);
		GameScreen.entityManager.removeAll();
		curMap.loadEntity();
        numCols = map[0].length;
        numRows = map.length;

        sizeX = numCols * GameScreen.tileSize;
        sizeY = numRows * GameScreen.tileSize;

        GameManager.camera.setBoundMax(Window.width - sizeX, Window.height  - sizeY);
        GameManager.camera.setBoundMin(0, 0);

		float[] color =curMap.getColor();
		if(color[0] != -1){
			Render.setClearColor(color[0], color[1], color[2]);
		}
	}

	public void begin(int mapID, int point){
		saveValues[0] = mapID; saveValues[1] = point;
		chargeMap();
		GameScreen.hud.setZone(curMap.getZone() , curMap.getLocation());
	}

	/**
	 * Change the layer to a higher layer.
	 */
	public void setLayer(int numberToAdd){
		if(currentLayer + numberToAdd >= -1 && currentLayer + numberToAdd < 3) {
			currentLayer+= numberToAdd;
			Window.console.println("Change the current layer to " + currentLayer);
		}
	}

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

    public int getCurrentMap() {
		return mapID;
    }
}