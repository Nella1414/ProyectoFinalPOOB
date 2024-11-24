package presentation;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class PVZGUI extends JFrame {

    public PVZGUI() {
        playBackgroundMusic("Sounds/1.StartInGameMusic.wav");

        JpanelImage imagePanel = new JpanelImage("Images/inGameFirstScreen/MainMenu.png");
        setContentPane(imagePanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1000, 625);
        setLocationRelativeTo(null);

        imagePanel.setLayout(null); // Asegúrate de establecer el diseño nulo

        try {
            BufferedImage originalImage = ImageIO.read(getClass().getClassLoader().getResource("Images/inGameFirstScreen/Button.png"));
            BufferedImage originalImage2 = ImageIO.read(getClass().getClassLoader().getResource("Images/inGameFirstScreen/InitGameButton.png"));

            // Panel transparente para manejar eventos de clic
            JPanel buttonPanel = new JPanel(null);
            buttonPanel.setOpaque(false);
            buttonPanel.setBounds(0, 0, 1000, 625);
            imagePanel.add(buttonPanel);

            // Botón de inicio
            JLabel startButton = createLabel(originalImage2, 515, 60, 370, 200);
            startButton.addMouseListener(createStartButtonMouseListener(startButton, originalImage2));
            buttonPanel.add(startButton);

            // Segundo botón
            JLabel secondButton = createLabel(originalImage, 510, 200, 355, 150);
            secondButton.addMouseListener(createSecondButtonMouseListener(secondButton, originalImage));
            buttonPanel.add(secondButton);

            // Tercer botón
            JLabel thirdButton = createLabel(originalImage, 515, 290, 330, 150);
            thirdButton.addMouseListener(createThirdButtonMouseListener(thirdButton, originalImage));
            buttonPanel.add(thirdButton);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PVZGUI().setVisible(true);
        });
    }

    private JLabel createLabel(BufferedImage originalImage, int x, int y, int width, int height) {
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(resizedImage));
        label.setBounds(x, y, width, height);
        return label;
    }

    private MouseAdapter createStartButtonMouseListener(JLabel startButton, BufferedImage image) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isPixelVisible(e, startButton, image)) {
                    animateButtonAndOpenWindow(startButton, new GameModesWindow());
                }
            }
        };
    }

    private MouseAdapter createSecondButtonMouseListener(JLabel secondButton, BufferedImage image) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isPixelVisible(e, secondButton, image)) {
                    animateButtonAndOpenWindow(secondButton, new oneVsOne());
                }
            }
        };
    }

    private MouseAdapter createThirdButtonMouseListener(JLabel thirdButton, BufferedImage image) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isPixelVisible(e, thirdButton, image)) {
                    animateButtonAndOpenWindow(thirdButton, new DifficultyWindow());
                }
            }
        };
    }

    private boolean isPixelVisible(MouseEvent e, JLabel label, BufferedImage image) {
        Point point = e.getPoint();
        int x = point.x * image.getWidth() / label.getWidth();
        int y = point.y * image.getHeight() / label.getHeight();
        int pixel = image.getRGB(x, y);
        return (pixel >> 24) != 0x00; // Verifica si el píxel no es transparente
    }

    private void animateButtonAndOpenWindow(JLabel button, JFrame window) {
        Timer timer = new Timer(10, new ActionListener() {
            final int y = button.getY();
            int direction = -1;
            int count = 0;

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (count == 0) {
                    direction = -1;
                } else if (count == 10) {
                    direction = 1;
                }
                if (count < 20) {
                    button.setLocation(button.getX(), y + direction);
                    count++;
                } else {
                    ((Timer) e.getSource()).stop();
                    window.setVisible(true);
                    PVZGUI.this.dispose();
                }
            }
        });
        timer.start();
    }

    private void playBackgroundMusic(String path) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource(path));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}