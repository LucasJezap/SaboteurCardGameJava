package player;

import card.BoardCard;
import game.GameController;

import java.util.ArrayList;

public class Computer extends Player {
    public Computer(GameController gameController, Boolean isSaboteur, ArrayList<BoardCard> cards) {
        super(gameController, isSaboteur, cards);
    }

    // TODO
    @Override
    public Move play() {
        System.out.println("Computer initialize()");
        return null;
    }
}
