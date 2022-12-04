import java.util.List;

public class Day04 {
    public static void main(final String[] args) throws Exception {
        final List<String> input = Utils.readFile(
                //"04-sample.txt"
                "04.txt"
                );
        part1(input);
    }

    static void part1(final List<String> input) {
        int sum = 0;
        for (String line : input) {
            String[] pairs = line.split(",");
            String[] pair1 = pairs[0].split("-");
            String[] pair2 = pairs[1].split("-");
            int p1a = Integer.parseInt(pair1[0]);
            int p1b = Integer.parseInt(pair1[1]);
            int p2a = Integer.parseInt(pair2[0]);
            int p2b = Integer.parseInt(pair2[1]);
            if (p2a >= p1a && p2b <= p1b) {
                sum += 1;
            } else if (p1a >= p2a && p1b <= p2b) {
                sum += 1;
            }
        }
        System.out.println(sum);
    }

    static void part2(final List<String> input) {
    }
}
