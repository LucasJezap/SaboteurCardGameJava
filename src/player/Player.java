package player;

import card.BoardCard;

import java.util.ArrayList;

public abstract class Player {
    protected final Boolean isSaboteur;
    protected final Boolean isBlocked;
    protected final ArrayList<BoardCard> cards;
    protected final Integer goldCount;

    public Player(Boolean isSaboteur, ArrayList<BoardCard> cards) {
        this.isSaboteur = isSaboteur;
        this.isBlocked = false;
        this.cards = cards;
        this.goldCount = 0;
    }

    public Boolean getSaboteur() {
        return isSaboteur;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public ArrayList<BoardCard> getCards() {
        return cards;
    }

    public Integer getGoldCount() {
        return goldCount;
    }

    protected abstract void initialize();

    protected abstract Move play();
}
