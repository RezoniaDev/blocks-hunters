package fr.mrtayai.blocks.state.generate;

import fr.mrtayai.blocks.BlockMain;
import fr.mrtayai.blocks.manager.Game;
import fr.mrtayai.blocks.structures.Lobby;
import fr.mrtayai.blocks.utils.LobbyAreaUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.HandlerList;

import java.io.File;

public class GenerateMain {

    private final String worldName1 = "world";
    private final String worldName2 = "world-1";
    private World world;

    private GenerateListeners listener;

    private LobbyAreaUtils lobbyUtils;

    private Game game;

    public GenerateMain(Game game){
        this.game = game;
    }

    public void startGenerate(){

        startListener();

        String str = "";
        if(Bukkit.getWorld(worldName1) != null){
            str = worldName2;
            deleteWorld(worldName1);
        }else if(Bukkit.getWorld(worldName2) != null) {
            str = worldName1;
            deleteWorld(worldName2);
        }
        WorldCreator wc = new WorldCreator(str);
        wc.environment(World.Environment.NORMAL);
        this.world =  wc.createWorld();

        Lobby lobby = new Lobby(0, 1000000, 0, this.world);
        lobby.build();
        this.lobbyUtils = new LobbyAreaUtils(new Location(this.world,0, 1000000, 0),new Location(this.world,8, 1000004, 8), new Location(this.world,4,  1000001, 4));

        stopListener();
        this.game.setLobby(this.lobbyUtils);
    }

    private void startListener(){
        this.listener = new GenerateListeners(this.game);
        Bukkit.getServer().getPluginManager().registerEvents(listener, this.game.getMain());
    }

    private void stopListener(){
        HandlerList.unregisterAll(this.listener);
    }

    private void deleteWorld(String str){
        World world1 = Bukkit.getWorld(str);

        if(world1 == null){
            return;
        }

        File worldFolder = new File(Bukkit.getServer().getWorldContainer(), str);
        deleteWorld(worldFolder);

    }

    private void deleteWorld(File worldFolder){
        if(worldFolder.exists()){
            File[] files = worldFolder.listFiles();
            if(files != null){
                for(File file : files){
                    if(file.isDirectory()) {
                        deleteWorld(file);
                    } else {
                        file.delete();
                    }
                }
            }
        }
        worldFolder.delete();
    }
}
