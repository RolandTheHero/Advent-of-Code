package AdventOfCode2025;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class ReadFile {
    /**
     * Returns a scanner on the specified filename.
     * @param filename to scan.
     * @return the scanner over the file contents.
     */
    static public Scanner scan(String filename) {
        try {
            return new Scanner(Path.of("AdventOfCode2025/" + filename));
        } catch(IOException e) {
            throw new Error("Failed to read the file.");
        }
    }
}