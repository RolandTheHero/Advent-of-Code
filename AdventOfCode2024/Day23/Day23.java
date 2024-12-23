package AdventOfCode2024.Day23;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import AdventOfCode2024.ReadFile;

public class Day23 {
    static Map<String, Computer> computers = new HashMap<>();
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day23/input.txt");
        while (s.hasNextLine()) {
            String[] names = s.nextLine().split("-");
            Computer c1 = computers.get(names[0]);
            Computer c2 = computers.get(names[1]);
            if (c1 == null) { c1 = new Computer(names[0]); computers.put(names[0], c1); }
            if (c2 == null) { c2 = new Computer(names[1]); computers.put(names[1], c2); }
            c1.addConnection(c2);
        }
        System.out.println(part1());
        System.out.println(part2());
    }
    static public long part1() {
        Set<Set<Computer>> trios = new HashSet<>();
        computers.values().forEach(c -> trios.addAll(c.findTrio()));
        return trios.stream().filter(s -> s.stream().anyMatch(c -> c.name().startsWith("t"))).count();
    }
    static public String part2() {
        Set<Computer> largest = new HashSet<>();
        for (Computer c : computers.values()) {
            Set<Computer> candidate = c.findLargestGroup();
            if (candidate.size() > largest.size()) largest = candidate;
        }
        return largest.stream().map(c -> c.name()).sorted().collect(Collectors.joining(","));
    }
}
class Computer {
    private String name;
    private Set<Computer> connections = new HashSet<>();
    public Computer(String name) { this.name = name; }
    public String name() { return name; }
    public Set<Computer> connections() { return Collections.unmodifiableSet(connections); }
    public boolean addConnection(Computer c) { return connections.add(c) && c.connections.add(this); }
    public Set<Set<Computer>> findTrio() {
        Set<Set<Computer>> toReturn = new HashSet<>();
        for (Computer c1 : connections()) {
            for (Computer c2 : connections()) {
                if (c1 == c2) continue;
                if (c1.connections().contains(c2)) toReturn.add(Set.of(this, c1, c2));
            }
        }
        return toReturn;
    }
    public Set<Computer> findLargestGroup() {
        Set<Set<Computer>> groups = new HashSet<>();
        for (Computer c : connections()) {
            groups.add(new HashSet<>(Set.of(this, c)));
        }
        for (Computer c : connections()) {
            for (Set<Computer> group : groups) {
                if (group.stream().allMatch(cc -> c.connections().contains(cc))) group.add(c);
            }
        }
        return groups.stream().reduce(Set.of(), (prev, curr) -> prev.size() > curr.size() ? prev : curr);
    }
    public String toString() { return name; }
}