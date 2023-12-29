package fr.mrtayai.blocks.manager;

import fr.mrtayai.blocks.BlockMain;
import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.utils.LobbyAreaUtils;
import fr.mrtayai.blocks.utils.TeamAreaUtils;
import org.bukkit.Location;
import org.jetbrains.annotations.Blocking;

import java.util.List;

public class Game {

    private LobbyAreaUtils lobby;
    private List<TeamAreaUtils> teamsBases;
    private TeamManager teamManager;
    private PlayerManager playerManager;

    private BlockMain main;

    private GamePhase phase;


    public Game(BlockMain main){
        this.main = main;
        this.phase = GamePhase.GENERATION;
        this.playerManager = new PlayerManager(this);
        this.teamManager = new TeamManager(this);

    }

    public void start(){
        this.phase = GamePhase.GENERATION;

    }

    public GamePhase getPhase() {
        return phase;
    }

    public BlockMain getMain() {
        return main;
    }

    public void setLobby(LobbyAreaUtils lobby) {
        this.lobby = lobby;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public List<TeamAreaUtils> getTeamsBases() {
        return teamsBases;
    }


    public LobbyAreaUtils getLobby() {
        return lobby;
    }
}