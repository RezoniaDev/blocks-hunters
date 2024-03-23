package fr.mrtayai.blocks.scoreboard;

import fr.mrmicky.fastboard.adventure.FastBoard;
import fr.mrtayai.blocks.manager.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager {

    private Game game;

    private int scoreboardRunnable;

    private Map<UUID, FastBoard> boards = new HashMap<>();

    public ScoreboardManager(Game game){
        this.game = game;
        BukkitTask scoreboardTask = Bukkit.getScheduler().runTaskTimer(this.game.getMain(), new ScoreboardRunnable(this.game, this), 0L, 20L);
        this.scoreboardRunnable = scoreboardTask.getTaskId();
    }

    public Map<UUID, FastBoard> getBoards() {
        return boards;
    }

    public void addBoard(Player player){
        FastBoard board = new FastBoard(player);
        this.boards.put(player.getUniqueId(), board);
    }

    public void removeBoard(Player player){
        this.boards.remove(player.getUniqueId());
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(this.scoreboardRunnable);
    }



}
