package fr.mrtayai.blocks.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TeamInventory {

    private UUID teamUUID;

    private List<ItemStack> items = new ArrayList<>();

    private int numberInventories = 0;

    private final ItemStack itemPage = new ItemStack(Material.OBSIDIAN);

    private int numberItems = 0;

    private List<Inventory> inventories = this.createCleanInventory(this.numberInventories);

    private final ItemStack itemSuivant = new ItemStack(Material.GOLD_INGOT);
    private final ItemStack itemPrecedent = new ItemStack(Material.ARROW);
    private HashMap<UUID, Integer> indexInventoriesPerPlayer = new HashMap<UUID, Integer>();

    private void modifyArrow() {
        ItemMeta itemMeta = this.itemSuivant.getItemMeta();
        itemMeta.setDisplayName("Page Suivante");
        this.itemSuivant.setItemMeta(itemMeta);
        ItemMeta itemMeta1 = this.itemPrecedent.getItemMeta();
        itemMeta1.setDisplayName("Page précédente");
        this.itemPrecedent.setItemMeta(itemMeta1);
    }

    public TeamInventory(UUID teamUUID, List<ItemStack> items){
        this.teamUUID = teamUUID;
        this.items = items;
        this.numberItems = items.size();
        this.numberInventories = (int) Math.ceil((double) this.numberItems/45);
        this.modifyArrow();
        this.inventories = this.createCleanInventory(this.numberInventories);
        this.fillInventories();
    }

    private List<Inventory> createCleanInventory(int numberInventory){
        if(numberInventory == 1) {
            Inventory inv = Bukkit.createInventory(null, 54, Component.text("Test"));
            return List.of(new Inventory[]{inv});
        }else {
            List<Inventory> list = new ArrayList<>();
            for(int i = 0; i < numberInventory; i++) {
                Inventory inv = Bukkit.createInventory(null, 54, Component.text("Test"));
                this.changeItemPage(i+1, numberInventory);
                inv.setItem(49, this.itemPage);
                if(i == 0) {
                    inv.setItem(53, this.itemSuivant);
                }else if(i == numberInventory-1) {
                    inv.setItem(45, this.itemPrecedent);
                }else{
                    inv.setItem(53, this.itemSuivant);
                    inv.setItem(45, this.itemPrecedent);
                }
                list.add(inv);
            }
            return list;
        }
    }

    private void changeItemPage(int numberPage, int maxPage) {
        ItemMeta itemMeta = this.itemPage.getItemMeta();
        itemMeta.setDisplayName("Page " + String.valueOf(numberPage) + "/" + String.valueOf(maxPage));
        this.itemPage.setItemMeta(itemMeta);
    }

    private void fillInventories() {
        for(int i = 0; i < numberInventories; i++) {
            Inventory inv = this.inventories.get(i);
            int itemsMax = this.numberItems - 45*(i);
            if(itemsMax > 45){
                itemsMax = 45;
            }
            for(int j = 0; j < itemsMax; j++) {
                int z = 45*i + j;
                inv.setItem(j, this.items.get(z));
            }
        }
    }

    public boolean isInInventory(UUID uuid){
        return this.indexInventoriesPerPlayer.containsKey(uuid);
    }

    public void startInventory(UUID uuid){
        this.indexInventoriesPerPlayer.put(uuid, 0);
        Bukkit.getPlayer(uuid).openInventory(this.inventories.get(0));
    }

    public void openPreviousPage(UUID uuid){
        int index = this.indexInventoriesPerPlayer.get(uuid);
        if(index == 0) {
            return;
        }
        this.openInventory(uuid, index-1);
        this.indexInventoriesPerPlayer.put(uuid, index-1);
    }

    public void openNextPage(UUID uuid){
        int index = this.indexInventoriesPerPlayer.get(uuid);
        if(index == this.numberInventories-1) {
            return;
        }
        this.openInventory(uuid, index+1);
        this.indexInventoriesPerPlayer.put(uuid, index+1);
    }

    public void closeInventory(UUID uuid){
        this.indexInventoriesPerPlayer.remove(uuid);
        Bukkit.getPlayer(uuid).closeInventory();
    }

    private void openInventory(UUID uuid, int index){
        Bukkit.getPlayer(uuid).closeInventory(InventoryCloseEvent.Reason.PLUGIN);
        Bukkit.getPlayer(uuid).openInventory(this.inventories.get(index));
    }


    public ItemStack getItemPage() {
        return itemPage;
    }

    public ItemStack getItemPrecedent() {
        return itemPrecedent;
    }

    public ItemStack getItemSuivant() {
        return itemSuivant;
    }

    public void setItemCollected(ItemStack itemCollected){
        for(Inventory inv : this.inventories){
            for(int i = 0; i < inv.getSize(); i++){
                Bukkit.getLogger().info("Item n°"+i + " => " + inv.getItem(i));
                if(inv.getItem(i) != null) {
                    if (inv.getItem(i).equals(itemCollected)) {
                        itemCollected.addUnsafeEnchantment(Enchantment.LUCK, 1);
                        ItemMeta itemMeta = itemCollected.getItemMeta();
                        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                        itemCollected.setItemMeta(itemMeta);
                        inv.setItem(i, itemCollected);
                    }
                }
            }
        }
    }

    public boolean containsInventory(Inventory inventory){
        return this.inventories.contains(inventory);
    }

}

