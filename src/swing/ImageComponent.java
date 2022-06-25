package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class is used for background image in Frame.
 */
public class ImageComponent extends JComponent {
    private Image image;
    private final Integer x;
    private final Integer y;

    public ImageComponent(Image image) {
        this.image = image;
        this.x = 0;
        this.y = 0;
    }

    /**
     * Draws the background image.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, x, y, this);
    }

    /**
     * It resizes the background image to take full screen.
     */
    public void resizeImage(int width, int height) {
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = resized.createGraphics();
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        image = resized;
    }

    /**
     * Static function for resizing images to given width and height.
     */
    public static BufferedImage resizeIm(BufferedImage im, int width, int height) {
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = resized.createGraphics();
        graphics2D.drawImage(im, 0, 0, width, height, null);
        graphics2D.dispose();
        return resized;
    }
}
