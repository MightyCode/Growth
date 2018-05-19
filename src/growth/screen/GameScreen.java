package growth.screen;

import growth.render.Render;
import growth.render.texture.Texture;
import growth.tilemap.TileMap;
import growth.entity.Player;
import growth.utils.Math;
import growth.main.Window;
import growth.utils.button.ClickButton;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

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
     * Game state.
     * This variable contains the different states of game.
     */
    private int gameState;

    /**
     * Game's states.
     * These static final variable counting the different state of game.
     */
    private static final int NORMALSCREEN = 0;
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
     * Escape temp.
     * This variable contains the state of escape's key in the previous frame.
     */
    private boolean tempEscape;

    private Texture pause;

    private ClickButton continu, menu;

    /**
     * GameScreen class constructor.
     * Instance the class and set all of the GameScreen's variables.
     *
     * @param screenManager Add screenManager change the global screen.
     */
    GameScreen(ScreenManager screenManager) {
        super(screenManager);

        /* Init gameScreen's variables */
            // Init screen vars
        gameState = NORMALSCREEN;

            // Init tileMap
        tileMap = new TileMap(TILESIZE, "/map/tileset.xml");
        tileMap.setTween(0.9);

            // Init player
        player = new Player(tileMap, TILESIZE, TILESIZE);

            // Init Keys util variables
        tempEscape = false;

            // Init game textures
        pause = new Texture("/images/menu/Pause.png");

            // Init buttons
        continu = new ClickButton(Window.WIDTH*0.5,Window.HEIGHT*0.5,Window.WIDTH*0.1,Window.HEIGHT*0.1,"Continu",this){
            @Override
            public void action(){
                gameState = NORMALSCREEN;
            }
        };
        menu = new ClickButton(Window.WIDTH*0.5,Window.HEIGHT*0.7,Window.WIDTH*0.1,Window.HEIGHT*0.1,"Return",this){
            @Override
            public void action(){
                screen.screenManager.setScreen(ScreenManager.MENUSCREEN);
            }
        };

        // Player begin in the ground on Panel 1
        player.setPosition(24 * TILESIZE, 6 * TILESIZE - player.getCY() / 2);
        // Set the position of map before beginning of the game
        tileMap.setPosition(Window.WIDTH / 2 - player.getPosX(), Window.HEIGHT / 2 - player.getPosY());
    }
     
    /**
     * Update the screen in terms of the game's state.
     */
    public void update() {
        switch (gameState) {
            case NORMALSCREEN:
                updateGameKeys();
                updateGame();
                break;
            case TRANSITIONSCREEN:
                updateTransition();
                break;
            case ESCAPESCREEN:
                updateEscapeKeys();
                continu.update();
                menu.update();
                break;
            case INVENTORYSCREEN:
                break;
            case DEATHSCREEN:
                break;
            default:
                updateGameKeys();
                updateGame();
                break;
        }
    }
    
    /**
     * Update the player and the map.
     */
    private void updateGame() {
        // Update player
        player.update();
        tileMap.setPosition(Window.WIDTH / 2 - player.getPosX(), Window.HEIGHT / 2 - player.getPosY());

        // Check border player collision to change the map
        if (player.getPosX() - player.getCX() / 2 <= 0) {
            changeMap(1);
        } else if (player.getPosX() + player.getCX() / 2 >= tileMap.getSizeX()) {
            changeMap(3);
        }
    }
	
    /**
     * Update the transition between two maps.
     */
    private void updateTransition() {
        transitionCounter++;
        if (transitionCounter == transitionTime / 2) {
	    // Set the new position of the player in the new map
            double[] pos;
            pos = tileMap.changeMap(transitionSide);
            player.setPosition(pos[0], pos[1] - player.getCY() / 2);
            player.setMapPosition();
            player.setSpeed(0, 0);
        } else if (transitionCounter > transitionTime) {
            gameState = NORMALSCREEN;
            transitionCounter = 0;
        }
    }

    /**
     * Update the key in game state.
     */
    private void updateGameKeys() {
        if(ScreenManager.KEY.keyPressed(GLFW_KEY_ESCAPE)) {
            gameState = ESCAPESCREEN;
        }
    }
    /**
     * Update the key in escape state.
     */
    private void updateEscapeKeys() {
        if(ScreenManager.KEY.keyPressed(GLFW_KEY_ESCAPE)) {
            gameState = NORMALSCREEN;
        }
    }

    /**
     * Display the screen in terms of the game'state
     */
    public void display() {
        // clear the framebuffer
        Render.clear();
        switch (gameState) {
            case NORMALSCREEN:
                displayGame();
                break;
            case TRANSITIONSCREEN:
                displayGame();
                displayTransition();
                break;
            case ESCAPESCREEN:
                displayGame();
                displayEscape();
                continu.display();
                menu.display();
                break;
            case INVENTORYSCREEN:
                break;
            case DEATHSCREEN:
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
        if (transitionCounter <= transitionTime / 2) {
            Render.rect(0, 0, Window.WIDTH, Window.HEIGHT, 0,
                    (float) Math.map(transitionCounter,
                            0, transitionTime / 2, 0, 1.5));
        } else {
            Render.rect(0, 0, Window.WIDTH, Window.HEIGHT, 0, (float) Math.map(transitionCounter, transitionTime / 2, transitionTime, 1.5, 0));
        }
    }

    /**
     * Display the transition between two map
     */
    private void displayEscape() {
        // Black rectangle
        Render.rect(0, 0, Window.WIDTH, Window.HEIGHT,0, (float)0.6);
        Render.rect(Window.WIDTH*0.1, Window.HEIGHT*0.15, Window.WIDTH*0.8, Window.HEIGHT*0.75 ,0, (float)0.5);

        // Textures and button
        Render.image(Window.WIDTH*0.40,Window.HEIGHT*0.05,Window.WIDTH*0.2,Window.HEIGHT*0.09,pause.getID(), 1);

    }


    /**
     * Set the change when the player touch a screen'edge.
     *
     * @param side The side where the change will be operated.
     */
    private void changeMap(int side) {
        if (tileMap.getNeighbour(side) != 0) {
            transitionSide = side;
            gameState = TRANSITIONSCREEN;
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
