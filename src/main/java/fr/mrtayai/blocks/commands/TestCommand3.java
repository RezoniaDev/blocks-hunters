package fr.mrtayai.blocks.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

public class TestCommand3 implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player player = (Player) commandSender;

            ItemStack itemStack = new ItemStack(Material.POTION);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("Potion de test 'EXTENDED'");
            itemStack.setItemMeta(itemMeta);
            PotionMeta potionMeta = (PotionMeta) itemMeta;
            potionMeta.setBasePotionData(new PotionData(PotionType.STRENGTH, true, false));
            itemStack.setItemMeta(potionMeta);

            ItemStack itemStack2 = new ItemStack(Material.POTION);
            ItemMeta itemMeta2 = itemStack2.getItemMeta();
            itemMeta2.setDisplayName("Potion de test 'UPGRADED'");
            itemStack2.setItemMeta(itemMeta2);
            PotionMeta potionMeta2 = (PotionMeta) itemMeta2;
            potionMeta2.setBasePotionData(new PotionData(PotionType.STRENGTH, false, true));
            itemStack2.setItemMeta(potionMeta2);

            ItemStack itemStack3 = new ItemStack(Material.TIPPED_ARROW);
            ItemMeta itemMeta3 = itemStack3.getItemMeta();
            itemMeta3.setDisplayName("Flèche de test 'EXTENDED'");
            itemStack3.setItemMeta(itemMeta3);
            PotionMeta potionMeta3 = (PotionMeta) itemMeta3;
            potionMeta3.setBasePotionData(new PotionData(PotionType.STRENGTH, true, false));
            itemStack3.setItemMeta(potionMeta3);

            ItemStack itemStack4 = new ItemStack(Material.TIPPED_ARROW);
            ItemMeta itemMeta4 = itemStack4.getItemMeta();
            itemMeta4.setDisplayName("Flèche de test 'UPGRADED'");
            itemStack4.setItemMeta(itemMeta4);
            PotionMeta potionMeta4 = (PotionMeta) itemMeta4;
            potionMeta4.setBasePotionData(new PotionData(PotionType.STRENGTH, false, true));
            itemStack4.setItemMeta(potionMeta4);

            player.getInventory().setItem(0, itemStack);
            player.getInventory().setItem(1, itemStack2);
            player.getInventory().setItem(2, itemStack3);
            player.getInventory().setItem(3, itemStack4);
        }

        return true;
    }
}
