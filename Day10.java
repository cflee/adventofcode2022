import java.util.*;

public class Day10 {
    public static void main(final String[] args) throws Exception {
        final List<String> input = Utils.readFile(
                //"10-sample.txt"
                //"10-sample-2.txt"
                "10.txt"
                );
        part1(input);
    }

    static int checkBreakpoints(final List<Integer> breakpoints, final int clock1, final int clock2) {
        for (Integer breakpoint : breakpoints) {
            if (clock1 < breakpoint && clock2 >= breakpoint) {
                return breakpoint;
            }
        }
        return -1;
    }

    static void part1(final List<String> input) {
        int clock = 0;
        int x = 1;
        int sum = 0;
        final List<Integer> breakpoints = Arrays.asList(new Integer[] { 20, 60, 100, 140, 180, 220 });
        for (final String line : input) {
            int clockNext = clock;
            int xNext = x;
            final String[] parts = line.split(" ");
            final String instr = parts[0];
            if (instr.equals("noop")) {
                clockNext += 1;
            } else if (instr.equals("addx")) {
                clockNext += 2;
                xNext += Integer.parseInt(parts[1]);
            }
            int breakpoint = checkBreakpoints(breakpoints, clock, clockNext);
            if (breakpoint != -1) {
                System.out.println(breakpoint + " " + x);
                sum += breakpoint * x;
            }
            clock = clockNext;
            x = xNext;
        }
        System.out.println(sum);
    }

    static void part2(final List<String> input) {
    }
}
