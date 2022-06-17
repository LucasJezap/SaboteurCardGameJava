package card;

public abstract class BoardCard {
    protected final CardType type;
    protected Boolean isAllocated;

    public BoardCard(CardType type) {
        this.type = type;
        this.isAllocated = false;
    }

    public CardType getType() {
        return type;
    }

    public Boolean getAllocated() {
        return isAllocated;
    }

    public void setAllocated(Boolean allocated) {
        isAllocated = allocated;
    }
}
