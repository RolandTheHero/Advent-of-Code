package AdventOfCode2024.Day8;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import AdventOfCode2024.ReadFile;

class Day8 {
    static Map<Point, Character> antennas = new HashMap<>();
    static Map<Character, Set<Point>> antennas2 = new HashMap<>();
    static Set<Point> antinodes = new HashSet<>();
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day8/Input8.txt");
        int currentY = 0;
        while (s.hasNextLine()) {
            String line = s.nextLine();
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if (c == '.') continue;
                Point p = new Point(x, currentY);
                antennas.put(p, c);
                Set<Point> points = antennas2.get(c);
                if (points == null) antennas2.put(c, new HashSet<>(Set.of(p)));
                else { points.add(p); }
            }
            currentY++;
        }
        for (Map.Entry<Character, Set<Point>> e1 : antennas2.entrySet()) {
            for (Point p1 : e1.getValue()) {
                for (Point p2 : e1.getValue()) {
                    if (p1.equals(p2)) continue;
                    // Part 1
                    //antinodes.add(new Point(p1.x() - 2*(p1.x() - p2.x()), p1.y() - 2*(p1.y() - p2.y())));
                    // Part 2
                    for (int i = 0; i < 50; i++) antinodes.add(new Point(p1.x() - i*(p1.x() - p2.x()), p1.y() - i*(p1.y() - p2.y())));
                }
            }
        }
        antinodes.removeIf(p -> p.x() < 0 || p.y() < 0 || p.x() > 49 || p.y() > 49); // Remove the antinodes that are outside the bounds
        System.out.println(antinodes.size());
    }
}

record Point(int x, int y) {}