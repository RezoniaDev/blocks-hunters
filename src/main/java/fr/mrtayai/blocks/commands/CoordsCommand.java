package fr.mrtayai.blocks.commands;

import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.manager.Game;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CoordsCommand implements CommandExecutor {

    private Game game;

    public CoordsCommand(Game game){
        this.game= game;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player){
            Player sender = (Player) commandSender;
            if(this.game.getPhase().equals(GamePhase.GAME)){
                Team team = this.game.getTeamManager().getTeamPlayer(this.game.getPlayerManager().getBlockPlayer(sender));
                for(BlockPlayer player : team.getPlayers()){
                    player.getPlayer().sendMessage(Component.text("[BlocksTeam]").color(team.getTextColor())
                            .append(Component.text(" " + sender.getName() + " est aux coordonées ("+sender.getLocation().getBlockX()+", " + sender.getLocation().getBlockY() + ", " + sender.getLocation().getBlockZ()+")")));
                }
            }
        }else{
            commandSender.sendMessage(Component.text("[Blocks] Vous ne pouvez pas envoyer vos coordonnées, vous êtes la console !").color(NamedTextColor.RED));
        }
        return true;
    }
}
