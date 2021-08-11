package battleship;

import java.util.Arrays;

public class GameField {
    final private String[][] battleBoard;
    final private String LETTERS = "ABCDEFGHIJ";
    final private int FIELDSIZE = 10;

    public GameField() {
        this.battleBoard = new String[FIELDSIZE][FIELDSIZE];
        for (String[] strings : this.battleBoard) {
            Arrays.fill(strings, "~");
        }
    }

    public void printBoard() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < 10; i++) {
            System.out.print(Character.valueOf(LETTERS.charAt(i)).toString() + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(battleBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printBoardFog() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < 10; i++) {
            System.out.print(Character.valueOf(LETTERS.charAt(i)).toString() + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print((battleBoard[i][j].equals("O") ? "~" : battleBoard[i][j]) + " ");
            }
            System.out.println();
        }
    }

    public boolean getCoordinates(String input) {
        final String matcher = "[A-J][0-9]0?";

        if (!input.matches(matcher)) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        }

        int y = LETTERS.indexOf(input.charAt(0));
        int x = Integer.parseInt(input.substring(1)) - 1;

        if (battleBoard[y][x].equals("O")) {
            System.out.println("You hit a ship!");
            battleBoard[y][x] = "X";
        } else {
            System.out.println("You missed!");
            battleBoard[y][x] = "M";
        }
        printBoard();
        return true;
    }

        public boolean putShip(String input, ShipType shipType) {
        final String matcher = "[A-J][0-9]0?(\\s*)?[A-J][0-9]0?";
        String[] strings;

        if (!input.matches(matcher)) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        }

        strings = input.split("\\s+");

        int x1 = Integer.parseInt(strings[0].substring(1)) - 1;
        int x2 = Integer.parseInt(strings[1].substring(1)) - 1;
        int y1 = LETTERS.indexOf(strings[0].charAt(0));
        int y2 = LETTERS.indexOf(strings[1].charAt(0));

        if (checkCoordinates(y1, x1, y2, x2, shipType)) {
            placeShips(y1, x1, y2, x2, shipType);
            printBoard();
            return true;
        }
        return false;
    }

    private boolean checkCoordinates(int y1, int x1, int y2, int x2, ShipType shipType) {
        if (checkDiagonals(y1, x1, y2, x2)) {
            int size = x1 == x2 ? Math.abs(y2 - y1) + 1 : Math.abs(x2 - x1) + 1;
            if (checkShipLength(size, shipType)) {
                return checkValidShip(y1, x1, y2, x2);
            }
        }
        return false;
    }

    private boolean checkDiagonals(int y1, int x1, int y2, int x2) {
        if (y1 != y2 && x1 != x2) {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }
        return true;
    }

    private boolean checkShipLength(int size, ShipType shipType) {
        if (size != shipType.getSize()) {
            System.out.println("Error! Wrong length of the " + shipType.getName() + "! Try again:");
            return false;
        }
        return true;
    }

    private boolean checkValidShip(int y1, int x1, int y2, int x2) {
        final int  MIN_X = 0, MIN_Y = 0, MAX_X = FIELDSIZE - 1, MAX_Y = FIELDSIZE - 1;
        final int size = y1 == y2 ? Math.abs(x2 - x1) : Math.abs(y2 - y1);

        for (int i = 0; i <= size; i++) {
            int thisPosX = y1 == y2 ? i + Math.min(x2, x1) : Math.min(x2, x1);
            int thisPosY = x1 == x2 ? i + Math.min(y2, y1) : Math.min(y2, y1);
            int startPosX = (thisPosX - 1 < MIN_X) ? thisPosX : thisPosX-1;
            int startPosY = (thisPosY - 1 < MIN_Y) ? thisPosY : thisPosY-1;
            int endPosX =   (thisPosX + 1 > MAX_X) ? thisPosX : thisPosX+1;
            int endPosY =   (thisPosY + 1 > MAX_Y) ? thisPosY : thisPosY+1;

            for (int rowNum=startPosY; rowNum<=endPosY; rowNum++) {
                for (int colNum=startPosX; colNum<=endPosX; colNum++) {

                    if (!battleBoard[rowNum][colNum].equals("~")) {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void placeShips(int y1, int x1, int y2, int x2, ShipType shipType) {
        for (int i = 0; i < shipType.getSize(); i++) {
            if (y1 == y2) {
                battleBoard[y1][i + Math.min(x1,x2)] = "O";
            } else if (x1 == x2) {
                battleBoard[Math.min(y1,y2) + i][x1] = "O";
            }
        }
    }
}
