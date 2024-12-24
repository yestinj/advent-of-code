package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.PriorityQueue;

public class Main {

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

    int sum = 0;
    while (!leftList.isEmpty() && !rightList.isEmpty()) {
      final Integer left = leftList.poll();
      final Integer right = rightList.poll();
      sum += Math.abs(left - right);
    }

    System.out.println("Sum: " + sum);
  }
}