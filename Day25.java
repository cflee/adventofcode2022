import java.util.*;

public class Day25 {
    public static void main(final String[] args) throws Exception {
        final List<String> input = Utils.readFile(
                //"25-sample.txt"
                "25.txt"
                );
        part1(input);
    }

    static long decode(final String snafu) {
        long result = 0;
        for (int i = 0; i < snafu.length(); i++) {
            char c = snafu.charAt(snafu.length() - 1 - i);
            long temp = (long) Math.pow(5, i);
            if (c == '=') {
                temp *= -2;
            } else if (c == '-') {
                temp *= -1;
            } else if (c == '0') {
                temp *= 0;
            } else if (c == '1') {
                temp *= 1;
            } else if (c == '2') {
                temp *= 2;
            }
            result += temp;
        }
        return result;
    }

    static String encode(final long decimal) {
        // assume the decimal numbers are always positive, so snafu starts with 1 or 2
        int maxPow = 0;
        long maxPossible = 2;
        while (maxPossible < decimal) {
            maxPow += 1;
            maxPossible += (long) Math.pow(5, maxPow) * 2;
        }

        final int[] digits = new int[] {0, 1, -1, 2, -2};
        String result = "";
        long acc = 0;
        for (int p = maxPow; p >= 0; p--) {
            long bestVal = acc;
            long bestDigit = 0;
            long pow = (long) Math.pow(5, p);
            for (int i = 0; i < digits.length; i++) {
                long temp = pow * digits[i];
                if (Math.abs(acc + temp - decimal) < Math.abs(bestVal - decimal)) {
                    bestVal = acc + temp;
                    bestDigit = digits[i];
                }
            }
            if (bestDigit >= 0) {
                result = result + bestDigit;
            } else if (bestDigit == -1) {
                result = result + "-";
            } else if (bestDigit == -2) {
                result = result + "=";
            }
            acc = bestVal;
        }
        return result;
    }

    static void part1(final List<String> input) {
        long sum = 0;
        for (String line : input) {
            sum += decode(line);
        }
        System.out.println(encode(sum));
    }

    static void part2(final List<String> input) {
    }
}
