package player;

import card.BoardCard;

import java.util.ArrayList;

public class Human extends Player {

    public Human(Boolean isSaboteur, ArrayList<BoardCard> cards) {
        super(isSaboteur, cards);
    }

    @Override
    protected void initialize() {
        System.out.println("Human initialize()");
    }

    @Override
    protected Move play() {
        System.out.println("Human play()");
        return null;
    }
}
