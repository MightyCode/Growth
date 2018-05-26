package growth.tilemap;

/**
 * Layer class.
 * This class is use to store layer of the map.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Layer {

    /**
     * Map.
     * This variable contains the map.
     */
    private int[][] tileID;

    /**
     * Layer class constructor.
     * Instance the class and set the right layer's content .
     *
     * @param tileID The tiles of the layer.
     */
    Layer(int[][] tileID){
        this.tileID = tileID;
    }

    /**
     * Get the layer's content.
     */
    int[][] getMap(){
        return tileID;
    }

    /**
     * Get the layer height.
     */
    public int getHeight(){
        return tileID.length;
    }

    /**
     * Get the layer width.
     */
    public int getWidth(){
        return tileID[0].length;
    }
}
