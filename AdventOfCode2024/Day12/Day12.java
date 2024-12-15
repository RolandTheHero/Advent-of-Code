package AdventOfCode2024.Day12;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import AdventOfCode2024.ReadFile;

class Day12 {
    static Map<Point, Character> map = new HashMap<>();
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day12/input.txt");
        int y = 0;
        while (s.hasNextLine()) {
            String line = s.next();
            for (int x = 0; x < line.length(); x++) map.put(new Point(x, y), line.charAt(x));
            y++;
        }
        System.out.println(part1());
        System.out.println(part2());
    }
    static public long part1() {
        Set<Point> done = new HashSet<>();
        long total = 0;
        for (Point p : map.keySet()) {
            if (done.contains(p)) continue;
            Set<Point> areaPoints = findArea(p);
            done.addAll(areaPoints);
            int edges = 0;
            for (Point pp : areaPoints) {
                edges += leftEdges(pp).size();
                edges += rightEdges(pp).size();
                edges += upEdges(pp).size();
                edges += downEdges(pp).size();
            }
            total += edges * areaPoints.size();
        }
        return total;
    }
    static public long part2() {
        Set<Point> done = new HashSet<>();
        long total = 0;
        for (Point p : map.keySet()) {
            if (done.contains(p)) continue;
            Set<Point> areaPoints = findArea(p);
            done.addAll(areaPoints);
            Set<Point> leftEdges = new HashSet<>();
            Set<Point> rightEdges = new HashSet<>();
            Set<Point> upEdges = new HashSet<>();
            Set<Point> downEdges = new HashSet<>();
            int fullEdges = 0;
            for (Point pp : areaPoints) {
                leftEdges.addAll(leftEdges(pp));
                rightEdges.addAll(rightEdges(pp));
                upEdges.addAll(upEdges(pp));
                downEdges.addAll(downEdges(pp));
            }
            Set<Set<Point>> edgesSeen = new HashSet<>();
            Set<Point> edgesVisited = new HashSet<>();
            for (Point pp : leftEdges) {
                if (edgesVisited.contains(pp)) continue;
                Set<Point> pointsInEdge = findVertical(leftEdges, pp);
                edgesSeen.add(pointsInEdge);
                edgesVisited.addAll(pointsInEdge);
            }
            fullEdges += edgesSeen.size();
            edgesSeen.clear();
            edgesVisited.clear();
            for (Point pp : rightEdges) {
                if (edgesVisited.contains(pp)) continue;
                Set<Point> pointsInEdge = findVertical(rightEdges, pp);
                edgesSeen.add(pointsInEdge);
                edgesVisited.addAll(pointsInEdge);
            }
            fullEdges += edgesSeen.size();
            edgesSeen.clear();
            edgesVisited.clear();
            for (Point pp : upEdges) {
                if (edgesVisited.contains(pp)) continue;
                Set<Point> pointsInEdge = findHorizontal(upEdges, pp);
                edgesSeen.add(pointsInEdge);
                edgesVisited.addAll(pointsInEdge);
            }
            fullEdges += edgesSeen.size();
            edgesSeen.clear();
            edgesVisited.clear();
            for (Point pp : downEdges) {
                if (edgesVisited.contains(pp)) continue;
                Set<Point> pointsInEdge = findHorizontal(downEdges, pp);
                edgesSeen.add(pointsInEdge);
                edgesVisited.addAll(pointsInEdge);
            }
            fullEdges += edgesSeen.size();
            total += fullEdges * areaPoints.size();
        }
        return total;
    }
    static public Set<Point> findVertical(Set<Point> edges, Point start) {
        return findVertical(edges, start, new HashSet<>());
    }
    static public Set<Point> findVertical(Set<Point> edges, Point start, Set<Point> toFill) {
        if (!edges.contains(start) || toFill.contains(start)) return toFill;
        toFill.add(start);
        findVertical(edges, new Point(start.x(), start.y() - 1), toFill);
        return findVertical(edges, new Point(start.x(), start.y() + 1), toFill);
    }
    static public Set<Point> findHorizontal(Set<Point> edges, Point start) {
        return findHorizontal(edges, start, new HashSet<>());
    }
    static public Set<Point> findHorizontal(Set<Point> edges, Point start, Set<Point> toFill) {
        if (!edges.contains(start) || toFill.contains(start)) return toFill;
        toFill.add(start);
        findHorizontal(edges, new Point(start.x() - 1, start.y()), toFill);
        return findHorizontal(edges, new Point(start.x() + 1, start.y()), toFill);
    }
    static public Set<Point> leftEdges(Point p) { // Vertical Edge
        char c = map.get(p);
        Set<Point> edgePoints = new HashSet<>();
        Point left = new Point(p.x() - 1, p.y());
        if (map.get(left) == null || map.get(left) != c) edgePoints.add(left);
        return edgePoints;
    }
    static public Set<Point> rightEdges(Point p) { // Vertical Edge
        char c = map.get(p);
        Set<Point> edgePoints = new HashSet<>();
        Point right = new Point(p.x() + 1, p.y());
        if (map.get(right) == null || map.get(right) != c) edgePoints.add(right);
        return edgePoints;
    }
    static public Set<Point> upEdges(Point p) { // Horizontal Edge
        char c = map.get(p);
        Set<Point> edgePoints = new HashSet<>();
        Point up = new Point(p.x(), p.y() - 1);
        if (map.get(up) == null || map.get(up) != c) edgePoints.add(up);
        return edgePoints;
    }
    static public Set<Point> downEdges(Point p) { // Horizontal Edge
        char c = map.get(p);
        Set<Point> edgePoints = new HashSet<>();
        Point down = new Point(p.x(), p.y() + 1);
        if (map.get(down) == null || map.get(down) != c) edgePoints.add(down);
        return edgePoints;
    }
    static public Set<Point> findArea(Point p) { return findArea(new HashSet<>(), p, map.get(p)); }
    static private Set<Point> findArea(Set<Point> points, Point p, char c) {
        if (map.get(p) == null || map.get(p) != c || points.contains(p)) return points;
        points.add(p);
        for (Point adjacent : p.adjacentPoints()) findArea(points, adjacent, c);
        return points;
    }
}

record Point(int x, int y) {
    public Set<Point> adjacentPoints() { return Set.of(new Point(x + 1, y), new Point(x, y + 1), new Point(x - 1, y), new Point(x, y - 1)); }
}