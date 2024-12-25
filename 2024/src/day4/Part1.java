package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Part1 {

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
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        final char c = grid[i][j];
        if (c != 'X') {
          continue;
        }
        // search in all 8 directions to see if we can find the word XMAS
        // 1. right
        if (j + 3 < grid[i].length) {
          if (grid[i][j + 1] == 'M' && grid[i][j + 2] == 'A' && grid[i][j + 3] == 'S') {
            matches++;
          }
        }
        // 2. left
        if (j - 3 >= 0) {
          if (grid[i][j - 1] == 'M' && grid[i][j - 2] == 'A' && grid[i][j - 3] == 'S') {
            matches++;
          }
        }
        // 3. down
        if (i + 3 < grid.length) {
          if (grid[i + 1][j] == 'M' && grid[i + 2][j] == 'A' && grid[i + 3][j] == 'S') {
            matches++;
          }
        }
        // 4. up
        if (i - 3 >= 0) {
          if (grid[i - 1][j] == 'M' && grid[i - 2][j] == 'A' && grid[i - 3][j] == 'S') {
            matches++;
          }
        }
        // 5. down right
        if (i + 3 < grid.length && j + 3 < grid[i].length) {
          if (grid[i + 1][j + 1] == 'M' && grid[i + 2][j + 2] == 'A' && grid[i + 3][j + 3] == 'S') {
            matches++;
          }
        }
        // 6. down left
        if (i + 3 < grid.length && j - 3 >= 0) {
          if (grid[i + 1][j - 1] == 'M' && grid[i + 2][j - 2] == 'A' && grid[i + 3][j - 3] == 'S') {
            matches++;
          }
        }
        // 7. up right
        if (i - 3 >= 0 && j + 3 < grid[i].length) {
          if (grid[i - 1][j + 1] == 'M' && grid[i - 2][j + 2] == 'A' && grid[i - 3][j + 3] == 'S') {
            matches++;
          }
        }
        // 8. up left
        if (i - 3 >= 0 && j - 3 >= 0) {
          if (grid[i - 1][j - 1] == 'M' && grid[i - 2][j - 2] == 'A' && grid[i - 3][j - 3] == 'S') {
            matches++;
          }
        }

      }
    }
    System.out.println("Matches: " + matches);
  }

}