package growth.entity.module.player;

import growth.entity.Eobject.Emoveable;
import growth.entity.module.Module;
import growth.main.Window;
import growth.screen.GameManager;
import growth.util.Part;

public class Admin_Save extends Module {

    /**
     * Mother module class constructor.
     * Instance the class and set the reference to the entity.
     *
     * @param entity Entity using the module.
     */
    public Admin_Save(Emoveable entity) {
        super(entity);
    }

    public void update(){
        if(GameManager.inputsManager.inputPressed(16)) {
            Part.saveParty();
        }
    }
}
