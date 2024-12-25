package day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Part2 {

  /*
   * https://adventofcode.com/2024/day/5
   */
  public static void main(String[] args) throws IOException {

    // Read in the data
    final String inputFilename = args[0];
    final List<String> input = Files.readAllLines(Paths.get(inputFilename));

    final Map<Integer, Set<Integer>> beforeOrderMap = new HashMap<>();
    final Map<Integer, Set<Integer>> afterOrderMap = new HashMap<>();
    final List<List<Integer>> manuals = new ArrayList<>();

    boolean switchMode = false;
    for (String line : input) {

      if (line.isBlank()) {
        switchMode = true;
        continue;
      }

      if (!switchMode) {
        String[] orderingParts = line.split("\\|");
        int before = Integer.parseInt(orderingParts[0]);
        int after = Integer.parseInt(orderingParts[1]);
        // add to the before map, e.g. in the end of left hand side maps to a set of all the pages it must come before

        if (beforeOrderMap.containsKey(before)) {
          final Set<Integer> beforeMap = beforeOrderMap.get(before);
          beforeMap.add(after);
        } else {
          final Set<Integer> beforeMap = new HashSet<>();
          beforeMap.add(after);
          beforeOrderMap.put(before, beforeMap);
        }

        // the same but for the after map, using the after value as the key
        if (afterOrderMap.containsKey(after)) {
          final Set<Integer> afterMap = afterOrderMap.get(after);
          afterMap.add(before);
        } else {
          final Set<Integer> afterMap = new HashSet<>();
          afterMap.add(before);
          afterOrderMap.put(after, afterMap);
        }
      } else {
        //System.out.println("Manual: " + line);
        final String[] manualParts = line.split(",");
        final List<Integer> manual = new ArrayList<>();
        for (String manualPart : manualParts) {
          manual.add(Integer.parseInt(manualPart));
        }
        manuals.add(manual);
      }
    }

    final List<List<Integer>> manualsInIncorrectOrder = new ArrayList<>();
    // now we have the data, what to do with it?
    for (List<Integer> manual : manuals) {
      // we need to check each page in the manual and ensure that it is in the correct order
      // we do this by checking the before and after maps
      // the before map tells us what pages the current page must come before
      // the after map tells us what pages the current page must come after
      // if the manual has all pages in the correct order, print a log and increment a counter
      boolean manualInOrder = true;
      for (int i = 0; i < manual.size(); i++) {
        final int currentPage = manual.get(i);

        if (!beforeOrderMap.containsKey(currentPage) && !afterOrderMap.containsKey(currentPage)) {
          // this page has no ordering constraints, skip it
          continue;
        }

        // check the before map
        if (beforeOrderMap.containsKey(currentPage)) {
          final Set<Integer> beforePages = beforeOrderMap.get(currentPage);
          for (int beforePage : beforePages) {
            // check that the before page is in the manual
            if (manual.contains(beforePage)) {
              // check that the before page is before the current page
              if (manual.indexOf(beforePage) < i) {
                //System.out.println("Manual is out of order: " + manual + ". " + beforePage + " is before " + currentPage);
                manualInOrder = false;
                break;
              }
            }
          }
        }
        // check the after map
        if (afterOrderMap.containsKey(currentPage)) {
          final Set<Integer> afterPages = afterOrderMap.get(currentPage);
          for (int afterPage : afterPages) {
            // check that the after page is in the manual
            if (manual.contains(afterPage)) {
              // check that the after page is after the current page
              if (manual.indexOf(afterPage) > i) {
                //System.out.println("Manual is out of order: " + manual + ". " + afterPage + " is after " + currentPage);
                manualInOrder = false;
                break;
              }
            }
          }
        }
      }
      if (!manualInOrder) {
        manualsInIncorrectOrder.add(manual);
      }
    }

    // apply the page ordering rules in the two maps to correct the ordering of each manual
    while (notOrderedCorrectly(manualsInIncorrectOrder, beforeOrderMap, afterOrderMap)) {
      for (List<Integer> manualInIncorrectOrder : manualsInIncorrectOrder) {
        outerLoop:
        for (int i = 0; i < manualInIncorrectOrder.size(); i++) {
          final int currentPage = manualInIncorrectOrder.get(i);

          if (!beforeOrderMap.containsKey(currentPage) && !afterOrderMap.containsKey(currentPage)) {
            System.out.println("No ordering constraints for " + currentPage);
            continue;
          }

          // check the before map
          if (beforeOrderMap.containsKey(currentPage)) {
            System.out.println("Checking before map for " + currentPage);
            final Set<Integer> beforePages = beforeOrderMap.get(currentPage);
            for (int beforePage : beforePages) {
              // check that the before page is in the manual
              if (manualInIncorrectOrder.contains(beforePage)) {
                // check that the before page is before the current page
                if (manualInIncorrectOrder.indexOf(beforePage) > i) {
                  // swap the two pages
                  final int beforePageIndex = manualInIncorrectOrder.indexOf(beforePage);
                  //System.out.println("Setting " + i + " to " + beforePage);
                  manualInIncorrectOrder.set(i, beforePage);
                  //System.out.println("Setting " + beforePageIndex + " to " + currentPage);
                  manualInIncorrectOrder.set(beforePageIndex, currentPage);
                  System.out.println("Swapped " + currentPage + " with " + beforePage);
                  //System.out.println("Manual: " + manualInIncorrectOrder);
                  break outerLoop;
                }
              }
            }
          }
          // check the after map
          if (afterOrderMap.containsKey(currentPage)) {
            System.out.println( "Checking after map for " + currentPage);
            final Set<Integer> afterPages = afterOrderMap.get(currentPage);
            for (int afterPage : afterPages) {
              // check that the after page is in the manual
              if (manualInIncorrectOrder.contains(afterPage)) {
                // check that the after page is after the current page
                if (manualInIncorrectOrder.indexOf(afterPage) < i) {
                  // swap the two pages
                  final int afterPageIndex = manualInIncorrectOrder.indexOf(afterPage);
                  manualInIncorrectOrder.set(i, afterPage);
                  manualInIncorrectOrder.set(afterPageIndex, currentPage);
                  System.out.println("Swapped " + currentPage + " with " + afterPage);
                  break outerLoop;
                }
              }
            }
          }
        }
      }
    }

    // calculate the total of the middle elements of the manuals
    int total = 0;
    for (List<Integer> manualInOrder : manualsInIncorrectOrder) {
      final Integer middleElement = findMiddleElement(manualInOrder);
      total += middleElement;
    }

    System.out.println("Manuals in order: " + manualsInIncorrectOrder);
    System.out.println("Total: " + total);
  }

  public static Integer findMiddleElement(List<Integer> list) {
    if (list == null || list.isEmpty()) {
      return null; // or throw an exception
    }
    int middleIndex = list.size() / 2;
    return list.get(middleIndex);
  }

  private static boolean notOrderedCorrectly(List<List<Integer>> manuals, Map<Integer, Set<Integer>> beforeOrderMap,
                                             Map<Integer, Set<Integer>> afterOrderMap) {
    for (List<Integer> manual : manuals) {
      System.out.println("Checking manual: " + manual);
      for (int i = 0; i < manual.size(); i++) {
        final int currentPage = manual.get(i);

        if (!beforeOrderMap.containsKey(currentPage) && !afterOrderMap.containsKey(currentPage)) {
          System.out.println("No ordering constraints for " + currentPage);
          continue;
        }

        // check the before map
        if (beforeOrderMap.containsKey(currentPage)) {
          final Set<Integer> beforePages = beforeOrderMap.get(currentPage);
          for (int beforePage : beforePages) {
            if (manual.contains(beforePage)) {
              if (manual.indexOf(beforePage) > i) {
                return true;
              }
            }
          }
        }
        if (afterOrderMap.containsKey(currentPage)) {
          final Set<Integer> afterPages = afterOrderMap.get(currentPage);
          for (int afterPage : afterPages) {
            if (manual.contains(afterPage)) {
              if (manual.indexOf(afterPage) < i) {
                return true;
              }
            }
          }
        }
      }
    }
    return false;
  }

}

// 5244 - too low