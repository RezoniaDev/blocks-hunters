package fr.mrtayai.blocks.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.jetbrains.annotations.NotNull;

public class TestCommand2 implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            StringBuilder strBuild = new StringBuilder();
            strBuild.append(String.format("Type de l'item : %s \n", itemStack.getType().toString()));
            if(itemStack.getType() == Material.POTION || itemStack.getType() == Material.SPLASH_POTION || itemStack.getType() == Material.LINGERING_POTION || itemStack.getType() == Material.TIPPED_ARROW){
                PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
                PotionData data = potionMeta.getBasePotionData();
                strBuild.append(String.format("Type de potion : %s \n", data.getType().toString()));
                strBuild.append(String.format("La potion est-elle niveau II ? : %s \n", data.isUpgraded() ? "Oui" : "Non"));
                strBuild.append(String.format("La potion est-elle etendue ? : %s \n", data.isExtended() ? "Oui" : "Non"));
            }
            player.sendMessage(strBuild.toString());
        }else{
            commandSender.sendMessage("TG");
        }

        return true;
    }
}
