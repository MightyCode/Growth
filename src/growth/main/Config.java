package growth.main;

import growth.util.FileMethods;
import growth.util.XmlReader;

import java.io.File;

import static org.lwjgl.glfw.GLFW.glfwSwapInterval;

/**
 * This class save the config of the game.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Config {

    private String[] values = new String[10];
    private int[][] inputs;

    public static final int FULLSCREEN = 0,
            WINDOW_WIDTH = 1,
            WINDOW_HEIGHT = 2,
            LANGUAGE = 3,
            PART_NB = 4,
            PART_PATH = 5,
            PART_MAX = 6,
            NOISE_VOL = 7,
            MUSIC_VOL = 8,
            LIMITED_FRAMERATE = 9;

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
        values[PART_NB] = "0";
        values[PART_MAX] = "0";

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
            values[PART_MAX] = "0";
            values[PART_MAX] = "-1";

        }
    }

    public String getValue(int index) { return values[index]; }

    public void setValue(String value, int index) {
        setValuesWithoutSave(value, index);
        XmlReader.saveConfiguration();
    }

    public int[][] getInputs() { return inputs; }
    public void setInputs(int[][] inputs) { this.inputs = inputs;}

    public void setValuesWithoutSave(String value,int index){
        // Before attributing
        switch (index){
            case PART_NB:
                if(value.equals("-1") && Integer.parseInt(values[PART_MAX]) > 0) value = "1";
                break;
        }

        // Attibuting the value
        values[index] = value;

        // After attributing
        switch (index){
            case PART_NB:
                values[PART_PATH] = SAVE_PATH + "save-" + values[PART_NB] + "/";
                break;
            case LIMITED_FRAMERATE:
                glfwSwapInterval((values[Config.LIMITED_FRAMERATE].equals("1"))? 1 : 0);
                break;
        }
    }

    /**
     * Save configurations on game's close.
     */
    public static void close(){ XmlReader.saveConfiguration(); }
}
