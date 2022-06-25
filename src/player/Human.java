package player;

import card.BoardCard;
import game.GameController;

import java.util.ArrayList;

public class Human extends Player {

    public Human(GameController gameController, Boolean isSaboteur, ArrayList<BoardCard> cards) {
        super(gameController, isSaboteur, cards);
    }
}
