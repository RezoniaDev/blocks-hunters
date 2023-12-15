package fr.mrtayai.blocks.listeners;

import fr.mrtayai.blocks.Untitled;
import net.kyori.adventure.text.Component;
import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.checkerframework.checker.units.qual.C;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.ceil;

public class GUIListeners implements Listener {
    private Untitled untitled;

    private List<ItemStack> potions = new ArrayList<>();

    private List<String> materials;

    private int numberItems;

    private List<Inventory> inventories = new ArrayList<>();

    private int numberInventories;

    private int indexInventory = -1;

    private List<ItemStack> items;

    private final ItemStack itemPage = new ItemStack(Material.OBSIDIAN);


    private final ItemStack itemSuivant = new ItemStack(Material.GOLD_INGOT);
    private final ItemStack itemPrecedent = new ItemStack(Material.ARROW);

    private void modifyArrow() {
        ItemMeta itemMeta = this.itemSuivant.getItemMeta();
        itemMeta.setDisplayName("Page Suivante");
        this.itemSuivant.setItemMeta(itemMeta);
        ItemMeta itemMeta1 = this.itemPrecedent.getItemMeta();
        itemMeta1.setDisplayName("Page précédente");
        this.itemPrecedent.setItemMeta(itemMeta1);
    }

    private void changeItemPage(int numberPage, int maxPage) {
        ItemMeta itemMeta = this.itemPage.getItemMeta();
        itemMeta.setDisplayName("Page " + String.valueOf(numberPage) + "/" + String.valueOf(maxPage));
        this.itemPage.setItemMeta(itemMeta);
    }

