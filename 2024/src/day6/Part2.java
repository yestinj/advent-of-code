package day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Part2 {

  /*
   * https://adventofcode.com/2024/day/6
   */
  public static void main(String[] args) throws IOException {

    // Read in the data
    final String inputFilename = args[0];
    final List<String> input = Files.readAllLines(Paths.get(inputFilename));

    final char[][] grid = new char[input.size()][input.size()];
    for (int i = 0; i < input.size(); i++) {
      grid[i] = input.get(i).toCharArray();
    }

    // make sure its stored correctly
    System.out.println("Initial grid:");
    for (char[] value : grid) {
      for (char c : value) {
        System.out.print(c + " ");
      }
      System.out.println("\n");
    }

    // find the guard
    int guardX = -1;
    int guardY = -1;
    char guardDirection = ' ';
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        final char contents = grid[i][j];
        if (contents == '^' || contents == 'v' || contents == '<' || contents == '>') {
          guardX = i;
          guardY = j;
          guardDirection = contents;
          break;
        }
      }
    }
    if (guardX == -1) {
      throw new RuntimeException("Guard not found!");
    }
    System.out.println("Guard found at: " + guardX + ", " + guardY + " facing " + guardDirection);

    // iterate over the gird, inserting obstacles one at a time in every position,
    // then running the simulation again and finding how many versions there are where
    // the guard gets stuck in an endless loop
    int stuckCount = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        System.out.println("Testing with obstacle at: " + i + ", " + j);
        if (grid[i][j] == '.') {
          grid[i][j] = '#';
          // make a new copy of the grid to pass to the simulation so we don't mess up the original
          char[][] copy = copyGrid(grid);
          // run the simulation
          final boolean getsStuck = runSimulation(copy, guardX, guardY, guardDirection);
          if (getsStuck) {
            stuckCount++;
          }
          // reset the grid
          grid[i][j] = '.';
        }
      }
    }

    System.out.println("Stuck count: " + stuckCount);

  }

  private static boolean runSimulation(char[][] grid, int guardX, int guardY, char guardDirection) {
    int loopCount = 0;
    do {
      // walk until we hit an obstacle
      while (true) {
        if (guardDirection == '^') {
          if (guardX - 1 < 0 || grid[guardX - 1][guardY] == '#') {
            break;
          }
          //update the grid to put an x where the guard has walked
          grid[guardX][guardY] = 'x';
          guardX--;
        } else if (guardDirection == 'v') {
          if (guardX + 1 >= grid.length || grid[guardX + 1][guardY] == '#') {
            break;
          }

          grid[guardX][guardY] = 'x';
          guardX++;
        } else if (guardDirection == '<') {
          if (guardY - 1 < 0 || grid[guardX][guardY - 1] == '#') {
            break;
          }
          grid[guardX][guardY] = 'x';
          guardY--;
        } else {
          if (guardY + 1 >= grid[0].length || grid[guardX][guardY + 1] == '#') {
            break;
          }
          grid[guardX][guardY] = 'x';
          guardY++;
        }
      }
      // turn right
      if (guardDirection == '^') {
        guardDirection = '>';
      } else if (guardDirection == 'v') {
        guardDirection = '<';
      } else if (guardDirection == '<') {
        guardDirection = '^';
      } else {
        guardDirection = 'v';
      }
      loopCount++;
    } while ((guardX - 1 >= 0 && guardX + 1 < grid.length && guardY - 1 >= 0 && guardY + 1 < grid[0].length) &&
            loopCount < 1000);
    return loopCount >= 1000;
  }

  private static char[][] copyGrid(char[][] original) {
    char[][] copy = new char[original.length][];
    for (int i = 0; i < original.length; i++) {
      copy[i] = original[i].clone();
    }
    return copy;
  }

}
