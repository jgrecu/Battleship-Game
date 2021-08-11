package battleship;

import java.util.Scanner;

public class Game {
    private final GameField gameField;
    private final Scanner scanner;

    public Game() {
        this.gameField = new GameField();
        this.scanner = new Scanner(System.in);
    }

    public void play() {
        setBoard();
        System.out.println("\nThe game starts!\n");
        startBattle();
    }

    private void setBoard() {
        gameField.printBoard();
        System.out.println();

        for (ShipType shipType : ShipType.values()) {
            System.out.println("Enter the coordinates of the " + shipType.getName() + " (" + shipType.getSize() + " cells):");
            setShip(shipType);
        }
    }

    private void startBattle(){
        gameField.printBoardFog();
        System.out.println("\nTake a shot!\n");
        while (true) {
            String input = scanner.nextLine();
            if (gameField.getCoordinates(input)) {
                break;
            }
        }
    }

    private void setShip(ShipType shipType){
        boolean placed = false;
        while (!placed){
            String input = scanner.nextLine();
            placed = gameField.putShip(input, shipType);
        }
    }
}
