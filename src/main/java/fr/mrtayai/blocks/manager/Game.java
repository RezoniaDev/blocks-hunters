package fr.mrtayai.blocks.manager;

import fr.mrtayai.blocks.BlockMain;
import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.scoreboard.ScoreboardManager;
import fr.mrtayai.blocks.state.generate.GenerateMain;
import fr.mrtayai.blocks.state.waiting.WaitingManager;
import fr.mrtayai.blocks.utils.LobbyAreaUtils;
import fr.mrtayai.blocks.utils.TeamAreaUtils;
import fr.mrtayai.blocks.classes.Team;
import org.bukkit.Location;
import org.jetbrains.annotations.Blocking;

import java.util.*;

public class Game {

    private LobbyAreaUtils lobby;
    private List<TeamAreaUtils> teamsBases;
    private TeamManager teamManager;
    private PlayerManager playerManager;

    private Map<UUID, Team> teamsVillagers;

    private BlockMain main;

    private WaitingManager manager;

    private ScoreboardManager scoreboardManager;

    private GamePhase phase;


    public Game(BlockMain main){
        this.main = main;
        this.phase = GamePhase.GENERATION;
        this.playerManager = new PlayerManager(this);
        this.teamsBases = new LinkedList<>();
        this.teamsVillagers = new HashMap<>();
        this.teamManager = new TeamManager(this);
        this.scoreboardManager = new ScoreboardManager(this);
    }

    public void start(){
        this.phase = GamePhase.GENERATION;
        GenerateMain manager = new GenerateMain(this);
        manager.startGenerate();
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

    public void setPhase(GamePhase phase){
        this.phase = phase;
    }

    public void setVillagers(Map<UUID,Team> villagers){
        this.teamsVillagers = villagers;
    }

    public Team getVillageTeam(UUID villager){
        return this.teamsVillagers.getOrDefault(villager, null);
    }

    public boolean isVillager(UUID villager){
        return this.teamsVillagers.containsKey(villager);
    }

    public TeamAreaUtils getTeamBase(BlockPlayer player){
        for(TeamAreaUtils utils : this.teamsBases){
            if(utils.getTeam().getName().equals(this.teamManager.getTeamPlayer(player).getName())){
                return utils;
            }
        }
        return null;
    }

    public void setWaitingManager(WaitingManager manager){
        this.manager = manager;
    }

    public void forceStart(){
        this.manager.stopWaitingState();
    }

}