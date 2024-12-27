package day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

  /*
   * https://adventofcode.com/2024/day/7
   */
  public static void main(String[] args) throws IOException {

    // Read in the data
    final String inputFilename = args[0];
    final List<String> input = Files.readAllLines(Paths.get(inputFilename));

    final List<CalibrationEquation> equations = new ArrayList<>(input.size());

    // populate the list of equations using the input in sample.txt, where the format is:
    // <expectedTotal>: <coefficient1> <coefficient2> ... <coefficientN>
    for (String line : input) {
      final String[] parts = line.split(": ");
      final long expectedTotal = Long.parseLong(parts[0]);
      final String[] coefficients = parts[1].split(" ");
      final List<Integer> coefficientsList = new ArrayList<>(coefficients.length);
      for (String coefficient : coefficients) {
        coefficientsList.add(Integer.parseInt(coefficient));
      }
      equations.add(new CalibrationEquation(expectedTotal, coefficientsList.stream().mapToInt(Integer::intValue).toArray(), true));
    }

    //print out the equations so we can see that they were populated correctly
//    for (CalibrationEquation equation : equations) {
//      System.out.println(equation);
//    }

    // now check all combinations of the equations and find the number that have a combination which matches their expected total
    long total = 0;
    for (final CalibrationEquation calibrationEquation : equations) {
      final boolean matchingCombinationFound = calibrationEquation.evaluateCombinationsWithConcat();
      if (matchingCombinationFound) {
        total += calibrationEquation.getExpectedTotal();
      }
    }

    System.out.println("Total: " + total);

  }

}