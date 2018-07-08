package growth.main;

/**
 * Main class of the game.
 *
 * @author MightyCode
 * @version of the current game developed: 0.3.3
 */
public class Growth {

    public static final boolean ADMIN = true;

    /**
     * Window.
     * This global variable contains all of the main game structure.
     */
    public static Window window;

    /**
     * Run the game.
     */
    public static void main(String[] args) {
        window = new Window();
        window.run();
    }
}
