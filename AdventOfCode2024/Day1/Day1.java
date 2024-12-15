package AdventOfCode2024.Day1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import AdventOfCode2024.ReadFile;

class Day1 {
    static public void main(String[] args) {
        Scanner sc = ReadFile.scan("Day1/Input1.txt");
        int total = 0;
        int similarity = 0;
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        while (sc.hasNext()) {
            int i1 = sc.nextInt();
            int i2 = sc.nextInt();
            l1.add(i1);
            l2.add(i2);
        }
        Collections.sort(l1);
        Collections.sort(l2);
        for (int i = 0; i < l1.size(); i++) {
            int i1 = l1.get(i);
            similarity += l2.stream().filter(a->a==i1).count() * i1;
            total += Math.abs(i1 - l2.get(i));
        }
        System.out.println(total + " " + similarity);
    }
}