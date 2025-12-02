package AdventOfCode2025.Day1;

import java.util.Scanner;

import AdventOfCode2025.ReadFile; // Use my last ReadFile implementation from last year

/**
 * https://adventofcode.com/2025/day/1
 */
public class Day1 {
    static boolean testsPass;
    public static void main(String[] args) {
        testsPass = test1() && test2() && test3() && test4() && test5() && test6() && test7() && test8() && test9();
        if (!testsPass) {
            System.out.println("Tests fail; cannot proceed.");
            return;
        }
        Scanner s = ReadFile.scan("Day1/input.txt");
        Dial dial = new Dial();
        int zeroes = 0;
        while (s.hasNextLine()) {
            String line = s.nextLine();
            char direction = line.charAt(0);
            int steps = Integer.parseInt(line.substring(1));
            if (direction == 'R') {
                dial.turnR(steps);
            } else if (direction == 'L') {
                dial.turnL(steps);
            }
            if (dial.position() == 0) { zeroes++; }
        }
        System.out.println("Part 1: " + zeroes);
        System.out.println("Part 2: " + dial.ticksAtZero());
    }
    static boolean test1() {
        System.out.println("Test 1");
        Dial dial = new Dial();
        dial.turnL(50);
        //System.out.println("Ticks at zero: " + dial.ticksAtZero());
        return dial.ticksAtZero() == 1;
    }
    static boolean test2() {
        System.out.println("Test 2");
        Dial dial = new Dial();
        dial.turnR(50);
        return dial.ticksAtZero() == 1;
    }
    static boolean test3() {
        System.out.println("Test 3");
        Dial dial = new Dial();
        dial.turnL(49);
        return dial.ticksAtZero() == 0;
    }
    static boolean test4() {
        System.out.println("Test 4");
        Dial dial = new Dial();
        dial.turnR(49);
        return dial.ticksAtZero() == 0;
    }
    static boolean test5() {
        System.out.println("Test 5");
        Dial dial = new Dial();
        dial.turnL(51);
        return dial.ticksAtZero() == 1;
    }
    static boolean test6() {
        System.out.println("Test 6");
        Dial dial = new Dial();
        dial.turnR(51);
        return dial.ticksAtZero() == 1;
    }
    static boolean test7() {
        System.out.println("Test 7");
        Dial dial = new Dial();
        dial.turnL(50);
        dial.turnL(1);
        //System.out.println("Ticks at zero: " + dial.ticksAtZero());
        return dial.ticksAtZero() == 1;
    }
    static boolean test8() {
        System.out.println("Test 8");
        Dial dial = new Dial();
        dial.turnL(68);
        dial.turnL(30);
        dial.turnR(48);
        dial.turnL(5);
        dial.turnR(60);
        dial.turnL(55);
        dial.turnL(1);
        dial.turnL(99);
        dial.turnR(14);
        dial.turnL(82);
        //System.out.println("Ticks at zero: " + dial.ticksAtZero());
        return dial.ticksAtZero() == 6;
    }
    static boolean test9() {
        System.out.println("Test 9");
        Dial dial = new Dial();
        dial.turnL(49);
        dial.turnL(49);
        //System.out.println("Ticks at zero: " + dial.ticksAtZero());
        return dial.ticksAtZero() == 1;
    }
}

class Dial {
    private int position = 50; // The current position of this dial
    private int ticksAtZero = 0; // The number of times this dial passes 0
    public int position() { return position; }
    public int ticksAtZero() { return ticksAtZero; }
    public void turnR(int steps) {
        if (steps >= 0) {
            ticksAtZero += (position + steps) / 100;
        } else {
            ticksAtZero += (100 - position - steps) / 100;
            if (position == 0) ticksAtZero--;
        }

        steps = steps % 100;
        if (steps < 0) steps += 100;
        position = (position + steps) % 100;
    }
    public void turnL(int steps) { turnR(-steps); }
}