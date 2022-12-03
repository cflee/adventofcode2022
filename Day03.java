import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day03 {
    public static void main(final String[] args) throws Exception {
        final List<String> input = Utils.readFile(
                //"03-sample.txt"
                "03.txt"
                );
        part1(input);
    }

    static int mapItemToPriority(final char item) {
        if (item >= 'a' && item <= 'z') {
            return item - 'a' + 1;
        } else if (item >= 'A' && item <= 'Z') {
            return item - 'A' + 27;
        }
        return 0;
    }

    static void part1(final List<String> input) {
        int sum = 0;
rucksackLoop:
        for (String rucksack : input) {
            Set<Character> left = new HashSet<>();
            for (int i = 0; i < rucksack.length(); i++) {
                if (i < rucksack.length() / 2) {
                    left.add(new Character(rucksack.charAt(i)));
                } else {
                    if (left.contains(rucksack.charAt(i))) {
                        System.out.println("rucksack " + rucksack + " " + rucksack.charAt(i));
                        sum += mapItemToPriority(rucksack.charAt(i));
                        continue rucksackLoop;
                    }
                }
            }
        }
        System.out.println(sum);
    }

    static void part2(final List<String> input) {
    }
}
