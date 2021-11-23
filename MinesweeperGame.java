package cs1302.game;

import java.util.*;
import java.io.*;

/**
 * This class represents a game of Minesweeper Alpha. Each game
 * contains a seed which has the number of rows, columns, and
 * mines. It also keeps track of the number of rounds played.
 */
public class MinesweeperGame {

    int rows;
    int cols;
    int rounds;
    int mines;
    int minesCordX;
    int minesCordY;
    double score = 100.0 * rows * cols / rounds;
    String[][] grid;
    boolean[][] mineGrid;
    String rowsFormat;
    String colsFormat;
    int rowsDigits;
    int colsDigits;
    Scanner input = new Scanner(System.in);
    String nl = System.getProperty("line.separator");
    boolean winState = false;
    boolean lossState = false;

    /**
     * Creates a Minesweeper game with the given seed. It constructs a
     * grid with the rows and columns, and places the mines in the
     * corresponding coordinates.
     *
     * It checks to make sure the string seed is an actual filename,
     * and that the seed file is formatted correctly to make the grid.
     * @param seed - the path for the seed file
     * @throws IllegalArgumentException if the seed is malformed.
     * @throws FileNotFoundException if file is not found or readable.
     */
    public MinesweeperGame(String seed) throws FileNotFoundException, IllegalArgumentException {
        FileNotFoundException fnfe = new FileNotFoundException();
        IllegalArgumentException iae = new IllegalArgumentException();
        try {
            File configFile = new File(seed);
            Scanner configScanner = new Scanner(configFile);
            rows = Integer.parseInt(configScanner.next());
            cols = Integer.parseInt(configScanner.next());
            mines = Integer.parseInt(configScanner.next());
            if (rows < 5 || cols < 5) {
                System.out.println();
                System.out.println("Seedfile Value Error: Cannot create a mine field with" +
                                   " that many rows and/or columns!");
                System.exit(3);
            }
            if (mines > (rows * cols)) {
                throw iae;
            }
            grid = new String[rows][cols];
            mineGrid = new boolean[rows][cols];
            rowsDigits = ((int)(Math.ceil(Math.log10(rows))));
            colsDigits = ((int)(Math.ceil(Math.log10(cols))));
            rowsFormat = "%" + (2 + rowsDigits) + "s";
            colsFormat = "%" + (2 + colsDigits) + "s";

            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    grid[x][y] = String.format(colsFormat, " ");
                    mineGrid[x][y] = false;
                }
            }

            try {
                for (int x = 0; x < mines; x++ ) {
                    minesCordX = configScanner.nextInt();
                    minesCordY = configScanner.nextInt();
                    mineGrid[minesCordX][minesCordY] = true;
                    if (minesCordX >= rows || minesCordX < 0) {
                        throw iae;
                    }
                    if (minesCordY >= cols || minesCordY < 0) {
                        throw iae;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException aioe) {
                throw iae;
            } catch (NoSuchElementException nse) {
                throw iae;
            }
        } catch (FileNotFoundException e) {
            throw fnfe;
        } //try
    } //MinesweeperGame constructor

    /**
     * Prints Welcome banner.
     */
    public void printWelcome() {
        System.out.println("        _" + nl +
                           "  /\\/\\ (_)_ __   ___  _____      _____  ___ _ __   ___ _ __ " + nl +
                           " /    \\| | '_ \\ / _ \\/ __\\ \\ /\\ / " +
                           "/ _ \\/ _ \\ '_ \\ / _ \\ '__|" + nl +
                           "/ /\\/\\ \\ | | | |  __/\\__ \\\\ V  V /  __/  __/ |_) |  __/ |" + nl +
                           "\\/    \\/_|_| |_|\\___||___/ \\_/\\_/" +
                           " \\___|\\___| .__/ \\___|_|" + nl +
                           "                 A L P H A   E D I T I O N |_| v2020.sp");
    } //printWelcome

    /**
     * Prints Loss Banner and exits game.
     */
    public void printLoss() {
        System.out.println();
        System.out.println(" Oh no... You revealed a mine!" + nl +
                           "  __ _  __ _ _ __ ___   ___    _____   _____ _ __" + nl +
                           " / _` |/ _` | '_ ` _ \\ / _ \\  / _ \\ \\ / / _ \\ '__|" + nl +
                           "| (_| | (_| | | | | | |  __/ | (_) \\ V /  __/ |" + nl +
                           " \\__, |\\__,_|_| |_| |_|\\___|  \\___/ \\_/ \\___|_|" + nl +
                           " |___/");
        System.out.println();
        System.exit(0);
    } //printLoss

