package fr.mrtayai.blocks.commands;

import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.manager.Game;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
                    for(BlockPlayer players : this.game.getPlayerManager().getPlayers()){
                        if(this.game.getTeamManager().getTeamPlayer(players) == null){
                            player.sendMessage(Component.text("[Blocks] Tous les joueurs n'ont pas d'équipes !").color(NamedTextColor.RED));
                            return true;
                        }
                    }
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
