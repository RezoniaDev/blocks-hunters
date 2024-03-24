package fr.mrtayai.blocks.classes;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BlockPlayer {

    private Player player;

    private Location previousLocation;

    private List<ItemStack> itemsCollected;

    public BlockPlayer(Player player){
        this.player = player;
        this.itemsCollected = new ArrayList<>();
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


    public List<ItemStack> getItemsCollected() {
        return this.itemsCollected;
    }

    public void addItemCollected(ItemStack item){
        this.itemsCollected.add(item);
    }

    public Location getPreviousLocation(){
        return this.previousLocation;
    }

    public void setPreviousLocation(Location previousLocation){
        this.previousLocation = previousLocation;
    }
}
