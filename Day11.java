import java.util.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

public class Day11 {
    public static void main(final String[] args) throws Exception {
        final List<String> input = Utils.readFile(
                //"11-sample.txt"
                "11.txt"
                );
        final List<List<String>> inputPartitioned = Utils.partitionByBlankLines(input);
        part2(inputPartitioned);
    }

    static long allFactors = 1;

    static class Monkey {
        List<Long> items;
        UnaryOperator<Long> operation;
        Function<Long, Integer> test;
    }

    /* 
     * Monkey 0:
     *   Starting items: 79, 98
     *   Operation: new = old * 19
     *   Test: divisible by 23
     *     If true: throw to monkey 2
     *     If false: throw to monkey 3
     */
    static List<Monkey> parseInput(final List<List<String>> input) {
        Pattern p1 = Pattern.compile("^Monkey (\\d+):$");
        Pattern p2 = Pattern.compile("^  Starting items: (.+)$");
        Pattern p3 = Pattern.compile("^  Operation: new = old (.) (\\d+|old)$");
        Pattern p4 = Pattern.compile("^  Test: divisible by (\\d+)$");
        Pattern p5 = Pattern.compile("^    If true: throw to monkey (\\d+)$");
        Pattern p6 = Pattern.compile("^    If false: throw to monkey (\\d+)$");
        final List<Monkey> monkeys = new ArrayList<>(input.size());
        for (final List<String> monkeyInput : input) {
            Monkey monkey = new Monkey();
            Matcher m1 = p1.matcher(monkeyInput.get(0));
            Matcher m2 = p2.matcher(monkeyInput.get(1));
            Matcher m3 = p3.matcher(monkeyInput.get(2));
            Matcher m4 = p4.matcher(monkeyInput.get(3));
            Matcher m5 = p5.matcher(monkeyInput.get(4));
            Matcher m6 = p6.matcher(monkeyInput.get(5));
            m1.find();
            int monkeyId = Integer.parseInt(m1.group(1));
            m2.find();
            monkey.items = Arrays.stream(m2.group(1).split(", ")).map(Long::valueOf).collect(Collectors.toList());
            m3.find();
            if (m3.group(1).equals("+")) {
                if (m3.group(2).equals("old")) {
                    monkey.operation = (i) -> i + i;
                } else {
                    monkey.operation = (i) -> i + Integer.parseInt(m3.group(2));
                }
            } else if (m3.group(1).equals("*")) {
                if (m3.group(2).equals("old")) {
                    monkey.operation = (i) -> i * i;
                } else {
                    monkey.operation = (i) -> i * Integer.parseInt(m3.group(2));
                }
            }
            m4.find();
            m5.find();
            m6.find();
            monkey.test = (i) -> {
                if (i % Integer.parseInt(m4.group(1)) == 0) {
                    return Integer.parseInt(m5.group(1));
                }
                return Integer.parseInt(m6.group(1));
            };
            allFactors *= Math.abs(Integer.parseInt(m4.group(1)));
            // assume they are in order...
            monkeys.add(monkey);
        }
        return monkeys;
    }

    static void part1(final List<List<String>> input) {
        List<Monkey> monkeys = parseInput(input);
        int[] inspections = new int[monkeys.size()];
        for (int r = 1; r <= 20; r++) {
            for (int m = 0; m < monkeys.size(); m++) {
                Monkey monkey = monkeys.get(m);
                Iterator<Long> itemIter = monkey.items.iterator();
                while (itemIter.hasNext()) {
                    Long item = itemIter.next();
                    long worry = monkey.operation.apply(item) / 3L;
                    int dest = monkey.test.apply(worry);
                    monkeys.get(dest).items.add(worry);
                    inspections[m] += 1;
                    itemIter.remove();
                }
            }
        }
        Arrays.sort(inspections);
        System.out.println(inspections[inspections.length - 1] * inspections[inspections.length - 2]);
    }

    static void part2(final List<List<String>> input) {
        List<Monkey> monkeys = parseInput(input);
        long[] inspections = new long[monkeys.size()];
        for (int r = 1; r <= 10000; r++) {
            for (int m = 0; m < monkeys.size(); m++) {
                Monkey monkey = monkeys.get(m);
                Iterator<Long> itemIter = monkey.items.iterator();
                while (itemIter.hasNext()) {
                    Long item = itemIter.next();
                    long worry = monkey.operation.apply(item) % allFactors;
                    int dest = monkey.test.apply(worry);
                    monkeys.get(dest).items.add(worry);
                    inspections[m] += 1;
                    itemIter.remove();
                }
            }
        }
        System.out.println(Arrays.toString(inspections));
        Arrays.sort(inspections);
        System.out.println(inspections[inspections.length - 1] * inspections[inspections.length - 2]);
    }
}
