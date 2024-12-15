package AdventOfCode2024.Day7;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import AdventOfCode2024.ReadFile;

class Day7 {
    static long total = 0;
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day7/Input7.txt");
        while (s.hasNextLine()) {
            String[] line = s.nextLine().split(": ");
            long ans = Long.parseLong(line[0]);
            List<Long> nums = new ArrayList<>();
            String[] str = line[1].split(" ");
            for (String st : str) nums.add(Long.parseLong(st));
            if (test(ans, nums)) total += ans;
        }
        System.out.println(total);
    }
    static public boolean test(long answer, List<Long> numbers) {
        if (numbers.size() == 1 && numbers.get(0) == answer) return true;
        if (numbers.size() == 1) return false;
        List<Long> nums1 = new ArrayList<>(numbers);
        List<Long> nums2 = new ArrayList<>(numbers);
        nums1.add(0, nums1.remove(0) + nums1.remove(0));
        nums2.add(0, nums2.remove(0) * nums2.remove(0));
        if (test(answer, nums1) || test(answer, nums2)) return true;

        // Comment these 3 lines for Part 1. Uncomment for Part 2.
        List<Long> nums3 = new ArrayList<>(numbers);
        nums3.add(0, Long.parseLong(String.valueOf(nums3.remove(0)) + String.valueOf(nums3.remove(0))));
        if (test(answer, nums3)) return true;

        return false;
    }
}