import java.util.*;
import java.util.stream.*;

public class Day13 {
    public static void main(final String[] args) throws Exception {
        final List<String> input = Utils.readFile(
                //"13-sample.txt"
                "13.txt"
                );
        final List<List<String>> pairs = Utils.partitionByBlankLines(input);
        part2(pairs);
    }

    static enum Result {
        NO,
        MAYBE,
        YES
    }

    // invoke on "[...]" or "[]"
    static List<Object> parse(final String input) {
        List<Object> data = new ArrayList<>();
        final String values = input.substring(1, input.length() - 1); // strip off outer []
        int cursor = 0; // unread position
        int paren = 0; // number of opened [
        boolean hadParen = false; // whether any [
        for (int i = 0; i < values.length(); i++) {
            char c = values.charAt(i);
            if (c == '[') {
                paren += 1;
                hadParen = true;
            } else if (c == ']') {
                paren -= 1;
            } else if (c == ',' && paren == 0) {
                if (hadParen) {
                    Object res = parse(values.substring(cursor, i));
                    data.add(res);
                } else {
                    Object res = Integer.parseInt(values.substring(cursor, i));
                    data.add(res);
                }
                cursor = i + 1; // +1 to skip the current i which is ','
                hadParen = false;
            }
        }
        if (cursor != values.length()) {
            if (hadParen) {
                Object res = parse(values.substring(cursor, values.length()));
                data.add(res);
            } else {
                Object res = Integer.parseInt(values.substring(cursor, values.length()));
                data.add(res);
            }
        }
        return data;
    }

    static Result compare(final Object left, final Object right) {
        if (left instanceof List && right instanceof List) {
            List<Object> leftList = (List<Object>) left;
            List<Object> rightList = (List<Object>) right;
            for (int i = 0; i < Math.max(leftList.size(), rightList.size()); i++) {
                if (i >= leftList.size() && i < rightList.size()) {
                    return Result.YES;
                } else if (i >= rightList.size() && i < leftList.size()) {
                    return Result.NO;
                }
                Result res = compare(leftList.get(i), rightList.get(i));
                if (res != Result.MAYBE) {
                    return res;
                }
            }
            return Result.MAYBE;
        } else if (left instanceof List) {
            return compare(left, List.of(right));
        } else if (right instanceof List) {
            return compare(List.of(left), right);
        } else {
            Integer leftNum = (Integer) left;
            Integer rightNum = (Integer) right;
            if (leftNum < rightNum) {
                return Result.YES;
            } else if (leftNum > rightNum) {
                return Result.NO;
            }
            return Result.MAYBE;
        }
    }

    static void part1(final List<List<String>> input) {
        int sum = 0;
        for (int i = 0; i < input.size(); i++) {
            final List<String> pair = input.get(i);
            List<Object> left = parse(pair.get(0));
            List<Object> right = parse(pair.get(1));
            final Result result = compare(left, right);
            if (result == Result.YES) {
                sum += (i + 1);
            }
        }
        System.out.println(sum);
    }

    static class PacketComparator implements Comparator<List<Object>> {
        public int compare(List<Object> o1, List<Object> o2) {
            Result result = Day13.compare(o1, o2);
            if (result == Result.YES) {
                return -1;
            }
            return 1;
        }
    }

    static void part2(final List<List<String>> input) {
        List<List<Object>> packets = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            final List<String> pair = input.get(i);
            packets.add(parse(pair.get(0)));
            packets.add(parse(pair.get(1)));
        }
        List<Object> divider1 = Arrays.asList(new Object[] { Arrays.asList( new Object[] { Integer.valueOf(2) }) });
        List<Object> divider2 = Arrays.asList(new Object[] { Arrays.asList( new Object[] { Integer.valueOf(6) }) });
        packets.add(divider1);
        packets.add(divider2);
        Collections.sort(packets, new PacketComparator());
        int pos1 = packets.indexOf(divider1) + 1;
        int pos2 = packets.indexOf(divider2) + 1;
        System.out.println(pos1 * pos2);
    }
}
