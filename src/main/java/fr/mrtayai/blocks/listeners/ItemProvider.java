package fr.mrtayai.blocks.listeners;

import fr.mrtayai.blocks.BlockMain;
import org.apache.commons.io.IOUtils;
import org.bukkit.Material;
import org.bukkit.MusicInstrument;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MusicInstrumentMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.ceil;

public class ItemProvider implements Listener {
    private BlockMain untitled;

    private List<ItemStack> potions = new ArrayList<>();

    private List<String> materials;

    private int numberItems;

    private List<Inventory> inventories = new ArrayList<>();

    private int numberInventories;

    private List<ItemStack> items;

    private void importMaterial(File file){
        InputStream input = null;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<String> list = null;
        try {
            list = IOUtils.readLines(input, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.materials = list;
    }

    private void getItemList(){
        List<ItemStack> items = new ArrayList<>();
        for(String material : this.materials) {
            items.add(new ItemStack(Material.valueOf(material)));
        }
        items.addAll(this.potions);
        this.items = items;
        this.numberItems = items.size();

    }

    public List<ItemStack> getItemsList(){
        return this.items;
    }

    public ItemProvider(BlockMain untitled, File file){
        this.untitled = untitled;
        this.importMaterial(file);
        this.addPotions();
        this.addGoatHorns();
        this.getItemList();
        this.untitled.getLogger().log(java.util.logging.Level.INFO, "Nombre d'items : " + String.valueOf(this.numberItems));
        this.untitled.getLogger().log(java.util.logging.Level.INFO, "Division : " + String.valueOf(ceil((double) this.items.size() /45)));
        this.untitled.getLogger().log(java.util.logging.Level.INFO, "Division inté : " + String.valueOf((int) ceil((double) this.items.size() /45)));
        this.numberInventories = (int) ceil((double) this.items.size() /45);
    }

    private void addPotions() {
        boolean extended = this.untitled.getConfig().getBoolean("potions.extended");
        boolean upgraded = this.untitled.getConfig().getBoolean("potions.upgraded");
        if(this.materials.contains("POTION")){
            if(this.untitled.getConfig().getBoolean("potions.normal")){
                this.addNormalPotion();
            }
            if(extended){
                this.addExtendedNormalPotion();
            }
            if(upgraded){
                this.addUpgradedNormalPotion();
            }
        }
        if(this.materials.contains("SPLASH_POTION")){
            if(this.untitled.getConfig().getBoolean("potions.splash")){
                this.addSplashPotion();
            }
            if(extended){
                this.addExtendedSplashPotion();
            }
            if(upgraded){
                this.addUpgradedSplashPotion();
            }
        }
        if(this.materials.contains("LINGERING_POTION")){
            if(this.untitled.getConfig().getBoolean("potions.lingering")){
                this.addLingeringPotion();
            }
            if(extended){
                this.addExtendedLingeringPotion();
            }
            if(upgraded){
                this.addUpgradedLingeringPotion();
            }
        }
        if(this.materials.contains("TIPPED_ARROW")){
            if(this.untitled.getConfig().getBoolean("potions.tipped_arrow")){
                this.addTippedArrow();
            }
            if(extended){
                this.addExtendedTippedArrow();
            }
            if(upgraded){
                this.addUpgradedTippedArrow();
            }
        }

    }

    private void addNormalPotion(){
        addPotion(Material.POTION, PotionType.SPEED, false, false);
        addPotion(Material.POTION, PotionType.SLOWNESS, false, false);
        addPotion(Material.POTION, PotionType.JUMP, false, false);
        addPotion(Material.POTION, PotionType.REGEN, false, false);
        addPotion(Material.POTION, PotionType.STRENGTH, false, false);
        addPotion(Material.POTION, PotionType.TURTLE_MASTER, false, false);
        addPotion(Material.POTION, PotionType.POISON, false, false);
        addPotion(Material.POTION, PotionType.INSTANT_HEAL, false, false);
        addPotion(Material.POTION, PotionType.FIRE_RESISTANCE, false, false);
        addPotion(Material.POTION, PotionType.INSTANT_DAMAGE, false, false);
        addPotion(Material.POTION, PotionType.FIRE_RESISTANCE, false, false);
        addPotion(Material.POTION, PotionType.WATER_BREATHING, false, false);
        addPotion(Material.POTION, PotionType.NIGHT_VISION, false, false);
        addPotion(Material.POTION, PotionType.SLOW_FALLING, false, false);
        addPotion(Material.POTION, PotionType.INVISIBILITY, false, false);
        addPotion(Material.POTION, PotionType.WEAKNESS, false, false);
        addPotion(Material.POTION, PotionType.AWKWARD, false, false);
        addPotion(Material.POTION, PotionType.THICK, false, false);
        addPotion(Material.POTION, PotionType.MUNDANE, false, false);
    }

    private void addExtendedNormalPotion(){
        addPotion(Material.POTION, PotionType.SPEED, true, false);
        addPotion(Material.POTION, PotionType.SLOWNESS, true, false);
        addPotion(Material.POTION, PotionType.JUMP, true, false);
        addPotion(Material.POTION, PotionType.REGEN, true, false);
        addPotion(Material.POTION, PotionType.STRENGTH, true, false);
        addPotion(Material.POTION, PotionType.TURTLE_MASTER, true, false);
        addPotion(Material.POTION, PotionType.POISON, true, false);
        addPotion(Material.POTION, PotionType.FIRE_RESISTANCE, true, false);
        addPotion(Material.POTION, PotionType.FIRE_RESISTANCE, true, false);
        addPotion(Material.POTION, PotionType.WATER_BREATHING, true, false);
        addPotion(Material.POTION, PotionType.NIGHT_VISION, true, false);
        addPotion(Material.POTION, PotionType.SLOW_FALLING, true, false);
        addPotion(Material.POTION, PotionType.INVISIBILITY, true, false);
        addPotion(Material.POTION, PotionType.WEAKNESS, true, false);
    }

    private void addUpgradedNormalPotion(){
        addPotion(Material.POTION, PotionType.SPEED, false, true);
        addPotion(Material.POTION, PotionType.SLOWNESS, false, true);
        addPotion(Material.POTION, PotionType.JUMP, false, true);
        addPotion(Material.POTION, PotionType.REGEN, false, true);
        addPotion(Material.POTION, PotionType.STRENGTH, false, true);
        addPotion(Material.POTION, PotionType.TURTLE_MASTER, false, true);
        addPotion(Material.POTION, PotionType.POISON, false, true);
        addPotion(Material.POTION, PotionType.INSTANT_HEAL, false, true);
        addPotion(Material.POTION, PotionType.INSTANT_DAMAGE, false, true);
        addPotion(Material.POTION, PotionType.FIRE_RESISTANCE, false, true);
    }

    private void addSplashPotion(){
        addPotion(Material.SPLASH_POTION, PotionType.SPEED, false, false);
        addPotion(Material.SPLASH_POTION, PotionType.SLOWNESS, false, false);
        addPotion(Material.SPLASH_POTION, PotionType.JUMP, false, false);
        addPotion(Material.SPLASH_POTION, PotionType.REGEN, false, false);
        addPotion(Material.SPLASH_POTION, PotionType.STRENGTH, false, false);
        addPotion(Material.SPLASH_POTION, PotionType.TURTLE_MASTER, false, false);
        addPotion(Material.SPLASH_POTION, PotionType.POISON, false, false);
        addPotion(Material.SPLASH_POTION, PotionType.INSTANT_HEAL, false, false);
        addPotion(Material.SPLASH_POTION, PotionType.FIRE_RESISTANCE, false, false);
        addPotion(Material.SPLASH_POTION, PotionType.INSTANT_DAMAGE, false, false);
        addPotion(Material.SPLASH_POTION, PotionType.FIRE_RESISTANCE, false, false);
        addPotion(Material.SPLASH_POTION, PotionType.WATER_BREATHING, false, false);
        addPotion(Material.SPLASH_POTION, PotionType.NIGHT_VISION, false, false);
        addPotion(Material.SPLASH_POTION, PotionType.SLOW_FALLING, false, false);
        addPotion(Material.SPLASH_POTION, PotionType.INVISIBILITY, false, false);
        addPotion(Material.SPLASH_POTION, PotionType.WEAKNESS, false, false);
        addPotion(Material.SPLASH_POTION, PotionType.AWKWARD, false, false);
        addPotion(Material.SPLASH_POTION, PotionType.THICK, false, false);
        addPotion(Material.SPLASH_POTION, PotionType.MUNDANE, false, false);
    }

    private void addExtendedSplashPotion(){
        addPotion(Material.SPLASH_POTION, PotionType.SPEED, true, false);
        addPotion(Material.SPLASH_POTION, PotionType.SLOWNESS, true, false);
        addPotion(Material.SPLASH_POTION, PotionType.JUMP, true, false);
        addPotion(Material.SPLASH_POTION, PotionType.REGEN, true, false);
        addPotion(Material.SPLASH_POTION, PotionType.STRENGTH, true, false);
        addPotion(Material.SPLASH_POTION, PotionType.TURTLE_MASTER, true, false);
        addPotion(Material.SPLASH_POTION, PotionType.POISON, true, false);
        addPotion(Material.SPLASH_POTION, PotionType.FIRE_RESISTANCE, true, false);
        addPotion(Material.SPLASH_POTION, PotionType.FIRE_RESISTANCE, true, false);
        addPotion(Material.SPLASH_POTION, PotionType.WATER_BREATHING, true, false);
        addPotion(Material.SPLASH_POTION, PotionType.NIGHT_VISION, true, false);
        addPotion(Material.SPLASH_POTION, PotionType.SLOW_FALLING, true, false);
        addPotion(Material.SPLASH_POTION, PotionType.INVISIBILITY, true, false);
        addPotion(Material.SPLASH_POTION, PotionType.WEAKNESS, true, false);
    }

    private void addUpgradedSplashPotion(){
        addPotion(Material.SPLASH_POTION, PotionType.SPEED, false, true);
        addPotion(Material.SPLASH_POTION, PotionType.SLOWNESS, false, true);
        addPotion(Material.SPLASH_POTION, PotionType.JUMP, false, true);
        addPotion(Material.SPLASH_POTION, PotionType.REGEN, false, true);
        addPotion(Material.SPLASH_POTION, PotionType.STRENGTH, false, true);
        addPotion(Material.SPLASH_POTION, PotionType.TURTLE_MASTER, false, true);
        addPotion(Material.SPLASH_POTION, PotionType.POISON, false, true);
        addPotion(Material.SPLASH_POTION, PotionType.INSTANT_HEAL, false, true);
        addPotion(Material.SPLASH_POTION, PotionType.INSTANT_DAMAGE, false, true);
        addPotion(Material.SPLASH_POTION, PotionType.FIRE_RESISTANCE, false, true);
    }

    private void addLingeringPotion(){
        addPotion(Material.LINGERING_POTION, PotionType.SPEED, false, false);
        addPotion(Material.LINGERING_POTION, PotionType.SLOWNESS, false, false);
        addPotion(Material.LINGERING_POTION, PotionType.JUMP, false, false);
        addPotion(Material.LINGERING_POTION, PotionType.REGEN, false, false);
        addPotion(Material.LINGERING_POTION, PotionType.STRENGTH, false, false);
        addPotion(Material.LINGERING_POTION, PotionType.TURTLE_MASTER, false, false);
        addPotion(Material.LINGERING_POTION, PotionType.POISON, false, false);
        addPotion(Material.LINGERING_POTION, PotionType.INSTANT_HEAL, false, false);
        addPotion(Material.LINGERING_POTION, PotionType.FIRE_RESISTANCE, false, false);
        addPotion(Material.LINGERING_POTION, PotionType.INSTANT_DAMAGE, false, false);
        addPotion(Material.LINGERING_POTION, PotionType.FIRE_RESISTANCE, false, false);
        addPotion(Material.LINGERING_POTION, PotionType.WATER_BREATHING, false, false);
        addPotion(Material.LINGERING_POTION, PotionType.NIGHT_VISION, false, false);
        addPotion(Material.LINGERING_POTION, PotionType.SLOW_FALLING, false, false);
        addPotion(Material.LINGERING_POTION, PotionType.INVISIBILITY, false, false);
        addPotion(Material.LINGERING_POTION, PotionType.WEAKNESS, false, false);
        addPotion(Material.LINGERING_POTION, PotionType.AWKWARD, false, false);
        addPotion(Material.LINGERING_POTION, PotionType.THICK, false, false);
        addPotion(Material.LINGERING_POTION, PotionType.MUNDANE, false, false);
    }

    private void addExtendedLingeringPotion(){
        addPotion(Material.LINGERING_POTION, PotionType.SPEED, true, false);
        addPotion(Material.LINGERING_POTION, PotionType.SLOWNESS, true, false);
        addPotion(Material.LINGERING_POTION, PotionType.JUMP, true, false);
        addPotion(Material.LINGERING_POTION, PotionType.REGEN, true, false);
        addPotion(Material.LINGERING_POTION, PotionType.STRENGTH, true, false);
        addPotion(Material.LINGERING_POTION, PotionType.TURTLE_MASTER, true, false);
        addPotion(Material.LINGERING_POTION, PotionType.POISON, true, false);
        addPotion(Material.LINGERING_POTION, PotionType.FIRE_RESISTANCE, true, false);
        addPotion(Material.LINGERING_POTION, PotionType.FIRE_RESISTANCE, true, false);
        addPotion(Material.LINGERING_POTION, PotionType.WATER_BREATHING, true, false);
        addPotion(Material.LINGERING_POTION, PotionType.NIGHT_VISION, true, false);
        addPotion(Material.LINGERING_POTION, PotionType.SLOW_FALLING, true, false);
        addPotion(Material.LINGERING_POTION, PotionType.INVISIBILITY, true, false);
        addPotion(Material.LINGERING_POTION, PotionType.WEAKNESS, true, false);
    }

    private void addUpgradedLingeringPotion(){
        addPotion(Material.LINGERING_POTION, PotionType.SPEED, false, true);
        addPotion(Material.LINGERING_POTION, PotionType.SLOWNESS, false, true);
        addPotion(Material.LINGERING_POTION, PotionType.JUMP, false, true);
        addPotion(Material.LINGERING_POTION, PotionType.REGEN, false, true);
        addPotion(Material.LINGERING_POTION, PotionType.STRENGTH, false, true);
        addPotion(Material.LINGERING_POTION, PotionType.TURTLE_MASTER, false, true);
        addPotion(Material.LINGERING_POTION, PotionType.POISON, false, true);
        addPotion(Material.LINGERING_POTION, PotionType.INSTANT_HEAL, false, true);
        addPotion(Material.LINGERING_POTION, PotionType.INSTANT_DAMAGE, false, true);
        addPotion(Material.LINGERING_POTION, PotionType.FIRE_RESISTANCE, false, true);
    }

    private void addTippedArrow(){
        addPotion(Material.TIPPED_ARROW, PotionType.SPEED, false, false);
        addPotion(Material.TIPPED_ARROW, PotionType.SLOWNESS, false, false);
        addPotion(Material.TIPPED_ARROW, PotionType.JUMP, false, false);
        addPotion(Material.TIPPED_ARROW, PotionType.REGEN, false, false);
        addPotion(Material.TIPPED_ARROW, PotionType.STRENGTH, false, false);
        addPotion(Material.TIPPED_ARROW, PotionType.TURTLE_MASTER, false, false);
        addPotion(Material.TIPPED_ARROW, PotionType.POISON, false, false);
        addPotion(Material.TIPPED_ARROW, PotionType.INSTANT_HEAL, false, false);
        addPotion(Material.TIPPED_ARROW, PotionType.FIRE_RESISTANCE, false, false);
        addPotion(Material.TIPPED_ARROW, PotionType.INSTANT_DAMAGE, false, false);
        addPotion(Material.TIPPED_ARROW, PotionType.FIRE_RESISTANCE, false, false);
        addPotion(Material.TIPPED_ARROW, PotionType.WATER_BREATHING, false, false);
        addPotion(Material.TIPPED_ARROW, PotionType.NIGHT_VISION, false, false);
        addPotion(Material.TIPPED_ARROW, PotionType.SLOW_FALLING, false, false);
        addPotion(Material.TIPPED_ARROW, PotionType.INVISIBILITY, false, false);
        addPotion(Material.TIPPED_ARROW, PotionType.WEAKNESS, false, false);
        addPotion(Material.TIPPED_ARROW, PotionType.AWKWARD, false, false);
        addPotion(Material.TIPPED_ARROW, PotionType.THICK, false, false);
        addPotion(Material.TIPPED_ARROW, PotionType.MUNDANE, false, false);
    }

    private void addExtendedTippedArrow(){
        addPotion(Material.TIPPED_ARROW, PotionType.SPEED, true, false);
        addPotion(Material.TIPPED_ARROW, PotionType.SLOWNESS, true, false);
        addPotion(Material.TIPPED_ARROW, PotionType.JUMP, true, false);
        addPotion(Material.TIPPED_ARROW, PotionType.REGEN, true, false);
        addPotion(Material.TIPPED_ARROW, PotionType.STRENGTH, true, false);
        addPotion(Material.TIPPED_ARROW, PotionType.TURTLE_MASTER, true, false);
        addPotion(Material.TIPPED_ARROW, PotionType.POISON, true, false);
        addPotion(Material.TIPPED_ARROW, PotionType.FIRE_RESISTANCE, true, false);
        addPotion(Material.TIPPED_ARROW, PotionType.FIRE_RESISTANCE, true, false);
        addPotion(Material.TIPPED_ARROW, PotionType.WATER_BREATHING, true, false);
        addPotion(Material.TIPPED_ARROW, PotionType.NIGHT_VISION, true, false);
        addPotion(Material.TIPPED_ARROW, PotionType.SLOW_FALLING, true, false);
        addPotion(Material.TIPPED_ARROW, PotionType.INVISIBILITY, true, false);
        addPotion(Material.TIPPED_ARROW, PotionType.WEAKNESS, true, false);
    }

    private void addUpgradedTippedArrow(){
        addPotion(Material.TIPPED_ARROW, PotionType.SPEED, false, true);
        addPotion(Material.TIPPED_ARROW, PotionType.SLOWNESS, false, true);
        addPotion(Material.TIPPED_ARROW, PotionType.JUMP, false, true);
        addPotion(Material.TIPPED_ARROW, PotionType.REGEN, false, true);
        addPotion(Material.TIPPED_ARROW, PotionType.STRENGTH, false, true);
        addPotion(Material.TIPPED_ARROW, PotionType.TURTLE_MASTER, false, true);
        addPotion(Material.TIPPED_ARROW, PotionType.POISON, false, true);
        addPotion(Material.TIPPED_ARROW, PotionType.INSTANT_HEAL, false, true);
        addPotion(Material.TIPPED_ARROW, PotionType.INSTANT_DAMAGE, false, true);
        addPotion(Material.TIPPED_ARROW, PotionType.FIRE_RESISTANCE, false, true);
    }


    private void addPotion(Material potionType, PotionType potionEffect, boolean extended, boolean upgraded){
        ItemStack item = new ItemStack(potionType);
        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
        potionMeta.setBasePotionData(new PotionData(potionEffect, extended, upgraded));
        item.setItemMeta(potionMeta);
        this.potions.add(item);
    }

    public void addGoatHorns(){
        if(this.materials.contains("GOAT_HORN")) {
            if(this.untitled.getConfig().getBoolean("goat_horn.specific")){
                this.materials.remove("GOAT_HORN");
                ItemStack goatHorn1 = new ItemStack(Material.GOAT_HORN);
                MusicInstrumentMeta horn1Meta = (MusicInstrumentMeta) goatHorn1.getItemMeta();
                horn1Meta.setInstrument(MusicInstrument.ADMIRE);
                goatHorn1.setItemMeta(horn1Meta);
                this.items.add(goatHorn1);

                ItemStack goatHorn2 = new ItemStack(Material.GOAT_HORN);
                MusicInstrumentMeta horn2Meta = (MusicInstrumentMeta) goatHorn2.getItemMeta();
                horn2Meta.setInstrument(MusicInstrument.CALL);
                goatHorn2.setItemMeta(horn2Meta);
                this.items.add(goatHorn2);

                ItemStack goatHorn3 = new ItemStack(Material.GOAT_HORN);
                MusicInstrumentMeta horn3Meta = (MusicInstrumentMeta) goatHorn3.getItemMeta();
                horn3Meta.setInstrument(MusicInstrument.FEEL);
                goatHorn3.setItemMeta(horn3Meta);
                this.items.add(goatHorn3);

                ItemStack goatHorn4 = new ItemStack(Material.GOAT_HORN);
                MusicInstrumentMeta horn4Meta = (MusicInstrumentMeta) goatHorn4.getItemMeta();
                horn4Meta.setInstrument(MusicInstrument.DREAM);
                goatHorn4.setItemMeta(horn4Meta);
                this.items.add(goatHorn4);

                ItemStack goatHorn5 = new ItemStack(Material.GOAT_HORN);
                MusicInstrumentMeta horn5Meta = (MusicInstrumentMeta) goatHorn5.getItemMeta();
                horn5Meta.setInstrument(MusicInstrument.PONDER);
                goatHorn5.setItemMeta(horn5Meta);
                this.items.add(goatHorn5);

                ItemStack goatHorn6 = new ItemStack(Material.GOAT_HORN);
                MusicInstrumentMeta horn6Meta = (MusicInstrumentMeta) goatHorn6.getItemMeta();
                horn6Meta.setInstrument(MusicInstrument.SEEK);
                goatHorn6.setItemMeta(horn6Meta);
                this.items.add(goatHorn6);

                ItemStack goatHorn7 = new ItemStack(Material.GOAT_HORN);
                MusicInstrumentMeta horn7Meta = (MusicInstrumentMeta) goatHorn7.getItemMeta();
                horn7Meta.setInstrument(MusicInstrument.SING);
                goatHorn7.setItemMeta(horn7Meta);
                this.items.add(goatHorn7);

                ItemStack goatHorn8 = new ItemStack(Material.GOAT_HORN);
                MusicInstrumentMeta horn8Meta = (MusicInstrumentMeta) goatHorn8.getItemMeta();
                horn8Meta.setInstrument(MusicInstrument.YEARN);
                goatHorn8.setItemMeta(horn8Meta);
                this.items.add(goatHorn8);
                }
        }
    }
}




