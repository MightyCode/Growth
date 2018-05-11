package growth.game.screen;

import growth.game.tilemap.TileMap;
import growth.main.Growth;
import growth.game.entity.Player;
import growth.game.render.Render;
import growth.game.utils.Math;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GameScreen extends Screen {

    public static final int TILESIZE = 64;

    // State of game
    private int gameState;
    private static final int NORMALSCREEN = 0;
    private static final int TRANSITIONSCREEN = 1;
    private static final int ESCAPESCREEN = 2;
    private static final int INVENTORYSCREEN = 3;
    private static final int DEATHSCREEN = 4;

    // Number of frames to finish a transition between two panels
    private final int transitionTime;
    // The counter to transition
    private int transitionCounter;
    private int transitionSide;
    private final TileMap tileMap;

    private final Player player;

    // CONSTRUCTOR
    GameScreen(ScreenManager screenManager) {
        super(screenManager);

        // Init screen vars
        gameState = NORMALSCREEN;
        transitionTime = 40;
        transitionCounter = 0;

        // Init tileMap
        tileMap = new TileMap(TILESIZE, "/map/tileset.xml");
        tileMap.setTween(1);

        // Init player
        player = new Player(tileMap, TILESIZE, TILESIZE);
        // Player begin in the ground on Panel 1
        player.setPosition(24 * TILESIZE, 6 * TILESIZE - player.getCY() / 2);

        // Set the position of map before the game
        tileMap.setPosition(Growth.WIDTH / 2 - player.getPosX(), Growth.HEIGHT / 2 - player.getPosY());
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
        tileMap.setPosition(Growth.WIDTH / 2 - player.getPosX(), Growth.HEIGHT / 2 - player.getPosY());

        // Check border player collision to change the map
        if (player.getPosX() - player.getCX() / 2 <= 0) {
            changeMap(1);
        } else if (player.getPosX() + player.getCX() / 2 >= tileMap.getWidth()) {
            changeMap(3);
        }
    }
	
    /**
    * Update the key in game.
    */	
    private void updateGameKeys() {
        // Key update
        if (glfwGetKey(screenManager.getWindow(), GLFW_KEY_W) == 1) player.setJumping(true);
        else player.setJumping(false);

        if (glfwGetKey(screenManager.getWindow(), GLFW_KEY_S) == 1) player.setDown(true);
        else player.setDown(false);

        if (glfwGetKey(screenManager.getWindow(), GLFW_KEY_A) == 1) player.setLeft(true);
        else player.setLeft(false);

        if (glfwGetKey(screenManager.getWindow(), GLFW_KEY_D) == 1) player.setRight(true);
        else player.setRight(false);

        if (glfwGetKey(screenManager.getWindow(), GLFW_KEY_LEFT_SHIFT) == 1) player.setSprint(true);
        else player.setSprint(false);
    }
	
    /**
    * Update the transition between two maps.
    */	
    private void updateTransition() {
        transitionCounter++;
        if (transitionCounter == transitionTime / 2) {
	    // Set the new position of the player in the new map
            double[] pos;
            pos = tileMap.setActualMap(transitionSide);
            player.setPosition(pos[0], pos[1] - player.getCY() / 2);
            tileMap.setPosition(Growth.WIDTH / 2 - player.getPosX(), Growth.HEIGHT / 2 - player.getPosY());
            player.setSpeed(0, 0);
        } else if (transitionCounter > transitionTime) {
            gameState = NORMALSCREEN;
            transitionCounter = 0;
        }
    }

    /**
    * Display the screen in terms of the game'state
    */	
    public void display() {
        // clear the framebuffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

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
        ScreenManager.MONOFONTO.drawText("hello, world!", 22, 20, 680, 0, 0, 0, 0, 0, false);
    }
	
    /**
    * Display the transition between two map
    */	
    private void displayTransition() {
        if (transitionCounter <= transitionTime / 2) {
            Render.rect(0, 0, Growth.WIDTH, Growth.HEIGHT, 0,
                    (float) Math.map(transitionCounter,
                            0, transitionTime / 2, 0, 1.5));
        } else {
            Render.rect(0, 0, Growth.WIDTH, Growth.HEIGHT, 0, (float) Math.map(transitionCounter, transitionTime / 2, transitionTime, 1.5, 0));
        }
    }

    /**
    * Set the change when the player touch a screen'edge
    */	
    private void changeMap(int side) {
        if (tileMap.getNeighbour(side) != 0) {
            transitionSide = side;
            gameState = TRANSITIONSCREEN;
        }
    }

    /**
    * Unload the texture to free memory
    */		
    public void unload() {
        tileMap.unload();
        player.unload();
    }
}
