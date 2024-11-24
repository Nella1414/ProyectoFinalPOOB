package presentation;

import domain.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;

public class Board extends JFrame {
    private final String difficulty;

    public Board(String difficulty, List<Entity> entities) {
        this.difficulty = difficulty;
        setTitle("Board - " + difficulty);
        JpanelImage background = new JpanelImage("assets/Images/inGame/board/backyardGood.png");
        setContentPane(background);
        setSize(1400, 788);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        background.setLayout(null);

        // Add more components and logic based on the difficulty

        JLabel boardtable = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("assets/Images/inGame/board/boardTable.png")));
        boardtable.setBounds(500, 50, 700, 146);
        background.add(boardtable);

        // Shovel
        try {
            BufferedImage shovelButton = ImageIO.read(getClass().getClassLoader().getResource("assets/Images/inGame/board/shovel.png"));
            JLabel shovel = createLabel(shovelButton, 1100, 85, 65, 70);
            shovel.addMouseListener(createShovelMouseListener(shovel, shovelButton));
            background.add(shovel);
            background.setComponentZOrder(shovel, 0); //0 es que esta en el top de las capas
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Sun
        JLabel sun = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("assets/Images/inGame/board/Sun.png")));
        sun.setBounds(522, 72, 100, 100);
        background.add(sun);
        background.setComponentZOrder(sun, 0);

        // Sun counter
        JLabel sunCounter = new JLabel(getSuns());
        sunCounter.setBounds(570, 130, 50, 50);
        sunCounter.setFont(new Font("Arial", Font.BOLD, 20));
        sunCounter.setForeground(Color.BLACK);
        background.add(sunCounter);
        background.setComponentZOrder(sunCounter, 0);

        // Add entities to the board
        int x = 622; // Starting x position
        int y = 76; // Starting y position
        int spacing = 96; // Spacing between plants

        for (Entity entity : entities) {
            JLabel entityLabel = new JLabel(entity.getIcon());
            entityLabel.setBounds(x, y, entity.getIcon().getIconWidth(), entity.getIcon().getIconHeight());
            entityLabel.addMouseListener(createPlantMouseListener(entityLabel, entity));
            background.add(entityLabel);
            background.setComponentZOrder(entityLabel, 0);

            JLabel costLabel = new JLabel(String.valueOf(entity.getCost()));
            costLabel.setBounds(x + (entity.getIcon().getIconWidth() / 2), y + entity.getIcon().getIconHeight(), 50, 20);
            costLabel.setFont(new Font("Arial", Font.BOLD, 16));
            costLabel.setForeground(Color.BLACK);
            background.add(costLabel);
            background.setComponentZOrder(costLabel, 0);

            x += spacing; // Update y position for the next plant
        }
    }

    //Cuantos soles tiene el jugador
    private String getSuns() {
        return "100";
    }

    public static void main(String[] args) {
        // Example entities
        List<Entity> entities = List.of(
                new Sunflower(new Point(0, 0)),
                new Peashooter(new Point(0, 0)),
                new WallNut(new Point(0, 0)),
                new PotatoMine(new Point(0, 0)),
                new ECIPlant(new Point(0, 0))
        );

        SwingUtilities.invokeLater(() -> {
            Board board = new Board("Novato", entities);
            board.setVisible(true);
        });
    }

    private MouseAdapter createShovelMouseListener(JLabel shovel, BufferedImage shovelButton) {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isPixelVisible(e, shovel, shovelButton)) {
                    System.out.println("Shovel clicked");
                }
            }
        };
    }

    private MouseAdapter createPlantMouseListener(JLabel plantLabel, Entity plant) {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isPixelVisible(e, plantLabel, plant.getIcon())) {
                    System.out.println(plant.getName() + " clicked");
                    // Add additional logic for plant click event here
                }
            }
        };
    }

    private boolean isPixelVisible(MouseEvent e, JLabel label, ImageIcon icon) {
        Point point = e.getPoint();
        int x = point.x * icon.getIconWidth() / label.getWidth();
        int y = point.y * icon.getIconHeight() / label.getHeight();
        Image image = icon.getImage();
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bufferedImage.createGraphics();
        bGr.drawImage(image, 0, 0, null);
        bGr.dispose();
        int pixel = bufferedImage.getRGB(x, y);
        return (pixel >> 24) != 0x00; // Verifies if the pixel is not transparent
    }

    private boolean isPixelVisible(MouseEvent e, JLabel label, BufferedImage image) {
        Point point = e.getPoint();
        int x = point.x * image.getWidth() / label.getWidth();
        int y = point.y * image.getHeight() / label.getHeight();
        int pixel = image.getRGB(x, y);
        return (pixel >> 24) != 0x00; // Verifies if the pixel is not transparent
    }

    private JLabel createLabel(BufferedImage originalImage, int x, int y, int width, int height) {
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(resizedImage));
        label.setBounds(x, y, width, height);
        return label;
    }
}