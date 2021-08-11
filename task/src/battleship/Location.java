package battleship;

public class Location {
    final private int row;
    final private int column;

    public Location(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return "Location{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
