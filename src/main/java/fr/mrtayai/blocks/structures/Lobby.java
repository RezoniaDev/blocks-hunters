package fr.mrtayai.blocks.structures;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class Lobby extends Structure{

    public Lobby(int x, int y, int z, World world1){
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world1;
    }

    @Override
    public void build(){
        for(int dx = 0; dx < 9; dx++){
            for(int dz = 0; dz < 9; dz++){
                if((dx == 1) || (dx == 7)){
                    if(dz == 4){
                        Location loc = new Location(this.world, this.x + dx, this.y, this.z + dz);
                        loc.getBlock().setType(Material.GLOWSTONE);
                    }
                }else if((dx == 2) || (dx == 6)){
                    if((dz == 3) || (dz == 5)){
                        Location loc = new Location(this.world, this.x + dx, this.y, this.z + dz);
                        loc.getBlock().setType(Material.GLOWSTONE);
                    }
                } else if((dx == 3) || (dx == 5)){
                    if((dz == 2) || (dz == 6)){
                        Location loc = new Location(this.world, this.x + dx, this.y, this.z + dz);
                        loc.getBlock().setType(Material.GLOWSTONE);
                    }
                } else if(dx == 4){
                    if((dz == 1) || (dz == 7)){
                        Location loc = new Location(this.world, this.x + dx, this.y, this.z + dz);
                        loc.getBlock().setType(Material.GLOWSTONE);
                    }
                }
                Location loc = new Location(this.world, this.x + dx, this.y, this.z +dz);
                loc.getBlock().setType(Material.QUARTZ_BLOCK);
            }
        }
        for(int dy = 0; dy < 3; dy++){
            for(int dx = 0; dx < 9; dx++){
                for(int dz = 0; dz < 9; dz++){
                    if((dx == 0 && dz == 0) || (dx == 0 && dz == 8) || (dx == 8 && dz == 0) || (dx == 8 && dz == 8)){
                        new Location(this.world, this.x + dx, this.y + dy, this.z + dz).getBlock().setType(Material.QUARTZ_BLOCK);
                    }
                    if((dx == 0) || (dx == 8)){
                        new Location(this.world, this.x + dx, this.y + dy, this.z + dz).getBlock().setType(Material.GLASS);
                    }
                    if((dz == 0) || (dz == 8)){
                        new Location(this.world, this.x + dx, this.y + dy, this.z + dz).getBlock().setType(Material.GLASS);
                    }
                }
            }
        }
        int dy = 4;
        for(int dx = 0; dx < 9; dx++){
            for(int dz = 0; dz < 9; dz++) {
                new Location(this.world, this.x + dx, this.y + dy, this.z + dz).getBlock().setType(Material.QUARTZ_BLOCK);
            }
        }
    }
}
