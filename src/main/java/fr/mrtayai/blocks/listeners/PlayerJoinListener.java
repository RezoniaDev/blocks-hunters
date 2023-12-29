package fr.mrtayai.blocks.listeners;

import fr.mrtayai.blocks.BlockMain;
import fr.mrtayai.blocks.classes.BlockPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinListener implements Listener{

    private BlockMain main;

    public PlayerJoinListener(BlockMain main){
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event){
        this.main.getLogger().info("Player added : " + event.getPlayer().getName() + "\n");
        this.main.getPlayerManager().addPlayer(event.getPlayer());
    }


}
