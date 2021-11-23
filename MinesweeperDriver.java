package cs1302.game;

import cs1302.game.MinesweeperGame;
import java.io.*;
import java.util.*;


/**
 * This class creates an object of MinesweeperGame, and plays it.
 * it also reads the seed files and other command-line arguments
 * and handles its exceptions.
 */
public class MinesweeperDriver {

    /**
     * Main method to create MinesweeperGame and play it. It also reads
     * a seed file for the parameters to create the Minesweeper Map.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        String nl = System.getProperty("line.seperator");
        String seed;
        if (args[0].equals("--seed")) {
            seed = args[1];
            try {
                File configFile = new File(seed);
                MinesweeperGame game = new MinesweeperGame(seed);
                game.play();
            } catch (FileNotFoundException e) {
                System.err.println("Seedfile Not Found Error: Cannot create game with FILENAME," +
                                   " because it cannot be be found");
                System.err.println("                          " +
                                   "or cannot be read due to permission.");
                System.exit(1);
            } catch (IllegalArgumentException iae) {
                System.err.println("Seedfile Format Error: Cannot create game with " + seed +
                                   ", because it is not formatted correctly.");
                System.exit(1);
            } // try
        } else {
            if (args[0].equals("--gen")) {
                System.err.println("Seedfile generation not supported.");
                System.exit(2);
            } else {
                System.err.println("Unable to interpret supplied command-line arguments.");
                System.exit(1);
            } // if
        } // if
    } // main
} // MinesweeuperDriver
