package domain.tools;



import java.awt.*;

public class Shovel extends Tool {

    public Shovel() {
        super("Shovel");
    }

    @Override
    public void action(Point position) {
        if (position != null) {
            System.out.println("Plant removed at " + position);
        } else {
            System.out.println("Default Shovel action");
        }
    }
}

