package growth.main;

import growth.screen.GameManager;
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
    private String projectPath;

    /**
     * Fullscreen state.
     */
    private boolean fullscreen;
    private int currentWindowWidth;
    private int currentWindowHeight;
    private int[][] inputs;
    private String language;

    private String partyNumber;
    private String partyPath;
    private int partyMax;

    private int noiseVolume;
    private  int musicVolume;

    /**
     * Public static final string about the path for different thing.
     */
    public static final String CONFIG_PATH = "data/config/config.xml";
    public static final String SAVE_PATH = "data/saves/";
    public static final String TILESET_PATH = "/textures/game/tiles/tileset.xml";
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
            File data = new File("data");
            data.mkdirs();
        }

        test = new File("data/config");
        if (!test.exists() && !test.isDirectory()){
            Window.console.println("Create file \"config\"");
            File config = new File("data/config");
            config.mkdirs();
        }

        test = new File(Config.CONFIG_PATH);
        if (!test.exists() && !test.isDirectory()){
            Window.console.println("Create file \"config.xml\"");
            if(!FileMethods.copy("resources/files/config.xml","data/config/config.xml")){
                Window.console.println("Error on creation of Config.xml");
                Window.exit();
            }
        }

        test = new File(SAVE_PATH);
        if (!test.exists() && !test.isDirectory()){
            Window.console.println("Create file \"saves\"");
            File data = new File("data/saves");
            data.mkdirs();
            partyMax = -1;
            partyNumber = String.valueOf(-1);
        }
    }

    public boolean getFullscreen() { return fullscreen; }
    public void setFullscreen(int fullscreen) { this.fullscreen = fullscreen == 1; }

    public int getWindowWidth() { return currentWindowWidth; }
    public void setWindowWidth(int windowWidth) { currentWindowWidth = windowWidth; }


    public int getWindowHeight() { return currentWindowHeight; }
    public void setWindowHeight(int windowHeight) { currentWindowHeight = windowHeight; }

    public int[][] getInputs() { return inputs; }
    public void setInputs(int[][] inputs) { this.inputs = inputs; }

    public String getLanguage(){return language;}
    public void setLanguage(String newLanguage){language = newLanguage;}

    public String getPartyNumber() { return partyNumber; }
    public void setPartyNumber(String partyNumber) {
        if(partyNumber.equals("-1") && partyMax > 0) partyNumber = "1";
        else                                         this.partyNumber = partyNumber;

        partyPath = SAVE_PATH + "save-" + partyNumber+ "/";
    }

    public String getPartyPath() { return partyPath; }

    public int getPartyMax(){ return partyMax; }

    public void setPartyMax(int newMax){ partyMax = newMax; }

    public int getMusicVolume() { return musicVolume; }
    public void setMusicVolume(int newVolume){ musicVolume = newVolume;}

    public int getNoiseVolume() { return noiseVolume; }
    public void setNoiseVolume(int newNoiseVolume){ noiseVolume = newNoiseVolume;}
    public void checkSave(){
        /*File test = new File("data/saves");
        if(!test.exists() && !test.isDirectory()){
            Window.console.println("Create file save");
            File save = new File("data/saves");
            save.mkdirs();
        }

        // Load the current party
        test = new File(Config.SAVE_PATH);
        if(Config.getPartyNumber().equals("-1") || (!test.exists() && !test.isDirectory())){
            if(!FileMethods.copy("resources/config/save.xml","data/saves/save-1.xml")){
                Window.console.println("Error to create the party");
                GameManager.setScreen(GameManager.MENUSCREEN);
            }
            Config.setPartyNumber("1");
        }*/
    }


    /**
     * Save configurations on game's close.
     */
    public static void close(){ XmlReader.saveConfiguration(); }
}
