package domain.entities;

import domain.board.Board;
import presentation.GameEasyWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public Zombie(String name, int cost, int life, int speed, int damage, float attackSpeed, String type, Board board, Point position, String imagePath, GameEasyWindow gameWindow) {
        super(name, cost, position, imagePath);
        this.life = life;
        this.speed = speed;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.type = type;
        this.isfreezed = false;
        this.gameWindow = gameWindow;
        startMoving(board);
    }

    public void startMoving(Board board) {
        int interval = 1000 / speed;
        moveTimer = new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move(board);
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
    Point currentPosition = this.getPosition();
    Point nextPosition = new Point(currentPosition.x - 35, currentPosition.y);
    int currentColumn = board.getColumnFromX(currentPosition.x);
    int nextColumn = board.getColumnFromX(nextPosition.x);

    if (nextColumn < 1) {
        System.out.println(this.getName() + " alcanzó el borde del tablero. ¡Juego terminado!");
        stopMoving();
        return;
    }

    if (board.isPlantInColumn(nextColumn)) {
        System.out.println(this.getName() + " encontró una planta en la columna " + nextColumn + " y comenzará a atacar.");
        stopMoving();
        startAttacking(board);
    } else {
        this.setPosition(nextPosition);
        board.getZombies().remove(currentPosition);
        board.getZombies().put(nextPosition, this);
        System.out.println(this.getName() + " se movió a la posición " + nextPosition);
    }
}

    public void startAttacking(Board board) {
        if (attackTimer != null && attackTimer.isRunning()) {
            System.out.println(this.getName() + " ya está atacando.");
            return;
        }
        int interval = (int) (attackSpeed * 1000);
        attackTimer = new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attack(board);
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
    }

    public void stopAttacking() {
        if (attackTimer != null) {
            attackTimer.stop();
        }
    }

    public void attack(Board board) {
        Point currentPosition = this.getPosition();
        if (board.getPlants().containsKey(currentPosition)) {
            Plant target = board.getPlants().get(currentPosition);
            target.receiveDamage(this.damage);
            System.out.println(this.getName() + " atacó a " + target.getName() + " causando " + this.damage + " de daño. Vida restante de la planta: " + target.getLife());

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

            if (!target.isAlive()) {
                board.getPlants().remove(currentPosition);
                System.out.println(target.getName() + " ha sido eliminada.");
                stopAttacking();
                startMoving(board);
            }
        } else {
            System.out.println(this.getName() + " no encontró plantas en su posición actual.");
            stopAttacking();
            startMoving(board);
        }
    }

    public boolean isAttacking() {
        return attackTimer != null && attackTimer.isRunning();
    }


    public void receiveDamage(int damage) {
        this.life -= damage;
        System.out.println(type + " recibe " + damage + " de daño. Vida restante: " + life);
        if (!isAlive()) {
            stopMoving();
            stopAttacking();
            SwingUtilities.invokeLater(() -> {
                JLabel zombieLabel = gameWindow.getZombieLabel(this);
                if (zombieLabel != null) {
                    ImageIcon deadZombieIcon = new ImageIcon(getClass().getClassLoader().getResource("assets/Images/inGame/zombies/DeadZombie.gif"));
                    zombieLabel.setIcon(deadZombieIcon);
                    zombieLabel.setVisible(true);
                    zombieLabel.repaint();
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
}