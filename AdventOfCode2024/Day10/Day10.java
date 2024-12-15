package AdventOfCode2024.Day10;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import AdventOfCode2024.ReadFile;

class Day10 {
    static Map<Point, Integer> trailmap = new HashMap<>();
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day10/Input10.txt");
        int y = 0;
        while (s.hasNextLine()) {
            String line = s.nextLine();
            for (int x = 0; x < line.length(); x++) trailmap.put(new Point(x, y), Integer.valueOf(String.valueOf(line.charAt(x))));
            y++;
        }
        run();
    }
    static public void run() {
        List<Map.Entry<Point, Integer>> trailheads = trailmap.entrySet().stream()
            .filter(e -> e.getValue() == 0)
            .toList();
        long totalP1 = 0;
        long totalP2 = 0;
        for (Map.Entry<Point, Integer> e : trailheads) {
            Set<Point> trailends = new HashSet<>();
            part1(trailends, -1, e.getKey());
            totalP2 += part2(-1, e.getKey());
            totalP1 += trailends.size();
        }
        System.out.println(totalP1 + " " + totalP2);
    }
    static public void part1(Set<Point> trailends, int previous, Point current) {
        Integer i = trailmap.get(current);
        if (i == null || i != previous + 1) return;
        if (i == 9) {
            trailends.add(current);
            return;
        }
        for (Point adjacent : current.adjacentPoints()) part1(trailends, i, adjacent);
    }
    static public int part2(int previous, Point current) {
        int paths = 0;
        Integer i = trailmap.get(current);
        if (i == null || i != previous + 1) return 0;
        if (i == 9) return 1;
        for (Point adjacent : current.adjacentPoints()) paths += part2(i, adjacent);
        return paths;
    }
}

record Point(int x, int y) {
    public Set<Point> adjacentPoints() { return Set.of(new Point(x + 1, y), new Point(x, y + 1), new Point(x - 1, y), new Point(x, y - 1)); }
}