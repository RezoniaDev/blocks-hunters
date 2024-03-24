package fr.mrtayai.blocks.state.playing;

import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.gui.TeamInventory;
import fr.mrtayai.blocks.manager.Game;
import fr.mrtayai.blocks.structures.Base;
import fr.mrtayai.blocks.utils.TeamAreaUtils;
import net.kyori.adventure.text.Component;
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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class GameListener implements Listener {

    private Game game;

    public GameListener(Game game){
        this.game = game;
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
            if(event.getBlock().getType() == Material.AIR){
                return;
            }
            if (this.game.getTeamBase(blockPlayer).getArea().isInArea(player.getLocation())) {
                event.setCancelled(true);
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
    public void onPlayerDeath(PlayerDeathEvent event){
        if(this.game.getPlayerManager().getBlockPlayer(event.getPlayer()) != null){
            event.setKeepInventory(true);
            event.setKeepLevel(true);
            this.game.randomTeleport(event.getPlayer());
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
                event.getPlayer().kick(Component.text("La partie a déjà commencé !"));
            }
            this.game.getScoreboardManager().addBoard(event.getPlayer());
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
    public void onPlayerPlaceBlock(BlockPlaceEvent event){
        if(this.game.getPhase().equals(GamePhase.GAME)){
            Player player = event.getPlayer();
            BlockPlayer blockPlayer = this.game.getPlayerManager().getBlockPlayer(player);
            ItemStack itemStack = new ItemStack(event.getBlockPlaced().getType());
            this.game.getStatsManager().getStatsPlayer(blockPlayer).addElementPlaced(itemStack);
            TeamAreaUtils utils = this.game.getTeamBase(this.game.getPlayerManager().getBlockPlayer(player));
            if(utils.getArea().isInArea(player.getLocation())){
                event.setCancelled(true);
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
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
        BlockPlayer blockPlayer = this.game.getPlayerManager().getBlockPlayer(event.getPlayer());
        Team team = this.game.getTeamManager().getTeamPlayer(blockPlayer);
        Base baseTeam = this.game.getTeamBase(blockPlayer).getBase();
        if(event.getRightClicked() instanceof Villager){
            Villager villager = (Villager) event.getRightClicked();
            if(baseTeam.getGivenVillagerID().equals(villager.getUniqueId())) {
                Player player = event.getPlayer();
                ItemStack itemMainHand = player.getInventory().getItemInMainHand();
                ItemStack itemPlugin = new ItemStack(itemMainHand);
                itemPlugin.setAmount(1);
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
            }
        }
    }
}
