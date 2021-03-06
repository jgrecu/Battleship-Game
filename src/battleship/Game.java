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
//        printBoardsP1();
//
//        System.out.println("\nThe game starts!\n");
        startBattle();
        scanner.close();
    }

    private void initializePlayers() {
        players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i] = new Player("Player " + (i + 1));
        }
    }
    private void setBoard() {
        for (Player player : players) {
            System.out.println(player.getName() + ", place your ships on the game field\n");
            player.getField().printBoard();
            System.out.println();

            for (ShipType shipType : ShipType.values()) {
                System.out.println("Enter the coordinates of the " + shipType.getName() + " (" + shipType.getSize() + " cells):");
                setShip(shipType, player);
            }
            System.out.println("\nPress Enter and pass the move to another player\n");
            scanner.nextLine();
        }
    }

    private void startBattle(){
        int player = 1;
        while (true) {
            if (player % 2 != 0) {
                printBoardsP1();
                String input = scanner.nextLine();
                if (players[0].takeShot(players[1],input)) {
                    break;
                } else {
                    System.out.println("\nPress Enter and pass the move to another player\n");
                    scanner.nextLine();
                }
            } else {
                printBoardsP2();
                String input = scanner.nextLine();
                if (players[1].takeShot(players[0], input)) {
                    break;
                } else {
                    System.out.println("\nPress Enter and pass the move to another player\n");
                    scanner.nextLine();
                }
            }
            player++;
        }
    }

    private void setShip(ShipType shipType, Player player){
        boolean placed = false;
        while (!placed){
            String input = scanner.nextLine();
            placed = player.getField().putShip(input, shipType);
        }
    }

    private void printBoardsP1() {
        players[1].getField().printBoardFog();
        System.out.println("---------------------");
        players[0].getField().printBoard();
        System.out.println("\n" + players[0].getName() + ", it's your turn:");
    }

    private void printBoardsP2() {
        players[0].getField().printBoardFog();
        System.out.println("---------------------");
        players[1].getField().printBoard();
        System.out.println("\n" + players[1].getName() + ", it's your turn:");
    }
}
