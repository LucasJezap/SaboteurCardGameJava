package swing;

import card.ActionCard;
import card.ActionType;
import card.BoardCard;
import card.CardType;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * This class is used for every panel on the Frame's board.
 */
public class ImagePanel extends JPanel {
    private final Frame f;
    private final int width;
    private final int height;
    private final Border originalBorder;
    private BufferedImage image;
    private BoardCard card;
    private boolean hasBorder;

    public ImagePanel(Frame f, BufferedImage image, int width, int height, boolean clickable) {
        this.f = f;
        this.width = width;
        this.height = height;
        this.hasBorder = false;
        this.originalBorder = getBorder();

        this.image = ImageComponent.resizeIm(image, width, height);

        // only for player's deck
        if (clickable) {
            this.addMouseListener(new ClickListener(this));
        }
    }

    /**
     * Draws the image.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    /**
     * Deletes the selection border of the player card.
     */
    public void restartBorder() {
        f.setSelectedPanel(null);
        this.hasBorder = false;
        this.setBorder(originalBorder);
        this.repaint();
    }

    /**
     * Custom ClickListener for cards in the player's deck.
     * It highlights the card and saves it in Frame for further usage.
     */
    private class ClickListener extends MouseAdapter {
        private final ImagePanel imagePanel;

        public ClickListener(ImagePanel imagePanel) {
            this.imagePanel = imagePanel;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            f.resetBorders();
            if (card != null && !hasBorder) {
                f.setSelectedPanel(imagePanel);
                if (card.getType() == CardType.ACTION) {
                    ActionCard actionCard = (ActionCard) card;
                    if (actionCard.containAction(ActionType.MAP) || actionCard.containAction(ActionType.ROCKFALL)) {
                        f.putTextOnBoard("Select a place");
                    } else {
                        f.putTextOnBoard("Select a player");
                    }
                } else {
                    f.putTextOnBoard("Select a place");
                }
                hasBorder = true;
                setBorder(new LineBorder(new Color(202, 119, 19), 3, true));
            } else {
                f.setSelectedPanel(null);
                f.putTextOnBoard("Select a card");
                hasBorder = false;
                setBorder(originalBorder);
            }
        }
    }

    public void setImage(BufferedImage image) {
        this.image = ImageComponent.resizeIm(image, width, height);
    }

    public boolean isHasBorder() {
        return hasBorder;
    }

    public BoardCard getCard() {
        return card;
    }

    public void setCard(BoardCard card) {
        this.card = card;
    }
}
