package fr.mrtayai.blocks.state.waiting;

import fr.mrtayai.blocks.BlockMain;
import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.manager.Game;
import fr.mrtayai.blocks.state.playing.GameManager;
import fr.mrtayai.blocks.utils.TeamAreaUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WaitingManager {

    private Game game;
    private BukkitScheduler scheduler;
    private WaitingListener listener;
    private int waitingRunnable;

    private TeamSelectionGUI gui;

    public WaitingManager(Game game){
        this.game = game;
        this.game.setWaitingManager(this);
        this.scheduler = Bukkit.getScheduler();
        this.registerListeners();
        this.launchWaitingRunnable();
        this.gui = new TeamSelectionGUI(this.game);
        Team redTeam = new Team("red", Color.RED);
        redTeam.setItemsToCollect(this.game.getMain().getItems());
        try {
            this.game.getTeamManager().addTeam(redTeam);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TeamAreaUtils redTeamUtils = new TeamAreaUtils(this.game.getLobby().getLobbySpawnLoc().getWorld(), 20000, 1000000, 20000, redTeam);
        this.game.getTeamsBases().add(redTeamUtils);
        Team greenTeam = new Team("green", Color.GREEN);
        greenTeam.setItemsToCollect(this.game.getMain().getItems());
        try {
            this.game.getTeamManager().addTeam(greenTeam);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TeamAreaUtils greenTeamUtils = new TeamAreaUtils(this.game.getLobby().getLobbySpawnLoc().getWorld(), -20000, 1000000, 20000, greenTeam);
        this.game.getTeamsBases().add(greenTeamUtils);
        Team blueTeam = new Team("blue", Color.BLUE);
        blueTeam.setItemsToCollect(this.game.getMain().getItems());
        try {
            this.game.getTeamManager().addTeam(blueTeam);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TeamAreaUtils blueTeamUtils = new TeamAreaUtils(this.game.getLobby().getLobbySpawnLoc().getWorld(), 20000, 1000000, -20000, blueTeam);
        this.game.getTeamsBases().add(blueTeamUtils);
        Team yellowTeam = new Team("yellow", Color.YELLOW);
        yellowTeam.setItemsToCollect(this.game.getMain().getItems());
        try {
            this.game.getTeamManager().addTeam(yellowTeam);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TeamAreaUtils yellowTeamUtils = new TeamAreaUtils(this.game.getLobby().getLobbySpawnLoc().getWorld(), -20000, 1000000, -20000, yellowTeam);
        this.game.getTeamsBases().add(yellowTeamUtils);
    }

    public void launchWaitingRunnable(){
        BukkitTask waiting = this.scheduler.runTaskTimer(this.game.getMain(),new WaitingRunnable(this.game, this), 0L, 20L);
        this.waitingRunnable = waiting.getTaskId();
    }

    public void stopWaitingRunnable(){
        this.scheduler.cancelTask(this.waitingRunnable);
    }

    private void registerListeners(){
        this.listener = new WaitingListener(this.game);
        Bukkit.getServer().getPluginManager().registerEvents(this.listener, this.game.getMain());
    }

    public void stopWaitingState(){
        unregisterListeners();
        stopWaitingRunnable();
        for(Team team : this.game.getTeamManager().getTeams()){
            if(team.getPlayers().isEmpty()){
                try {
                    this.game.getTeamManager().removeTeam(team.getName());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        for(TeamAreaUtils utils : this.game.getTeamsBases()){
            if(this.game.getTeamManager().getTeams().contains(utils.getTeam())){
                utils.build();
            }else{
                this.game.getTeamsBases().remove(utils);
            }
        }
        new GameManager(this.game).start();
        this.game.setPhase(GamePhase.GAME);
    }

    private void registerVillagers(){
        Map<UUID, Team> villagers = new HashMap<>();
        for(TeamAreaUtils base : this.game.getTeamsBases()){
            for(UUID villager : base.getBase().getVillagersUUID()){
                villagers.put(villager, base.getTeam());
            }
        }
        this.game.setVillagers(villagers);
    }

    private void unregisterListeners(){
        HandlerList.unregisterAll(this.listener);
    }

}
