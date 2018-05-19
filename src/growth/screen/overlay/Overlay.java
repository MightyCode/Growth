package growth.screen.overlay;

import growth.main.Window;
import growth.screen.screens.Screen;

public class Overlay {
    Screen screen;

    public Overlay(Screen screen) {
        this.screen = screen;
    }

    public void update(){

    }

    public void display(){

    }

    public void setScreen(int newScreen){
        Window.SCREENMANAGER.setScreen(newScreen);
    }

    public void setState(int newsState){
        screen.setState(newsState);
    }
}
