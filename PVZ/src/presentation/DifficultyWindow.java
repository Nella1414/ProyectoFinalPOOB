package presentation;

import javax.swing.*;
import java.awt.*;

public class DifficultyWindow extends JFrame {
    public DifficultyWindow() {
        setTitle("Difficulty");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        // Add components for difficulty selection

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Select a Difficulty"));

        setContentPane(panel);
    }
}