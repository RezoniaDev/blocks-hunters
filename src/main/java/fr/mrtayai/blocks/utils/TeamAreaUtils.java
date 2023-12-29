package fr.mrtayai.blocks.utils;

import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.structures.Base;
import org.bukkit.Location;
import org.bukkit.World;

public class TeamAreaUtils {

    private Area area;
    private Team team;
    private Base base;
    private Location teamSpawn;

    public TeamAreaUtils(World world, int x, int y, int z, Team team){
        Location loc1 = new Location(world, x, y, z);
        Location loc2 = new Location(world, x + 12, y + 6 , z + 19);
        this.area = new Area(loc1, loc2);
        this.base = new Base(x, y, z, world);
        this.teamSpawn = new Location(world, x + 12, y + 6, z + 19);
    }

    public void build(){
        this.base.build();
    }

    public Team getTeam(){
        return this.team;
    }

}

