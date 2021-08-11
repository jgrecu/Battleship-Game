package battleship;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        GameField gameField = new GameField();


        gameField.printBoard();

        for (ShipType shipType : ShipType.values()) {
            System.out.println();

            while (true) {
                String input = scanner.nextLine();
                if (gameField.getShipCoordinates(input, shipType)) {
                    break;
                }
            }
        }

        System.out.println("\nThe game starts!\n");


        gameField.printBoardFog();
        System.out.println("\nTake a shot!\n");
        while (true) {
            String input = scanner.nextLine();
            if (gameField.getCoordinates(input)) {
                break;
            }
        }

        gameField.printBoard();
        scanner.close();
    }

}
