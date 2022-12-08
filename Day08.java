import java.util.*;

public class Day08 {
    public static void main(final String[] args) throws Exception {
        final List<String> input = Utils.readFile(
                //"08-sample.txt"
                "08.txt"
                );
        part1(input);
    }

    /* +---> y
     * |
     * v x    */
    static void part1(final List<String> input) {
        int xLen = input.size();
        int yLen = input.get(0).length();
        boolean[][] visible = new boolean[xLen][yLen];
        // left to right
        for (int x = 0; x < xLen; x++) {
            int max = Integer.MIN_VALUE;
            for (int y = 0; y < yLen; y++) {
                int val = input.get(x).charAt(y) - '0';
                if (val > max) {
                    visible[x][y] = true;
                    max = val;
                }
            }
        }
        // right to left
        for (int x = 0; x < xLen; x++) {
            int max = Integer.MIN_VALUE;
            for (int y = yLen - 1; y >= 0; y--) {
                int val = input.get(x).charAt(y) - '0';
                if (val > max) {
                    visible[x][y] = true;
                    max = val;
                }
            }
        }
        // top to bottom
        for (int y = 0; y < yLen; y++) {
            int max = Integer.MIN_VALUE;
            for (int x = 0; x < xLen; x++) {
                int val = input.get(x).charAt(y) - '0';
                if (val > max) {
                    visible[x][y] = true;
                    max = val;
                }
            }
        }
        // bottom to top
        for (int y = 0; y < yLen; y++) {
            int max = Integer.MIN_VALUE;
            for (int x = xLen - 1; x >= 0; x--) {
                int val = input.get(x).charAt(y) - '0';
                if (val > max) {
                    visible[x][y] = true;
                    max = val;
                }
            }
        }

        int count = 0;
        for (int x = 0; x < xLen; x++) {
            for (int y = 0; y < yLen; y++) {
                if (visible[x][y]) {
                    count += 1;
                }
            }
        }
        System.out.println(count);
    }

    static void part2(final List<String> input) {
    }
}
