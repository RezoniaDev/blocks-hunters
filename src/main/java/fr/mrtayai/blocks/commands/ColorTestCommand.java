package fr.mrtayai.blocks.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ColorTestCommand implements CommandExecutor{


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Component message = Component.text("Liste des couleurs : \n");
        if(commandSender instanceof Player){
            for(String key : NamedTextColor.NAMES.keys()) {
                NamedTextColor color = NamedTextColor.NAMES.value(key);
                message = message.append(Component.text(key + " : Ceci est une phrase française \n", color));
            }
            commandSender.sendMessage(message);
            return true;
        } else {
            commandSender.sendMessage("TG CONNARD");
            return true;
        }
    }
}
