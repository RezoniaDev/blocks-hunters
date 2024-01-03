package fr.mrtayai.blocks.commands;

import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.manager.Game;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LobbyCommand implements CommandExecutor {

    private Game game;

    public LobbyCommand(Game game){
        this.game = game;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            if(player.hasPermission("blocks.lobby")){
                if(this.game.getPhase().equals(GamePhase.WAITING)){
                    player.teleport(this.game.getLobby().getLobbySpawnLoc());
                    return true;
                }
            }
        }
        return  true;
    }
}
