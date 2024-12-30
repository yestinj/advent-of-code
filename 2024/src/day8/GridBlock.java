package day8;

public class GridBlock {
  private final char frequency;
  private boolean hasAntinode;

  public GridBlock(char frequency) {
    this.frequency = frequency;
    this.hasAntinode = false;
  }

  public char getFrequency() {
    return frequency;
  }

  public boolean hasAntinode() {
    return hasAntinode;
  }

  public void setHasAntinode(boolean hasAntinode) {
    this.hasAntinode = hasAntinode;
  }
}
