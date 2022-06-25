package player;

import card.ActionType;
import card.BoardCard;
import game.GameController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Player {
    protected final GameController gameController;
    protected final Boolean isSaboteur;
    protected Map<ActionType, Boolean> isBlocked;
    protected final ArrayList<BoardCard> cards;

    public Player(GameController gameController, Boolean isSaboteur, ArrayList<BoardCard> cards) {
        this.gameController = gameController;
        this.isSaboteur = isSaboteur;
        this.isBlocked = new HashMap<>(Map.of(ActionType.PICKAXE, false, ActionType.CART, false, ActionType.LAMP, false));
        this.cards = cards;
    }

    public Boolean getSaboteur() {
        return isSaboteur;
    }

    public ArrayList<BoardCard> getCards() {
        return cards;
    }

    public Map<ActionType, Boolean> getIsBlocked() {
        return isBlocked;
    }

    public void block(ActionType type) {
        isBlocked.put(type, true);
    }

    public void unblock(ActionType type) {
        isBlocked.put(type, false);
    }
}
