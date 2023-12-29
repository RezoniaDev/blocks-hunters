package fr.mrtayai.blocks.commands;

import fr.mrtayai.blocks.BlockMain;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {

    private BlockMain untitled;

    public TestCommand (BlockMain untitled){
        this.untitled = untitled;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            untitled.getGUI().showFirstInventory(player);
        }else{
            commandSender.sendMessage(Component.text("TG CONNARD"));
        }
        return true;
    }
}
