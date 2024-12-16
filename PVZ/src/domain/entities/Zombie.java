package domain.entities;

import domain.board.Board;
import presentation.GameEasyWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * La clase Zombie representa un zombie en el juego.
 * Extiende la clase Entity y define las propiedades y acciones específicas de un zombie.
 */
public abstract class Zombie extends Entity {
    private final int speed;
    private final int damage;
    private final float attackSpeed;
    private final String type;
    private final GameEasyWindow gameWindow;
    private int life;
    private boolean isfreezed;
    private Timer moveTimer;
    private Timer attackTimer;
    private int x, y;

    private JLabel zombieLabel;

    /**
     * Construye un Zombie con las coordenadas, nombre, costo, vida, velocidad, daño, velocidad de ataque, tipo, tablero, posición, ruta de imagen y ventana del juego especificados.
     *
     * @param x la coordenada x del zombie
     * @param y la coordenada y del zombie
     * @param name el nombre del zombie
     * @param cost el costo del zombie
     * @param life la vida del zombie
     * @param speed la velocidad del zombie
     * @param damage el daño del zombie
     * @param attackSpeed la velocidad de ataque del zombie
     * @param type el tipo de zombie
     * @param board el tablero del juego
     * @param position la posición del zombie en el tablero
     * @param imagePath la ruta a la imagen del zombie
     * @param gameWindow la ventana del juego
     */
    public Zombie(int x, int y, String name, int cost, int life, int speed, int damage, float attackSpeed, String type, Board board, Point position, String imagePath, GameEasyWindow gameWindow) {
        super(name, cost, position, imagePath);
        this.life = life;
        this.speed = speed;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.type = type;
        this.isfreezed = false;
        this.gameWindow = gameWindow;
        this.x = position.x;
        this.y = position.y;
        createZombieLabel();
        startMoving(board);
    }

