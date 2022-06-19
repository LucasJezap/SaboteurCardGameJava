package player;

import board.Cell;
import card.BoardCard;

public class Move {
    protected final MoveType moveType;
    protected final BoardCard card;
    protected final Cell location;

    public Move(MoveType moveType, BoardCard card, Cell location) {
        this.moveType = moveType;
        this.card = card;
        this.location = location;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public BoardCard getCard() {
        return card;
    }
}
