package growth.entity.type;

import growth.screen.screens.GameScreen;
import growth.util.Part;

public class SavePoint extends InteractObject {

    private int point;

    public static final int SAVE_POINT = 6;

    public SavePoint(String[] att){
        super(att);
        this.point = Integer.parseInt(att[SAVE_POINT])-1;
    }

    @Override
    public void action() {
        Part.savePartyWithPoint(point);
    }
}