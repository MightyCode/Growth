package growth.util;

import growth.main.Config;
import growth.main.Window;
import growth.screen.GameManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class Party {
    public static void createParty() {
        File directory = new File("data/saves");
        File[] fList = directory.listFiles();

        assert fList != null;
        if(fList.length <= 0){
            Window.config.getValue(Config.PARTY_PATH);
            Window.config.setValue("1", Config.PARTY_MAX);
            Window.config.setValue("1", Config.PARTY_NB);
        } else {
            int pn = 0;
            for(int i = 1; i < fList.length; i++){
                int l1 = fList[i-1].getPath().length();
                int l2 = fList[i].getPath().length();
                if(Integer.parseInt(fList[i].getPath().substring(l2-1,l2)) - Integer.parseInt(fList[i-1].getPath().substring(l1-1,l1))> 1){
                    pn = Integer.parseInt(fList[i-1].getPath().substring(l1-1,l1)) +1;
                }
            }

            if(pn == 0){
                int max = Integer.parseInt(Window.config.getValue(Config.PARTY_PATH)) + 1;
                Window.config.setValue(String.valueOf(max), Config.PARTY_MAX);
                Window.config.setValue(Window.config.getValue(Config.PARTY_MAX), Config.PARTY_NB);
            } else {
                Window.config.setValue(String.valueOf(pn), Config.PARTY_NB);
            }
        }

        File file = new File(Window.config.getValue(Config.PARTY_PATH));
        file.mkdir();
        if (!FileMethods.copy("resources/files/save.xml", Window.config.getValue(Config.PARTY_PATH) + "save.xml")) {
            Window.console.println("Error on creation of save.xml");
        }
        file = new File(Window.config.getValue(Config.PARTY_PATH) + "/maps");
        file.mkdir();

        int n = 1;
        while (new File("resources/files/maps/map" + n + ".xml").exists()) {
            n++;
        }

        for (int i = 1; i < n; i++) {
            if (FileMethods.copy("resources/files/maps/map"+i+".xml", Window.config.getValue(Config.PARTY_PATH) + "maps/map" + i + ".xml")) {
            } else { Window.console.println("Error on creation maps of game"); }
        }
    }

    public static void checkParty(){
        File file = new File(Window.config.getValue(Config.PARTY_PATH));
        if(!file.exists()){
            File directory = new File("data/saves");
            File[] fList = directory.listFiles();

            assert fList != null;
            if(fList.length <= 0){
                Window.config.setValue("0", Config.PARTY_MAX);
                Window.config.setValue("-1", Config.PARTY_NB);
                createParty();
            } else {
                int length = fList[0].getPath().length();
                Window.config.setValue(fList[0].getPath().substring(length-1, length), Config.PARTY_MAX);
                Window.config.setValue(Window.config.getValue(Config.PARTY_MAX), Config.PARTY_NB);
            }
        }

        GameManager.setScreen(GameManager.GAMESCREEN);
    }

    public static void deleteParty(int nbParty){
        File file = new File("data/saves/save-"+ nbParty);

        if(file.delete()) {
            Window.console.println("Save " + nbParty + " deleted");
        } else {
            Window.console.println("Failed to delete the save " + nbParty);
        }
    }
}
