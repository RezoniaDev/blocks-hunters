package fr.mrtayai.blocks.state.end;

import fr.mrtayai.blocks.manager.Game;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerKickEvent;

public class EndManager {

    private Game game;
    private EndListener listener;

    public EndManager(Game game){
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            player.setGameMode(GameMode.SPECTATOR);
        }
        this.game = game;
        this.startListener();
    }


    private void startListener(){
        this.listener = new EndListener(this.game);
        Bukkit.getServer().getPluginManager().registerEvents(this.listener, this.game.getMain());
    }

    public void stopEnding(){
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            player.kick(Component.text("Le plugin recharge un monde."), PlayerKickEvent.Cause.PLUGIN);;
        }
        stopListener();
        this.game.start();
    }

    private void stopListener(){
        HandlerList.unregisterAll(this.listener);
    }



}
