package swing;

import board.Board;
import card.BoardCard;
import misc.CardInfo;
import player.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Frame {
    protected final JFrame f;
    protected final String title;
    protected final String imagePath;
    protected BufferedImage image;
    protected final Integer frameWidth;
    protected final Integer frameHeight;
    protected final ImageComponent imageComponent;
    protected final Map<String, JPanel> panels = new HashMap<>();
    protected final Map<JPanel, BoardCard> cards = new HashMap<>();

    public Frame(String title, String imagePath) throws IOException {
        this.title = title;
        f = new JFrame(title);
        this.imagePath = imagePath;
        image = ImageIO.read(new File(imagePath));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.frameWidth = dim.width;
        this.frameHeight = dim.height;
        f.setSize(dim.width, dim.height);
        f.setLayout(new GridLayout());
        imageComponent = new ImageComponent(image);
        imageComponent.resizeImage(frameWidth, frameHeight);
    }

    public void initialize(ArrayList<Player> players, Board board) throws IOException {
        addLabels();
        addPlayers(players.size());
        addCards(board);
        addPlayerCards(players);
        addPass();
        addTextSection();
        putTextOnBoard("hei");
    }

    private void addLabels() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("The Saboteur Game", SwingConstants.CENTER);
        label.setFont(new Font("Courier", Font.ITALIC, 72));
        panel.setOpaque(false);
        panel.setBounds((int) (0.125 * frameWidth), 0, (int) (0.625 * frameWidth), (int) (0.12 * frameHeight));
        panel.setLayout(new GridBagLayout());
        panel.add(label);
        f.add(panel);

        panel = new JPanel();
        label = new JLabel("Copyright @ 2022 Katarzyna Przybyła", SwingConstants.CENTER);
        label.setFont(new Font("Courier", Font.ITALIC, 20));
        panel.setOpaque(false);
        panel.setBounds(0, (int) (0.92 * frameHeight), (int) (0.3 * frameWidth), (int) (0.12 * frameHeight));
        panel.setLayout(new GridBagLayout());
        panel.add(label);
        f.add(panel);

        f.getContentPane().repaint();
    }

    private void addPlayers(int numOfPlayers) throws IOException {
        for (int i = 0; i < numOfPlayers; i++) {
            int x = 0;
            int y = (int) (i * (frameHeight * (0.05 + 1.0 / 15)));
            int w = frameWidth / 9;
            int h = w * 2 / 9;
            addPanel("player" + (i + 1), "img/player" + (i + 1) + ".png",
                    new Rectangle(x, y, w, h));
            int y2 = y + ((int) ((i + 1) * (frameHeight * (0.05 + 1.0 / 15))) - y) / 2;
            addPanel("lamp_player" + (i + 1), "img/lamp.png", new Rectangle(x, y2, w / 3, h));
            addPanel("pickaxe_player" + (i + 1), "img/pickaxe.png", new Rectangle(x + w / 3, y2, w / 3, h));
            addPanel("cart_player" + (i + 1), "img/cart.png", new Rectangle(x + 2 * w / 3, y2, w / 3, h));
        }
        addPanel("current_player", "img/player1.png",
                new Rectangle((int) (0.81 * frameWidth), (int) (0.25 * frameHeight), frameWidth / 7, frameWidth / 30));
    }

    private void addCards(Board board) throws IOException {
        int cardWidth = (int) (0.07 * frameWidth);
        int cardHeight = (int) (cardWidth / 0.7);
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                int x = (int) (0.125 * frameWidth + j * cardWidth);
                int y = (int) (0.115 * frameHeight + i * cardHeight);
                String name = "card_" + (i + 1) + "_" + (j + 1);
                if (i == 2 && j == 1) {
                    addPanel(name, "img/tunnel1.png", new Rectangle(x, y, cardWidth, cardHeight));
                } else if ((i == 0 && j == 8) || (i == 2 && j == 8) || (i == 4 && j == 8)) {
                    addPanel(name, "img/target_back.png", new Rectangle(x, y, cardWidth, cardHeight));
                } else {
                    addPanel(name, null, new Rectangle(x, y, cardWidth, cardHeight), false);
                }
                panels.get(name).setBorder(new LineBorder(new Color(226, 181,123)));
            }
        }
    }

    private void addPlayerCards(ArrayList<Player> players) throws IOException {
        int cardWidth = (int) (0.07 * frameWidth);
        int cardHeight = (int) (cardWidth / 0.7);
        ArrayList<BoardCard> cards = players.get(0).getCards();
        for (int i = 0; i < CardInfo.numOfCards.get(players.size()); i++) {
            int x = (int) (0.81 * frameWidth + (i % 2) * cardWidth);
            int y = (int) (0.37 * frameHeight + (i % 3) * cardHeight);
            addPanel("player_card_" + (i + 1), cards.get(i).getImage(), new Rectangle(x, y, cardWidth, cardHeight), true);
        }
    }

    private void addPass() throws IOException {
        addPanel("pass", "img/pass.png",
                new Rectangle((int) (0.81 * frameWidth), (int) (0.9 * frameHeight), frameWidth / 7, frameWidth / 30));
    }

    private void addTextSection() throws IOException {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setFont(new Font("Courier", Font.ITALIC, 20));
        panel.setOpaque(false);
        panel.setBounds((int) (0.78 * frameWidth), (int) (0.025 * frameHeight), (int) (0.19 * frameWidth), (int) (0.21 * frameHeight));
        panel.setLayout(new GridBagLayout());
        panel.add(label);
        f.add(panel);
        panels.put("textboard", panel);

        f.getContentPane().repaint();
    }

    private void addPanel(String name, String path, Rectangle r) throws IOException {
        BufferedImage img = ImageIO.read(new File(path));
        JPanel panel = new ImagePanel(img, r.width, r.height, false);
        panel.setBounds(r);
        panel.setOpaque(false);
        panels.put(name, panel);
        f.add(panel);
        f.getContentPane().repaint();
    }

    private void addPanel(String name, BufferedImage img, Rectangle r, boolean clickable) throws IOException {
        JPanel panel = new ImagePanel(img, r.width, r.height, clickable);
        panel.setBounds(r);
        panel.setOpaque(false);
        panels.put(name, panel);
        f.add(panel);
        f.getContentPane().repaint();
    }

    public void nextPlayer(Player player, int num) throws IOException {
        changePanelImage("current_player", "img/player" + num + ".png");
        for (int i = 0; i < player.getCards().size(); i++) {
            changePanelImage("player_card_" + (i + 1), player.getCards().get(i).getImage());
        }
    }

    private void changePanelImage(String name, String path) throws IOException {
        ImagePanel p = (ImagePanel) panels.get(name);
        p.setImage(ImageIO.read(new File(path)));
        f.getContentPane().repaint();
    }

    private void changePanelImage(String name, BufferedImage img) throws IOException {
        ImagePanel p = (ImagePanel) panels.get(name);
        p.setImage(img);
        f.getContentPane().repaint();
    }

    public void putTextOnBoard(String text) {
        JLabel label = (JLabel) panels.get("textboard").getComponents()[0];
        label.setText(text);
    }


    public void show() {
        f.setContentPane(imageComponent);
        f.setVisible(true);
    }

    public void close() {
        f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
    }
}


