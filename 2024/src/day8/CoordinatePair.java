package day8;

public record CoordinatePair(char symbol, int x1, int y1, int x2, int y2) {

  @Override
  public String toString() {
    return "Pair: " + symbol + " (" + x1 + "," + y1 + ") and (" + x2 + "," + y2 + ")";
  }

  @Override
  public boolean equals(Object obj) {
    // return true if the fields are identical, or if they are symmetric, e.g. x1=1, y1=2, x2=3, y2=4 is the same as x1=3, y1=4, x2=1, y2=2
    if (this == obj) {
      return true;
    }
    if (obj instanceof CoordinatePair other) {
      // or identical
      if (symbol != other.symbol) {
        return false;
      }
      if (x1 == other.x1 && y1 == other.y1 && x2 == other.x2 && y2 == other.y2) {
        return true;
      }
      // or symmetric
       else return x1 == other.x2 && y1 == other.y2 && x2 == other.x1 && y2 == other.y1;
    }
    return false;
  }
}
