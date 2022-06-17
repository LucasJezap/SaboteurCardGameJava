package card;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class BoardCard {
    protected final CardType type;
    protected Boolean isAllocated;
    protected final BufferedImage image;

    public BoardCard(CardType type, String imagePath) throws IOException {
        this.type = type;
        this.isAllocated = false;
        this.image = ImageIO.read(new File(imagePath));
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

    public BufferedImage getImage() {
        return image;
    }
}
