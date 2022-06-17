import board.Board;
import board.Cell;
import card.ActionCard;
import card.BoardCard;
import card.GoldCard;
import card.PathCard;
import game.GameController;
import game.GameState;
import misc.CardInfo;
import player.Human;
import player.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Main {
    private static final Integer boardWidth = 9;
    private static final Integer boardHeight = 5;
    private static final Integer numOfPlayers = 3;
    private static final Integer tunnelCards = 49;
    private static final Integer actionCards = 27;
    private static final Integer goldCards = 28;
    private static final Map<Integer, Integer> saboteurCount = Map.of(
            3, 1,
            4, 1,
            5, 2,
            6, 2,
            7, 3,
            8, 3,
            9, 3,
            10, 4
    );
    private static final Map<Integer, Integer> minerCount = Map.of(
            3, 3,
            4, 4,
            5, 4,
            6, 5,
            7, 5,
            8, 6,
            9, 7,
            10, 7
    );
    private static final Map<Integer, Integer> numOfCards = Map.of(
            3, 6,
            4, 6,
            5, 6,
            6, 5,
            7, 5,
            8, 4,
            9, 4,
            10, 4
    );

    public static void main(String[] args) throws IOException {
        System.out.println("Starting Saboteur...");

        GameController gameController = initialize();
        //gameController.play();
    }

    private static GameController initialize() throws IOException {
        ArrayList<BoardCard> cards = initializeCards();
        ArrayList<BoardCard> goldNuggetCards = initializeGoldCards();
        Board board = initializeBoard(cards);
        ArrayList<Player> players = initializePlayers(cards);
        int saboteurs = 0;

        for (Player player : players) {
            if (player.getSaboteur()) {
                saboteurs++;
            }
        }

        GameState gameState = new GameState(
                cards,
                goldNuggetCards,
                board,
                players,
                numOfPlayers - saboteurs,
                saboteurs);

        return new GameController(gameState);
    }
    private static ArrayList<BoardCard> initializeCards() throws IOException {
        ArrayList<BoardCard> cards = new ArrayList<>();
        for (int i = 0; i < tunnelCards; i++) {
            cards.add(new PathCard(
                    new ArrayList<>(CardInfo.directions.get(i)),
                    CardInfo.isPathBlocked(i),
                    CardInfo.isPathStarting(i),
                    CardInfo.isPathTarget(i),
                    CardInfo.isPathGold(i),
                    "img/tunnel" + (i + 1) + ".jpg"));
        }

        for (int i = 0; i < actionCards; i++) {
            cards.add(new ActionCard(
                    new ArrayList<>(CardInfo.actions.get(i)),
                    "img/action" + (i + 1) + ".jpg"));
        }

        return cards;
    }

    private static ArrayList<BoardCard> initializeGoldCards() throws IOException {
        ArrayList<BoardCard> goldNuggetCards = new ArrayList<>();
        for (int i = 0; i < goldCards; i++) {
            goldNuggetCards.add(new GoldCard(
                    CardInfo.goldNuggetCount(i),
                    "img/gold" + (i + 1) + ".jpg"));
        }

        return goldNuggetCards;
    }

    private static Board initializeBoard(ArrayList<BoardCard> cards) {
        ArrayList<ArrayList<Cell>> cells = new ArrayList<>();
        for (int i = 0; i < boardHeight; i++) {
            cells.add(new ArrayList<>());
            for (int j = 0; j < boardWidth; j++) {
                cells.get(i).add(new Cell(i, j));
            }
        }

        cells.get(2).get(0).setCard(cards.get(0)); // karta startu
        cells.get(0).get(8).setCard(cards.get(1)); // karta celu 1
        cells.get(2).get(8).setCard(cards.get(2)); // karta celu 2
        cells.get(4).get(8).setCard(cards.get(3)); // karta celu 3
        for (int i = 0; i < 4; i++) {
            cards.get(i).setAllocated(true);
        }

        return new Board(boardWidth, boardHeight, cells);
    }

    private static ArrayList<Player> initializePlayers(ArrayList<BoardCard> cards) {
        ArrayList<Player> players = new ArrayList<>();
        Integer numOfSaboteurs = saboteurCount.get(numOfPlayers);
        Integer numOfMiners = minerCount.get(numOfPlayers);

        for (int i = 0; i < numOfPlayers; i++) {
            Boolean isSaboteur = isPlayerSaboteur(numOfSaboteurs, numOfMiners);
            if (isSaboteur) {
                numOfSaboteurs--;
            } else {
                numOfMiners--;
            }

            players.add(new Human(
                    isSaboteur,
                    getRandomCards(cards, numOfCards.get(numOfPlayers))));
        }
        return players;
    }

    private static Boolean isPlayerSaboteur(Integer numOfSaboteurs, Integer numOfMiners) {
        return new Random().nextInt(numOfSaboteurs + numOfMiners) < numOfSaboteurs;
    }

    private static ArrayList<BoardCard> getRandomCards(ArrayList<BoardCard> cards, Integer num) {
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
