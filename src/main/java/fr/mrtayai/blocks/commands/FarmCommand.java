package fr.mrtayai.blocks.commands;

import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.manager.Game;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
            if(this.game.getTeamBase(player).getArea().isInArea(player.getPlayer().getLocation())) {
                player.getPlayer().teleport(player.getPreviousLocation());
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
