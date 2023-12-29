package fr.mrtayai.blocks.manager;

import fr.mrtayai.blocks.BlockMain;
import fr.mrtayai.blocks.classes.BlockPlayer;
import org.bukkit.entity.Player;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {

    private HashMap<UUID, BlockPlayer> players;
    private BlockMain main;

    public PlayerManager(BlockMain main){
        this.main = main;
        this.players = new HashMap<>();
    }


    public void addPlayer(Player player){
        BlockPlayer blockPlayer = new BlockPlayer(player);
        this.players.put(player.getUniqueId(), blockPlayer);
    }

    public BlockPlayer getBlockPlayer(Player player){
        this.main.getLogger().info("Player : " + player.getName() + "\n");
        this.main.getLogger().info("Player UUID : " + player.getUniqueId() + "\n");
        this.main.getLogger().info("Players : " + this.players + "\n");
        return this.players.get(player.getUniqueId());
    }


    public BlockPlayer getBlockPlayer(UUID uuid){
        return this.players.get(uuid);
    }

    public BlockPlayer getBlockPlayer(String playerName){
        for(BlockPlayer player : this.players.values()){
            if(player.getPlayer().getName().equals(playerName)){
                return player;
            }
        }
        return null;
    }

    public BlockPlayer removePlayer(UUID uuid){
        return this.players.remove(uuid);
    }


    public BlockPlayer removePlayer(Player player) {
        return this.players.remove(player.getUniqueId());
    }
}
