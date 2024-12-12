package domain.entities;

import domain.board.Board;
import presentation.GameEasyWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

    public void startMoving(Board board) {
        int interval = 100; // 0.1 seconds
        moveTimer = new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move(board);
                zombieLabel.setLocation(getX(), getY()); // Update the label's position
            }
        });
        moveTimer.start();
    }

    public void stopMoving() {
        if (moveTimer != null) {
            moveTimer.stop();
        }
    }

    public void move(Board board) {
        if (isfreezed) {
            System.out.println(this.getName() + " está congelado y no puede moverse.");
            return;
        }
        setX(getX() - 2); // Move 2 pixels to the left
        int currentX = getX();
        int currentY = getY();
        int nextX = currentX - 2;
        int nextColumn = board.getColumnFromX(nextX);

        if (nextColumn < 1) {
//            System.out.println(this.getName() + " alcanzó el borde del tablero. ¡Juego terminado!");
            stopMoving();
            return;
        }

        ArrayList<Plant> plantsInRow = board.getPlantsList();
//        System.out.println(plantsInRow);
        for (Plant plant : plantsInRow) {
            System.out.println(plant.getX() + " " + plant.getY() + " " + nextX + " " + currentY);
            if (Math.abs(plant.getX() - nextX) < 7 && Math.abs(plant.getY() - currentY) < 70) {
                System.out.println("holamundo");
                System.out.println(this.getName() + " encontró una planta en su camino.");
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
//        System.out.println(this.getName() + " se movió a la posición (" + nextX + ", " + currentY + ")");
        zombieLabel.setLocation(nextX, currentY); // Update the label's position and repaint
    }

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
            // Detener el ataque si no hay plantas en la fila al frente
            stopAttacking();
        }
    }


    public void stopAttacking() {
        if (attackTimer != null) {
            attackTimer.stop();
        }
    }

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

    public void receiveDamage(int damage) {
        this.life -= damage;
        System.out.println(type + " recibe " + damage + " de daño. Vida restante: " + life);
        if (!isAlive()) {
            stopMoving();
            stopAttacking();
            SwingUtilities.invokeLater(() -> {
                JLabel zombieLabel = getZombieLabel();

                System.out.println(zombieLabel);
                System.out.println(zombieLabel);
                if (zombieLabel != null) {
                    System.out.println("Zombie removed from interface: " + this.getName());
                    gameWindow.removeZombieLabel(zombieLabel);

                    System.out.println("Zombie removed from interface: " + this.getName());
                }
            });
        }
    }

    public boolean isAlive() {
        return this.life > 0;
    }

    public void freeze() {
        this.isfreezed = true;
        System.out.println(type + " está congelado.");
    }

    public void unfreeze() {
        this.isfreezed = false;
        System.out.println(type + " ya no está congelado.");
    }

    public int getLife() {
        return life;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDamage() {
        return damage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public String getType() {
        return type;
    }

    public boolean getIsfreezed() {
        return this.isfreezed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    private void createZombieLabel() {
        if (zombieLabel != null) {
            gameWindow.removeZombieLabel(zombieLabel); // Remove the existing label if it exists
        }
        zombieLabel = new JLabel();
        ImageIcon zombieIcon = new ImageIcon(getClass().getClassLoader().getResource("assets/Images/inGame/zombies/ConeheadZombie.gif"));
        zombieLabel.setIcon(zombieIcon);
        zombieLabel.setBounds(getX(), getY(), zombieIcon.getIconWidth(), zombieIcon.getIconHeight());
        gameWindow.addZombieLabel(this, zombieLabel);
    }

    public JLabel getZombieLabel() {
        return zombieLabel;
    }
}