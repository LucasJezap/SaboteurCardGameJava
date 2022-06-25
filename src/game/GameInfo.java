package game;

import card.ActionType;
import card.Direction;

import java.util.List;

public class GameInfo {
    public static final Integer boardWidth = 9;
    public static final Integer boardHeight = 5;
    public static final Integer numOfPlayers = 1;
    public static final Integer tunnelCards = 49;
    public static final Integer actionCards = 27;
    public static final Integer goldCards = 28;
    public static final int saboteurCount = 2;
    public static final int minerCount = 4;
    public static final int numOfCards = 6;
    /**
     * This is a list of all actions for all action cards in this project.
     * It had to be filled manually.
     */
    public static List<List<ActionType>> actions = List.of(
            List.of(ActionType.LAMP, ActionType.CART),
            List.of(ActionType.PICKAXE, ActionType.LAMP),
            List.of(ActionType.PICKAXE, ActionType.CART),
            List.of(ActionType.ROCKFALL),
            List.of(ActionType.ROCKFALL),
            List.of(ActionType.PICKAXE_BLOCK),
            List.of(ActionType.PICKAXE_BLOCK),
            List.of(ActionType.PICKAXE_BLOCK),
            List.of(ActionType.PICKAXE),
            List.of(ActionType.PICKAXE),
            List.of(ActionType.CART_BLOCK),
            List.of(ActionType.CART_BLOCK),
            List.of(ActionType.CART_BLOCK),
            List.of(ActionType.CART),
            List.of(ActionType.CART),
            List.of(ActionType.LAMP_BLOCK),
            List.of(ActionType.LAMP_BLOCK),
            List.of(ActionType.LAMP_BLOCK),
            List.of(ActionType.LAMP),
            List.of(ActionType.LAMP),
            List.of(ActionType.ROCKFALL),
            List.of(ActionType.MAP),
            List.of(ActionType.MAP),
            List.of(ActionType.MAP),
            List.of(ActionType.PICKAXE),
            List.of(ActionType.CART),
            List.of(ActionType.LAMP)
    );
    /**
     * This is a list of all directions for all path cards in this project.
     * It had to be filled manually.
     */
    public static List<List<Direction>> directions = List.of(
            List.of(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT),
            List.of(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT),
            List.of(Direction.DOWN, Direction.RIGHT),
            List.of(Direction.DOWN, Direction.LEFT),
            List.of(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT),
            List.of(Direction.DOWN, Direction.RIGHT),
            List.of(Direction.DOWN, Direction.LEFT),
            List.of(Direction.DOWN, Direction.UP),
            List.of(Direction.RIGHT),
            List.of(Direction.UP),
            List.of(Direction.DOWN, Direction.UP, Direction.RIGHT),
            List.of(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT),
            List.of(Direction.LEFT, Direction.RIGHT),
            List.of(Direction.UP, Direction.LEFT, Direction.RIGHT),
            List.of(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT),
            List.of(Direction.LEFT, Direction.RIGHT),
            List.of(Direction.LEFT, Direction.RIGHT),
            List.of(Direction.LEFT, Direction.RIGHT),
            List.of(Direction.LEFT, Direction.RIGHT),
            List.of(Direction.LEFT, Direction.RIGHT),
            List.of(Direction.DOWN, Direction.LEFT, Direction.RIGHT),
            List.of(Direction.DOWN, Direction.LEFT, Direction.RIGHT),
            List.of(Direction.DOWN, Direction.LEFT, Direction.RIGHT),
            List.of(Direction.DOWN, Direction.LEFT, Direction.RIGHT),
            List.of(Direction.DOWN, Direction.LEFT, Direction.RIGHT),
            List.of(Direction.DOWN, Direction.RIGHT),
            List.of(Direction.DOWN, Direction.RIGHT),
            List.of(Direction.DOWN, Direction.RIGHT),
            List.of(Direction.DOWN, Direction.RIGHT),
            List.of(Direction.DOWN, Direction.RIGHT),
            List.of(Direction.DOWN, Direction.LEFT),
            List.of(Direction.DOWN, Direction.LEFT),
            List.of(Direction.DOWN, Direction.LEFT),
            List.of(Direction.DOWN, Direction.LEFT),
            List.of(Direction.DOWN, Direction.LEFT),
            List.of(Direction.DOWN, Direction.UP),
            List.of(Direction.DOWN, Direction.UP),
            List.of(Direction.DOWN, Direction.UP),
            List.of(Direction.DOWN, Direction.UP),
            List.of(Direction.DOWN, Direction.UP),
            List.of(Direction.DOWN, Direction.UP, Direction.LEFT),
            List.of(Direction.DOWN, Direction.UP, Direction.LEFT),
            List.of(Direction.DOWN, Direction.UP, Direction.LEFT),
            List.of(Direction.DOWN, Direction.UP, Direction.LEFT),
            List.of(Direction.DOWN, Direction.UP, Direction.LEFT),
            List.of(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT),
            List.of(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT),
            List.of(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT),
            List.of(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT)
    );

    public static Boolean isPathStarting(int i) {
        return i == 0;
    }

    public static Boolean isPathTarget(int i) {
        return i == 1 || i == 2 || i == 3;
    }

    public static Boolean isPathTreasure(int i) {
        return i == 1;
    }
}
