package growth.main;

import growth.util.XmlReader;

public class Config {
    private boolean fullscreen;
    private int windowWidth;
    private int windowHeight;
    private int[][] inputs;
    private String language;

    public Config(String path){
        XmlReader.loadConfig(path, this);
    }

    boolean getFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(int fullscreen) { this.fullscreen = fullscreen == 1; }

    int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int[][] getInputs() {
        return inputs;
    }

    public void setInputs(int[][] inputs) {
        this.inputs = inputs;
    }

    public void setLanguage(String newLanguage){language = newLanguage;}

    public String getLanguage(){return language;}
}
