import java.util.List;

public class Day02 {
    public static void main(final String[] args) throws Exception {
        final List<String> input = Utils.readFile(
                //"02-sample.txt"
                "02.txt"
                );
        part2(input);
    }

    // [theirs][yours]
    // 0 = rock, 1 = paper, 2 = scissors
    static final int[][] scoreMatrix = new int[][] {
        new int[] { 3, 6, 0 },
        new int[] { 0, 3, 6 },
        new int[] { 6, 0, 3 }
    };

    static void part1(final List<String> input) {
        Integer totalScore = input.stream()
            .mapToInt(line -> {
                char theirs = line.charAt(0);
                char yours = line.charAt(2);
                int choiceScore = yours - 'X' + 1;
                int roundScore = scoreMatrix[theirs - 'A'][yours - 'X'];
                return choiceScore + roundScore;
            })
            .sum();
        System.out.println(totalScore);
    }

    // [theirs][yours]
    // outcome:       0 = lose, 1 = draw, 2 = win
    // theirs/choice: 0 = rock, 1 = paper, 2 = scissors
    static final int[][] choiceMatrix = new int[][] {
        new int[] { 2, 0, 1 },
        new int[] { 0, 1, 2 },
        new int[] { 1, 2, 0 }
    };

    static void part2(final List<String> input) {
        Integer totalScore = input.stream()
            .mapToInt(line -> {
                char theirs = line.charAt(0);
                char outcome = line.charAt(2);
                int choiceScore = choiceMatrix[theirs - 'A'][outcome - 'X'] + 1;
                int roundScore = (outcome - 'X') * 3;
                return choiceScore + roundScore;
            })
            .sum();
        System.out.println(totalScore);
    }
}
