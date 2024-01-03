package fr.mrtayai.blocks.classes;

import fr.mrtayai.blocks.state.playing.ItemInventory;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

import static org.bukkit.Color.*;

public class Team {

    private String name;
    private Color color;
    private List<BlockPlayer> players;

    private List<ItemStack> itemsToCollect;
    private List<ItemStack> itemsCollected;

    public Team(String name, Color color){
        this.name = name;
        this.color = color;
        this.players = new LinkedList<>();

    }

    public String getName() {
        return this.name;
    }

    public boolean isInTheTeam(BlockPlayer player){
        return this.players.contains(player);
    }

    public void addPlayer(BlockPlayer player){
        if(this.players.contains(player)) {
            return;
        }
        this.players.add(player);
    }

    public void removePlayer(BlockPlayer player){
        if(! isInTheTeam(player)){
            return;
        }
        this.players.remove(player);
    }

    public List<BlockPlayer> getPlayers(){
        return this.players;
    }

    public Color getColor(){
        return this.color;
    }

    public void sendTeamMessage(Component message){
        Component teamMessage = Component.text("[BlocksTeam] ", getTextColor()).append(message);
        for(BlockPlayer player : this.players){
            player.getPlayer().sendMessage(teamMessage);
        }
    }

    private TextColor getTextColor(){
        return TextColor.color(this.color.asRGB());
    }

    public void setItemsToCollect(List<ItemStack> items){
        this.itemsToCollect = items;
    }

    public boolean setItemCollected(ItemStack item){
        if(itemsCollected.contains(item)){
            return false;
        }
        itemsCollected.add(item);
        return true;
    }

    public double getPercent(){
        return ((double) this.itemsCollected.size() /this.itemsToCollect.size())*100;
    }

    public List<ItemStack> getItemsCollected() {
        return itemsCollected;
    }

    public List<ItemStack> getItemsToCollect() {
        return itemsToCollect;
    }

    private void createTeamInventories(){

    }

    private void changeItemInventory(){

    }

}
