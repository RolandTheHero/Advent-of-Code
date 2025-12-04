package AdventOfCode2025.Day3;

import java.util.Scanner;

import AdventOfCode2025.ReadFile;

public class Day3 {
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day3/input.txt");
        long joltageSumPart1 = 0;
        long joltageSumPart2 = 0;
        while (s.hasNextLine()) {
            String bank = s.nextLine();
            joltageSumPart1 += biggestJolt(2, bank);
            joltageSumPart2 += biggestJolt(12, bank);
        }
        System.out.println("Part 1: " + joltageSumPart1);
        System.out.println("Part 2: " + joltageSumPart2);
    }
    static public long biggestJolt(int length, String bank) {
        int startIndex = 0;
        int endIndex = bank.length() - length;
        String maxJoltString = "";
        while (maxJoltString.length() < length) {
            int highest = 0;
            for (int i = startIndex; i <= endIndex; i++) {
                int jolt = Integer.valueOf(bank.charAt(i) + "");
                if (jolt > highest) {
                    highest = jolt;
                    startIndex = i + 1;
                }
                if (jolt == 9) break;
            }
            maxJoltString += highest;
            endIndex++;
        }
        return Long.valueOf(maxJoltString);
    }
}