package domain.entities;

import javax.swing.*;

/**
 * La clase shoot representa un disparo en el juego.
 * Contiene las coordenadas y la etiqueta del disparo.
 */
public class shoot {

    private int x, y;
    private JLabel shootLabel;

    /**
     * Construye un disparo con las coordenadas especificadas.
     *
     * @param x la coordenada x del disparo
     * @param y la coordenada y del disparo
     */
    public shoot(int x, int y) {
        this.x = x;
        this.y = y;
        this.shootLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("assets/Images/inGame/plants/Pea.png")));
    }

    /**
     * Devuelve la coordenada x del disparo.
     *
     * @return la coordenada x del disparo
     */
    public int getX() {
        return x;
    }

    /**
     * Devuelve la coordenada y del disparo.
     *
     * @return la coordenada y del disparo
     */
    public int getY() {
        return y;
    }

    /**
     * Devuelve la etiqueta del disparo.
     *
     * @return la etiqueta del disparo
     */
    public JLabel getShootLabel() {
        return shootLabel;
    }

    /**
     * Elimina la etiqueta del disparo haciéndola invisible.
     */
    public void removeShootLabel() {
        shootLabel.setVisible(false);
    }

    /**
     * Establece la coordenada x del disparo.
     *
     * @param x la nueva coordenada x del disparo
     */
    public void setX(int x) {
        this.x = x;
    }
}