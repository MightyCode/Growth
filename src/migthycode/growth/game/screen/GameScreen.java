package migthycode.growth.game.screen;

import migthycode.growth.game.entity.Player;
import migthycode.growth.game.tilemap.TileMap;
import migthycode.growth.game.utils.Render;
import migthycode.growth.game.utils.UsefulFunctions;
import migthycode.growth.main.Growth;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GameScreen extends Screen {

    private static final int NORMALSCREEN = 0;
    private static final int TRANSITIONSCREEN = 1;
    private static final int ESCAPESCREEN = 2;
    private static final int INVENTORYSCREEN = 3;
    private static final int DEATHSCREEN = 4;
    public static final int TILESIZE = 64;
    private int screenState;
    // Number of frames to finish a transition between two panels
    private final int transitionTime;
    // The counter to transition
    private int transitionCounter;
    private int transitionSide;
    private final TileMap tileMap;
    // CONSTRUCTOR
    private final Player player;

    GameScreen(ScreenManager screenManager) {
        super(screenManager);

        // Init screen vars
        screenState = NORMALSCREEN;
        transitionTime = 40;
        transitionCounter = 0;

        // Init tileMap
        tileMap = new TileMap(TILESIZE, "/maps/tileset.xml");
        tileMap.setTween(1);


        // Init player
        player = new Player(tileMap, TILESIZE, TILESIZE);
        // Player begin in the ground on Panel 1
        player.setPosition(24 * TILESIZE, 6 * TILESIZE - player.getCY() / 2);

        // Set the position of map before the game
        tileMap.setPosition(Growth.WIDTH / 2 - player.getPosX(), Growth.HEIGHT / 2 - player.getPosY());
    }

    public void update() {
        switch (screenState) {
            case NORMALSCREEN:
                updateKeys();
                updatePlayer();
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
                updateKeys();
                updatePlayer();
                break;
        }
		
		/*} else {
		if (buttonSelected < 0) {
			buttonSelected = button.length-1;
		} else if (buttonSelected > button.length-1){
			buttonSelected = 0;
		}
		
        mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        mouseY = MouseInfo.getPointerInfo().getLocation().getY()-21;*/
    }

    private void updatePlayer() {
        // update player
        player.update();
        tileMap.setPosition(Growth.WIDTH / 2 - player.getPosX(), Growth.HEIGHT / 2 - player.getPosY());

        // Check border player collision to change the map
        //System.out.println(player.getPosX()-10);
        if (player.getPosX() - player.getCX() / 2 <= 0) {
            changeMap(1);
        } else if (player.getPosX() + player.getCX() / 2 >= tileMap.getWidth()) {
            changeMap(3);
        }
    }

    private void updateKeys() {
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

    private void updateTransition() {
        transitionCounter++;
        if (transitionCounter == transitionTime / 2) {
            double[] pos;
            pos = tileMap.setActualMap(transitionSide);
            System.out.println("newPosX :" + pos[0]);
            player.setPosition(pos[0], pos[1] - player.getCY() / 2);
            tileMap.setPosition(Growth.WIDTH / 2 - player.getPosX(), Growth.HEIGHT / 2 - player.getPosY());
            player.setSpeed(0, 0);
        } else if (transitionCounter > transitionTime) {
            screenState = NORMALSCREEN;
            transitionCounter = 0;
        }
    }

    /// Function to display the screen
    public void display() {
        // clear the framebuffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        switch (screenState) {
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
		
		/*if(pause) {
			// Draw global black background
			graphics.drawImage(pauseBg, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
			// Draw pause background
			graphics.drawImage(
					pauseBg,
					(int)GamePanel.WIDTH/5,
					(int)GamePanel.HEIGHT/5,
					(int)(GamePanel.WIDTH - GamePanel.WIDTH/2.5),
					(int)(GamePanel.HEIGHT - GamePanel.HEIGHT/2.5),
					null);
			
			Render.drawStringCenter(graphics, "Pause", titleFont,  GamePanel.WIDTH/2, GamePanel.HEIGHT/5);
			//graphics.drawOval((int)mouseX - 50, (int)mouseY -50 , 100, 100);
			
			for(int i = 0; i < button.length ; i++) {
				if(buttonSelected == i) {
					Render.drawStringCenter(graphics,button[i], buttonSelectedFont, GamePanel.WIDTH/2,GamePanel.HEIGHT/2 + 100*i);
				} else {
					Render.drawStringCenter(graphics,button[i], buttonFont, GamePanel.WIDTH/2,GamePanel.HEIGHT/2 + 100*i);;
				}
			}
		}*/
    }

    private void displayGame() {
        // Draw map
        tileMap.display();
        // Draw player
        player.display();
    }

    private void displayTransition() {
        if (transitionCounter <= transitionTime / 2) {
            Render.rect(0, 0, Growth.WIDTH, Growth.HEIGHT, 0,
                    (float) UsefulFunctions.map(transitionCounter,
                            0, transitionTime / 2, 0, 1.5));
        } else {
            Render.rect(0, 0, Growth.WIDTH, Growth.HEIGHT, 0, (float) UsefulFunctions.map(transitionCounter, transitionTime / 2, transitionTime, 1.5, 0));
        }
    }

    private void changeMap(int side) {
        if (tileMap.getNeighbour(side) != 0) {
            transitionSide = side;
            screenState = TRANSITIONSCREEN;
        }
    }

    public void unload() {
        tileMap.unload();
        player.unload();
    }
}
