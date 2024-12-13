package presentation;

import controllers.GameLogicWorker;
import domain.board.Board;
import domain.board.BoardDay;
import domain.entities.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEasyWindow extends JFrame {
    private final String difficulty;
    private final int gridSize = 90; // Tamaño de cada cuadro de la grilla
    private final int minX = 360;
    private final int maxX = 1080;
    private final int minY = 270;
    private final int maxY = 630;
    private final int LawnCleaner = 270;
    private final List<Entity> entitiesForHud;
    private final Board board;
    private final List<Entity> entitiesInGame = new ArrayList<>();
    private final JLabel sunCounter;
    private final JpanelImage1 background;
    private int suns = 0;
    private Entity selectedPlant = null;
    private boolean isShovelSelected = false;// Variable to track if the shovel is selected

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

        JButton pauseButton = new JButton("Pausar");
        pauseButton.setBounds(1300, 10, 80, 30);
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseGame();
            }
        });
        background.add(pauseButton);
        background.setComponentZOrder(pauseButton, 0);

        JButton resumeButton = new JButton("Reanudar");
        resumeButton.setBounds(1300, 50, 80, 30);
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeGame();
            }
        });
        background.add(resumeButton);
        background.setComponentZOrder(resumeButton, 0);


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
                                Plant plant = (Plant) entity;
                                board.removePlant(entity.getPosition());
                                board.removePlantList(plant);

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
                            Plant newPlant = (Plant) selectedPlant.getClass().getConstructor(int.class, int.class, Board.class, Point.class).newInstance(gridX, gridY, board, new Point(gridX, gridY));
                            if (board.addPlant(newPlant, new Point(gridX, gridY))) {
                                entitiesInGame.add(newPlant);
                                JLabel plantLabel = new JLabel(newPlant.getIcon());
                                plantLabel.setName(newPlant.getName()); // Set the name of the label
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
        startPlantHealthCheck();


    }

    public static void main(String[] args) {
        // Entidades de ejemplo
        BoardDay board = new BoardDay(1000);
        List<Entity> entities = List.of(new Sunflower(0, 0, board, new Point(0, 0)), new Peashooter(0, 0, board, new Point(0, 0)), new WallNut(0, 0, board, new Point(0, 0)), new PotatoMine(0, 0, board, new Point(0, 0)), new ECIPlant(0, 0, board, new Point(0, 0)));
        for (Entity entity : entities) {
            if (entity instanceof Peashooter peashooter) {
                peashooter.stopShooting();

            }
        }
        SwingUtilities.invokeLater(() -> {
            GameEasyWindow game = new GameEasyWindow("Novato", new ArrayList<>(entities), board); // Convert to mutable list
            game.setVisible(true);
        });
    }

    // GameEasyWindow.java

    public JLabel getZombieLabel(Zombie zombie) {
        for (Component component : background.getComponents()) {
            if (component instanceof JLabel label) {
                String labelName = label.getName();
                if (labelName != null && labelName.equals(zombie.getName())) {
                    return label;
                }
            }
        }
        return null;
    }


