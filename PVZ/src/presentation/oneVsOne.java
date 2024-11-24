package presentation;

import javax.swing.*;
import java.awt.*;

public class oneVsOne extends JFrame {
    public oneVsOne() {
        setTitle("One vs One");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        // Add components for game mode selection

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Select a game mode"));
    }
}
