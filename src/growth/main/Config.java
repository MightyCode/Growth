package growth.main;

import growth.util.XmlReader;

public class Config {
    private static boolean fullscreen;
    private static int windowWidth;
    private static int windowHeight;
    private static int[][] inputs;
    private static String language;

    public static final String CONFIG_PATH = "/config/config.xml";
    public static final String SAVE_PATH = "/config/saves/";
    public static final String MAP_PATH = "/map/";
    public static final String MAP_OPTION_PATH = "/map/mapOptions.xml";


    public Config(String path){
        XmlReader.loadConfig(path, this);
    }

    public static boolean getFullscreen() {
        return fullscreen;
    }

    public static void setFullscreen(int fullscreen) { Config.fullscreen = fullscreen == 1; }

    public static int getWindowWidth() {
        return windowWidth;
    }

    public static void setWindowWidth(int windowWidth) {
        Config.windowWidth = windowWidth;
    }

    public static int getWindowHeight() {
        return windowHeight;
    }

    public static void setWindowHeight(int windowHeight) {
        Config.windowHeight = windowHeight;
    }

    public static int[][] getInputs() {
        return inputs;
    }

    public static void setInputs(int[][] inputs) {
        Config.inputs = inputs;
    }

    public static void setLanguage(String newLanguage){language = newLanguage;}

    public static String getLanguage(){return language;}
}
