package fr.mrtayai.blocks.state.waiting;

import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.manager.Game;
import io.papermc.paper.event.block.BlockBreakBlockEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class WaitingListener implements Listener {

    private Game game;

    public WaitingListener(Game game){
        this.game = game;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        this.game.getPlayerManager().addPlayer(event.getPlayer());
        event.getPlayer().teleport(this.game.getLobby().getLobbySpawnLoc());
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

}
