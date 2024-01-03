package fr.mrtayai.blocks;

import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.commands.*;
import fr.mrtayai.blocks.listeners.GUIListeners;
import fr.mrtayai.blocks.manager.Game;
import fr.mrtayai.blocks.manager.PlayerManager;
import fr.mrtayai.blocks.manager.TeamManager;
import fr.mrtayai.blocks.state.generate.GenerateListeners;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class BlockMain extends JavaPlugin {

    private GUIListeners gui;

    private Game game;

    private List<ItemStack> items;

    @Override
    public void onEnable(){
        System.out.println("Bonjour bitches");

        this.gui = new GUIListeners(this);

        this.items = this.gui.getItemsList();


        this.game = new Game(this);
        this.game.start();

        getCommand("t").setExecutor(new TeamTextCommand(this.game));
        getCommand("team").setExecutor(new TeamCommand(this.game));
        getCommand("start").setExecutor(new StartCommand(this.game));
        getCommand("base").setExecutor(new BaseCommand(this.game));
        getCommand("farm").setExecutor(new FarmCommand(this.game));
        getCommand("status").setExecutor(new StatusCommand(this.game));


    }

    @Override
    public void onDisable() {
        System.out.println("Au revoir bitches");
    }

    public GUIListeners getGUI(){
        return this.gui;
    }

    public List<ItemStack> getItems(){
        return this.items;
    }
}
