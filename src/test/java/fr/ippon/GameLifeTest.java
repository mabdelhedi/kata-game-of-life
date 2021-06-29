package fr.ippon;

import org.junit.Test;

import java.util.List;

import static fr.ippon.GameLife.ALIVE_CHAR;
import static fr.ippon.GameLife.DEAD_CHAR;
import static fr.ippon.GameLife.NEXT_GENERATION_HEADER;
import static fr.ippon.GameLife.getGridFromLines;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class GameLifeTest {

    @Test
    public void should_getGridFromLines_return_bidimensional_char_array() {
        final char[][] expectedGrid = new char[][] {
                {ALIVE_CHAR, DEAD_CHAR, DEAD_CHAR},
                {ALIVE_CHAR, DEAD_CHAR, DEAD_CHAR},
                {DEAD_CHAR, DEAD_CHAR,ALIVE_CHAR},
                {DEAD_CHAR, DEAD_CHAR,ALIVE_CHAR}
        };

        final List<String> lines = List.of(
                String.valueOf(expectedGrid[0]),
                String.valueOf(expectedGrid[1]),
                String.valueOf(expectedGrid[2]),
                String.valueOf(expectedGrid[3])
                );

        final char[][] result = getGridFromLines(lines);

        assertEquals(result.length, expectedGrid.length);
        assertArrayEquals(result[0], expectedGrid[0]);
        assertArrayEquals(result[1], expectedGrid[1]);
        assertArrayEquals(result[2], expectedGrid[2]);
        assertArrayEquals(result[3], expectedGrid[3]);
    }

    @Test
    public void should_getNextGenerationLines_return_formatted_output() {
        final char[][] grid = new char[][] {
                {DEAD_CHAR, DEAD_CHAR, ALIVE_CHAR},
                {ALIVE_CHAR, DEAD_CHAR, DEAD_CHAR},
                {ALIVE_CHAR, DEAD_CHAR,ALIVE_CHAR},
                {DEAD_CHAR, DEAD_CHAR,DEAD_CHAR}
        };

        final String expectedResult = String.join("\n",
                NEXT_GENERATION_HEADER,
                "4 3",
                String.valueOf(new char[]{DEAD_CHAR,DEAD_CHAR,DEAD_CHAR}),
                String.valueOf(new char[]{DEAD_CHAR,DEAD_CHAR,DEAD_CHAR}),
                String.valueOf(new char[]{DEAD_CHAR,ALIVE_CHAR,DEAD_CHAR}),
                String.valueOf(new char[]{DEAD_CHAR,DEAD_CHAR,DEAD_CHAR})
        );

        final GameLife gameLife = new GameLife(4,3, grid);
        final String result = gameLife.getNextGenerationOutput();

        assertEquals(result, expectedResult);
    }

    @Test
    public void should_nextGenerationGrid_with_no_changes_return_same_grid() {
        final char[][] grid = new char[][] {
                {DEAD_CHAR, DEAD_CHAR, DEAD_CHAR},
                {DEAD_CHAR, ALIVE_CHAR, ALIVE_CHAR},
                {DEAD_CHAR, ALIVE_CHAR,ALIVE_CHAR},
                {DEAD_CHAR, DEAD_CHAR,DEAD_CHAR}
        };

        final GameLife gameLife = new GameLife(4,3, grid);
        final char [][] result = gameLife.getNextGenerationGrid();

        assertEquals(result.length, grid.length);
        assertArrayEquals(result[0], grid[0]);
        assertArrayEquals(result[1], grid[1]);
        assertArrayEquals(result[2], grid[2]);
        assertArrayEquals(result[3], grid[3]);
    }

    @Test
    public void should_nextGenerationGrid_with_changes_return_new_grid() {
        final char[][] grid = new char[][] {
                {DEAD_CHAR, DEAD_CHAR, ALIVE_CHAR},
                {ALIVE_CHAR, DEAD_CHAR, DEAD_CHAR},
                {ALIVE_CHAR, DEAD_CHAR,ALIVE_CHAR},
                {DEAD_CHAR, DEAD_CHAR,DEAD_CHAR}
        };

        final char[][] expectedGrid = new char[][] {
                {DEAD_CHAR, DEAD_CHAR, DEAD_CHAR},
                {DEAD_CHAR, DEAD_CHAR, DEAD_CHAR},
                {DEAD_CHAR, ALIVE_CHAR,DEAD_CHAR},
                {DEAD_CHAR, DEAD_CHAR,DEAD_CHAR}
        };

        final GameLife gameLife = new GameLife(4,3, grid);
        final char [][] result = gameLife.getNextGenerationGrid();

        assertEquals(result.length, expectedGrid.length);
        assertArrayEquals(result[0], expectedGrid[0]);
        assertArrayEquals(result[1], expectedGrid[1]);
        assertArrayEquals(result[2], expectedGrid[2]);
        assertArrayEquals(result[3], expectedGrid[3]);
    }

    @Test
    public void should_nextGenerationCell_with_alive_char_return_dead_char_when_less_than_2_alive_neighbors() {
        final char[][] grid = new char[][] {
                {ALIVE_CHAR, DEAD_CHAR, DEAD_CHAR},
                {ALIVE_CHAR, DEAD_CHAR, DEAD_CHAR},
                {DEAD_CHAR, DEAD_CHAR,ALIVE_CHAR},
                {DEAD_CHAR, DEAD_CHAR,ALIVE_CHAR}
        };

        final GameLife gameLife = new GameLife(4,3, grid);
        final char result = gameLife.getNextGenerationCell(2,2);

        assertEquals(result, DEAD_CHAR);
    }

    @Test
    public void should_nextGenerationCell_with_alive_char_return_dead_char_when_more_than_3_alive_neighbors() {
        final char[][] grid = new char[][] {
                {ALIVE_CHAR, DEAD_CHAR, DEAD_CHAR},
                {ALIVE_CHAR, DEAD_CHAR, ALIVE_CHAR},
                {DEAD_CHAR, ALIVE_CHAR,ALIVE_CHAR},
                {DEAD_CHAR, DEAD_CHAR,ALIVE_CHAR}
        };

        final GameLife gameLife = new GameLife(4,3, grid);
        final char cell = gameLife.getNextGenerationCell(2,1);

        assertEquals(cell, DEAD_CHAR);
    }

    @Test
    public void should_nextGenerationCell_with_alive_char_return_alive_char_when_2_alive_neighbors() {
        final char[][] grid = new char[][] {
                {ALIVE_CHAR, DEAD_CHAR, DEAD_CHAR},
                {ALIVE_CHAR, DEAD_CHAR, ALIVE_CHAR},
                {DEAD_CHAR, ALIVE_CHAR,ALIVE_CHAR},
                {DEAD_CHAR, DEAD_CHAR,ALIVE_CHAR}
        };

        final GameLife gameLife = new GameLife(4,3, grid);
        final char result = gameLife.getNextGenerationCell(1,2);

        assertEquals(result, ALIVE_CHAR);
    }

    @Test
    public void should_nextGenerationCell_with_dead_char_return_dead_char_when_2_alive_neighbors() {
        char[][] grid = new char[][] {
                {ALIVE_CHAR, DEAD_CHAR, DEAD_CHAR},
                {ALIVE_CHAR, DEAD_CHAR, DEAD_CHAR},
                {DEAD_CHAR, ALIVE_CHAR,ALIVE_CHAR},
                {DEAD_CHAR, DEAD_CHAR,ALIVE_CHAR}
        };

        final GameLife gameLife = new GameLife(4,3, grid);
        char cell = gameLife.getNextGenerationCell(1,2);

        assertEquals(cell, DEAD_CHAR);
    }

    @Test
    public void should_nextGenerationCell_with_dead_char_return_alive_char_when_3_alive_neighbors() {
        final char[][] grid = new char[][] {
                {ALIVE_CHAR, DEAD_CHAR, DEAD_CHAR},
                {ALIVE_CHAR, DEAD_CHAR, DEAD_CHAR},
                {DEAD_CHAR, DEAD_CHAR,ALIVE_CHAR},
                {DEAD_CHAR, DEAD_CHAR,ALIVE_CHAR}
        };

        final GameLife gameLife = new GameLife(4,3, grid);
        final char result = gameLife.getNextGenerationCell(1,1);

        assertEquals(result, ALIVE_CHAR);
    }

    @Test
    public void should_countAliveNeighbors() {
        final char[][] grid = new char[][] {
                {ALIVE_CHAR, DEAD_CHAR, DEAD_CHAR},
                {ALIVE_CHAR, ALIVE_CHAR, DEAD_CHAR},
                {DEAD_CHAR, DEAD_CHAR,ALIVE_CHAR},
                {DEAD_CHAR, DEAD_CHAR,ALIVE_CHAR}
        };

        final GameLife gameLife = new GameLife(4,3, grid);
        final int result = gameLife.countAliveNeighbors(1,1);

        assertEquals(result,3);
    }

    @Test
    public void should_count_aliveNeighbors_when_cell_at_top_left() {
        final char[][] grid = new char[][] {
                {ALIVE_CHAR, DEAD_CHAR, DEAD_CHAR},
                {ALIVE_CHAR, ALIVE_CHAR, DEAD_CHAR},
                {DEAD_CHAR, DEAD_CHAR,ALIVE_CHAR},
                {DEAD_CHAR, DEAD_CHAR,ALIVE_CHAR}
        };

        final GameLife gameLife = new GameLife(4,3, grid);
        final int result = gameLife.countAliveNeighbors(0,0);

        assertEquals(result,2);
    }

    @Test
    public void should_count_aliveNeighbors_when_cell_at_bottom_right() {
        final char[][] grid = new char[][] {
                {ALIVE_CHAR, DEAD_CHAR, DEAD_CHAR},
                {ALIVE_CHAR, ALIVE_CHAR, DEAD_CHAR},
                {DEAD_CHAR, DEAD_CHAR,ALIVE_CHAR},
                {DEAD_CHAR, DEAD_CHAR,DEAD_CHAR}
        };

        final GameLife gameLife = new GameLife(4,3, grid);
        final int result = gameLife.countAliveNeighbors(3,2);

        assertEquals(result,1);
    }

}