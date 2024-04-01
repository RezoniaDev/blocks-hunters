package fr.mrtayai.blocks.commands;

import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.manager.Game;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BaseCommand implements CommandExecutor {

    private Game game;

    public BaseCommand(Game game){
        this.game = game;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(game.getPhase().equals(GamePhase.GAME)) {
            if (commandSender instanceof Player) {
                BlockPlayer player = this.game.getPlayerManager().getBlockPlayer((Player) commandSender);
                if (!this.game.getTeamBase(player).getArea().isInArea(player.getPlayer().getLocation())) {
                    this.game.getPlayerManager().changeLastLocation(player, player.getPlayer().getLocation());
                }
                player.getPlayer().teleport(this.game.getTeamBase(player).getTeamSpawn());
                player.getPlayer().setSaturation(5);
                player.getPlayer().setFoodLevel(20);
                player.getPlayer().setHealth(20);
                player.getPlayer().sendMessage(Component.text("[Blocks] Téléportation à la base"));
                return true;
            }
        }
        return true;
    }
}
