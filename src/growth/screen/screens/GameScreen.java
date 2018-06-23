package growth.screen.screens;

import growth.game.Hud;
import growth.render.Render;
import growth.screen.ScreenManager;
import growth.screen.overlay.DeathOverlay;
import growth.screen.overlay.PauseOverlay;
import growth.game.tilemap.TileMap;
import growth.entity.Player;
import growth.utils.Math;

/**
 * Game class.
 * This class is the game screen.
 *
 * @author MightyCode
 * @version 1.0
 */
public class GameScreen extends Screen {

    public static final Hud HUD = new Hud();

    /**
     * Tile size.
     * This variable contains the definitive tile size.
     */
    public static final int TILESIZE = 64;

    /**
     * Game's states.
     * These static final variable counting the different state of game.
     */
    public static final int NORMALSCREEN = 0;
    private static final int TRANSITIONSCREEN = 1;
    private static final int ESCAPESCREEN = 2;
    private static final int INVENTORYSCREEN = 3;
    private static final int DEATHSCREEN = 4;

    /**
     * Time to transition.
     * This variable contains the number of frames to finish a transition between two map.
     */
    private final int transitionTime = 40;

    /**
     * Transition counter.
     * This variable contains the number of frame after the beginning of transition.
     */
    private int transitionCounter;

    /**
     * Transition side.
     * This variable contains where the player has touch the border of window.
     */
    private int transitionSide;

    /**
     * Transition point.
     * This variable contains where the player go in the next map after the transition.
     */
    private int transitionPoint;

    /**
     * TileMap.
     * This variable contains the tileMap to interact with it.
     */
    private final TileMap tileMap;

    /**
     * Player.
     * This variable contains the player to ... play.
     */
    private final Player player;

    /**
     * Pause Overlay.
     * This variable contains the pause overlay.
     */
    private final PauseOverlay pause;

    /**
     * Death Overlay.
     * This variable contains the overlay of death that appears when the character dies.
     */
    private final DeathOverlay death;

    /**
     * GameScreen class constructor.
     * Instance the class and set all of the GameScreen's variables.
     *
     * @param screenManager Add screenManager to change the global screen.
     */
    public GameScreen(ScreenManager screenManager) {
        super(screenManager);
        Render.setClearColor(0.67f, 0.85f, 0.90f, 1f);
        System.out.println("\n-------------------------- \n");

        /* Init gameScreen's variables */
        // Init screen vars
        state = NORMALSCREEN;
        // Init screen's overlay
        pause = new PauseOverlay(this);
        death = new DeathOverlay(this);

        // Init tileMap
        tileMap = new TileMap(TILESIZE, "/map/tileset.xml");
        ScreenManager.CAMERA.setTween(0.3f, 1f);

        // Init player
        player = new Player(this, tileMap, TILESIZE, TILESIZE);

        // Player begin in the ground on Panel 1
        player.setPosition(24 * TILESIZE, 6 * TILESIZE - player.getCY() / 2);

        // Add player for the camera
        ScreenManager.CAMERA.setEntityToCamera(player);


        // Set the position of map before beginning of the game
        ScreenManager.CAMERA.setPosition(false);
    }

    /**
     * Update the screen in terms of the game's state.
     */
    public void update() {
        switch (state) {
            case NORMALSCREEN:
                updateGame();
                break;
            case TRANSITIONSCREEN:
                updateTransition();
                break;
            case ESCAPESCREEN:
                pause.update();
                break;
            case INVENTORYSCREEN:
                break;
            case DEATHSCREEN:
                death.update();
                break;
            default:
                updateGame();
                updateGame();
                break;
        }
    }

    /**
     * Update the player and the map.
     */
    private void updateGame() {

        if(ScreenManager.KEY.keyPressed(8)) {
            tileMap.upLayer();
        }

        if(ScreenManager.KEY.keyPressed(9)) {
            tileMap.downLayer();
        }

        if(ScreenManager.KEY.keyPressed(0)) {
            state = ESCAPESCREEN;
        }
        // Update player
        player.update();

        ScreenManager.CAMERA.setPosition(true);

        // Check border player collision to change the map
        if (player.getPosX() - player.getCX() / 2 <= 0) {
            changeMap(0);
        } else if (player.getPosX() + player.getCX() / 2 >= tileMap.getSizeX()) {
            changeMap(2);
        } else if(player.getPosY() + player.getCY()/ 2 >= tileMap.getSizeY()){
            changeMap(3);
        }

        HUD.update();
    }

    /**
     * Update the transition between two maps.
     */
    private void updateTransition() {
        transitionCounter++;
        if (transitionCounter == transitionTime / 2) {
            float[] pos = tileMap.changeMap(transitionSide,transitionPoint);
            player.setPosition(pos[0], pos[1] - player.getCY() / 2);
            ScreenManager.CAMERA.setPosition(false);
            player.setSpeed(0, 0);
        } else if (transitionCounter > transitionTime) {
            state = NORMALSCREEN;
            transitionCounter = 0;
        }
    }

    /**
     * Display the screen in terms of the game'state
     */
    public void display() {
        // clear the framebuffer
        Render.clear();
        switch (state) {
            case NORMALSCREEN:
                displayGame();
                break;
            case TRANSITIONSCREEN:
                displayTransition();
                break;
            case ESCAPESCREEN:
                displayGame();
                pause.display();
                break;
            case INVENTORYSCREEN:
                break;
            case DEATHSCREEN:
                displayGame();
                death.display();
                break;
        }
    }

    /**
     * Display the map and the player
     */
    private void displayGame() {
        // Draw map behind the player
        tileMap.display(true);
        // Draw player
        player.display();
        // Draw map in front of the play
        tileMap.display(false);
        HUD.display();
    }


    /**
     * Display the transition between two map
     */
    private void displayTransition() {
        displayGame();
        if (transitionCounter <= transitionTime / 2) {
            ScreenManager.CAMERA.transition( 0, (float) Math.map(transitionCounter, 0, transitionTime / 2, 0, 1.5));
        } else {
            ScreenManager.CAMERA.transition(0, (float) Math.map(transitionCounter, transitionTime / 2, transitionTime, 1.5, 0));
        }
    }

    /**
     * Set the change when the player touch a screen'edge.
     *
     */
    private void changeMap(int side) {
        int[] data = tileMap.isMap(side, player.getPosX(), player.getPosY());
        if(data[0] != 0){
            transitionSide = side;
            transitionPoint = data[1];
            state = TRANSITIONSCREEN;
        } else if (side == 3){
            player.setPosition(-100,-100);
            state = DEATHSCREEN;
        }
    }

    void chargeGame(){

    }

    void saveGame(){

    }

    /**
     * Unload the texture to free memory.
     */
    public void unload() {
        System.out.println("\n--------------------------- \n");
        HUD.unload();
        pause.unload();
        death.unload();
        tileMap.unload();
        player.unload();
    }
}
