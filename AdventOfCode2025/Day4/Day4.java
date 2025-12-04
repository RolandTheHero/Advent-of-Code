package AdventOfCode2025.Day4;

import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;

import AdventOfCode2025.ReadFile;

public class Day4 {
    static Set<Point> paper = new HashSet<>();
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day4/input.txt");
        int y = 0;
        while (s.hasNextLine()) {
            String line = s.nextLine();
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '@') paper.add(new Point(x, y));
            }
            y++;
        }
        int part1 = 0;
        for (Point p : paper) {
            int neighbourPaper = 0;
            for (int dy = -1; dy <= 1; dy++) {
                for (int dx = -1; dx <= 1; dx++) {
                    if (dx == 0 && dy == 0) continue;
                    Point neighbor = new Point(p.x() + dx, p.y() + dy);
                    if (paper.contains(neighbor)) {
                        neighbourPaper++;
                    }
                }
            }
            if (neighbourPaper < 4) part1++;
        }
        Set<Point> toRemove = new HashSet<>();
        int part2 = 0;
        do {
            toRemove.clear();
            for (Point p : paper) {
                int neighbourPaper = 0;
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        if (dx == 0 && dy == 0) continue;
                        Point neighbor = new Point(p.x() + dx, p.y() + dy);
                        if (paper.contains(neighbor)) {
                            neighbourPaper++;
                        }
                    }
                }
                if (neighbourPaper < 4) toRemove.add(p);
            }
            part2 += toRemove.size();
            paper.removeAll(toRemove);
        } while (!toRemove.isEmpty());
        System.out.println("Part 1: " + part1);
        System.out.println("Part 2: " + part2);
    }
}

record Point(int x, int y) {}
