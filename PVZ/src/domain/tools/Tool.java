package domain.tools;
import javax.swing.*;
import java.awt.*;

public abstract class Tool {
    protected String name;

    public Tool(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public  void action(Point position) {
        System.out.println("Action");
    }
}
