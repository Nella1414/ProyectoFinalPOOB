package presentation;

import javax.swing.*;
import java.awt.*;

public class GameModesWindow extends JFrame {

    public GameModesWindow() {
        setTitle("Game Modes");
        setSize(1000, 625);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Select a game mode"));
        // Agrega más componentes según sea necesario

        setContentPane(panel);
    }
}