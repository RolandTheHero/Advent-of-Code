package AdventOfCode2024.Day13;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import AdventOfCode2024.ReadFile;

class Day13 {
    static List<ClawMachine> clawMachines = new ArrayList<>();
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day13/input.txt");
        while (s.hasNextLine()) {
            String buttonA = s.nextLine();
            String buttonB = s.nextLine();
            String prizeText = s.nextLine();
            if (s.hasNextLine()) s.nextLine();
            clawMachines.add(parseClawMachine(buttonA, buttonB, prizeText));
        }
        double minimumToWin = 0;
        for (ClawMachine cm : clawMachines) {
            double min = cm.minimumTokensToWin();
            minimumToWin += min == Double.POSITIVE_INFINITY ? 0 : min;
        }
        System.out.println("Minimum tokens to win all prizes: " + minimumToWin);
    }
    static public Button parseButton(String text) {
        if (!text.startsWith("Button")) throw new IllegalArgumentException(text);
        String[] xy = text.substring(10).split(", ");
        double dx = Long.parseLong(xy[0].split("\\+")[1]);
        double dy = Long.parseLong(xy[1].split("\\+")[1]);
        return new Button(dx, dy);
    }
    static public ClawMachine parseClawMachine(String buttonAText, String buttonBText, String prizeText) {
        if (!prizeText.startsWith("Prize")) throw new IllegalArgumentException(prizeText);
        String[] xy = prizeText.substring(7).split(", ");
        double x = Long.parseLong(xy[0].split("=")[1]);
        double y = Long.parseLong(xy[1].split("=")[1]);

        // Comment these two lines for Part 1
        x += 10000000000000L;
        y += 10000000000000L;
        
        return new ClawMachine(parseButton(buttonAText), parseButton(buttonBText), new Point(x, y));
    }
}
record Point(double x, double y) {}
record Button(double dx, double dy) {}
record ClawMachine(Button a, Button b, Point prize) {
    static double buttonACost = 3;
    static double buttonBCost = 1;
    public double minimumTokensToWin() {
        double minCost = Double.POSITIVE_INFINITY;
        double aPress = findAPresses();
        Point p = new Point(aPress * a.dx(), aPress * a.dy());
        if (aPress * buttonACost >= minCost) return minCost;
        if ((prize.x() - p.x()) % b.dx() == 0 && (prize.y() - p.y()) % b.dy() == 0 && (prize.x() - p.x()) / b.dx() == (prize.y() - p.y()) / b.dy()) {
            double bPress = (prize.x() - p.x()) / b.dx();
            double cost = aPress * buttonACost + bPress * buttonBCost;
            minCost = Double.min(minCost, cost);
        }
        return minCost;
    }
    public double findAPresses() {
        double v1 = a.dx();
        double v2 = b.dx();
        double g1 = prize.x();
        double v3 = a.dy();
        double v4 = b.dy();
        double g2 = prize.y();
        double aPushes = (g1 - g2 * (v2/v4))/(v1 - v3 * (v2/v4));
        return Math.round(aPushes);
    }
}