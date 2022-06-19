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
import java.util.*;

public class Main {
    private static GameController gameController;
    private static final Integer boardWidth = 9;
    private static final Integer boardHeight = 5;
    private static final Integer numOfPlayers = 5;
    private static final Integer tunnelCards = 49;
    private static final Integer actionCards = 27;
    private static final Integer goldCards = 28;

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Starting Saboteur...");

        gameController = initialize();
        gameController.initializeBoard();
//        gameController.play();
    }

    private static GameController initialize() throws IOException {
        gameController = new GameController();
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

        ArrayList<BoardCard> unusedCards = new ArrayList<>();
        for (BoardCard card : cards) {
            if (!card.getAllocated()) {
                unusedCards.add(card);
            }
        }

        GameState gameState = new GameState(
                cards,
                unusedCards,
                goldNuggetCards,
                board,
                players,
                numOfPlayers - saboteurs,
                saboteurs);

        gameController.setGameState(gameState);
        return gameController;
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
                    "img/tunnel" + (i + 1) + ".png"));
        }

        for (int i = 0; i < actionCards; i++) {
            cards.add(new ActionCard(
                    new ArrayList<>(CardInfo.actions.get(i)),
                    "img/action" + (i + 1) + ".png"));
        }

        return cards;
    }

    private static ArrayList<BoardCard> initializeGoldCards() throws IOException {
        ArrayList<BoardCard> goldNuggetCards = new ArrayList<>();
        for (int i = 0; i < goldCards; i++) {
            goldNuggetCards.add(new GoldCard(
                    CardInfo.goldNuggetCount(i),
                    "img/gold" + (i + 1) + ".png"));
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

        Integer[] targetCards = {1, 2, 3};
        List<Integer> targetCardsList = Arrays.asList(targetCards);
        Collections.shuffle(targetCardsList);
        targetCardsList.toArray(targetCards);

        cells.get(2).get(0).setCard(cards.get(0));
        cells.get(0).get(boardWidth - 1).setCard(cards.get(targetCards[0]));
        cells.get(boardHeight / 2).get(boardWidth - 1).setCard(cards.get(targetCards[1]));
        cells.get(boardHeight - 1).get(boardWidth - 1).setCard(cards.get(targetCards[2]));
        for (int i = 0; i < 4; i++) {
            cards.get(i).setAllocated(true);
        }

        return new Board(boardWidth, boardHeight, cells);
    }

    private static ArrayList<Player> initializePlayers(ArrayList<BoardCard> cards) {
        ArrayList<Player> players = new ArrayList<>();
        Integer numOfSaboteurs = CardInfo.saboteurCount.get(numOfPlayers);
        Integer numOfMiners = CardInfo.minerCount.get(numOfPlayers);

        for (int i = 0; i < numOfPlayers; i++) {
            Boolean isSaboteur = isPlayerSaboteur(numOfSaboteurs, numOfMiners);
            if (isSaboteur) {
                numOfSaboteurs--;
            } else {
                numOfMiners--;
            }

            players.add(new Human(
                    gameController,
                    isSaboteur,
                    getRandomCards(cards, CardInfo.numOfCards.get(numOfPlayers))));
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
