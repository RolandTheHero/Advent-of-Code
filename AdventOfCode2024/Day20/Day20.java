package AdventOfCode2024.Day20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import AdventOfCode2024.ReadFile;

public class Day20 {
    static Map<Point, Character> map = new HashMap<>();
    static List<Point> path = new ArrayList<>();
    static Map<Point, Integer> distanceFromStart = new HashMap<>();
    static Point start = null;
    static Point end = null;
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day20/input.txt");
        int y = 0;
        while (s.hasNextLine()) {
            String line = s.nextLine();
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                Point p = new Point(x, y);
                if (c == 'S') { start = p; c = '.'; }
                else if (c == 'E') { end = p; c = '.'; }
                map.put(p, c);
            }
            y++;
        }
        Point current = start;
        Point previous = start;
        int distFromStart = 0;
        while (!current.equals(end)) { // Add points that make up the path to the end
            path.add(current);
            distanceFromStart.put(current, distFromStart);
            Set<Point> adjP = current.adjacentPoints();
            adjP.removeIf(p -> map.get(p) == null || map.get(p) == '#');
            adjP.remove(previous);
            for (Point p : adjP) { previous = current; current = p; }
            distFromStart++;
        }
        path.add(end);
        distanceFromStart.put(end, distFromStart);
        System.out.println(cheat(2, 100));
        System.out.println(cheat(20, 100));
    }
    static public long cheat(int cheatTime, int picoSaveMin) {
        long total = 0;
        for (Point p : path) {
            for (Point pp : p.reachablePoints(cheatTime)) {
                int toReach = p.stepsToReach(pp);
                if (distanceFromStart.get(pp) >= distanceFromStart.get(p) + picoSaveMin + toReach) total++;
            }
        }
        return total;
    }
}
record Point(int x, int y) {
    public Set<Point> adjacentPoints() {
        return new HashSet<>(Set.of(new Point(x + 1, y), new Point(x, y + 1), new Point(x - 1, y), new Point(x, y - 1)));
    }
    public int stepsToReach(Point other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }
    public Set<Point> reachablePoints(int maxSteps) { // steps is picoseconds in this case
        Set<Point> potentialPoints = new HashSet<>(Day20.distanceFromStart.keySet());
        potentialPoints.removeIf(p -> stepsToReach(p) > maxSteps);
        return potentialPoints;
    }
}