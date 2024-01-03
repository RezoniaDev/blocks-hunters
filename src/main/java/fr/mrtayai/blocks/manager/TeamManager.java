package fr.mrtayai.blocks.manager;

import fr.mrtayai.blocks.BlockMain;
import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.state.playing.ItemInventory;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class TeamManager {
    private List<Team> teams;
    private final Game game;

    private Map<Team, ItemInventory> teamInventories;

    public TeamManager(Game game){
        this.game = game;
        this.teams = new LinkedList<>();
        this.teamInventories = new HashMap<>();
    }

    public boolean isInATeam(BlockPlayer player){
        for(Team team : this.teams){
            if(team.isInTheTeam(player)){
                return true;
            }
        }
        return false;
    }

    public Team getTeamPlayer(BlockPlayer player){
        if(isInATeam(player)) {
            for (Team team : this.teams) {
                if (team.isInTheTeam(player)) {
                    return team;
                }
            }
        }
        return null;
    }

    public void addPlayerToTeam(BlockPlayer player, Team team) throws Exception {
        if(isInATeam(player)){
            if(getTeamPlayer(player) == team) {
                return;
            }else{
                throw new Exception("The player has already a team !");
            }
        }else{
            if(this.teams.contains(team)) {
                for (Team team1 : this.teams) {
                    if (team1.getName().equals(team.getName())){
                        team1.addPlayer(player);
                    }
                }
            }else{
                throw new Exception("The team doesn't exists");
            }
        }
    }

    public void removePlayerToTeam(BlockPlayer player) throws Exception {
        if(!isInATeam(player)){
            throw new Exception("The player isn't a team");
        }else{
            for(Team team : teams){
                if(team.isInTheTeam(player)){
                    team.removePlayer(player);
                }
            }
        }
    }

    public void addPlayerToTeam(BlockPlayer player, String teamName) throws Exception {
        if(isInATeam(player)){
            if(Objects.equals(getTeamPlayer(player).getName(), teamName)) {
                return;
            }else{
                for(Team team : teams){
                    if(team.getName().equals(getTeamPlayer(player).getName())){
                        team.removePlayer(player);
                    }
                    if(team.getName().equals(teamName)){
                        team.addPlayer(player);
                    }
                }
            }
        }else{
            for(Team team : this.teams){
                if(Objects.equals(team.getName(), teamName)){
                    team.addPlayer(player);
                    return;
                }
            }
            throw new Exception("The team doesn't exists");

        }
    }

    public List<Team> getTeams() {
        return teams;
    }


    public Team getTeam(String teamName){
        for(Team team : teams){
            if(Objects.equals(team.getName(), teamName)){
                return team;
            }
        }
        return null;
    }

    public void removeTeam(String teamName) throws Exception {
        for(Team team : teams){
            if(Objects.equals(team.getName(), teamName)){
                this.teams.remove(team);
                HandlerList.unregisterAll(this.teamInventories.get(team));
                this.teamInventories.remove(team);
            }
        }
        throw new Exception("No team with the name isn't exists");
    }

    public void addTeam(Team team) throws Exception {
        for(Team teamLoop : this.teams){
           if(Objects.equals(teamLoop.getName(), team.getName())){
                throw new Exception("A team with the name already exists");
           }
        }
        this.teams.add(team);
        ItemInventory inventory = new ItemInventory(this.game, team);
        Bukkit.getPluginManager().registerEvents(inventory, this.game.getMain());
        this.teamInventories.put(team, new ItemInventory(this.game, team));
    }

    public boolean isCollected(ItemStack item, Team teamSearch){
        for(Team team : this.teams){
            if(team.getName().equals(teamSearch.getName())){
                return team.getItemsCollected().contains(item);
            }
        }
        return false;
    }

    public void setCollected(ItemStack item, Team teamSearch){
        for(Team team : this.teams){
            if(team.getName().equals(teamSearch.getName())){
                team.setItemCollected(item);
            }
        }
    }
    public void openTeamInventory(BlockPlayer player){
        this.teamInventories.get(this.getTeamPlayer(player)).openInventory(player);
    }

}
