package swing;

import card.BoardCard;
import card.CardType;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    private final Frame f;
    private BufferedImage image;
    private BoardCard card;
    private final int width;
    private final int height;
    private final Border originalBorder;
    private boolean hasBorder;
    private final Point corner;

    public ImagePanel(Frame f, BufferedImage image, int width, int height, boolean clickable) {
        this.f = f;
        this.width = width;
        this.height = height;
        this.hasBorder = false;
        this.originalBorder = getBorder();

        this.image = ImageComponent.resizeIm(image, width, height);
        corner = new Point(0, 0);

        if (clickable) {
            this.addMouseListener(new ClickListener(this));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, (int) corner.getX(), (int) corner.getY(), null);
    }

    private class ClickListener extends MouseAdapter {
        private final ImagePanel imagePanel;

        public ClickListener(ImagePanel imagePanel) {
            this.imagePanel = imagePanel;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (card != null && !hasBorder) {
                f.setSelectedPanel(imagePanel);
                if (card.getType() == CardType.ACTION) {
                    f.putTextOnBoard("Select a player");
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

    public void setCard(BoardCard card) {
        this.card = card;
    }

    public boolean isHasBorder() {
        return hasBorder;
    }

    public void restartBorder() {
        f.setSelectedPanel(null);
        this.hasBorder = false;
        this.setBorder(originalBorder);
    }

    public BoardCard getCard() {
        return card;
    }
}
