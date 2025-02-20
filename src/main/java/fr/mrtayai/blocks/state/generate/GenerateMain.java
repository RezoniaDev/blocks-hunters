package fr.mrtayai.blocks.state.generate;

import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.manager.Game;
import fr.mrtayai.blocks.state.waiting.WaitingManager;
import fr.mrtayai.blocks.utils.LobbyAreaUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.popcraft.chunky.api.ChunkyAPI;

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

    private void startListener(){
        this.listener = new GenerateListeners(this.game);
        Bukkit.getServer().getPluginManager().registerEvents(listener, this.game.getMain());
        ChunkyAPI chunky = this.game.getMain().getChunkyAPI();
        chunky.startTask("world", "square1", 0, 0, 5000, 5000, "pattern");
        chunky.onGenerationComplete(eventV -> this.stopGenerate());
    }

    private void stopListener(){
        HandlerList.unregisterAll(this.listener);
    }

    public void stopGenerate(){
        this.stopListener();
        this.game.setPhase(GamePhase.WAITING);
        WaitingManager manager = new WaitingManager(this.game);
        this.game.setWaitingManager(manager);
    }
}