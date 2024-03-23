package fr.mrtayai.blocks.state.generate;

import fr.mrtayai.blocks.BlockMain;
import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.manager.Game;
import fr.mrtayai.blocks.state.waiting.WaitingManager;
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
        this.world = this.game.getMain().getServer().getWorld("world");
        startListener();
    }

    public void startGenerate(){
        Lobby lobby = new Lobby(0, 300, 0, this.world);
        lobby.build();
        this.lobbyUtils = new LobbyAreaUtils(lobby);

        stopListener();
        this.game.setLobby(this.lobbyUtils);
        this.game.setPhase(GamePhase.WAITING);
        WaitingManager manager = new WaitingManager(this.game);
        this.game.setWaitingManager(manager);
    }

    private void startListener(){
        this.listener = new GenerateListeners(this.game);
        Bukkit.getServer().getPluginManager().registerEvents(listener, this.game.getMain());
    }

    private void stopListener(){
        HandlerList.unregisterAll(this.listener);
    }
}
