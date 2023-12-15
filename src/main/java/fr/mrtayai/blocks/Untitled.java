package fr.mrtayai.blocks;

import fr.mrtayai.blocks.commands.TestCommand;
import fr.mrtayai.blocks.commands.TestCommand2;
import fr.mrtayai.blocks.commands.TestCommand3;
import fr.mrtayai.blocks.listeners.GUIListeners;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public final class Untitled extends JavaPlugin {

    private GUIListeners gui;


    @Override
    public void onEnable(){
        System.out.println("Bonjour bitches");

        this.gui = new GUIListeners(this);
        getServer().getPluginManager().registerEvents(this.gui, this);
        System.out.println("Inventaire crée bitches");
        getCommand("test").setExecutor(new TestCommand(this));
        getCommand("test2").setExecutor(new TestCommand2());
        getCommand("test3").setExecutor(new TestCommand3());
    }

    @Override
    public void onDisable() {
        System.out.println("Au revoir bitches");
    }

    public GUIListeners getGUI(){
        return this.gui;
    }


}
