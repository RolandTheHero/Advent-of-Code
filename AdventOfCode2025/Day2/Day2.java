package AdventOfCode2025.Day2;

import java.util.Scanner;

import AdventOfCode2025.ReadFile;

public class Day2 {
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day2/input.txt");
        String[] ranges = s.nextLine().split(",");
        long part1 = 0;
        long part2 = 0;
        for (String str : ranges) {
            String[] parts = str.split("-");
            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);
            for (long i = start; i <= end; i++) {
                String currentNumString = i + "";

                // Part 2
                for (int d = 1; d <= currentNumString.length() / 2; d++) {
                    if (currentNumString.length() % d != 0) continue;
                    boolean valid = false;
                    for (int range = 0; range < currentNumString.length(); range += d) {
                        String segment = currentNumString.substring(range, range + d);
                        if (!segment.equals(currentNumString.substring(0, d))) {
                            valid = true;
                            break;
                        }
                    }
                    if (!valid) {
                        part2 += i;
                        break;
                    }
                }

                // Part 1
                if (currentNumString.length() % 2 != 0) { // Odd length
                    continue;
                }
                String leftHalf = currentNumString.substring(0, currentNumString.length() / 2);
                String rightHalf = currentNumString.substring(currentNumString.length() / 2);
                if (leftHalf.equals(rightHalf)) part1 += i;
            }
        }
        System.out.println("Part 1: " + part1);
        System.out.println("Part 2: " + part2);
    }
}
