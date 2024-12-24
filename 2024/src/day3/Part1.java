package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {

  /*
   * https://adventofcode.com/2024/day/3
   */
  public static void main(String[] args) throws IOException {

    // Read in the data
    final String inputFilename = args[0];
    final String input = Files.readString(Paths.get(inputFilename));

    System.out.println(input);

    // Build a regex and find the matches
    final String regex = "mul\\(\\d{1,3},\\d{1,3}\\)";

    final Pattern pattern = Pattern.compile(regex);
    final Matcher matcher = pattern.matcher(input);

    // Secondary regex to extract numbers
    final Pattern numberPattern = Pattern.compile("\\d{1,3}");

    long total = 0;
    while (matcher.find()) {
      final String match = matcher.group();
      System.out.println("Found: " + matcher.group());

      final Matcher numberMatcher = numberPattern.matcher(match);

      // Assuming we'll only ever have two numbers
      numberMatcher.find();
      final int firstNumber = Integer.parseInt(numberMatcher.group());
      numberMatcher.find();
      final int secondNumber = Integer.parseInt(numberMatcher.group());
      //System.out.println("First number: " + firstNumber);
      //System.out.println("Second number: " + secondNumber);
      total += firstNumber * secondNumber;
    }

    System.out.println("Total: " + total);
  }

}