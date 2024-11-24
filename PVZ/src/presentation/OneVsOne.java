package presentation;

import javax.swing.*;
import java.awt.*;

public class OneVsOne extends JFrame {
    public OneVsOne() {
        //playBackgroundMusic("Assets/Sounds/1.StartInGameMusic.wav");

        JpanelImage imagePanel = new JpanelImage("Assets/Images/oneVsOne/Main.png");
        setTitle("One vs One");
        setSize(1000, 667);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLocationRelativeTo(null);
        // Add components for game mode selection

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Select a 1 vs 1 game mode"));

        setContentPane(panel);
    }
}
