package growth.util;

import growth.main.Config;
import growth.main.Window;
import growth.screen.GameManager;
import growth.screen.screens.GameScreen;

import java.io.File;
import java.lang.reflect.GenericArrayType;

public class Part {
    public static void createParty() {
        File directory = new File("data/saves");
        File[] fList = directory.listFiles();

        assert fList != null;
        if(fList.length <= 0){
            Window.config.getValue(Config.PART_PATH);
            Window.config.setValue("1", Config.PART_MAX);
            Window.config.setValue("1", Config.PART_NB);
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
                int max = Integer.parseInt(Window.config.getValue(Config.PART_MAX)) + 1;
                Window.config.setValue(String.valueOf(max), Config.PART_MAX);
                Window.config.setValue(Window.config.getValue(Config.PART_MAX), Config.PART_NB);
            } else {
                Window.config.setValue(String.valueOf(pn), Config.PART_NB);
            }
        }

        File file = new File(Window.config.getValue(Config.PART_PATH));
        file.mkdir();
        if (!FileMethods.copy("resources/files/save.xml", Window.config.getValue(Config.PART_PATH) + "save.xml")) {
            Window.console.println("Error on creation of save.xml");
        }
        file = new File(Window.config.getValue(Config.PART_PATH) + "/maps");
        file.mkdir();

        int n = 1;
        while (new File("resources/files/maps/map" + n + ".xml").exists()) {
            n++;
        }

        for (int i = 1; i < n; i++) {
            if (FileMethods.copy("resources/files/maps/map"+ i +".xml", Window.config.getValue(Config.PART_PATH) + "maps/map" + i + ".xml")) {
            } else { Window.console.println("Error on creation maps of game"); }
        }
    }

    public static void checkParty(){
        File file = new File(Window.config.getValue(Config.PART_PATH));
        if(!file.exists()){
            File directory = new File("data/saves");
            File[] fList = directory.listFiles();

            assert fList != null;
            if(fList.length <= 0){
                Window.config.setValue("0", Config.PART_MAX);
                Window.config.setValue("-1", Config.PART_NB);
                createParty();
            } else {
                int length = fList[0].getPath().length();
                Window.config.setValue(fList[0].getPath().substring(length-1, length), Config.PART_MAX);
                Window.config.setValue(Window.config.getValue(Config.PART_MAX), Config.PART_NB);
            }
        }
        Window.console.print(Window.config.getValue(Config.PART_PATH));
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

    public static void deleteTemp(){
        File file = new File(Window.config.getValue(Config.PART_PATH) + "temp");
        if(file.isDirectory()) {
            File[] entries = file.listFiles();
            if (entries != null) {
                for (File entry : entries) entry.delete();
            }
        }
        file.delete();
    }

    public static void copyTemp(){
        File file = new File(Window.config.getValue(Config.PART_PATH) + "temp");
        if(file.exists()) Part.deleteTemp();

        Window.console.println("\n File temp created with " + ((file.mkdir())? "success":"fail") + "\n");

        // Copy map to the temp
        int n = 1;
        while (new File(Window.config.getValue(Config.PART_PATH)+ "/maps/map" + n + ".xml").exists()) n++;

        for (int i = 1; i < n; i++)
            if (FileMethods.copy(
                    Window.config.getValue(Config.PART_PATH) + "maps/map"+ i +".xml",
                    Window.config.getValue(Config.PART_PATH) + "temp/map" + i + ".xml"
            )){}
            else Window.console.println("Error on creation maps of game");

        FileMethods.copy(
                Window.config.getValue(Config.PART_PATH) + "save.xml",
                Window.config.getValue(Config.PART_PATH) + "temp/save.xml"
        );
    }

    public static void savePartyWithPoint(int point){
        XmlReader.changeValue(
                Window.config.getValue(Config.PART_PATH) + "temp/save.xml",
                "map",
                String.valueOf(GameScreen.tileMap.getCurrentMap()),
                "location"
        );

        XmlReader.changeValue(
                Window.config.getValue(Config.PART_PATH) + "temp/save.xml",
                "point",
                String.valueOf(point),
                "location"
        );

        saveParty();
    }

    public static void saveParty(){
        XmlReader.changeValue(
                Window.config.getValue(Config.PART_PATH) + "temp/save.xml",
                "life",
                String.valueOf(GameScreen.entityManager.getPlayer().getHealthPoint()),
                "player"
        );

        XmlReader.changeValue(
                Window.config.getValue(Config.PART_PATH) + "temp/save.xml",
                "form",
                String.valueOf(GameScreen.entityManager.getPlayer().getForm()),
                "player"
        );

        XmlReader.changeValue(
                Window.config.getValue(Config.PART_PATH) + "temp/save.xml",
                "acorn",
                GameScreen.entityManager.getPlayer().getHoldAcorn(),
                "player");

        XmlReader.changeValue(
                Window.config.getValue(Config.PART_PATH) + "temp/save.xml",
                "acornnb",
                String.valueOf(GameScreen.entityManager.getPlayer().getAcornNumber()),
                "player"
        );

        int n = 1;
        while (new File(Window.config.getValue(Config.PART_PATH)+ "/temp/map" + n + ".xml").exists()) n++;

        for (int i = 1; i < n; i++)
            if (FileMethods.copy(
                    Window.config.getValue(Config.PART_PATH) + "temp/map"+ i +".xml",
                    Window.config.getValue(Config.PART_PATH) + "maps/map" + i + ".xml"
            )){}
            else Window.console.println("Error on creation save of game");

        FileMethods.copy(
                Window.config.getValue(Config.PART_PATH) + "temp/save.xml",
                Window.config.getValue(Config.PART_PATH) + "save.xml"
        );
    }
}
