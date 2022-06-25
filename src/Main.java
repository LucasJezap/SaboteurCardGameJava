import card.ActionCard;
import card.BoardCard;
import card.PathCard;
import game.GameController;
import game.GameInfo;
import game.GameState;
import player.Computer;
import player.Human;
import player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main {
    private static GameController gameController;

    public static void main(String[] args) throws IOException {
        gameController = initialize();
        gameController.initializeBoard();
    }

    /**
     * Initializes GameController:
     * - generates cards,
     * - generates players,
     * - add random cards to players.
     */
    private static GameController initialize() throws IOException {
        gameController = new GameController();
        ArrayList<BoardCard> cards = initializeCards();
        ArrayList<Player> players = initializePlayers(cards);
        int saboteurs = 0;

        for (Player player : players) {
            if (player.getSaboteur()) {
                saboteurs++;
            }
        }

        ArrayList<BoardCard> unusedCards = new ArrayList<>();
        for (BoardCard card : cards) {
            if (!card.getAllocated()) {
                unusedCards.add(card);
            }
        }

        GameState gameState = new GameState(cards, unusedCards, players, GameInfo.numOfPlayers - saboteurs, saboteurs);

        gameController.setGameState(gameState);
        return gameController;
    }

    private static ArrayList<BoardCard> initializeCards() throws IOException {
        ArrayList<BoardCard> cards = new ArrayList<>();
        for (int i = 0; i < GameInfo.tunnelCards; i++) {
            cards.add(new PathCard(new ArrayList<>(GameInfo.directions.get(i)), GameInfo.isPathStarting(i), GameInfo.isPathTarget(i), GameInfo.isPathTreasure(i), "img/tunnel" + (i + 1) + ".png"));
        }
        Collections.shuffle(cards.subList(1, 4));

        for (int i = 0; i < GameInfo.actionCards; i++) {
            cards.add(new ActionCard(new ArrayList<>(GameInfo.actions.get(i)), "img/action" + (i + 1) + ".png"));
        }

        for (int i = 0; i < 4; i++) {
            cards.get(i).setAllocated(true);
        }

        return cards;
    }

    private static ArrayList<Player> initializePlayers(ArrayList<BoardCard> cards) {
        ArrayList<Player> players = new ArrayList<>();
        Integer numOfSaboteurs = GameInfo.saboteurCount;
        Integer numOfMiners = GameInfo.minerCount;

        for (int i = 0; i < 5; i++) {
            Boolean isSaboteur = isPlayerSaboteur(numOfSaboteurs, numOfMiners);
            if (isSaboteur) {
                numOfSaboteurs--;
            } else {
                numOfMiners--;
            }
            if (i < GameInfo.numOfPlayers) {
                players.add(new Human(gameController, isSaboteur, getRandomCards(cards)));
            } else {
                players.add(new Computer(gameController, isSaboteur, getRandomCards(cards)));
            }
        }
        Collections.shuffle(players.subList(1, players.size()));

        return players;
    }

    private static Boolean isPlayerSaboteur(Integer numOfSaboteurs, Integer numOfMiners) {
        return new Random().nextInt(numOfSaboteurs + numOfMiners) < numOfSaboteurs;
    }

    private static ArrayList<BoardCard> getRandomCards(ArrayList<BoardCard> cards) {
        int num = GameInfo.numOfCards;
        ArrayList<BoardCard> playerCards = new ArrayList<>();
        int numOfCards = cards.size();
        Random random = new Random();

        while (true) {
            BoardCard card = cards.get(random.nextInt(numOfCards));
            if (!card.getAllocated()) {
                playerCards.add(card);
                card.setAllocated(true);
                num--;
            }
            if (num == 0) {
                return playerCards;
            }
        }
    }
}
