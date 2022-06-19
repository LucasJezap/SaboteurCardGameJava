package player;

import card.ActionCard;
import card.BoardCard;
import card.CardType;
import card.PathCard;
import game.GameController;

import java.util.ArrayList;
import java.util.Scanner;

public class Human extends Player {

    public Human(GameController gameController, Boolean isSaboteur, ArrayList<BoardCard> cards) {
        super(gameController, isSaboteur, cards);
    }

    // TODO
    @Override
    public Move play() {
        System.out.println("Please select your move.");
        System.out.println("Your cards:\n{");
        for (int i = 0; i < cards.size(); i++) {
            BoardCard card = cards.get(i);
            if (card.getType() == CardType.ACTION) {
                ActionCard ac = (ActionCard) card;
                System.out.println((i + 1) + ": " + ac);
            } else {
                PathCard pc = (PathCard) card;
                System.out.println((i + 1) + ": " + pc);
            }
        }
        System.out.println("}\nPlease select your card:");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            BoardCard chosenCard = cards.get(scanner.nextInt());
            Move move = new Move(MoveType.ACTION, chosenCard, null);
            if (gameController.isMoveValid(move))
                return move;
        }
    }
}
