package fr.mrtayai.blocks.commands;

import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.manager.Game;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class FarmCommand implements CommandExecutor {

    private Game game;

    public FarmCommand(Game game){
        this.game = game;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player){
            BlockPlayer player = this.game.getPlayerManager().getBlockPlayer((Player) commandSender);
            Team team = this.game.getTeamManager().getTeamPlayer(player);
            if(team == null){
                Bukkit.getLogger().log(java.util.logging.Level.SEVERE, "The player is not in a team");
            }
            if(this.game.getTeamBase(player).getArea().isInArea(player.getPlayer().getLocation())) {
                player.getPlayer().teleport(player.getPreviousLocation());
                this.game.loadChunks(player.getPlayer().getLocation());
                player.getPlayer().removePotionEffect(PotionEffectType.SATURATION);
                player.getPlayer().sendMessage(Component.text("[Blocks] Téléportation au farm"));
                return true;
            }else{
                player.getPlayer().sendMessage(Component.text("[Blocks] T'es déjà dans le farm"));
                return true;
            }
        }
        return true;
    }
}
