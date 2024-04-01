package fr.mrtayai.blocks.commands;

import fr.mrtayai.blocks.manager.Game;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PauseCommand implements CommandExecutor {

    private Game game;

    public PauseCommand(Game game){
        this.game = game;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player player){
            if(player.hasPermission("blocks.pause")){
                if(!this.game.isPaused()) {
                    this.game.setPause(true);
                    player.sendMessage(Component.text("[Blocks] Vous avez mis le jeu en pause !"));
                    return true;
                }else{
                    player.sendMessage(Component.text("[Blocks] Le jeu est déjà en pause !").color(NamedTextColor.RED));
                    return true;
                }
            }
        }else{
            commandSender.sendMessage(Component.text("Status de la partie:", NamedTextColor.GREEN));
            commandSender.sendMessage(Component.text("Phase: " + this.game.getPhase().toString(), NamedTextColor.GREEN));
            return true;
        }
        return true;
    }

}
