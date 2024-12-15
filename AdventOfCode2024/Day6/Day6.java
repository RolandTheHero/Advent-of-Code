package AdventOfCode2024.Day6;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import AdventOfCode2024.ReadFile;

class Day6 {
    static Point start = new Point(-1, -1);
    static Map<Point, Boolean> obstacles = new HashMap<>();
    static Point max = new Point(0, 0);
    static int loopSpots = 0;
    static Set<Point> firstVisit = new HashSet<>();
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day6/Input6.txt");
        int currentY = 0;
        int currentX = 0;
        while (s.hasNextLine()) {
            String row = s.nextLine();
            currentX = row.length();
            for (int i = 0; i < row.length(); i++) {
                obstacles.put(new Point(i, currentY), false);
                if (row.charAt(i) == '#') obstacles.put(new Point(i, currentY), true);
                if (row.charAt(i) == '^') { start = new Point(i, currentY); }
            }
            currentY++;
        }
        max = new Point(currentX, currentY);
        //System.out.println(max);
        int moved = run(obstacles, start);
        for (Point newP : firstVisit) {
            if (!obstacles.get(newP) && !newP.equals(start)) {
                if (isLoop(new HashMap<>(obstacles), start, newP)) loopSpots++;
            }
        }
        System.out.println(moved + " " + loopSpots);
    }
    static public int run(Map<Point, Boolean> obstacles, Point current) {
        Direction dir = Direction.Up;
        Set<Point> traversed = new HashSet<>();
        firstVisit.add(current);
        traversed.add(current);
        while (obstacles.get(current) != null) {
            //Point ahead = current.move(dir);
            while (obstacles.get(current.move(dir)) != null && obstacles.get(current.move(dir))) dir = dir.right(); // HAD TO CHANGE THIS TO A WHILE LOOP ;( 2 HOURS DOWN THE DRAIN AHH
            current = current.move(dir);
            if (obstacles.get(current) != null) {firstVisit.add(current);traversed.add(current);}
        }
        return traversed.size();
    }
    static public boolean isLoop(Map<Point, Boolean> obstacles, Point current, Point newObstacle) {
        Map<Point, Boolean> obstacles2 = new HashMap<>(obstacles);
        obstacles2.put(newObstacle, true);
        Set<GuardPost> been = new HashSet<>();
        Direction dir = Direction.Up;
        been.add(new GuardPost(current, dir));
        while (obstacles2.get(current) != null) {
            //Point ahead = current.move(dir);
            while (obstacles2.get(current.move(dir)) != null && obstacles2.get(current.move(dir))) dir = dir.right(); // HAD TO CHANGE THIS TO A WHILE LOOP ;( 2 HOURS DOWN THE DRAIN AHH
            current = current.move(dir);
            if (been.contains(new GuardPost(current, dir))) return true;
            been.add(new GuardPost(current, dir));
        }
        return false;
    }
}

record Point(int x, int y) {
    public Point move(Direction d) {
        return new Point(x + d.moveX(), y + d.moveY());
    }
}
record GuardPost(Point p, Direction d) {}

enum Direction {
    Up {
        public Direction right() { return Right; }
        public int moveX() { return 0; }
        public int moveY() { return -1; }
    },
    Right {
        public Direction right() { return Down; }
        public int moveX() { return 1; }
        public int moveY() { return 0; }
    },
    Down {
        public Direction right() { return Left; }
        public int moveX() { return 0; }
        public int moveY() { return 1; }
    },
    Left {
        public Direction right() { return Up; }
        public int moveX() { return -1; }
        public int moveY() { return 0; }
    };
    abstract public Direction right();
    abstract public int moveX();
    abstract public int moveY();
}
