package day8;

import java.util.List;

public class Grid {
  private final GridBlock[][] grid;

  @Override
  public String toString() {
    // put all elements of the grid into a string
    final StringBuilder sb = new StringBuilder();
    for (GridBlock[] row : grid) {
      for (GridBlock block : row) {
        if (block.hasAntinode()) {
          sb.append("#");
        } else {
          sb.append(block.getFrequency());
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  public Grid(List<String> input) {
    // populate the grid
    this.grid = new GridBlock[input.size()][input.getFirst().length()];
    for (int i = 0; i < input.size(); i++) {
      final String row = input.get(i);
      for (int j = 0; j < row.length(); j++) {
        grid[i][j] = new GridBlock(row.charAt(j));
      }
    }
  }

  public GridBlock[][] getGrid() {
    return grid;
  }

  public int getAntiNodeCount() {
    int count = 0;
    for (GridBlock[] row : grid) {
      for (GridBlock block : row) {
        if (block.hasAntinode()) {
          count++;
        }
      }
    }
    return count;
  }
}
