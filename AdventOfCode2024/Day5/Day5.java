package AdventOfCode2024.Day5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import AdventOfCode2024.ReadFile;

class Day5 {
    static Map<Integer, List<Integer>> after = new HashMap<>(); // X|Y - X->Y
    static Map<Integer, List<Integer>> before = new HashMap<>(); // X|Y - Y->X
    static int total = 0;
    static int total_ = 0; // After correcting the incorrect orders
    static public void main(String[] args) {
        Scanner sA = ReadFile.scan("Day5/Input5a.txt");
        while (sA.hasNextLine()) {
            String line = sA.nextLine();
            String[] nums = line.split("\\|");
            int n1 = Integer.parseInt(nums[0]);
            int n2 = Integer.parseInt(nums[1]);
            if (after.get(n1) == null) { after.put(n1, new ArrayList<>(List.of(n2))); }
            else after.get(n1).add(n2);
            if (before.get(n2) == null) { before.put(n2, new ArrayList<>(List.of(n1))); }
            else before.get(n2).add(n1);
        }
        Scanner sB = ReadFile.scan("Day5/Input5b.txt");
        while (sB.hasNextLine()) {
            String[] pages = sB.nextLine().split(",");
            List<Integer> nums = new ArrayList<>();
            for (String n : pages) {
                int num = Integer.parseInt(n);
                nums.add(num);
            }
            check(nums);
        }
        System.out.println(total + " " + total_);
    }
    static public void check(List<Integer> nums) {
        for (int i = 0; i < nums.size(); i++) {
            for (int ii = 0; ii < nums.size(); ii++) {
                if (ii == i) continue;
                if (ii < i) {
                    List<Integer> n = before.get(nums.get(i));
                    List<Integer> nn = after.get(nums.get(ii));
                    if (!((n != null && n.contains(nums.get(ii))) || (nn != null && nn.contains(nums.get(i))))) {reorder(nums); return;};
                }
                if (ii > i) {
                    List<Integer> n = after.get(nums.get(i));
                    List<Integer> nn = before.get(nums.get(ii));
                    if (!((n != null && n.contains(nums.get(ii))) || (nn != null && nn.contains(nums.get(i))))) {reorder(nums); return;};
                }
            }
        }
        total += nums.get(nums.size()/2);
    }
    static public void reorder(List<Integer> nums) {
        boolean redo = false;
        do {
            redo = false;
            for (int i = 0; i < nums.size(); i++) {
                for (int ii = 0; ii < nums.size(); ii++) {
                    if (ii == i) continue;
                    if (ii < i) {
                        List<Integer> n = before.get(nums.get(i));
                        List<Integer> nn = after.get(nums.get(ii));
                        if (!((n != null && n.contains(nums.get(ii))) || (nn != null && nn.contains(nums.get(i))))) {
                            nums.add(0, nums.remove(ii));
                            redo = true;
                        }
                    }
                    if (ii > i) {
                        List<Integer> n = after.get(nums.get(i));
                        List<Integer> nn = before.get(nums.get(ii));
                        if (!((n != null && n.contains(nums.get(ii))) || (nn != null && nn.contains(nums.get(i))))) {
                            nums.add(0, nums.remove(ii));
                            redo = true;
                        };
                    }
                }
            }
        } while (redo);
        total_ += nums.get(nums.size()/2);
    }
    
}
