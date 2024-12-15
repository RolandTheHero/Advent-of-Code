package AdventOfCode2024.Day2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import AdventOfCode2024.ReadFile;

class Day2 {
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day2/Input2.txt");
        int safeReports = 0;
        while (s.hasNextLine()) {
            Scanner sc = new Scanner(s.nextLine());
            List<Integer> levels = new ArrayList<>();
            while (sc.hasNext()) {
                int n = sc.nextInt();
                levels.add(n);
            }
            sc.close();
            if (isSafe(levels)) {safeReports++; continue;}
            for (int index = 0; index < levels.size(); index++) {
                List<Integer> levels2 = new ArrayList<>();
                levels2.addAll(levels);
                levels2.remove(index);
                if (isSafe(levels2)) {safeReports++; break;}
            }
        }
        System.out.println(safeReports);
    }

    static public boolean isSafe(List<Integer> levels) {
        boolean allIncreasing = false;
        boolean allDecreasing = false;
        boolean inRange = true;
        List<Integer> levels2 = new ArrayList<>();
        levels2.addAll(levels);
        Collections.sort(levels2);
        if (levels.equals(levels2)) allIncreasing = true;
        Collections.reverse(levels2);
        if (levels.equals(levels2)) allDecreasing = true;
        int n = -9999;
        for (int i : levels) {
            if (n == -9999) {n = i; continue;}
            if (Math.abs(n - i) < 1 || Math.abs(n - i) > 3) inRange = false;
            n = i;
        }
        return (allIncreasing || allDecreasing) && inRange;
    }
}
