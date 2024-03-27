package fr.mrtayai.blocks.classes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BlockPlayer {

    private UUID playerUUID;

    private Location previousLocation;

    private List<ItemStack> itemsCollected;

    public BlockPlayer(Player player){
        this.playerUUID = player.getUniqueId();
        this.itemsCollected = new ArrayList<>();
        this.previousLocation = null;
    }

    public Player getPlayer() {
        return Bukkit.getServer().getPlayer(this.playerUUID);
    }

    public UUID getPlayerUUID(){
        return this.playerUUID;
    }

    public void setPlayer(Player player) {
        this.playerUUID = player.getUniqueId();
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
