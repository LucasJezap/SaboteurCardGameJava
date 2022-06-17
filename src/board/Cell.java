package board;

import card.BoardCard;

public class Cell {
    protected final Integer x;
    protected final Integer y;
    protected BoardCard card;

    public Cell(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.card = null;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public BoardCard getCard() {
        return card;
    }

    public void setCard(BoardCard card) {
        this.card = card;
    }
}
