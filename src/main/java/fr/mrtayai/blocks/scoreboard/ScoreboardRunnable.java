package fr.mrtayai.blocks.scoreboard;

import fr.mrmicky.fastboard.adventure.FastBoard;
import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.manager.Game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;

import java.awt.*;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

public class ScoreboardRunnable implements Runnable {

    private Game game;
    private ScoreboardManager manager;

    public ScoreboardRunnable(Game game, ScoreboardManager manager) {
        this.game = game;
        this.manager = manager;
    }

    @Override
    public void run() {
        int timer = 0;
        for(FastBoard board : this.manager.getBoards().values()){
            if (this.game.getPhase() == GamePhase.WAITING) {
                changeWaitingBoard(board);
            } else if (this.game.getPhase() == GamePhase.GAME) {
                changeGameBoard(board, timer);
            }
        }
        timer++;
    }


    public static String formatSecondsToHHMMSS(int seconds) {
        Duration duration = Duration.ofSeconds(seconds);
        LocalTime time = LocalTime.ofSecondOfDay(duration.getSeconds());
        return time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    private void changeWaitingBoard(FastBoard board){
        board.updateTitle(Component.text("Blocks", NamedTextColor.GOLD));
        Team team = this.game.getTeamManager().getTeamPlayer(this.game.getPlayerManager().getBlockPlayer(board.getPlayer()));

        Component lines = Component.newline();
        lines = lines.append(Component.text("Etat du jeu : En attente"));
        lines = lines.append(Component.newline());
        lines = lines.append(Component.text("Equipe actuelle : " + (team == null ? "Aucune" : team.getName())));
        lines = lines.append(Component.newline());
        lines = lines.append(Component.text("Nombre de joueurs : " + Bukkit.getServer().getOnlinePlayers()));
        lines = lines.append(Component.newline());

        board.updateLines(lines);
    }

    private void changeGameBoard(FastBoard board, int timer){
        BlockPlayer player = this.game.getPlayerManager().getBlockPlayer(board.getPlayer());
        Team team =this.game.getTeamManager().getTeamPlayer(player);
        board.updateTitle(Component.text("Blocks", NamedTextColor.GOLD));

        Component lines = Component.newline();
        lines = lines.append(Component.text("Etat du jeu : En jeu"));
        lines = lines.append(Component.newline());
        lines = lines.append(Component.text("Durée de jeu : " + formatSecondsToHHMMSS(timer)));
        lines = lines.append(Component.newline());
        lines = lines.append(Component.text("Pourcentage de blocs obtenus : " + String.format("%.2f", team.getPercent()) +"%"));
        lines = lines.append(Component.newline());
        lines = lines.append(Component.text("Equipe actuelle : " + this.game.getTeamManager().getTeamPlayer(player).getName()));
        lines = lines.append(Component.newline());
        lines = lines.append(Component.text("Nombre de joueurs : " + Bukkit.getServer().getOnlinePlayers()));
        lines = lines.append(Component.newline());
        board.updateLines(lines);
    }

}
