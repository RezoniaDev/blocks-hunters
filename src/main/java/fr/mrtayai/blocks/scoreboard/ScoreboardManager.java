package fr.mrtayai.blocks.scoreboard;

import fr.mrmicky.fastboard.adventure.FastBoard;
import fr.mrtayai.blocks.manager.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager {

    private Game game;

    private Map<UUID, FastBoard> boards = new HashMap<>();

    public ScoreboardManager(Game game){
        this.game = game;
    }

    public Map<UUID, FastBoard> getBoards() {
        return boards;
    }
}