    private void importMaterial(){
        InputStream input = getClass().getClassLoader().getResourceAsStream("items_list.txt");
        List<String> list = null;
        try {
            list = IOUtils.readLines(input, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.materials = list;
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

    private void getItemList(){
        List<ItemStack> items = new ArrayList<>();
        for(String material : this.materials) {
            items.add(new ItemStack(Material.valueOf(material)));
        }
        for(ItemStack potion : this.potions){
            items.add(potion);
        }
        this.items = items;
        this.numberItems = items.size();

    }

    public GUIListeners(Untitled untitled){
        this.untitled = untitled;
        this.importMaterial();
        this.addPotions();
        this.getItemList();
        this.untitled.getLogger().log(java.util.logging.Level.INFO, "Nombre d'items : " + String.valueOf(this.numberItems));
        this.untitled.getLogger().log(java.util.logging.Level.INFO, "Division : " + String.valueOf(ceil((double) this.items.size() /45)));
        this.untitled.getLogger().log(java.util.logging.Level.INFO, "Division inté : " + String.valueOf((int) ceil((double) this.items.size() /45)));
        this.numberInventories = (int) ceil((double) this.items.size() /45);
        this.modifyArrow();
        this.inventories = this.createCleanInventory(this.numberInventories);
        this.fillInventories();

    }

    private void refreshInventory(Player p) {
        this.changeItemPage(this.indexInventory, this.numberInventories);
        p.openInventory(this.inventories.get(this.indexInventory));
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (! inventories.contains(e.getInventory())) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final Player p = (Player) e.getWhoClicked();

        if(clickedItem.equals(this.itemPrecedent)) {
            this.indexInventory -= 1;
            this.refreshInventory(p);
            return;
        }else if(clickedItem.equals(this.itemSuivant)) {
            this.indexInventory += 1;
            this.refreshInventory(p);
            return;
        }else if(clickedItem.getType().equals(Material.OBSIDIAN) && clickedItem.displayName().equals("Page " + String.valueOf(indexInventory + 1) + "/" + String.valueOf(this.numberInventories))) {
            return;
        }
        p.sendMessage("Vous avez cliqué sur l'item : " + clickedItem.getType().toString());
        return;
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (this.inventories.contains(e.getInventory())) {
            e.setCancelled(true);
        }
    }

    public void showFirstInventory(Player p){
        p.openInventory(this.inventories.get(0));
        this.indexInventory = 0;
    }

    private void addPotions() {

        ItemStack itemA = new ItemStack(Material.POTION);
        PotionMeta potionMetaA = (PotionMeta) itemA.getItemMeta();
        potionMetaA.setBasePotionData(new PotionData(PotionType.AWKWARD, false, false));



        ItemStack item2 = new ItemStack(Material.POTION);
        PotionMeta potionMeta2 = (PotionMeta) item2.getItemMeta();
        potionMeta2.setBasePotionData(new PotionData(PotionType.AWKWARD, false, false));
        item2.setItemMeta(potionMeta2);
        this.potions.add(item2);

        ItemStack item3 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta3 = (PotionMeta) item3.getItemMeta();
        potionMeta3.setBasePotionData(new PotionData(PotionType.AWKWARD, false, false));
        item3.setItemMeta(potionMeta3);
        this.potions.add(item3);

        ItemStack item4 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta4 = (PotionMeta) item4.getItemMeta();
        potionMeta4.setBasePotionData(new PotionData(PotionType.AWKWARD, false, false));
        item4.setItemMeta(potionMeta4);
        this.potions.add(item4);

        ItemStack item5 = new ItemStack(Material.POTION);
        PotionMeta potionMeta5 = (PotionMeta) item5.getItemMeta();
        potionMeta5.setBasePotionData(new PotionData(PotionType.INVISIBILITY, false, false));
        item5.setItemMeta(potionMeta5);
        this.potions.add(item5);

        ItemStack item6 = new ItemStack(Material.POTION);
        PotionMeta potionMeta6 = (PotionMeta) item6.getItemMeta();
        potionMeta6.setBasePotionData(new PotionData(PotionType.INVISIBILITY, true, false));
        item6.setItemMeta(potionMeta6);
        this.potions.add(item6);

        ItemStack item7 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta7 = (PotionMeta) item7.getItemMeta();
        potionMeta7.setBasePotionData(new PotionData(PotionType.INVISIBILITY, false, false));
        item7.setItemMeta(potionMeta7);
        this.potions.add(item7);

        ItemStack item8 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta8 = (PotionMeta) item8.getItemMeta();
        potionMeta8.setBasePotionData(new PotionData(PotionType.INVISIBILITY, true, false));
        item8.setItemMeta(potionMeta8);
        this.potions.add(item8);

        ItemStack item9 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta9 = (PotionMeta) item9.getItemMeta();
        potionMeta9.setBasePotionData(new PotionData(PotionType.INVISIBILITY, false, false));
        item9.setItemMeta(potionMeta9);
        this.potions.add(item9);

        ItemStack item10 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta10 = (PotionMeta) item10.getItemMeta();
        potionMeta10.setBasePotionData(new PotionData(PotionType.INVISIBILITY, true, false));
        item10.setItemMeta(potionMeta10);
        this.potions.add(item10);

        ItemStack item11 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta11 = (PotionMeta) item11.getItemMeta();
        potionMeta11.setBasePotionData(new PotionData(PotionType.INVISIBILITY, false, false));
        item11.setItemMeta(potionMeta11);
        this.potions.add(item11);

        ItemStack item12 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta12 = (PotionMeta) item12.getItemMeta();
        potionMeta12.setBasePotionData(new PotionData(PotionType.INVISIBILITY, true, false));
        item12.setItemMeta(potionMeta12);
        this.potions.add(item12);

        ItemStack item13 = new ItemStack(Material.POTION);
        PotionMeta potionMeta13 = (PotionMeta) item13.getItemMeta();
        potionMeta13.setBasePotionData(new PotionData(PotionType.FIRE_RESISTANCE, false, false));
        item13.setItemMeta(potionMeta13);
        this.potions.add(item13);

        ItemStack item14 = new ItemStack(Material.POTION);
        PotionMeta potionMeta14 = (PotionMeta) item14.getItemMeta();
        potionMeta14.setBasePotionData(new PotionData(PotionType.FIRE_RESISTANCE, true, false));
        item14.setItemMeta(potionMeta14);
        this.potions.add(item14);

        ItemStack item15 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta15 = (PotionMeta) item15.getItemMeta();
        potionMeta15.setBasePotionData(new PotionData(PotionType.FIRE_RESISTANCE, false, false));
        item15.setItemMeta(potionMeta15);
        this.potions.add(item15);

        ItemStack item16 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta16 = (PotionMeta) item16.getItemMeta();
        potionMeta16.setBasePotionData(new PotionData(PotionType.FIRE_RESISTANCE, true, false));
        item16.setItemMeta(potionMeta16);
        this.potions.add(item16);

        ItemStack item17 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta17 = (PotionMeta) item17.getItemMeta();
        potionMeta17.setBasePotionData(new PotionData(PotionType.FIRE_RESISTANCE, false, false));
        item17.setItemMeta(potionMeta17);
        this.potions.add(item17);

        ItemStack item18 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta18 = (PotionMeta) item18.getItemMeta();
        potionMeta18.setBasePotionData(new PotionData(PotionType.FIRE_RESISTANCE, true, false));
        item18.setItemMeta(potionMeta18);
        this.potions.add(item18);

        ItemStack item19 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta19 = (PotionMeta) item19.getItemMeta();
        potionMeta19.setBasePotionData(new PotionData(PotionType.FIRE_RESISTANCE, false, false));
        item19.setItemMeta(potionMeta19);
        this.potions.add(item19);

        ItemStack item20 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta20 = (PotionMeta) item20.getItemMeta();
        potionMeta20.setBasePotionData(new PotionData(PotionType.FIRE_RESISTANCE, true, false));
        item20.setItemMeta(potionMeta20);
        this.potions.add(item20);

        ItemStack item21 = new ItemStack(Material.POTION);
        PotionMeta potionMeta21 = (PotionMeta) item21.getItemMeta();
        potionMeta21.setBasePotionData(new PotionData(PotionType.JUMP, false, false));
        item21.setItemMeta(potionMeta21);
        this.potions.add(item21);

        ItemStack item22 = new ItemStack(Material.POTION);
        PotionMeta potionMeta22 = (PotionMeta) item22.getItemMeta();
        potionMeta22.setBasePotionData(new PotionData(PotionType.JUMP, true, false));
        item22.setItemMeta(potionMeta22);
        this.potions.add(item22);

        ItemStack item23 = new ItemStack(Material.POTION);
        PotionMeta potionMeta23 = (PotionMeta) item23.getItemMeta();
        potionMeta23.setBasePotionData(new PotionData(PotionType.JUMP, false, true));
        item23.setItemMeta(potionMeta23);
        this.potions.add(item23);

        ItemStack item24 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta24 = (PotionMeta) item24.getItemMeta();
        potionMeta24.setBasePotionData(new PotionData(PotionType.JUMP, false, false));
        item24.setItemMeta(potionMeta24);
        this.potions.add(item24);

        ItemStack item25 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta25 = (PotionMeta) item25.getItemMeta();
        potionMeta25.setBasePotionData(new PotionData(PotionType.JUMP, true, false));
        item25.setItemMeta(potionMeta25);
        this.potions.add(item25);

        ItemStack item26 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta26 = (PotionMeta) item26.getItemMeta();
        potionMeta26.setBasePotionData(new PotionData(PotionType.JUMP, false, true));
        item26.setItemMeta(potionMeta26);
        this.potions.add(item26);

        ItemStack item27 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta27 = (PotionMeta) item27.getItemMeta();
        potionMeta27.setBasePotionData(new PotionData(PotionType.JUMP, false, false));
        item27.setItemMeta(potionMeta27);
        this.potions.add(item27);

        ItemStack item28 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta28 = (PotionMeta) item28.getItemMeta();
        potionMeta28.setBasePotionData(new PotionData(PotionType.JUMP, true, false));
        item28.setItemMeta(potionMeta28);
        this.potions.add(item28);

        ItemStack item29 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta29 = (PotionMeta) item29.getItemMeta();
        potionMeta29.setBasePotionData(new PotionData(PotionType.JUMP, false, true));
        item29.setItemMeta(potionMeta29);
        this.potions.add(item29);

        ItemStack item30 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta30 = (PotionMeta) item30.getItemMeta();
        potionMeta30.setBasePotionData(new PotionData(PotionType.JUMP, false, false));
        item30.setItemMeta(potionMeta30);
        this.potions.add(item30);

        ItemStack item31 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta31 = (PotionMeta) item31.getItemMeta();
        potionMeta31.setBasePotionData(new PotionData(PotionType.JUMP, true, false));
        item31.setItemMeta(potionMeta31);
        this.potions.add(item31);

        ItemStack item32 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta32 = (PotionMeta) item32.getItemMeta();
        potionMeta32.setBasePotionData(new PotionData(PotionType.JUMP, false, true));
        item32.setItemMeta(potionMeta32);
        this.potions.add(item32);

        ItemStack item33 = new ItemStack(Material.POTION);
        PotionMeta potionMeta33 = (PotionMeta) item33.getItemMeta();
        potionMeta33.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL, false, false));
        item33.setItemMeta(potionMeta33);
        this.potions.add(item33);

        ItemStack item34 = new ItemStack(Material.POTION);
        PotionMeta potionMeta34 = (PotionMeta) item34.getItemMeta();
        potionMeta34.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL, false, true));
        item34.setItemMeta(potionMeta34);
        this.potions.add(item34);

