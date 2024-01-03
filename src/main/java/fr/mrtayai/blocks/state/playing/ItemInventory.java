package fr.mrtayai.blocks.state.playing;

import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.manager.Game;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemInventory implements Listener {
        private Game game;

        private Inventory inventory;

        private List<Inventory> inventories = new ArrayList<>();

        private int numberInventories;

        private Map<UUID, Integer> numberItems = new HashMap<>();

        private Integer numberOfItems;

        private Team team;

        private List<ItemStack> items;

        private final ItemStack itemPage = new ItemStack(Material.OBSIDIAN);


        private final ItemStack itemSuivant = new ItemStack(Material.GOLD_INGOT);
        private final ItemStack itemPrecedent = new ItemStack(Material.ARROW);

        private void modifyArrow() {
            ItemMeta itemMeta = this.itemSuivant.getItemMeta();
            itemMeta.displayName(Component.text("Page Suivante"));
            this.itemSuivant.setItemMeta(itemMeta);
            ItemMeta itemMeta1 = this.itemPrecedent.getItemMeta();
            itemMeta1.displayName(Component.text("Page précédente"));
            this.itemPrecedent.setItemMeta(itemMeta1);
        }

        public ItemInventory(Game game, Team team){
            this.game = game;
            this.modifyArrow();
            this.team = team;
            this.numberOfItems = this.team.getItemsToCollect().size();
            this.numberInventories = (int) Math.ceil(this.team.getItemsToCollect().size()/45);
        }

        @EventHandler
        public void onInventoryClick(InventoryClickEvent event){
            if(!(Objects.equals(event.getClickedInventory(), this.inventory))){
                return;
            }
            BlockPlayer player = this.game.getPlayerManager().getBlockPlayer((Player) event.getWhoClicked());
            if(!this.game.getTeamManager().getTeamPlayer(player).getName().equals(this.team.getName())){
                return;
            }
            event.setCancelled(true);
            this.updateItems();

            final ItemStack clickedItem = event.getCurrentItem();

            // verify current item is not null
            if (clickedItem == null || clickedItem.getType().isAir()) return;
            Integer inventoryPage = 0;
            if(this.numberItems.containsKey(player.getPlayerUUID())){
                inventoryPage = this.numberItems.get(player.getPlayerUUID());
            }else{
                this.numberItems.put(player.getPlayerUUID(), inventoryPage);
            }
            if(clickedItem.equals(this.itemPrecedent)) {
                this.numberItems.replace(player.getPlayerUUID(), Math.max(inventoryPage-1, 0));
                this.refreshInventory(player);
                return;
            }else if(clickedItem.equals(this.itemSuivant)) {
                this.numberItems.replace(player.getPlayerUUID(), Math.min(inventoryPage+1, this.numberInventories));
                this.refreshInventory(player);
                return;
            }else if(clickedItem.equals(Objects.requireNonNull(event.getClickedInventory()).getItem(49))) {
                return;
            }
        }

        @EventHandler
        public void onInventoryQuit(InventoryCloseEvent event){
            BlockPlayer player = this.game.getPlayerManager().getBlockPlayer((Player) event.getPlayer());
            if(this.inventories.contains(event.getInventory())){
                if(Objects.equals(this.game.getTeamManager().getTeamPlayer(player), this.team)){
                    this.numberItems.remove(player.getPlayerUUID());
                }
            }
        }

        private void refreshInventory(BlockPlayer player){
            Integer numberInventory = this.numberItems.get(player.getPlayerUUID());
            player.getPlayer().openInventory(this.inventories.get(numberInventory));
        }

        private List<Inventory> createCleanInventory(int numberInventory){
            if(numberInventory == 1) {
                Inventory inv = Bukkit.createInventory(null, 54, Component.text("Test"));
                return List.of(new Inventory[]{inv});
            }else {
                List<Inventory> list = new ArrayList<>();
                for(int i = 0; i < numberInventory; i++) {
                    Inventory inv = Bukkit.createInventory(null, 54, Component.text("Test"));
                    ItemStack itemPagev2 = this.getItemPage(i+1, numberInventory);
                    inv.setItem(49, itemPagev2);
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

        private void fillInventories() {
            for(int i = 0; i < numberInventories; i++) {
                Inventory inv = this.inventories.get(i);
                int itemsMax = this.numberOfItems - 45*(i);
                if(itemsMax > 45){
                    itemsMax = 45;
                }
                for(int j = 0; j < itemsMax; j++) {
                    int z = 45*i + j;
                    inv.setItem(j, this.items.get(z));
                }
            }
        }

        private ItemStack getItemPage(int numberPage, int maxPage) {
            ItemStack newItem = this.itemPage.clone();
            ItemMeta itemMeta = newItem.getItemMeta();
            itemMeta.displayName(Component.text(String.format("Page %d/%d", numberPage, maxPage)));
            newItem.setItemMeta(itemMeta);
            return newItem;
        }

        private void updateItems(){
            for(Inventory inv : this.inventories){
                for(int i = 0; i < 45; i++){
                    ItemStack item = inv.getItem(i);
                    if(this.team.getItemsToCollect().contains(item) && (item.getEnchantments().isEmpty())){
                        ItemMeta meta = item.getItemMeta();
                        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
                        item.setItemMeta(meta);
                        inv.setItem(i, item);
                    }
                }
            }
        }

        public void openInventory(BlockPlayer player){
            this.numberItems.put(player.getPlayerUUID(), 0);
            player.getPlayer().openInventory(this.inventories.get(0));
        }


}