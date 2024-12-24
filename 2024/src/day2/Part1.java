package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

  /*
   * https://adventofcode.com/2024/day/2
   */
  public static void main(String[] args) throws IOException {

    // Read in the data
    final String inputFilename = args[0];
    final List<String> lines = Files.readAllLines(Paths.get(inputFilename));

    final List<List<Integer>> reports = new ArrayList<>(lines.size());

    for (String line : lines) {
      //System.out.println(line);
      final List<Integer> levels = new ArrayList<>();
      for (String level : line.split("\\s+")) {
        levels.add(Integer.parseInt(level));
      }
      reports.add(levels);
    }

    // Check the reports
    int safeReports = 0;
    int unsafeReports = 0;

    for (List<Integer> report : reports) {
      int last = -1;
      Boolean increasing = null;
      boolean safe = true;
      for (Integer level : report) {
        if (last == -1) {
          last = level;
          continue;
        }
        if (level > last) {
          if (increasing == Boolean.FALSE) {
            safe = false;
            break;
          }
          if (!isLevelDifferenceSafe(last, level)) {
            safe = false;
            break;
          }
          increasing = true;
        } else if (level < last) {
          if (increasing == Boolean.TRUE) {
            safe = false;
            break;
          }
          if (!isLevelDifferenceSafe(last, level)) {
            safe = false;
            break;
          }
          increasing = false;
        } else {
          safe = false;
          break;
        }
        last = level;
      }

      if (safe) {
        safeReports++;
      } else {
        unsafeReports++;
      }
    }

    System.out.println("Safe reports: " + safeReports);
    System.out.println("Unsafe reports: " + unsafeReports);
  }

  private static boolean isLevelDifferenceSafe(int level1, int level2) {
    final int difference = Math.abs(level1 - level2);
    return difference >= 1 && difference <= 3;
  }
}