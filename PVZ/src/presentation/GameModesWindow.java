package presentation;

import domain.board.BoardDay;
import domain.entities.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GameModesWindow extends JFrame {
    private final JTextField sunsField;
    private Clip backgroundMusicClip;
    private String selectedDifficulty;
    private int initialSuns;
    private List<Entity> entities;

    public GameModesWindow() {
        playBackgroundMusic("assets/Sounds/1.StartInGameMusic.wav");

        setTitle("Game Modes");
        setSize(1000, 677);
        JpanelImage1 background = new JpanelImage1("assets/Images/gameModesWindow/QuickPlay.png");
        setContentPane(background);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        background.setLayout(null);

        // Create a custom JComboBox
        String[] gameModes = {"Novato", "Medio", "Experto"};
        JComboBox<String> comboBox = new JComboBox<>(gameModes);
        comboBox.setBounds(400, 300, 200, 30);
        comboBox.setRenderer(new CustomComboBoxRenderer());
        comboBox.setOpaque(false);
        background.add(comboBox);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDifficulty = (String) comboBox.getSelectedItem();
            }
        });

        // Create a custom JTextField to select initial suns
        sunsField = new JTextField("200");
        sunsField.setBounds(400, 400, 200, 30);
        sunsField.setHorizontalAlignment(JTextField.CENTER);
        sunsField.setFont(new Font("Arial", Font.BOLD, 16));
        sunsField.setForeground(Color.BLACK);
        sunsField.setOpaque(false);
        background.add(sunsField);

        // Create a custom JButton
        try {
            BufferedImage buttonImage = ImageIO.read(getClass().getClassLoader().getResource("assets/Images/gameModesWindow/StartButton.png"));
            JLabel startButton = createLabel(buttonImage, 765, 450, 160, 150);
            startButton.addMouseListener(createStartButtonMouseListener(startButton, buttonImage));
            background.add(startButton);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Add components for game mode selection
        JLabel label = new JLabel("Select a game mode");

        final InputStream is = getClass().getClassLoader().getResourceAsStream("assets/Fonts/pvz.ttf");

        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(20f);
            label.setFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        label.setForeground(Color.BLACK);
        label.setBounds(400, 250, 300, 30);
        background.add(label);

        JLabel label2 = new JLabel("Select initial suns");

        final InputStream is2 = getClass().getClassLoader().getResourceAsStream("assets/Fonts/pvz.ttf");

        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, is2).deriveFont(20f);
            label2.setFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        label2.setForeground(Color.BLACK);
        label2.setBounds(400, 350, 300, 30);
        background.add(label2);

        // Create a Back button with custom image
        try {
            BufferedImage backButtonImage = ImageIO.read(getClass().getClassLoader().getResource("assets/Images/gameModesWindow/BackButton.png"));
            JLabel backButton = createLabel(backButtonImage, 50, 50, 89, 26);
            backButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new PVZGUI().setVisible(true);
                    GameModesWindow.this.dispose();
                }
            });
            background.add(backButton);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GameModesWindow().setVisible(true);
        });
    }

    private JLabel createLabel(BufferedImage originalImage, int x, int y, int width, int height) {
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(resizedImage));
        label.setBounds(x, y, width, height);
        return label;
    }

    private MouseAdapter createStartButtonMouseListener(JLabel startButton, BufferedImage originalImage2) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isPixelVisible(e, startButton, originalImage2)) {
                    playClickSound("assets/Sounds/2.Click.wav");
//                    showLoadingScreenAndOpenBoard();
                }
            }
        };
    }

    private boolean isPixelVisible(MouseEvent e, JLabel label, BufferedImage image) {
        Point point = e.getPoint();
        int x = point.x * image.getWidth() / label.getWidth();
        int y = point.y * image.getHeight() / label.getHeight();
        int pixel = image.getRGB(x, y);
        return (pixel >> 24) != 0x00; // Verifies if the pixel is not transparent
    }

    private void playClickSound(String soundPath) {
        // Play a sound when the button is clicked
    }

    private void playBackgroundMusic(String path) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource(path));
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioInputStream);
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopBackgroundMusic() {
        if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop();
            backgroundMusicClip.close();
        }
    }


}

class CustomComboBoxRenderer extends JLabel implements ListCellRenderer<Object> {
    public CustomComboBoxRenderer() {
        setOpaque(false);
        setFont(new Font("Arial", Font.BOLD, 16));
        setForeground(Color.BLACK);
        setHorizontalAlignment(CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());
        if (isSelected) {
            setBackground(Color.BLACK);
            setForeground(Color.GREEN);
        } else {
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
        }
        return this;
    }
}