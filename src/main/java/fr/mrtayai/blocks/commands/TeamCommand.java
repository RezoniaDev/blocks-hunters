package fr.mrtayai.blocks.commands;

import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.manager.Game;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;

public class TeamCommand implements CommandExecutor {

    private final Game game;



    /*
    Permissions :
    "blocks.team.*"
    "blocks.team.list"
    "blocks.team.join"
    "blocks.team.create"
    "blocks.team.add"
    "blocks.team.quit"
    "blocks.team.remove"
     */

    public TeamCommand(Game game){
        this.game = game;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            BlockPlayer blockPlayer = this.game.getPlayerManager().getBlockPlayer(player);
            StringBuilder arg = new StringBuilder();
            for(String s : args){
                arg.append(s+"@");
            }
            if(args.length == 0){
                sender.sendMessage(getHelpMessage(blockPlayer, false));
                return true;
            }
            if (Objects.equals(args[0], "list")) {
                if( (!player.hasPermission("blocks.team.list")) || (!player.hasPermission("blocks.team.*"))) {
                    player.sendMessage(Component.text("[Blocks] Vous n'avez pas la permission pour cette commande !", NamedTextColor.RED));
                    return true;
                }
                Component message = Component.text("=========[Blocks]=========", NamedTextColor.RED);
                message = message.append(Component.newline());
                message = message.append(Component.text("Liste des équipes : \n"));
                if(this.game.getTeamManager().getTeams().isEmpty()){
                    message = message.append(Component.text("Aucune équipe n'a été créée.", NamedTextColor.RED));
                }else {
                    for (Team team : this.game.getTeamManager().getTeams()) {
                        message = message.append(Component.text(String.format("- Nom : %s | Couleur : %s\n", team.getName(), team.getColor().toString())));
                    }
                }
                player.sendMessage(message);
                return true;
            } else if(Objects.equals(args[0], "join")) {
                if((!player.hasPermission("blocks.team.join") || (!player.hasPermission("blocks.team.*")))){
                    player.sendMessage(Component.text("[Blocks] Vous n'avez pas la permission pour cette commande !", NamedTextColor.RED));
                    return true;
                }
                if(args.length != 2){
                    sender.sendMessage(getHelpMessage(blockPlayer, false));
                    return true;
                }
                String teamName = args[1].toLowerCase(Locale.FRENCH);
                try {
                    this.game.getTeamManager().addPlayerToTeam(blockPlayer, teamName);
                    player.sendMessage(Component.text(String.format("[Blocks] Vous avez bien rejoint l'équipe %s", teamName)));
                    return true;
                } catch (Exception e) {
                    Bukkit.getLogger().log(Level.SEVERE, e.getMessage());
                    return true;
                }
            } else if(Objects.equals(args[0], "add")){
                if((!player.hasPermission("blocks.team.add") || (!player.hasPermission("blocks.team.*")))){
                    player.sendMessage(Component.text("[Blocks] Vous n'avez pas la permission pour cette commande !", NamedTextColor.RED));
                    return true;
                }
                if(args.length != 3){
                    sender.sendMessage(getHelpMessage(blockPlayer, false));
                    return true;
                }
                String playerName = args[1];
                BlockPlayer blockPlayerTarget = this.game.getPlayerManager().getBlockPlayer(playerName);
                String teamName = args[2].toLowerCase(Locale.FRENCH);
                try {
                    this.game.getTeamManager().addPlayerToTeam(blockPlayerTarget, teamName);
                    player.sendMessage("[Blocks] Le joueur %s a bien été ajouté à l'équipe %s", playerName, teamName);
                    return true;
                } catch (Exception e) {
                    Bukkit.getLogger().log(Level.SEVERE, e.getMessage());
                    return true;
                }
            } else if(Objects.equals(args[0], "quit")) {
                if (!(player.hasPermission("blocks.team.quit")) || !(player.hasPermission("blocks.team.*"))) {
                    player.sendMessage(Component.text("[Blocks] Vous n'avez pas la permission !"));
                    return true;
                }
                if (args.length != 1) {
                    sender.sendMessage(getHelpMessage(blockPlayer, false));
                    return true;
                }
                try {
                    this.game.getTeamManager().removePlayerToTeam(blockPlayer);
                    player.sendMessage(Component.text("[Blocks] Vous avez bien quitté votre équipe !"));
                    return true;
                } catch (Exception e) {
                    player.sendMessage(Component.text(String.format("[Blocks] %s", e)));
                    return true;
                }
            } else if(Objects.equals(args[0], "remove")) {
                    if((!player.hasPermission("blocks.team.remove") || (!player.hasPermission("blocks.team.*")))){
                        player.sendMessage(Component.text("[Blocks] Vous n'avez pas la permission pour cette commande !", NamedTextColor.RED));
                        return true;
                    }
                    if(args.length != 3){
                        sender.sendMessage(getHelpMessage(blockPlayer, false));
                        return true;
                    }
                    String playerName = args[1];
                    BlockPlayer blockPlayerTarget = this.game.getPlayerManager().getBlockPlayer(playerName);
                    try {
                        this.game.getTeamManager().removePlayerToTeam(blockPlayerTarget);
                        player.sendMessage("[Blocks] Le joueur %s a bien été retiré de son équipe.", playerName);
                        return true;
                    } catch (Exception e) {
                        sender.sendMessage(Component.text(String.format("[Blocks] %s", e), NamedTextColor.RED));
                        return true;
                    }
            } else if(Objects.equals(args[0], "delete")) {
                if((!player.hasPermission("blocks.team.delete") || (!player.hasPermission("blocks.team.*")))){
                    player.sendMessage(Component.text("[Blocks] Vous n'avez pas la permission pour cette commande !", NamedTextColor.RED));
                    return true;
                }
                if(args.length != 2){
                    sender.sendMessage(getHelpMessage(blockPlayer, false));
                    return true;
                }
                String teamName = args[1].toLowerCase(Locale.FRENCH);
                try {
                    this.game.getTeamManager().removeTeam(teamName);
                    player.sendMessage(Component.text(String.format("[Blocks] L'équipe %s a bien été supprimée !", teamName)));
                    return true;
                } catch (Exception e) {
                    player.sendMessage(Component.text(String.format("[Blocks] %s", e), NamedTextColor.RED));
                    return true;
                }
            }else if(Objects.equals(args[0], "create")) {
                if((!player.hasPermission("blocks.team.create") || (!player.hasPermission("blocks.team.*")))){
                    player.sendMessage(Component.text("[Blocks] Vous n'avez pas la permission pour cette commande !", NamedTextColor.RED));
                    return true;
                }
                if(args.length != 3){
                    sender.sendMessage(getHelpMessage(blockPlayer, false));
                    return true;
                }
                String teamName = args[1].toLowerCase(Locale.FRENCH);
                Color color = getColorFromString(args[2].toLowerCase(Locale.FRENCH));
                Team team = new Team(teamName, teamName, color);
                try {
                    this.game.getTeamManager().addTeam(team);
                    player.sendMessage(Component.text("[Blocks] L'équipe" +team.getDisplayName() +"a bien été créée !"));
                    return true;
                } catch (Exception e) {
                    player.sendMessage(Component.text("[Blocks] " + e.toString(), NamedTextColor.RED));
                    return true;
                }
            }else if(Objects.equals(args[0], "info")) {
                if(args.length != 2){
                    sender.sendMessage(getHelpMessage(null, false));
                    return true;
                }
                String playerName = args[1].toLowerCase(Locale.FRENCH);
                BlockPlayer blockPlayer1 = this.game.getPlayerManager().getBlockPlayer(playerName);
                Team team = this.game.getTeamManager().getTeamPlayer(blockPlayer1);
                sender.sendMessage(Component.text(team.getDisplayName()));
            }

            sender.sendMessage(getHelpMessage(blockPlayer, false));
        }else{
            if(args.length == 0){
                sender.sendMessage(getHelpMessage(null, true));
                return true;
            }
            if (Objects.equals(args[0], "list")) {
                Component message = Component.text("=========[Blocks]=========", NamedTextColor.RED);
                message = message.append(Component.newline());
                message = message.append(Component.text("Liste des équipes : \n"));
                for (Team team : this.game.getTeamManager().getTeams()) {
                    message = message.append(Component.text("- Nom : "+team.getDisplayName()+" | Couleur : "+ team.getTextColor().toString()+ "\n"));
                }
                sender.sendMessage(message);
                return true;
            } else if (Objects.equals(args[0], "add")) {
                if (args.length != 3) {
                    sender.sendMessage(getHelpMessage(null, true));
                    return true;
                }
                String playerName = args[1];
                BlockPlayer blockPlayerTarget = this.game.getPlayerManager().getBlockPlayer(playerName);
                String teamName = args[2].toLowerCase(Locale.FRENCH);
                try {
                    this.game.getTeamManager().addPlayerToTeam(blockPlayerTarget, teamName);
                    sender.sendMessage("[Blocks] Le joueur "+playerName +" a bien été ajouté à l'équipe "+teamName);
                    return true;
                } catch (Exception e) {
                    sender.sendMessage(Component.text(String.format("[Blocks] %s", e), NamedTextColor.RED));
                    return true;
                }
            } else if (Objects.equals(args[0], "remove")) {
                if (args.length != 2) {
                    sender.sendMessage(getHelpMessage(null, true));
                    return true;
                }
                String playerName = args[1];
                BlockPlayer blockPlayerTarget = this.game.getPlayerManager().getBlockPlayer(playerName);
                try {
                    this.game.getTeamManager().removePlayerToTeam(blockPlayerTarget);
                    sender.sendMessage("[Blocks] Le joueur %s a bien été retiré de son équipe.", playerName);
                    return true;
                } catch (Exception e) {
                    sender.sendMessage(Component.text(String.format("[Blocks] %s", e), NamedTextColor.RED));
                    return true;
                }
            } else if (Objects.equals(args[0], "delete")) {
                if (args.length != 2) {
                    sender.sendMessage(getHelpMessage(null, true));
                    return true;
                }
                String teamName = args[1].toLowerCase(Locale.FRENCH);
                try {
                    this.game.getTeamManager().removeTeam(teamName);
                    sender.sendMessage(Component.text(String.format("[Blocks] L'équipe %s a bien été supprimée !", teamName)));
                    return true;
                } catch (Exception e) {
                    sender.sendMessage(Component.text(String.format("[Blocks] %s", e), NamedTextColor.RED));
                    return true;
                }
            } else if (Objects.equals(args[0], "create")) {
                if (args.length != 3) {
                    sender.sendMessage(getHelpMessage(null, true));
                    return true;
                }
                String teamName = args[1].toLowerCase(Locale.FRENCH);
                Color color = getColorFromString(args[2].toLowerCase(Locale.FRENCH));
                Team team = new Team(teamName, teamName, color);
                try {
                    this.game.getTeamManager().addTeam(team);
                    sender.sendMessage(Component.text(String.format("[Blocks] L'équipe %s a bien été créée !", teamName)));
                    return true;
                } catch (Exception e) {
                    sender.sendMessage(Component.text(String.format("[Blocks] %s", e), NamedTextColor.RED));
                    return true;
                }
            } else if (Objects.equals(args[0], "info")) {
                if (args.length != 2) {
                    sender.sendMessage(getHelpMessage(null, true));
                    return true;
                }
                String playerName = args[1].toLowerCase(Locale.FRENCH);
                BlockPlayer player = this.game.getPlayerManager().getBlockPlayer(playerName);
                Team team = this.game.getTeamManager().getTeamPlayer(player);
                sender.sendMessage(Component.text("[Blocks] Le joueur " + playerName + " est dans l'équipe : " + team == null ? "aucune" : team.getDisplayName()));
            }
            sender.sendMessage(getHelpMessage(null, true));
        }
        return true;
    }


    private Component getHelpMessage(BlockPlayer blockPlayer, boolean isConsole){
        if(!isConsole) {
            Player player = blockPlayer.getPlayer();
            boolean hasOnePermission = false;
            Component message = Component.text("=========[Blocks]=========", NamedTextColor.RED);
            message = message.append(Component.newline());
            boolean hasAllPermission = blockPlayer.getPlayer().hasPermission("blocks.team.*");
            if (hasAllPermission || player.hasPermission("blocks.team.list")) {
                message = message.append(Component.text("/team list ", NamedTextColor.GOLD));
                message = message.append(Component.text(": Affiche la liste des équipes existantes", NamedTextColor.WHITE));
                message = message.append(Component.newline());
                hasOnePermission = true;
            }
            if (hasAllPermission || player.hasPermission("blocks.team.create")) {
                message = message.append(Component.text("/team create <nom> <couleur> ", NamedTextColor.GOLD));
                message = message.append(Component.text(": Crée une équipe en renseignant le nom et la couleur", NamedTextColor.WHITE));
                message = message.append(Component.newline());
                hasOnePermission = true;
            }
            if (hasAllPermission || player.hasPermission("blocks.team.join")) {
                message = message.append(Component.text("/team join <nom> ", NamedTextColor.GOLD));
                message = message.append(Component.text(": Permet de rejoindre l'équipe renseignée.", NamedTextColor.WHITE));
                message = message.append(Component.newline());
                hasOnePermission = true;
            }
            if (hasAllPermission || player.hasPermission("blocks.team.add")) {
                message = message.append(Component.text("/team add <joueur> <nom> ", NamedTextColor.GOLD));
                message = message.append(Component.text(": Permet d'ajouter un joueur spécifique de l'équipe renseignée.", NamedTextColor.WHITE));
                message = message.append(Component.newline());
                hasOnePermission = true;
            }
            if (hasAllPermission || player.hasPermission("blocks.team.quit")) {
                message = message.append(Component.text("/team quit", NamedTextColor.GOLD));
                message = message.append(Component.text(": Permet de quitter l'équipe d'où on est", NamedTextColor.WHITE));
                message = message.append(Component.newline());
                hasOnePermission = true;
            }
            if (hasAllPermission || player.hasPermission("blocks.team.remove")) {
                message = message.append(Component.text("/team remove <player> <nom> ", NamedTextColor.GOLD));
                message = message.append(Component.text(": Permet de retirer un joueur spécifique de l'équipe renseignée.", NamedTextColor.WHITE));
                message = message.append(Component.newline());
                hasOnePermission = true;
            }
            if (hasAllPermission || player.hasPermission("blocks.team.delete")) {
                message = message.append(Component.text("/team delete <nom> ", NamedTextColor.GOLD));
                message = message.append(Component.text(": Permet de supprimer l'équipe renseignée.", NamedTextColor.WHITE));
                message = message.append(Component.newline());
                hasOnePermission = true;
            }
            if (!hasOnePermission) {
                return Component.text("");
            } else {
                return message;
            }
        }else{
            Component message = Component.text("=========[Blocks]=========", NamedTextColor.RED);
            message = message.append(Component.newline());
            message = message.append(Component.text("/team list ", NamedTextColor.GOLD));
            message = message.append(Component.text(": Affiche la liste des équipes existantes", NamedTextColor.WHITE));
            message = message.append(Component.newline());
            message = message.append(Component.text("/team create <nom> <couleur> ", NamedTextColor.GOLD));
            message = message.append(Component.text(": Crée une équipe en renseignant le nom et la couleur", NamedTextColor.WHITE));
            message = message.append(Component.newline());
            message = message.append(Component.text("/team join <nom> ", NamedTextColor.GOLD));
            message = message.append(Component.text(": Permet de rejoindre l'équipe renseignée.", NamedTextColor.WHITE));
            message = message.append(Component.newline());
            message = message.append(Component.text("/team add <joueur> <nom> ", NamedTextColor.GOLD));
            message = message.append(Component.text(": Permet d'ajouter un joueur spécifique de l'équipe renseignée.", NamedTextColor.WHITE));
            message = message.append(Component.newline());

            message = message.append(Component.text("/team quit", NamedTextColor.GOLD));
            message = message.append(Component.text(": Permet de quitter l'équipe d'où on est", NamedTextColor.WHITE));
            message = message.append(Component.newline());

            message = message.append(Component.text("/team remove <player> <nom> ", NamedTextColor.GOLD));
            message = message.append(Component.text(": Permet de retirer un joueur spécifique de l'équipe renseignée.", NamedTextColor.WHITE));
            message = message.append(Component.newline());

            message = message.append(Component.text("/team delete <nom> ", NamedTextColor.GOLD));
            message = message.append(Component.text(": Permet de supprimer l'équipe renseignée.", NamedTextColor.WHITE));
            message = message.append(Component.newline());
            return message;

        }

    }

    private Color getColorFromString(String color){
        switch(color){
            case "aqua":
                return Color.AQUA;
            case "black":
                return Color.BLACK;
            case "blue":
                return Color.BLUE;
            case "fuchsia":
                return Color.FUCHSIA;
            case "gray":
                return Color.GRAY;
            case "green":
                return Color.GREEN;
            case "lime":
                return Color.LIME;
            case "maroon":
                return Color.MAROON;
            case "navy":
                return Color.NAVY;
            case "olive":
                return Color.OLIVE;
            case "orange":
                return Color.ORANGE;
            case "purple":
                return Color.PURPLE;
            case "red":
                return Color.RED;
            case "silver":
                return Color.SILVER;
            case "teal":
                return Color.TEAL;
            case "yellow":
                return Color.YELLOW;
            case "white":
            default:
                return Color.WHITE;
        }
    }

}
