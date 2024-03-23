package fr.mrtayai.blocks.state.playing;

import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.manager.Game;
import fr.mrtayai.blocks.state.end.EndManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

public class GameManager {

    private Game game;
    private int gameRunnableID;

    private GameListener listener;

    private BukkitScheduler scheduler;

    public GameManager(Game game){
        this.game = game;
        this.scheduler = Bukkit.getScheduler();
        this.listener = new GameListener(this.game);
    }

    public void start(){
        GameRunnable gameRunnable = new GameRunnable(this.game, this);
        Bukkit.getPluginManager().registerEvents(this.listener, this.game.getMain());
        BukkitTask gameTask = this.scheduler.runTaskTimer(this.game.getMain(), gameRunnable, 0L, 20L);
        this.gameRunnableID = gameTask.getTaskId();
        for(BlockPlayer player : this.game.getPlayerManager().getPlayers()){
            this.game.randomTeleport(player.getPlayer());
            player.getPlayer().getInventory().clear();
            player.setPreviousLocation(player.getPlayer().getLocation());
        }
    }

    public void stop(){
        this.scheduler.cancelTask(this.gameRunnableID);
        this.game.setPhase(GamePhase.END);
        HandlerList.unregisterAll(this.listener);
        EndManager manager = new EndManager(this.game);
    }

}
