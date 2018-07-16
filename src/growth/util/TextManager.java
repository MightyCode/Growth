package growth.util;

import growth.main.Config;
import growth.render.text.FontRenderer;

import java.util.ArrayList;

public class TextManager {
    private String[] word;
    private ArrayList<FontRenderer> users;

    public TextManager() {
        word = XmlReader.loadWord();
        users = new ArrayList<>();
    }

    public String getWord(int number) {
        return word[number];
    }

    public String getWord(FontRenderer newUser, int number) {
        users.add(newUser);
        return word[number];
    }

    public void changeLanguage(String newLanguage) {
        // Change the param
        XmlReader.changeValue(Config.CONFIG_PATH, "language", newLanguage, "general");
        Config.setLanguage(newLanguage);
        // Reload the new language
        word = XmlReader.loadWord();
        // Update every text users object
        for (FontRenderer user : users) {
            user.update();
        }
    }

    public void remove(FontRenderer user) {
        users.remove(user);
    }
}
