package day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

  /*
   * https://adventofcode.com/2024/day/8
   */
  public static void main(String[] args) throws IOException {

    // Read in the data
    final String inputFilename = args[0];
    final List<String> input = Files.readAllLines(Paths.get(inputFilename));

    // the input contains a grid, with the following attributes:
    // 1. a . represents an empty space
    // 2. any lowercase, upper case character, or a single digit number represents an antenna
    final Grid grid = new Grid(input);
    System.out.println(grid);

    // find the 'pairs' of antennas in the grid, defined as:
    // for two antennas of the same frequency, they form a pair
    // one antenna can be in multiple pairs
    // a list containing each pair to process
    final List<CoordinatePair> pairs = new ArrayList<>();
    for (int i = 0; i < grid.getGrid().length; i++) {
      for (int j = 0; j < grid.getGrid()[i].length; j++) {
        final char contents = grid.getGrid()[i][j].getFrequency();
        //System.out.println("Finding pairs for: " + contents + " at (" + i + "," + j + ")");
        if (Character.isLetter(contents) || Character.isDigit(contents)) {
          //System.out.println("Finding pairs for: " + contents + " at (" + i + "," + j + ")");
          // find any other GridBlocks that this one forms a pair with
          // only scan forwards from this block
          for (int k = i; k < grid.getGrid().length; k++) {
            int l = i == k ? (j + 1) : 0;
            while (l < grid.getGrid()[k].length) {
              final char otherContents = grid.getGrid()[k][l].getFrequency();
              //System.out.println("> Checking: " + otherContents + " at (" + k + "," + l + ")");
//              if (Character.isLetter(otherContents) || Character.isDigit(otherContents)) {
//                System.out.println(">> Checking: " + otherContents + " at (" + k + "," + l + ")");
//              }
              if (contents == otherContents) {
                // we have a pair
                //System.out.println("Pair found: " + contents + " at (" + i + "," + j + ") and (" + k + "," + l + ")");
                pairs.add(new CoordinatePair(contents, i, j, k, l));
              }
              l++;
            }
          }
        }
      }
    }

    // print out the pairs
//    for (CoordinatePair pair : pairs) {
//      System.out.println(pair);
//    }

    // given how we scanned, we shouldn't actually end up with any duplicate pairs with the points reversed
    // now we need to calculate the antinodes generated by each pair, defined as:
    // an antinode occurs at any point that is perfectly in line with two antennas of the same frequency - but only when one of the antennas is twice as far away as the other. This means that for any pair of antennas with the same frequency, there are two antinodes, one on either side of them.
    // we need to iterate the list of pairs, then determine the two antinodes each pair produces, and update the relevant field in the GridBlock, in the main grid
    for (CoordinatePair pair : pairs) {
      //System.out.println("Calculating antinodes for pair: " + pair);
      final int x1 = pair.x1();
      final int y1 = pair.y1();
      final int x2 = pair.x2();
      final int y2 = pair.y2();
      final int xDiff = Math.abs(x1 - x2);
      final int yDiff = Math.abs(y1 - y2);
      //System.out.println("xDiff: " + xDiff + ", yDiff: " + yDiff);
      if (xDiff == 0) {
        // the pair is vertical
        //System.out.println("Pair is vertical");

        // the pair must generate two antinodes, one on either 'side' of the pair, and be twice as far
        // away from one of the nodes as it is from the other
        int antiNodeAx = x1;
        int antiNodeAy = yDiff - yDiff*2;
        int antiNodeBx = x1;
        int antiNodeBy = yDiff + yDiff*2;

        System.out.println("Antinodes at: (" + antiNodeAx + "," + antiNodeAy + ") and (" + antiNodeBx + "," + antiNodeBy + ")");
        // if the antinode is outside of the grid, then don't update it
        if (isAntiNodeWithinGrid(antiNodeAy, grid, antiNodeBy)) {
          grid.getGrid()[antiNodeAx][antiNodeAy].setHasAntinode(true);
          grid.getGrid()[antiNodeBx][antiNodeBy].setHasAntinode(true);
        }
      } else if (yDiff == 0) {
        // the pair is horizontal
        //System.out.println("Pair is horizontal");

        int antiNodeAx = xDiff - xDiff*2;
        int antiNodeAy = y1;
        int antiNodeBx = xDiff + xDiff*2;
        int antiNodeBy = y1;

        System.out.println("Antinodes at: (" + antiNodeAx + "," + antiNodeAy + ") and (" + antiNodeBx + "," + antiNodeBy + ")");
        if (isAntiNodeWithinGrid(antiNodeAy, grid, antiNodeBy)) {
          grid.getGrid()[antiNodeAx][antiNodeAy].setHasAntinode(true);
          grid.getGrid()[antiNodeBx][antiNodeBy].setHasAntinode(true);
        }
      } else {
        // the pair is diagonal
        //System.out.println("Pair is diagonal");

        // as above, but this time in the diagonal direction
        // we need to determine which direction the pair is in, to know if we should + or - from the starting x and y positions
        // determine if the other node in the pair is diagonally down left or down right from the first node
        int antiNodeAx = -1;
        int antiNodeAy = -1;
        int antiNodeBx = -1;
        int antiNodeBy = -1;

        if (x1 < x2 && y1 < y2) {
          // down right
          //System.out.println("Pair is down right!");
          antiNodeAx = x1 - xDiff;
          antiNodeAy = y1 - yDiff;
          antiNodeBx = x2 + xDiff;
          antiNodeBy = y2 + yDiff;
          //System.out.println("Antinodes at: (" + antiNodeAx + "," + antiNodeAy + ") and (" + antiNodeBx + "," + antiNodeBy + ")");
        } else if (x1 < x2 && y1 > y2) {
          // up right
          //System.out.println("Pair is up right");
          antiNodeAx = x1 - xDiff;
          antiNodeAy = y1 + yDiff;
          antiNodeBx = x2 + xDiff;
          antiNodeBy = y2 - yDiff;
          //System.out.println("Antinodes at: (" + antiNodeAx + "," + antiNodeAy + ") and (" + antiNodeBx + "," + antiNodeBy + ")");
        } else if (x1 > x2 && y1 < y2) {
          // down left
          //System.out.println("Pair is down left");
          antiNodeAx = x1 - xDiff;
          antiNodeAy = y1 + yDiff;
          antiNodeBx = x2 + xDiff;
          antiNodeBy = y2 - yDiff;
          //System.out.println("Antinodes at: (" + antiNodeAx + "," + antiNodeAy + ") and (" + antiNodeBx + "," + antiNodeBy + ")");
        } else {
          // shouldn't need this case, as we process down downwards
          // up left
          System.out.println("Pair is up left");
        }

        // if antinode A is within the grid, then update the grid
        if (isAntiNodeWithinGrid(antiNodeAy, grid, antiNodeAx)) {
          //System.out.println("Visible antinode at: (" + antiNodeAx + "," + antiNodeAy + ")");
          grid.getGrid()[antiNodeAx][antiNodeAy].setHasAntinode(true);
        }

        // if antinode B is within the grid, then update the grid
        if (isAntiNodeWithinGrid(antiNodeBy, grid, antiNodeBx)) {
          //System.out.println("Visible antinodes at: (" + antiNodeBx + "," + antiNodeBy + ")");
          grid.getGrid()[antiNodeBx][antiNodeBy].setHasAntinode(true);
        }

      }
    }

    System.out.println(grid);
    System.out.println("There are " + grid.getAntiNodeCount() + " antinodes in the grid");

  }

  private static boolean isAntiNodeWithinGrid(int nodeY, Grid grid, int nodeX) {
    //System.out.println("Checking if antinode is within grid: " + nodeX + "," + nodeY);
    //System.out.println("Grid dimensions: " + grid.getGrid().length + "," + grid.getGrid()[0].length);
    return nodeY >= 0 && nodeY < grid.getGrid()[0].length && nodeX >= 0 && nodeX < grid.getGrid().length;
  }

}