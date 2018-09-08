package growth.main;

import growth.sound.SoundManager;
import growth.util.FileMethods;
import growth.util.XmlReader;

import java.io.File;

/**
 * This class save the config of the game.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Config {

    /**
     * Project path
     */
    private static String projectPath;

    /**
     * Fullscreen state.
     */
    private static boolean fullscreen;

    /**
     * Current window size in width.
     */
    private static int currentWindowWidth;

    /**
     * Current window size in height.
     */
    private static int currentWindowHeight;

    /**
     * Basic table of inputs.
     */
    private static int[][] inputs;

    /**
     * Current language use.
     */
    private static String language;

    /**
     * Current party number.
     */
    private static String partyNumber;

    /**
     * Current party path.
     */
    private static String partyPath;

    /**
     * Current noiseVolume.
     */
    private static int noiseVolume;

    /**
     * Current musicVolume.
     */
    private static int musicVolume;

    /**
     * Public static final string about the path for different thing.
     */
    public static final String CONFIG_PATH = "data/config/config.xml";
    public static final String SAVE_PATH = "data/saves/save-";
    public static final String MAP_PATH = "/map/";
    public static final String MAP_OPTION_PATH = "/map/mapOptions.xml";
    public static final String TILESET_PATH = "/map/tileset.xml";
    public static final String ENTITY_PATH = "growth.entity.type.";

    /**
     * Class constructor.
     */
    public Config(){
        // Information
        projectPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();

        // If the directory don't exists
        File test = new File("data/");
        if (!test.exists() && !test.isDirectory()){
            Window.console.println("Create file Data");
            File config = new File("data/config");
            File data = new File("data/saves");
            config.mkdirs();
            data.mkdirs();

            if(!FileMethods.copy("resources/config/configOriginal.xml","data/config/config.xml")){
                Window.console.println("Error on creation of Data");
                Window.exit();
            }
        }

        test = new File("data/config");
        if (!test.exists() && !test.isDirectory()){
            Window.console.println("Create file Config");
            File config = new File("data/config");
            config.mkdirs();
            if(!FileMethods.copy("resources/config/configOriginal.xml","data/config/config.xml")){
                Window.console.println("Error on creation of config");
                Window.exit();
            }
        }


        test = new File(Config.CONFIG_PATH);
        if (!test.exists() && !test.isDirectory()){
            Window.console.println("Create file Config.xml");
            if(!FileMethods.copy("resources/config/configOriginal.xml","data/config/config.xml")){
                Window.console.println("Error on creation of Config.xml");
                Window.exit();
            }
        }

        // Load configurationss
        XmlReader.loadConfig();
        SoundManager.setNoiseVolume(noiseVolume);
        SoundManager.setMusicVolume(musicVolume);
    }

    /**
     * Get the fullscreen state.
     * @return The state (boolean).
     */
    public static boolean getFullscreen() { return fullscreen; }

    /**
     * Set the fullscreen state.
     * @param fullscreen The fullscreen state.
     */
    public static void setFullscreen(int fullscreen) { Config.fullscreen = fullscreen == 1; }


    /**
     * Get the window width size.
     * @return The size(int).
     */
    public static int getWindowWidth() { return currentWindowWidth; }

    /**
     * Set the window size in width.
     * @param windowWidth The new window size width.
     */
    public static void setWindowWidth(int windowWidth) { Config.currentWindowWidth = windowWidth; }

    /**
     * Get the window height size.
     * @return The size(int).
     */
    public static int getWindowHeight() { return currentWindowHeight; }

    /**
     * Set the window size in height.
     * @param windowHeight The new window size height.
     */
    public static void setWindowHeight(int windowHeight) { Config.currentWindowHeight = windowHeight; }

    /**
     * Get the inputs.
     * @return The inputs table (int[][])
     */
    public static int[][] getInputs() { return inputs; }

    /**
     * Set the new inputs
     * @param inputs The new inputs.
     */
    public static void setInputs(int[][] inputs) { Config.inputs = inputs; }

    /**
     * Get current the language.
     * @return The aka(string)
     */
    public static String getLanguage(){return language;}

    /**
     * Set the new game's language.
     * @param newLanguage The new language.
     */
    public static void setLanguage(String newLanguage){language = newLanguage;}

    /**
     * Get the current number of the party played.
     * @return The party number.
     */
    public static String getPartyNumber() { return partyNumber; }

    /**
     * Set the new party number.
     * @param partyNumber The new party number.
     */
    public static void setPartyNumber(String partyNumber) {
        Config.partyNumber = partyNumber;
        partyPath = SAVE_PATH + partyNumber + ".xml";
    }

    /**
     * Get party path.
     * @return The party path(string).
     */
    public static String getPartyPath() { return partyPath; }

    /**
     * Set the party path.
     * @param partyPath The new party path.
     */
    public static void setPartyPath(String partyPath) { Config.partyPath = partyPath; }

    /**
     * Get music volume.
     * @return The music volume(int).
     */
    public static int getMusicVolume() { return musicVolume; }

    /**
     * Set the new music volume.
     * @param newVolume The new music volume.
     */
    public static void setMusicVolume(int newVolume){ musicVolume = newVolume; }

    /**
     * Get noise volume.
     * @return The noise volume(int).
     */
    public static int getNoiseVolume() { return noiseVolume; }

    /**
     * Set the new noise volume.
     * @param newNoiseVolume The new noise volume.
     */
    public static void setNoiseVolume(int newNoiseVolume){ noiseVolume = newNoiseVolume; }

    /**
     * Save configurations on game's close.
     */
    public static void close(){ XmlReader.saveConfiguration(); }
}
