package fr.mrtayai.blocks.state.playing;

import fr.mrtayai.blocks.classes.Team;
import fr.mrtayai.blocks.manager.Game;

public class GameRunnable implements Runnable{

    private Game game;
    private GameManager gameManager;

    public GameRunnable(Game game, GameManager gameManager){
        this.game = game;
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        int timer = 0;

        for(Team team : this.game.getTeamManager().getTeams()){
            if(team.getPercent() == 100){
                this.gameManager.stop();
            }
        }

        timer++;
    }
}
