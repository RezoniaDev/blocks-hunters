package fr.mrtayai.blocks.utils;

import fr.mrtayai.blocks.structures.Lobby;
import org.bukkit.Location;

public class LobbyAreaUtils {

    private Location lobbySpawnLoc;
    private Area area;

    public LobbyAreaUtils(Lobby lobby){

        this.area = new Area(lobby.getLoc1(), lobby.getLoc2());
        this.lobbySpawnLoc = lobby.getLocSpawn();
    }

    public Area getArea() {
        return area;
    }

    public Location getLobbySpawnLoc() {
        return lobbySpawnLoc;
    }
}
