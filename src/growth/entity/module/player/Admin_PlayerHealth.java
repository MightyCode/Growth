package growth.entity.module.player;

import growth.entity.type.Player;
import growth.entity.module.Module;
import growth.main.Window;
import growth.screen.GameManager;

/**
 * Administrator player health module class.
 * This class is the module use by a administrator-player to change it health and it max health.
 *
 * @author MightyCode
 * @version 1.0
 */
public class Admin_PlayerHealth extends Module {
    /**
     * Player.
     * This variable contains the reference to the player who use this module.
     */
    private final Player player;

    /**
     * Administrator player health module class constructor.
     * Instance the class and set the reference to the entity.
     *
     * @param player Player using the module.
     */
    public Admin_PlayerHealth(Player player){
        super(player);

        // Init variables
        this.player = player;
    }

    /**
     * Update the module.
     */
    public void update(){
        if(GameManager.inputsManager.inputPressed(11)) {
            Window.console.println("Before : " + player.getMaxHealthPoint());
            player.setMaxHealthPoint(player.getMaxHealthPoint()-2);
            Window.console.println(":(admin):");
            Window.console.println("\033[34mNew max player health value : \033[0m" + player.getMaxHealthPoint() + "\n");
        }

        if(GameManager.inputsManager.inputPressed(12)) {
            Window.console.println("Before : " + player.getMaxHealthPoint());
            player.setMaxHealthPoint(player.getMaxHealthPoint()+2);
            Window.console.println(":(admin):");
            Window.console.println("\033[34mNew max player health value : \033[0m" + player.getMaxHealthPoint() + "\n");
        }

        if(GameManager.inputsManager.inputPressed(13)) {
            player.setHealthPoint(player.getHealthPoint()-1);
            Window.console.println(":(admin):");
            Window.console.println("\033[34mNew player health value : \033[0m" + player.getHealthPoint() + "\n");
        }

        if(GameManager.inputsManager.inputPressed(14)) {
            player.setHealthPoint(player.getHealthPoint()+1);
            Window.console.println(":(admin):");
            Window.console.println("\033[34mNew player health value : \033[0m" + player.getHealthPoint() + "\n");
        }
    }
}
