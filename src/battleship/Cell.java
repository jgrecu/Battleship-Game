package battleship;

public class Cell {
    final private Location location;
    private Ship ship;
    private boolean hitOrMiss = false;

    public Cell(Location location) {
        this.location = location;
        this.ship = null;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public boolean isHitOrMiss() {
        return hitOrMiss;
    }

    public void setHitOrMiss(boolean hitOrMiss) {
        this.hitOrMiss = hitOrMiss;
    }

    public Location getLocation() {
        return location;
    }
}
