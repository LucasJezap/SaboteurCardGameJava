package card;

import java.util.ArrayList;

public class PathCard extends BoardCard {
    protected final ArrayList<Direction> roads;
    protected final Boolean isGold;
    protected final Boolean isTreasure;

    public PathCard(ArrayList<Direction> roads, Boolean isGold, Boolean isTreasure) {
        super(CardType.PATH);
        this.roads = roads;
        this.isGold = isGold;
        this.isTreasure = isTreasure;
    }

    public ArrayList<Direction> getRoads() {
        return roads;
    }

    public Boolean getGold() {
        return isGold;
    }

    public Boolean getTreasure() {
        return isTreasure;
    }
}
