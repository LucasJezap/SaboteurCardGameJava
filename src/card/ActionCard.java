package card;

import java.io.IOException;
import java.util.ArrayList;

public class ActionCard extends BoardCard {
    protected final ArrayList<ActionType> actions;

    public ActionCard(ArrayList<ActionType> actions, String imagePath) throws IOException {
        super(CardType.ACTION, imagePath);
        this.actions = actions;
    }

    @Override
    public String toString() {
        return "ActionCard: { actions: " + actions.toString() + " }";
    }

    public ArrayList<ActionType> getActions() {
        return actions;
    }

    public boolean containAction(ActionType type) {
        return actions.contains(type);
    }
}
