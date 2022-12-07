import java.util.*;
import java.util.regex.*;

public class Day07 {
    public static void main(final String[] args) throws Exception {
        final List<String> input = Utils.readFile(
                //"07-sample.txt"
                "07.txt"
                );
        part1(input);
    }

    final static Pattern cd = Pattern.compile("^\\$ cd (.*)$");
    final static Pattern ls = Pattern.compile("^(dir|\\d+) (.+)$");

    static List<String> parentPaths(final List<String> path) {
        final List<String> result = new ArrayList<>();
        for (int i = 1; i <= path.size(); i++) {
            result.add(String.join("/", path.subList(0, i)));
        }
        if (path.size() == 0) {
            result.add("");
        }
        return result;
    }

    static void part1(final List<String> input) {
        final Map<String, Integer> dirSizeTotal = new HashMap<>();
        final List<String> currentPath = new ArrayList<>();
        for (final String line : input) {
            if (line.charAt(0) == '$') {
                final Matcher cdMatcher = cd.matcher(line);
                if (cdMatcher.find()) {
                    final String cdPath = cdMatcher.group(1);
                    if (cdPath.equals("/")) {
                        currentPath.clear();
                    } else if (cdPath.equals("..")) {
                        currentPath.remove(currentPath.size() - 1);
                    } else {
                        currentPath.add(cdPath);
                    }
                }
            } else {
                final Matcher lsMatcher = ls.matcher(line);
                if (lsMatcher.find()) {
                    final String size = lsMatcher.group(1);
                    final String name = lsMatcher.group(2);
                    if (!size.equals("dir")) {
                        final int sizeInt = Integer.parseInt(size);
                        for (String parentPath : parentPaths(currentPath)) {
                            if (dirSizeTotal.get(parentPath) == null) {
                                dirSizeTotal.put(parentPath, 0);
                            }
                            dirSizeTotal.put(parentPath, dirSizeTotal.get(parentPath) + sizeInt);
                        }
                    }
                }
            }
        }

        int eligibleSum = 0;
        for (Map.Entry<String, Integer> dirSize : dirSizeTotal.entrySet()) {
            if (dirSize.getValue() <= 100000) {
                eligibleSum += dirSize.getValue();
            }
        }
        System.out.println(eligibleSum);
    }

    static void part2(final List<String> input) {
    }
}
