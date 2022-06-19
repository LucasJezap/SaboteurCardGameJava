package game;

import card.BoardCard;
import swing.Frame;
import player.Move;
import player.Player;

import java.io.IOException;

public class GameController {
    protected GameState gameState;
    protected Frame f;

    public GameController() {
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void initializeBoard() throws IOException, InterruptedException {
        f = new Frame("The Saboteur game", "img/background.png");
        f.show();
        f.initialize(gameState.players, gameState.board);
    }

    public void play() {
        System.out.println("The game has started.");
        int currentPlayer = 0;
        for (int round = 1; round <= 3; round++) {
            System.out.println("----- Round " + round + " -----");
            do {
                Player player = gameState.players.get(currentPlayer);
                Move move = player.play();
                BoardCard nextCard = gameState.getRandomUnusedCard();
                player.getCards().add(nextCard);
                gameState.processMove(move);

                System.out.println("Player " + (currentPlayer + 1) + " has made a move");
                currentPlayer = (currentPlayer + 1) % gameState.players.size();
            } while (!gameState.isRoundFinished());
            System.out.println("Round is finished");
            gameState.finishRound();
        }
        gameState.finishGame();
    }

    public Boolean isMoveValid(Move move) {
        return true;
    }
}
