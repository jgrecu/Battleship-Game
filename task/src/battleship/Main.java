package battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public enum Ships {
        CARRIER("Aircraft Carrier", 5),
        BATTLESHIP("Battleship", 4),
        SUBMARINE("Submarine", 3),
        CRUISER("Cruiser", 3),
        DESTROYER("Destroyer", 2);

        private String name;
        private int size;

        Ships(String name, int size) {
            this.name = name;
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public int getSize() {
            return size;
        }
    }

    public static final String LETTERS = "ABCDEFGHIJ";
    public static void main(String[] args) {
        final String[][] battleBoard = createBoard();

        printBoard(battleBoard);

        //getCoordinates();
        //getCoordinates(Ships.SUBMARINE, battleBoard);
        //checkCoordinates(getCoordinates(), 5);
        for (Ships ship : Ships.values()) {
            System.out.println(ship.getName());
            getCoordinates(ship, battleBoard);
        }
    }

    private static String[][] createBoard() {
        final String[][] battleBoard = new String[10][10];
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

    private static String[] getCoordinates() {
        String[] strings = new String[2];
        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.nextLine();
            if (input.length() < 5 || input.length() > 7) {
                System.out.println("Error! Invalid coordinates");
            }
            strings = input.split(" ");
        }
        System.out.println(Arrays.toString(strings));
        return strings;
    }

    private static void getCoordinates(Ships value, String[][] board) {
        String[] strings = new String[2];

        System.out.println("Enter the coordinates of the " + value.getName() + " (" + value.getSize() + " cells):");

        try (Scanner scanner = new Scanner(System.in)) {
            boolean isFound = false;
            while (!isFound) {
                System.out.println("TEST!");
                String input = scanner.nextLine();
                if (input.length() < 5 || input.length() > 7) {
                    System.out.println("Error! Invalid coordinates! Try again:");
                }
                strings = input.split(" ");
                System.out.println(Arrays.toString(strings));
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
        }

        //return ;
    }

    private static boolean checkCoordinates(String[] strings, Ships ship, String[][] board) {
        String start = strings[0];
        String end = strings[1];

        if (LETTERS.indexOf(start.charAt(0)) == -1 || LETTERS.indexOf(end.charAt(0)) == -1) {
            System.out.println("Error! Invalid coordinates! Try again:");
            return false;
        }

        int x1 = Integer.parseInt(strings[0].substring(1)) - 1;
        int x2 = Integer.parseInt(strings[1].substring(1)) - 1;
        int y1 = LETTERS.indexOf(strings[0].charAt(0));
        int y2 = LETTERS.indexOf(strings[1].charAt(0));


        if (y1 == y2) {
            if (Math.abs(x2 - x1) + 1 != ship.getSize()) {
                System.out.println("Error! Wrong length of the " + ship.getName() + "! Try again:");
                return false;
            } else {
                if (!checkValidShip(board, y1, x1, y2, x2)) {
                    return false;
                }
            }
        } else if (x1 == x2) {
            if (Math.abs(y2 - y1) + 1 != ship.getSize()) {
                System.out.println("Error! Wrong length of the " + ship.getName() + "! Try again:");
                return false;
            } else {
                if (!checkValidShip(board, y1, x1, y2, x2)) {
                    return false;
                }
            }
        } else {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }

        return true;
    }

    private static boolean checkValidShip(String[][] board, int y1, int x1, int y2, int x2) {
        final int  MIN_X = 0, MIN_Y = 0, MAX_X = 10, MAX_Y = 10;

        int sizex = Math.abs(x2 - x1);
        int sizey = Math.abs(y2 - y1);
        int x = Math.min(x2, x1);
        int y = Math.min(y2, y1);

        if (y1 == y2) {
            for (int i = 0; i <= sizex; i++) {
                int thisPosX = i + x;
                int thisPosY = y1;
                int startPosX = (thisPosX - 1 <= MIN_X) ? thisPosX : thisPosX-1;
                int startPosY = (thisPosY - 1 <= MIN_Y) ? thisPosY : thisPosY-1;
                int endPosX =   (thisPosX + 1 >= MAX_X) ? thisPosX : thisPosX+1;
                int endPosY =   (thisPosY + 1 >= MAX_Y) ? thisPosY : thisPosY+1;

                for (int rowNum=startPosY; rowNum<=endPosY; rowNum++) {
                    for (int colNum=startPosX; colNum<=endPosX; colNum++) {
                        if (!board[colNum][rowNum].equals("~")) {
                            System.out.println("Error! You placed it too close to another one. Try again:");
                            return false;
                        }
                    }
                }
            }
        } else if (x1 == x2) {
            for (int i = 0; i <= sizey; i++) {
                int thisPosX = x1;
                int thisPosY = i + y;
                int startPosX = (thisPosX - 1 <= MIN_X) ? thisPosX : thisPosX-1;
                int startPosY = (thisPosY - 1 <= MIN_Y) ? thisPosY : thisPosY-1;
                int endPosX =   (thisPosX + 1 >= MAX_X) ? thisPosX : thisPosX+1;
                int endPosY =   (thisPosY + 1 >= MAX_Y) ? thisPosY : thisPosY+1;

                for (int rowNum=startPosY; rowNum<=endPosY; rowNum++) {
                    for (int colNum=startPosX; colNum<=endPosX; colNum++) {
                        if (!board[colNum][rowNum].equals("~")) {
                            System.out.println("Error! You placed it too close to another one. Try again:");
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private static void placeShips(String[][] battleBoard, Ships ship, int y1, int x1, int y2, int x2) {
        //final String[][] tempBoard = Arrays.copyOf(battleBoard, 10);
        for (int i = 0; i < ship.getSize(); i++) {
            if (y1 == y2) {
                battleBoard[y1][i + x1] = "O";
            } else if (x1 == x2) {
                battleBoard[y1 + i][x1] = "O";
            }
        }
        //return tempBoard;
    }
}
