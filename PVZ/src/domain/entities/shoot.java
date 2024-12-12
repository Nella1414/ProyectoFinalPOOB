package domain.entities;

import javax.swing.*;
public class shoot {

    private int x ,y;
    private JLabel shootLabel;

    public shoot(int x, int y) {
        this.x = x;
        this.y = y;
        this.shootLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("assets/Images/inGame/plants/Pea.png")));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public JLabel getShootLabel() {
        return shootLabel;
    }

    public void removeShootLabel() {
        shootLabel.setVisible(false);
    }

    public void setX(int x) {
        this.x = x;
    }




}
