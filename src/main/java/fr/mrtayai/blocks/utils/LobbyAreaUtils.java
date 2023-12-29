package fr.mrtayai.blocks.utils;

import org.bukkit.Location;

public class LobbyAreaUtils {

    private Location lobbySpawnLoc;
    private Area area;

    public LobbyAreaUtils(Location loc1, Location loc2, Location locSpawn){
        this.area = new Area(loc1, loc2);
        this.lobbySpawnLoc = locSpawn;
    }

    public Area getArea() {
        return area;
    }

    public Location getLobbySpawnLoc() {
        return lobbySpawnLoc;
    }
}
