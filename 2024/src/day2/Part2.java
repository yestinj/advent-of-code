package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

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

      // if the level is unsafe, we actually need to retry the level with each item removed, and see if any of those
      // variations is safe...
      final List<List<Integer>> levelVariations = new ArrayList<>();
      // add the initial version
      levelVariations.add(report);

      // iterate the list, remove one item at a time, and add the variation to the list
      for (int i = 0; i < report.size(); i++) {
        final List<Integer> variation = new ArrayList<>(report); // create a copy
        variation.remove(i); // remove the item
        levelVariations.add(variation); // add the variation
      }

      boolean actuallySafe = false;
      for (List<Integer> variation : levelVariations) {
        int last = -1;
        Boolean increasing = null;
        boolean safe = true;
        for (Integer level : variation) {
          // The first level is always safe, so we skip it
          if (last == -1) {
            last = level;
            continue;
          }
          // Increasing
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
          } //decreasing
          else if (level < last) {
            if (increasing == Boolean.TRUE) {
              safe = false;
              break;
            }
            if (!isLevelDifferenceSafe(last, level)) {
              safe = false;
              break;
            }
            increasing = false;
          } // Equal
          else {
            safe = false;
            break;
          }
          // Nothing disqualified this level vs last, update last and move on
          last = level;
        }
        if (safe) {
          actuallySafe = true;
          break; // this variation is safe, no need to check the others
        }
      }

      if (actuallySafe) {
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