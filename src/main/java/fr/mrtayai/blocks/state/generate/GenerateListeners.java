package fr.mrtayai.blocks.state.generate;

import fr.mrtayai.blocks.BlockMain;
import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.manager.Game;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.world.WorldLoadEvent;

public class GenerateListeners implements Listener {

    private Game game;

    public GenerateListeners(Game game){
        this.game = game;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if(this.game.getPhase() == GamePhase.GENERATION) {
            event.getPlayer().kick(Component.text("[Blocks] Le plugin est entrain de recharger la carte !"), PlayerKickEvent.Cause.PLUGIN);
        }
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event){
        Bukkit.getLogger().info("[Blocks] Le monde a fini de chargé !");
        this.game.getGenerationManager().stopGenerate();
    }

}
