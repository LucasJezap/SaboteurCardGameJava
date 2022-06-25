package game;

import card.BoardCard;
import player.Player;

import java.util.ArrayList;
import java.util.Random;

public class GameState {
    protected final ArrayList<BoardCard> cards;
    protected final ArrayList<BoardCard> unusedCards;
    protected final ArrayList<Player> players;
    protected final Integer minerCount;
    protected final Integer saboteurCount;

    public GameState(ArrayList<BoardCard> cards, ArrayList<BoardCard> unusedCards, ArrayList<Player> players, Integer minerCount, Integer saboteurCount) {
        this.cards = cards;
        this.unusedCards = unusedCards;
        this.players = players;
        this.minerCount = minerCount;
        this.saboteurCount = saboteurCount;
    }

    /**
     * Returns a random, unused card and deletes it from unusedCards list.
     */
    protected BoardCard getRandomUnusedCard() {
        if (unusedCards.size() == 0) {
            return null;
        }
        BoardCard nextCard = unusedCards.get(new Random().nextInt(unusedCards.size()));
        unusedCards.remove(nextCard);
        return nextCard;
    }

    public ArrayList<BoardCard> getCards() {
        return cards;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
