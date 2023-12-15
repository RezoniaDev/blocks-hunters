package fr.mrtayai.blocks.teams;

import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.util.List;

public class Team {

    private String name;
    private Color color;
    private List<Player> players;

    public Team(String name, Color color){
        this.name = name;
        this.color = color;
    }

    public void addPlayer(Player player){
        if(this.players.contains(player)){
            return;
        }
        this.players.add(player);
    }

}
