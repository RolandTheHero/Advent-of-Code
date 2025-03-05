package AdventOfCode2024.Day24;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import AdventOfCode2024.ReadFile;

public class Day24 {
    static Map<String, Boolean> wires = new HashMap<>();
    static Set<Gate> toRun = new HashSet<>();
    static long x;
    static long y;
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day24/input.txt");
        while (s.hasNextLine()) {
            String line = s.nextLine();
            if (line.isEmpty()) break;
            String[] wStrings = line.split(": ");
            wires.put(wStrings[0], wStrings[1].equals("1") ? true : false);
        }
        while (s.hasNextLine()) {
            String w1 = s.next();
            String type = s.next();
            String w2 = s.next();
            s.next();
            String output = s.next();
            wires.put(w1, wires.get(w1));
            wires.put(w2, wires.get(w2));
            wires.put(output, wires.get(output));
            toRun.add(Gate.make(w1, type, w2, output));
        }
        System.out.println(part1(wires, toRun));
        x = binaryToDecimal(wires.keySet().stream().filter(w -> w.startsWith("x")).toList());
        y = binaryToDecimal(wires.keySet().stream().filter(w -> w.startsWith("y")).toList());
        System.out.println(part2());
    }
    static public long binaryToDecimal(String binary) {
        long total = 0;
        for (int i = 0; i < binary.length(); i++) {
            char c = binary.charAt(binary.length() - i - 1);
            if (c == '1') total += Math.pow(2, i);
        }
        return total;
    }
    static public long binaryToDecimal(List<String> wireList) {
        wireList = wireList.stream().sorted().toList().reversed();
        return binaryToDecimal(wireList.stream()
            .map(w -> wires.get(w) ? "1" : "0")
            .collect(Collectors.joining()));
    }
    static public String makeString(Collection<String> collection) {
        return collection.stream()
            .sorted()
            .collect(Collectors.joining(","));
    }
    static public long part1(Map<String, Boolean> wires, Set<Gate> toRun) {
        List<String> startingZ = wires.keySet().stream()
            .filter(w -> w.startsWith("z"))
            .sorted()
            .collect(Collectors.toList());
        while (startingZ.stream().anyMatch(w -> wires.get(w) == null)) {
            for (Gate g : toRun) {
                try { g.evaluate(wires); }
                catch (NullPointerException e) {}
            }
        }
        return binaryToDecimal(startingZ);
    }
    static public String part2() {
        return null;
    }
}
interface Gate {
    static public Gate make(String w1, String type, String w2, String output) {
        return switch (type) {
            case "XOR" -> new XOR(w1, w2, output);
            case "AND" -> new AND(w1, w2, output);
            case "OR" -> new OR(w1, w2, output);
            default -> null;
        };
    }
    public boolean evaluate(Map<String, Boolean> to);
    public String w1();
    public String w2();
    public String output();
    public Gate swapOutput(Gate other);
}
record XOR(String w1, String w2, String output) implements Gate {
    public boolean evaluate(Map<String, Boolean> to) { return to.put(output, !Day24.wires.get(w1).equals(Day24.wires.get(w2))); }
    public Gate swapOutput(Gate other) { return new XOR(w1, w2, other.output()); }
}
record AND(String w1, String w2, String output) implements Gate {
    public boolean evaluate(Map<String, Boolean> to) { return to.put(output, Day24.wires.get(w1) && Day24.wires.get(w2)); }
    public Gate swapOutput(Gate other) { return new AND(w1, w2, other.output()); }
}
record OR(String w1, String w2, String output) implements Gate {
    public boolean evaluate(Map<String, Boolean> to) { return to.put(output, Day24.wires.get(w1) || Day24.wires.get(w2)); }
    public Gate swapOutput(Gate other) { return new OR(w1, w2, other.output()); }
}