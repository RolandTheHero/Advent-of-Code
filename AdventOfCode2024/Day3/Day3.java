package AdventOfCode2024.Day3;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import AdventOfCode2024.ReadFile;

class Day3 {
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day3/Input3.txt");
        String string = "";
        while (s.hasNext()) string += s.nextLine();
        Pattern pattern = Pattern.compile("mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\)");
        Matcher matcher = pattern.matcher(string);
        int total = 0;
        boolean active = true;
        while (matcher.find()) {
            String mul = matcher.group(0);
            if (mul.equals("do()")) { active = true; continue; }
            if (mul.equals("don't()")) { active = false; continue; }
            if (!active) continue;
            String stripped = mul.substring(4, mul.length() - 1);
            String[] nums = stripped.split(",");
            total += Integer.parseInt(nums[0]) * Integer.parseInt(nums[1]);
        }
        System.out.println(total);
    }
}
