package growth.game.entity.module.player;

import growth.game.entity.type.Player;
import growth.game.entity.module.Module;
import growth.screen.ScreenManager;

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
        if(ScreenManager.KEY.keyPressed(10)) {
            System.out.println("Before : " + player.getMaxHealthPoint());
            player.setMaxHealthPoint(player.getMaxHealthPoint()-2);
            System.out.println(":(admin):");
            System.out.println("\033[34mNew max player health value : \033[0m" + player.getMaxHealthPoint() + "\n");
        }

        if(ScreenManager.KEY.keyPressed(11)) {
            System.out.println("Before : " + player.getMaxHealthPoint());
            player.setMaxHealthPoint(player.getMaxHealthPoint()+2);
            System.out.println(":(admin):");
            System.out.println("\033[34mNew max player health value : \033[0m" + player.getMaxHealthPoint() + "\n");
        }

        if(ScreenManager.KEY.keyPressed(12)) {
            player.setHealthPoint(player.getHealthPoint()-1);
            System.out.println(":(admin):");
            System.out.println("\033[34mNew player health value : \033[0m" + player.getHealthPoint() + "\n");
        }

        if(ScreenManager.KEY.keyPressed(13)) {
            player.setHealthPoint(player.getHealthPoint()+1);
            System.out.println(":(admin):");
            System.out.println("\033[34mNew player health value : \033[0m" + player.getHealthPoint() + "\n");
        }
    }

    public void display(){}
}
