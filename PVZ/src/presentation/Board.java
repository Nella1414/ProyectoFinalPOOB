package presentation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Board extends JFrame {
    private String difficulty;

    public Board(String difficulty) {
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
            JLabel shovel = createLabel(shovelButton, 1170, 50, 65, 70);
            shovel.addMouseListener(createShovelMouseListener(shovel, shovelButton));
            background.add(shovel);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Board board = new Board("Novato");
            board.setVisible(true);
        });
    }
}