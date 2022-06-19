package swing;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    private BufferedImage image;
    private final int width;
    private final int height;
    private Border originalBorder;
    private boolean hasBorder;
    private Point previousPoint;
    private final Point corner;

    public ImagePanel(BufferedImage image, int width, int height, boolean clickable) {
        this.width = width;
        this.height = height;
        this.hasBorder = false;
        this.originalBorder = getBorder();

        this.image = ImageComponent.resizeIm(image, width, height);
        corner = new Point(0, 0);

        if (clickable) {
            this.addMouseListener(new ClickListener());
            this.addMouseMotionListener(new DragListener());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, (int) corner.getX(), (int) corner.getY(), null);
    }

    private class ClickListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            previousPoint = e.getPoint();
            if (!hasBorder) {
                hasBorder = true;
                setBorder(new LineBorder(new Color(202, 119, 19), 3, true));
            } else {
                hasBorder = false;
                setBorder(originalBorder);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            corner.x = 0;
            corner.y = 0;
            repaint();
        }
    }

    private class DragListener extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            Point currentPoint = e.getPoint();

            corner.translate(
                    (int) (currentPoint.getX() - previousPoint.getX()),
                    (int) (currentPoint.getY() - previousPoint.getY()));

            previousPoint = currentPoint;
            repaint();
        }
    }

    public void setImage(BufferedImage image) {
        this.image = ImageComponent.resizeIm(image, width, height);
    }
}
