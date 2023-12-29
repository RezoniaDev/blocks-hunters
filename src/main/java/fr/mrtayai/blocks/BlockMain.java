package fr.mrtayai.blocks;

import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.commands.*;
import fr.mrtayai.blocks.listeners.GUIListeners;
import fr.mrtayai.blocks.listeners.PlayerJoinListener;
import fr.mrtayai.blocks.manager.PlayerManager;
import fr.mrtayai.blocks.manager.TeamManager;
import fr.mrtayai.blocks.state.generate.GenerateListeners;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class BlockMain extends JavaPlugin {

    private GUIListeners gui;
    private PlayerManager playerManager;
    private TeamManager teamManager;

    private GamePhase gamePhase;

    private List<ItemStack> items;

    @Override
    public void onEnable(){
        gamePhase = GamePhase.GENERATION;
        System.out.println("Bonjour bitches");

        this.gui = new GUIListeners(this);
        this.playerManager = new PlayerManager(this);
        this.teamManager = new TeamManager(this);

        this.items = this.gui.getItemsList();

        getServer().getPluginManager().registerEvents(this.gui, this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new GenerateListeners(this), this);

        System.out.println("Inventaire crée bitches");
        getCommand("test").setExecutor(new TestCommand(this));
        getCommand("test2").setExecutor(new TestCommand2());
        getCommand("test3").setExecutor(new TestCommand3());
        getCommand("color").setExecutor(new ColorTestCommand());
        getCommand("t").setExecutor(new TeamTextCommand(this));
        getCommand("team").setExecutor(new TeamCommand(this));
    }

    @Override
    public void onDisable() {
        System.out.println("Au revoir bitches");
    }

    public GUIListeners getGUI(){
        return this.gui;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public GamePhase getGamePhase() {
        return this.gamePhase;
    }

    public void setGamePhase(GamePhase gamePhase) {
        this.gamePhase = gamePhase;
    }

    public List<ItemStack> getItems(){
        return this.items;
    }
}
