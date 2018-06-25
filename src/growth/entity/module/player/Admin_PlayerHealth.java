package growth.entity.module.player;

import growth.entity.Player;
import growth.entity.module.Module;
import growth.screen.ScreenManager;

public class Admin_PlayerHealth extends Module {
    /**
     * Player.
     * This variable contains the reference to the player who use this module.
     */
    private final Player player;

    public Admin_PlayerHealth(Player player){
        super(player);

        // Init variables
        this.player = player;
    }

    /**
     * Update the module and the player's jump.
     */
    public void update(){
        if(ScreenManager.KEY.keyPressed(10)) {
            player.setMaxHealthPoint(player.getMaxHealthPoint()-2);
            System.out.println(":(admin):");
            System.out.println("\033[34mNew max player health value : \033[0m" + player.getMaxHealthPoint() + "\n");
        }

        if(ScreenManager.KEY.keyPressed(11)) {
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
}
