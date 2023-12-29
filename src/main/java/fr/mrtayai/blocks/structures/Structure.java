package fr.mrtayai.blocks.structures;

import org.bukkit.World;

abstract class Structure {

    int x;
    int y;
    int z;
    World world;
    abstract void build();

}
