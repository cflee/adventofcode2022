import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day03 {
    public static void main(final String[] args) throws Exception {
        final List<String> input = Utils.readFile(
                //"03-sample.txt"
                "03.txt"
                );
        part2(input);
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
                        sum += mapItemToPriority(rucksack.charAt(i));
                        continue rucksackLoop;
                    }
                }
            }
        }
        System.out.println(sum);
    }

    static void part2(final List<String> input) {
        int sum = 0;
outer:
        for (int j = 0; j < input.size(); j+=3) {
            String rucksack1 = input.get(j);
            String rucksack2 = input.get(j+1);
            String rucksack3 = input.get(j+2);
            Set<Character> seen1 = new HashSet<>();
            Set<Character> seen2 = new HashSet<>();
            for (int i = 0; i < rucksack1.length(); i++) {
                seen1.add(new Character(rucksack1.charAt(i)));
            }
            for (int i = 0; i < rucksack2.length(); i++) {
                seen2.add(new Character(rucksack2.charAt(i)));
            }
            for (int i = 0; i < rucksack3.length(); i++) {
                Character item = new Character(rucksack3.charAt(i));
                if (seen1.contains(item) && seen2.contains(item)) {
                    sum += mapItemToPriority(rucksack3.charAt(i));
                    continue outer;
                }
            }
        }
        System.out.println(sum);
    }
}
