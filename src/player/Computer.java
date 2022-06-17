package player;

import card.BoardCard;

import java.util.ArrayList;

public class Computer extends Player {
    public Computer(Boolean isSaboteur, ArrayList<BoardCard> cards) {
        super(isSaboteur, cards);
    }

    @Override
    protected void initialize() {
        System.out.println("Computer initialize()");
    }

    @Override
    protected Move play() {
        System.out.println("Computer initialize()");
        return null;
    }
}
