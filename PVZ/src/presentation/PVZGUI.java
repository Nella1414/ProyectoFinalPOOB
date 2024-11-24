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
        int buttonWidth = 380;
        int buttonHeight = 152;

        imagePanel.setLayout(null); // Asegúrate de establecer el diseño nulo

        try {
            BufferedImage originalImage = ImageIO.read(getClass().getClassLoader().getResource("Images/inGameFirstScreen/ChallengeButton.png"));
            Image resizedImage = originalImage.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
            JButton startButton = new JButton(new ImageIcon(resizedImage));
            startButton.setContentAreaFilled(false);
            startButton.setBorderPainted(false);
            startButton.setFocusPainted(false);
            startButton.setOpaque(false);
            startButton.setBounds(500, 200, buttonWidth, buttonHeight); // Establece la posición y el tamaño del botón
            startButton.addActionListener(createStartButtonActionListener(startButton));
            imagePanel.add(startButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PVZGUI().setVisible(true);
        });
    }
}