        ItemStack item35 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta35 = (PotionMeta) item35.getItemMeta();
        potionMeta35.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL, false, false));
        item35.setItemMeta(potionMeta35);
        this.potions.add(item35);

        ItemStack item36 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta36 = (PotionMeta) item36.getItemMeta();
        potionMeta36.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL, false, true));
        item36.setItemMeta(potionMeta36);
        this.potions.add(item36);

        ItemStack item37 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta37 = (PotionMeta) item37.getItemMeta();
        potionMeta37.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL, false, false));
        item37.setItemMeta(potionMeta37);
        this.potions.add(item37);

        ItemStack item38 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta38 = (PotionMeta) item38.getItemMeta();
        potionMeta38.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL, false, true));
        item38.setItemMeta(potionMeta38);
        this.potions.add(item38);

        ItemStack item39 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta39 = (PotionMeta) item39.getItemMeta();
        potionMeta39.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL, false, false));
        item39.setItemMeta(potionMeta39);
        this.potions.add(item39);

        ItemStack item40 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta40 = (PotionMeta) item40.getItemMeta();
        potionMeta40.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL, false, true));
        item40.setItemMeta(potionMeta40);
        this.potions.add(item40);

        ItemStack item41 = new ItemStack(Material.POTION);
        PotionMeta potionMeta41 = (PotionMeta) item41.getItemMeta();
        potionMeta41.setBasePotionData(new PotionData(PotionType.INSTANT_DAMAGE, false, false));
        item41.setItemMeta(potionMeta41);
        this.potions.add(item41);

        ItemStack item42 = new ItemStack(Material.POTION);
        PotionMeta potionMeta42 = (PotionMeta) item42.getItemMeta();
        potionMeta42.setBasePotionData(new PotionData(PotionType.INSTANT_DAMAGE, false, true));
        item42.setItemMeta(potionMeta42);
        this.potions.add(item42);

        ItemStack item43 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta43 = (PotionMeta) item43.getItemMeta();
        potionMeta43.setBasePotionData(new PotionData(PotionType.INSTANT_DAMAGE, false, false));
        item43.setItemMeta(potionMeta43);
        this.potions.add(item43);

        ItemStack item44 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta44 = (PotionMeta) item44.getItemMeta();
        potionMeta44.setBasePotionData(new PotionData(PotionType.INSTANT_DAMAGE, false, true));
        item44.setItemMeta(potionMeta44);
        this.potions.add(item44);

        ItemStack item45 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta45 = (PotionMeta) item45.getItemMeta();
        potionMeta45.setBasePotionData(new PotionData(PotionType.INSTANT_DAMAGE, false, false));
        item45.setItemMeta(potionMeta45);
        this.potions.add(item45);

        ItemStack item46 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta46 = (PotionMeta) item46.getItemMeta();
        potionMeta46.setBasePotionData(new PotionData(PotionType.INSTANT_DAMAGE, false, true));
        item46.setItemMeta(potionMeta46);
        this.potions.add(item46);

        ItemStack item47 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta47 = (PotionMeta) item47.getItemMeta();
        potionMeta47.setBasePotionData(new PotionData(PotionType.INSTANT_DAMAGE, false, false));
        item47.setItemMeta(potionMeta47);
        this.potions.add(item47);

        ItemStack item48 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta48 = (PotionMeta) item48.getItemMeta();
        potionMeta48.setBasePotionData(new PotionData(PotionType.INSTANT_DAMAGE, false, true));
        item48.setItemMeta(potionMeta48);
        this.potions.add(item48);

        ItemStack item49 = new ItemStack(Material.POTION);
        PotionMeta potionMeta49 = (PotionMeta) item49.getItemMeta();
        potionMeta49.setBasePotionData(new PotionData(PotionType.LUCK, false, false));
        item49.setItemMeta(potionMeta49);
        this.potions.add(item49);

        ItemStack item50 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta50 = (PotionMeta) item50.getItemMeta();
        potionMeta50.setBasePotionData(new PotionData(PotionType.LUCK, false, false));
        item50.setItemMeta(potionMeta50);
        this.potions.add(item50);

        ItemStack item51 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta51 = (PotionMeta) item51.getItemMeta();
        potionMeta51.setBasePotionData(new PotionData(PotionType.LUCK, false, false));
        item51.setItemMeta(potionMeta51);
        this.potions.add(item51);

        ItemStack item52 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta52 = (PotionMeta) item52.getItemMeta();
        potionMeta52.setBasePotionData(new PotionData(PotionType.LUCK, false, false));
        item52.setItemMeta(potionMeta52);
        this.potions.add(item52);

        ItemStack item53 = new ItemStack(Material.POTION);
        PotionMeta potionMeta53 = (PotionMeta) item53.getItemMeta();
        potionMeta53.setBasePotionData(new PotionData(PotionType.MUNDANE, false, false));
        item53.setItemMeta(potionMeta53);
        this.potions.add(item53);

        ItemStack item54 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta54 = (PotionMeta) item54.getItemMeta();
        potionMeta54.setBasePotionData(new PotionData(PotionType.MUNDANE, false, false));
        item54.setItemMeta(potionMeta54);
        this.potions.add(item54);

        ItemStack item55 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta55 = (PotionMeta) item55.getItemMeta();
        potionMeta55.setBasePotionData(new PotionData(PotionType.MUNDANE, false, false));
        item55.setItemMeta(potionMeta55);
        this.potions.add(item55);

        ItemStack item56 = new ItemStack(Material.POTION);
        PotionMeta potionMeta56 = (PotionMeta) item56.getItemMeta();
        potionMeta56.setBasePotionData(new PotionData(PotionType.NIGHT_VISION, false, false));
        item56.setItemMeta(potionMeta56);
        this.potions.add(item56);

        ItemStack item57 = new ItemStack(Material.POTION);
        PotionMeta potionMeta57 = (PotionMeta) item57.getItemMeta();
        potionMeta57.setBasePotionData(new PotionData(PotionType.NIGHT_VISION, true, false));
        item57.setItemMeta(potionMeta57);
        this.potions.add(item57);

        ItemStack item58 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta58 = (PotionMeta) item58.getItemMeta();
        potionMeta58.setBasePotionData(new PotionData(PotionType.NIGHT_VISION, false, false));
        item58.setItemMeta(potionMeta58);
        this.potions.add(item58);

        ItemStack item59 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta59 = (PotionMeta) item59.getItemMeta();
        potionMeta59.setBasePotionData(new PotionData(PotionType.NIGHT_VISION, true, false));
        item59.setItemMeta(potionMeta59);
        this.potions.add(item59);

        ItemStack item60 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta60 = (PotionMeta) item60.getItemMeta();
        potionMeta60.setBasePotionData(new PotionData(PotionType.NIGHT_VISION, false, false));
        item60.setItemMeta(potionMeta60);
        this.potions.add(item60);

        ItemStack item61 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta61 = (PotionMeta) item61.getItemMeta();
        potionMeta61.setBasePotionData(new PotionData(PotionType.NIGHT_VISION, true, false));
        item61.setItemMeta(potionMeta61);
        this.potions.add(item61);

        ItemStack item62 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta62 = (PotionMeta) item62.getItemMeta();
        potionMeta62.setBasePotionData(new PotionData(PotionType.NIGHT_VISION, false, false));
        item62.setItemMeta(potionMeta62);
        this.potions.add(item62);

        ItemStack item63 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta63 = (PotionMeta) item63.getItemMeta();
        potionMeta63.setBasePotionData(new PotionData(PotionType.NIGHT_VISION, true, false));
        item63.setItemMeta(potionMeta63);
        this.potions.add(item63);

        ItemStack item64 = new ItemStack(Material.POTION);
        PotionMeta potionMeta64 = (PotionMeta) item64.getItemMeta();
        potionMeta64.setBasePotionData(new PotionData(PotionType.POISON, false, false));
        item64.setItemMeta(potionMeta64);
        this.potions.add(item64);

        ItemStack item65 = new ItemStack(Material.POTION);
        PotionMeta potionMeta65 = (PotionMeta) item65.getItemMeta();
        potionMeta65.setBasePotionData(new PotionData(PotionType.POISON, true, false));
        item65.setItemMeta(potionMeta65);
        this.potions.add(item65);

        ItemStack item66 = new ItemStack(Material.POTION);
        PotionMeta potionMeta66 = (PotionMeta) item66.getItemMeta();
        potionMeta66.setBasePotionData(new PotionData(PotionType.POISON, false, true));
        item66.setItemMeta(potionMeta66);
        this.potions.add(item66);

        ItemStack item67 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta67 = (PotionMeta) item67.getItemMeta();
        potionMeta67.setBasePotionData(new PotionData(PotionType.POISON, false, false));
        item67.setItemMeta(potionMeta67);
        this.potions.add(item67);

        ItemStack item68 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta68 = (PotionMeta) item68.getItemMeta();
        potionMeta68.setBasePotionData(new PotionData(PotionType.POISON, true, false));
        item68.setItemMeta(potionMeta68);
        this.potions.add(item68);

        ItemStack item69 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta69 = (PotionMeta) item69.getItemMeta();
        potionMeta69.setBasePotionData(new PotionData(PotionType.POISON, false, true));
        item69.setItemMeta(potionMeta69);
        this.potions.add(item69);

        ItemStack item70 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta70 = (PotionMeta) item70.getItemMeta();
        potionMeta70.setBasePotionData(new PotionData(PotionType.POISON, false, false));
        item70.setItemMeta(potionMeta70);
        this.potions.add(item70);

        ItemStack item71 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta71 = (PotionMeta) item71.getItemMeta();
        potionMeta71.setBasePotionData(new PotionData(PotionType.POISON, true, false));
        item71.setItemMeta(potionMeta71);
        this.potions.add(item71);

        ItemStack item72 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta72 = (PotionMeta) item72.getItemMeta();
        potionMeta72.setBasePotionData(new PotionData(PotionType.POISON, false, true));
        item72.setItemMeta(potionMeta72);
        this.potions.add(item72);

        ItemStack item73 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta73 = (PotionMeta) item73.getItemMeta();
        potionMeta73.setBasePotionData(new PotionData(PotionType.POISON, false, false));
        item73.setItemMeta(potionMeta73);
        this.potions.add(item73);

        ItemStack item74 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta74 = (PotionMeta) item74.getItemMeta();
        potionMeta74.setBasePotionData(new PotionData(PotionType.POISON, true, false));
        item74.setItemMeta(potionMeta74);
        this.potions.add(item74);

        ItemStack item75 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta75 = (PotionMeta) item75.getItemMeta();
        potionMeta75.setBasePotionData(new PotionData(PotionType.POISON, false, true));
        item75.setItemMeta(potionMeta75);
        this.potions.add(item75);

        ItemStack item76 = new ItemStack(Material.POTION);
        PotionMeta potionMeta76 = (PotionMeta) item76.getItemMeta();
        potionMeta76.setBasePotionData(new PotionData(PotionType.REGEN, false, false));
        item76.setItemMeta(potionMeta76);
        this.potions.add(item76);

        ItemStack item77 = new ItemStack(Material.POTION);
        PotionMeta potionMeta77 = (PotionMeta) item77.getItemMeta();
        potionMeta77.setBasePotionData(new PotionData(PotionType.REGEN, true, false));
        item77.setItemMeta(potionMeta77);
        this.potions.add(item77);

        ItemStack item78 = new ItemStack(Material.POTION);
        PotionMeta potionMeta78 = (PotionMeta) item78.getItemMeta();
        potionMeta78.setBasePotionData(new PotionData(PotionType.REGEN, false, true));
        item78.setItemMeta(potionMeta78);
        this.potions.add(item78);

        ItemStack item79 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta79 = (PotionMeta) item79.getItemMeta();
        potionMeta79.setBasePotionData(new PotionData(PotionType.REGEN, false, false));
        item79.setItemMeta(potionMeta79);
        this.potions.add(item79);

        ItemStack item80 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta80 = (PotionMeta) item80.getItemMeta();
        potionMeta80.setBasePotionData(new PotionData(PotionType.REGEN, true, false));
        item80.setItemMeta(potionMeta80);
        this.potions.add(item80);

        ItemStack item81 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta81 = (PotionMeta) item81.getItemMeta();
        potionMeta81.setBasePotionData(new PotionData(PotionType.REGEN, false, true));
        item81.setItemMeta(potionMeta81);
        this.potions.add(item81);

        ItemStack item82 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta82 = (PotionMeta) item82.getItemMeta();
        potionMeta82.setBasePotionData(new PotionData(PotionType.REGEN, false, false));
        item82.setItemMeta(potionMeta82);
        this.potions.add(item82);

        ItemStack item83 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta83 = (PotionMeta) item83.getItemMeta();
        potionMeta83.setBasePotionData(new PotionData(PotionType.REGEN, true, false));
        item83.setItemMeta(potionMeta83);
        this.potions.add(item83);

        ItemStack item84 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta84 = (PotionMeta) item84.getItemMeta();
        potionMeta84.setBasePotionData(new PotionData(PotionType.REGEN, false, true));
        item84.setItemMeta(potionMeta84);
        this.potions.add(item84);

        ItemStack item85 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta85 = (PotionMeta) item85.getItemMeta();
        potionMeta85.setBasePotionData(new PotionData(PotionType.REGEN, false, false));
        item85.setItemMeta(potionMeta85);
        this.potions.add(item85);

        ItemStack item86 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta86 = (PotionMeta) item86.getItemMeta();
        potionMeta86.setBasePotionData(new PotionData(PotionType.REGEN, true, false));
        item86.setItemMeta(potionMeta86);
        this.potions.add(item86);

        ItemStack item87 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta87 = (PotionMeta) item87.getItemMeta();
        potionMeta87.setBasePotionData(new PotionData(PotionType.REGEN, false, true));
        item87.setItemMeta(potionMeta87);
        this.potions.add(item87);

        ItemStack item88 = new ItemStack(Material.POTION);
        PotionMeta potionMeta88 = (PotionMeta) item88.getItemMeta();
        potionMeta88.setBasePotionData(new PotionData(PotionType.SLOW_FALLING, false, false));
        item88.setItemMeta(potionMeta88);
        this.potions.add(item88);

        ItemStack item89 = new ItemStack(Material.POTION);
        PotionMeta potionMeta89 = (PotionMeta) item89.getItemMeta();
        potionMeta89.setBasePotionData(new PotionData(PotionType.SLOW_FALLING, true, false));
        item89.setItemMeta(potionMeta89);
        this.potions.add(item89);

        ItemStack item90 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta90 = (PotionMeta) item90.getItemMeta();
        potionMeta90.setBasePotionData(new PotionData(PotionType.SLOW_FALLING, false, false));
        item90.setItemMeta(potionMeta90);
        this.potions.add(item90);

        ItemStack item91 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta91 = (PotionMeta) item91.getItemMeta();
        potionMeta91.setBasePotionData(new PotionData(PotionType.SLOW_FALLING, true, false));
        item91.setItemMeta(potionMeta91);
        this.potions.add(item91);

        ItemStack item92 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta92 = (PotionMeta) item92.getItemMeta();
        potionMeta92.setBasePotionData(new PotionData(PotionType.SLOW_FALLING, false, false));
        item92.setItemMeta(potionMeta92);
        this.potions.add(item92);

        ItemStack item93 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta93 = (PotionMeta) item93.getItemMeta();
        potionMeta93.setBasePotionData(new PotionData(PotionType.SLOW_FALLING, true, false));
        item93.setItemMeta(potionMeta93);
        this.potions.add(item93);

        ItemStack item94 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta94 = (PotionMeta) item94.getItemMeta();
        potionMeta94.setBasePotionData(new PotionData(PotionType.SLOW_FALLING, false, false));
        item94.setItemMeta(potionMeta94);
        this.potions.add(item94);

        ItemStack item95 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta95 = (PotionMeta) item95.getItemMeta();
        potionMeta95.setBasePotionData(new PotionData(PotionType.SLOW_FALLING, true, false));
        item95.setItemMeta(potionMeta95);
        this.potions.add(item95);

        ItemStack item96 = new ItemStack(Material.POTION);
        PotionMeta potionMeta96 = (PotionMeta) item96.getItemMeta();
        potionMeta96.setBasePotionData(new PotionData(PotionType.SLOWNESS, false, false));
        item96.setItemMeta(potionMeta96);
        this.potions.add(item96);

        ItemStack item97 = new ItemStack(Material.POTION);
        PotionMeta potionMeta97 = (PotionMeta) item97.getItemMeta();
        potionMeta97.setBasePotionData(new PotionData(PotionType.SLOWNESS, true, false));
        item97.setItemMeta(potionMeta97);
        this.potions.add(item97);

        ItemStack item98 = new ItemStack(Material.POTION);
        PotionMeta potionMeta98 = (PotionMeta) item98.getItemMeta();
        potionMeta98.setBasePotionData(new PotionData(PotionType.SLOWNESS, false, true));
        item98.setItemMeta(potionMeta98);
        this.potions.add(item98);

        ItemStack item99 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta99 = (PotionMeta) item99.getItemMeta();
        potionMeta99.setBasePotionData(new PotionData(PotionType.SLOWNESS, false, false));
        item99.setItemMeta(potionMeta99);
        this.potions.add(item99);

        ItemStack item100 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta100 = (PotionMeta) item100.getItemMeta();
        potionMeta100.setBasePotionData(new PotionData(PotionType.SLOWNESS, true, false));
        item100.setItemMeta(potionMeta100);
        this.potions.add(item100);

        ItemStack item101 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta101 = (PotionMeta) item101.getItemMeta();
        potionMeta101.setBasePotionData(new PotionData(PotionType.SLOWNESS, false, true));
        item101.setItemMeta(potionMeta101);
        this.potions.add(item101);

        ItemStack item102 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta102 = (PotionMeta) item102.getItemMeta();
        potionMeta102.setBasePotionData(new PotionData(PotionType.SLOWNESS, false, false));
        item102.setItemMeta(potionMeta102);
        this.potions.add(item102);

        ItemStack item103 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta103 = (PotionMeta) item103.getItemMeta();
        potionMeta103.setBasePotionData(new PotionData(PotionType.SLOWNESS, true, false));
        item103.setItemMeta(potionMeta103);
        this.potions.add(item103);

        ItemStack item104 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta104 = (PotionMeta) item104.getItemMeta();
        potionMeta104.setBasePotionData(new PotionData(PotionType.SLOWNESS, false, true));
        item104.setItemMeta(potionMeta104);
        this.potions.add(item104);

        ItemStack item105 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta105 = (PotionMeta) item105.getItemMeta();
        potionMeta105.setBasePotionData(new PotionData(PotionType.SLOWNESS, false, false));
        item105.setItemMeta(potionMeta105);
        this.potions.add(item105);

        ItemStack item106 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta106 = (PotionMeta) item106.getItemMeta();
        potionMeta106.setBasePotionData(new PotionData(PotionType.SLOWNESS, true, false));
        item106.setItemMeta(potionMeta106);
        this.potions.add(item106);

        ItemStack item107 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta107 = (PotionMeta) item107.getItemMeta();
        potionMeta107.setBasePotionData(new PotionData(PotionType.SLOWNESS, false, true));
        item107.setItemMeta(potionMeta107);
        this.potions.add(item107);

        ItemStack item108 = new ItemStack(Material.POTION);
        PotionMeta potionMeta108 = (PotionMeta) item108.getItemMeta();
        potionMeta108.setBasePotionData(new PotionData(PotionType.SPEED, false, false));
        item108.setItemMeta(potionMeta108);
        this.potions.add(item108);

        ItemStack item109 = new ItemStack(Material.POTION);
        PotionMeta potionMeta109 = (PotionMeta) item109.getItemMeta();
        potionMeta109.setBasePotionData(new PotionData(PotionType.SPEED, true, false));
        item109.setItemMeta(potionMeta109);
        this.potions.add(item109);

        ItemStack item110 = new ItemStack(Material.POTION);
        PotionMeta potionMeta110 = (PotionMeta) item110.getItemMeta();
        potionMeta110.setBasePotionData(new PotionData(PotionType.SPEED, false, true));
        item110.setItemMeta(potionMeta110);
        this.potions.add(item110);

        ItemStack item111 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta111 = (PotionMeta) item111.getItemMeta();
        potionMeta111.setBasePotionData(new PotionData(PotionType.SPEED, false, false));
        item111.setItemMeta(potionMeta111);
        this.potions.add(item111);

        ItemStack item112 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta112 = (PotionMeta) item112.getItemMeta();
        potionMeta112.setBasePotionData(new PotionData(PotionType.SPEED, true, false));
        item112.setItemMeta(potionMeta112);
        this.potions.add(item112);

        ItemStack item113 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta113 = (PotionMeta) item113.getItemMeta();
        potionMeta113.setBasePotionData(new PotionData(PotionType.SPEED, false, true));
        item113.setItemMeta(potionMeta113);
        this.potions.add(item113);

        ItemStack item114 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta114 = (PotionMeta) item114.getItemMeta();
        potionMeta114.setBasePotionData(new PotionData(PotionType.SPEED, false, false));
        item114.setItemMeta(potionMeta114);
        this.potions.add(item114);

        ItemStack item115 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta115 = (PotionMeta) item115.getItemMeta();
        potionMeta115.setBasePotionData(new PotionData(PotionType.SPEED, true, false));
        item115.setItemMeta(potionMeta115);
        this.potions.add(item115);

        ItemStack item116 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta116 = (PotionMeta) item116.getItemMeta();
        potionMeta116.setBasePotionData(new PotionData(PotionType.SPEED, false, true));
        item116.setItemMeta(potionMeta116);
        this.potions.add(item116);

        ItemStack item117 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta117 = (PotionMeta) item117.getItemMeta();
        potionMeta117.setBasePotionData(new PotionData(PotionType.SPEED, false, false));
        item117.setItemMeta(potionMeta117);
        this.potions.add(item117);

        ItemStack item118 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta118 = (PotionMeta) item118.getItemMeta();
        potionMeta118.setBasePotionData(new PotionData(PotionType.SPEED, true, false));
        item118.setItemMeta(potionMeta118);
        this.potions.add(item118);

        ItemStack item119 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta119 = (PotionMeta) item119.getItemMeta();
        potionMeta119.setBasePotionData(new PotionData(PotionType.SPEED, false, true));
        item119.setItemMeta(potionMeta119);
        this.potions.add(item119);

        ItemStack item120 = new ItemStack(Material.POTION);
        PotionMeta potionMeta120 = (PotionMeta) item120.getItemMeta();
        potionMeta120.setBasePotionData(new PotionData(PotionType.STRENGTH, false, false));
        item120.setItemMeta(potionMeta120);
        this.potions.add(item120);

        ItemStack item121 = new ItemStack(Material.POTION);
        PotionMeta potionMeta121 = (PotionMeta) item121.getItemMeta();
        potionMeta121.setBasePotionData(new PotionData(PotionType.STRENGTH, true, false));
        item121.setItemMeta(potionMeta121);
        this.potions.add(item121);

        ItemStack item122 = new ItemStack(Material.POTION);
        PotionMeta potionMeta122 = (PotionMeta) item122.getItemMeta();
        potionMeta122.setBasePotionData(new PotionData(PotionType.STRENGTH, false, true));
        item122.setItemMeta(potionMeta122);
        this.potions.add(item122);

        ItemStack item123 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta123 = (PotionMeta) item123.getItemMeta();
        potionMeta123.setBasePotionData(new PotionData(PotionType.STRENGTH, false, false));
        item123.setItemMeta(potionMeta123);
        this.potions.add(item123);

        ItemStack item124 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta124 = (PotionMeta) item124.getItemMeta();
        potionMeta124.setBasePotionData(new PotionData(PotionType.STRENGTH, true, false));
        item124.setItemMeta(potionMeta124);
        this.potions.add(item124);

        ItemStack item125 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta125 = (PotionMeta) item125.getItemMeta();
        potionMeta125.setBasePotionData(new PotionData(PotionType.STRENGTH, false, true));
        item125.setItemMeta(potionMeta125);
        this.potions.add(item125);

        ItemStack item126 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta126 = (PotionMeta) item126.getItemMeta();
        potionMeta126.setBasePotionData(new PotionData(PotionType.STRENGTH, false, false));
        item126.setItemMeta(potionMeta126);
        this.potions.add(item126);

        ItemStack item127 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta127 = (PotionMeta) item127.getItemMeta();
        potionMeta127.setBasePotionData(new PotionData(PotionType.STRENGTH, true, false));
        item127.setItemMeta(potionMeta127);
        this.potions.add(item127);

        ItemStack item128 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta128 = (PotionMeta) item128.getItemMeta();
        potionMeta128.setBasePotionData(new PotionData(PotionType.STRENGTH, false, true));
        item128.setItemMeta(potionMeta128);
        this.potions.add(item128);

        ItemStack item129 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta129 = (PotionMeta) item129.getItemMeta();
        potionMeta129.setBasePotionData(new PotionData(PotionType.STRENGTH, false, false));
        item129.setItemMeta(potionMeta129);
        this.potions.add(item129);

        ItemStack item130 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta130 = (PotionMeta) item130.getItemMeta();
        potionMeta130.setBasePotionData(new PotionData(PotionType.STRENGTH, true, false));
        item130.setItemMeta(potionMeta130);
        this.potions.add(item130);

        ItemStack item131 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta131 = (PotionMeta) item131.getItemMeta();
        potionMeta131.setBasePotionData(new PotionData(PotionType.STRENGTH, false, true));
        item131.setItemMeta(potionMeta131);
        this.potions.add(item131);

        ItemStack item132 = new ItemStack(Material.POTION);
        PotionMeta potionMeta132 = (PotionMeta) item132.getItemMeta();
        potionMeta132.setBasePotionData(new PotionData(PotionType.THICK, false, false));
        item132.setItemMeta(potionMeta132);
        this.potions.add(item132);

        ItemStack item133 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta133 = (PotionMeta) item133.getItemMeta();
        potionMeta133.setBasePotionData(new PotionData(PotionType.THICK, false, false));
        item133.setItemMeta(potionMeta133);
        this.potions.add(item133);

        ItemStack item134 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta134 = (PotionMeta) item134.getItemMeta();
        potionMeta134.setBasePotionData(new PotionData(PotionType.THICK, false, false));
        item134.setItemMeta(potionMeta134);
        this.potions.add(item134);

        ItemStack item135 = new ItemStack(Material.POTION);
        PotionMeta potionMeta135 = (PotionMeta) item135.getItemMeta();
        potionMeta135.setBasePotionData(new PotionData(PotionType.TURTLE_MASTER, false, false));
        item135.setItemMeta(potionMeta135);
        this.potions.add(item135);

        ItemStack item136 = new ItemStack(Material.POTION);
        PotionMeta potionMeta136 = (PotionMeta) item136.getItemMeta();
        potionMeta136.setBasePotionData(new PotionData(PotionType.TURTLE_MASTER, true, false));
        item136.setItemMeta(potionMeta136);
        this.potions.add(item136);

        ItemStack item137 = new ItemStack(Material.POTION);
        PotionMeta potionMeta137 = (PotionMeta) item137.getItemMeta();
        potionMeta137.setBasePotionData(new PotionData(PotionType.TURTLE_MASTER, false, true));
        item137.setItemMeta(potionMeta137);
        this.potions.add(item137);

        ItemStack item138 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta138 = (PotionMeta) item138.getItemMeta();
        potionMeta138.setBasePotionData(new PotionData(PotionType.TURTLE_MASTER, false, false));
        item138.setItemMeta(potionMeta138);
        this.potions.add(item138);

        ItemStack item139 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta139 = (PotionMeta) item139.getItemMeta();
        potionMeta139.setBasePotionData(new PotionData(PotionType.TURTLE_MASTER, true, false));
        item139.setItemMeta(potionMeta139);
        this.potions.add(item139);

        ItemStack item140 = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta140 = (PotionMeta) item140.getItemMeta();
        potionMeta140.setBasePotionData(new PotionData(PotionType.TURTLE_MASTER, false, true));
        item140.setItemMeta(potionMeta140);
        this.potions.add(item140);

        ItemStack item141 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta141 = (PotionMeta) item141.getItemMeta();
        potionMeta141.setBasePotionData(new PotionData(PotionType.TURTLE_MASTER, false, false));
        item141.setItemMeta(potionMeta141);
        this.potions.add(item141);

        ItemStack item142 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta142 = (PotionMeta) item142.getItemMeta();
        potionMeta142.setBasePotionData(new PotionData(PotionType.TURTLE_MASTER, true, false));
        item142.setItemMeta(potionMeta142);
        this.potions.add(item142);

        ItemStack item143 = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta143 = (PotionMeta) item143.getItemMeta();
        potionMeta143.setBasePotionData(new PotionData(PotionType.TURTLE_MASTER, false, true));
        item143.setItemMeta(potionMeta143);
        this.potions.add(item143);

        ItemStack item144 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta144 = (PotionMeta) item144.getItemMeta();
        potionMeta144.setBasePotionData(new PotionData(PotionType.TURTLE_MASTER, false, false));
        item144.setItemMeta(potionMeta144);
        this.potions.add(item144);

        ItemStack item145 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta145 = (PotionMeta) item145.getItemMeta();
        potionMeta145.setBasePotionData(new PotionData(PotionType.TURTLE_MASTER, true, false));
        item145.setItemMeta(potionMeta145);
        this.potions.add(item145);

        ItemStack item146 = new ItemStack(Material.TIPPED_ARROW);
        PotionMeta potionMeta146 = (PotionMeta) item146.getItemMeta();
        potionMeta146.setBasePotionData(new PotionData(PotionType.TURTLE_MASTER, false, true));
        item146.setItemMeta(potionMeta146);
        this.potions.add(item146);



    }
}




