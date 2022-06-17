package card;

import java.util.ArrayList;

public class ActionCard extends BoardCard {
    protected final ArrayList<ActionType> actions;
    protected final Boolean isBlocking;
    protected final Boolean isSpecial;

    public ActionCard(ArrayList<ActionType> actions, boolean isBlocking, boolean isSpecial) {
        super(CardType.ACTION);
        this.actions = actions;
        this.isBlocking = isBlocking;
        this.isSpecial = isSpecial;
    }

    public ArrayList<ActionType> getActions() {
        return actions;
    }

    public Boolean isBlocking() {
        return isBlocking;
    }

    public Boolean isSpecial() {
        return isSpecial;
    }
}
