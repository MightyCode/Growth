package growth.screen.screens;

import growth.game.Hud;
import growth.game.entity.EntityManager;
import growth.main.Config;
import growth.main.Window;
import growth.render.Render;
import growth.screen.GameManager;
import growth.screen.overlay.DeathOverlay;
import growth.screen.overlay.OptionOverlay;
import growth.screen.overlay.PauseOverlay;
import growth.game.tilemap.TileMap;
import growth.game.entity.type.Player;
import growth.util.XmlReader;
import growth.util.math.Math;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Game class.
 * This class is the game screen.
 *
 * @author MightyCode
 * @version 1.0
 */
public class GameScreen extends Screen {

    public static Hud hud;

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
    public static final int NORMALSCREEN = 0;
    public static final int TRANSITIONSCREEN = 1;
    public static final int ESCAPESCREEN = 2;
    public static final int INVENTORYSCREEN = 3;
    public static final int DEATHSCREEN = 4;
    public static final int OPTIONSCREEN = 5;

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

    private final OptionOverlay option;

    /**
     * GameScreen class constructor.
     * Instance the class and set all of the GameScreen's variables.
     *
     * @param gameManager Add screenManager to change the global screen.
     */
    public GameScreen(GameManager gameManager) {
        super(gameManager);
        Window.destroyWindow();
        Window.createNewWindow();

        tileSize = Window.width/20;

        hud = new Hud();
        hud.load();
        Render.setClearColor(0.67f, 0.85f, 0.90f, 1f);
        System.out.println("\n-------------------------- \n");

        /* Init gameScreen's variables */
        // Init screen vars
        state = NORMALSCREEN;
        // Init screen's overlay
        pause = new PauseOverlay(this);
        death = new DeathOverlay(this);
        option = new OptionOverlay(this){
            @Override
            public void quit(){
                Screen.setState(ESCAPESCREEN);
            }
        };

        // Init tileMap
        tileMap = new TileMap( "/map/tileset.xml");
        GameManager.CAMERA.setTween(0.3f, 1f);

        entityManager.addEntity(new Player(this, tileMap, tileSize, tileSize));
        tileMap.setEntity(entityManager.getEntity(0));

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
            case OPTIONSCREEN:
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
            state = ESCAPESCREEN;
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
            tileMap.givePosition();
            entityManager.setSpeed(0,0,0);
        } else if (transitionCounter > transitionTime) {
            state = NORMALSCREEN;
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

        switch (state) {
            case NORMALSCREEN:
                displayGame();
                hud.display();
                break;
            case TRANSITIONSCREEN:
                displayGame();
                hud.display();
                displayTransition();
                break;
            case ESCAPESCREEN:
                displayGame();
                pause.display();
                break;
            case INVENTORYSCREEN:
                break;
            case OPTIONSCREEN:
                option.display();
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
        entityManager.display();
        // Draw map in front of the play
        tileMap.display(false);
    }


    /**
     * Display the transition between two map
     */
    private void displayTransition() {
        if (transitionCounter <= transitionTime / 2) {
            GameManager.CAMERA.transition( 0, (float) Math.map(transitionCounter, 0, transitionTime / 2, 0, 1.5));
        } else {
            GameManager.CAMERA.transition(0, (float) Math.map(transitionCounter, transitionTime / 2, transitionTime, 1.5, 0));
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

    public void focus(boolean b) {
        if (!b && state == NORMALSCREEN) state = ESCAPESCREEN;
    }
}