    /**
     * Prints Win Banner with {@code score} and exits game.
     */
    public void printWin() {
        score = 100.0 * rows * cols / rounds;
        System.out.println();
        System.out.println(" ░░░░░░░░░▄░░░░░░░░░░░░░░▄░░░░ \"So Doge\"" + nl +
                           " ░░░░░░░░▌▒█░░░░░░░░░░░▄▀▒▌░░░" + nl +
                           " ░░░░░░░░▌▒▒█░░░░░░░░▄▀▒▒▒▐░░░ \"Such Score\"" + nl +
                           " ░░░░░░░▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐░░░" + nl +
                           " ░░░░░▄▄▀▒░▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐░░░ \"Much Minesweeping\"" + nl +
                           " ░░░▄▀▒▒▒░░░▒▒▒░░░▒▒▒▀██▀▒▌░░░" + nl +
                           " ░░▐▒▒▒▄▄▒▒▒▒░░░▒▒▒▒▒▒▒▀▄▒▒▌░░ \"Wow\"" + nl +
                           " ░░▌░░▌█▀▒▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐░░" + nl +
                           " ░▐░░░▒▒▒▒▒▒▒▒▌██▀▒▒░░░▒▒▒▀▄▌░" + nl +
                           " ░▌░▒▄██▄▒▒▒▒▒▒▒▒▒░░░░░░▒▒▒▒▌░" + nl +
                           " ▀▒▀▐▄█▄█▌▄░▀▒▒░░░░░░░░░░▒▒▒▐░" + nl +
                           " ▐▒▒▐▀▐▀▒░▄▄▒▄▒▒▒▒▒▒░▒░▒░▒▒▒▒▌" + nl +
                           " ▐▒▒▒▀▀▄▄▒▒▒▄▒▒▒▒▒▒▒▒░▒░▒░▒▒▐░" + nl +
                           " ░▌▒▒▒▒▒▒▀▀▀▒▒▒▒▒▒░▒░▒░▒░▒▒▒▌░" + nl +
                           " ░▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▒▄▒▒▐░░" + nl +
                           " ░░▀▄▒▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▄▒▒▒▒▌░░" + nl +
                           " ░░░░▀▄▒▒▒▒▒▒▒▒▒▒▄▄▄▀▒▒▒▒▄▀░░░ CONGRATULATIONS!" + nl +
                           " ░░░░░░▀▄▄▄▄▄▄▀▀▀▒▒▒▒▒▄▄▀░░░░░ YOU HAVE WON!" + nl +
                           " ░░░░░░░░░▒▒▒▒▒▒▒▒▒▒▀▀░░░░░░░░ SCORE:" + score);
        System.out.println();
        System.exit(0);
    } //printWin

    /**
     * Prints out updated mine field.
     * Also prints out the number of rounds completed, and Game title at bottom.
     */
    public void printMineField() {
        System.out.println();
        System.out.println(" Rounds Completed: " + rounds);

        for (int x = 0; x < rows; x++) {
            System.out.println();
            System.out.printf(rowsFormat, (x + " "));
            System.out.print("|");
            for (int y = 0; y < cols; y++) {
                System.out.print(grid[x][y] + "|");
            }
        }
        System.out.println();
        System.out.printf(rowsFormat, " ");
        System.out.print(" ");

        for (int y = 0; y < cols; y++) {
            System.out.printf(colsFormat, (y + " "));
            System.out.print(" ");
        }
        System.out.println();
        System.out.println();
    } //printMineField

    /**
     * Reads users input and checks the syntax for the corresponding option.
     * Depending on the option and coordinates, the squares on the grid are updated.
     */
    public void promptUser() {
        System.out.print("minesweeper-alpha: ");
        int commandX = -1;
        int commandY = -1;
        ArrayList<String> tokens = new ArrayList<String>();
        Scanner tokenize = new Scanner(input.nextLine());
        while (tokenize.hasNext()) {
            tokens.add(tokenize.next());
        }
        if (tokens.size() > 3) {
            printPrompterr();
        } else {
            try {
                switch (tokens.get(0)) {
                case "m":
                case "mark":
                case "g":
                case "guess":
                case "reveal":
                case "r":
                    promptHelper(tokens, commandX, commandY);
                    break;
                case "nofog":
                    for (int a = 0; a < rows; a++) {
                        for (int b = 0; b < cols; b++) {
                            if (mineGrid[a][b]) {
                                grid[a][b] = "<" +
                                    grid[a][b].substring(1,(grid[a][b].length() - 1)) +
                                    ">";
                            }
                        }
                    }
                    rounds++;
                    break;
                case "help":
                case "h":
                    System.out.println();
                    System.out.println("Commands Available..." + nl +
                                       " - Reveal: r/reveal row col" + nl +
                                       " -   Mark: m/mark   row col" + nl +
                                       " -  Guess: g/guess  row col" + nl +
                                       " -   Help: h/help" + nl + " -   Quit: q/quit");
                    break;
                case "quit":
                case "q":
                    System.out.println();
                    System.out.println("Quitting the game..." + nl + "Bye!" + nl);
                    System.exit(0);
                    break;
                default:
                    printPrompterr();
                    break;
                } // switch
            } catch (ArrayIndexOutOfBoundsException aoe) {
                printPrompterr();
            } // try catch
        } // if
        winState = isWon();
        lossState = isLost();
    } //promptUser

