package fr.mrtayai.blocks.structures;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

import java.util.UUID;

public class Base extends Structure{


    private UUID givenVillagerID;

    private int x;
    private int y;
    private int z;

    private UUID listVillagerID;

    private boolean enchantingTable;

    public Base(int x, int y, int z, World world, boolean enchantingTable){
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.enchantingTable = enchantingTable;
    }

    @Override
    public void build(){
        this.makeFloor(0);
        for(int i = 1; i < 5; i++){
            placeMainPiece(i);
        }
        this.makeWalls();
        this.makeFloor(6);
        this.placeLight();
        this.placeOtherThings();
        this.spawnVillagers();
        this.forceChunkLoad();
    }

    private void makeFloor(int dy){
        for(int dx = 0; dx < 13; dx++){
            for(int dz = 0; dz < 20; dz++) {
                place(dx, dy, dz, Material.BEDROCK);
            }
        }
    }


    private void makeWalls(){
        for(int dy = 1; dy < 6; dy++){
            makeWall(dy);
            if(dy < 3){
                place(6, dy, 8, Material.AIR);
                place(7, dy, 8, Material.AIR);
            }
            if(dy < 6){
                place(2, dy, 8, Material.BREWING_STAND);
                place(3, dy, 8, Material.BREWING_STAND);
            }
        }
    }
    private void makeWall(int dy){
        for(int dx = 0; dx < 13; dx++){
            for(int dz = 0; dz < 20; dz++) {
                if((dx == 0) || (dx == 12)) {
                    place(dx, dy, dz, Material.BEDROCK);
                }

                if((dz == 0) || (dz == 19)) {
                    place(dx, dy, dz, Material.BEDROCK);
                }
            }
        }
        place(1, dy, 1, Material.BEDROCK);
        for(int dx = 1; dx < 12; dx++){
            place(dx, dy, 8, Material.BEDROCK);
        }
        place(1, dy, 18, Material.BEDROCK);
        place(11, dy, 18, Material.BEDROCK);
    }

    private void place(int x, int y, int z, Material material){
        new Location(this.world, this.x + x, this.y + y, this.z + z).getBlock().setType(material);
    }

    private void place(int x, int y, int z, Material material, BlockFace facing){
        Location loc = new Location(this.world, this.x + x, this.y + y, this.z + z);
        loc.getBlock().setType(material);
        Directional blockFacing = (Directional) loc.getBlock().getBlockData();
        blockFacing.setFacing(facing);
        loc.getBlock().setBlockData(blockFacing);


    }

    private void placeDoubleChest(int x, int y, int z, BlockFace facing, boolean Xoriented){
        Location firstChest = null;
        Location secondChest = null;
        if(Xoriented) {
            firstChest = new Location(this.world, this.x + x, this.y + y, this.z + z);
            secondChest = new Location(this.world, this.x + (x + 1), this.y + y, this.z + z);
        }else {
            firstChest = new Location(this.world, this.x + x, this.y + y, this.z + z);
            secondChest = new Location(this.world, this.x + x, this.y + y, this.z + (z -1));
        }
        Block firstBlock = firstChest.getBlock();
        Block secondBlock = secondChest.getBlock();

        firstBlock.setType(Material.CHEST);
        secondBlock.setType(Material.CHEST);

        Directional firstChestDirectionnal = (Directional) firstBlock.getBlockData();
        firstChestDirectionnal.setFacing(facing);
        firstChest.getBlock().setBlockData(firstChestDirectionnal);

        Directional secondChestDirectionnal = (Directional) secondBlock.getBlockData();
        secondChestDirectionnal.setFacing(facing);
        secondChest.getBlock().setBlockData(secondChestDirectionnal);


        org.bukkit.block.data.type.Chest firstChestData = (org.bukkit.block.data.type.Chest) firstBlock.getBlockData();
        firstChestData.setType(Chest.Type.LEFT);
        firstBlock.setBlockData(firstChestData);

        org.bukkit.block.data.type.Chest secondChestData = (org.bukkit.block.data.type.Chest) secondBlock.getBlockData();
        secondChestData.setType(Chest.Type.RIGHT);
        secondBlock.setBlockData(secondChestData);

    }

    private void placeLight(){
        int dy = 5;
        for(int dx = 1; dx < 12; dx++){
            for(int dz = 1; dz < 8; dz++){
                place(dx, dy, dz, Material.GLOWSTONE);
            }
        }
        for(int dx = 2; dx < 11; dx++){
            for(int dz = 9; dz < 18; dz++){
                place(dx, dy, dz, Material.GLOWSTONE);
            }
        }
        place(1, dy, 13, Material.GLOWSTONE);
        place(11, 13, 13, Material.GLOWSTONE);
    }

    private void placeMainPiece(int dy){
        place(1, dy, 9, Material.BLAST_FURNACE,BlockFace.EAST);
        place(1, dy, 10, Material.BLAST_FURNACE, BlockFace.EAST);
        place(1, dy, 11, Material.BLAST_FURNACE, BlockFace.EAST);
        place(1, dy, 12, Material.BLAST_FURNACE, BlockFace.EAST);
        place(1, dy, 13, Material.CRAFTING_TABLE);
        place(1, dy, 14, Material.FURNACE, BlockFace.EAST);
        place(1, dy, 15, Material.FURNACE, BlockFace.EAST);
        place(1, dy, 16, Material.FURNACE, BlockFace.EAST);
        place(1, dy, 17, Material.FURNACE, BlockFace.EAST);
        /* Coffres au mur perpendiculaire aux fours */
        place(2, dy, 18, Material.CHEST, BlockFace.NORTH);
        placeDoubleChest(3, dy, 18, BlockFace.NORTH, true);
        placeDoubleChest(5, dy, 18, BlockFace.NORTH,  true);
        placeDoubleChest(7, dy, 18, BlockFace.NORTH,  true);
        placeDoubleChest(9, dy, 18, BlockFace.NORTH,  true);

        /* Coffres en face du mur de fours */
        place(11, dy, 17, Material.SMOKER, BlockFace.WEST);
        placeDoubleChest(11, dy, 16, BlockFace.WEST, false);
        place(11, dy, 14, Material.SMITHING_TABLE);
        place(11, dy, 13, Material.CRAFTING_TABLE);
        place(11, dy, 12, Material.GRINDSTONE, BlockFace.WEST);
        placeDoubleChest(11, dy, 11, BlockFace.WEST, false);
        place(11, dy, 9, Material.STONECUTTER, BlockFace.WEST);
    }

