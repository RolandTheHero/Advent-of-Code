package AdventOfCode2024.Day18;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import AdventOfCode2024.ReadFile;

public class Day18 {
    static Point mapSize = new Point(70, 70); // (and goal is at this point)
    static List<Point> corruptedBytes = new ArrayList<>();
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day18/input.txt");
        while (s.hasNextLine()) {
            String[] nums = s.nextLine().split(",");
            corruptedBytes.add(new Point(Integer.parseInt(nums[0]), Integer.parseInt(nums[1])));
        }
        System.out.println(part1(1024));
        System.out.println(part2());
    }
    static public int part1(int firstBytes) {
        Map<Point, Character> afterFirstBytes = new HashMap<>();
        for (int y = 0; y <= mapSize.y(); y++) for (int x = 0; x <= mapSize.x(); x++) afterFirstBytes.put(new Point(x, y), '.');
        for (int i = 0; i < firstBytes; i++) afterFirstBytes.put(corruptedBytes.get(i), '#');
        Queue<Path> paths = new PriorityQueue<>();
        paths.offer(new Path(afterFirstBytes));
        while (!paths.isEmpty()) {
            Path currentPath = paths.poll();
            for (Point adjPoint : currentPath.adjacentPoints()) {
                Path newPath = new Path(currentPath, afterFirstBytes);
                newPath.moveTo(adjPoint);
                paths.offer(newPath);
                if (adjPoint.equals(mapSize)) return newPath.pathPoints().size() - 1;
                Path toRemove = null;
                for (Path other : paths) {
                    if (newPath == other) continue;
                    if (other.pathPoints().keySet().contains(adjPoint)) {
                        if (newPath.pathPoints().get(adjPoint) < other.pathPoints().get(adjPoint)) toRemove = other;
                        else toRemove = newPath;
                    }
                }
                paths.remove(toRemove);
            }
        }
        return -1;
    }
    static public Point part2() {
        int db = 1024;
        while (db >= corruptedBytes.size()) db /= 2;
        for (int b = 0; b < corruptedBytes.size(); b += db) {
            Point nextByte = corruptedBytes.get(b);
            //System.out.println("Adding corrupted byte no. " + b + " at " + nextByte);
            if (part1(b + 1) == -1 && db != 1) { b -= db; db /= 2; }
            else if (part1(b + 1) == -1) return nextByte; // b + 1 because index start at 0, so 0th byte is the 1st one
            if (b + db >= corruptedBytes.size()) b = corruptedBytes.size() - db - 1;
        }
        return null;
    }
}
record Point(int x, int y) {
    public Set<Point> adjacentPoints() {
        return new HashSet<>(Set.of(new Point(x + 1, y), new Point(x, y + 1), new Point(x - 1, y), new Point(x, y - 1)));
    }
}
class Path implements Comparable<Path> {
    private Map<Point, Character> currentMap;
    private Map<Point, Integer> pathPoints = new HashMap<>();
    private Point position = new Point(0, 0);
    public Path(Path old, Map<Point, Character> map) {
        currentMap = map;
        pathPoints = new HashMap<>(old.pathPoints);
        position = old.position;
    }
    public Path(Map<Point, Character> map) {
        currentMap = map;
        pathPoints.put(new Point(0, 0), 0);
    }
    public void moveTo(Point p) {
        if (!adjacentPoints().contains(p)) throw new IllegalArgumentException("Can only move to adjacent points or points not already traversed!");
        pathPoints.put(p, pathPoints.size());
        position = p;
    }
    public Set<Point> adjacentPoints() {
        Set<Point> adj = position.adjacentPoints();
        adj.removeIf(p -> pathPoints.keySet().contains(p) || currentMap.get(p) == null || currentMap.get(p) != '.');
        return adj;
    }
    public Map<Point, Integer> pathPoints() { return pathPoints; }
    public Point currentPosition() { return position; }
    public int compareTo(Path other) { return Integer.compare(pathPoints.size(), other.pathPoints.size()); }
}