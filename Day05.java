import java.util.*;
import java.util.regex.*;

public class Day05 {
    public static void main(final String[] args) throws Exception {
        final List<String> input = Utils.readFile(
                //"05-sample.txt"
                "05.txt"
                );
        part1(input);
    }

    static void part1(final List<String> input) {
        // locate blank line delimiter
        int delimiter = 0;
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).length() == 0) {
                delimiter = i;
                break;
            }
        }

        // identify number of stacks
        int numStacks = (input.get(delimiter - 1).length() + 1 ) / 4;
        List<Deque<Character>> stacks = new ArrayList<>(numStacks);
        for (int i = 0; i < numStacks; i++) {
            stacks.add(new ArrayDeque<>());
        }

        // parse the stacks
        for (int l = delimiter - 2; l >= 0; l--) {
            for (int s = 0; s < numStacks; s++) {
                char c = input.get(l).charAt((s + 1) * 4 - 3);
                if (c != ' ') {
                    stacks.get(s).push(c);
                }
            }
        }

        // operate the stacks
        Pattern p = Pattern.compile("move (\\d+) from (\\d) to (\\d)");
        for (int l = delimiter + 1; l < input.size(); l++) {
            Matcher m = p.matcher(input.get(l));
            if (!m.find()) {
                System.out.println("uh oh - " + l + " " + input.get(l));
            }
            int count = Integer.parseInt(m.group(1));
            int from = Integer.parseInt(m.group(2));
            int to = Integer.parseInt(m.group(3));
            for (int i = 0; i < count; i++) {
                stacks.get(to - 1).push(stacks.get(from - 1).pop());
            }
        }

        // output
        for (Deque<Character> stack : stacks) {
            System.out.print(stack.pop());
        }
        System.out.println();
    }

    static void part2(final List<String> input) {
    }
}
