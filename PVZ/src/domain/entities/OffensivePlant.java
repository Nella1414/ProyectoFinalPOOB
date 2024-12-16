package domain.entities;

import domain.board.Board;

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La clase OffensivePlant representa una planta ofensiva en el juego.
 * Extiende la clase Plant y define las propiedades y acciones específicas de una planta ofensiva.
 */
public abstract class OffensivePlant extends Plant {
    private Timer attackTimer;
    private int damage;
    private float attackSpeed; // En segundos

    /**
     * Construye una OffensivePlant con las coordenadas, nombre, costo, vida, tipo, daño, velocidad de ataque, posición, ruta de imagen y tablero especificados.
     *
     * @param x la coordenada x de la planta
     * @param y la coordenada y de la planta
     * @param name el nombre de la planta
     * @param cost el costo de la planta
     * @param life la vida de la planta
     * @param type el tipo de la planta
     * @param damage el daño que causa la planta
     * @param attackSpeed la velocidad de ataque de la planta en segundos
     * @param position la posición de la planta en el tablero
     * @param imagePath la ruta a la imagen de la planta
     * @param board el tablero del juego
     */
    public OffensivePlant(int x, int y ,String name, int cost, int life, String type, int damage, float attackSpeed, Point position,String imagePath,Board board) {
        super(x,y,name, cost, life, type, position, imagePath,board);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
    }

    /**
     * Inicia la acción de ataque de la planta.
     * Configura un temporizador para llamar al método de ataque a intervalos definidos por la velocidad de ataque.
     *
     * @param board el tablero del juego
     */
    public void startAttacking(Board board) {
        int interval = (int) (attackSpeed * 1000); // Convertir segundos a milisegundos
        attackTimer = new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attack(board);
            }
        });
        attackTimer.start();
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
     * Método abstracto para realizar la acción de ataque.
     * Este método debe ser implementado por las subclases.
     *
     * @param board el tablero del juego
     */
    public abstract void attack(Board board); // Metodo definido en subclases

    /**
     * Devuelve el daño que causa la planta.
     *
     * @return el daño que causa la planta
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Devuelve la velocidad de ataque de la planta.
     *
     * @return la velocidad de ataque de la planta en segundos
     */
    public float getAttackSpeed() {
        return this.attackSpeed;
    }
}