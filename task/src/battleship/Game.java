package battleship;

import java.util.Scanner;

public class Game {
//    private final GameField gameField;
    private Player[] players;
    private final int numberOfPlayers = 2;
    private final Scanner scanner = new Scanner(System.in);

//    public Game() {
//        //this.gameField = new GameField();
//        this.player = new Player();
//        this.scanner = new Scanner(System.in);
//    }

    public void play() {
        initializePlayers();
        setBoard();
        printBoards();

        System.out.println("\nThe game starts!\n");
        //startBattle();
        scanner.close();
    }

    private void initializePlayers() {
        players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i] = new Player();
        }
    }
    private void setBoard() {
        for (Player player : players) {
            player.getField().printBoard();
            System.out.println();

            for (ShipType shipType : ShipType.values()) {
                System.out.println("Enter the coordinates of the " + shipType.getName() + " (" + shipType.getSize() + " cells):");
                setShip(shipType, player);
            }
            System.out.println("Press Enter and pass the move to another player");
            scanner.nextLine();
        }
    }

//    private void startBattle(){
//        gameField.printBoardFog();
//        System.out.println("\nTake a shot!\n");
//        while (true) {
//            String input = scanner.nextLine();
//            if (gameField.getCoordinates(input)) {
//                break;
//            }
//        }
//    }

    private void setShip(ShipType shipType, Player player){
        boolean placed = false;
        while (!placed){
            String input = scanner.nextLine();
            placed = player.getField().putShip(input, shipType);
        }
    }
    private void printBoards() {
        players[0].getField().printBoardFog();
        System.out.println("---------------------");
        players[1].getField().printBoardFog();
    }
}
