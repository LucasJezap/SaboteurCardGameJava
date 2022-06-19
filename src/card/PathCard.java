package card;

import java.io.IOException;
import java.util.ArrayList;

public class PathCard extends BoardCard {
    protected final ArrayList<Direction> roads;
    protected final Boolean isBlocked;
    protected final Boolean isStart;
    protected final Boolean isGold;
    protected final Boolean isTreasure;

    public PathCard(ArrayList<Direction> roads, Boolean isBlocked, Boolean isStart, Boolean isGold, Boolean isTreasure, String imagePath) throws IOException {
        super(CardType.PATH, imagePath);
        this.roads = roads;
        this.isBlocked = isBlocked;
        this.isStart = isStart;
        this.isGold = isGold;
        this.isTreasure = isTreasure;
    }

    @Override
    public String toString() {
        return "PathCard: { roads: " + roads.toString() + ", isBlocked: " + isBlocked + " }";
    }
}
