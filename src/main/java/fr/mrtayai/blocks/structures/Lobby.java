package fr.mrtayai.blocks.structures;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class Lobby extends Structure{

    private Location loc1;
    private Location loc2;
    private Location locSpawn;

    public Lobby(int x, int y, int z, World world1){
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world1;
        this.loc1 = new Location(world1, x, y, z);
        this.loc2 = new Location(world1, x + 8, y + 4, z + 8);
        this.locSpawn = new Location(world1, x + 4, y + 1, z + 4);
    }

    public Location getLoc1() {
        return loc1;
    }

    public Location getLoc2() {
        return loc2;
    }

    public Location getLocSpawn() {
        return locSpawn;
    }


    @Override
    public void build() {
        for(int dx = 0; dx < 9; dx++){
            for(int dz = 0; dz < 9; dz++){
                new Location(this.world, this.x, this.y, this.z + dz).getBlock().setType(Material.QUARTZ_BLOCK);
                new Location(this.world, this.x + 8, this.y, this.z + dz).getBlock().setType(Material.QUARTZ_BLOCK);
                new Location(this.world, this.x + dx, this.y, this.z).getBlock().setType(Material.QUARTZ_BLOCK);
                new Location(this.world, this.x + dx, this.y, this.z + 8).getBlock().setType(Material.QUARTZ_BLOCK);
            }
        }

        for(int dx = 1; dx < 8; dx++){
            for(int dz = 1; dz < 8; dz++){
                if(dx % 2 == 1){
                    if(dz % 2 == 1){
                        new Location(this.world, this.x + dx, this.y, this.z + dz).getBlock().setType(Material.GLOWSTONE);
                    } else {
                        new Location(this.world, this.x + dx, this.y, this.z + dz).getBlock().setType(Material.QUARTZ_BLOCK);
                    }
                } else {
                    if(dz % 2 == 1){
                        new Location(this.world, this.x + dx, this.y, this.z + dz).getBlock().setType(Material.QUARTZ_BLOCK);
                    } else {
                        new Location(this.world, this.x + dx, this.y, this.z + dz).getBlock().setType(Material.GLOWSTONE);
                    }
                }
            }
        }

        for(int dy = 1; dy < 4; dy++){
            for(int dx = 0; dx < 9; dx++){
                for(int dz = 0; dz < 9; dz++){
                    if(dx == 0 || dx == 8){
                        if(dz == 0 || dz == 8){
                            new Location(this.world, this.x + dx, this.y + dy, this.z + dz).getBlock().setType(Material.QUARTZ_BLOCK);
                        } else {
                            new Location(this.world, this.x + dx, this.y + dy, this.z + dz).getBlock().setType(Material.GLASS);
                        }
                    }
                    if(dz == 0 || dz == 8){
                        if(dx == 0 || dx == 8){
                            new Location(this.world, this.x + dx, this.y + dy, this.z + dz).getBlock().setType(Material.QUARTZ_BLOCK);
                        } else {
                            new Location(this.world, this.x + dx, this.y + dy, this.z + dz).getBlock().setType(Material.GLASS);
                        }
                    }

                }
            }
        }
        for(int dx = 0; dx < 9; dx++){
            for(int dz = 0; dz < 9; dz++){
                new Location(this.world, this.x + dx, this.y + 4, this.z + dz).getBlock().setType(Material.QUARTZ_BLOCK);
            }
        }
    }

}
