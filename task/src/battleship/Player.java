package battleship;

public class Player {
    private GameField field;
    private final String name;

    public Player(String name) {
        this.field = new GameField();
        this.name = name;
    }

    public GameField getField() {
        return field;
    }

    public String getName() {
        return name;
    }

    public boolean takeShot(Player opponent, String coordinates) {
        return opponent.getField().getCoordinates(coordinates);
    }

}
