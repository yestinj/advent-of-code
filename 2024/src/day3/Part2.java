package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part2 {

  /*
   * https://adventofcode.com/2024/day/3
   */
  public static void main(String[] args) throws IOException {

    // Read in the data
    final String inputFilename = args[0];
    // Read in the data
    final String input = Files.readString(Paths.get(inputFilename));
    //System.out.println(input);

    boolean doing = true;
    long total = 0;

    int nextMarkerIndex;
    int lastMarkerIndexEnd = 0;
    while (true) {

      boolean shouldBreak = false;
      if (doing) {
        // find the next position of don't() after the last match, starts from the beginning of the string
        nextMarkerIndex = input.indexOf("don't()", lastMarkerIndexEnd);
        if (nextMarkerIndex == -1) {
          // we've reached the end of the string
          nextMarkerIndex = input.length();
          shouldBreak = true;
        }

        // doing mode, we need to process the substring between the last match and the current match
        final String doString = input.substring(lastMarkerIndexEnd, nextMarkerIndex);
        System.out.println("Processing doString: " + doString);
        total += findMulProductSumInSubstring(doString);

        if (!shouldBreak) {
          // switch modes and update the index to the start of the next substring to process
          lastMarkerIndexEnd = nextMarkerIndex + 7;
          doing = false;
        } else {
          break;
        }
      } else {
        nextMarkerIndex = input.indexOf("do()", lastMarkerIndexEnd);

        // we've reached the end of the string in don't mode
        if (nextMarkerIndex == -1) {
          break;
        }
        System.out.println("Not processing don't string: " + input.substring(lastMarkerIndexEnd, nextMarkerIndex));

        lastMarkerIndexEnd = nextMarkerIndex + 4;
        doing = true;
      }
    }

    System.out.println("Total: " + total);

  }

  private static long findMulProductSumInSubstring(String doString) {
    final String mulRegex = "mul\\(\\d{1,3},\\d{1,3}\\)";
    final Pattern mulPattern = Pattern.compile(mulRegex);
    final Pattern numberPattern = Pattern.compile("\\d{1,3}");

    final Matcher mulMatcher = mulPattern.matcher(doString);

    long total = 0;
    while (mulMatcher.find()) {
      final String mulTerm = mulMatcher.group();

      final Matcher numberMatcher = numberPattern.matcher(mulTerm);

      // Assuming we'll only ever have two numbers
      numberMatcher.find();
      final int firstNumber = Integer.parseInt(numberMatcher.group());
      numberMatcher.find();
      final int secondNumber = Integer.parseInt(numberMatcher.group());
      total += (long) firstNumber * secondNumber;
    }
    return total;
  }
}

// 78683433 - answer