package player;

import card.*;
import game.GameController;
import swing.Frame;
import swing.ImagePanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Computer extends Player {
    public Computer(GameController gameController, Boolean isSaboteur, ArrayList<BoardCard> cards) {
        super(gameController, isSaboteur, cards);
    }

    public BoardCard useRandomCard(boolean isBlocked) {
        ArrayList<BoardCard> possibleCards = new ArrayList<>();
        for (BoardCard card : cards) {
            if (isCardValid(gameController, card, isBlocked)) {
                possibleCards.add(card);
            }
        }

        return possibleCards.size() > 0? possibleCards.get(new Random().nextInt(possibleCards.size())): null;
    }

    public static boolean isCardValid(GameController gameController, BoardCard card, Boolean isBlocked) {
        if (card.getType() == CardType.PATH) {
            if (isBlocked) {
                return false;
            }
            PathCard pathCard = (PathCard) card;
            for (int i = 0; i < gameController.getGameState().getBoard().getHeight(); i++) {
                for (int j = 0; j < gameController.getGameState().getBoard().getWidth(); j++) {
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
                for (int i = 0; i < gameController.getGameState().getBoard().getHeight(); i++) {
                    for (int j = 0; j < gameController.getGameState().getBoard().getWidth(); j++) {
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

    public static String playActionCard(GameController gameController, ActionCard actionCard) throws IOException {
        Frame f = gameController.getF();
        if (actionCard.containAction(ActionType.MAP)) {
            int randomTarget = new Random().nextInt(3);
            return "He looked at target card " + randomTarget;
        } else if (actionCard.containAction(ActionType.ROCKFALL)) {
            for (int i = 0; i < gameController.getGameState().getBoard().getHeight(); i++) {
                for (int j = 0; j < gameController.getGameState().getBoard().getWidth(); j++) {
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
                    return "He destroyed card (" + (i + 1) + "," + (j + 1) + ")";
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
                return "He unblocked Player " + randomPlayer;
            } else {
                return "He blocked Player " + randomPlayer;
            }
        }
    }

    public static String playPathCard(GameController gameController, PathCard pathCard) {
        for (int i = 0; i < gameController.getGameState().getBoard().getHeight(); i++) {
            for (int j = 0; j < gameController.getGameState().getBoard().getWidth(); j++) {
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
                    if ((j+1 == 8 && i+1 == 1 && pathCard.hasRoad(Direction.RIGHT))
                            || (j+1 == 9 && i+1 == 2 && pathCard.hasRoad(Direction.UP))) {
                        gameController.checkTarget(1);
                    }
                    if ((j+1 == 8 && i+1 == 3 && pathCard.hasRoad(Direction.RIGHT))
                            || (j+1 == 9 && i+1 == 2 && pathCard.hasRoad(Direction.DOWN))
                            || (j+1 == 9 && i+1 == 4 && pathCard.hasRoad(Direction.UP))) {
                        gameController.checkTarget(2);
                    }
                    if ((j+1 == 8 && i+1 == 5 && pathCard.hasRoad(Direction.RIGHT))
                            || (j+1 == 9 && i+1 == 4 && pathCard.hasRoad(Direction.DOWN))) {
                        gameController.checkTarget(3);
                    }
                    return "Put a card at (" + (i + 1) + "," + (j + 1) + ")";
                }
            }
        }
        return "";
    }
}
