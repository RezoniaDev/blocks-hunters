package fr.mrtayai.blocks.classes;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BlockPlayer {

    private Player player;

    private Location previousLocation;

    private int blocksGiven;

    public BlockPlayer(Player player){
        this.player = player;
        this.blocksGiven = 0;
        this.previousLocation = null;
    }

    public Player getPlayer() {
        return this.player;
    }

    public UUID getPlayerUUID(){
        return this.player.getUniqueId();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public int getBlocksGiven() {
        return this.blocksGiven;
    }

    public void incrementBlocksGiven(){
        this.blocksGiven++;
    }

    public Location getPreviousLocation(){
        return this.previousLocation;
    }

    public void setPreviousLocation(Location previousLocation){
        this.previousLocation = previousLocation;
    }
}
