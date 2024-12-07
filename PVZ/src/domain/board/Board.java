package domain.board;


import domain.entities.Plant;
import domain.entities.PotatoMine;
import domain.entities.Zombie;
import domain.tools.LawnMower;
import domain.tools.Tool;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Board {
    private final Map<Integer, LawnMower> lawnMowers;
    protected int rows;
    protected int columns;
    protected Map<Point, Plant> plants;
    protected Map<Point, Zombie> zombies;
    private int sunPoints;

    public Board(int rows, int columns, int sunPoints) {
        this.rows = rows;
        this.columns = columns;
        this.plants = new HashMap<>();
        this.zombies = new HashMap<>();
        this.sunPoints = sunPoints;
        this.lawnMowers = new HashMap<>();
        for (int i = 0; i < rows; i++) {
            lawnMowers.put(i, new LawnMower("LawnMower", i));
        }
        updateZombiesWhenLawnMower();
    }

    public int getRowFromYForPlants(int y) {
        return ((y - 270) / 90) + 1;
    }

    public int getRowFromYForZombies(int y) {
        return ((y - 90) / 90);
    }

    public int getColumnFromX(int x) {
        return (x - 100) / 35;
    }

    public boolean isPlantInColumn(int column) {
        for (Plant plant : plants.values()) {
            int plantColumn = getColumnFromX(plant.getPosition().x);
            if (plantColumn == column) {
                return true;
            }
        }
        return false;
    }

    public int getSunPoints() {
        return sunPoints;
    }

    public void addSun(int sunPoints) {
        this.sunPoints += sunPoints;
        System.out.println("Soles actuales: " + this.sunPoints);
    }

    public boolean spendSuns(int amount) {
        if (this.sunPoints >= amount) {
            this.sunPoints -= amount;
            System.out.println("Soles restantes: " + this.sunPoints);
            return true;
        }
        System.out.println("No hay suficientes soles para gastar.");
        return false;

    }

    public boolean addPlant(Plant plant, Point position) {
        // Validar si la posición está dentro del tablero
        if (position.x >= columns || position.y >= rows) {
            System.out.println("La posición " + position + " está fuera de los límites del tablero.");
            return false;
        }

        // Verificar si la posición está ocupada
        if (plants.containsKey(position)) {
            System.out.println("La posición " + position + " ya está ocupada por otra planta.");
            return false;
        }

        // Verificar si hay suficientes soles
        if (plant.getCost() > sunPoints) {
            System.out.println("No hay suficientes soles para la planta: " + plant.getName());
            return false;
        }

        // Descontar el costo y añadir la planta
        sunPoints -= plant.getCost();
        plants.put(position, plant);
        System.out.println("Se añadió la planta " + plant.getName() + " en posición " + position + ". Soles restantes: " + sunPoints);
        return true;
    }


    public boolean addZombie(Zombie zombie, Point position) {
        if (position.y >= rows || zombies.containsKey(position)) {
            return false;
        }
        zombies.put(position, zombie);
        return true;
    }

    public Plant getPlantAt(Point position) {
        return plants.get(position);
    }

    public Zombie getZombieAt(Point position) {
        return zombies.get(position);
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Map<Point, Plant> getPlants() {
        return plants;
    }

    public Map<Point, Zombie> getZombies() {
        return zombies;
    }

    public Zombie findTargetInRow(int row) {
        for (Point pos : zombies.keySet()) {
            if (pos.y == row) return zombies.get(pos);
        }
        return null;
    }

    public void removePlant(Point position) {
        plants.remove(position);
    }

    public void removeZombie(Point position) {
        zombies.remove(position);
    }

    public void useShovel(Tool tool, Point position) {
        tool.action(position);
        if (tool.getName().equals("Shovel") && plants.containsKey(position)) {
            this.removePlant(position);
            System.out.println("Plant removed from board");
        } else {
            System.out.println("No plant found at " + position + ". Nothing to remove.");
        }
    }

    public void checkPotatoMine(Point position) {
        Plant plant = plants.get(position);
        if (plant instanceof PotatoMine) {
            Zombie target = zombies.get(position);
            ((PotatoMine) plant).detonate(target, this);
            if (plant.getLife() <= 0) {
                plants.remove(position); // Eliminar la mina tras explotar.
            }
        }
    }

    public Zombie findClosestZombieInRow(int row, int startColumn) {
    Zombie closestZombie = null;
    int minDistance = Integer.MAX_VALUE;
    for (Point pos : zombies.keySet()) {
        int zombieRow = getRowFromYForZombies(pos.y);
        System.out.println("Revisando posición de zombi: " + pos + " en fila " + zombieRow);
        if (zombieRow == row && pos.x >= startColumn) {
            int distance = pos.x - startColumn;
            if (distance < minDistance) {
                minDistance = distance;
                closestZombie = zombies.get(pos);
                System.out.println("Zombi más cercano actualizado: " + closestZombie.getName() + " en posición " + pos);
            }
        }
    }
    return closestZombie;
}

    public void printBoardState() {
        System.out.println("Estado del tablero:");
        System.out.println("Plantas:");
        for (Point pos : plants.keySet()) {
            Plant plant = plants.get(pos);
            System.out.println(" - " + plant.getName() + " en posición " + getRowFromYForPlants(pos.y) + " en Y, y con vida: " + plant.getLife());
            System.out.println(" - " + sunPoints + " soles");
        }
        System.out.println("Zombis:");
        for (Point pos : zombies.keySet()) {
            Zombie zombie = zombies.get(pos);
            int zombieRow = getRowFromYForZombies(pos.y);
            System.out.println(" - " + zombie.getName() + " en posición " + zombieRow + " en Y, y con vida: " + zombie.getLife());
        }
    }

    public List<Zombie> getZombiesAtPosition(Point position) {
        return zombies.values().stream().filter(zombie -> zombie.getPosition().equals(position)).collect(Collectors.toList());
    }

    public void updateZombiesWhenLawnMower() {
        // Crear una copia de las entradas del mapa para iterar de forma segura
        List<Map.Entry<Point, Zombie>> entries = new ArrayList<>(zombies.entrySet());

        List<Point> toRemove = new ArrayList<>();

        for (Map.Entry<Point, Zombie> entry : entries) {
            Zombie zombie = entry.getValue();
            zombie.move(this); // Mover al zombi

            // Activar la podadora si el zombi alcanza el extremo izquierdo
            if (zombie.getPosition().x == 0) {
                LawnMower mower = lawnMowers.get(zombie.getPosition().y);
                if (mower != null) {
                    mower.activate(this);
                    toRemove.add(entry.getKey()); // Marcar la posición para eliminar
                    System.out.println("Zombie eliminado por LawnMower en posición " + zombie.getPosition());
                }
            }
        }

        // Aplicar los cambios al mapa fuera del bucle
        for (Point position : toRemove) {
            zombies.remove(position);
        }
    }


}
