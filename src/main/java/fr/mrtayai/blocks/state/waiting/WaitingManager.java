package fr.mrtayai.blocks.state.waiting;

import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.gui.TeamInventory;
import fr.mrtayai.blocks.manager.Game;
import fr.mrtayai.blocks.state.playing.GameManager;
import fr.mrtayai.blocks.structures.Lobby;
import fr.mrtayai.blocks.utils.LobbyAreaUtils;
import fr.mrtayai.blocks.utils.TeamAreaUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.*;

public class WaitingManager {

    private Game game;
    private BukkitScheduler scheduler;
    private WaitingListener listener;
    private int waitingRunnable;

    private ItemStack teamChooser;

    private Inventory chooseInventory;

    public WaitingManager(Game game){
        this.game = game;

        Lobby lobby = new Lobby(0, 300, 0, Bukkit.getWorld("world"));
        lobby.build();
        this.game.loadChunks(lobby.getLocSpawn());
        LobbyAreaUtils lobbyUtils = new LobbyAreaUtils(lobby);

        this.game.setLobby(lobbyUtils);

        this.game.setWaitingManager(this);
        this.scheduler = Bukkit.getScheduler();
        this.registerListeners();
        this.launchWaitingRunnable();
        /* Création de l'équipe rouge */
        Team redTeam = new Team("red", "Rouge",Color.RED);
        redTeam.setItemsToCollect(new ArrayList<>(this.game.getMain().getItems()));
        this.game.getTeamManager().addTeamInventory(redTeam, new TeamInventory(redTeam.getTeamID(), redTeam.getItemsToCollect()));
        try {
            this.game.getTeamManager().addTeam(redTeam);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TeamAreaUtils redTeamUtils = new TeamAreaUtils(this.game.getLobby().getLobbySpawnLoc().getWorld(), 20000, 300, 20000, redTeam.getTeamID());
        this.game.addTeamBase(redTeamUtils);

        /* Création de l'équipe verte */
        Team greenTeam = new Team("green", "Vert", Color.GREEN);
        greenTeam.setItemsToCollect(new ArrayList<>(this.game.getMain().getItems()));
        this.game.getTeamManager().addTeamInventory(greenTeam, new TeamInventory(greenTeam.getTeamID(), greenTeam.getItemsToCollect()));
        try {
            this.game.getTeamManager().addTeam(greenTeam);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TeamAreaUtils greenTeamUtils = new TeamAreaUtils(this.game.getLobby().getLobbySpawnLoc().getWorld(), -20000, 300, 20000, greenTeam.getTeamID());
        this.game.addTeamBase(greenTeamUtils);

        /* Création de l'équipe bleue */
        Team blueTeam = new Team("blue", "Bleu", Color.BLUE);
        blueTeam.setItemsToCollect(new ArrayList<>(this.game.getMain().getItems()));
        this.game.getTeamManager().addTeamInventory(blueTeam, new TeamInventory(blueTeam.getTeamID(), blueTeam.getItemsToCollect()));
        try {
            this.game.getTeamManager().addTeam(blueTeam);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TeamAreaUtils blueTeamUtils = new TeamAreaUtils(this.game.getLobby().getLobbySpawnLoc().getWorld(), 20000, 300, -20000, blueTeam.getTeamID());
        this.game.addTeamBase(blueTeamUtils);

        /* Création de l'équipe jaune */
        Team yellowTeam = new Team("yellow", "Jaune", Color.YELLOW);
        yellowTeam.setItemsToCollect(new ArrayList<>(this.game.getMain().getItems()));
        this.game.getTeamManager().addTeamInventory(yellowTeam, new TeamInventory(yellowTeam.getTeamID(), yellowTeam.getItemsToCollect()));
        try {
            this.game.getTeamManager().addTeam(yellowTeam);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        TeamAreaUtils yellowTeamUtils = new TeamAreaUtils(this.game.getLobby().getLobbySpawnLoc().getWorld(), -20000, 300, -20000, yellowTeam.getTeamID());
        this.game.addTeamBase(yellowTeamUtils);
        this.teamChooser = this.createTeamChooser();
        this.chooseInventory = this.createChooseInventory();

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
        this.game.getTeamManager().removeEmptyTeams();
        this.game.prepareTeamBases();
        new GameManager(this.game).start();
        this.game.setPhase(GamePhase.GAME);

    }

    private void unregisterListeners(){
        HandlerList.unregisterAll(this.listener);
    }

    private ItemStack createTeamChooser(){
        ItemStack item = new ItemStack(Material.COMPASS);
        ItemMeta meta = item.getItemMeta();
        if(meta != null){
            meta.displayName(Component.text("Sélection des équipes"));
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack getTeamChooser() {
        return teamChooser;
    }

    public Inventory getChooseInventory() {
        return chooseInventory;
    }

    private Inventory createChooseInventory(){
        Inventory inv = Bukkit.createInventory(null, 9, "Sélection des équipes");
        inv.setItem(2, this.getTeamWool("yellow"));
        inv.setItem(3, this.getTeamWool("green"));
        inv.setItem(4, this.getTeamWool("red"));
        inv.setItem(5, this.getTeamWool("blue"));
        return inv;
    }

    private ItemStack getTeamWool(String teamName){
        ItemStack item = null;
        switch(teamName){
            case "yellow":
                item = new ItemStack(Material.YELLOW_WOOL);
                break;
            case "green":
                item = new ItemStack(Material.GREEN_WOOL);
                break;
            case "red":
                item = new ItemStack(Material.RED_WOOL);
                break;
            case "blue":
                item = new ItemStack(Material.BLUE_WOOL);
                break;
        }
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("Rejoindre l'équipe " + teamName));
        return item;
    }

}
