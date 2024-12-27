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

  public CalibrationEquation(long expectedTotal, int[] coefficients, boolean useConcat) {
    this.expectedTotal = expectedTotal;
    this.coefficients = coefficients;
    this.currentOperators = new char[coefficients.length - 1];
    if (useConcat) {
      this.operatorCombinations = generateOperatorCombinationsWithConcat(currentOperators.length);
    } else {
      this.operatorCombinations = generateOperatorCombinations(currentOperators.length);
    }
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

  private char[][] generateOperatorCombinationsWithConcat(int n) {
    final char[][] combinations = new char[(int) Math.pow(3, n)][n];
    for (int i = 0; i < combinations.length; i++) {
      for (int j = 0; j < n; j++) {
        int operatorIndex = (i / (int) Math.pow(3, j)) % 3;
        combinations[i][j] = operatorIndex == 0 ? '+' : operatorIndex == 1 ? '*' : '|';
      }
    }
    return combinations;
  }

  public boolean evaluateCombinationsWithConcat() {
    for (char[] operatorCombination : operatorCombinations) {
      System.arraycopy(operatorCombination, 0, currentOperators, 0, currentOperators.length);
      long total = coefficients[0];
      for (int i = 0; i < currentOperators.length; i++) {
        if (currentOperators[i] == '+') {
          total += coefficients[i + 1];
        } else if (currentOperators[i] == '*') {
          total *= coefficients[i + 1];
        } else {
          total = Long.parseLong(Long.toString(total).concat(Integer.toString(coefficients[i + 1])));
        }
      }
      if (total == expectedTotal) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    for (int coefficient : coefficients) {
      sb.append(coefficient).append(" ");
    }
    sb.append("Should equal ").append(expectedTotal);
    return sb.toString();
  }
}
