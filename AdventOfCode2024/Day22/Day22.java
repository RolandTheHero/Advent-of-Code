package AdventOfCode2024.Day22;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import AdventOfCode2024.ReadFile;

public class Day22 {
    static List<Long> initialSecrets = new ArrayList<>();
    public static void main(String[] args) {
        Scanner s = ReadFile.scan("Day22/input.txt");
        while (s.hasNextLine()) initialSecrets.add(Long.parseLong(s.nextLine()));
        System.out.println(part1());
        System.out.println(part2());
    }
    static public long part1() {
        return initialSecrets.stream()
            .mapToLong(n -> simulate(n, 2000))
            .sum();
    }
    static public long part2() { // Takes like 15-20 minutes to finish :( SORRY
        long highest = 0;
        int[] toCheck = new int[]{-3, -3, -3, -2};
        while (!(toCheck[0] == 3 && toCheck[1] == 3 && toCheck[2] == 3 && toCheck[3] == 3)) {
            if (toCheck[0] + toCheck[1] + toCheck[2] + toCheck[3] >= 10 || toCheck[0] + toCheck[1] + toCheck[2] + toCheck[3] <= -10) {
                toCheck[3] += 1;
                if (toCheck[3] == 10) {
                    toCheck[3] = -9;
                    toCheck[2] += 1;
                    if (toCheck[2] == 10) {
                        toCheck[2] = -9;
                        toCheck[1] += 1;
                        if (toCheck[1] == 10) {
                            toCheck[1] = -9;
                            toCheck[0] += 1;
                            if (toCheck[0] == 10) toCheck[0] = -10;
                        }
                    }
                }
                continue;
            }
            //System.out.println(toCheck[0] + " " + toCheck[1] + " " + toCheck[2] + " " + toCheck[3]);
            long totalBanana = 0;
            for (long n : initialSecrets) totalBanana += findFirst(n, toCheck);
            highest = totalBanana > highest ? totalBanana : highest;
            toCheck[3] += 1;
            if (toCheck[3] == 10) {
                toCheck[3] = -9;
                toCheck[2] += 1;
                if (toCheck[2] == 10) {
                    toCheck[2] = -9;
                    toCheck[1] += 1;
                    if (toCheck[1] == 10) {
                        toCheck[1] = -9;
                        toCheck[0] += 1;
                        if (toCheck[0] == 10) toCheck[0] = -10;
                    }
                }
            }
        }
        return highest;
    }
    static public int findFirst(long secretNumber, int... changes) {
        int pointer = 0;
        int previousPrice = price(secretNumber);
        for (int i = 0; i < 2000; i++) {
            secretNumber = simulate(secretNumber);
            int currentPrice = price(secretNumber);
            int diff = currentPrice - previousPrice;
            if (changes[pointer] == diff) pointer++;
            else if (changes[0] == diff) pointer = 1;
            else pointer = 0;
            if (pointer >= changes.length) return currentPrice;
            previousPrice = currentPrice;
        }
        return 0;
    }
    static public long simulate(long secretNumber) {
        long a = secretNumber * 64;
        secretNumber = mix(secretNumber, a);
        secretNumber = prune(secretNumber);
        a = secretNumber / 32;
        secretNumber = mix(secretNumber, a);
        secretNumber = prune(secretNumber);
        a = secretNumber * 2048;
        secretNumber = mix(secretNumber, a);
        secretNumber = prune(secretNumber);
        return secretNumber;
    }
    static public long simulate(long secretNumber, int n) {
        for (int i = 0; i < n; i++) secretNumber = simulate(secretNumber);
        return secretNumber;
    }
    static public long mix(long n1, long n2) { return n1 ^ n2; }
    static public long prune(long n) { return n % 16777216; }
    static public int price(long n) { return (int)(n % 10); }
}