package presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PVZGUI extends JFrame {

    public PVZGUI() {
        JpanelImage imagePanel = new JpanelImage("Images/inGameFirstScreen/MainMenu.png");
        setContentPane(imagePanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1000, 625);
        setLocationRelativeTo(null);

        imagePanel.setLayout(null); // Asegúrate de establecer el diseño nulo

        try {
            BufferedImage originalImage = ImageIO.read(getClass().getClassLoader().getResource("Images/inGameFirstScreen/Button.png"));

            // Primer botón
            JButton startButton = createButton(originalImage, 500, 100, 450, 300);
            startButton.addActionListener(createStartButtonActionListener(startButton));
            imagePanel.add(startButton);

            // Segundo botón
            JButton secondButton = createButton(originalImage, 500, 200, 380, 200);
            secondButton.addActionListener(createSecondButtonActionListener());
            imagePanel.add(secondButton);

            // Tercer botón
            JButton thirdButton = createButton(originalImage, 500, 300, 300, 150);
            thirdButton.addActionListener(createThirdButtonActionListener());
            imagePanel.add(thirdButton);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JButton createButton(BufferedImage originalImage, int x, int y, int width, int height) {
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(resizedImage));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setBounds(x, y, width, height);
        return button;
    }

    private ActionListener createStartButtonActionListener(JButton startButton) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Animación de minisalto
                Timer timer = new Timer(10, new ActionListener() {
                    int y = startButton.getY();
                    int direction = -1;
                    int count = 0;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (count == 0) {
                            direction = -1;
                        } else if (count == 10) {
                            direction = 1;
                        }
                        if (count < 20) {
                            startButton.setLocation(startButton.getX(), y + direction);
                            count++;
                        } else {
                            ((Timer) e.getSource()).stop();
                            // Abrir la nueva ventana
                            new GameModesWindow().setVisible(true);
                            PVZGUI.this.dispose();
                        }
                    }
                });
                timer.start();
            }
        };
    }

    private ActionListener createSecondButtonActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir la ventana de modo de juego
                new oneVsOne().setVisible(true);
                PVZGUI.this.dispose();
            }
        };
    }

    private ActionListener createThirdButtonActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir la ventana de dificultad
                new DifficultyWindow().setVisible(true);
                PVZGUI.this.dispose();
            }
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PVZGUI().setVisible(true);
        });
    }
}