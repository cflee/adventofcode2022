import java.util.*;

public class Day06 {
    public static void main(final String[] args) throws Exception {
        final List<String> input = Utils.readFile(
        //        "06-sample.txt"
                "06.txt"
                );
        System.out.println(7 == part1("mjqjpqmgbljsphdztnvjfqwrcgsmlb"));
        System.out.println(5 == part1("bvwbjplbgvbhsrlpgdmjqwftvncz"));
        System.out.println(6 == part1("nppdvjthqldpwncqszvftbrmjlhg"));
        System.out.println(10 == part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"));
        System.out.println(11 == part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"));
        System.out.println(part1(input.get(0)));
        System.out.println(19 == part2("mjqjpqmgbljsphdztnvjfqwrcgsmlb"));
        System.out.println(23 == part2("bvwbjplbgvbhsrlpgdmjqwftvncz"));
        System.out.println(23 == part2("nppdvjthqldpwncqszvftbrmjlhg"));
        System.out.println(29 == part2("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"));
        System.out.println(26 == part2("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"));
        System.out.println(part2(input.get(0)));
    }

    static boolean uniqueRange(char[] chars, int start, int end) {
        Set<Character> seen = new HashSet<>();
        for (int i = start; i < end; i++) {
            if (seen.contains(chars[i])) {
                return false;
            }
            seen.add(chars[i]);
        }
        return true;
    }

    static int part1(final String input) {
        char[] chars = input.toCharArray();
        for (int i = 4; i <= chars.length; i++) {
            if (uniqueRange(chars, i - 4, i)) {
                return i;
            }
        }
        return -1;
    }

    static int part2(final String input) {
        char[] chars = input.toCharArray();
        for (int i = 14; i <= chars.length; i++) {
            if (uniqueRange(chars, i - 14, i)) {
                return i;
            }
        }
        return -1;
    }
}
