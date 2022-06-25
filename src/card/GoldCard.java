package card;

import java.io.IOException;

public class GoldCard extends BoardCard {
    protected final Integer goldNuggetCount;

    public GoldCard(Integer goldNuggetCount, String imagePath) throws IOException {
        super(CardType.GOLD, imagePath);
        this.goldNuggetCount = goldNuggetCount;
    }
}
