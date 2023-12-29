package fr.mrtayai.blocks.state.playing;

import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.manager.Game;

public class GameRunnable implements Runnable{

    private Game game;

    public GameRunnable(Game game){
        this.game = game;
    }

    @Override
    public void run() {
        int timer = 0;

        for(Team team : this.game.getTeamManager().getTeams()){
            if(team.getPercent() == 100){
                stopGame();
            }
        }

        timer++;
    }
}
