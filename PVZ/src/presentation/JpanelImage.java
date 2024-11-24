package presentation;

import javax.swing.*;
import java.awt.*;

public class JpanelImage extends JPanel {
    private Image image;

    public JpanelImage(String imagePath) {
        try {
            image = new ImageIcon(getClass().getClassLoader().getResource(imagePath)).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}