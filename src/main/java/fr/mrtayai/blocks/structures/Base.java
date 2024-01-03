package fr.mrtayai.blocks.structures;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Base extends Structure{


    private List<UUID> villagersUUID;
    public Base(int x, int y, int z, World world){
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.villagersUUID = new ArrayList<>();
    }

    @Override
    public void build(){
        makeFloor(0);
        for(int dy = 1; dy < 7; dy++){
            makeWalls(dy);
            if(dy < 6){
                placeMainPiece(dy);
            }
        }
        makeFloor(6);
        placeLight();
        placeOtherThings();
        spawnVillagers();

    }

    private void makeFloor(int dy){
        for(int dx = 0; dx < 13; dx++){
            for(int dz = 0; dz < 20; dz++) {
                place(dx, dy, dz, Material.BEDROCK);
            }
        }
    }

    private void makeWalls(int dy){
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
        place(1, dy, 1, Material.BEDROCK);
        place(1, dy, 12, Material.BEDROCK);
        place(1, dy, 13, Material.BEDROCK);
        place(2, dy, 13, Material.BEDROCK);
        place(3, dy, 13, Material.BEDROCK);
        place(10, dy, 13, Material.BEDROCK);
        place(11, dy, 13, Material.BEDROCK);

    }

    private void place(int x, int y, int z, Material material){
        new Location(this.world, this.x + x, this.y + y, this.z + z).getBlock().setType(material);
    }

    private void placeLight(){
        for(int dx = 1; dx < 12; dx++){
            for(int dz = 1; dz < 7; dz++){
                place(dx, 5, dz, Material.GLOWSTONE);
            }
        }

        for(int dx = 2; dx < 11; dx++){
            for(int dz = 8; dz < 18; dz++){
                place(dx, 5, dz, Material.GLOWSTONE);
            }
        }
    }

    private void placeMainPiece(int dy){
        /*   1  2  3  4  5  6  7  8  9  10  11
           8 X  A  A  X  X  X  X  X  X   X   X
           9 B  X  X  X  X  X  X  X  X   X   C
          10 B  X  X  X  X  X  X  X  X   X   C
          11 B  X  X  X  X  X  X  X  X   X   C
          12 B  X  X  X  X  X  X  X  X   X   C
          13 E  X  X  X  X  X  X  X  X   X   E
          14 F  X  X  X  X  X  X  X  X   X   C
          15 F  X  X  X  X  X  X  X  X   X   C
          16 F  X  X  X  X  X  X  X  X   X   C
          17 F  X  X  X  X  X  X  X  X   X   C
          18 X  C  C  C  C  E  C  C  C   C   X
         */
        place(2, dy, 8, Material.BREWING_STAND);
        place(3, dy, 8, Material.BREWING_STAND);
        place(1, dy, 9, Material.BLAST_FURNACE);
        place(1, dy, 10, Material.BLAST_FURNACE);
        place(1, dy, 11, Material.BLAST_FURNACE);
        place(1, dy, 12, Material.BLAST_FURNACE);
        place(1, dy, 13, Material.CRAFTING_TABLE);
        place(1, dy, 14, Material.FURNACE);
        place(1, dy, 15, Material.FURNACE);
        place(1, dy, 16, Material.FURNACE);
        place(1, dy, 17, Material.FURNACE);
        place(2, dy, 18, Material.CHEST);
        place(3, dy, 18, Material.CHEST);
        place(4, dy, 18, Material.CHEST);
        place(5, dy, 18, Material.CHEST);
        place(6, dy, 18, Material.CHEST);
        place(7, dy, 18, Material.CHEST);
        place(8, dy, 18, Material.CHEST);
        place(9, dy, 18, Material.CHEST);
        place(10, dy, 18, Material.CHEST);
        place(11, dy, 9, Material.CHEST);
        place(11, dy, 10, Material.CHEST);
        place(11, dy, 11, Material.CHEST);
        place(11, dy, 12, Material.CHEST);
        place(11, dy, 13, Material.CRAFTING_TABLE);
        place(11, dy, 14, Material.CHEST);
        place(11, dy, 15, Material.CHEST);
        place(11, dy, 16, Material.CHEST);
        place(11, dy, 17, Material.CHEST);
    }

    private void placeOtherThings(){
        /*
             1  2  3  4  5  6  7  8  9  10  11
           1 X  X  X  X  X  X  B  B  B  B   B
           2 P  O  X  X  X  X  B  X  X  X   B
           3 X  P  X  X  X  X  B  X  E  X   B
           4 P  O  X  X  X  X  B  X  X  X   B
           5 X  P  X  X  X  X  X  X  X  X   X
           6 P  O  X  X  X  X  X  X  X  X   X
         */
        place(7, 1, 1, Material.BOOKSHELF);
        place(8, 1, 1, Material.BOOKSHELF);
        place(9, 1, 1, Material.BOOKSHELF);
        place(10, 1, 1, Material.BOOKSHELF);
        place(11, 1, 1, Material.BOOKSHELF);

        place(1, 1, 2, Material.OAK_PLANKS);
        place(2,1, 2, Material.OAK_PLANKS);
        place(7, 1, 2, Material.BOOKSHELF);
        place(11, 1, 2, Material.BOOKSHELF);

        place(2,1, 3, Material.OAK_PLANKS);
        place(7, 1, 3, Material.BOOKSHELF);
        place(9, 1, 3, Material.ENCHANTING_TABLE);
        place(11, 1, 3, Material.BOOKSHELF);

        place(1, 1, 4, Material.OAK_PLANKS);
        place(2,1, 4, Material.OAK_PLANKS);
        place(7, 1, 4, Material.BOOKSHELF);
        place(11, 1, 4, Material.BOOKSHELF);

        place(2,1, 5, Material.OAK_PLANKS);

        place(1, 1, 6, Material.OAK_PLANKS);
        place(2,1, 6, Material.OAK_PLANKS);

        place(7, 2, 1, Material.BOOKSHELF);
        place(8, 2, 1, Material.BOOKSHELF);
        place(9, 2, 1, Material.BOOKSHELF);
        place(10, 2, 1, Material.BOOKSHELF);
        place(11, 2, 1, Material.BOOKSHELF);

        place(1, 2, 2, Material.OAK_PLANKS);
        place(2,2, 2, Material.OAK_PLANKS);
        place(7, 2, 2, Material.BOOKSHELF);
        place(11, 2, 2, Material.BOOKSHELF);

        place(2,2, 3, Material.OAK_PLANKS);
        place(7, 2, 3, Material.BOOKSHELF);
        place(11, 2, 3, Material.BOOKSHELF);

        place(1, 2, 4, Material.OAK_PLANKS);
        place(2,2, 4, Material.OAK_PLANKS);

        place(2,2, 5, Material.OAK_PLANKS);

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
    }

    private void spawnVillagers(){
        Location villagerLoc1 = new Location(this.world, this.x + 1, this.y + 1, this.z + 3);
        Villager villager1 = (Villager) this.world.spawnEntity(villagerLoc1, EntityType.VILLAGER);
        villager1.setCustomNameVisible(true);
        villager1.setAI(false);
        villager1.setAdult();
        villager1.setVillagerType(Villager.Type.PLAINS);
        villager1.setInvulnerable(true);
        villager1.customName(Component.text("Liste des items"));
        this.villagersUUID.add(villager1.getUniqueId());
        Location villagerLoc2 = new Location(this.world, this.x + 1, this.y + 1, this.z + 3);
        Villager villager2 = (Villager) this.world.spawnEntity(villagerLoc1, EntityType.VILLAGER);
        villager2.setCustomNameVisible(true);
        villager2.setAI(false);
        villager2.setAdult();
        villager2.setVillagerType(Villager.Type.PLAINS);
        villager2.setInvulnerable(true);
        villager2.customName(Component.text("Don des items"));
        this.villagersUUID.add(villager2.getUniqueId());
    }

    public List<UUID> getVillagersUUID() {
        return villagersUUID;
    }
}
