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
        if(fList.length <= 0){
            Window.config.setPartyMax(1);
            Window.config.setPartyNumber("1");
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
                Window.config.setPartyMax(Window.config.getPartyMax() + 1);
                Window.config.setPartyNumber(String.valueOf(Window.config.getPartyMax()));
            } else {
                Window.config.setPartyMax(pn);
                Window.config.setPartyNumber(String.valueOf(Window.config.getPartyMax()));
            }
        }

        File file = new File(Window.config.getPartyPath());
        file.mkdir();
        if (!FileMethods.copy("resources/files/save.xml", Window.config.getPartyPath() + "save.xml")) {
            Window.console.println("Error on creation of Config.xml");
        }
        file = new File(Window.config.getPartyPath() + "/maps");
        file.mkdir();

        int n = 1;
        while (new File("resources/files/maps/map" + n + ".xml").exists()) {
            n++;
        }

        for (int i = 1; i < n; i++) {
            if (FileMethods.copy("resources/files/maps/map"+i+".xml", Window.config.getPartyPath() + "maps/map" + i + ".xml")) {
            } else { Window.console.println("Error on creation of Config.xml"); }
        }
    }

    public static void checkParty(){
        File file = new File(Window.config.getPartyPath());
        if(!file.exists()){
            File directory = new File("data/saves");
            File[] fList = directory.listFiles();
            if(fList.length <= 0){
                Window.config.setPartyMax(0);
                Window.config.setPartyNumber("0");
                createParty();
            } else {
                int lenght = fList[0].getPath().length();
                Window.config.setPartyMax(Integer.parseInt(fList[0].getPath().substring(lenght-1,lenght)));
                Window.config.setPartyNumber(String.valueOf(Window.config.getPartyMax()));
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

    public static void deleteParty(){
        deleteParty(Window.config.getPartyMax());
    }
}
