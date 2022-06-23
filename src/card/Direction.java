package card;

public enum Direction {
    LEFT,
    RIGHT,
    DOWN,
    UP;

    public int getDirection() {
        if (this == LEFT)
            return 0;
        else if (this == RIGHT)
            return 1;
        else if (this == DOWN)
            return 2;
        else
            return 3;
    }
}
