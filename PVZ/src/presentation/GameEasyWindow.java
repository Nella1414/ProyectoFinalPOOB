package presentation;

import controllers.GameLogicWorker;
import domain.board.BoardDay;
import domain.entities.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import domain.board.Board;
import domain.tools.Shovel;

public class GameEasyWindow extends JFrame {
    private final String difficulty;
    private final int gridSize = 90; // Tamaño de cada cuadro de la grilla
    private final int minX = 360;
    private final int maxX = 1080;
    private final int minY = 270;
    private final int maxY = 630;
    private int suns = 0;
    private final int LawnCleaner = 270;
    private final List<Entity> entitiesForHud;
    private Entity selectedPlant = null;
    private boolean isShovelSelected = false;// Variable to track if the shovel is selected
    private Board board;

    private List<Entity> entitiesInGame = new ArrayList<>();

    private JLabel sunCounter;

    private JpanelImage1 background;

    public GameEasyWindow(String difficulty, List<Entity> entitiesForHud, Board board) {
        this.difficulty = difficulty;
        this.entitiesForHud = new ArrayList<>(entitiesForHud); // Convert to mutable list
        setTitle("Board - " + difficulty);
        background = new JpanelImage1("assets/Images/inGame/board/backyardGood.png");
        setContentPane(background);
        setSize(1400, 788);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        background.setLayout(null);
        this.board = board;
        this.suns = board.getSunPoints();
        new GameLogicWorker(board, this).execute();




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
        sunCounter = new JLabel(getSuns());
        sunCounter.setBounds(570, 130, 50, 50);
        sunCounter.setFont(new Font("Arial", Font.BOLD, 20));
        sunCounter.setForeground(Color.BLACK);
        background.add(sunCounter);
        background.setComponentZOrder(sunCounter, 0);

        // Añadir entidades al tablero
        int x = 622; // Posición x inicial
        int y = 76; // Posición y inicial
        int spacing = 96; // Espaciado entre plantas


        for (Entity entity : entitiesForHud) {
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
                for (Entity entity : entitiesInGame) {
                    if (entity.getPosition().equals(new Point(gridX, gridY))) {
                        entitiesInGame.remove(entity);
                        board.removePlant(entity.getPosition());

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
                    Plant newPlant = (Plant) selectedPlant.getClass().getConstructor(Board.class, Point.class).newInstance(board, new Point(gridX, gridY));
                    if (board.addPlant(newPlant, new Point(gridX, gridY))) {
                        entitiesInGame.add(newPlant);
                        JLabel plantLabel = new JLabel(newPlant.getIcon());
                        plantLabel.setBounds(gridX + ((90 - newPlant.getIcon().getIconWidth()) / 2), gridY, newPlant.getIcon().getIconWidth(), newPlant.getIcon().getIconHeight());
                        background.add(plantLabel);
                        background.setComponentZOrder(plantLabel, 0);
                        background.repaint(); // Ensure the panel is repainted
                        System.out.println("Plant placed at: " + gridX + ", " + gridY);
                        selectedPlant = null; // Reset selected plant
                    } else {
                        System.out.println("Failed to place plant at: " + gridX + ", " + gridY);
                    }
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


    public void spawnRandomZombie() {
        Random random = new Random();
        int row = random.nextInt(5); // Assuming there are 5 rows
        int gridY = 240 + (row * gridSize);
        int gridX = maxX; // Zombies appear at the last position of the grid

        try {
            BasicZombie newZombie = new BasicZombie(board, new Point(gridX, gridY));
            if (board.addZombie(newZombie, new Point(gridX, gridY))) {
                JLabel zombieLabel = new JLabel(newZombie.getIcon());
                zombieLabel.setBounds(gridX, gridY, newZombie.getIcon().getIconWidth(), newZombie.getIcon().getIconHeight());
                zombieLabel.setName(newZombie.getName());
                background.add(zombieLabel);
                background.setComponentZOrder(zombieLabel, 0);
                background.repaint(); // Ensure the panel is repainted
                System.out.println("Zombie spawned at: " + gridX + ", " + gridY);
            } else {
                System.out.println("Failed to spawn zombie at: " + gridX + ", " + gridY);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public JLabel getZombieLabel(Zombie zombie) {
        for (Component component : background.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                String labelName = label.getName(); // Usar getName() en lugar de getText()
                if (labelName != null && labelName.equals(zombie.getName())) {
                    return label;
                }
            }
        }
        return null;
    }

    public void updateAllZombiePositions() {
        SwingUtilities.invokeLater(() -> {
            // Remove all existing zombie labels
            List<Component> componentsToRemove = new ArrayList<>();
            for (Component component : background.getComponents()) {
                if (component instanceof JLabel && ((JLabel) component).getName() != null && ((JLabel) component).getName().startsWith("BasicZombie")) {
                    componentsToRemove.add(component);
                }
            }
            for (Component component : componentsToRemove) {
                background.remove(component);
            }

            // Add new zombie labels at updated positions
            for (Zombie zombie : board.getZombies().values()) {
                Point position = zombie.getPosition();
                JLabel newZombieLabel = new JLabel(zombie.getIcon());
                newZombieLabel.setBounds(position.x, position.y, zombie.getIcon().getIconWidth(), zombie.getIcon().getIconHeight());
                newZombieLabel.setName(zombie.getName());
                background.add(newZombieLabel);
                background.setComponentZOrder(newZombieLabel, 0);
            }
            background.revalidate(); // Ensure the panel layout is updated
            background.repaint(); // Ensure the panel is repainted
        });
    }
    public void updateSuns() {
        this.suns = this.board.getSunPoints();
    }
    public void updateSunsLabel() {
        updateSuns();
        sunCounter.setText(getSuns());
        System.out.println("Suns updated: " + board.getSunPoints());
    }
    public static void main(String[] args) {
        // Entidades de ejemplo
        BoardDay board = new BoardDay(1000);
        List<Entity> entities = List.of(
                new Sunflower(board, new Point(0, 0)),
                new Peashooter(board, new Point(0, 0)),
                new WallNut(board,new Point(0, 0)),
                new PotatoMine(board,new Point(0, 0)),
                new ECIPlant(board, new Point(0, 0)));



        SwingUtilities.invokeLater(() -> {
            GameEasyWindow game = new GameEasyWindow("Novato", new ArrayList<>(entities),board); // Convert to mutable list
            game.setVisible(true);
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
                if (isPixelVisible(e, plantLabel, new ImageIcon(getClass().getClassLoader().getResource("assets/Images/inGame/plants/ECIPlant.gif")))) {
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