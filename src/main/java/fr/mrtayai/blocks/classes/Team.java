package fr.mrtayai.blocks.classes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Team {


    private final UUID teamID = UUID.randomUUID();

    private String name;
    private Color color;

    private NamedTextColor textColor;

    private List<BlockPlayer> players;

    private List<ItemStack> itemsToCollect;
    private List<ItemStack> itemsCollected;

    private String displayName;

    public Team(String name, String displayName, Color color){
        this.name = name;
        this.color = color;
        this.convertColor(this.color);
        this.displayName = displayName;
        this.players = new ArrayList<>();
        this.itemsCollected = new ArrayList<>();
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

    public void sendTeamMessage(Component message){
        Component teamMessage = Component.text("[BlocksTeam] ", this.textColor).append(message);
        for(BlockPlayer player : this.players){
            player.getPlayer().sendMessage(teamMessage);
        }
    }

    public void setItemsToCollect(List<ItemStack> items){
        this.itemsToCollect = items;
    }

    public void setItemCollected(ItemStack item){
        if(this.itemsCollected.contains(item)){
            return;
        }
        this.itemsCollected.add(item);
    }

    public double getPercent(){
        return ((double) this.itemsCollected.size() /this.itemsToCollect.size())*100;
    }

    public List<ItemStack> getItemsCollected() {
        return this.itemsCollected;
    }

    public List<ItemStack> getItemsToCollect() {
        return this.itemsToCollect;
    }

    public UUID getTeamID() {
        return this.teamID;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Color getColor() {
        return this.color;
    }

    public NamedTextColor getTextColor() {
        return this.textColor;
    }

    private void convertColor(Color color){
        if (color.equals(Color.RED)) {
            this.textColor = NamedTextColor.RED;
        } else if (color.equals(Color.BLUE)) {
            this.textColor = NamedTextColor.BLUE;
        } else if (color.equals(Color.GREEN)) {
            this.textColor = NamedTextColor.DARK_GREEN;
        } else if (color.equals(Color.YELLOW)) {
            this.textColor = NamedTextColor.YELLOW;
        } else if (color.equals(Color.AQUA)) {
            this.textColor = NamedTextColor.AQUA;
        } else if (color.equals(Color.BLACK)) {
            this.textColor = NamedTextColor.BLACK;
        } else if (color.equals(Color.FUCHSIA)) {
            this.textColor = NamedTextColor.LIGHT_PURPLE;
        } else if (color.equals(Color.GRAY)) {
            this.textColor = NamedTextColor.GRAY;
        } else if (color.equals(Color.LIME)) {
            this.textColor = NamedTextColor.GREEN;
        } else if (color.equals(Color.MAROON)) {
            this.textColor = NamedTextColor.DARK_RED;
        } else if (color.equals(Color.NAVY)) {
            this.textColor = NamedTextColor.DARK_BLUE;
        } else if (color.equals(Color.OLIVE)) {
            this.textColor = NamedTextColor.DARK_GREEN;
        } else {
            this.textColor = NamedTextColor.WHITE;
        }
    }

}
