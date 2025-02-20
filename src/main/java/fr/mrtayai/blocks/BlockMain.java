package fr.mrtayai.blocks;

import fr.mrtayai.blocks.commands.*;
import fr.mrtayai.blocks.listeners.ItemProvider;
import fr.mrtayai.blocks.manager.Game;
import fr.mrtayai.blocks.recipes.EkaliaRecipes;
import fr.mrtayai.blocks.utils.LobbyAreaUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.popcraft.chunky.api.ChunkyAPI;

import java.io.*;
import java.util.List;

public final class BlockMain extends JavaPlugin {

    private ItemProvider gui;

    private Game game;

    private LobbyAreaUtils lobby;

    private List<ItemStack> items;

    private ChunkyAPI chunkyAPI;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Le plugin Blocks est en train de chargé");
        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) dataFolder.mkdir();

        saveDefaultConfig();

        File itemFile = new File(dataFolder, "items.txt");
        if (!itemFile.exists()) {
            try {
                InputStream inputStream = getClassLoader().getResourceAsStream("items_list.txt");
                OutputStream outputStream = new FileOutputStream(itemFile);
                byte[] buffer = new byte[2048];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                outputStream.close();
                getLogger().info("Le fichier items.txt a été créé avec les items par défaut.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        this.gui = new ItemProvider(this, itemFile);

        this.items = this.gui.getItemsList();

        this.chunkyAPI = Bukkit.getServer().getServicesManager().load(ChunkyAPI.class);

        this.game = new Game(this);
        this.game.setLobby(this.lobby);
        this.game.start();

        if(getConfig().getBoolean("ekalia.recipes")) {
            EkaliaRecipes recipes = new EkaliaRecipes(this.game);
            this.game.setRecipe(recipes);
        }

        getCommand("t").setExecutor(new TeamTextCommand(this.game));
        getCommand("team").setExecutor(new TeamCommand(this.game));
        getCommand("start").setExecutor(new StartCommand(this.game));
        getCommand("base").setExecutor(new BaseCommand(this.game));
        getCommand("farm").setExecutor(new FarmCommand(this.game));
        getCommand("status").setExecutor(new StatusCommand(this.game));
        getCommand("coords").setExecutor(new CoordsCommand(this.game));
        getCommand("join").setExecutor(new JoinCommand(this.game));
        getCommand("pause").setExecutor(new PauseCommand(this.game));
        getCommand("unpause").setExecutor(new UnpauseCommand(this.game));
        getCommand("stop").setExecutor(new StopCommand(this.game));
        getCommand("stopserver").setExecutor(new StopServerCommand());

    }

    @Override
    public void onLoad(){
        //deleteDirectory(new File(Bukkit.getWorldContainer(), "world"));
        //deleteDirectory(new File(Bukkit.getWorldContainer(), "world_nether"));
        //deleteDirectory(new File(Bukkit.getWorldContainer(), "world_the_end"));
    }

    @Override
    public void onDisable() {

        Bukkit.getLogger().info("Le plugin Blocks est déchargé.");
    }

    public ItemProvider getGUI(){
        return this.gui;
    }

    public List<ItemStack> getItems(){
        return this.items;
    }

    public boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    public ChunkyAPI getChunkyAPI() {
        return chunkyAPI;
    }

    public void setLobby(LobbyAreaUtils lobby){
        this.lobby = lobby;
    }


}
