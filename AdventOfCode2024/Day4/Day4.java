package AdventOfCode2024.Day4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import AdventOfCode2024.ReadFile;

class Day4 {
    static List<List<Character>> wordsearch = new ArrayList<>();
    static public void main(String[] args) {
        Scanner s = ReadFile.scan("Day4/Input4.txt");
        int xmas = 0;
        int x_mas = 0;
        while (s.hasNextLine()) {
            String line = s.nextLine();
            List<Character> row = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                row.add(line.charAt(i));
            }
            wordsearch.add(row);
        }
        for (int y = 0; y < wordsearch.size(); y++) {
            for (int x = 0; x < wordsearch.get(y).size(); x++) {
                if (right(x,y)) xmas++;
                if (rightDown(x,y)) xmas++;
                if (down(x,y)) xmas++;
                if (leftDown(x,y)) xmas++;
                if (left(x,y)) xmas++;
                if (leftUp(x,y)) xmas++;
                if (up(x,y)) xmas++;
                if (rightUp(x,y)) xmas++;
                if (cross(x,y)) x_mas++;
            }
        }
        System.out.println(xmas + " " + x_mas);
    }

    static public boolean cross(int x, int y) {
        try {
            return wordsearch.get(y).get(x).equals('A') &&
                ((wordsearch.get(y-1).get(x-1).equals('M') && wordsearch.get(y+1).get(x+1).equals('S')) || (wordsearch.get(y-1).get(x-1).equals('S') && wordsearch.get(y+1).get(x+1).equals('M'))) &&
                ((wordsearch.get(y-1).get(x+1).equals('M') && wordsearch.get(y+1).get(x-1).equals('S')) || (wordsearch.get(y-1).get(x+1).equals('S') && wordsearch.get(y+1).get(x-1).equals('M')));
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    static public boolean right(int x, int y) {
        try {
            return wordsearch.get(y).get(x).equals('X') &&
                wordsearch.get(y).get(x+1).equals('M') &&
                wordsearch.get(y).get(x+2).equals('A') &&
                wordsearch.get(y).get(x+3).equals('S');
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
    static public boolean rightDown(int x, int y) {
        try {
            return wordsearch.get(y).get(x).equals('X') &&
                wordsearch.get(y+1).get(x+1).equals('M') &&
                wordsearch.get(y+2).get(x+2).equals('A') &&
                wordsearch.get(y+3).get(x+3).equals('S');
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
    static public boolean down(int x, int y) {
        try {
            return wordsearch.get(y).get(x).equals('X') &&
                wordsearch.get(y+1).get(x).equals('M') &&
                wordsearch.get(y+2).get(x).equals('A') &&
                wordsearch.get(y+3).get(x).equals('S');
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
    static public boolean leftDown(int x, int y) {
        try {
            return wordsearch.get(y).get(x).equals('X') &&
                wordsearch.get(y+1).get(x-1).equals('M') &&
                wordsearch.get(y+2).get(x-2).equals('A') &&
                wordsearch.get(y+3).get(x-3).equals('S');
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
    static public boolean left(int x, int y) {
        try {
            return wordsearch.get(y).get(x).equals('X') &&
                wordsearch.get(y).get(x-1).equals('M') &&
                wordsearch.get(y).get(x-2).equals('A') &&
                wordsearch.get(y).get(x-3).equals('S');
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
    static public boolean leftUp(int x, int y) {
        try {
            return wordsearch.get(y).get(x).equals('X') &&
                wordsearch.get(y-1).get(x-1).equals('M') &&
                wordsearch.get(y-2).get(x-2).equals('A') &&
                wordsearch.get(y-3).get(x-3).equals('S');
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
    static public boolean up(int x, int y) {
        try {
            return wordsearch.get(y).get(x).equals('X') &&
                wordsearch.get(y-1).get(x).equals('M') &&
                wordsearch.get(y-2).get(x).equals('A') &&
                wordsearch.get(y-3).get(x).equals('S');
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
    static public boolean rightUp(int x, int y) {
        try {
            return wordsearch.get(y).get(x).equals('X') &&
                wordsearch.get(y-1).get(x+1).equals('M') &&
                wordsearch.get(y-2).get(x+2).equals('A') &&
                wordsearch.get(y-3).get(x+3).equals('S');
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
}
