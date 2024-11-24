package presentation;

import javax.swing.*;
import java.awt.*;

public class LoadingScreen extends JDialog {
    public LoadingScreen(JFrame parent) {
        super(parent, "Loading", true);
        setUndecorated(true);
        setSize(1000, 667);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Set background color to black
        getContentPane().setBackground(Color.BLACK);

        // Add loading GIF
        JLabel loadingGifLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("assets/Images/forEverything/loading.gif")));
        loadingGifLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(loadingGifLabel, BorderLayout.CENTER);
    }
}