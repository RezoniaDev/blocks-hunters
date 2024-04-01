package fr.mrtayai.blocks.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.classes.CountMap;
import fr.mrtayai.blocks.classes.StatsPlayer;
import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.utils.JsonToFile;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MusicInstrumentMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class StatsManager {

    private HashMap<BlockPlayer, StatsPlayer> statsPlayers = new HashMap<>();

    private Game game;

    public StatsManager(Game game){
        this.game = game;
    }

    public void addStatsPlayer(StatsPlayer player){
        if(this.statsPlayers.containsKey(player.getBlockPlayer())){
            return;
        }
        this.statsPlayers.put(player.getBlockPlayer(), player);
    }

    public void removeStatsPlayer(BlockPlayer player){
        this.statsPlayers.remove(player);
    }

    public StatsPlayer getStatsPlayer(BlockPlayer player){
        return this.statsPlayers.get(player);
    }

    public JsonObject getGameJSON(){
        int timer = this.game.getFinalTime();
        LocalDateTime myDateObj = LocalDateTime.now(ZoneId.of("Europe/Paris"));
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy@HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        JsonObject gameObject = new JsonObject();
        gameObject.addProperty("datetime", formattedDate);
        gameObject.addProperty("time_played", timer);
        JsonObject teams = new JsonObject();
        JsonObject players = new JsonObject();
        for(Team team : this.game.getTeamManager().getTeams()){
            teams.add(team.getName(), this.getTeamJSON(team));
        }
        for(BlockPlayer player : this.game.getPlayerManager().getPlayers()){
            players.add(player.getPlayerUUID().toString(), this.getPlayerJSON(player));
        }
        JsonArray itemsToCollect = this.getItemToCollect();
        gameObject.add("teams", teams);
        gameObject.add("players", players);
        gameObject.add("itemsToCollect", itemsToCollect);
        return gameObject;
    }

    public JsonObject getTeamJSON(Team team){
        JsonObject teamObject = new JsonObject();
        teamObject.addProperty("percent", team.getPercent());
        JsonArray players = new JsonArray();
        for(BlockPlayer player : team.getPlayers()){
            players.add(player.getPlayerUUID().toString());
        }
        JsonArray blocksCollected = new JsonArray();
        for(ItemStack items : team.getItemsCollected()){
           blocksCollected.add(this.getString(items));
        }
        teamObject.add("players", players);
        teamObject.add("blocksCollected", blocksCollected);
        return teamObject;
    }

    private JsonArray getItemToCollect(){
        JsonArray itemsToCollect = new JsonArray();
        for(ItemStack item : this.game.getMain().getItems()){
            itemsToCollect.add(this.getString(item));
        }
        return itemsToCollect;
    }

    private JsonObject getPlayerJSON(BlockPlayer player){
        JsonObject playerObject = new JsonObject();
        StatsPlayer statsPlayer = this.game.getStatsManager().getStatsPlayer(player);
//        // statsPlayer.collectStatistics();
        playerObject.addProperty("team", this.game.getTeamManager().getTeamPlayer(player).getName());
        JsonObject collected = new JsonObject();
        collected.addProperty("number", player.getItemsCollected().size());
        JsonArray blocksCollected = new JsonArray();
        for(ItemStack items : player.getItemsCollected()){
            blocksCollected.add(this.getString(items));
        }
        collected.add("blocks", blocksCollected);
        JsonObject blocksPlaced = this.listItems(statsPlayer.getElementsPlaced());
        JsonObject blocksCrafted = this.listItems(statsPlayer.getElementsCrafted());
        JsonObject blocksBreaked = this.listItems(statsPlayer.getElementsBreaked());
        JsonObject blocksPickup = this.listItems(statsPlayer.getElementsPickup());
        playerObject.add("blocksPlaced", blocksPlaced);
        playerObject.add("blocksCrafted", blocksCrafted);
        playerObject.add("blocksBreaked", blocksBreaked);
        playerObject.add("blocksPickup", blocksPickup);
//        JsonArray stats = new JsonArray();
//        Map<String, Integer> statsMap = statsPlayer.getStats();
//        for(String element : statsMap.keySet()){
//            Integer value = statsMap.get(element);
//            JsonObject object = new JsonObject();
//            collected.addProperty(element, value);
//            stats.add(object);
//        }
        return playerObject;
    }

    private JsonObject listItems(CountMap<ItemStack> itemsList){
        JsonObject json = new JsonObject();
        for(ItemStack item : itemsList.getMap().keySet()){
            int nbItems = itemsList.getCount(item);
            json.addProperty(item.getType().toString(), nbItems);
        }
        return json;
    }

    private String getString(ItemStack item){
        if(item.getType() == Material.POTION || item.getType() == Material.SPLASH_POTION || item.getType() == Material.LINGERING_POTION || item.getType() == Material.TIPPED_ARROW){
            return getStringOfPotions(item);
        } else if((this.game.getMain().getConfig().getBoolean("goat_horn.specific")) && (item.getType() == Material.GOAT_HORN)){
            return getStringOfGoat(item);
        } else {
            return item.getType().toString();
        }
    }

    private String getStringOfPotions(ItemStack item){
        StringBuilder builder = new StringBuilder(item.getType().toString());
        builder.append(" | ");
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        PotionData potionData = meta.getBasePotionData();
        builder.append("Effect : ");
        builder.append(potionData.getType().toString());
        builder.append(" | Extended : ");
        builder.append(potionData.isExtended());
        builder.append(" | Upgraded : ");
        builder.append(potionData.isUpgraded());
        return builder.toString();
    }

    private String getStringOfGoat(ItemStack item){
        StringBuilder builder = new StringBuilder(item.getType().toString());
        builder.append(" | Type of Goat Horn : ");
        MusicInstrumentMeta instrumentMeta = (MusicInstrumentMeta) item.getItemMeta();
        builder.append(instrumentMeta.getInstrument().toString());
        return builder.toString();
    }

    public void writeJSON(){
        File gamesLogFolder = new File(this.game.getMain().getDataFolder(), "games");
        if(!gamesLogFolder.exists()) { gamesLogFolder.mkdir();}
        JsonObject gameJSON = this.getGameJSON();
        String datetime = gameJSON.get("datetime").toString().split("\"")[1];
        File gameFile = new File(gamesLogFolder, "game_"+datetime+".json");
        new JsonToFile(gameJSON, gameFile.toString());
    }

}
