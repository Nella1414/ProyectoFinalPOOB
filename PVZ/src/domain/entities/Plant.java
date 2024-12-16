package domain.entities;

import domain.board.Board;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

/**
 * La clase Plant representa una planta genérica en el juego.
 * Extiende la clase Entity y define las propiedades y acciones comunes a todas las plantas.
 */
public abstract class Plant extends Entity {
    int life;
    String type;
    Board board;
    double attackSpeed; // Define la velocidad de ataque
    Timer attackTimer;  // Define el temporizador de ataque
    int x, y;

    /**
     * Construye una planta con las coordenadas, nombre, costo, vida, tipo, posición, ruta de imagen y tablero especificados.
     *
     * @param x la coordenada x de la planta
     * @param y la coordenada y de la planta
     * @param name el nombre de la planta
     * @param cost el costo de la planta
     * @param life la vida de la planta
     * @param type el tipo de la planta
     * @param position la posición de la planta en el tablero
     * @param imagePath la ruta a la imagen de la planta
     * @param board el tablero del juego
     */
    public Plant(int x, int y, String name, int cost, int life, String type, Point position, String imagePath, Board board) {
        super(name, cost, position, imagePath);
        this.life = life;
        this.type = type;
        this.board = board;
        this.x = x;
        this.y = y;
    }

    /**
     * Devuelve la coordenada x de la planta.
     *
     * @return la coordenada x de la planta
     */
    public int getX() {
        return x;
    }

    /**
     * Devuelve la coordenada y de la planta.
     *
     * @return la coordenada y de la planta
     */
    public int getY() {
        return y;
    }

    /**
     * Devuelve la vida de la planta.
     *
     * @return la vida de la planta
     */
    public int getLife() {
        return this.life;
    }

    /**
     * Devuelve el tipo de la planta.
     *
     * @return el tipo de la planta
     */
    public String getType() {
        return this.type;
    }

    /**
     * Recibe daño y reduce la vida de la planta.
     * Si la vida de la planta es menor que 0, la planta se elimina del tablero.
     *
     * @param damage el daño recibido por la planta
     */
    public void receiveDamage(int damage) {
        this.life -= damage;
        if (this.life < 0) {
            board.removePlantList(this);
            this.life = 0;
        }
        System.out.println(this.getName() + " recibió " + damage + " de daño. Vida restante: " + this.life);
    }

    /**
     * Verifica si la planta está viva.
     *
     * @return true si la planta está viva, false en caso contrario
     */
    public boolean isAlive() {
        return this.life > 0;
    }

    /**
     * Detiene la acción de ataque de la planta.
     * Detiene el temporizador de ataque si está en funcionamiento.
     */
    public void stopAttacking() {
        if (attackTimer != null) {
            attackTimer.stop();
        }
    }

    /**
     * Inicia la acción de ataque de la planta.
     * Configura un temporizador para llamar al método de ataque a intervalos definidos por la velocidad de ataque.
     *
     * @param board el tablero del juego
     */
    public void startAttacking(Board board) {
        int interval = (int) (1000 / attackSpeed);
        attackTimer = new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attack(board);
            }
        });
        attackTimer.start();
    }

    /**
     * Método abstracto para realizar la acción de ataque.
     * Este método debe ser implementado por las subclases.
     *
     * @param board el tablero del juego
     */
    public abstract void attack(Board board); // Define el método abstracto de ataque

}