package fr.mrtayai.blocks.state.playing;

import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.manager.Game;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GameRunnable implements Runnable{

    private Game game;
    private GameManager gameManager;

    private int timer = 0;

    public GameRunnable(Game game, GameManager gameManager){
        this.game = game;
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        for(Team team : this.game.getTeamManager().getTeams()){
            if(team.getPercent() == 100){
                Bukkit.getServer().broadcast(
                        Component.text("L'équipe ")
                                .append(Component.text(team.getDisplayName()).color(team.getTextColor()))
                                .append(Component.text(" a fini en "))
                                .append(Component.text(formatSecondsToHHMMSS(timer)))
                                .append(Component.text(" !"))
                );
                this.game.setFinalTime(timer);
                this.gameManager.stop();
            }
        }

        timer++;
    }

    private static String formatSecondsToHHMMSS(int seconds) {
        Duration duration = Duration.ofSeconds(seconds);
        LocalTime time = LocalTime.ofSecondOfDay(duration.getSeconds());
        return time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
