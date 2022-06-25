package board;

import java.util.ArrayList;

public class Board {
    protected final Integer width;
    protected final Integer height;
    protected final ArrayList<ArrayList<Cell>> cells;

    public Board(Integer width, Integer height, ArrayList<ArrayList<Cell>> cells) {
        this.width = width;
        this.height = height;
        this.cells = cells;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
}
