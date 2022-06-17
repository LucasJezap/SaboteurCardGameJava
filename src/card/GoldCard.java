package card;

public class GoldCard extends BoardCard {
    protected final Integer goldNuggetCount;

    public GoldCard(Integer goldNuggetCount) {
        super(CardType.GOLD);
        this.goldNuggetCount = goldNuggetCount;
    }

    public Integer getGoldNuggetCount() {
        return goldNuggetCount;
    }
}
