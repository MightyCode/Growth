package growth.screen.screens;

import growth.game.Hud;
import growth.entity.EntityManager;
import growth.main.Config;
import growth.main.Window;
import growth.screen.render.Render;
import growth.screen.GameManager;
import growth.screen.overlay.DeathOverlay;
import growth.screen.overlay.OptionOverlay;
import growth.screen.overlay.PauseOverlay;
import growth.game.tilemap.TileMap;
import growth.entity.type.Player;
import growth.util.FileMethods;
import growth.util.XmlReader;
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
    public static final int STATE_INVENTORY = 3;
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
     * Pause Overlay.
     * This variable contains the pause overlay.
     */
    private final PauseOverlay pause;

    /**
     * Death Overlay.
     * This variable contains the overlay of death that appears when the player dies.
     */
    private final DeathOverlay death;

    /**
     * Option overlay.
     * This variable contains the overlay where the player change its options.
     */
    private final OptionOverlay option;

    /**
     * Game screen class constructor.
     * Instance the class and set all of the GameScreen's variables.
     *
     * @param gameManager Add gameManager to change the global screen.
     */
    public GameScreen(GameManager gameManager) {
        super(gameManager);

        File test = new File("data/saves");
        if(!test.exists() && !test.isDirectory()){
            System.out.println("Create file save");
            File save = new File("data\\saves");
            save.mkdirs();
        }

        // Load the current party
        test = new File(Config.SAVE_PATH);
        if(Config.getPartyNumber().equals("-1") || (!test.exists() && !test.isDirectory())){
            if(!FileMethods.copyFromJar("/config/saveOriginal.xml","data/saves/save-1.xml")){
                System.out.println("Error to create the party");
                setScreen(GameManager.MENUSCREEN);
            }
            Config.setPartyNumber("1");
        }

        tileSize = Window.width/20;

        hud = new Hud();
        Render.setClearColor(0.67f, 0.85f, 0.90f, 1f);
        System.out.println("\n-------------------------- \n");

        /* Init gameScreen's variables */
        // Init screen vars
        screenState = STATE_NORMAL;
        // Init screen's overlay
        pause = new PauseOverlay(this);
        death = new DeathOverlay(this);
        option = new OptionOverlay(this){
            @Override
            public void quit(){
                Screen.setState(STATE_PAUSE);
            }
        };

        // Init tileMap
        tileMap = new TileMap( Config.TILESET_PATH);
        GameManager.CAMERA.setTween(0.3f, 1f);

        entityManager.addEntity(new Player(this, tileMap, new Vec2(tileSize)));
        tileMap.setEntity((Player)entityManager.getEntity(0));

        // Player begin in the ground on Panel 1
        tileMap.changeMap(Integer.parseInt(XmlReader.getValue(Config.getPartyPath(),"map","location")),
                Integer.parseInt(XmlReader.getValue(Config.getPartyPath(),"point","location")));

        // Add player for the camera
        entityManager.setCamera(0);

        // Set the position of map before beginning of the game
        GameManager.CAMERA.setPosition(false);
    }

    /**
     * Update the screen in terms of the game's state.
     */
    public void update() {
        switch (screenState) {
            case STATE_NORMAL:
                updateGame();
                break;
            case STATE_TRANSITION:
                updateTransition();
                break;
            case STATE_PAUSE:
                pause.update();
                break;
            case STATE_INVENTORY:
                break;
            case STATE_DEATH:
                death.update();
                break;
            case STATE_OPTION:
                option.update();
                break;
            default:
                updateGame();
                break;
        }
    }

    /**
     * Update the player and the map.
     */
    private void updateGame() {
        if(GameManager.inputsManager.inputPressed(0)) {
            screenState = STATE_PAUSE;
        }
        // Update player
        entityManager.update();
        GameManager.CAMERA.setPosition(true);
        hud.update();

        entityManager.dispose();
    }

    /**
     * Update the transition between two maps.
     */
    private void updateTransition() {
        if (transitionCounter == transitionTime / 2) {
            tileMap.doTransition();
            entityManager.setSpeed(0,0,0);
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
                pause.display();
                break;
            case STATE_INVENTORY:
                break;
            case STATE_OPTION:
                option.display();
                break;
            case STATE_DEATH:
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
        entityManager.display();
        // Draw map in front of the play
        tileMap.display(false);
    }


    /**
     * Display the transition between two map
     */
    private void displayTransition() {
        if (transitionCounter <= transitionTime / 2) {
            GameManager.CAMERA.transition( 0, Math.map(transitionCounter, 0, transitionTime / 2, 0, 1.5f));
        } else {
            GameManager.CAMERA.transition(0, Math.map(transitionCounter, transitionTime / 2, transitionTime, 1.5f, 0));
        }
    }

    /**
     * Unload the texture to free memory.
     */
    public void unload() {
        hud.unload();
        pause.unload();
        death.unload();
        tileMap.unload();
        option.unload();
        entityManager.removeAll();
    }

    /**
     * If the focus ins't on the game, the normal state change to be the escape state.
     * @param b The focus.
     */
    public void focus(boolean b) {
        if (!b && screenState == STATE_NORMAL) screenState = STATE_PAUSE;
    }
}
