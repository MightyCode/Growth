package growth.sound;

import growth.main.Config;
import growth.util.XmlReader;

public class SoundManager {
    private static int musicVolume;
    private static int noiseVolume;

    public SoundManager() {
        musicVolume = Config.getMusicVolume();
        noiseVolume = Config.getNoiseVolume();
    }

    public static int getMusicVolume() {
        return musicVolume;
    }

    public static void setMusicVolume(int newVolume) {
        if(newVolume < 0) musicVolume = 0;
        if(newVolume > 100) musicVolume = 100;
        Config.setMusicVolume(musicVolume);
    }

    public static int getNoiseVolume() {
        return noiseVolume;
    }

    public static void setNoiseVolume(int newNoiseVolume) {
        if(newNoiseVolume < 0) noiseVolume = 0;
        if(newNoiseVolume > 100) noiseVolume = 100;
        Config.setNoiseVolume(noiseVolume);
    }
}
