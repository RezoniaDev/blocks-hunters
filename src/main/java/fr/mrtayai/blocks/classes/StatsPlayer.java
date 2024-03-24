package fr.mrtayai.blocks.classes;

import org.bukkit.Statistic;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class StatsPlayer {

    private BlockPlayer player;

    private Map<String, Integer> stats = new HashMap<String, Integer>();

    private CountMap<ItemStack> elementsPlaced = new CountMap<>();

    private CountMap<ItemStack> elementsBreaked = new CountMap<>();

    private CountMap<ItemStack> elementsPickup = new CountMap<>();

    private CountMap<ItemStack> elementsCrafted = new CountMap<>();

    public StatsPlayer(BlockPlayer player){
        this.player = player;
    }

    public void collectStatistics(){
        stats.put("blocks_collected", this.player.getItemsCollected().size());
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
        stats.put("deaths", this.player.getPlayer().getStatistic(Statistic.DEATHS));
        stats.put("interaction_with_dispenser", this.player.getPlayer().getStatistic(Statistic.DISPENSER_INSPECTED));
        stats.put("items_dropped", this.player.getPlayer().getStatistic(Statistic.DROP));
        stats.put("interaction_with_dropper", this.player.getPlayer().getStatistic(Statistic.DROPPER_INSPECTED));
        stats.put("interaction_with_enderchest", this.player.getPlayer().getStatistic(Statistic.ENDERCHEST_OPENED));
        stats.put("entities_killed_by_player", this.player.getPlayer().getStatistic(Statistic.ENTITY_KILLED_BY));
        stats.put("distance_falled", this.player.getPlayer().getStatistic(Statistic.FALL_ONE_CM));
        stats.put("fishes_captured", this.player.getPlayer().getStatistic(Statistic.FISH_CAUGHT));
        stats.put("flowers_potted", this.player.getPlayer().getStatistic(Statistic.FLOWER_POTTED));
        stats.put("distance_flied", this.player.getPlayer().getStatistic(Statistic.FLY_ONE_CM));
        stats.put("interaction_with_furnace", this.player.getPlayer().getStatistic(Statistic.FURNACE_INTERACTION));
        stats.put("interaction_with_hopper", this.player.getPlayer().getStatistic(Statistic.HOPPER_INSPECTED));
        stats.put("distance_with_horse", this.player.getPlayer().getStatistic(Statistic.HORSE_ONE_CM));
        stats.put("interaction_with_anvil", this.player.getPlayer().getStatistic(Statistic.INTERACT_WITH_ANVIL));
        stats.put("interaction_with_blast_furnace", this.player.getPlayer().getStatistic(Statistic.INTERACT_WITH_BLAST_FURNACE));
        stats.put("interaction_with_campfire", this.player.getPlayer().getStatistic(Statistic.INTERACT_WITH_CAMPFIRE));
        stats.put("interaction_with_carotgraphy_table", this.player.getPlayer().getStatistic(Statistic.INTERACT_WITH_CARTOGRAPHY_TABLE));
        stats.put("interaction_with_grindstone", this.player.getPlayer().getStatistic(Statistic.INTERACT_WITH_GRINDSTONE));
        stats.put("interaction_with_lectern", this.player.getPlayer().getStatistic(Statistic.INTERACT_WITH_LECTERN));
        stats.put("interaction_with_loom", this.player.getPlayer().getStatistic(Statistic.INTERACT_WITH_LOOM));
        stats.put("interaction_with_smithing_table", this.player.getPlayer().getStatistic(Statistic.INTERACT_WITH_SMITHING_TABLE));
        stats.put("interaction_with_smoker", this.player.getPlayer().getStatistic(Statistic.INTERACT_WITH_SMOKER));
        stats.put("interaction_with_stonecutter", this.player.getPlayer().getStatistic(Statistic.INTERACT_WITH_STONECUTTER));
        stats.put("items_enchanted", this.player.getPlayer().getStatistic(Statistic.ITEM_ENCHANTED));
        stats.put("jump", this.player.getPlayer().getStatistic(Statistic.JUMP));
        stats.put("leaves_game", this.player.getPlayer().getStatistic(Statistic.LEAVE_GAME));
        stats.put("blocks_mined", this.player.getPlayer().getStatistic(Statistic.MINE_BLOCK));
        stats.put("distance_minecart", this.player.getPlayer().getStatistic(Statistic.MINECART_ONE_CM));
        stats.put("mob_kills", this.player.getPlayer().getStatistic(Statistic.MOB_KILLS));
        stats.put("notes_played", this.player.getPlayer().getStatistic(Statistic.NOTEBLOCK_PLAYED));
        stats.put("notes_tuned", this.player.getPlayer().getStatistic(Statistic.NOTEBLOCK_TUNED));
        stats.put("barrels_opened", this.player.getPlayer().getStatistic(Statistic.OPEN_BARREL));
        stats.put("items_pickup", this.player.getPlayer().getStatistic(Statistic.PICKUP));
        stats.put("distance_on_pig", this.player.getPlayer().getStatistic(Statistic.PIG_ONE_CM));
        stats.put("duration_played", this.player.getPlayer().getStatistic(Statistic.PLAY_ONE_MINUTE));
        stats.put("kills_of_players", this.player.getPlayer().getStatistic(Statistic.PLAYER_KILLS));
        stats.put("number_of_raids", this.player.getPlayer().getStatistic(Statistic.RAID_TRIGGER));
        stats.put("number_of_raids_winned", this.player.getPlayer().getStatistic(Statistic.RAID_WIN));
        stats.put("disks_played", this.player.getPlayer().getStatistic(Statistic.RECORD_PLAYED));
        stats.put("shulker_box_opened", this.player.getPlayer().getStatistic(Statistic.SHULKER_BOX_OPENED));
        stats.put("number_of_sleeps", this.player.getPlayer().getStatistic(Statistic.SLEEP_IN_BED));
        stats.put("time_sneaked", this.player.getPlayer().getStatistic(Statistic.SNEAK_TIME));
        stats.put("distance_sprinted", this.player.getPlayer().getStatistic(Statistic.SPRINT_ONE_CM));
        stats.put("distance_with_strider", this.player.getPlayer().getStatistic(Statistic.STRIDER_ONE_CM));
        stats.put("distance_swimed", this.player.getPlayer().getStatistic(Statistic.SWIM_ONE_CM));
        stats.put("interaction_with_villagers", this.player.getPlayer().getStatistic(Statistic.TALKED_TO_VILLAGER));
        stats.put("hits_on_target", this.player.getPlayer().getStatistic(Statistic.TARGET_HIT));
        stats.put("time_until_last_death", this.player.getPlayer().getStatistic(Statistic.TIME_SINCE_DEATH));
        stats.put("time_until_last_rest", this.player.getPlayer().getStatistic(Statistic.TIME_SINCE_REST));
        stats.put("time_gamed", this.player.getPlayer().getStatistic(Statistic.TOTAL_WORLD_TIME));
        stats.put("trades_with_villagers", this.player.getPlayer().getStatistic(Statistic.TRADED_WITH_VILLAGER));
        stats.put("interaction_with_trapped_chest", this.player.getPlayer().getStatistic(Statistic.TRAPPED_CHEST_TRIGGERED));
        stats.put("distance_frost_boots", this.player.getPlayer().getStatistic(Statistic.WALK_ON_WATER_ONE_CM));
        stats.put("distance_walked", this.player.getPlayer().getStatistic(Statistic.WALK_ONE_CM));
        stats.put("distance_under_water", this.player.getPlayer().getStatistic(Statistic.WALK_UNDER_WATER_ONE_CM));
    }


    public void addElementPlaced(ItemStack element){
        this.elementsPlaced.add(element);
    }

    public void addElementBreaked(ItemStack element){
        this.elementsBreaked.add(element);
    }

    public void addElementPickup(ItemStack element){
        this.elementsPickup.add(element);
    }

    public void addElementCrafted(ItemStack element){
        this.elementsCrafted.add(element);
    }

    public CountMap<ItemStack> getElementsBreaked() {
        return elementsBreaked;
    }

    public CountMap<ItemStack> getElementsCrafted() {
        return elementsCrafted;
    }

    public CountMap<ItemStack> getElementsPickup() {
        return elementsPickup;
    }

    public CountMap<ItemStack> getElementsPlaced() {
        return elementsPlaced;
    }

    public BlockPlayer getBlockPlayer(){
        return this.player;
    }
}

