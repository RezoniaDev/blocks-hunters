package fr.mrtayai.blocks.state.end;

public class EndRunnable implements Runnable{

    private EndManager manager;

    public EndRunnable(EndManager manager){
        this.manager = manager;
    }

    @Override
    public void run() {
        int timer = 0;

        if(timer == 60*5){
            this.manager.stopEnding();
        }

        timer++;
    }
}
