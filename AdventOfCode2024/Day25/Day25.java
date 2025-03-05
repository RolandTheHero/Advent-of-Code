package AdventOfCode2024.Day25;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import AdventOfCode2024.ReadFile;

public class Day25 {
    static int rows = 7;
    static Set<Lock> locks = new HashSet<>();
    static Set<Key> keys = new HashSet<>();
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day25/input.txt");
        while (s.hasNextLine()) {
            String line = s.nextLine();
            int[] heights = new int[line.length()];
            for (int r = 1; r < rows - 1; r++) {
                String nextLine = s.nextLine();
                for (int x = 0; x < heights.length; x++) {
                    if (nextLine.charAt(x) == '#') heights[x]++;
                }
            }
            s.nextLine();
            if (line.startsWith("#")) locks.add(new Lock(heights));
            else keys.add(new Key(heights));
            if (s.hasNextLine()) s.nextLine();
        }
        System.out.println(part1());
    }
    static public long part1() {
        return locks.stream()
            .mapToLong(l -> keys.stream().filter(k -> l.checkKey(k)).count())
            .sum();
    }
}
record Lock(int[] heights) {
    public boolean checkKey(Key k) {
        if (k.heights().length != heights.length) return false;
        for (int i = 0; i < heights.length; i++) {
            if (heights[i] + k.heights()[i] > Day25.rows - 2) return false;
        }
        return true;
    }
    public String toString() { return "Lock" + Arrays.stream(heights).mapToObj(i -> "" + i).collect(Collectors.joining(",", "(", ")")); }
}
record Key(int[] heights) {
    public boolean checkLock(Lock l) { return l.checkKey(this); }
    public String toString() { return "Key" + Arrays.stream(heights).mapToObj(i -> "" + i).collect(Collectors.joining(",", "(", ")")); }
}