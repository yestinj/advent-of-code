package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Part2 {

  /*
   * https://adventofcode.com/2024/day/1
   */
  public static void main(String[] args) throws IOException {

    final String inputFilename = args[0];
    final List<String> lines = Files.readAllLines(Paths.get(inputFilename));

    final PriorityQueue<Integer> leftList = new PriorityQueue<>(lines.size());
    final PriorityQueue<Integer> rightList = new PriorityQueue<>(lines.size());

    for (String line : lines) {
      //System.out.println(line);
      final String[] stringParts = line.split("\\s+");
      leftList.add(Integer.parseInt(stringParts[0]));
      rightList.add(Integer.parseInt(stringParts[1]));
    }

    final Map<Integer, Integer> rightOccurrences = new HashMap<>();

    while (!rightList.isEmpty()) {
      final Integer right = rightList.poll();
      rightOccurrences.put(right, rightOccurrences.getOrDefault(right, 0) + 1);
    }

    int similarity = 0;
    while (!leftList.isEmpty()) {
      final int left = leftList.poll();
      final int occurrences = rightOccurrences.getOrDefault(left, 0);
      similarity += left * occurrences;
    }

    System.out.println("Similarity: " + similarity);
  }
}