package fr.mrtayai.blocks.manager;

import fr.mrtayai.blocks.classes.BlockPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerManager {

    private HashMap<UUID, BlockPlayer> players;
    private Game game;

    public PlayerManager(Game game){
        this.game = game;
        this.players = new HashMap<>();
    }


    public void addPlayer(Player player){
        BlockPlayer blockPlayer = new BlockPlayer(player);
        this.players.put(player.getUniqueId(), blockPlayer);
    }

    public BlockPlayer getBlockPlayer(Player player){
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


    public void addItemCollected(UUID uuid, ItemStack item){
        this.getBlockPlayer(uuid).addItemCollected(item);
    }

    public void changeLastLocation(BlockPlayer player, Location previousLocation){
        for(BlockPlayer player1 : this.players.values()){
            if(player1.getPlayerUUID().equals(player.getPlayerUUID())){
                player1.setPreviousLocation(previousLocation);
            }
        }
    }

    public int getPlayerNumber() {
        return this.players.size();
    }

    public List<BlockPlayer> getPlayers() {
        List<BlockPlayer> players = new ArrayList<>();
        for(BlockPlayer player : this.players.values()){
            players.add(player);
        }
        return players;
    }
}
