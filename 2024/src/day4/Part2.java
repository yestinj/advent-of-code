package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Part2 {

  /*
   * https://adventofcode.com/2024/day/4
   */
  public static void main(String[] args) throws IOException {

    // Read in the data
    final String inputFilename = args[0];
    final List<String> input = Files.readAllLines(Paths.get(inputFilename));

    //System.out.println(input);

    char[][] grid = new char[input.size()][input.size()];
    for (int i = 0; i < input.size(); i++) {
      grid[i] = input.get(i).toCharArray();
    }

//    for (int i = 0; i < grid.length; i++) {
//      for (int j = 0; j < grid[i].length; j++) {
//        System.out.print(grid[i][j] + " ");
//      }
//      System.out.println();
//    }

    int matches = 0;
    for (int i = 1; i < grid.length - 1; i++) {
      for (int j = 1; j < grid[i].length - 1; j++) {
        final char c = grid[i][j];
        if (c != 'A') {
          continue;
        }

        // Check all four possible patterns around 'A'
        if (grid[i - 1][j - 1] == 'M' && grid[i + 1][j + 1] == 'S' && grid[i - 1][j + 1] == 'M' && grid[i + 1][j - 1] == 'S') {
          matches++;
        }
        if (grid[i - 1][j - 1] == 'S' && grid[i + 1][j + 1] == 'M' && grid[i - 1][j + 1] == 'S' && grid[i + 1][j - 1] == 'M') {
          matches++;
        }
        if (grid[i - 1][j - 1] == 'M' && grid[i + 1][j + 1] == 'S' && grid[i - 1][j + 1] == 'S' && grid[i + 1][j - 1] == 'M') {
          matches++;
        }
        if (grid[i - 1][j - 1] == 'S' && grid[i + 1][j + 1] == 'M' && grid[i - 1][j + 1] == 'M' && grid[i + 1][j - 1] == 'S') {
          matches++;
        }
      }
    }
    System.out.println("Matches: " + matches);
  }

}