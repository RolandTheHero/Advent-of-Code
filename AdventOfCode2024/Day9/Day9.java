package AdventOfCode2024.Day9;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import AdventOfCode2024.ReadFile;

class Day9 {
    static List<Integer> blocks = new ArrayList<>();
    static long total = 0;
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day9/Input9.txt");
        String line = s.nextLine();
        int id = 0;
        for (int i = 0; i < line.length(); i++) {
            int n = Integer.parseInt(String.valueOf(line.charAt(i)));
            for (int ii = 0; ii < n; ii++) {
                if (i % 2 == 0) blocks.add(id);
                else blocks.add(null);
            }
            if (i % 2 == 0) id++;
        }
        //System.out.println(blocks);
        Set<Integer> movedID = new HashSet<>();
        for (int i = blocks.size() - 1; i >= 0; i--) {
            Integer n = blocks.get(i);
            if (n == null || movedID.contains(n)) continue;
            movedID.add(n);
            int fSize = 0;
            int firstIndex = -1;
            for (int ii = 0; ii <= i; ii++) { // Find all of the same ID
                if (!n.equals(blocks.get(ii))) continue;
                firstIndex = ii;
                fSize = i - ii + 1;
                break;
            }
            for (int ii = 0; ii < firstIndex; ii++) {
                if (blocks.get(ii) != null) continue;
                int totalNull = 0;
                for (int iii = ii; iii < firstIndex; iii++) { // Find size of Null Gap
                    if (blocks.get(iii) == null) totalNull++;
                    else break;
                }
                if (totalNull >= fSize) {
                    for (int a = 0; a < fSize; a++) {
                        blocks.remove(firstIndex);
                    }
                    for (int a = 0; a < fSize; a++) {
                        blocks.add(firstIndex, null);
                    }
                    for (int a = 0; a < fSize; a++) {
                        blocks.remove(ii);
                    }
                    for (int a = 0; a < fSize; a++) {
                        blocks.add(ii, n);
                    }
                    break;
                }
            }
        }
        for (int i = 0; i < blocks.size(); i++) {
            Integer n = blocks.get(i);
            if (n == null) continue;
            total += (long)i * (long)n;
        }
        //System.out.println("\n"+blocks);
        System.out.println(total);
    }
}
