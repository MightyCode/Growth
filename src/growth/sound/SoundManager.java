package growth.sound;

import growth.main.Config;
import growth.util.XmlReader;

public class SoundManager {
    private static int noiseVolume;
    private static int musicVolume;

    public SoundManager(){
        musicVolume = Integer.parseInt(XmlReader.getValue(Config.CONFIG_PATH, "music", "sound"));
        noiseVolume = Integer.parseInt(XmlReader.getValue(Config.CONFIG_PATH, "noise", "sound"));
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public static void setMusicVolume(){

    }

    public float getNoiseVolume() {
        return noiseVolume;
    }

    public static void setNoiseVolume(){

    }
}
