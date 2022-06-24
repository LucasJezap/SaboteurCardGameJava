package game;

import card.ActionType;
import card.BoardCard;
import card.PathCard;
import player.Move;
import player.Player;
import swing.Frame;

import javax.swing.*;
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
        JOptionPane.showMessageDialog(f.getF(),
                """
                        Welcome to Saboteur game!
                        This version has two limitations:
                        1. There's only one round
                        2. You can't rotate cards
                        Have fun!""");
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

    public void addNewCardToPlayer(BoardCard card) {
        Player player = gameState.players.get(currentPlayer);
        player.getCards().remove(card);
        player.getCards().add(gameState.getRandomUnusedCard());
        areCardsFinished();
    }

    public void checkTarget(int num) {
        PathCard pathCard = (PathCard) gameState.getCards().get(num);
        if (num == 1) {
            f.changePanelImage("card_1_9", pathCard.getImage());
        } else if (num == 2) {
            f.changePanelImage("card_3_9", pathCard.getImage());
        } else {
            f.changePanelImage("card_5_9", pathCard.getImage());
        }
        pathCard.setGold(false);

        if (pathCard.getTreasure()) {
            endGame("Miners won!");
        }
    }

    public void areCardsFinished() {
        boolean endOfCards = true;
        for (Player player : gameState.players) {
            for (BoardCard card : player.getCards()) {
                if (card != null) {
                    endOfCards = false;
                    break;
                }
            }
        }
        if (endOfCards) {
            endGame("Saboteurs won!");
        }
    }

    private void endGame(String message) {
        JOptionPane.showMessageDialog(f.getF(), message);
        System.exit(0);
    }

    public boolean isPlayerBlocked() {
        return gameState.players.get(currentPlayer).getIsBlocked().containsValue(true);
    }

    public boolean isPlayerSaboteur() {
        return gameState.players.get(currentPlayer).getSaboteur();
    }
}
