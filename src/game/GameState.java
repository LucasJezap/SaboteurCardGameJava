package game;

import board.Board;
import card.BoardCard;
import player.Player;

import java.util.ArrayList;

public class GameState {
    protected final ArrayList<BoardCard> cards;
    protected final Board board;
    protected final ArrayList<Player> players;
    protected final Integer minerCount;
    protected final Integer saboteurCount;

    public GameState(ArrayList<BoardCard> cards, Board board, ArrayList<Player> players, Integer minerCount, Integer saboteurCount) {
        this.cards = cards;
        this.board = board;
        this.players = players;
        this.minerCount = minerCount;
        this.saboteurCount = saboteurCount;
    }

    public ArrayList<BoardCard> getCards() {
        return cards;
    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Integer getMinerCount() {
        return minerCount;
    }

    public Integer getSaboteurCount() {
        return saboteurCount;
    }
}
