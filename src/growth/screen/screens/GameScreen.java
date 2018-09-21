package growth.screen.screens;

import growth.game.Hud;
import growth.entity.EntityManager;
import growth.main.Config;
import growth.main.Window;
import growth.screen.overlay.Overlay;
import growth.screen.render.Render;
import growth.screen.GameManager;
import growth.screen.overlay.DeathOverlay;
import growth.screen.overlay.OptionOverlay;
import growth.screen.overlay.PauseOverlay;
import growth.game.tilemap.TileMap;
import growth.entity.type.Player;
import growth.util.*;
import growth.util.math.Math;
import growth.util.math.Vec2;
import java.io.File;

/**
 * Game class.
 * This class is the game screen.
 *
 * @author MightyCode
 * @version 1.0
 */
public class GameScreen extends Screen {

    public static Hud hud;

    /**
     * The entity manager manage every entity of the game.
     */
    public static final EntityManager entityManager = new EntityManager();

    /**
     * Tile size.
     * This variable contains the definitive tile size.
     */
    public static int tileSize;

    /**
     * Game's states.
     * These static final variable counting the different state of game.
     */
    public static final int STATE_NORMAL = 0;
    public static final int STATE_TRANSITION = 1;
    public static final int STATE_PAUSE = 2;
    //public static final int STATE_INVENTORY = 3;
    public static final int STATE_DEATH = 4;
    public static final int STATE_OPTION = 5;

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
     * TileMap.
     * This variable contains the tileMap to interact with it.
     */
    public static TileMap tileMap = null;


    /**
     * Game screen class constructor.
     * Instance the class and set all of the GameScreen's variables.
     */
    public GameScreen() {
        super();
        Window.console.println("\n-------------------------- \n");
        Part.copyTemp();

        XmlReader.saveConfiguration();
        tileSize = Window.width/20;

        hud = new Hud();
        /* Init gameScreen's variables */
        // Init screen vars
        screenState = STATE_NORMAL;

        // Init tileMap
        tileMap = new TileMap(Config.TILESET_PATH);
        GameManager.camera.setTween(0.3f, 1f);

        Player player = new Player(this, tileMap, new Vec2(tileSize));
        entityManager.setPlayer(player);
        LoadPlayerTree.playerTree();

        tileMap.setEntity(player);

        // Player begin in the ground on Panel 1
        tileMap.begin(
                Integer.parseInt(
                        XmlReader.getValueNoRes(
                                Window.config.getValue(Config.PART_PATH) + "save.xml", "map", "location" )
                ),
                Integer.parseInt(
                        XmlReader.getValueNoRes(Window.config.getValue(Config.PART_PATH) + "save.xml", "point", "location" )
                )
        );

        // Add player for the camera
        GameManager.camera.setEntityToCamera(player);
    }

    /**
     * Update the screen in terms of the game's state.
     */
    public void update() {
        currentOverlay.update();
        switch (screenState) {
            case STATE_NORMAL:
                updateGame();
                break;
            case STATE_TRANSITION:
                updateTransition();
                break;
        }
    }

    /**
     * Update the player and the map.
     */
    private void updateGame() {
        if(GameManager.inputsManager.inputPressed(0)){
            setState(STATE_PAUSE);
        }

        // Update player
        entityManager.update();
        GameManager.camera.setPosition(true);
        hud.update();
        entityManager.dispose();
    }

    /**
     * Update the transition between two maps.
     */
    private void updateTransition() {
        if (transitionCounter == transitionTime / 2) {
            tileMap.doTransition();
            entityManager.setSpeed(0,0);
        } else if (transitionCounter > transitionTime) {
            screenState = STATE_NORMAL;
            transitionCounter = 0;
        }
        transitionCounter++;
    }

    /**
     * Display the screen in terms of the game'state
     */
    public void display() {
        // clear the framebuffer
        Render.clear();

        switch (screenState) {
            case STATE_NORMAL:
                displayGame();
                hud.display();
                break;
            case STATE_TRANSITION:
                displayGame();
                hud.display();
                displayTransition();
                break;
            case STATE_PAUSE:
                displayGame();
                break;
            case STATE_DEATH:
                displayGame();
                break;
        }
        currentOverlay.display();
    }

    /**
     * Display the map and the player
     */
    private void displayGame() {
        // Draw map behind the player
        tileMap.display(true);
        // Draw player
        entityManager.display();
        // Draw map in front of the play
        tileMap.display(false);
    }

    /**
     * Display the transition between two map
     */
    private void displayTransition() {
        if (transitionCounter <= transitionTime / 2) {
            GameManager.camera.transition( 0, Math.map(transitionCounter, 0, transitionTime / 2, 0, 1.5f));
        } else {
            GameManager.camera.transition(0, Math.map(transitionCounter, transitionTime / 2, transitionTime, 1.5f, 0));
        }
    }

    @Override
    public void setState(int newState){
        super.setState(newState);
        switch (screenState) {
            case STATE_PAUSE:
                currentOverlay = new PauseOverlay();
                break;
            case STATE_DEATH:
                currentOverlay = new DeathOverlay();
                break;
            case STATE_OPTION:
                currentOverlay = new OptionOverlay(){
                    @Override
                    public void quit(){
                        GameManager.setState(STATE_PAUSE);
                    }
                };
                break;
            default:
                 currentOverlay = new Overlay();
                 break;
        }
    }

    /**
     * Unload the texture to free memory.
     */
    public void unload() {
        super.unload();
        entityManager.removeAll();
        entityManager.getPlayer().unload();
        entityManager.setPlayer(null);
        hud.unload();
        tileMap.unload();
        currentOverlay.unload();

        Part.deleteTemp();
    }

    /**
     * If the focus ins't on the game, the normal state change to be the escape state.
     * @param b The focus.
     */
    public void focus(boolean b) {
        /*if (!b && screenState == STATE_NORMAL) setState(STATE_PAUSE);*/
    }
}
