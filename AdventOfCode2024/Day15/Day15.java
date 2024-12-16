package AdventOfCode2024.Day15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import AdventOfCode2024.ReadFile;

class Day15 {
    static Map<Point, Tile> warehouse = new HashMap<>() { // Part 1 Warehouse
        public Tile get(Object p) {
            Tile t = super.get(p);
            return t == null ? Tile.Empty : t;
        }
    };
    static Point mapSize = new Point(0, 0);
    static Point robotPosition = null;

    static Map<Point, Tile2> warehouse2 = new HashMap<>() { // Part 2 Warehouse
        public Tile2 get(Object p) {
            Tile2 t = super.get(p);
            return t == null ? Tile2.Empty : t;
        }
    };
    static Point mapSize2 = new Point(0, 0);
    static Point robotPosition2 = null;

    static List<Direction> robotMoves = new ArrayList<>();
    static public void main(String[] args) {
        String file = "Day15/input.txt";
        Scanner s = ReadFile.scan(file);
        int y = 0;
        while (s.hasNextLine()) {
            String line = s.nextLine();
            if (line.isEmpty()) break;
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                Tile t = Tile.getType(c);
                Point p = new Point(x, y);
                if (t == Tile.Robot) robotPosition = p;
                warehouse.put(p, t);
                mapSize = p.translate(Direction.Right).translate(Direction.Down);
            }
            y++;
        }
        while (s.hasNextLine()) {
            String line = s.nextLine();
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                robotMoves.add(Direction.getType(c));
            }
        }
        System.out.println(part1());
        
        s = ReadFile.scan(file);
        y = 0;
        while (s.hasNextLine()) {
            String line = s.nextLine();
            if (line.isEmpty()) break;
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                Tile t = Tile.getType(c);
                Point p = new Point(x, y);
                if (t == Tile.Robot) robotPosition2 = new Point(x * 2, y);
                Tile2.loadTile(p, c);
                mapSize2 = new Point(p.x() * 2 + 2, p.y() + 1);
            }
            y++;
        }
        System.out.println(part2());
    }
    static public long part1() {
        for (Direction d : robotMoves) {
            if (warehouse.get(robotPosition).move(robotPosition, d)) robotPosition = robotPosition.translate(d);
        }
        long total = 0;
        for (Map.Entry<Point, Tile> e : warehouse.entrySet()) {
            if (e.getValue() != Tile.Box) continue;
            Point p = e.getKey();
            total += 100 * p.y() + p.x();
        }
        return total;
    }
    static public long part2() {
        for (Direction d : robotMoves) {
            if (warehouse2.get(robotPosition2).canMove(robotPosition2, d) && warehouse2.get(robotPosition2).move(robotPosition2, d)) robotPosition2 = robotPosition2.translate(d);
        }
        long total = 0;
        for (Map.Entry<Point, Tile2> e : warehouse2.entrySet()) {
            if (e.getValue() != Tile2.BoxL) continue;
            Point p = e.getKey();
            total += 100 * p.y() + p.x();
        }
        return total;
    }
    static String constructMap() {
        StringBuilder sb = new StringBuilder("");
        for (int y = 0; y < mapSize.y(); y++) {
            for (int x = 0; x < mapSize.x(); x++) {
                Point p = new Point(x, y);
                sb.append(warehouse.get(p));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    static String constructMap2() {
        StringBuilder sb = new StringBuilder("");
        for (int y = 0; y < mapSize2.y(); y++) {
            for (int x = 0; x < mapSize2.x(); x++) {
                Point p = new Point(x, y);
                sb.append(warehouse2.get(p));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
record Point(int x, int y) {
    public Point translate(Direction d) { return new Point(x + d.dx(), y + d.dy()); }
}
enum Direction {
    Up { public int dy() { return -1; } },
    Down { public int dy() { return 1; } },
    Left { public int dx() { return -1; } },
    Right { public int dx() { return 1; } };
    static public Direction getType(char c) {
        return switch (c) {
            case '^' -> Up;
            case 'v' -> Down;
            case '<' -> Left;
            case '>' -> Right;
            default -> throw new IllegalArgumentException("Unknown direction character: " + c);
        };
    }
    public int dx() { return 0; }
    public int dy() { return 0; }
}
enum Tile {
    Empty {
        public boolean move(Point current, Direction d) { return true; }
        public String toString() { return "."; }
    },
    Wall {
        public boolean move(Point current, Direction d) { return false; }
        public String toString() { return "#"; }
    },
    Box {
        public String toString() { return "O"; }
    },
    Robot {
        public String toString() { return "@"; }
    };
    static public Tile getType(char c) {
        return switch (c) {
            case '.' -> Empty;
            case '#' -> Wall;
            case 'O' -> Box;
            case '@' -> Robot;
            default -> throw new IllegalArgumentException("Unknown tile character: " + c);
        };
    }
    public boolean move(Point current, Direction d) {
        Point next = current.translate(d);
        boolean canMove = Day15.warehouse.get(next).move(next, d);
        if (canMove) {
            Day15.warehouse.remove(current);
            Day15.warehouse.put(next, this);
        };
        return canMove;
    }
}
enum Tile2 {
    Empty {
        public boolean move(Point current, Direction d) { return true; }
        public boolean canMove(Point current, Direction d) { return true; }
        public String toString() { return "."; }
    },
    Wall {
        public boolean move(Point current, Direction d) { return false; }
        public boolean move2(Point current, Direction d) { return false; }
        public String toString() { return "#"; }
    },
    BoxL {
        public boolean move(Point current, Direction d) {
            Point next = current.translate(d);
            Point rightSide = current.translate(Direction.Right);
            boolean canMove = false;
            if (next.equals(rightSide) || d == Direction.Left) canMove = Day15.warehouse2.get(next).move(next, d);
            else canMove = Day15.warehouse2.get(next).move(next, d) && Day15.warehouse2.get(rightSide).move2(rightSide, d);
            if (canMove) {
                Day15.warehouse2.remove(current);
                Day15.warehouse2.put(next, this);
            }
            return canMove;
        }
        public boolean move2(Point current, Direction d) {
            Point next = current.translate(d);
            boolean canMove = Day15.warehouse2.get(next).move(next, d);
            if (canMove) {
                Day15.warehouse2.remove(current);
                Day15.warehouse2.put(next, this);
            }
            return canMove;
        }
        public boolean canMove(Point current, Direction d) {
            Point next = current.translate(d);
            Point rightSide = current.translate(Direction.Right);
            boolean canMove = false;
            if (next.equals(rightSide) || d == Direction.Left) canMove = Day15.warehouse2.get(next).canMove(next, d);
            else canMove = Day15.warehouse2.get(next).canMove(next, d) && Day15.warehouse2.get(rightSide).canMove2(rightSide, d);
            return canMove;
        }
        public String toString() { return "["; }
    },
    BoxR {
        public boolean move(Point current, Direction d) {
            Point next = current.translate(d);
            Point leftSide = current.translate(Direction.Left);
            boolean canMove = false;
            if (next.equals(leftSide) || d == Direction.Right) canMove = Day15.warehouse2.get(next).move(next, d);
            else canMove = Day15.warehouse2.get(next).move(next, d) && Day15.warehouse2.get(leftSide).move2(leftSide, d);
            if (canMove) {
                Day15.warehouse2.remove(current);
                Day15.warehouse2.put(next, this);
            };
            return canMove;
        }
        public boolean move2(Point current, Direction d) {
            Point next = current.translate(d);
            boolean canMove = Day15.warehouse2.get(next).move(next, d);
            if (canMove) {
                Day15.warehouse2.remove(current);
                Day15.warehouse2.put(next, this);
            };
            return canMove;
        }
        public boolean canMove(Point current, Direction d) {
            Point next = current.translate(d);
            Point leftSide = current.translate(Direction.Left);
            boolean canMove = false;
            if (next.equals(leftSide) || d == Direction.Right) canMove = Day15.warehouse2.get(next).canMove(next, d);
            else canMove = Day15.warehouse2.get(next).canMove(next, d) && Day15.warehouse2.get(leftSide).canMove2(leftSide, d);
            return canMove;
        }
        public String toString() { return "]"; }
    },
    Robot {
        public String toString() { return "@"; }
    };
    static public void loadTile(Point p, char c) {
        p = new Point(p.x() * 2, p.y());
        switch (c) {
            case '.': { Day15.warehouse2.put(p, Empty); Day15.warehouse2.put(p.translate(Direction.Right), Empty); break; }
            case '#': { Day15.warehouse2.put(p, Wall); Day15.warehouse2.put(p.translate(Direction.Right), Wall); break; }
            case 'O': { Day15.warehouse2.put(p, BoxL); Day15.warehouse2.put(p.translate(Direction.Right), BoxR); break; }
            case '@': { Day15.warehouse2.put(p, Robot); Day15.warehouse2.put(p.translate(Direction.Right), Empty); break; }
            default: throw new IllegalArgumentException("Unknown tile character: " + c);
        }
    }
    public boolean move(Point current, Direction d) {
        Point next = current.translate(d);
        boolean canMove = Day15.warehouse2.get(next).move(next, d);
        if (canMove) {
            Day15.warehouse2.remove(current);
            Day15.warehouse2.put(next, this);
        };
        return canMove;
    }
    public boolean move2(Point current, Direction d) {
        return true;
    }
    public boolean canMove(Point current, Direction d) {
        Point next = current.translate(d);
        return Day15.warehouse2.get(next).canMove(next, d);
    }
    public boolean canMove2(Point current, Direction d) {
        Point next = current.translate(d);
        return Day15.warehouse2.get(next).canMove(next, d);
    }
}