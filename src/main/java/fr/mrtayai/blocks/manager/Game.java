package fr.mrtayai.blocks.manager;

import fr.mrtayai.blocks.BlockMain;
import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.recipes.EkaliaRecipes;
import fr.mrtayai.blocks.scoreboard.ScoreboardManager;
import fr.mrtayai.blocks.state.generate.GenerateMain;
import fr.mrtayai.blocks.state.playing.GameManager;
import fr.mrtayai.blocks.state.waiting.WaitingManager;
import fr.mrtayai.blocks.utils.LobbyAreaUtils;
import fr.mrtayai.blocks.utils.TeamAreaUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class Game {

    private LobbyAreaUtils lobby;
    private List<TeamAreaUtils> teamsBases;

    private int finalTime = -1;
    private TeamManager teamManager;

    private StatsManager statsManager;

    private PlayerManager playerManager;

    private Map<UUID, Team> teamsVillagers;

    private Map<UUID, Inventory> playerInventories;

    private List<String> playerToAdd;

    private HashMap<String, Team> playersToTeam;

    private BlockMain main;

    private WaitingManager manager;

    private GameManager gameManager;

    private ScoreboardManager scoreboardManager;

    private GenerateMain generationManager;


    private GamePhase phase;

    private Random random;

    private EkaliaRecipes recipes;

    private boolean pause;


    public Game(BlockMain main) {
        this.main = main;
        this.pause = false;
        this.playerInventories = new HashMap<>();
        this.phase = GamePhase.GENERATION;
        this.playersToTeam = new HashMap<>();
        this.playerToAdd = new ArrayList<>();
        this.random = new Random();
        this.playerManager = new PlayerManager(this);
        this.teamsBases = new ArrayList<>();
        this.teamsVillagers = new HashMap<>();
        this.teamManager = new TeamManager(this);
        this.scoreboardManager = new ScoreboardManager(this);
        this.statsManager = new StatsManager(this);
    }

    public void setRecipe(EkaliaRecipes recipe) {
        this.recipes = recipe;
    }

    public void addPlayerInGame(String username, Team team) {
        this.playerToAdd.add(username);
        this.playersToTeam.put(username, team);
    }

    public List<String> getPlayerToAdd() {
        return playerToAdd;
    }

    public Team getTeamByWaitingPlayer(String username) {
        return this.playersToTeam.get(username);
    }

    public EkaliaRecipes getRecipes() {
        return recipes;
    }

    public void setPause(boolean a) {
        if (isPaused() && a) {
            return;
        } else if (isPaused() && !a) {
            Bukkit.broadcast(Component.text("Le jeu a été mis en pause !"));
            this.pause = a;
        } else if (!isPaused() && a) {
            Bukkit.broadcast(Component.text("Le jeu reprend !"));
            this.pause = a;
        } else {
            return;
        }
    }

    public boolean isPaused() {
        return this.pause;
    }

    public void start() {
        this.generationManager = new GenerateMain(this);
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

    public void setPhase(GamePhase phase) {
        this.phase = phase;
    }

    public void setVillagers(Map<UUID, Team> villagers) {
        this.teamsVillagers = villagers;
    }

    public Team getVillagerTeam(UUID villager) {
        return this.teamsVillagers.getOrDefault(villager, null);
    }

    public boolean isVillager(UUID villager) {
        return this.teamsVillagers.containsKey(villager);
    }

    public TeamAreaUtils getTeamBase(BlockPlayer player) {
        for (TeamAreaUtils utils : this.teamsBases) {
            Team teamPlayer = this.teamManager.getTeamPlayer(player);
            if (teamPlayer != null) {
                if (utils.getTeamID().equals(teamPlayer.getTeamID())) {
                    return utils;
                }
            }
        }
        return null;
    }

    public void setWaitingManager(WaitingManager manager) {
        this.manager = manager;
    }

    public void forceStart() {
        this.manager.stopWaitingState();
    }

    public void forceEnd() {
        this.gameManager.stop();
    }


    public Location randomTeleport() {
        World farmWorld = Bukkit.getWorld("world");
        int randomX = this.random.nextInt((10001 - (-10000) + 1)) - 10000;
        int randomZ = this.random.nextInt((10001 - (-10000) + 1)) - 10000;
        int safeY = farmWorld.getHighestBlockYAt(randomX, randomZ);
        Location location = new Location(farmWorld, randomX, safeY, randomZ);
        while (location.getBlock().getType() != Material.AIR) {
            safeY++;
            location = new Location(farmWorld, randomX, safeY, randomZ);
        }
        this.loadChunks(location);
        return location;
    }

    public void prepareTeamBases() {
        this.teamsBases.removeIf(base -> this.getTeamManager().hasTeam(base.getTeamID()));
        this.teamsBases.forEach(teamAreaUtils -> {
            teamAreaUtils.build();
            this.loadChunks(teamAreaUtils.getTeamSpawn());
        });
    }

    public int getFinalTime() {
        return this.finalTime;
    }

    public void setFinalTime(int finalTime) {
        this.finalTime = finalTime;
    }


    public void addTeamBase(TeamAreaUtils teamUtils) {
        if (this.teamsBases.contains(teamUtils)) {
            return;
        }
        this.teamsBases.add(teamUtils);
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public WaitingManager getWaitingManager() {
        return this.manager;
    }

    public void loadChunks(Location location) {
        int x = location.getBlockX();
        int z = location.getBlockZ();
        int xChunk = Math.floorDiv(x, 16);
        int zChunk = Math.floorDiv(z, 16);
        for (int dx = 0; dx < 4; dx++) {
            for (int dz = 0; dz < 4; dz++) {
                Bukkit.getWorld("world").getChunkAt(xChunk + dx, zChunk + dz).load(true);
            }
        }
    }

    public StatsManager getStatsManager() {
        return statsManager;
    }

    public GenerateMain getGenerationManager() {
        return generationManager;
    }

    public boolean hasPlayerInventory(UUID playerUUID) {
        return this.playerInventories.containsKey(playerUUID);
    }

    public void addPlayerInventory(UUID playerUUID, Inventory inventory) {
        this.playerInventories.put(playerUUID, inventory);
    }

    public void removePlayerInventory(UUID playerUUID) {
        if (hasPlayerInventory(playerUUID)) {
            this.playerInventories.remove(playerUUID);
        }
    }

    public Inventory getPlayerInventory(UUID playerUUID) {
        return this.playerInventories.get(playerUUID);
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }


}