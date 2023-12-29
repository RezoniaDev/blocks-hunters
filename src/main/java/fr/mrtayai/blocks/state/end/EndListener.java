package fr.mrtayai.blocks.state.end;

import fr.mrtayai.blocks.manager.Game;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;

public class EndListener implements Listener {

    private Game game;

    public EndListener(Game game){
        this.game = game;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        event.getPlayer().kick(Component.text("Le plugin recharge un monde."), PlayerKickEvent.Cause.PLUGIN);
    }

}
