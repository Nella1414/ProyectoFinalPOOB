package domain.entities;

import domain.board.Board;

import java.awt.*;

/**
 * La clase PotatoMine representa una mina de papa en el juego.
 * Extiende la clase SupportPlant y define las propiedades y acciones específicas de una PotatoMine.
 */
public class PotatoMine extends SupportPlant {
    private boolean isArmed = false;

    /**
     * Construye una PotatoMine con las coordenadas, el tablero y la posición especificados.
     *
     * @param x la coordenada x de la planta
     * @param y la coordenada y de la planta
     * @param board el tablero del juego
     * @param position la posición de la planta en el tablero
     */
    public PotatoMine(int x, int y, Board board, Point position) {
        super(x, y, "PotatoMine", 25, 300, "SupportPlant", 5000, position, "assets/Images/inGame/plants/PotatoMine/PotatoMine.gif", board); // 5000 ms = 5 segundos
        startAction();
    }

    /**
     * Realiza la acción de la PotatoMine.
     * Si la mina no está armada, la arma y muestra un mensaje indicando que está lista para explotar.
     */
    @Override
    public void action() {
        if (!isArmed) {
            isArmed = true;
            System.out.println(this.getName() + " está armada y lista para explotar.");
        }
    }

    /**
     * Detona la mina causando daño masivo al zombie objetivo.
     * Si el zombie muere, lo elimina del tablero y también elimina la mina.
     *
     * @param target el zombie objetivo
     * @param board el tablero del juego
     */
    public void detonate(Zombie target, Board board) {
        if (isArmed && target != null) {
            System.out.println(this.getName() + " explotó dañando a " + target.getName());
            target.receiveDamage(1000); // Daño masivo
            if (!target.isAlive()) {
                board.getZombies().remove(target.getPosition());
                System.out.println(target.getName() + " ha sido eliminado.");
            }
            board.getPlants().remove(this.getPosition()); // Eliminar la mina tras explotar
            stopAction();
        }
    }

    /**
     * Realiza la acción de ataque de la PotatoMine.
     * Actualmente, este método no implementa ninguna lógica de ataque.
     *
     * @param board el tablero del juego
     */
    @Override
    public void attack(Board board) {

    }
}