    /**
     * Extends the code for promptUser and goes through mark, reveal, and guess commands
     * and updates the grid.
     * @param tokens the array of tokens from input.
     * @param commandX the x coordinate of the command.
     * @param commandY the y coordinate of the command.
     */
    public void promptHelper(ArrayList<String> tokens, int commandX, int commandY) {
        try {
            switch (tokens.get(0)) {
            case "mark":
            case "m":
                commandX = Integer.parseInt(tokens.get(1));
                commandY = Integer.parseInt(tokens.get(2));
                if (isInBounds(commandX, commandY)) {
                    grid[commandX][commandY] = String.format(colsFormat, "F ");
                    rounds++;
                    break;
                }
            case "r":
            case "reveal":
                commandX = Integer.parseInt(tokens.get(1));
                commandY = Integer.parseInt(tokens.get(2));
                if (isInBounds(commandX, commandY)) {
                    grid[commandX][commandY] = String.format(colsFormat, +
                                                             getNumAdjMines(commandX, commandY) +
                                                             " ");
                    rounds++;
                    break;
                }
            case "guess":
            case "g":
                commandX = Integer.parseInt(tokens.get(1));
                commandY = Integer.parseInt(tokens.get(2));
                if (isInBounds(commandX, commandY)) {
                    grid[commandX][commandY] = String.format(colsFormat, "? ");
                    rounds++;
                    break;
                }
            default:
                printPrompterr();
                break;
            } // switch
        } catch (IndexOutOfBoundsException iobe) {
            printPrompterr();
        } //try catch
    } // promptHelper

    /**
     * Prints error for the promptUser() method where command isn't
     * formatted right or not recognized.
     */
    public void printPrompterr() {
        System.out.println();
        System.out.println("Input Error: Command not recognized!");
    }

    /**
     * Indicates whether or not the square is in the game grid.
     *
     * @param row the row index of the square
     * @param col the column index of the square
     * @return true if the square is in the game grid; false otherwise
     */
    private boolean isInBounds(int row, int col) {
        if ((row >= 0 && row < rows) && (col >= 0 && col < cols)) {
            return true;
        } else {
            return false;
        }
    } // isInBounds

    /**
     * Returns the number of mines adjacent to the specified
     * square in the grid.
     *
     * @param row the row index of the square
     * @param col the column index of the square
     * @return the number of adjacent mines
     */
    private int getNumAdjMines(int row, int col) {
        int numAdjMines = 0;
        for (int a = row - 1; a <= row + 1; a++) {
            for (int b = col - 1; b <= col + 1; b++) {
                if (isInBounds(a, b) && mineGrid[a][b]) {
                    numAdjMines++;
                }
            }
        }
        return numAdjMines;
    } // getNumAdjMines

    /**
     * Checks to see if the player's grid has "F" for all places
     * where the mine grid has a mine.
     * @return true if the Game is won.
     */
    public boolean isWon() {
        boolean won1 = true;
        boolean won2 = true;
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                if (mineGrid[x][y] && (grid[x][y].indexOf("F") == - 1)) {
                    won1 = false;
                }
                if (!(mineGrid[x][y])) {
                    if (!(grid[x][y].matches(".*\\d.*"))) {
                        won2 = false;
                    }
                }
            }
        }
        return (won1 && won2);
    } // isWon

    /**
     * Checks to see if the player has revealed a square with a mine on it.
     * @return true if the Game is lost.
     */
    public boolean isLost() {
        boolean lost = false;
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                if ((mineGrid[x][y]) && grid[x][y].matches(".*\\d.*")) {
                    lost = true;
                }
            }
        }
        return lost;
    } // isLost

    /**
     * Provides main game loop.
     */
    public void play() {
        printWelcome();
        printMineField();
        boolean gameState = false;
        while (!gameState) {
            promptUser();
            if (winState) {
                printWin();
                gameState = true;
            }
            if (lossState) {
                printLoss();
                gameState = true;
            }
            printMineField();
        }
    } // play
} // MinesweeperGame class
