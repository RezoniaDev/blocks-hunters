package fr.mrtayai.blocks.commands;

import fr.mrtayai.blocks.manager.Game;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UnpauseCommand implements CommandExecutor {

    private Game game;

    public UnpauseCommand(Game game){
        this.game = game;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            if(player.hasPermission("blocks.unpause")){
                if(this.game.isPaused()){
                    this.game.setPause(false);
                    player.sendMessage(Component.text("[Blocks] Vous avez bien faire reprendre la partie !"));
                    return true;
                }else{
                    player.sendMessage(Component.text("[Blocks] Vous ne pouvez pas reprendre une partie pas en pause !").color(NamedTextColor.RED));
                    return true;
                }
            }
        }else{
            if(this.game.isPaused()){
                this.game.setPause(false);
                commandSender.sendMessage(Component.text("[Blocks] Vous avez bien faire reprendre la partie !"));
                return true;
            }else{
                commandSender.sendMessage(Component.text("[Blocks] Vous ne pouvez pas reprendre une partie pas en pause !").color(NamedTextColor.RED));
                return true;
            }
        }
        return true;
    }
}
