package fr.ippon;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameLife {

    public static final char DEAD_CHAR = '.';

    public static final char ALIVE_CHAR = '*';

    public static final String NEXT_GENERATION_HEADER = "Generation 2:";

    private int nbRaw;

    private int nbCol;

    private char[][] grid;

    public GameLife(int nbRaw, int nbCol, char[][] grid) {
        this.nbRaw = nbRaw;
        this.nbCol = nbCol;
        this.grid = grid;
    }

    public GameLife(List<String> startingPosition) {
        if (startingPosition.size() > 2) {
            final String [] gridDimensions = startingPosition.get(1).split(" ");
            if (gridDimensions.length > 0) {
                this.nbRaw = Integer.parseInt(gridDimensions[0]);
                this.nbCol = Integer.parseInt(gridDimensions[1]);
            }
            this.grid = getGridFromLines(startingPosition.subList(2, startingPosition.size()));
        }
    }

    public String getNextGenerationOutput() {
        final String gridDimensions = String.join(" ",String.valueOf(nbRaw), String.valueOf(nbCol));
        final String nextGrid = Stream.of(getNextGenerationGrid())
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));

        return String.join("\n",NEXT_GENERATION_HEADER, gridDimensions, nextGrid);

    }

    protected static char[][] getGridFromLines(List<String> gridLines) {
        return gridLines.stream()
                    .map(String::toCharArray)
                    .collect(Collectors.toList())
                    .toArray(new char[][]{});
    }

    protected char[][] getNextGenerationGrid() {
        char [][] nextGenGrid = new char[this.nbRaw][this.nbCol];

        for (int currentRaw = 0; currentRaw < this.grid.length; currentRaw++) {

            for (int currentCol = 0; currentCol < this.grid[currentRaw].length; currentCol++) {
                nextGenGrid[currentRaw][currentCol] = getNextGenerationCell(currentRaw, currentCol);
            }
        }
        return nextGenGrid;
    }

    protected char getNextGenerationCell(int numRaw, int numCol) {
        int aliveNeighbors = countAliveNeighbors(numRaw, numCol);

        if (aliveNeighbors == 3) {
            return ALIVE_CHAR;
        }
        else if (aliveNeighbors < 2 || aliveNeighbors > 3) {
            return DEAD_CHAR;
        }
        else {
            return this.grid[numRaw][numCol];
        }
    }

    protected int countAliveNeighbors(int numRaw, int numCol) {
        int aliveNeighbors = 0;

        final int minRaw = numRaw == 0 ? numRaw: numRaw - 1;
        final int maxRaw = numRaw == this.grid.length - 1 ? numRaw : numRaw + 1;
        final int minCol = numCol == 0 ? numCol : numCol - 1;
        final int maxCol = numCol == this.grid[numRaw].length - 1 ? numCol : numCol + 1;

        for (int currentRaw = minRaw; currentRaw <= maxRaw; currentRaw++) {

            for (int currentCol = minCol; currentCol <= maxCol; currentCol++) {

                if (currentCol != numCol || currentRaw != numRaw) {
                    char cell = this.grid[currentRaw][currentCol];

                    if (cell == ALIVE_CHAR) {
                        aliveNeighbors++;
                    }

                }
            }
        }
        return aliveNeighbors;
    }

}
