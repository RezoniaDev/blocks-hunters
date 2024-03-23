package fr.mrtayai.blocks.utils;

import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.manager.Game;
import fr.mrtayai.blocks.structures.Base;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class TeamAreaUtils {

    private Area area;
    private UUID teamID;

    private Base base;
    private Location teamSpawn;

    public TeamAreaUtils( World world, int x, int y, int z, UUID teamID){
        Location loc1 = new Location(world, x, y, z);
        Location loc2 = new Location(world, x + 12, y + 6 , z + 19);
        this.area = new Area(loc1, loc2);
        this.base = new Base(x, y, z, world);
        this.teamSpawn = new Location(world, x + 6, y + 1, z + 11);
        this.teamID = teamID;
    }

    public void build(){
        this.base.build();
    }

    public UUID getTeamID(){
        return this.teamID;
    }

    public Base getBase() {
        return base;
    }

    public Area getArea() {return area;}

    public Location getTeamSpawn() {
        return teamSpawn;
    }
}

