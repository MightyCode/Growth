package growth.tilemap;

public class Layer {
    private int[][] map;

    Layer(int[][] map){
        this.map = map;
    }

    int[][] getMap(){
        return map;
    }
}
