package migthycode.growth.Game.tilemap;

import java.util.ArrayList;

import migthycode.growth.Game.utils.Render;
import migthycode.growth.Game.utils.XmlReader;
import migthycode.growth.main.main;

public class TileMap {
	
	// Read Resources
	XmlReader xmlReader;
	
	// position
	private double x;
	private double y;
	
	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	private double tween;
	
	// map
	private int actualMap;
	private int[][]map;
	private ArrayList<Map> maps = new ArrayList<Map>();
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	// tileset
	private Tile[] tileSet;
	
	// drawing
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	public TileMap(int tileSize, String path2) {
		this.tileSize = tileSize;
		
		// Hard code to add and configure game's panels
			// Path to map file, and the leftIdPanel, upIdPanel, rightIdPanel and downIdPanel
			// Id of translate (1 to left, 2 to top, 3 to right and 4 to bottom)
			// The second and third parameter is tile position X and tile position Y (double)
		maps.add(new Map("map1.xml",0,0,2,0));

		maps.get(0).setTileToCome(1,1,6);
		
		maps.add(new Map("map2.xml",1,0,3,0));
		maps.get(1).setTileToCome(3,1,6);
		maps.get(1).setTileToCome(1,1,5);
		
		maps.add(new Map("map3.xml",2,0,0,0));
		maps.get(2).setTileToCome(3,1,14);
		
		actualMap = 1;
		
		// Init variables
		
		map = maps.get(actualMap-1).getMap();
		numCols = map[0].length;
		numRows = map.length;
		
		numRowsToDraw = main.HEIGHT / tileSize + 2;
		numColsToDraw = main.WIDTH / tileSize + 2;
		tween = 1;
		
		xmlReader = new XmlReader();
		tileSet = xmlReader.createTileSet(path2);
		
		width = numCols * tileSize;
		height = numRows * tileSize;
		
		xmin = main.WIDTH - width;
		xmax = 0;
		ymin = main.HEIGHT - height;
		ymax = 0;
	}
	
	public void display(Render render) {
		
		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
			
			if(row >= numRows) break;
			for(int col = colOffset; col < colOffset + numColsToDraw; col++) {
				
				if(col >= numCols) break;
				if(map[row][col] == 0) continue;

				render.image(
					(int)x + col * tileSize,
					(int)y + row * tileSize,
					tileSize,
					tileSize,
					"tile",
					(map[row][col]-1)
				);
			}
		}	
	}
	
	/** Setters **/
	public double[] setActualMap(int side){
		actualMap = maps.get(actualMap-1).getNeighbour(side);
		map = maps.get(actualMap-1).getMap();
		numCols = map[0].length;
		numRows = map.length;
		
		width = numCols * tileSize;
		height = numRows * tileSize;
		
		xmin = main.WIDTH - width;
		ymin = main.HEIGHT - height;
		
		double[] newPos = new double[2];
		if(side == 1) {
			newPos[0] = width - ( maps.get(actualMap-1).getTileToComeX(side) * tileSize);
		} else  newPos[0] = ( maps.get(actualMap-1).getTileToComeX(side) * tileSize);
		
		if(side == 4) newPos[1] = height - (maps.get(actualMap-1).getTileToComeY(side) * tileSize);
		else newPos[1] = maps.get(actualMap-1).getTileToComeY(side) * tileSize;
		return newPos;
	}
	public void setTween(double d) { tween = d; }
	public void setPosition(double x, double y) {
		this.x += (x - this.x) * tween;
		this.y += (y - this.y) * tween;
		
		fixBounds();
		
		colOffset = (int)-this.x / tileSize;
		rowOffset = (int)-this.y / tileSize;	
	}
	
	private void fixBounds() {
		if(x < xmin) x = xmin;
		if(y < ymin) y = ymin;
		if(x > xmax) x = xmax;
		if(y > ymax) y = ymax;
	}
	
	/** Getters **/
	public int getNumRows() { return numRows; }
	public int getNumCols() { return numCols; }
	public int getTileSize() { return tileSize; }
	public int getx() { return (int)x; }
	public int gety() { return (int)y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getNeightbour(int side) { return maps.get(actualMap-1).getNeighbour(side); }
	public int getType(int row, int col) {
		if(map[row][col] == 0) return 0;
		int rc = map[row][col]-1;
		return tileSet[rc].getType();
	}
	
}



















