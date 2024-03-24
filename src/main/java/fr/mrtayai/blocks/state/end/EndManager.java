package fr.mrtayai.blocks.state.end;

import fr.mrtayai.blocks.manager.Game;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class EndManager {

    private Game game;
    private EndListener listener;

    private BukkitScheduler scheduler;

    private int endRunnableId;

    public EndManager(Game game){
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            player.setGameMode(GameMode.SPECTATOR);
        }
        this.game = game;
        this.startListener();
        this.scheduler = Bukkit.getScheduler();
        this.startRunnable();
        this.game.getStatsManager().writeJSON();
    }


    private void startRunnable(){
        EndRunnable endRunnable = new EndRunnable(this);
        BukkitTask endTask = this.scheduler.runTaskTimer(this.game.getMain(), endRunnable, 0L, 20L);
        this.endRunnableId = endTask.getTaskId();
    }

    private void startListener(){
        this.listener = new EndListener(this.game);
        Bukkit.getServer().getPluginManager().registerEvents(this.listener, this.game.getMain());
    }

    public void stopEnding(){
        this.stopRunnable();
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            player.kick(Component.text("Le plugin recharge un monde."), PlayerKickEvent.Cause.PLUGIN);;
        }
        stopListener();
        Bukkit.getServer().spigot().restart();
    }

    private void stopRunnable(){
        this.scheduler.cancelTask(this.endRunnableId);
    }

    private void stopListener(){
        HandlerList.unregisterAll(this.listener);
    }



}