//
//    private int getZombieSpawnIntervalBasedOnDifficulty() {
//        switch (difficulty) {
//            case "Novato":
//                return 5000; // Para cada 5 segundos
//            case "Medio":
//                return 3000; // Para cada 3 segundos
//            case "Experto":
//                return 1000; // Para cada 1 segundo
//            default:
//                return 5000; // Por defecto, para cada 5 segundos
//        }
//    }
//
//    public void startZombieSpawning() {
//        int interval = getZombieSpawnIntervalBasedOnDifficulty();
//        Timer spawnTimer = new Timer(interval, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                spawnRandomZombie();
//            }
//        });
//        spawnTimer.start();
//    }

    public void pauseGame() {
        for (Zombie zombie : board.getZombies().values()) {
            zombie.stopMoving();
            zombie.stopAttacking();
        }
        for (Plant plant : board.getPlants().values()) {
            plant.stopAttacking();

        }
    }

    public void resumeGame() {
        for (Zombie zombie : board.getZombies().values()) {
            zombie.startMoving(board);
//            zombie.startAttacking(board);
        }
        for (Plant plant : board.getPlants().values()) {
            plant.startAttacking(board);
        }
    }

    // Get difficulty
    public String getDifficulty() {
        return difficulty;
    }

    public void spawnRandomZombie() {
        // List of zombie factories
        List<ZombieFactory> zombieFactories = List.of(
                new BasicZombieFactory(),
                new DuckyZombieFactory()
        );

        Random random = new Random();
        int row = random.nextInt(5); // Assuming there are 5 rows
        int gridY = 210 + (row * gridSize);
        int gridX = maxX; // Zombies appear at the last position of the grid

        // Randomly select a factory
        ZombieFactory selectedFactory = zombieFactories.get(random.nextInt(zombieFactories.size()));
        Zombie zombie = selectedFactory.createZombie(gridX, gridY, board, new Point(gridX, gridY), this);

        Point position = new Point(gridX, gridY);
        board.addZombie(zombie, position);
    }

    public void removeZombie(Zombie zombie) {
        JLabel zombieLabel = zombie.getZombieLabel();
        if (zombieLabel != null) {
            // Change the zombie's image to the dead zombie image
            ImageIcon deadZombieIcon = new ImageIcon(getClass().getClassLoader().getResource("assets/Images/inGame/zombies/DeadZombie.gif"));
            zombieLabel.setIcon(deadZombieIcon);
            zombieLabel.setVisible(true); // Ensure the JLabel is visible
            zombieLabel.repaint();

            // Schedule the removal of the JLabel after a short delay
            Timer timer = new Timer(100, e -> {
                background.remove(zombieLabel);
                background.repaint(); // Ensure the panel is repainted
                System.out.println("Zombie removed from interface: " + zombie.getName());
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
//
//    private void restartGame() {
//        dispose();
//
//        SwingUtilities.invokeLater(() -> {
//            new GameEasyWindow(difficulty, entitiesForHud, new BoardDay(1000)).setVisible(true);
//        });
//    }
//
//    private void showGameOverScreen() {
//        pauseGame();
//
//        JPanel gameOverPanel = new JPanel();
//        gameOverPanel.setLayout(null);
//        gameOverPanel.setBounds(0, 0, getWidth(),getHeight());
//
//        JLabel gameOverBackground = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("assets/Images/inGame/board/GameOver.png")));
//        gameOverBackground.setBounds(0, 0, getWidth(), getHeight());
//        gameOverPanel.add(gameOverBackground);
//
//        JButton restartButton = new JButton("Reiniciar");
//        restartButton.setBounds(getWidth() / 2 - 50, getHeight() / 2 + 100, 100, 50);
//        restartButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                restartGame();
//            }
//        });
//
//        gameOverBackground.add(restartButton);
//
//        setContentPane(gameOverPanel);
//        revalidate();
//        repaint();
//    }
//

    public void updateAllZombiePositions() {
        ArrayList<Zombie> zombiesToRemove = board.getZombiesList();
        System.out.println("Zombies to remove: " + zombiesToRemove);
        for (Zombie zombie : board.getZombiesList()) {
            System.out.println();
            JLabel zombieLabel = zombie.getZombieLabel();
            if (zombieLabel != null) {
                zombieLabel.setBounds(zombie.getX(), zombie.getY(), zombieLabel.getWidth(), zombieLabel.getHeight());
//                if (zombie.getX() < minX) {
//                    System.out.println("Iniciando again");
//                    showGameOverScreen();
//                    break;
//                }
            }
        }
    }

    public void updateSuns() {
        this.suns = this.board.getSunPoints();
    }

    public void updateSunsLabel() {
        updateSuns();
        sunCounter.setText(getSuns());
        System.out.println("Suns updated: " + board.getSunPoints());
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

    public void addZombieLabel(Zombie zombie, JLabel label) {
        // Agregar el label al panel de juego
        background.add(label);

        // Establecer el orden de los componentes
        // Los componentes con índice más alto se pintan primero
        background.setComponentZOrder(label, background.getComponentCount() - 1);
        background.revalidate();
        background.repaint();
    }


    public void removeZombieLabel(JLabel zombieLabel) {
        this.remove(zombieLabel);
        this.repaint();
    }

    public void startPlantHealthCheck() {
        int interval = 200; // 1 segundo
        Timer healthCheckTimer = new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkPlantHealth();
                paintShots();
            }
        });
        healthCheckTimer.start();
    }

    private void checkPlantHealth() {
        for (Plant plant : board.getPlantsList()) {
            if (plant.getLife() <= 0) {
                removePlantFromInterface(plant);
            }
        }
    }

    private void removePlantFromInterface(Plant plant) {
        SwingUtilities.invokeLater(() -> {
            JLabel plantLabel = getPlantLabel(plant);
            System.out.println("Plant to remove: " + plant.getName());
            System.out.println("Plant label: " + plantLabel);
            if (plantLabel != null) {
                background.remove(plantLabel);
                background.repaint();
                System.out.println("Plant removed from interface: " + plant.getName());
            }
        });
    }

    private JLabel getPlantLabel(Plant plant) {
        for (Component component : background.getComponents()) {
            if (component instanceof JLabel label) {
                String labelName = label.getName();
                if (labelName != null && labelName.equals(plant.getName())) {
                    return label;
                }
            }
        }
        return null;
    }

    public void paintShots() {
        List<shoot> shoots = board.getShoots();
        for (shoot s : shoots) {
            JLabel shootLabel = s.getShootLabel();
            s.setX(s.getX() + 10);
            shootLabel.setLocation(s.getX(), s.getY());
            shootLabel.setBounds(s.getX(), s.getY(), shootLabel.getIcon().getIconWidth(), shootLabel.getIcon().getIconHeight());
            background.add(shootLabel);
        }
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