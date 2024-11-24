package presentation;

import javax.swing.*;
import java.awt.*;

public class Board extends JFrame {
    private String difficulty;

    public Board(String difficulty) {
        this.difficulty = difficulty;
        setTitle("Board - " + difficulty);
        JpanelImage background = new JpanelImage("assets/Images/inGame/board/backyard.png");
        setContentPane(background);
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        background.setLayout(null);

        // Add more components and logic based on the difficulty
    }
}