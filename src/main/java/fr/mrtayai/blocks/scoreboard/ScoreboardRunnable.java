package fr.mrtayai.blocks.scoreboard;

import fr.mrmicky.fastboard.adventure.FastBoard;
import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.manager.Game;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class ScoreboardRunnable implements Runnable {

    private Game game;
    private ScoreboardManager manager;

    private int timer = 0;

    public ScoreboardRunnable(Game game, ScoreboardManager manager) {
        this.game = game;
        this.manager = manager;
    }

    @Override
    public void run() {

        for(FastBoard board : this.manager.getBoards().values()){
            if (this.game.getPhase().equals(GamePhase.WAITING)) {
                changeWaitingBoard(board);
                timer=0;
            } else if (this.game.getPhase().equals(GamePhase.GAME)) {
                changeGameBoard(board, timer);
            } else if (this.game.getPhase().equals(GamePhase.END)){
                changeEndBoard(board);
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
        Component teamComponent = Component.text(team == null ? "Aucune" : team.getDisplayName());
        if(team != null){
            teamComponent = teamComponent.color(team.getTextColor());
        }
        board.updateLines(
                Component.text(""),
                Component.text("Etat du jeu : En attente"),
                Component.text(""),
                Component.text("Equipe actuelle : ").append(teamComponent),
                Component.text(""),
                Component.text("Nombre de joueurs : " + this.game.getPlayerManager().getPlayerNumber()),
                Component.text("")
        );
    }

    private void changeGameBoard(FastBoard board, int timer){
        BlockPlayer player = this.game.getPlayerManager().getBlockPlayer(board.getPlayer());
        Team team =this.game.getTeamManager().getTeamPlayer(player);
        board.updateTitle(Component.text("Blocks", NamedTextColor.GOLD));
        board.updateLines(
                Component.text(""),
                Component.text("Etat du jeu : En jeu"),
                Component.text(""),
                Component.text("Durée de jeu : " + formatSecondsToHHMMSS(timer)),
                Component.text(""),
                Component.text("Pourcentage de blocs obtenus : " + String.format("%.2f", team.getPercent()) +"%"),
                Component.text(""),
                Component.text("Equipe actuelle : ").append(Component.text(this.game.getTeamManager().getTeamPlayer(player).getDisplayName()).color(team.getTextColor())),
                Component.text(""),
                Component.text("Nombre de joueurs : " + this.game.getPlayerManager().getPlayerNumber()),
                Component.text("")

        );
    }

    private void changeEndBoard(FastBoard board){
        BlockPlayer player = this.game.getPlayerManager().getBlockPlayer(board.getPlayer());
        Map<Double, UUID> scores = this.game.getTeamManager().getScorePerTeam();
        board.updateTitle(Component.text("Blocks", NamedTextColor.GOLD));
        ArrayList<Component> lines = new ArrayList<>();
        lines.add(Component.text(""));
        lines.add(Component.text("Etat du jeu : Fini"));
        lines.add(Component.text(""));
        lines.add(Component.text("Durée de jeu : " + formatSecondsToHHMMSS(this.game.getFinalTime())));
        lines.add(Component.text(""));
        ArrayList<Team> teams = new ArrayList<>();
        for(UUID uuid : scores.values()){
            teams.add(this.game.getTeamManager().getTeam(uuid));
        }
        for(int i = 0; i < teams.size(); i++){
            Team team = teams.get(i);
            if(i == 0){
                Component text = Component.text("1er : ");
                text = text.append(getTeamComponent(team));
                lines.add(text);
            }else if(i == 1){
                Component text = Component.text("2nde : ");
                text = text.append(getTeamComponent(team));
                lines.add(text);
            } else {
                Component text = Component.text(i+1);
                text = text.append(Component.text("eme : "));
                text = text.append(getTeamComponent(team));
                lines.add(text);
            }
            lines.add(Component.text(""));
        }
        board.updateLines(lines);

    }

    private Component getTeamComponent(Team team){
        Component teamComponent = Component.text("Equipe ")
                .append(Component.text(team.getDisplayName()).color(team.getTextColor()))
                .append(Component.text(" | Pourcentage : "))
                .append(Component.text(team.getPercent()))
                .append(Component.text(" %"));
        return teamComponent;
    }

}
