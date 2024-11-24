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

        JLabel loadingLabel = new JLabel("Loading...", SwingConstants.CENTER);
        add(loadingLabel, BorderLayout.CENTER);
    }
}