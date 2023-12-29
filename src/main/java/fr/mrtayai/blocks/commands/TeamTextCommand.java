package fr.mrtayai.blocks.commands;

import fr.mrtayai.blocks.BlockMain;
import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.classes.Team;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeamTextCommand implements CommandExecutor {

    private BlockMain main;

    public TeamTextCommand(BlockMain main){
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(this.main.getGamePhase().equals(GamePhase.GAME)){
            if(commandSender instanceof Player){
                Player player = (Player) commandSender;
                if(strings.length > 0){
                    String message = "";
                    for(String string : strings){
                        message += string + " ";
                    }
                    Team team = this.main.getTeamManager().getTeamPlayer(this.main.getPlayerManager().getBlockPlayer(player));
                    if(team != null){
                        team.sendTeamMessage(Component.text(message, NamedTextColor.WHITE));
                    }else{
                        player.sendMessage(Component.text("Erreur :", NamedTextColor.RED).append(Component.text(" Vous n'êtes pas dans une équipe", NamedTextColor.WHITE)));
                    }
                    return true;
                } else {
                    player.sendMessage(Component.text("Erreur :", NamedTextColor.RED).append(Component.text(" Vous devez spécifier un message", NamedTextColor.WHITE)));
                    return true;
                }
            } else {
                commandSender.sendMessage(Component.text("Erreur :", NamedTextColor.RED).append(Component.text(" Vous devez être un joueur pour utiliser cette commande", NamedTextColor.WHITE)));
                return true;
            }
        } else {
            commandSender.sendMessage(Component.text("Erreur :", NamedTextColor.RED).append(Component.text(" Vous devez être en jeu pour utiliser cette commande", NamedTextColor.WHITE)));
            return true;
        }
    }
}
