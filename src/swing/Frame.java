package swing;

import board.Board;
import card.*;
import game.GameController;
import misc.CardInfo;
import player.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Frame {
    protected final JFrame f;
    protected final GameController gameController;
    protected final String title;
    protected final String imagePath;
    protected BufferedImage image;
    protected final Integer frameWidth;
    protected final Integer frameHeight;
    protected final ImageComponent imageComponent;
    protected final Map<String, JPanel> panels = new HashMap<>();
    protected ImagePanel selectedPanel;

    public Frame(GameController gameController, String title, String imagePath) throws IOException {
        this.gameController = gameController;
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

    public void initialize(ArrayList<Player> players, Board board, ArrayList<BoardCard> cards) throws IOException {
        addLabels();
        addPlayers(players.size());
        addCards(board, cards);
        addPlayerCards(players);
        addPassAndDwarf();
        addTextSection();
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
            String number = String.valueOf(i + 1);
            String name = "player" + number;
            addPanel(name, "img/player" + (i + 1) + ".png",
                    new Rectangle(x, y, w, h));
            int y2 = y + ((int) ((i + 1) * (frameHeight * (0.05 + 1.0 / 15))) - y) / 2;
            addPanel("lamp_player" + (i + 1), "img/lamp.png", new Rectangle(x, y2, w / 3, h));
            addPanel("pickaxe_player" + (i + 1), "img/pickaxe.png", new Rectangle(x + w / 3, y2, w / 3, h));
            addPanel("cart_player" + (i + 1), "img/cart.png", new Rectangle(x + 2 * w / 3, y2, w / 3, h));
            setPlayerMouseListener(name, number);
        }
        addPanel("current_player", "img/player1.png",
                new Rectangle((int) (0.81 * frameWidth), (int) (0.25 * frameHeight), frameWidth / 7, frameWidth / 30));
    }

    private void addCards(Board board, ArrayList<BoardCard> cards) throws IOException {
        int cardWidth = (int) (0.07 * frameWidth);
        int cardHeight = (int) (cardWidth / 0.7);
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                int x = (int) (0.125 * frameWidth + j * cardWidth);
                int y = (int) (0.115 * frameHeight + i * cardHeight);
                int row = i + 1;
                int column = j + 1;
                String name = "card_" + row + "_" + column;
                if (i == 2 && j == 1) {
                    addPanel(name, "img/tunnel1.png", new Rectangle(x, y, cardWidth, cardHeight));
                    ImagePanel start = (ImagePanel) panels.get(name);
                    start.setCard(cards.get(0));
                } else if ((i == 0 && j == 8) || (i == 2 && j == 8) || (i == 4 && j == 8)) {
                    addPanel(name, "img/target_back.png", new Rectangle(x, y, cardWidth, cardHeight));
                    ImagePanel start = (ImagePanel) panels.get(name);
                    start.setCard(cards.get(i / 2 + 1));
                } else {
                    addPanel(name, null, new Rectangle(x, y, cardWidth, cardHeight), false);
                }
                panels.get(name).setBorder(new LineBorder(new Color(226, 181, 123)));
                setBoardCardMouseListener(name, row, column);
            }
        }
    }

    private void addPlayerCards(ArrayList<Player> players) {
        int cardWidth = (int) (0.07 * frameWidth);
        int cardHeight = (int) (cardWidth / 0.7);
        ArrayList<BoardCard> cards = players.get(0).getCards();
        for (int i = 0; i < CardInfo.numOfCards.get(players.size()); i++) {
            int x = (int) (0.81 * frameWidth + (i % 2) * cardWidth);
            int y = (int) (0.37 * frameHeight + (i % 3) * cardHeight);
            String name = "player_card_" + (i + 1);
            addPanel(name, cards.get(i).getImage(), new Rectangle(x, y, cardWidth, cardHeight), true);
            ImagePanel panel = (ImagePanel) panels.get(name);
            panel.setCard(cards.get(i));
        }
    }

    private void addPassAndDwarf() throws IOException {
        String name = "pass";
        addPanel(name, "img/pass.png",
                new Rectangle((int) (0.81 * frameWidth), (int) (0.9 * frameHeight), frameWidth / 7, frameWidth / 30));
        panels.get(name).addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    setChangePlayerTimer();
                    putTextOnBoard("Next player in 3...");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        name = "dwarf";
        int x = (int) (frameWidth * 0.012);
        int y = (int) (frameHeight * 0.63);
        int w = (int) (0.1 * frameWidth);
        int h = (int) (w / 0.7);
        if (gameController.isPlayerSaboteur()) {
            addPanel(name, "img/saboteur.png", new Rectangle(x, y, w, h));
        } else {
            addPanel(name, "img/miner.png", new Rectangle(x, y, w, h));
        }

    }

    private void addTextSection() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setFont(new Font("Courier", Font.ITALIC, 20));
        panel.setOpaque(false);
        panel.setBounds((int) (0.78 * frameWidth), (int) (0.025 * frameHeight), (int) (0.19 * frameWidth), (int) (0.21 * frameHeight));
        panel.setLayout(new GridBagLayout());
        panel.add(label);
        f.add(panel);
        panels.put("textboard", panel);
        label.setText("Select a card");

        f.getContentPane().repaint();
    }

    private void addPanel(String name, String path, Rectangle r) throws IOException {
        BufferedImage img = ImageIO.read(new File(path));
        JPanel panel = new ImagePanel(this, img, r.width, r.height, false);
        panel.setBounds(r);
        panel.setOpaque(false);
        panels.put(name, panel);
        f.add(panel);
        f.getContentPane().repaint();
    }

    private void addPanel(String name, BufferedImage img, Rectangle r, boolean clickable) {
        JPanel panel = new ImagePanel(this, img, r.width, r.height, clickable);
        panel.setBounds(r);
        panel.setOpaque(false);
        panels.put(name, panel);
        f.add(panel);
        f.getContentPane().repaint();
    }

    public void nextPlayer(Player player, int num) throws IOException {
        changePanelImage("current_player", "img/player" + num + ".png");
        for (int i = 0; i < player.getCards().size(); i++) {
            String name = "player_card_" + (i + 1);
            ImagePanel panel = (ImagePanel) panels.get(name);
            if (panel.isHasBorder()) {
                panel.restartBorder();
            }
            if (player.getCards().get(i) == null) {
                panel.setImage(null);
                panel.setCard(null);
                f.repaint();
                return;
            }
            changePanelImage(name, player.getCards().get(i).getImage());
            panel.setCard(player.getCards().get(i));
        }

        ImagePanel panel = (ImagePanel) panels.get("dwarf");
        if (gameController.isPlayerSaboteur()) {
            panel.setImage(ImageIO.read(new File("img/saboteur.png")));
        } else {
            panel.setImage(ImageIO.read(new File("img/miner.png")));
        }

        putTextOnBoard("Select a card");
    }

    private void changePanelImage(String name, String path) throws IOException {
        ImagePanel p = (ImagePanel) panels.get(name);
        p.setImage(ImageIO.read(new File(path)));
        f.getContentPane().repaint();
    }

    private void changePanelImage(String name, BufferedImage img) {
        ImagePanel p = (ImagePanel) panels.get(name);
        p.setImage(img);
        f.getContentPane().repaint();
    }

    public void putTextOnBoard(String text) {
        JLabel label = (JLabel) panels.get("textboard").getComponents()[0];
        label.setText(text);
    }

    public void setSelectedPanel(ImagePanel selectedPanel) {
        this.selectedPanel = selectedPanel;
    }

    public void show() {
        f.setContentPane(imageComponent);
        f.setVisible(true);
    }

    private void setChangePlayerTimer() {
        Timer timer = new Timer(3000, t -> {
            try {
                gameController.changePlayer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void setPlayerMouseListener(String name, String number) {
        panels.get(name).addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedPanel != null) {
                    BoardCard selectedCard = selectedPanel.getCard();
                    if (selectedCard.getType() == CardType.ACTION) {
                        try {
                            ActionCard actionCard = (ActionCard) selectedCard;
                            if (actionCard.containAction(ActionType.ROCKFALL) || actionCard.containAction(ActionType.MAP)) {
                                putTextOnBoard("Wrong action card!");
                                return;
                            }
                            for (ActionType actionType : actionCard.getActions()) {
                                String[] action = actionType.toString().split("_");
                                String tool = action[0].toLowerCase();
                                boolean block = (action.length > 1);
                                if (block) {
                                    changePanelImage(tool + "_player" + number, "img/" + tool + "_block.png");
                                } else {
                                    changePanelImage(tool + "_player" + number, "img/" + tool + ".png");
                                }
                                gameController.changePlayerBlock(actionType, Integer.parseInt(number) - 1);
                            }
                            gameController.addNewCardToPlayer(selectedCard);
                            gameController.changePlayer();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        putTextOnBoard("Wrong card!");
                    }
                }
            }
        });
    }

    private boolean doesCardNotFit(PathCard card1, PathCard card2, Direction d1, Direction d2, int row, int column) {
        if ((column == 0 && d2 == Direction.LEFT) || (column == 8 && d2 == Direction.RIGHT) ||
                (row == 0 && d2 == Direction.UP) || (row == 4 && d2 == Direction.DOWN)) {
            return false;
        }

        if (card1 == null) {
            return false;
        }

        return !((!card1.hasRoad(d1) && !card2.hasRoad(d2))
                || (card1.hasRoad(d1) && card2.hasRoad(d2)));

    }

    private void setBoardCardMouseListener(String name, int row, int column) {
        panels.get(name).addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedPanel != null) {
                    boolean changePlayer = false;
                    BoardCard selectedCard = selectedPanel.getCard();
                    if (selectedCard.getType() == CardType.ACTION) {
                        ActionCard actionCard = (ActionCard) selectedCard;
                        ImagePanel panel = (ImagePanel) panels.get(name);
                        if (actionCard.getActions().contains(ActionType.MAP)) {
                            if (panel.getCard() != null) {
                                PathCard pathCard = (PathCard) panel.getCard();
                                if (pathCard.getGold()) {
                                    if (pathCard.getTreasure()) {
                                        putTextOnBoard("Treasure is here!");
                                    } else {
                                        putTextOnBoard("No treasure!");
                                    }
                                    gameController.addNewCardToPlayer(selectedCard);
                                    setChangePlayerTimer();
                                } else {
                                    putTextOnBoard("Not a target card!");
                                }
                            } else {
                                putTextOnBoard("No card here!");
                            }
                        } else if (actionCard.getActions().contains(ActionType.ROCKFALL)) {
                            if (panel.getCard() != null) {
                                PathCard pathCard = (PathCard) panel.getCard();
                                if (pathCard.getStart() || pathCard.getGold()) {
                                    putTextOnBoard("It can't be deleted!");
                                } else {
                                    panel.setCard(null);
                                    panel.setImage(null);
                                    changePlayer = true;
                                    gameController.addNewCardToPlayer(selectedCard);
                                }
                            } else {
                                putTextOnBoard("No card here!");
                            }
                        } else {
                            putTextOnBoard("Wrong card!");
                        }
                    } else if (selectedCard.getType() == CardType.PATH) {
                        try {
                            if (gameController.isPlayerBlocked()) {
                                putTextOnBoard("You are blocked!");
                                return;
                            }
                            PathCard pathCard = (PathCard) selectedCard;
                            String prefix = "card_";
                            ImagePanel[] neighbours = new ImagePanel[]{
                                    (ImagePanel) panels.get(prefix + row + "_" + (column - 1)),
                                    (ImagePanel) panels.get(prefix + row + "_" + (column + 1)),
                                    (ImagePanel) panels.get(prefix + (row + 1) + "_" + column),
                                    (ImagePanel) panels.get(prefix + (row - 1) + "_" + column)
                            };
                            PathCard[] neighbourCards = new PathCard[]{
                                    neighbours[0] != null ? (PathCard) neighbours[0].getCard() : null,
                                    neighbours[1] != null ? (PathCard) neighbours[1].getCard() : null,
                                    neighbours[2] != null ? (PathCard) neighbours[2].getCard() : null,
                                    neighbours[3] != null ? (PathCard) neighbours[3].getCard() : null
                            };

                            if (neighbourCards[0] == null && neighbourCards[1] == null && neighbourCards[2] == null && neighbourCards[3] == null) {
                                putTextOnBoard("It must connect!");
                                return;
                            }

                            if (doesCardNotFit(neighbourCards[0], pathCard, Direction.RIGHT, Direction.LEFT, row, column) ||
                                    doesCardNotFit(neighbourCards[1], pathCard, Direction.LEFT, Direction.RIGHT, row, column) ||
                                    doesCardNotFit(neighbourCards[2], pathCard, Direction.UP, Direction.DOWN, row, column) ||
                                    doesCardNotFit(neighbourCards[3], pathCard, Direction.DOWN, Direction.UP, row, column)) {
                                putTextOnBoard("It does not fit!");
                                return;
                            }

                            ImagePanel panel = (ImagePanel) panels.get(name);
                            panel.setCard(pathCard);
                            panel.setImage(pathCard.getImage());
                            selectedPanel.restartBorder();

                            changePlayer = true;
                            gameController.addNewCardToPlayer(selectedCard);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        putTextOnBoard("Wrong card!");
                    }
                    if (changePlayer) {
                        try {
                            gameController.changePlayer();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}


