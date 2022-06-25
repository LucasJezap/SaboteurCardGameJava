package game;

import card.*;
import player.Computer;
import player.Player;
import swing.Frame;
import swing.ImagePanel;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameController {
    protected GameState gameState;
    protected int currentPlayer;
    protected Frame f;

    public GameController() {
    }

    /**
     * Creates swing frame and shows it.
     * It also displays a welcome message.
     */
    public void initializeBoard() throws IOException {
        f = new Frame(this, "The Saboteur game", "img/background.png");
        f.show();
        f.initialize(gameState.players, gameState.cards);
        this.currentPlayer = 0;
        JOptionPane.showMessageDialog(f.getF(),
                """
                        Welcome to Saboteur game!
                        This version has three limitations:
                        1. There's only one round
                        2. You can't rotate cards
                        3. Blocking path cards don't block
                        Have fun!""");
    }

    /**
     * Changes player.
     * If it's a computer, it makes a random move and changes to next player.
     */
    public void changePlayer() throws IOException {
        currentPlayer = (currentPlayer + 1) % gameState.players.size();
        f.nextPlayer(gameState.players.get(currentPlayer), currentPlayer + 1);
        if (gameState.players.get(currentPlayer).getClass().equals(Computer.class)) {
            doComputerMove();
            changePlayer();
        }
    }

    /**
     * Makes a random computer move and displays the move.
     */
    private void doComputerMove() throws IOException {
        Computer computer = (Computer) gameState.players.get(currentPlayer);
        BoardCard card = computer.useRandomCard(isPlayerBlocked());
        String message;
        if (card == null) {
            message = "Computer can't make a move!";
        } else if (card.getType() == CardType.ACTION) {
            message = Computer.playActionCard(this, (ActionCard) card);
            addNewCardToPlayer(card);
        } else {
            message = Computer.playPathCard(this, (PathCard) card);
            addNewCardToPlayer(card);
        }
        JOptionPane.showMessageDialog(f.getF(), "Computer has made a move!\n" + message);
    }


    /**
     * (Un)blocks the player.
     */
    public void changePlayerBlock(ActionType type, int index) {
        if (type.toString().contains("_BLOCK")) {
            gameState.players.get(index).block(ActionType.getNoBlockType(type));
        } else {
            gameState.players.get(index).unblock(ActionType.getNoBlockType(type));
        }
    }

    /**
     * Replaces used card with a new, random one from the stock.
     */
    public void addNewCardToPlayer(BoardCard card) {
        Player player = gameState.players.get(currentPlayer);
        player.getCards().remove(card);
        player.getCards().add(gameState.getRandomUnusedCard());
        areCardsFinished();
    }

    /**
     * Checks what target card was discovered.
     * If the target card is a treasure, the game ends.
     */
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

    /**
     * Checks if saboteurs have won.
     * If there are no cards in the stock and no player can use a card - the game ends.
     */
    public void areCardsFinished() {
        if (gameState.unusedCards.size() != 0) {
            return;
        }
        boolean endOfGame = true;
        for (Player player : gameState.players) {
            for (BoardCard card : player.getCards()) {
                if (card != null && Computer.isCardValid(this, card, player.getIsBlocked().containsValue(true))) {
                    endOfGame = false;
                    break;
                }
            }
        }
        if (endOfGame) {
            endGame("Saboteurs won!");
        }
    }

    /**
     * Ends game, with appropriate message.
     * Closes the program.
     */
    private void endGame(String message) {
        JOptionPane.showMessageDialog(f.getF(), message);
        System.exit(0);
    }

    /**
     * Checks if given JPanel contains a Path Card.
     * It cannot be start or target card.
     */
    public boolean panelContainsCard(String name) {
        ImagePanel panel = (ImagePanel) f.getPanels().get(name);
        if (panel.getCard() != null) {
            PathCard pathCard = (PathCard) panel.getCard();
            return !(pathCard.getStart() || pathCard.getGold());
        }

        return false;
    }

    /**
     * Returns all neighbours (or null values) of given panel.
     */
    public ArrayList<PathCard> getPanelNeighbours(int row, int col) {
        ArrayList<PathCard> cards = new ArrayList<>();
        int[] rows = {0, 1, -1, 0};
        int[] cols = {-1, 0, 0, 1};
        for (int i = 0; i < 4; i++) {
            String name = "card_" + (row + rows[i]) + "_" + (col + cols[i]);
            ImagePanel panel = (ImagePanel) f.getPanels().get(name);
            if (panel != null && panel.getCard() != null) {
                PathCard pathCard = (PathCard) panel.getCard();
                if (!pathCard.getGold()) {
                    cards.add(pathCard);
                } else {
                    cards.add(null);
                }
            } else {
                cards.add(null);
            }
        }
        return cards;
    }

    /**
     * Returns a random card from current player deck.
     */
    public BoardCard randomCard() {
        ArrayList<BoardCard> cards = gameState.players.get(currentPlayer).getCards();
        while (true) {
            int num = new Random().nextInt(cards.size());
            if (cards.get(num) != null) {
                return cards.get(num);
            }
        }
    }

    public Frame getF() {
        return f;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean isPlayerBlocked() {
        return gameState.players.get(currentPlayer).getIsBlocked().containsValue(true);
    }

    public boolean isPlayerSaboteur() {
        return gameState.players.get(currentPlayer).getSaboteur();
    }
}
