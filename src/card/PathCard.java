package card;

import java.io.IOException;
import java.util.ArrayList;

public class PathCard extends BoardCard {
    protected final ArrayList<Direction> roads;
    protected final Boolean isStart;
    protected Boolean isGold;
    protected final Boolean isTreasure;

    public PathCard(ArrayList<Direction> roads, Boolean isStart, Boolean isGold, Boolean isTreasure, String imagePath) throws IOException {
        super(CardType.PATH, imagePath);
        this.roads = roads;
        this.isStart = isStart;
        this.isGold = isGold;
        this.isTreasure = isTreasure;
    }

    public ArrayList<Direction> getRoads() {
        return roads;
    }

    public Boolean getStart() {
        return isStart;
    }

    public Boolean getGold() {
        return isGold;
    }

    public Boolean getTreasure() {
        return isTreasure;
    }

    public void setGold(Boolean gold) {
        isGold = gold;
    }

    public Boolean hasRoad(Direction direction) {
        return roads.contains(direction);
    }
}
