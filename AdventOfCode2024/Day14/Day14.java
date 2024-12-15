package AdventOfCode2024.Day14;

import java.util.Scanner;

import AdventOfCode2024.ReadFile;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

class Day14 {
    static List<Robot> robots = new ArrayList<>();
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day14/input.txt");
        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] pv = line.split(" ");
            String[] pxy = pv[0].substring(2).split(",");
            String[] vxy = pv[1].substring(2).split(",");
            robots.add(new Robot(new Point((double)Integer.parseInt(pxy[0]), (double)Integer.parseInt(pxy[1])), new Velocity(Integer.parseInt(vxy[0]), Integer.parseInt(vxy[1]))));
        }
        // Part 1
        robots.forEach(r -> r.move(100));
        long q1 = robots.stream().filter(r -> r.quadrant() == 1).count();
        long q2 = robots.stream().filter(r -> r.quadrant() == 2).count();
        long q3 = robots.stream().filter(r -> r.quadrant() == 3).count();
        long q4 = robots.stream().filter(r -> r.quadrant() == 4).count();
        System.out.println(q1*q2*q3*q4);
        robots.forEach(r -> r.move(-100));
        // Part 2
        long moves = 0;
        while (true) { // Continuously prints out Christmas Trees along with their iteration number!
            robots.forEach(r -> r.move(1));
            moves++;
            Map<Point, Robot> map = new HashMap<>();
            robots.forEach(r -> map.put(r.position, r));
            boolean clumped = map.keySet().stream()
                .anyMatch(p -> // Look for 3x3 chunk of Robots
                    map.get(new Point(p.x() - 1, p.y() - 1)) != null && map.get(new Point(p.x(), p.y() - 1)) != null && map.get(new Point(p.x() + 1, p.y() - 1)) != null &&
                    map.get(new Point(p.x() - 1, p.y())) != null && map.get(new Point(p.x(), p.y())) != null && map.get(new Point(p.x() + 1, p.y())) != null &&
                    map.get(new Point(p.x() - 1, p.y() + 1)) != null && map.get(new Point(p.x(), p.y() + 1)) != null && map.get(new Point(p.x() + 1, p.y() + 1)) != null
                );
            if (clumped) System.out.println("No. " + moves + "\n" + constructMap());
        }
    }
    static String constructMap() {
        StringBuilder sb = new StringBuilder("");
        for (int y = 0; y < Robot.mapSize.y(); y++) {
            for (int x = 0; x < Robot.mapSize.x(); x++) {
                Point p = new Point(x, y);
                long robotsHere = robots.stream().filter(r -> r.position.equals(p)).count();
                String str = robotsHere == 0 ? "." : robotsHere + "";
                sb.append(str);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
record Point(double x, double y) {}
record Velocity(int dx, int dy) {}
class Robot {
    static Point mapSize = new Point(101, 103);
    static Point centre = new Point(Math.floor(Robot.mapSize.x()/2), Math.floor(Robot.mapSize.y()/2));
    Point position;
    Velocity velocity;
    public Robot(Point p, Velocity v) {
        position = p;
        velocity = v;
    }
    public void move(int moves) {
        int newX = ((int)position.x() + moves * velocity.dx()) % (int)mapSize.x();
        int newY = ((int)position.y() + moves * velocity.dy()) % (int)mapSize.y();
        if (newX < 0) newX = (int)mapSize.x() + newX;
        if (newY < 0) newY = (int)mapSize.y() + newY;
        position = new Point(newX, newY);
    }
    public int quadrant() {
        if (position.x() == centre.x() || position.y() == centre.y()) return -1;
        if (position.x() < centre.x()) {
            if (position.y() < centre.y()) return 1;
            return 3;
        }
        if (position.y() < centre.y()) return 2;
        return 4;
    }
    public String toString() { return "Robot(" + position + velocity + ")"; }
}