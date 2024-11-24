package presentation;

import javax.swing.*;
import java.awt.*;

public class Board extends JFrame {
    private String difficulty;

    public Board(String difficulty) {
        this.difficulty = difficulty;
        setTitle("Board - " + difficulty);
        JpanelImage background = new JpanelImage("assets/Images/inGame/board/background.png");
        setSize(1000, 677);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setContentPane(background);
        background.setLayout(null);
        // Add more components and logic based on the difficulty
    }
}