    /**
     * Inicia el movimiento del zombie en el tablero.
     *
     * @param board el tablero del juego
     */
    public void startMoving(Board board) {
        int interval = 100; // 0.1 segundos
        moveTimer = new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move(board);
                zombieLabel.setLocation(getX(), getY()); // Actualiza la posición de la etiqueta
            }
        });
        moveTimer.start();
    }

    /**
     * Detiene el movimiento del zombie.
     */
    public void stopMoving() {
        if (moveTimer != null) {
            moveTimer.stop();
        }
    }

    /**
     * Mueve el zombie en el tablero.
     *
     * @param board el tablero del juego
     */
    public void move(Board board) {
        if (isfreezed) {
            System.out.println(this.getName() + " está congelado y no puede moverse.");
            return;
        }
        setX(getX() - 2); // Mueve 2 píxeles a la izquierda
        int currentX = getX();
        int currentY = getY();
        int nextX = currentX - 2;
        int nextColumn = board.getColumnFromX(nextX);

        if (nextColumn < 1) {
            stopMoving();
            return;
        }

        ArrayList<Plant> plantsInRow = board.getPlantsList();
        for (Plant plant : plantsInRow) {
            if (Math.abs(plant.getX() - nextX) < 7 && Math.abs(plant.getY() - currentY) < 68) {
                stopMoving();
                startAttacking(board, plant);
                return;
            }
        }

        ArrayList<shoot> shootsInRow = board.getShoots();
        for (shoot shoot : shootsInRow) {
            if (Math.abs(shoot.getX() - nextX) < 7 && Math.abs(shoot.getY() - currentY) < 70) {
                board.removeShoot(shoot);
                shoot.removeShootLabel();
                return;
            }
        }

        setX(nextX);
        board.getZombies().put(new Point(nextX, currentY), this);
        zombieLabel.setLocation(nextX, currentY); // Actualiza la posición de la etiqueta y repinta
    }

    /**
     * Inicia el ataque del zombie a una planta objetivo.
     *
     * @param board el tablero del juego
     * @param targetPlant la planta objetivo
     */
    public void startAttacking(Board board, Plant targetPlant) {
        if (targetPlant != null) {
            if (attackTimer != null && attackTimer.isRunning()) {
                System.out.println(this.getName() + " ya está atacando.");
                return;
            }
            int interval = (int) (attackSpeed * 1000);
            attackTimer = new Timer(interval, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    attack(board, targetPlant);
                }
            });
            attackTimer.start();
            System.out.println(this.getName() + " comenzó a atacar.");

            // Cambiar la imagen del zombie cuando comienza a atacar
            SwingUtilities.invokeLater(() -> {
                JLabel zombieLabel = gameWindow.getZombieLabel(this);
                if (zombieLabel != null) {
                    ImageIcon attackingZombieIcon = new ImageIcon(getClass().getClassLoader().getResource("assets/Images/inGame/zombies/ConeheadZombieAttack.gif"));
                    zombieLabel.setIcon(attackingZombieIcon);
                    zombieLabel.setVisible(true);
                    zombieLabel.repaint();
                }
            });
        } else {
            stopAttacking();
        }
    }

    /**
     * Detiene el ataque del zombie.
     */
    public void stopAttacking() {
        if (attackTimer != null) {
            attackTimer.stop();
        }
    }

    /**
     * Realiza el ataque del zombie a una planta objetivo.
     *
     * @param board el tablero del juego
     * @param targetPlant la planta objetivo
     */
    public void attack(Board board, Plant targetPlant) {
        if (targetPlant != null) {
            targetPlant.receiveDamage(this.damage);
            System.out.println(this.getName() + " atacó a " + targetPlant.getName() + " causando " + this.damage + " de daño. Vida restante de la planta: " + targetPlant.getLife());

            // Cambiar la imagen del zombie cuando ataca
            SwingUtilities.invokeLater(() -> {
                JLabel zombieLabel = gameWindow.getZombieLabel(this);
                if (zombieLabel != null) {
                    ImageIcon attackingZombieIcon = new ImageIcon(getClass().getClassLoader().getResource("assets/Images/inGame/zombies/AttackingZombie.gif"));
                    zombieLabel.setIcon(attackingZombieIcon);
                    zombieLabel.setVisible(true);
                    zombieLabel.repaint();
                }
            });

            if (!targetPlant.isAlive()) {
                board.getPlants().remove(targetPlant.getPosition());
                System.out.println(targetPlant.getName() + " ha sido eliminada.");
                stopAttacking();
                startMoving(board);
            }
        } else {
            System.out.println(this.getName() + " no encontró plantas en su posición actual.");
            stopAttacking();
            startMoving(board);
        }
    }

    /**
     * Recibe daño y reduce la vida del zombie.
     *
     * @param damage el daño recibido
     */
    public void receiveDamage(int damage) {
        this.life -= damage;
        System.out.println(type + " recibe " + damage + " de daño. Vida restante: " + life);
        if (!isAlive()) {
            stopMoving();
            stopAttacking();
            SwingUtilities.invokeLater(() -> {
                JLabel zombieLabel = getZombieLabel();
                if (zombieLabel != null) {
                    gameWindow.removeZombieLabel(zombieLabel);
                }
            });
        }
    }

    /**
     * Verifica si el zombie está vivo.
     *
     * @return true si el zombie está vivo, false en caso contrario
     */
    public boolean isAlive() {
        return this.life > 0;
    }

    /**
     * Congela al zombie, impidiendo su movimiento.
     */
    public void freeze() {
        this.isfreezed = true;
        System.out.println(type + " está congelado.");
    }

    /**
     * Descongela al zombie, permitiendo su movimiento.
     */
    public void unfreeze() {
        this.isfreezed = false;
        System.out.println(type + " ya no está congelado.");
    }

    /**
     * Devuelve la vida del zombie.
     *
     * @return la vida del zombie
     */
    public int getLife() {
        return life;
    }

    /**
     * Devuelve la velocidad del zombie.
     *
     * @return la velocidad del zombie
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Devuelve el daño del zombie.
     *
     * @return el daño del zombie
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Devuelve la velocidad de ataque del zombie.
     *
     * @return la velocidad de ataque del zombie
     */
    public float getAttackSpeed() {
        return attackSpeed;
    }

    /**
     * Devuelve el tipo de zombie.
     *
     * @return el tipo de zombie
     */
    public String getType() {
        return type;
    }

    /**
     * Verifica si el zombie está congelado.
     *
     * @return true si el zombie está congelado, false en caso contrario
     */
    public boolean getIsfreezed() {
        return this.isfreezed;
    }

    /**
     * Devuelve la coordenada x del zombie.
     *
     * @return la coordenada x del zombie
     */
    public int getX() {
        return x;
    }

    /**
     * Establece la coordenada x del zombie.
     *
     * @param x la nueva coordenada x del zombie
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Devuelve la coordenada y del zombie.
     *
     * @return la coordenada y del zombie
     */
    public int getY() {
        return y;
    }

    /**
     * Crea la etiqueta del zombie y la añade a la ventana del juego.
     */
    private void createZombieLabel() {
        if (gameWindow != null) {
            if (zombieLabel != null) {
                gameWindow.removeZombieLabel(zombieLabel); // Elimina la etiqueta existente si existe
            }
            zombieLabel = new JLabel();
            ImageIcon zombieIcon = new ImageIcon(getClass().getClassLoader().getResource(getImagePath()));
            zombieLabel.setIcon(zombieIcon);
            zombieLabel.setBounds(getX(), getY(), zombieIcon.getIconWidth(), zombieIcon.getIconHeight());
            gameWindow.addZombieLabel(this, zombieLabel);
        }
    }

    /**
     * Devuelve la ruta a la imagen del zombie.
     *
     * @return la ruta a la imagen del zombie
     */
    public String getImagePath() {
        return "assets/Images/inGame/zombies/" + getName() + ".gif";
    }

    /**
     * Devuelve la etiqueta del zombie.
     *
     * @return la etiqueta del zombie
     */
    public JLabel getZombieLabel() {
        return zombieLabel;
    }
}