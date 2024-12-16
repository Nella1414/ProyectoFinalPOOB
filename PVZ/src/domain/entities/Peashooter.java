package domain.entities;

import domain.board.Board;
import presentation.GameEasyWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La clase Peashooter representa una planta ofensiva que dispara guisantes en el juego.
 * Extiende la clase OffensivePlant y define las propiedades y acciones específicas de un Peashooter.
 */
public class Peashooter extends OffensivePlant {

    private shoot shoot;
    private Timer shootTimer;

    /**
     * Construye un Peashooter con las coordenadas, tablero y posición especificados.
     *
     * @param x la coordenada x de la planta
     * @param y la coordenada y de la planta
     * @param board el tablero del juego
     * @param position la posición de la planta en el tablero
     */
    public Peashooter(int x, int y, Board board, Point position) {
        super(x, y, "Peashooter", 100, 300, "Offensive", 20, 1.5f, position, "assets/Images/inGame/plants/Peashooter.gif", board); // 1.5 segundos entre ataques
        this.shoot = new shoot(x+25, y+10);
        this.startAttacking(board);
        startShooting(board);
    }

    /**
     * Inicia la acción de disparo de la planta.
     * Configura un temporizador para crear y añadir un nuevo disparo al tablero cada 1.5 segundos.
     *
     * @param board el tablero del juego
     */
    private void startShooting(Board board) {
        shootTimer = new Timer(1500, new ActionListener() { // 1.5 segundos entre disparos
            @Override
            public void actionPerformed(ActionEvent e) {
                shoot newShoot = new shoot(getX() + 25, getY() + 15);
                board.addShoot(newShoot);
            }
        });
        shootTimer.start();
    }

    /**
     * Realiza la acción de ataque de la planta.
     * Busca el zombie más cercano en la misma fila y le causa daño.
     * Si el zombie muere, lo elimina del tablero.
     *
     * @param board el tablero del juego
     */
    @Override
    public void attack(Board board) {
        int peashooterRow = board.getRowFromYForPlants(this.getY());
        Zombie target = board.findClosestZombieInRow(peashooterRow, this.getX());

        if (target != null && target.isAlive()) {
            int targetRow = board.getRowFromYForZombies(target.getY());

            if (targetRow == peashooterRow) {
                target.receiveDamage(this.getDamage());
                if (!target.isAlive()) {
                    board.removeZombieList(target);
                }
            }
        }
    }

    /**
     * Detiene la acción de disparo de la planta.
     * Detiene el temporizador de disparo si está en funcionamiento.
     */
    public void stopShooting() {
        if (shootTimer != null) {
            shootTimer.stop();
        }
    }

}