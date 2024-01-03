package fr.mrtayai.blocks.commands;

import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.manager.Game;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StartCommand implements CommandExecutor {

    private Game game;

    public StartCommand(Game game){
        this.game = game;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(this.game.getPhase().equals(GamePhase.WAITING)) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                if (player.hasPermission("blocks.start")) {
                    this.game.forceStart();
                    player.getPlayer().sendMessage(Component.text("[Blocks] Début de la partie !"));
                    return true;
                }
            }else{
                this.game.forceStart();
                commandSender.sendMessage(Component.text("[Blocks] Début de la partie !"));
                return true;
            }
        }
        return true;
    }

}
