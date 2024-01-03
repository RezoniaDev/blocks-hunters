package fr.mrtayai.blocks.state.playing;

import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.manager.Game;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class GameListener implements Listener {

    private Game game;

    public GameListener(Game game){
        this.game = game;
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
        BlockPlayer blockPlayer = this.game.getPlayerManager().getBlockPlayer(event.getPlayer());
        if(event.getRightClicked() instanceof Villager){
            if(this.game.isVillager(event.getRightClicked().getUniqueId())){
                Villager villager = (Villager) event.getRightClicked();
                if(Objects.equals(villager.customName(), Component.text("Don des items"))) {
                    Player player = event.getPlayer();
                    ItemStack itemMainHand = player.getInventory().getItemInMainHand();
                    if (itemMainHand.isEmpty() || itemMainHand.getType().isAir()) {
                        return;
                    }
                    Team team = this.game.getTeamManager().getTeamPlayer(blockPlayer);
                    if (this.game.getTeamManager().isCollected(itemMainHand, team)) {
                        player.sendMessage(Component.text("[Blocks] Cet item a déjà été ajouté !"));
                    } else {
                        this.game.getTeamManager().setCollected(itemMainHand, team);
                        this.game.getPlayerManager().incrementBlock(player.getUniqueId());
                        player.sendMessage(Component.text("[Blocks] Cet item a bien été ajouté !"));
                    }
                }else{
                    this.game.getTeamManager().openTeamInventory(blockPlayer);
                }
            }
        }
    }


}
