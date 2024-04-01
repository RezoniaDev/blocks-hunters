package fr.mrtayai.blocks.commands;

import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.manager.Game;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StopCommand implements CommandExecutor {

    private Game game;

    public StopCommand(Game game){
        this.game = game;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(this.game.getPhase().equals(GamePhase.GAME)){
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                if (player.hasPermission("blocks.stop")) {
                    this.game.forceEnd();
                    player.sendMessage(Component.text("[Blocks] Vous avez arreté la partie !"));
                    return true;
                }
            } else {
                this.game.forceEnd();
                commandSender.sendMessage(Component.text("[Blocks] Vous avez arreté la partie !"));
                return true;
            }
            return true;
        }else{
            commandSender.sendMessage(Component.text("[Blocks] Vous ne pouvez pas arrêter une partie si elle n'a pas commencée !").color(NamedTextColor.RED));
            return true;
        }
    }
}
