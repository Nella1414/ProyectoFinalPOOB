package presentation;

import domain.Entities.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Board extends JFrame {
    private final String difficulty;
    private final int gridSize = 90; // Tamaño de cada cuadro de la grilla
    private final int minX = 360;
    private final int maxX = 1080;
    private final int minY = 270;
    private final int maxY = 630;
    private final int suns = 100;
    private final int LawnCleaner = 270;
    private final List<Entity> entities;
    private Entity selectedPlant = null;
    private boolean isShovelSelected = false; // Variable to track if the shovel is selected

    public Board(String difficulty, List<Entity> entities) {
        this.difficulty = difficulty;
        this.entities = new ArrayList<>(entities); // Convert to mutable list
        setTitle("Board - " + difficulty);
        JpanelImage1 background = new JpanelImage1("assets/Images/inGame/board/backyardGood.png");
        setContentPane(background);
        setSize(1400, 788);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        background.setLayout(null);

        // Añadir más componentes y lógica basada en la dificultad

        JLabel boardtable = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("assets/Images/inGame/board/boardTable.png")));
        boardtable.setBounds(500, 50, 700, 146);
        background.add(boardtable);

        // Cortadora de pasto

        for (int i = 0; i < 5; i++) {
            JLabel lawnCleaner = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("assets/Images/inGame/board/LawnCleaner.png")));
            lawnCleaner.setBounds(270, LawnCleaner + (i * 90), 90, 90);
            background.add(lawnCleaner);
            background.setComponentZOrder(lawnCleaner, 0);
        }

        // Pala
        try {
            BufferedImage shovelButton = ImageIO.read(getClass().getClassLoader().getResource("assets/Images/inGame/board/shovel.png"));
            JLabel shovel = createLabel(shovelButton, 1100, 85, 65, 70);
            shovel.addMouseListener(createShovelMouseListener(shovel, shovelButton));
            background.add(shovel);
            background.setComponentZOrder(shovel, 0); //0 es que está en el top de las capas
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Sol
        JLabel sun = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("assets/Images/inGame/board/Sun.png")));
        sun.setBounds(522, 72, 100, 100);
        background.add(sun);
        background.setComponentZOrder(sun, 0);

        // Contador de soles
        JLabel sunCounter = new JLabel(getSuns());
        sunCounter.setBounds(570, 130, 50, 50);
        sunCounter.setFont(new Font("Arial", Font.BOLD, 20));
        sunCounter.setForeground(Color.BLACK);
        background.add(sunCounter);
        background.setComponentZOrder(sunCounter, 0);

        // Añadir entidades al tablero
        int x = 622; // Posición x inicial
        int y = 76; // Posición y inicial
        int spacing = 96; // Espaciado entre plantas

        for (Entity entity : entities) {
            JLabel entityLabel = new JLabel(entity.getIcon());
            entityLabel.setBounds(x, y + ((70 - (entity.getIcon().getIconHeight())) / 2), entity.getIcon().getIconWidth(), entity.getIcon().getIconHeight());
            entityLabel.addMouseListener(createPlantMouseListener(entityLabel, entity));
            background.add(entityLabel);
            background.setComponentZOrder(entityLabel, 0);

            JLabel sunLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("assets/Images/inGame/board/LittleSun.png")));
            sunLabel.setBounds(x + (entity.getIcon().getIconWidth() / 2) - 25, 150, 20, 20);
            background.add(sunLabel);
            background.setComponentZOrder(sunLabel, 0);

            JLabel costLabel = new JLabel(String.valueOf(entity.getCost()));
            costLabel.setBounds(x + (entity.getIcon().getIconWidth() / 2), 150, 50, 20);
            costLabel.setFont(new Font("Arial", Font.BOLD, 16));
            costLabel.setForeground(Color.BLACK);
            background.add(costLabel);
            background.setComponentZOrder(costLabel, 0);

            x += spacing; // Actualizar posición x para la siguiente planta
        }

        // Añadir mouse listener al fondo para colocar plantas o eliminar plantas
        background.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point clickPoint = e.getPoint();
                int gridX = (clickPoint.x / gridSize) * gridSize;
                int gridY = (clickPoint.y / gridSize) * gridSize;

                if (gridX >= minX && gridX <= maxX && gridY >= minY && gridY <= maxY) {
                    if (isShovelSelected) {
                        // Remove plant if shovel is selected
                        for (Entity entity : entities) {
                            if (entity.getPosition().equals(new Point(gridX, gridY))) {
                                entities.remove(entity);
                                Component componentToRemove = null;
                                for (Component component : background.getComponents()) {
                                    if (component.getBounds().contains(gridX + 20, gridY)) {
                                        componentToRemove = component;
                                        break;
                                    }
                                }
                                if (componentToRemove != null) {
                                    background.remove(componentToRemove);
                                    background.repaint(); // Ensure the panel is repainted
                                }
                                System.out.println("Plant removed at: " + gridX + ", " + gridY);
                                break;
                            }
                        }
                        isShovelSelected = false; // Reset shovel selection
                    } else if (selectedPlant != null) {
                        // Add plant if a plant is selected
                        try {
                            Entity newPlant = selectedPlant.getClass().getConstructor(Point.class).newInstance(new Point(gridX, gridY));
                            entities.add(newPlant);

                            JLabel plantLabel = new JLabel(newPlant.getIcon());
                            plantLabel.setBounds(gridX + ((90 - newPlant.getIcon().getIconWidth()) / 2), gridY, newPlant.getIcon().getIconWidth(), newPlant.getIcon().getIconHeight());
                            background.add(plantLabel);
                            background.setComponentZOrder(plantLabel, 0);
                            background.repaint(); // Ensure the panel is repainted
                            System.out.println("Plant placed at: " + gridX + ", " + gridY);

                            selectedPlant = null; // Reset selected plant
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        System.out.println("No plant selected or shovel not selected." + gridX + "," + gridY);
                    }
                } else {
                    System.out.println("Click outside the allowed grid area." + gridX + "," + gridY);
                }
            }
        });

        // Dibujar la cuadrícula
        background.setGridSize(gridSize);
    }

    public static void main(String[] args) {
        // Entidades de ejemplo
        List<Entity> entities = List.of(
                new Sunflower(new Point(0, 0)),
                new Peashooter(new Point(0, 0)),
                new WallNut(new Point(0, 0)),
                new PotatoMine(new Point(0, 0)),
                new ECIPlant(new Point(0, 0)));

        SwingUtilities.invokeLater(() -> {
            Board board = new Board("Novato", new ArrayList<>(entities)); // Convert to mutable list
            board.setVisible(true);
        });
    }

    // Cuántos soles tiene el jugador
    private String getSuns() {
        return String.valueOf(suns);
    }

    private MouseAdapter createShovelMouseListener(JLabel shovel, BufferedImage shovelButton) {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isPixelVisible(e, shovel, shovelButton)) {
                    System.out.println("Shovel clicked");
                    isShovelSelected = true; // Set shovel as selected
                }
            }
        };
    }

    private MouseAdapter createPlantMouseListener(JLabel plantLabel, Entity plant) {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isPixelVisible(e, plantLabel, plant.getIcon())) {
                    System.out.println(plant.getName() + " clicked");
                    selectedPlant = plant; // Set the selected plant
                }
            }
        };
    }

    private boolean isPixelVisible(MouseEvent e, JLabel label, ImageIcon icon) {
        Point point = e.getPoint();
        int x = point.x * icon.getIconWidth() / label.getWidth();
        int y = point.y * icon.getIconHeight() / label.getHeight();
        Image image = icon.getImage();
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bufferedImage.createGraphics();
        bGr.drawImage(image, 0, 0, null);
        bGr.dispose();
        int pixel = bufferedImage.getRGB(x, y);
        return (pixel >> 24) != 0x00; // Verifies if the pixel is not transparent
    }

    private boolean isPixelVisible(MouseEvent e, JLabel label, BufferedImage image) {
        Point point = e.getPoint();
        int x = point.x * image.getWidth() / label.getWidth();
        int y = point.y * image.getHeight() / label.getHeight();
        int pixel = image.getRGB(x, y);
        return (pixel >> 24) != 0x00; // Verifies if the pixel is not transparent
    }

    private JLabel createLabel(BufferedImage originalImage, int x, int y, int width, int height) {
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(resizedImage));
        label.setBounds(x, y, width, height);
        return label;
    }
}

class JpanelImage1 extends JPanel {
    private final String path;
    private BufferedImage image;
    private int gridSize;

    public JpanelImage1(String path) {
        this.path = path;
        try {
            image = ImageIO.read(getClass().getClassLoader().getResource(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);

//        g.setColor(Color.RED);
//        for (int i = 0; i < getWidth(); i += gridSize) {
//            for (int j = 0; j < getHeight(); j += gridSize) {
//                g.drawRect(i, j, gridSize, gridSize);
//            }
//        }
    }
}