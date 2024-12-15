package AdventOfCode2024.Day11;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import AdventOfCode2024.ReadFile;

class Day11 {
    static List<Long> originalStones = new ArrayList<>();
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day11/input.txt");
        while (s.hasNext()) originalStones.add(s.nextLong());

        //List<Long> part1Stones = new ArrayList<>(originalStones);
        //for (int i = 0; i < 25; i++) part1Stones = blink(part1Stones, new ArrayList<>());
        System.out.println(blink(originalStones, 25));
        //System.out.println(blink(List.of(0L), 25));
        //System.out.println(blink(originalStones, 75));
    }
    static List<Long> blink(List<Long> oldStones, List<Long> newStones) {
        for (long n : oldStones) {
            String sn = String.valueOf(n);
            if (n == 0L) newStones.add(1L);
            else if (sn.length() % 2 == 0) {
                newStones.add(Long.parseLong(sn.substring(0, sn.length()/2)));
                newStones.add(Long.parseLong(sn.substring(sn.length()/2, sn.length())));
            } else newStones.add(n * 2024);
        }
        return newStones;
    }
    static long blink(List<Long> stones, int remainingBlinks) {
        if (remainingBlinks == 0) return stones.size();
        long total = 0;
        for (long n : stones) {
            List<Long> newStones = blink(List.of(n), new ArrayList<>());
            total += blink(newStones, remainingBlinks - 1);
        }
        return total;
    }
}
