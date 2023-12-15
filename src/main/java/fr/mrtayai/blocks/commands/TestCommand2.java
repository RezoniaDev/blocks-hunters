package fr.mrtayai.blocks.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TestCommand2 implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            player.sendMessage(itemStack.getType().toString());
        }else{
            commandSender.sendMessage("TG");
        }

        return true;
    }
}
