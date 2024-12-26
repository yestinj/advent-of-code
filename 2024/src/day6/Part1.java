package day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Part1 {

  /*
   * https://adventofcode.com/2024/day/6
   */
  public static void main(String[] args) throws IOException {

    // Read in the data
    final String inputFilename = args[0];
    final List<String> input = Files.readAllLines(Paths.get(inputFilename));

    // populate the initial grid
    // . is an empty space
    // # is an obstacle
    // ^, v, <, > is the direction the guard is facing
    // guard will keep going until he hits an obstacle, then turn right
    // when the guard walks out of the grid, we're done!
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

    // now the hard part
    // we need to walk the grid until we hit an obstacle
    // then turn right
    // if we hit the edge of the grid, we're done
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
      //System.out.println("Guard is at: " + guardX + ", " + guardY + " facing " + guardDirection);
      // check if we're done
    } while (guardX - 1 >= 0 && guardX + 1 < grid.length && guardY - 1 >= 0 && guardY + 1 < grid[0].length);

    System.out.println("Guard walked the grid and is done!");
    // he was last seen at
    System.out.println("Guard last seen at: " + guardX + ", " + guardY + " facing " + guardDirection);

    //grid[guardX][guardY] = 'O';

    // now scan the grid and count the number of x's to get the answer
    int uniqueStepCount = 0;
    for (char[] value : grid) {
      for (char c : value) {
        if (c == 'x') {
          uniqueStepCount++;
        }
      }
    }

    // is this off by one? Or another error?
    uniqueStepCount += 1;

    System.out.println("The guard covered " + uniqueStepCount + " distinct sectors");

    // print out the entire update grid
//    System.out.println("Final grid:");
//    for (char[] chars : grid) {
//      for (char aChar : chars) {
//        System.out.print(aChar + " ");
//      }
//      System.out.println("\n");
//    }

  }

}

// answer 4967, so it must just have been off by 1
