package fr.mrtayai.blocks.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import fr.mrtayai.blocks.manager.Game;
import org.jetbrains.annotations.NotNull;

public class StatusCommand implements CommandExecutor {

    private Game game;

    public StatusCommand(Game game){
        this.game = game;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player player){
            if(player.hasPermission("blocks.status")){
                player.sendMessage("§aStatus de la partie:");
                player.sendMessage("§aPhase: " + this.game.getPhase().toString());
                return true;
            }
        }else{
            commandSender.sendMessage(Component.text("Status de la partie:", NamedTextColor.GREEN));
            commandSender.sendMessage(Component.text("Phase: " + this.game.getPhase().toString(), NamedTextColor.GREEN));
            return true;
        }
        return true;
    }
}
