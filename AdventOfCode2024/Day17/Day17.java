package AdventOfCode2024.Day17;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

import AdventOfCode2024.ReadFile;

public class Day17 {
    static long registerA;
    static long registerB;
    static long registerC;

    static List<Instruction> instructions = new ArrayList<>();
    static List<Long> program = new ArrayList<>();
    static int pointer = 0;
    static List<Long> output = new ArrayList<>();
    
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day17/input.txt");
        s.next(); s.next(); registerA = s.nextLong();
        s.next(); s.next(); registerB = s.nextLong();
        s.next(); s.next(); registerC = s.nextLong();
        s.nextLine(); s.next();
        String str = s.nextLine().trim();
        String[] instructionsText = str.split(",");
        for (String sr : instructionsText) {
            instructions.add(Instruction.getInstructionFromCode(Integer.parseInt(sr)));
            program.add(Long.parseLong(sr));
        }
        System.out.println(part1());
        System.out.println(part2());
    }
    static public String part1() {
        pointer = 0;
        output.clear();
        while (pointer < instructions.size()) {
            Instruction i = instructions.get(pointer);
            i.run(instructions.get(pointer + 1).opcode());
            pointer += 2;
        }
        return output.stream().map(l -> l + "").collect(Collectors.joining(","));
    }
    static public long part2() {
        long toCheck = 1229500000L;
        while (true) {
            registerA = toCheck;
            if (toCheck % 10000000 == 0) System.out.println("Checking " + registerA);
            if (part1().equals(instructions.stream().map(i -> i.opcode() + "").collect(Collectors.joining(",")))) break;
            toCheck++;
        }
        return toCheck;
    }
}
enum Instruction {
    ADV {
        public int opcode() { return 0; }
        public void run(int operand) {
            Day17.registerA = (long)(Day17.registerA / Math.pow(2, comboValue(operand)));
        }
    },
    BXL {
        public int opcode() { return 1; }
        public void run(int operand) {
            Day17.registerB = Day17.registerB ^ operand;
        }
    },
    BST {
        public int opcode() { return 2; }
        public void run(int operand) {
            Day17.registerB = comboValue(operand) % 8;
        }
    },
    JNZ {
        public int opcode() { return 3; }
        public void run(int operand) {
            if (Day17.registerA == 0) return;
            Day17.pointer = operand - 2; // -2 so that it the pointer jump at the end of every instruction will be at the right place
        }
    },
    BXC {
        public int opcode() { return 4; }
        public void run(int operand) {
            Day17.registerB = Day17.registerB ^ Day17.registerC;
        }
    },
    OUT {
        public int opcode() { return 5; }
        public void run(int operand) {
            Day17.output.add(comboValue(operand) % 8);
        }
    },
    BDV {
        public int opcode() { return 6; }
        public void run(int operand) {
            Day17.registerB = (long)(Day17.registerA / Math.pow(2, comboValue(operand)));
        }
    },
    CDV {
        public int opcode() { return 7; }
        public void run(int operand) {
            Day17.registerC = (long)(Day17.registerA / Math.pow(2, comboValue(operand)));
        }
    };
    static public Instruction getInstructionFromCode(int opcode) { return Instruction.values()[opcode]; }
    static public long comboValue(int code) {
        if (0 <= code && code <= 3) return code;
        else if (code == 4) return Day17.registerA;
        else if (code == 5) return Day17.registerB;
        else if (code == 6) return Day17.registerC;
        throw new IllegalArgumentException("Opcode " + code + " is not valid.");
    }
    abstract public int opcode();
    abstract public void run(int operand);
}