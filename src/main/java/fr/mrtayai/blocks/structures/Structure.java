package fr.mrtayai.blocks.structures;

import fr.mrtayai.blocks.manager.Game;
import org.bukkit.World;

abstract class Structure {

    int x;
    int y;
    int z;
    World world;
    abstract void build();

}
