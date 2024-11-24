package domain.Entities;

import java.awt.*;

public class Peashooter extends OffensivePlant {
    public Peashooter(Point position) {
        super("Peashooter", 100, 300, "Offensive", 20, 1.5f, "assets/Images/inGame/plants/Peashooter.gif", position);
    }
}
