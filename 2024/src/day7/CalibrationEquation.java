package day7;

public class CalibrationEquation {
  private final long expectedTotal;
  private final int[] coefficients;
  private final char[] currentOperators;
  private final char[][] operatorCombinations;

  public CalibrationEquation(long expectedTotal, int[] coefficients) {
    this.expectedTotal = expectedTotal;
    this.coefficients = coefficients;
    this.currentOperators = new char[coefficients.length - 1];
    this.operatorCombinations = generateOperatorCombinations(currentOperators.length);
  }

  public boolean evaluateCombinations() {
    for (char[] operatorCombination : operatorCombinations) {
      System.arraycopy(operatorCombination, 0, currentOperators, 0, currentOperators.length);
      long total = coefficients[0];
      for (int i = 0; i < currentOperators.length; i++) {
        if (currentOperators[i] == '+') {
          total += coefficients[i + 1];
        } else {
          total *= coefficients[i + 1];
        }
      }
      if (total == expectedTotal) {
        return true;
      }
    }
    return false;
  }

  public long getExpectedTotal() {
    return expectedTotal;
  }

  // generate all the possible combinations of operators (only + and *) for the given number of coefficients
  private char[][] generateOperatorCombinations(int n) {
    char[][] combinations = new char[(int) Math.pow(2, n)][n];
    for (int i = 0; i < combinations.length; i++) {
      for (int j = 0; j < n; j++) {
        combinations[i][j] = (i & (1 << j)) > 0 ? '+' : '*';
      }
    }
    return combinations;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    for (int coefficient : coefficients) {
      sb.append(coefficient).append(" ");
    }
    sb.append("should equal ").append(expectedTotal);
    return sb.toString();
  }
}
