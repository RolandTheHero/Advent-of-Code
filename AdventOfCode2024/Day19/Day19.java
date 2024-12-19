package AdventOfCode2024.Day19;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import AdventOfCode2024.ReadFile;

public class Day19 {
    static Set<String> available = new HashSet<>();
    static Set<String> designs =  new HashSet<>();
    //static Map<String, Boolean> possible = new HashMap<>(); // Part 1 map. No longer needed
    static Map<String, Long> combinations = new HashMap<>();
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day19/input.txt");
        String[] availableStrings = s.nextLine().split(", ");
        for (String as : availableStrings) if (!available.add(as)) throw new Error("Duplicate available towel pattern: " + as);
        s.nextLine();
        while (s.hasNextLine()) {
            String pattern = s.nextLine();
            if (!designs.add(pattern)) throw new Error("Duplicate towel design pattern: " + pattern);
        }
        System.out.println(part1());
        System.out.println(part2());
    }
    static public long part1() {
        long possiblePatterns = 0;
        for (String design : designs) {
            if (combinations(design) > 0) possiblePatterns++;
        }
        return possiblePatterns;
    }
    static public long part2() {
        long totalCombinations = 0;
        for (String design : designs) totalCombinations += combinations(design);
        return totalCombinations;
    }
    static public long combinations(String design) {
        if (design.isEmpty()) return 1L;
        if (combinations.get(design) != null) return combinations.get(design);
        long totalPatterns = 0;
        for (String av : available) {
            if (design.startsWith(av)) totalPatterns += combinations(design.substring(av.length()));
        }
        combinations.put(design, totalPatterns);
        return totalPatterns;
    }
    /*
     * This was a method made for part 1.
     * No longer needed as you can just call combinations(String) and check that it is greater than 0.
     */
    // static public boolean possible(String design) {
    //     if (design.isEmpty()) return true;
    //     if (possible.get(design) != null) return possible.get(design);
    //     for (String av : available) {
    //         if (design.startsWith(av) && possible(design.substring(av.length()))) {
    //             possible.put(design, true);
    //             return true;
    //         }
    //     }
    //     possible.put(design, false);
    //     return false;
    // }
}