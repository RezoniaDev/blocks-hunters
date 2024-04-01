package fr.mrtayai.blocks.state.playing;

import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.classes.StatsPlayer;
import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.gui.TeamInventory;
import fr.mrtayai.blocks.manager.Game;
import fr.mrtayai.blocks.structures.Base;
import fr.mrtayai.blocks.utils.TeamAreaUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class GameListener implements Listener {

    private Game game;

    public GameListener(Game game){
        this.game = game;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        BlockPlayer blockPlayer = this.game.getPlayerManager().getBlockPlayer(event.getPlayer());
        this.game.addPlayerInventory(event.getPlayer().getUniqueId(), event.getPlayer().getInventory());
        if(blockPlayer == null){
            return;
        }
        Team team = this.game.getTeamManager().getTeamPlayer(blockPlayer);
        if(team == null){
            return;
        }
        this.game.getScoreboardManager().removeBoard(event.getPlayer());

    }

    @EventHandler
    public void onItemPickUp(EntityPickupItemEvent event){
        if(event.getItem().getItemStack().getType() == Material.AIR){
            return;
        }
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            BlockPlayer blockPlayer = this.game.getPlayerManager().getBlockPlayer(player);
            this.game.getStatsManager().getStatsPlayer(blockPlayer).addElementPickup(event.getItem().getItemStack());
        }
    }

    @EventHandler
    public void onBlockBreak(org.bukkit.event.block.BlockBreakEvent event) {
        Player player = event.getPlayer();
        BlockPlayer blockPlayer = this.game.getPlayerManager().getBlockPlayer(player);
        if (this.game.getPhase().equals(GamePhase.GAME)) {
            if(this.game.isPaused()){
                player.sendMessage(Component.text("[Blocks] Le jeu a été en pause !"));
                event.setCancelled(true);
            }
            if(event.getBlock().getType() == Material.AIR){
                return;
            }
            if (this.game.getTeamBase(blockPlayer).getArea().isInArea(player.getLocation())) {
                switch(event.getBlock().getType()){
                    case ACACIA_SIGN, BAMBOO_SIGN, BIRCH_SIGN, CHERRY_SIGN, CRIMSON_SIGN, DARK_OAK_SIGN, JUNGLE_SIGN,  MANGROVE_SIGN, OAK_SIGN, SPRUCE_SIGN, WARPED_SIGN, ACACIA_WALL_SIGN, BAMBOO_WALL_SIGN, BIRCH_WALL_SIGN, CHERRY_WALL_SIGN, CRIMSON_WALL_SIGN, DARK_OAK_WALL_SIGN, JUNGLE_WALL_SIGN, MANGROVE_WALL_SIGN, OAK_WALL_SIGN, SPRUCE_WALL_SIGN, WARPED_WALL_SIGN, BOOKSHELF, CHISELED_BOOKSHELF, ENCHANTING_TABLE, TORCH, WALL_TORCH -> {
                        this.game.getStatsManager().getStatsPlayer(blockPlayer).addElementBreaked(new ItemStack(event.getBlock().getType()));
                        return;
                    }
                    default -> {
                        event.setCancelled(true);
                        return;
                    }
                }
            }else{
                ItemStack item = new ItemStack(event.getBlock().getType());
                this.game.getStatsManager().getStatsPlayer(blockPlayer).addElementBreaked(item);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if(this.game.getPhase().equals(GamePhase.GAME)) {
            if (e.getWhoClicked() instanceof Player) {
                Player player = (Player) e.getWhoClicked();
                Team team = this.game.getTeamManager().getTeamPlayer(this.game.getPlayerManager().getBlockPlayer(player));
                TeamInventory teamInventory = this.game.getTeamManager().getTeamInventory(team);
                if(e.getClickedInventory() == null) return;
                if(teamInventory.containsInventory(e.getClickedInventory())){
                    e.setCancelled(true);
                    if(e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()){
                        return;
                    }
                    if(e.getCurrentItem().equals(teamInventory.getItemSuivant())) {
                        teamInventory.openNextPage(player.getUniqueId());
                    }else if(e.getCurrentItem().equals(teamInventory.getItemPrecedent())){
                        teamInventory.openPreviousPage(player.getUniqueId());
                    }else{
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onAnvilDamage(BlockDamageEvent event){
        Location blockLocation = event.getBlock().getLocation();
        for(TeamAreaUtils team : this.game.getTeamsBases()){
            if(team.getArea().isInArea(blockLocation)){
                if(event.getBlock().getType().equals(Material.ANVIL)){
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onAnvilUse(PrepareAnvilEvent event){
        List<ItemStack> items = Arrays.stream(event.getInventory().getContents()).toList();
        if(this.isAMusicDisc(items.get(0).getType())){
            event.setResult(new ItemStack(Material.DISC_FRAGMENT_5, 8));
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        if(this.game.getPlayerManager().getBlockPlayer(event.getPlayer()) != null){
            event.setKeepInventory(true);
            event.getDrops().clear();
            event.setKeepLevel(true);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        if(this.game.getPhase().equals(GamePhase.GAME)){
            event.setRespawnLocation(this.game.randomTeleport());
            this.game.getPlayerManager().getBlockPlayer(event.getPlayer()).setPreviousLocation(event.getPlayer().getLocation());
        }
    }

    @EventHandler
    public void onInventoryQuit(final InventoryCloseEvent e){
        if(this.game.getPhase().equals(GamePhase.GAME)) {
            if (e.getPlayer() instanceof Player) {
                Player player = (Player) e.getPlayer();
                Team team = this.game.getTeamManager().getTeamPlayer(this.game.getPlayerManager().getBlockPlayer(player));
                TeamInventory teamInventory = this.game.getTeamManager().getTeamInventory(team);
                if(e.getReason().equals(InventoryCloseEvent.Reason.PLUGIN)){
                    return;
                }
                if(teamInventory.containsInventory(e.getInventory())){
                    teamInventory.closeInventory(player.getUniqueId());
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if(this.game.getPhase().equals(GamePhase.GAME)){
            if(this.game.getPlayerManager().getBlockPlayer(event.getPlayer()) == null){
                if(this.game.getPlayerToAdd().contains(event.getPlayer().getName())){
                    this.game.getPlayerManager().addPlayer(event.getPlayer());
                    BlockPlayer player = this.game.getPlayerManager().getBlockPlayer(event.getPlayer());
                    this.game.getStatsManager().addStatsPlayer(new StatsPlayer(player));
                    Team team = this.game.getTeamByWaitingPlayer(player.getPlayer().getName());
                    player.getPlayer().teleport(this.game.randomTeleport());
                    try {
                        this.game.getTeamManager().addPlayerToTeam(player, team);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    this.game.getScoreboardManager().addBoard(player.getPlayer());
                    event.getPlayer().displayName(Component.text(event.getPlayer().getName()).color(team.getTextColor()));
                }else {
                    event.getPlayer().kick(Component.text("La partie a déjà commencé !"));
                }
                return;
            }
            event.getPlayer().getInventory().setContents(this.game.getPlayerInventory(event.getPlayer().getUniqueId()).getContents());
            this.game.removePlayerInventory(event.getPlayer().getUniqueId());
            this.game.getScoreboardManager().addBoard(event.getPlayer());
            BlockPlayer blockPlayer = this.game.getPlayerManager().getBlockPlayer(event.getPlayer());
            event.getPlayer().teleport(blockPlayer.getPreviousLocation());
            Team team = this.game.getTeamManager().getTeamPlayer(blockPlayer);
            event.getPlayer().displayName(Component.text(event.getPlayer().getName()).color(team.getTextColor()));
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        if(this.game.isPaused()){
            event.getPlayer().sendMessage(Component.text("[Blocks] Le jeu a été mis en pause !"));
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onItemCraft(CraftItemEvent event){
        if(event.getCurrentItem().getType() == Material.AIR){
            return;
        }
        Player player = (Player) event.getWhoClicked();
        BlockPlayer blockPlayer = this.game.getPlayerManager().getBlockPlayer(player);
        this.game.getStatsManager().getStatsPlayer(blockPlayer).addElementCrafted(event.getCurrentItem());
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event){
        if(game.isPaused() && this.game.getPhase().equals(GamePhase.GAME)){
            event.setCancelled(true);
            event.getPlayer().sendMessage(Component.text("[Blocks] Le jeu a été mis en pause !"));
        }
    }

    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event){
        if(this.game.getPhase().equals(GamePhase.GAME)){
            if(game.isPaused()){
                event.setCancelled(true);
                event.getPlayer().sendMessage(Component.text("[Blocks] Le jeu a été mis en pause !"));
            }
            Player player = event.getPlayer();
            BlockPlayer blockPlayer = this.game.getPlayerManager().getBlockPlayer(player);
            ItemStack itemStack = new ItemStack(event.getBlockPlaced().getType());
            this.game.getStatsManager().getStatsPlayer(blockPlayer).addElementPlaced(itemStack);
            TeamAreaUtils utils = this.game.getTeamBase(this.game.getPlayerManager().getBlockPlayer(player));
            if(utils.getArea().isInArea(player.getLocation())){
                switch(event.getBlockPlaced().getType()){
                    case ACACIA_SIGN, BAMBOO_SIGN, BIRCH_SIGN, CHERRY_SIGN, CRIMSON_SIGN, DARK_OAK_SIGN, JUNGLE_SIGN,  MANGROVE_SIGN, OAK_SIGN, SPRUCE_SIGN, WARPED_SIGN, TORCH, WALL_TORCH, BOOKSHELF, CHISELED_BOOKSHELF, ENCHANTING_TABLE, ACACIA_WALL_SIGN, BAMBOO_WALL_SIGN, BIRCH_WALL_SIGN, CHERRY_WALL_SIGN, CRIMSON_WALL_SIGN, DARK_OAK_WALL_SIGN, JUNGLE_WALL_SIGN, MANGROVE_WALL_SIGN, OAK_WALL_SIGN, SPRUCE_WALL_SIGN, WARPED_WALL_SIGN -> {
                        this.game.getStatsManager().getStatsPlayer(blockPlayer).addElementBreaked(new ItemStack(event.getBlock().getType()));
                        return;
                    }

                    default -> {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player){
            Player player1 = (Player) event.getDamager();
            BlockPlayer player = this.game.getPlayerManager().getBlockPlayer(player1);
            if(event.getEntity() instanceof Player){
                event.setCancelled(true);
            }
        }
        if(event.getEntity() instanceof Player){
            if(this.game.isPaused()){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerFood(FoodLevelChangeEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            TeamAreaUtils teamAreaUtils = this.game.getTeamBase(this.game.getPlayerManager().getBlockPlayer(player));
            if(teamAreaUtils.getArea().isInArea(player.getLocation())){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event){
        if(event.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)){
            if(!this.game.getMain().getConfig().getBoolean("dimension.nether")){
                event.getPlayer().sendMessage(Component.text("[Blocks] Le nether a été désactivé !").color(NamedTextColor.RED));
                event.setCancelled(true);
                event.setCanCreatePortal(false);
            }
        }
        if(event.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)){
            if(!this.game.getMain().getConfig().getBoolean("dimension.end")){
                event.getPlayer().sendMessage(Component.text("[Blocks] L'end a été désactivé !").color(NamedTextColor.RED));
                event.setCancelled(true);
                event.setCanCreatePortal(false);
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
        if(this.game.isPaused()){
            event.setCancelled(true);
        }
        BlockPlayer blockPlayer = this.game.getPlayerManager().getBlockPlayer(event.getPlayer());
        Team team = this.game.getTeamManager().getTeamPlayer(blockPlayer);
        Base baseTeam = this.game.getTeamBase(blockPlayer).getBase();
        if(event.getRightClicked() instanceof Villager){
            Villager villager = (Villager) event.getRightClicked();
            if(baseTeam.getGivenVillagerID().equals(villager.getUniqueId())) {
                Player player = event.getPlayer();
                ItemStack itemMainHand = player.getInventory().getItemInMainHand();
                ItemStack itemPlugin = this.clearFishBuckets(itemMainHand);
                if (itemMainHand.getType().isAir()) {
                    return;
                }
                if (this.game.getTeamManager().isCollected(itemPlugin, team)) {
                    player.sendMessage(Component.text("[Blocks] Cet item a déjà été ajouté !"));
                } else if (!this.game.getTeamManager().getTeam(team.getTeamID()).getItemsToCollect().contains(itemPlugin)) {
                    return;
                }else {
                    this.game.getTeamManager().setCollected(itemPlugin, team);
                    this.game.getPlayerManager().addItemCollected(player.getUniqueId(), itemPlugin);
                    if(itemMainHand.getAmount() > 1){
                        int amount = itemMainHand.getAmount();
                        player.getInventory().getItemInMainHand().setAmount(amount-1);
                    }else {
                        player.getInventory().getItemInMainHand().setAmount(0);
                    }
                    player.sendMessage(Component.text("[Blocks] Cet item a bien été ajouté !"));
                }
            }else if(baseTeam.getListVillagerID().equals(villager.getUniqueId())){
                this.game.getTeamManager().getTeamInventory(team).startInventory(blockPlayer.getPlayerUUID());
            } else {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Component.text("[Blocks] Les échanges des villageois sont désactivés.").color(NamedTextColor.RED));
            }
        }
    }

    private ItemStack clearFishBuckets(ItemStack item){
        switch(item.getType()){
            case PUFFERFISH_BUCKET -> {
                return new ItemStack(Material.PUFFERFISH_BUCKET, 1);
            }
            case TADPOLE_BUCKET -> {
                return new ItemStack(Material.TADPOLE_BUCKET,1);
            }
            case AXOLOTL_BUCKET -> {
                return new ItemStack(Material.AXOLOTL_BUCKET, 1);
            }
            case COD_BUCKET -> {
                return new ItemStack(Material.COD_BUCKET, 1);
            }
            case SALMON_BUCKET -> {
                return new ItemStack(Material.SALMON_BUCKET, 1);
            }
            case TROPICAL_FISH_BUCKET -> {
                return new ItemStack(Material.TROPICAL_FISH_BUCKET, 1);
            }
            case ENCHANTED_BOOK -> {
                return new ItemStack(Material.ENCHANTED_BOOK, 1);
            }
            default -> {
                ItemStack itemStack = new ItemStack(item);
                itemStack.setAmount(1);
                return itemStack;
            }
        }
    }

    private boolean isAMusicDisc(Material material){
        List<String> strings = List.of(
                "MUSIC_DISC_11",
                "MUSIC_DISC_13",
                "MUSIC_DISC_5",
                "MUSIC_DISC_BLOCKS",
                "MUSIC_DISC_CAT",
                "MUSIC_DISC_CHIRP",
                "MUSIC_DISC_FAR",
                "MUSIC_DISC_MALL",
                "MUSIC_DISC_MELLOHI",
                "MUSIC_DISC_OTHERSIDE",
                "MUSIC_DISC_PIGSTEP",
                "MUSIC_DISC_RELIC",
                "MUSIC_DISC_STAL",
                "MUSIC_DISC_STRAD",
                "MUSIC_DISC_WAIT",
                "MUSIC_DISC_WARD");
        return strings.contains(material.toString());
    }
}
