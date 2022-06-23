package misc;

import card.ActionType;
import card.Direction;

import java.util.List;
import java.util.Map;

public class CardInfo {
    public static final Map<Integer, Integer> saboteurCount = Map.of(
            3, 1,
            4, 1,
            5, 2
    );
    public static final Map<Integer, Integer> minerCount = Map.of(
            3, 3,
            4, 4,
            5, 4
    );
    public static final Map<Integer, Integer> numOfCards = Map.of(
            3, 6,
            4, 6,
            5, 6
    );

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

    public static Integer goldNuggetCount(int i) {
        if (i < 16)
            return 1;
        else if (i < 24)
            return 2;
        else
            return 3;
    }

    public static Boolean isPathBlocked(int i) {
        return List.of(5, 6, 7, 10, 11, 12, 13, 14).contains(i);
    }

    public static Boolean isPathStarting(int i) {
        return i == 0;
    }

    public static Boolean isPathTarget(int i) {
        return i == 1 || i == 2 || i == 3;
    }

    public static Boolean isPathGold(int i) {
        return i == 1;
    }
}
