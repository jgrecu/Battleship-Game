package battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static final String LETTERS = "ABCDEFGHIJ";
    public static final int FIELDSIZE = 10;
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final String[][] battleBoard = createBoard();

        printBoard(battleBoard);

        //getCoordinates(scanner, Ships.DESTROYER, battleBoard);

        for (Ship ship : Ship.values()) {
            System.out.println();
            getCoordinates(scanner, ship, battleBoard);
        }
        System.out.println("\nThe game starts!\n");
        printBoard(battleBoard);
        getCoordinates(scanner, battleBoard);
        scanner.close();
    }

    private static String[][] createBoard() {
        final String[][] battleBoard = new String[FIELDSIZE][FIELDSIZE];
        for (String[] strings : battleBoard) {
            Arrays.fill(strings, "~");
        }
        return battleBoard;
    }

    private static void printBoard(String[][] battleBoard) {
        for (int i = 0; i <= 10; i++) {
            System.out.print(i == 0 ? " " : " " + i);
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print(Character.valueOf(LETTERS.charAt(i)).toString() + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(battleBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void getCoordinates(Scanner scanner, String[][] board) {
        final String matcher = "[A-J][0-9]0?";

        System.out.println("\nTake a shot!\n");

        while (true) {
            String input = scanner.nextLine();

            if (!input.matches(matcher)) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                continue;
            }


            int y = LETTERS.indexOf(input.charAt(0));
            int x = Integer.parseInt(input.substring(1)) - 1;

            if (board[y][x].equals("O")) {
                System.out.println("You hit a ship!");
                board[y][x] = "X";
                printBoard(board);
            } else {
                System.out.println("You missed!");
                board[y][x] = "M";
                printBoard(board);
            }
            break;

        }

}

    private static void getCoordinates(Scanner scanner, Ship value, String[][] board) {
        final String matcher = "[A-J][0-9]0?(\\s*)?[A-J][0-9]0?";
        String[] strings;

        System.out.println("Enter the coordinates of the " + value.getName() + " (" + value.getSize() + " cells):");

        boolean isFound = false;
        while (!isFound) {
            String input = scanner.nextLine();

            if (!input.matches(matcher)) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                continue;
            }

            strings = input.split("\\s+");

            if (checkCoordinates(strings, value, board)) {
                int x1 = Integer.parseInt(strings[0].substring(1)) - 1;
                int x2 = Integer.parseInt(strings[1].substring(1)) - 1;
                int y1 = LETTERS.indexOf(strings[0].charAt(0));
                int y2 = LETTERS.indexOf(strings[1].charAt(0));
                placeShips(board, value, y1, x1, y2, x2);
                printBoard(board);
                isFound = true;
            }
        }
        //return ;
    }

    private static boolean checkCoordinates(String[] strings, Ship ship, String[][] board) {
        String start = strings[0];
        String end = strings[1];

        int x1 = Integer.parseInt(strings[0].substring(1)) - 1;
        int x2 = Integer.parseInt(strings[1].substring(1)) - 1;
        int y1 = LETTERS.indexOf(strings[0].charAt(0));
        int y2 = LETTERS.indexOf(strings[1].charAt(0));


        if (y1 == y2) {
            if (Math.abs(x2 - x1) + 1 != ship.getSize()) {
                System.out.println("Error! Wrong length of the " + ship.getName() + "! Try again:");
                return false;
            } else {
                return checkValidShip(board, y1, x1, y2, x2);
            }
        } else if (x1 == x2) {
            if (Math.abs(y2 - y1) + 1 != ship.getSize()) {
                System.out.println("Error! Wrong length of the " + ship.getName() + "! Try again:");
                return false;
            } else {
                return checkValidShip(board, y1, x1, y2, x2);
            }
        } else {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }
    }

    private static boolean checkValidShip(String[][] board, int y1, int x1, int y2, int x2) {
        final int  MIN_X = 0, MIN_Y = 0, MAX_X = FIELDSIZE - 1, MAX_Y = FIELDSIZE - 1;
        final int size = y1 == y2 ? Math.abs(x2 - x1) : Math.abs(y2 - y1);

        for (int i = 0; i <= size; i++) {
            int thisPosX = y1 == y2 ? i + Math.min(x2, x1) : Math.min(x2, x1);
            int thisPosY = x1 == x2 ? i + Math.min(y2, y1) : Math.min(y2, y1);
            int startPosX = (thisPosX - 1 < MIN_X) ? thisPosX : thisPosX-1;
            int startPosY = (thisPosY - 1 < MIN_Y) ? thisPosY : thisPosY-1;
            int endPosX =   (thisPosX + 1 > MAX_X) ? thisPosX : thisPosX+1;
            int endPosY =   (thisPosY + 1 > MAX_Y) ? thisPosY : thisPosY+1;

            //System.out.println("thisX: " + thisPosX + " thisY: " + thisPosY);
            for (int rowNum=startPosY; rowNum<=endPosY; rowNum++) {
                for (int colNum=startPosX; colNum<=endPosX; colNum++) {

                    if (!board[rowNum][colNum].equals("~")) {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static void placeShips(String[][] battleBoard, Ship ship, int y1, int x1, int y2, int x2) {
        //final String[][] tempBoard = Arrays.copyOf(battleBoard, 10);
        for (int i = 0; i < ship.getSize(); i++) {
            if (y1 == y2) {
                battleBoard[y1][i + Math.min(x1,x2)] = "O";
            } else if (x1 == x2) {
                battleBoard[Math.min(y1,y2) + i][x1] = "O";
            }
        }
        //return tempBoard;
    }
}
