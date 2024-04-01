package fr.mrtayai.blocks.commands;

import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.manager.Game;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class JoinCommand implements CommandExecutor {

    private Game game;

    public JoinCommand(Game game){
        this.game = game;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player){
            return true;
        }else{
            if(strings.length != 2){
                commandSender.sendMessage(Component.text("[Blocks] /join <username> <teamName>"));
                return true;
            }
            Team team = this.game.getTeamManager().getTeam(strings[1]);
            System.out.println(Arrays.toString(strings));
            if(team == null){
                commandSender.sendMessage(Component.text("[Blocks] /join <username> <teamName>"));
                return true;
            }
            this.game.addPlayerInGame(strings[0], team);
            commandSender.sendMessage(Component.text("[Blocks] "+strings[0]+" a ete bien ajoute a l'equipe "+strings[1]));
            return true;
        }
    }
}
