package day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

  /*
   * https://adventofcode.com/2024/day/9
   */
  public static void main(String[] args) throws IOException {

    // Read in the data
    final String inputFilename = args[0];
    final String input = Files.readString(Paths.get(inputFilename));

    // parse the input into an int array, single digits only
    final int[] diskMap = new int[input.length()];
    for (int i = 0; i < input.length(); i++) {
      diskMap[i] = Integer.parseInt(input.charAt(i) + "");
    }

    // print out the disk map
    for (int k : diskMap) {
      System.out.print(k);
    }
    System.out.println("\nDisk map length is " + diskMap.length);

    // now expand the disk map
    final List<String> expandedDiskMapList = new ArrayList<>();

    long fileIdNo = 0;
    for (int i = 0; i < diskMap.length; i++) {
      // even numbers represent file sizes in blocks
      if (i % 2 == 0) {
        for (int j = 0; j < diskMap[i]; j++) {
          expandedDiskMapList.add(String.valueOf(fileIdNo));
        }
        fileIdNo++;
      } else {
        // if its odd, this means the number represents the number of spaces to expand to in the expanded map
        for (int j = 0; j < diskMap[i]; j++) {
          expandedDiskMapList.add(".");
        }
      }
    }

    System.out.println("\nExpanded disk map list : " + String.join("", expandedDiskMapList));

    // iterate the map list backwards, so swap the position of the file with the next empty space (represented by a .)
    for (int i = expandedDiskMapList.size() - 1; i >= 0; i--) {
      final String blockToMove = expandedDiskMapList.get(i);
      if (!blockToMove.equals(".")) {
        for (int j = 0; j < i; j++) {
          final String blockToSwap = expandedDiskMapList.get(j);
          if (blockToSwap.equals(".")) {
            // swap the two
            expandedDiskMapList.set(j, blockToMove);
            expandedDiskMapList.set(i, ".");
            break;
          }
        }
      }
    }

    // print out the result, which is the compacted disk map
    System.out.println("Compacted disk map: " + String.join("", expandedDiskMapList));
    System.out.println("Compacted disk map length is " + expandedDiskMapList.size());

    // finally, calculate the checksum, which is the total obtained adding together all the file ids, multiplied by their position in the map
    long checksum = 0;
    for (int i = 0; i < expandedDiskMapList.size(); i++) {
      final String block = expandedDiskMapList.get(i);
      //System.out.println("Block: " + block);
      if (!block.equals(".")) {
        checksum += Long.parseLong(block) * i;
        //System.out.println("Check in progress: " + checksum);
      }
    }

    System.out.println("Checksum: " + checksum);
  }
}

// wrong, too low 90008815667
//correct, 6334655979668