package fr.mrtayai.blocks.state.waiting;

import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.classes.StatsPlayer;
import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.manager.Game;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class WaitingListener implements Listener {

    private Game game;

    public WaitingListener(Game game){
        this.game = game;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if(this.game.getPhase().equals(GamePhase.WAITING)) {
            this.game.getPlayerManager().addPlayer(event.getPlayer());
            StatsPlayer statsPlayer = new StatsPlayer(this.game.getPlayerManager().getBlockPlayer(event.getPlayer()));
            this.game.getStatsManager().addStatsPlayer(statsPlayer);
            event.getPlayer().teleport(this.game.getLobby().getLobbySpawnLoc());
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
            this.game.getScoreboardManager().addBoard(event.getPlayer());
            event.getPlayer().getInventory().clear();
            event.getPlayer().setFoodLevel(20);
            event.getPlayer().getInventory().setItem(4, this.game.getWaitingManager().getTeamChooser());
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event){
        if(event.getEntity() instanceof Player){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        if(this.game.getPhase().equals(GamePhase.WAITING)){
            this.game.getStatsManager().removeStatsPlayer(this.game.getPlayerManager().getBlockPlayer(event.getPlayer()));
            this.game.getPlayerManager().removePlayer(event.getPlayer());
            event.getPlayer().getInventory().clear();
            event.getPlayer().clearActivePotionEffects();
            this.game.getScoreboardManager().removeBoard(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(this.game.getPhase().equals(GamePhase.WAITING)) {
            event.setCancelled(true);
            if(event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR){
                return;
            }
            if(event.getItem() == null){
                return;
            }
            if(event.getItem().equals(this.game.getWaitingManager().getTeamChooser())){
                event.getPlayer().openInventory(this.game.getWaitingManager().getChooseInventory());
            }
        }
    }

    @EventHandler
    public void onInventoryInteract(InventoryClickEvent event){
        if(!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        event.setCancelled(true);
        Team team = null;
        if(clickedItem == null) return;
        try {
            switch(clickedItem.getType()){
                case YELLOW_WOOL:
                    team = this.game.getTeamManager().getTeam("yellow");
                    this.game.getTeamManager().addPlayerToTeam(this.game.getPlayerManager().getBlockPlayer(player), team);

                    break;
                case GREEN_WOOL:
                    team = this.game.getTeamManager().getTeam("green");
                    this.game.getTeamManager().addPlayerToTeam(this.game.getPlayerManager().getBlockPlayer(player), team);
                    break;
                case RED_WOOL:
                    team = this.game.getTeamManager().getTeam("red");
                    this.game.getTeamManager().addPlayerToTeam(this.game.getPlayerManager().getBlockPlayer(player), team);
                    break;
                case BLUE_WOOL:
                    team = this.game.getTeamManager().getTeam("blue");
                    this.game.getTeamManager().addPlayerToTeam(this.game.getPlayerManager().getBlockPlayer(player), team);
                    break;
                default:
                    return;
            }
        } catch (Exception e) {
            Bukkit.getLogger().info(e.getMessage());
        }
        Component message = Component.text("[Blocks] Vous avez rejoint l'équipe ")
                .append(Component.text(team.getDisplayName()).color(team.getTextColor()))
                .append(Component.text(" !"));
        player.sendMessage(message);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equals("Sélection des équipes")){
            return;
        }

        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();
        if(clickedItem == null || clickedItem.getType().isAir()){
            return;
        }

        Player player = (Player) event.getWhoClicked();
        BlockPlayer blockPlayer = this.game.getPlayerManager().getBlockPlayer(player);
        String teamName = "";
        switch(clickedItem.getType()) {
            case YELLOW_WOOL:
                teamName = "yellow";
                break;
            case GREEN_WOOL:
                teamName = "green";
                break;
            case RED_WOOL:
                teamName = "red";
                break;
            case BLUE_WOOL:
                teamName = "blue";
                break;
            default:
                teamName = "";
                break;
        }
        try {
            this.game.getTeamManager().addPlayerToTeam(blockPlayer, teamName);
        } catch (Exception e) {
            player.sendMessage(Component.text(e.toString()));
        }

    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event){
        if(this.game.getPhase().equals(GamePhase.WAITING)){
            if(event.getDamager() instanceof Player){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        if(this.game.getPhase().equals(GamePhase.WAITING)){
            event.setKeepInventory(true);
            event.getPlayer().teleport(this.game.getLobby().getLobbySpawnLoc());
        }
    }


}
