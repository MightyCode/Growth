package growth.screen.screens;

import growth.render.Render;
import growth.render.texture.Texture;
import growth.screen.ScreenManager;
import growth.screen.overlay.DeathOverlay;
import growth.screen.overlay.PauseOverlay;
import growth.tilemap.TileMap;
import growth.entity.Player;
import growth.utils.Math;
import growth.main.Window;
import growth.utils.button.ClickButton;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

/**
 * Game class.
 * This class is the game screen.
 *
 * @author MightyCode
 * @version 1.0
 */
public class GameScreen extends Screen {

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
    public static final int TRANSITIONSCREEN = 1;
    public static final int ESCAPESCREEN = 2;
    public static final int INVENTORYSCREEN = 3;
    public static final int DEATHSCREEN = 4;

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
     * TileMap.
     * This variable contains the tileMap to interact with it.
     */
    private final TileMap tileMap;

    /**
     * Player.
     * This variable contains the player to ... play.
     */
    private final Player player;

    private PauseOverlay pause;
    private DeathOverlay death;

    /**
     * GameScreen class constructor.
     * Instance the class and set all of the GameScreen's variables.
     *
     * @param screenManager Add screenManager change the global screen.
     */
    public GameScreen(ScreenManager screenManager) {
        super(screenManager);

        /* Init gameScreen's variables */
            // Init screen vars
        state = NORMALSCREEN;
            // Init screen's overlay
        pause = new PauseOverlay(this);
        death = new DeathOverlay(this);

            // Init tileMap
        tileMap = new TileMap(TILESIZE, "/map/tileset.xml");
        tileMap.setTween(0.9);

            // Init player
        player = new Player(tileMap, TILESIZE, TILESIZE);

        // Player begin in the ground on Panel 1
        player.setPosition(24 * TILESIZE, 6 * TILESIZE - player.getCY() / 2);
        // Set the position of map before beginning of the game
        tileMap.setPosition(Window.WIDTH / 2 - player.getPosX(), Window.HEIGHT / 2 - player.getPosY());
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

        if(ScreenManager.KEY.keyPressed(GLFW_KEY_ESCAPE)) {
            state = ESCAPESCREEN;
        }
        // Update player
        player.update();
        tileMap.setPosition(Window.WIDTH / 2 - player.getPosX(), Window.HEIGHT / 2 - player.getPosY());

        // Check border player collision to change the map
        if (player.getPosX() - player.getCX() / 2 <= 0) {
            changeMap(1);
        } else if (player.getPosX() + player.getCX() / 2 >= tileMap.getSizeX()) {
            changeMap(3);
        } else if(player.getPosY() + player.getCY()/ 2 >= tileMap.getSizeY()){
            if(tileMap.getNeighbour(4) != 0){
                changeMap(4);
            } else{
                player.setPosition(-100,-100);
                state = DEATHSCREEN;
            }
        }
    }

    /**
     * Update the transition between two maps.
     */
    private void updateTransition() {
        transitionCounter++;
        if (transitionCounter == transitionTime / 2) {
            double[] pos;
            pos = tileMap.changeMap(transitionSide);
            System.out.println("newPosX :" + pos[0]);
            player.setPosition(pos[0], pos[1] - player.getCY() / 2);
            tileMap.setPosition(Window.WIDTH / 2 - player.getPosX(), Window.HEIGHT / 2 - player.getPosY());
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
        // Draw map
        tileMap.display();
        // Draw player
        player.display();
    }


    /**
     * Display the transition between two map
     */
    private void displayTransition() {
        displayGame();
        if (transitionCounter <= transitionTime / 2) {
            Render.rect(0, 0, Window.WIDTH, Window.HEIGHT, 0,
                    (float) Math.map(transitionCounter,
                            0, transitionTime / 2, 0, 1.5));
        } else {
            Render.rect(0, 0, Window.WIDTH, Window.HEIGHT, 0, (float) Math.map(transitionCounter, transitionTime / 2, transitionTime, 1.5, 0));
        }
    }

    /**
     * Set the change when the player touch a screen'edge.
     *
     * @param side The side where the change will be operated.
     */
    private void changeMap(int side) {
        if (tileMap.getNeighbour(side) != 0) {
            transitionSide = side;
            state = TRANSITIONSCREEN;
        }
    }

    /**
     * Unload the texture to free memory.
     */
    public void unload() {
        tileMap.unload();
        player.unload();
    }
}