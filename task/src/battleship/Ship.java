package battleship;

public class Ship {
    final private int size;
    final private String name;
    private int lives;
    boolean isSunk = false;

    public Ship(ShipType shipType) {
        this.size = shipType.getSize();
        this.name = shipType.getName();
        this.lives = shipType.getSize();
    }

    public int getLives() {
        return lives;
    }

    public void hit() {
        if (lives > 1) {
            lives--;
        } else if (lives == 1) {
            lives--;
            isSunk = true;
        }
    }
}
