package domain.entities;
import java.awt.*;
import domain.board.Board;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
public abstract class Zombie extends Entity {
    private int life;
    private int speed;
    private int damage;
    private float attackSpeed;
    private String type;
    private boolean isfreezed;
    private Timer moveTimer;
    private Timer attackTimer;
    public Zombie(String name, int cost, int life, int speed, int damage, float attackSpeed, String type, Board board, Point position, String imagePath) {
        super(name,cost,position, imagePath);
        this.life = life;
        this.speed = speed;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.type = type;
        this.isfreezed = false;
        startMoving(board);
    }
    public void startMoving(Board board) {
        int interval = (int) (1000 / speed); // Intervalo en milisegundos basado en velocidad
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
        if (nextPosition.x < 100) {
            System.out.println(this.getName() + " alcanzó el borde del tablero. ¡Juego terminado!");
            stopMoving();
            return;
        }
        if (board.getPlants().containsKey(currentPosition)) {
            System.out.println(this.getName() + " encontró una planta en " + nextPosition + " y comenzará a atacar.");
            stopMoving(); // Detener movimiento mientras ataca
            startAttacking(board); // Iniciar ataque
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
            return; // Evitar reiniciar el temporizador
        }
        int interval = (int) (attackSpeed * 1000); // Convertir segundos a milisegundos
        attackTimer = new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attack(board);
            }
        });
        attackTimer.start();
        System.out.println(this.getName() + " comenzó a atacar.");
    }
    public void stopAttacking() {
        if (attackTimer != null) {
            attackTimer.stop();
        }
    }
    public void attack(Board board) {
        Point currentPosition = this.getPosition();
        if (board.getPlants().containsKey(currentPosition) ) {
            Plant target = board.getPlants().get(currentPosition);
            target.receiveDamage(this.damage); // Aplicar daño
            System.out.println(this.getName() + " atacó a " + target.getName() + " causando " + this.damage + " de daño. Vida restante de la planta: " + target.getLife());
            if (!target.isAlive()) { // Si la planta muere
                board.getPlants().remove(currentPosition); // Remover planta del tablero
                System.out.println(target.getName() + " ha sido eliminada.");
                stopAttacking(); // Detener ataque
                startMoving(board); // Reanudar movimiento
            }
        } else {
            System.out.println(this.getName() + " no encontró plantas en su posición actual.");
            stopAttacking(); // Detener ataque si no hay planta
            startMoving(board); // Reanudar movimiento si no hay plantas
        }
    }
    public void receiveDamage(int damage) {
        this.life -= damage;
        System.out.println(type + " recibe " + damage + " de daño. Vida restante: " + life);
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
    // Getters
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