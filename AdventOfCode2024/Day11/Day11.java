package AdventOfCode2024.Day11;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import AdventOfCode2024.ReadFile;

class Day11 {
    static Map<Long, Long> stones = new HashMap<>();
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day11/input.txt");
        while (s.hasNext()) addStones(stones, s.nextLong(), 1);
        System.out.println(countStones(blink(25)));
        System.out.println(countStones(blink(75)));
    }
    static public long countStones(Map<Long, Long> stones) {
        return stones.values().stream().mapToLong(l -> l).sum();
    }
    static public Map<Long, Long> blink(long blinks) {
        Map<Long, Long> current = new HashMap<>(stones);
        for (long blink = 0; blink < blinks; blink++) current = blink(current);
        return current;
    }
    static private Map<Long, Long> blink(Map<Long, Long> currentStones) {
        Map<Long, Long> newStones = new HashMap<>();
        for (Map.Entry<Long, Long> e : currentStones.entrySet()) {
            long number = e.getKey();
            long amount = e.getValue();
            String numberString = String.valueOf(number);
            if (number == 0L) addStones(newStones, 1L, amount);
            else if (numberString.length() % 2 == 0) {
                long leftNum = Long.parseLong(numberString.substring(0, numberString.length()/2));
                long rightNum = Long.parseLong(numberString.substring(numberString.length()/2, numberString.length()));
                addStones(newStones, leftNum, amount);
                addStones(newStones, rightNum, amount);
            } else addStones(newStones, number * 2024, amount);
        }
        return newStones;
    }
    static private void addStones(Map<Long, Long> stones, long number, long amount) {
        stones.put(number, stones.get(number) == null ? amount : amount + stones.get(number));
    }
}