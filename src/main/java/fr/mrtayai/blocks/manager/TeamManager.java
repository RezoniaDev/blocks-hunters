package fr.mrtayai.blocks.manager;

import fr.mrtayai.blocks.classes.BlockPlayer;
import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.gui.TeamInventory;
import fr.mrtayai.blocks.state.playing.ItemInventory;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class TeamManager {
    private List<Team> teams;
    private final Game game;

    private Map<Team, TeamInventory> teamInventories;

    private Map<Double, UUID> scorePerTeam = new HashMap<Double, UUID>();

    public TeamManager(Game game){
        this.game = game;
        this.teams = new ArrayList<>();
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
                        changeColorInTab(team1, player.getPlayer());
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
                    changeColorInTab(null, player.getPlayer());
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
                    Team teamPlayer = getTeamPlayer(player);
                    if(teamPlayer != null) {
                        if (team.getName().equals(teamPlayer.getName())) {
                            team.removePlayer(player);
                            changeColorInTab(null, player.getPlayer());
                        }
                    }else{
                        if(team.getName().equals(teamName)) {
                            team.addPlayer(player);
                            changeColorInTab(team, player.getPlayer());
                        }
                    }
                }
            }
        }else{
            for(Team team : this.teams){
                if(Objects.equals(team.getName(), teamName)){
                    team.addPlayer(player);
                    changeColorInTab(team, player.getPlayer());
                    return;
                }
            }
            throw new Exception("The team doesn't exists");

        }
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
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
    }

    public void addTeamInventory(Team team, TeamInventory inventory){
        this.teamInventories.put(team, inventory);
    }

    public boolean isCollected(ItemStack item, Team teamSearch){
        for(Team team : this.teams){
            if(team.getName().equals(teamSearch.getName())){
                Bukkit.getLogger().info("Team " + teamSearch.getName());
                Bukkit.getLogger().info("Item " + item.toString());
                return team.getItemsCollected().contains(item);
            }
        }
        return false;
    }

    public void setCollected(ItemStack item, Team teamSearch){
        for(Team team : this.teams){
            if(team.getName().equals(teamSearch.getName())){
                team.setItemCollected(item);
                this.teamInventories.get(team).setItemCollected(item);
            }
        }
    }
    public TeamInventory getTeamInventory(Team team){
        return this.teamInventories.get(team);
    }

    public void removeEmptyTeams(){
        this.teams.removeIf(team -> team.getPlayers().isEmpty());
    }

    public boolean hasTeam(Team team){
        return this.teams.contains(team);
    }

    public boolean hasTeam(UUID uuid){
        for(Team team : this.teams){
            for(BlockPlayer player : team.getPlayers()){
                if(player.getPlayer().getUniqueId().equals(uuid)){
                    return true;
                }
            }
        }
        return false;
    }

    public Team getTeam(UUID uuid){
        for(Team team : this.teams){
            if(team.getTeamID().equals(uuid)){
                return team;
            }
        }
        return null;
    }

    private void changeColorInTab(Team team, Player player){
        if(team == null){
            player.playerListName(Component.text(player.getName()).color(NamedTextColor.WHITE));
        }else {
            player.playerListName(Component.text(player.getName()).color(team.getTextColor()));
        }
    }

    public void registerTeamScore(){
        for(Team team : this.teams){
            this.scorePerTeam.put(team.getPercent(), team.getTeamID());
        }
        List<Double> employeeByKey = new ArrayList<>(this.scorePerTeam.keySet());
        Collections.sort(employeeByKey, Collections.reverseOrder());
        Map<Double, UUID> sortedScore = new HashMap<>();
        for(Double score : employeeByKey){
            sortedScore.put(score, this.scorePerTeam.get(score));
        }
        this.scorePerTeam = sortedScore;
    }

    public Map<Double, UUID> getScorePerTeam(){
        return this.scorePerTeam;
    }



}
