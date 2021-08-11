package battleship;

public class Player {
    private GameField field;

    public Player() {
        this.field = new GameField();
    }

    public GameField getField() {
        return field;
    }

    public void takeShot(Player opponent, String coordinates) {
        opponent.getField().getCoordinates(coordinates);
    }
}
