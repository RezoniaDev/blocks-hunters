package fr.mrtayai.blocks.recipes;

import fr.mrtayai.blocks.manager.Game;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EkaliaRecipes {

    private Game game;

    private List<Recipe> recipes = new ArrayList<>();

    private List<NamespacedKey> keys = new ArrayList<>();

    public EkaliaRecipes(Game game){
        this.game = game;
        this.addRecipes();
        this.registerRecipe();
    }

    public void discoverRecipes(Player player){
        for(NamespacedKey key : this.keys){
            player.discoverRecipe(key);
        }
    }

    private void registerRecipe(){
        for(Recipe recipe : this.recipes){
            Bukkit.addRecipe(recipe);
        }
    }

    private void addRecipes(){
        this.addElytra();
        this.addCreeperHead();
        this.addTridentRecipe();
        this.addGoldenAppleRecipe();
        this.addEchoShard();
        this.addHangingRoots();
        this.addTotemOfUndying();
        this.addPowderBucketRecipe();
        this.addDeepSlateOreRecipe();
        this.addHeartOfSea();
        this.addOxidisedCopper();
        this.addZombieHead();
        this.addPiglinHead();
        this.addSkeletonSkull();
        this.addSaddle();
        this.addBottleOEnchanting();
        this.addHorseArmor();
    }

    private void addTridentRecipe(){
        NamespacedKey key = new NamespacedKey(this.game.getMain(), "trident");
        keys.add(key);
        ItemStack trident = new ItemStack(Material.TRIDENT);
        ShapedRecipe recipe = new ShapedRecipe(key, trident);

        recipe.shape(
                " DN",
                " BD",
                "B  "
        );

        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('B', Material.BLAZE_ROD);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);

        this.recipes.add(recipe);
    }

    private void addTotemOfUndying(){
        NamespacedKey key = new NamespacedKey(this.game.getMain(), "totem_of_undying");
        keys.add(key);
        ItemStack totemOfUndying = new ItemStack(Material.TOTEM_OF_UNDYING);
        ShapedRecipe recipe = new ShapedRecipe(key, totemOfUndying);

        recipe.shape(
                "EGE",
                " D ",
                "DGD"
        );

        recipe.setIngredient('E', Material.EMERALD);
        recipe.setIngredient('G', Material.GOLD_BLOCK);
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);

        recipes.add(recipe);
    }

    private void addGoldenAppleRecipe(){
        NamespacedKey key = new NamespacedKey(this.game.getMain(), "enchanted_golden_apple");
        keys.add(key);
        ItemStack enchantedGoldenApple = new ItemStack(Material.GOLDEN_APPLE);
        ShapedRecipe recipe = new ShapedRecipe(key, enchantedGoldenApple);

        recipe.shape(
                "GGG",
                "GAG",
                "GGG"
        );

        recipe.setIngredient('G', Material.GOLD_BLOCK);
        recipe.setIngredient('A', Material.APPLE);

        this.recipes.add(recipe);
    }

    private void addPowderBucketRecipe(){
        NamespacedKey key = new NamespacedKey(this.game.getMain(), "powder_snow_bucket");
        keys.add(key);
        ItemStack enchantedGoldenApple = new ItemStack(Material.POWDER_SNOW_BUCKET);
        ShapedRecipe recipe = new ShapedRecipe(key, enchantedGoldenApple);

        recipe.shape(
                "III",
                "ISI",
                "III"
        );

        recipe.setIngredient('I', Material.IRON_BLOCK);
        recipe.setIngredient('S', Material.SNOW_BLOCK);

        this.recipes.add(recipe);
    }

    private void addDeepSlateOreRecipe(){
        List<Material> materials = List.of(Material.COAL_BLOCK, Material.RAW_IRON_BLOCK, Material.RAW_COPPER_BLOCK, Material.RAW_GOLD_BLOCK, Material.EMERALD_BLOCK, Material.DIAMOND_BLOCK, Material.REDSTONE_BLOCK, Material.LAPIS_BLOCK);
        for(Material material : materials){
            NamespacedKey key = new NamespacedKey(this.game.getMain(), "deepslate_"+this.getElementString(material)+"_ore");
            keys.add(key);
            ItemStack deepslateOre = new ItemStack(this.getDeepslate(material));
            ShapedRecipe recipe = new ShapedRecipe(key, deepslateOre);

            recipe.shape(
                    "EDE",
                    "DED",
                    "EDE"
            );
            recipe.setIngredient('E', material);
            recipe.setIngredient('D', Material.DEEPSLATE);

            this.recipes.add(recipe);
        }
    }

    private void addHeartOfSea(){
        for(Material material : List.of(Material.PUFFERFISH_BUCKET, Material.COD_BUCKET, Material.SALMON_BUCKET, Material.TADPOLE_BUCKET, Material.TROPICAL_FISH_BUCKET)){
            NamespacedKey key = new NamespacedKey(this.game.getMain(), "heart_of_the_sea");
            keys.add(key);
            ItemStack heartOfTheSea = new ItemStack(Material.HEART_OF_THE_SEA);
            ShapedRecipe recipe = new ShapedRecipe(key, heartOfTheSea);

            recipe.shape(
                    "KKK",
                    "NBN",
                    "KKK"
            );

            recipe.setIngredient('K', Material.DRIED_KELP_BLOCK);
            recipe.setIngredient('N', Material.NAUTILUS_SHELL);
            recipe.setIngredient('B', material);
        }
    }

    private void addElytra(){
        NamespacedKey key = new NamespacedKey(this.game.getMain(), "elytra");
        keys.add(key);
        ItemStack heartOfTheSea = new ItemStack(Material.ELYTRA);
        ShapedRecipe recipe = new ShapedRecipe(key, heartOfTheSea);

        recipe.shape(
                "PCP",
                "FPF",
                "P P"
        );

        recipe.setIngredient('P', Material.PHANTOM_MEMBRANE);
        recipe.setIngredient('C', Material.LEATHER);
        recipe.setIngredient('F', Material.FEATHER);

        this.recipes.add(recipe);
    }

    private void addOxidisedCopper(){
        List<Material> elements = List.of(Material.COPPER_BLOCK, Material.EXPOSED_COPPER, Material.WEATHERED_COPPER, Material.OXIDIZED_COPPER);
        for(int i = 0; i < (elements.size() - 1); i++){
            Material element = elements.get(i);
            NamespacedKey key = new NamespacedKey(this.game.getMain(), elements.get(i+1).toString().toLowerCase(Locale.FRENCH));
            keys.add(key);
            ItemStack copper = new ItemStack(elements.get(i+1), 8);
            ShapedRecipe recipe = new ShapedRecipe(key, copper);

            recipe.shape(
                    "EEE",
                    "EBE",
                    "EEE"
            );

            recipe.setIngredient('E', element);
            recipe.setIngredient('B', Material.WATER_BUCKET);

            this.recipes.add(recipe);
        }
    }

    private void addHangingRoots(){
        NamespacedKey key = new NamespacedKey(this.game.getMain(), "hanging_roots");
        keys.add(key);
        ItemStack hangingRoots = new ItemStack(Material.HANGING_ROOTS);
        ShapedRecipe recipe = new ShapedRecipe(key, hangingRoots);

        recipe.shape(
                "OOO",
                "MLM",
                " M "
        );

        recipe.setIngredient('O', Material.OAK_WOOD);
        recipe.setIngredient('M', Material.MOSS_BLOCK);
        recipe.setIngredient('L', Material.BIG_DRIPLEAF);

        this.recipes.add(recipe);
    }

    private void addCreeperHead(){
        NamespacedKey key = new NamespacedKey(this.game.getMain(), "creeper_head");
        keys.add(key);
        ItemStack hangingRoots = new ItemStack(Material.CREEPER_HEAD);
        ShapedRecipe recipe = new ShapedRecipe(key, hangingRoots);

        recipe.shape(
                "PTP",
                "TCT",
                "PTP"
        );

        recipe.setIngredient('P', Material.GUNPOWDER);
        recipe.setIngredient('T', Material.TNT);
        recipe.setIngredient('C', Material.CARVED_PUMPKIN);

        this.recipes.add(recipe);
    }

    private void addZombieHead(){
        NamespacedKey key = new NamespacedKey(this.game.getMain(), "zombie_head");
        keys.add(key);
        ItemStack hangingRoots = new ItemStack(Material.ZOMBIE_HEAD);
        ShapedRecipe recipe = new ShapedRecipe(key, hangingRoots);

        recipe.shape(
                "RRR",
                "RPR",
                "RRR"
        );

        recipe.setIngredient('R', Material.ROTTEN_FLESH);
        recipe.setIngredient('P', Material.CARVED_PUMPKIN);

        this.recipes.add(recipe);
    }

    private void addSkeletonSkull(){
        NamespacedKey key = new NamespacedKey(this.game.getMain(), "skeleton_skull");
        keys.add(key);
        ItemStack hangingRoots = new ItemStack(Material.SKELETON_SKULL);
        ShapedRecipe recipe = new ShapedRecipe(key, hangingRoots);

        recipe.shape(
                "BBB",
                "BPB",
                "BBB"
        );

        recipe.setIngredient('B', Material.BONE_BLOCK);
        recipe.setIngredient('P', Material.CARVED_PUMPKIN);

        this.recipes.add(recipe);
    }

    private void addPiglinHead(){
        NamespacedKey key = new NamespacedKey(this.game.getMain(), "piglin_head");
        keys.add(key);
        ItemStack hangingRoots = new ItemStack(Material.PIGLIN_HEAD);
        ShapedRecipe recipe = new ShapedRecipe(key, hangingRoots);

        recipe.shape(
                "CWC",
                "WPW",
                "CWC"
        );

        recipe.setIngredient('C', Material.WARPED_FUNGUS);
        recipe.setIngredient('W', Material.CRIMSON_FUNGUS);
        recipe.setIngredient('P', Material.CARVED_PUMPKIN);

        this.recipes.add(recipe);
    }

    private void addEchoShard(){
        NamespacedKey key = new NamespacedKey(this.game.getMain(), "echo_shard");
        keys.add(key);
        ItemStack hangingRoots = new ItemStack(Material.ECHO_SHARD);
        ShapedRecipe recipe = new ShapedRecipe(key, hangingRoots);

        recipe.shape(
                "SSS",
                "SAS",
                "SSS"
        );

        recipe.setIngredient('S', Material.SCULK);
        recipe.setIngredient('A', Material.SCULK_SENSOR);

        this.recipes.add(recipe);
    }

    private void addHorseArmor(){
        List<String> strings = List.of("LEATHER", "IRON", "GOLDEN", "DIAMOND");
        List<Material> materials = List.of(Material.LEATHER, Material.IRON_INGOT, Material.GOLD_INGOT, Material.DIAMOND);
        for(int i = 0; i < strings.size(); i++){
            NamespacedKey key = new NamespacedKey(this.game.getMain(), strings.get(i)+"_horse_armor".toUpperCase(Locale.FRENCH));
            keys.add(key);
            ItemStack hangingRoots = new ItemStack(Material.valueOf(strings.get(i)+"_horse_armor".toUpperCase(Locale.FRENCH)));
            Material material = materials.get(i);
            ShapedRecipe recipe = new ShapedRecipe(key, hangingRoots);recipe.shape(
                    "E E",
                    "EIE",
                    "E E"
            );

            recipe.setIngredient('E', material);
            recipe.setIngredient('I', Material.LEATHER);

            this.recipes.add(recipe);
        }
    }

    private void addSaddle(){
        NamespacedKey key = new NamespacedKey(this.game.getMain(), "saddle");
        keys.add(key);
        ItemStack hangingRoots = new ItemStack(Material.SADDLE);
        ShapedRecipe recipe = new ShapedRecipe(key, hangingRoots);

        recipe.shape(
                "LLL",
                "LIL",
                "I I"
        );

        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('L', Material.LEATHER);

        this.recipes.add(recipe);
    }

    private void addBottleOEnchanting(){
        NamespacedKey key = new NamespacedKey(this.game.getMain(), "experience_bottle");
        keys.add(key);
        ItemStack hangingRoots = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ShapedRecipe recipe = new ShapedRecipe(key, hangingRoots);

        recipe.shape(
                "RCO",
                "SBP",
                "EAS"
        );

        recipe.setIngredient('R', Material.ROTTEN_FLESH);
        recipe.setIngredient('C', Material.LEATHER);
        recipe.setIngredient('O', Material.BONE);

        recipe.setIngredient('S', Material.STRING);
        recipe.setIngredient('B', Material.GLASS_BOTTLE);
        recipe.setIngredient('P', Material.GUNPOWDER);

        recipe.setIngredient('E', Material.ENDER_PEARL);
        recipe.setIngredient('A', Material.BLAZE_ROD);
        recipe.setIngredient('S', Material.SLIME_BALL);

        this.recipes.add(recipe);
    }

    private String getElementString(Material material){
        if(material.equals(Material.COAL_BLOCK)){
            return "coal";
        }else if(material.equals(Material.RAW_IRON_BLOCK)){
            return "iron";
        }else if(material.equals(Material.RAW_COPPER_BLOCK)){
            return "copper";
        }else if(material.equals(Material.RAW_GOLD_BLOCK)){
            return "gold";
        }else if(material.equals(Material.EMERALD_BLOCK)){
            return "emerald";
        }else if(material.equals(Material.DIAMOND_BLOCK)){
            return "diamond";
        }else if(material.equals(Material.REDSTONE_BLOCK)){
            return "redstone";
        }else if(material.equals(Material.LAPIS_BLOCK)){
            return "lapis";
        }
        return "";
    }

    private Material getDeepslate(Material material){
        if(material.equals(Material.COAL_BLOCK)){
            return Material.DEEPSLATE_COAL_ORE;
        }else if(material.equals(Material.RAW_IRON_BLOCK)){
            return Material.DEEPSLATE_IRON_ORE;
        }else if(material.equals(Material.RAW_COPPER_BLOCK)){
            return Material.DEEPSLATE_COPPER_ORE;
        }else if(material.equals(Material.RAW_GOLD_BLOCK)){
            return Material.DEEPSLATE_GOLD_ORE;
        }else if(material.equals(Material.EMERALD_BLOCK)){
            return Material.DEEPSLATE_EMERALD_ORE;
        }else if(material.equals(Material.DIAMOND_BLOCK)){
            return Material.DEEPSLATE_DIAMOND_ORE;
        }else if(material.equals(Material.REDSTONE_BLOCK)){
            return Material.DEEPSLATE_REDSTONE_ORE;
        }else if(material.equals(Material.LAPIS_BLOCK)){
            return Material.DEEPSLATE_LAPIS_ORE;
        }
        return Material.AIR;
    }

}
