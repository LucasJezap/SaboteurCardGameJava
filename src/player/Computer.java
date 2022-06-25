package player;

import card.*;
import game.GameController;
import game.GameInfo;
import swing.Frame;
import swing.ImagePanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Computer extends Player {
    public Computer(GameController gameController, Boolean isSaboteur, ArrayList<BoardCard> cards) {
        super(gameController, isSaboteur, cards);
    }

    /**
     * Finds all player's cards that can be used on the board.
     * Returns a random one of them.
     */
    public BoardCard useRandomCard(boolean isBlocked) {
        ArrayList<BoardCard> possibleCards = new ArrayList<>();
        for (BoardCard card : cards) {
            if (isCardValid(gameController, card, isBlocked)) {
                possibleCards.add(card);
            }
        }

        return possibleCards.size() > 0 ? possibleCards.get(new Random().nextInt(possibleCards.size())) : null;
    }

    /**
     * Checks if given card can be used on the board, depending on its type.
     * If it's an Action Card, then it only checks if ROCKFALL can be used (the rest can be always used).
     * If it's a Path Card, it looks for a place on the board where the card can be placed.
     * Finding at least 1 place, it returns true.
     */
    public static boolean isCardValid(GameController gameController, BoardCard card, Boolean isBlocked) {
        if (card.getType() == CardType.PATH) {
            if (isBlocked) {
                return false;
            }
            PathCard pathCard = (PathCard) card;
            for (int i = 0; i < GameInfo.boardHeight; i++) {
                for (int j = 0; j < GameInfo.boardWidth; j++) {
                    ImagePanel panel = (ImagePanel) gameController.getF().getPanels().get("card_" + (i + 1) + "_" + (j + 1));
                    if (panel.getCard() != null) {
                        continue;
                    }
                    ArrayList<PathCard> neighbours = gameController.getPanelNeighbours(i + 1, j + 1);
                    int connections = 0;
                    int possibleConnections = 0;
                    for (int k = 0; k < 4; k++) {
                        if (neighbours.get(k) != null) {
                            possibleConnections++;
                            if ((k == 0 && pathCard.hasRoad(Direction.LEFT) && neighbours.get(k).hasRoad(Direction.RIGHT)) ||
                                    (k == 1 && pathCard.hasRoad(Direction.DOWN) && neighbours.get(k).hasRoad(Direction.UP)) ||
                                    (k == 2 && pathCard.hasRoad(Direction.UP) && neighbours.get(k).hasRoad(Direction.DOWN)) ||
                                    (k == 3 && pathCard.hasRoad(Direction.RIGHT) && neighbours.get(k).hasRoad(Direction.LEFT))) {
                                connections++;
                            }
                        }
                    }
                    if (connections > 0 && possibleConnections == connections) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            ActionCard actionCard = (ActionCard) card;
            if (actionCard.containAction(ActionType.ROCKFALL)) {
                for (int i = 0; i < GameInfo.boardHeight; i++) {
                    for (int j = 0; j < GameInfo.boardWidth; j++) {
                        String name = "card_" + (i + 1) + "_" + (j + 1);
                        if (gameController.panelContainsCard(name)) {
                            return true;
                        }
                    }
                }
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * Plays the given card.
     * It looks for the first compatible place on the board, then plays the card.
     * It is granted that there is a possibility to play that card.
     */
    public static String playActionCard(GameController gameController, ActionCard actionCard) throws IOException {
        Frame f = gameController.getF();
        if (actionCard.containAction(ActionType.MAP)) {
            int randomTarget = new Random().nextInt(3);
            return "Look at target card " + (randomTarget + 1);
        } else if (actionCard.containAction(ActionType.ROCKFALL)) {
            for (int i = 0; i < GameInfo.boardHeight; i++) {
                for (int j = 0; j < GameInfo.boardWidth; j++) {
                    ImagePanel panel = (ImagePanel) gameController.getF().getPanels().get("card_" + (i + 1) + "_" + (j + 1));
                    if (panel.getCard() == null) {
                        continue;
                    }
                    PathCard pathCard = (PathCard) panel.getCard();
                    if (pathCard.getStart() || pathCard.getGold()) {
                        continue;
                    }
                    panel.setCard(null);
                    panel.setImage(null);
                    return "Destroy card (" + (i + 1) + "," + (j + 1) + ")";
                }
            }
            return "";
        } else {
            int randomPlayer = new Random().nextInt(gameController.getGameState().getPlayers().size()) + 1;
            for (ActionType actionType : actionCard.getActions()) {
                String[] action = actionType.toString().split("_");
                String tool = action[0].toLowerCase();
                boolean block = (action.length > 1);
                if (block) {
                    f.changePanelImage(tool + "_player" + randomPlayer, "img/" + tool + "_block.png");
                } else {
                    f.changePanelImage(tool + "_player" + randomPlayer, "img/" + tool + ".png");
                }
                gameController.changePlayerBlock(actionType, randomPlayer - 1);
            }
            if (actionCard.getActions().size() > 1 || !actionCard.getActions().get(0).toString().contains("BLOCK")) {
                String actions = actionCard.getActions().get(0).toString().split("_")[0];
                if (actionCard.getActions().size() > 1) {
                    actions = actions + " " + actionCard.getActions().get(1).toString().split("_")[0];
                }
                return "Unblock Player " + randomPlayer + "(" + actions + ")";
            } else {
                return "Block Player " + randomPlayer + " (" +
                        actionCard.getActions().get(0).toString().split("_")[0] + ")";
            }
        }
    }

    public static String playPathCard(GameController gameController, PathCard pathCard) {
        for (int i = 0; i < GameInfo.boardHeight; i++) {
            for (int j = 0; j < GameInfo.boardWidth; j++) {
                ImagePanel panel = (ImagePanel) gameController.getF().getPanels().get("card_" + (i + 1) + "_" + (j + 1));
                if (panel.getCard() != null) {
                    continue;
                }
                ArrayList<PathCard> neighbours = gameController.getPanelNeighbours(i + 1, j + 1);
                int connections = 0;
                int possibleConnections = 0;
                for (int k = 0; k < 4; k++) {
                    if (neighbours.get(k) != null) {
                        possibleConnections++;
                        if ((k == 0 && pathCard.hasRoad(Direction.LEFT) && neighbours.get(k).hasRoad(Direction.RIGHT)) ||
                                (k == 1 && pathCard.hasRoad(Direction.DOWN) && neighbours.get(k).hasRoad(Direction.UP)) ||
                                (k == 2 && pathCard.hasRoad(Direction.UP) && neighbours.get(k).hasRoad(Direction.DOWN)) ||
                                (k == 3 && pathCard.hasRoad(Direction.RIGHT) && neighbours.get(k).hasRoad(Direction.LEFT))) {
                            connections++;
                        }
                    }
                }
                if (connections > 0 && possibleConnections == connections) {
                    panel.setCard(pathCard);
                    panel.setImage(pathCard.getImage());
                    if ((j + 1 == 8 && i + 1 == 1 && pathCard.hasRoad(Direction.RIGHT))
                            || (j + 1 == 9 && i + 1 == 2 && pathCard.hasRoad(Direction.UP))) {
                        gameController.checkTarget(1);
                    }
                    if ((j + 1 == 8 && i + 1 == 3 && pathCard.hasRoad(Direction.RIGHT))
                            || (j + 1 == 9 && i + 1 == 2 && pathCard.hasRoad(Direction.DOWN))
                            || (j + 1 == 9 && i + 1 == 4 && pathCard.hasRoad(Direction.UP))) {
                        gameController.checkTarget(2);
                    }
                    if ((j + 1 == 8 && i + 1 == 5 && pathCard.hasRoad(Direction.RIGHT))
                            || (j + 1 == 9 && i + 1 == 4 && pathCard.hasRoad(Direction.DOWN))) {
                        gameController.checkTarget(3);
                    }
                    return "Put a card at (" + (i + 1) + "," + (j + 1) + ")";
                }
            }
        }
        return "";
    }
}
