package AdventOfCode2024.Day16;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import AdventOfCode2024.ReadFile;

class Day16 {
    static Map<Point, Character> map = new HashMap<>();
    static Point start;
    static Point end;
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day16/input.txt");
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
        System.out.println(part1());
        System.out.println(part2());
    }
    static public long part1() {
        Queue<Path> paths = new PriorityQueue<>();
        paths.offer(new Path());
        while (!paths.isEmpty()) {
            Path path = paths.poll();
            Set<Point> adjPoints = path.adjacentPoints();
            for (Point adjPoint : adjPoints) {
                Path newPath = new Path(path);
                newPath.moveTo(adjPoint);
                paths.offer(newPath);
                if (adjPoint.equals(end)) return newPath.score();
                Path toRemove = null;
                for (Path other : paths) {
                    if (newPath == other) continue;
                    if (newPath.position().point().equals(other.position().point())) {
                        if (newPath.score() < other.score()) {
                            toRemove = other;
                        } else {
                            toRemove = newPath;
                        }
                    }
                }
                paths.remove(toRemove);
            }
        }
        return -1;
    }
    static public long part2() { // Almost exactly like part 1, but combine paths with the same score and then find length when goal is reached
        Queue<Path> paths = new PriorityQueue<>();
        paths.offer(new Path());
        while (!paths.isEmpty()) {
            Path path = paths.poll();
            Set<Point> adjPoints = path.adjacentPoints();
            for (Point adjPoint : adjPoints) {
                Path newPath = new Path(path);
                newPath.moveTo(adjPoint);
                paths.offer(newPath);
                if (adjPoint.equals(end)) return newPath.length();
                Set<Path> toRemove = new HashSet<>();
                Path toAdd = null;
                for (Path other : paths) {
                    if (newPath == other) continue;
                    if (newPath.position().point().equals(other.position().point())) {
                        if (newPath.score() < other.score()) {
                            toRemove.add(other);
                        } else if (newPath.score() == other.score()) {
                            toRemove.add(other);
                            toRemove.add(newPath);
                            toAdd = newPath.combine(other);
                        } else {
                            toRemove.add(newPath);
                        }
                    }
                }
                paths.removeAll(toRemove);
                if (toAdd != null) paths.add(toAdd);
            }
        }
        return -1;
    }
}
record Point(int x, int y) {
    public Point translate(Direction d) { return new Point(x + d.dx(), y + d.dy()); }
    public Set<Point> adjacentPoints() {
        Set<Point> points = new HashSet<>(Set.of(new Point(x + 1, y), new Point(x, y + 1), new Point(x - 1, y), new Point(x, y - 1)));
        return points;
    }
}
enum Direction {
    Up {
        public int dy() { return -1; }
        public Direction left() { return Left; }
        public Direction right() { return Right; }
    },
    Down {
        public int dy() { return 1; }
        public Direction left() { return Right; }
        public Direction right() { return Left; }
    },
    Left {
        public int dx() { return -1; }
        public Direction left() { return Down; }
        public Direction right() { return Up; }
    },
    Right {
        public int dx() { return 1; }
        public Direction left() { return Up; }
        public Direction right() { return Down; }
    };
    public int dx() { return 0; }
    public int dy() { return 0; }
    abstract public Direction left();
    abstract public Direction right();
}
enum Action {
    Move {
        public Position act(Position pos) { return new Position(pos.point().translate(pos.direction()), pos.direction()); }
        public long score() { return 1L; }
    },
    TurnL {
        public Position act(Position pos) { return new Position(pos.point(), pos.direction().left()); }
        public long score() { return 1000L; }
    },
    TurnR {
        public Position act(Position pos) { return new Position(pos.point(), pos.direction().right()); }
        public long score() { return 1000L; }
    };
    abstract public Position act(Position pos);
    abstract long score();
}
record Position(Point point, Direction direction) {}
class Path implements Comparable<Path> {
    final private Set<Point> pathPoints; // Points this path covers
    private Position position; // Current location in the path
    private long score = 0;
    public Path(Path old) {
        pathPoints = new HashSet<>(old.pathPoints);
        position = new Position(old.position.point(), old.position.direction());
        score = old.score;
    }
    public Path() {
        pathPoints = new HashSet<>();
        position = new Position(Day16.start, Direction.Right);
        pathPoints.add(Day16.start);
    }
    public Path combine(Path other) {
        if (score != other.score) throw new IllegalArgumentException("To combine two paths, they need a matching score.");
        Path newP = new Path(this);
        newP.pathPoints.addAll(other.pathPoints);
        return newP;
    }
    public void addAction(Action a) {
        position = a.act(position);
        pathPoints.add(position.point());
        score += a.score();
    }
    public Set<Point> adjacentPoints() {
        Set<Point> ps = position.point().adjacentPoints();
        ps.removeIf(p -> Day16.map.get(p) == null || Day16.map.get(p) != '.');
        ps.removeIf(p -> pathPoints.contains(p));
        return ps;
    }
    public void moveTo(Point p) {
        if (!adjacentPoints().contains(p)) throw new IllegalArgumentException("Cannot move to " + p);
        if (position.point().translate(position.direction()).equals(p)) {
            addAction(Action.Move);
        } else if (Action.Move.act(Action.TurnL.act(position)).point().equals(p)) {
            addAction(Action.TurnL);
            addAction(Action.Move);
        } else if (Action.Move.act(Action.TurnR.act(position)).point().equals(p)) {
            addAction(Action.TurnR);
            addAction(Action.Move);
        }
        return; // In theory, should not reach here
    }
    public Position position() { return position; }
    public long score() { return score; }
    public int length() { return pathPoints.size(); }
    public int compareTo(Path other) { return Long.compare(score, other.score); }
}