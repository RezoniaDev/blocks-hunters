package fr.mrtayai.blocks.utils;

import org.bukkit.Location;

public class Area {

    private Location location1;
    private Location location2;

    public Area(Location location1, Location location2){
        this.location1 = location1;
        this.location2 = location2;
    }

    public boolean isInArea(Location locationTarget) {
        int targetX = locationTarget.getBlockX();
        int targetY = locationTarget.getBlockY();
        int targetZ = locationTarget.getBlockZ();

        int minX = Math.min(location1.getBlockX(), location2.getBlockX());
        int maxX = Math.max(location1.getBlockX(), location2.getBlockX());
        int minY = Math.min(location1.getBlockY(), location2.getBlockY());
        int maxY = Math.max(location1.getBlockY(), location2.getBlockY());
        int minZ = Math.min(location1.getBlockZ(), location2.getBlockZ());
        int maxZ = Math.max(location1.getBlockZ(), location2.getBlockZ());

        // Vérifier si locationTarget est dans la zone définie par location1 et location2
        boolean isInsideX = (targetX >= minX && targetX <= maxX);
        boolean isInsideY = (targetY >= minY && targetY <= maxY);
        boolean isInsideZ = (targetZ >= minZ && targetZ <= maxZ);

        return isInsideX && isInsideY && isInsideZ;
    }

}
