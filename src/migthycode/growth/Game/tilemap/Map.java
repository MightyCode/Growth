package migthycode.growth.Game.tilemap;

import migthycode.growth.Game.utils.XmlReader;


public class Map {

	private int[] idMapNeightbour = new int[4];
	private double[][] tileToCome = new double [4][2];
	
	// Read Resources
	private XmlReader xmlReader;

	private int [][] map;
	
	public Map(String path) {
		xmlReader = new XmlReader();
		map = xmlReader.createMap(path);
	    idMapNeightbour[0] = 
	    idMapNeightbour[1]  = 
	    idMapNeightbour[2] = idMapNeightbour[3] = 0;
	}
	
	public Map(String path, int left, int up, int right, int down) {
		xmlReader = new XmlReader();
		map = xmlReader.createMap("/maps/" + path);
	    idMapNeightbour[0] = left;
	    idMapNeightbour[1] = up;
	    idMapNeightbour[2] = right;
	    idMapNeightbour[3] = down;
	}
	
	/** Setters */
	public void setIdPanel(int side, int i){idMapNeightbour[side-1] = i;}
	public void setTileToCome(int side, double beginX, double beginY) {
		tileToCome[side-1][0] = beginX;
		tileToCome[side-1][1] = beginY;
	}
	
	/** Getters **/
	public int getNeighbour(int side){ return idMapNeightbour[side-1]; }
	public int[][] getMap(){ return map; }
	public double getTileToComeX(int side) { return tileToCome[side-1][0]; }
	public double getTileToComeY(int side) { return tileToCome[side-1][1]; }
}
