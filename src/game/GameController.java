package game;

import card.ActionType;
import player.Move;
import swing.Frame;

import java.io.IOException;

public class GameController {
    protected GameState gameState;
    protected int currentPlayer;
    protected Frame f;

    public GameController() {
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void initializeBoard() throws IOException {
        f = new Frame(this, "The Saboteur game", "img/background.png");
        f.show();
        f.initialize(gameState.players, gameState.board, gameState.cards);
        this.currentPlayer = 0;
    }

    public void changePlayer() throws IOException {

        currentPlayer = (currentPlayer + 1) % gameState.players.size();
        f.nextPlayer(gameState.players.get(currentPlayer), currentPlayer + 1);
    }

    public Boolean isMoveValid(Move move) {
        return true;
    }

    public void changePlayerBlock(ActionType type, int index) {
        if (type.toString().contains("_BLOCK")) {
            gameState.players.get(index).block(ActionType.getNoBlockType(type));
        } else {
            gameState.players.get(index).unblock(ActionType.getNoBlockType(type));
        }
    }

    public boolean isPlayerBlocked() {
        return gameState.players.get(currentPlayer).getIsBlocked().containsValue(true);
    }

    public boolean isPlayerSaboteur() {
        return gameState.players.get(currentPlayer).getSaboteur();
    }
}