    private void placeOtherThings(){

        place(11, 1, 7, Material.ANVIL);
        place(11, 2, 7, Material.ANVIL);
        place(11, 3, 7, Material.ANVIL);
        place(11, 4, 7, Material.ANVIL);

        place(1, 1, 2, Material.OAK_PLANKS);
        place(2,1, 2, Material.OAK_PLANKS);

        place(2,1, 3, Material.OAK_PLANKS);

        place(1, 1, 4, Material.OAK_PLANKS);
        place(2,1, 4, Material.OAK_PLANKS);

        place(2,1, 5, Material.OAK_PLANKS);

        place(1, 1, 6, Material.OAK_PLANKS);
        place(2,1, 6, Material.OAK_PLANKS);

        place(1, 2, 2, Material.OAK_PLANKS);
        place(2,2, 2, Material.OAK_PLANKS);

        place(1, 2, 4, Material.OAK_PLANKS);
        place(2,2, 4, Material.OAK_PLANKS);

        place(1, 2, 6, Material.OAK_PLANKS);
        place(2,2, 6, Material.OAK_PLANKS);

        place(1, 3, 2, Material.OAK_PLANKS);
        place(2,3, 2, Material.OAK_PLANKS);

        place(2,3, 3, Material.OAK_PLANKS);

        place(1, 3, 4, Material.OAK_PLANKS);
        place(2,3, 4, Material.OAK_PLANKS);

        place(2,3, 5, Material.OAK_PLANKS);

        place(1, 3, 6, Material.OAK_PLANKS);
        place(2,3, 6, Material.OAK_PLANKS);

        if(this.enchantingTable){
            this.placeEnchantingTable();
        }
    }

    private void placeEnchantingTable(){
        place(7, 1, 1, Material.BOOKSHELF);
        place(8, 1, 1, Material.BOOKSHELF);
        place(9, 1, 1, Material.BOOKSHELF);
        place(10, 1, 1, Material.BOOKSHELF);
        place(11, 1, 1, Material.BOOKSHELF);

        place(7, 1, 2, Material.BOOKSHELF);
        place(11, 1, 2, Material.BOOKSHELF);

        place(7, 1, 3, Material.BOOKSHELF);
        place(9, 1, 3, Material.ENCHANTING_TABLE);
        place(11, 1, 3, Material.BOOKSHELF);

        place(7, 1, 4, Material.BOOKSHELF);
        place(11, 1, 4, Material.BOOKSHELF);

        place(7, 2, 1, Material.BOOKSHELF);
        place(8, 2, 1, Material.BOOKSHELF);
        place(9, 2, 1, Material.BOOKSHELF);
        place(10, 2, 1, Material.BOOKSHELF);
        place(11, 2, 1, Material.BOOKSHELF);

        place(7, 2, 2, Material.BOOKSHELF);
        place(11, 2, 2, Material.BOOKSHELF);

        place(7, 2, 3, Material.BOOKSHELF);
        place(11, 2, 3, Material.BOOKSHELF);
    }

    private void spawnVillagers(){
        Location villagerLoc1 = new Location(this.world, this.x + 1.5, this.y + 1, this.z + 3.5, -90, 0);
        Villager villager1 = (Villager) this.world.spawnEntity(villagerLoc1, EntityType.VILLAGER);
        villager1.setCustomNameVisible(true);
        villager1.setAI(false);
        villager1.setAdult();
        villager1.setVillagerType(Villager.Type.PLAINS);
        villager1.setInvulnerable(true);
        villager1.customName(Component.text("Liste des items"));
        this.listVillagerID = villager1.getUniqueId();
        Location villagerLoc2 = new Location(this.world, this.x + 1.5, this.y + 1, this.z + 5.5, -90, 0);
        Villager villager2 = (Villager) this.world.spawnEntity(villagerLoc2, EntityType.VILLAGER);
        villager2.setCustomNameVisible(true);
        villager2.setAI(false);
        villager2.setAdult();
        villager2.setVillagerType(Villager.Type.PLAINS);
        villager2.setInvulnerable(true);
        villager2.customName(Component.text("Don des items"));
        this.givenVillagerID = villager2.getUniqueId();
    }

    public UUID getGivenVillagerID() {
        return givenVillagerID;
    }

    public UUID getListVillagerID() {
        return listVillagerID;
    }

    private void forceChunkLoad(){
        Location firstLocation = new Location(Bukkit.getWorld("world"), this.x, this.y, this.z);
        int xFirstChunk = firstLocation.getChunk().getX();
        int zFirstChunk = firstLocation.getChunk().getZ();
        for(int dx = 0; dx < 2; dx++){
            for(int dz = 0; dz < 2; dz++){
                Bukkit.getServer().getWorld("world").getChunkAt(xFirstChunk + dx, zFirstChunk + dz).setForceLoaded(true);
            }
        }
    }

}
