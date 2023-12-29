package fr.mrtayai.blocks.state.waiting;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.Inventory;
import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.manager.Game;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeamSelectionGUI {

    private Game game;

    public TeamSelectionGUI(Game game){
        this.game = game;
    }

    public void openGUI(BlockPlayer player){
        Inventory inv = Bukkit.createInventory(null, 27, "Sélection des équipes");

        ItemStack redTeam = new ItemStack(Material.RED_WOOL);
        ItemMeta redMeta = redTeam.getItemMeta();
        redMeta.displayName(Component.text("Equipe Rouge"));
        redTeam.setItemMeta(redMeta);

        ItemStack blueTeam = new ItemStack(Material.BLUE_WOOL);
        ItemMeta blueMeta = blueTeam.getItemMeta();
        blueMeta.displayName(Component.text("Equipe bleue"));
        redTeam.setItemMeta(blueMeta);

        ItemStack greenTeam = new ItemStack(Material.GREEN_WOOL);
        ItemMeta greenMeta = greenTeam.getItemMeta();
        greenMeta.displayName(Component.text("Equipe verte"));

        ItemStack yellowTeam = new ItemStack(Material.YELLOW_WOOL);
        ItemMeta yellowMeta = yellowTeam.getItemMeta();
        yellowMeta.displayName(Component.text("Equipe jaune"));

        inv.setItem(10, redTeam);
        inv.setItem(12, blueTeam);
        inv.setItem(14, greenTeam);
        inv.setItem(16, yellowTeam);

    }

}
