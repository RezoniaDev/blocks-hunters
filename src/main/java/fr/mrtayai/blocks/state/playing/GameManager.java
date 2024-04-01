package fr.mrtayai.blocks.state.playing;

import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.manager.Game;
import fr.mrtayai.blocks.state.end.EndManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.TimeUnit;

public class GameManager {

    private Game game;
    private int gameRunnableID;

    private GameListener listener;

    private boolean forceStop = false;

    private BukkitScheduler scheduler;

    private GameRunnable runnable;

    public GameManager(Game game){
        this.game = game;
        this.game.setGameManager(this);
        this.scheduler = Bukkit.getScheduler();
        this.listener = new GameListener(this.game);
    }

    public void start(){
        this.runnable = new GameRunnable(this.game, this);
        Bukkit.getPluginManager().registerEvents(this.listener, this.game.getMain());
        BukkitTask gameTask = this.scheduler.runTaskTimer(this.game.getMain(), runnable, 0L, 20L);
        this.gameRunnableID = gameTask.getTaskId();
        for(BlockPlayer player : this.game.getPlayerManager().getPlayers()){
            player.getPlayer().teleport(this.game.randomTeleport());
            player.getPlayer().getInventory().clear();
            player.getPlayer().clearActivePotionEffects();
            player.getPlayer().setSaturation(5);
            player.setPreviousLocation(player.getPlayer().getLocation());
            this.game.getRecipes().discoverRecipes(player.getPlayer());
        }
    }

    public void stop(){
        this.forceStop = true;
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.scheduler.cancelTask(this.gameRunnableID);
        this.game.setPhase(GamePhase.END);
        HandlerList.unregisterAll(this.listener);
        this.game.getTeamManager().registerTeamScore();
        EndManager manager = new EndManager(this.game);
    }

    public boolean getForceStop(){
        return this.forceStop;
    }

}
