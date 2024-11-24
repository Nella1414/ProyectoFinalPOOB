package presentation;

import javax.swing.*;
import java.awt.*;

public class OneVsOne extends JFrame {
    public OneVsOne() {
        setTitle("One vs One");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        // Add components for game mode selection

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Select a 1 vs 1 game mode"));

        setContentPane(panel);
    }
}
