package fr.mrtayai.blocks.state.waiting;

import fr.mrtayai.blocks.classes.GamePhase;
import fr.mrtayai.blocks.manager.Game;

public class WaitingRunnable implements Runnable{

    private Game game;
    private WaitingManager manager;

    public WaitingRunnable(Game game, WaitingManager manager){
        this.game = game;
        this.manager = manager;
    }

    @Override
    public void run() {
        int timer = 0;


        if(game.getPhase() == GamePhase.GAME){
            this.manager.stopWaitingState();
        }

        timer++;
    }
}
