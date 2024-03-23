package fr.mrtayai.blocks.classes;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class StatsPlayer {

    private BlockPlayer player;

    private Map<String, Integer> stats = new HashMap<String, Integer>();

    public StatsPlayer(BlockPlayer player){
        this.player = player;
    }

    public void collectStatistics(){
        stats.put("blocks_collected", this.player.getBlocksGiven());
        stats.put("animal_bred", this.player.getPlayer().getStatistic(Statistic.ANIMALS_BRED));
        stats.put("armor_cleaned", this.player.getPlayer().getStatistic(Statistic.ARMOR_CLEANED));
        stats.put("distance_elytra", this.player.getPlayer().getStatistic(Statistic.AVIATE_ONE_CM));
        stats.put("banner_cleaned", this.player.getPlayer().getStatistic(Statistic.BANNER_CLEANED));
        stats.put("interaction_with_beacon", this.player.getPlayer().getStatistic(Statistic.BEACON_INTERACTION));
        stats.put("interaction_with_bell", this.player.getPlayer().getStatistic(Statistic.BELL_RING));
        stats.put("distance_boat", this.player.getPlayer().getStatistic(Statistic.BOAT_ONE_CM));
        stats.put("item_breaks", this.player.getPlayer().getStatistic(Statistic.BREAK_ITEM));
        stats.put("interaction_with_brewing_stand", this.player.getPlayer().getStatistic(Statistic.BREWINGSTAND_INTERACTION));
        stats.put("cake_parts_eaten", this.player.getPlayer().getStatistic(Statistic.CAKE_SLICES_EATEN));
        stats.put("cauldron_filled", this.player.getPlayer().getStatistic(Statistic.CAULDRON_FILLED));
        stats.put("cauldron_used", this.player.getPlayer().getStatistic(Statistic.CAULDRON_USED));
        stats.put("chest_opened", this.player.getPlayer().getStatistic(Statistic.CHEST_OPENED));
        stats.put("clean_shulker_box", this.player.getPlayer().getStatistic(Statistic.CLEAN_SHULKER_BOX));
        stats.put("distance_climbed", this.player.getPlayer().getStatistic(Statistic.CLIMB_ONE_CM));
        stats.put("items_crafted", this.player.getPlayer().getStatistic(Statistic.CRAFT_ITEM));
        stats.put("interaction_with_crafting_table", this.player.getPlayer().getStatistic(Statistic.CRAFTING_TABLE_INTERACTION));
        stats.put("distance_sneaking", this.player.getPlayer().getStatistic(Statistic.CROUCH_ONE_CM));
        stats.put("damage_absorbed", this.player.getPlayer().getStatistic(Statistic.DAMAGE_ABSORBED));
        stats.put("damage_blocked_by_shield", this.player.getPlayer().getStatistic(Statistic.DAMAGE_BLOCKED_BY_SHIELD));
        stats.put("damage_emitted", this.player.getPlayer().getStatistic(Statistic.DAMAGE_DEALT));
        stats.put("damage_emitted_and_blocked_armor", this.player.getPlayer().getStatistic(Statistic.DAMAGE_DEALT_ABSORBED));
        stats.put("damage_emitted_and_resisted", this.player.getPlayer().getStatistic(Statistic.DAMAGE_DEALT_RESISTED));
        stats.put("damage_absorbed", this.player.getPlayer().getStatistic(Statistic.DAMAGE_ABSORBED));
        stats.put("damage_absorbed", this.player.getPlayer().getStatistic(Statistic.DAMAGE_ABSORBED));
        stats.put("damage_taken", this.player.getPlayer().getStatistic(Statistic.DAMAGE_TAKEN));
    }

}
