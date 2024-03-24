package fr.mrtayai.blocks.state.end;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

public class EndRunnable implements Runnable{

    private EndManager manager;

    private int timer = 0;
    public EndRunnable(EndManager manager){
        this.manager = manager;
    }

    @Override
    public void run() {

        if(timer % 60 == 0){
            int timerMinutes = 5 - ((int) timer/60);
            String stringMinute = timerMinutes > 1 ? " minutes" : " minute";
            Bukkit.getServer().broadcast(Component.text("[Blocks] Le serveur va redémarrer dans " +  timerMinutes + stringMinute + "." ));
        }

        if(timer == ((4*60)+30)){
            Bukkit.getServer().broadcast(Component.text("[Blocks] Le serveur va redémarrer dans 30 secondes."));
        }

        if(timer >= ((4*60)+50)){
            int secondes = timer - ((4*60)+50);
            String stringSecondes = secondes > 1 ? " secondes" : " seconde";
            Bukkit.getServer().broadcast(Component.text("[Blocks] Le serveur va redémarrer dans "+secondes+ stringSecondes + "."));
        }

        if(timer == 60*5){
            this.manager.stopEnding();
        }

        timer++;
    }
}
