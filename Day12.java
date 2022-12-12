import java.util.*;

public class Day12 {
    public static void main(final String[] args) throws Exception {
        final List<String> input = Utils.readFile(
                //"12-sample.txt"
                "12.txt"
                );
        part2(input);
    }

    static class Point implements Comparable<Point> {
        int x;
        int y;
        int dist;

        public Point(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Point) {
                Point that = (Point) obj;
                return this.x == that.x && this.y == that.y;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Point{x=" + x + ",y=" + y + ",dist=" + dist + "}";
        }

        @Override
        public int compareTo(Point that) {
            return this.dist - that.dist;
        }
    }

    static boolean isValidMove(char[][] map, Set<Point> visited, Point cur, Point next) {
        // in range, and not visited, and at most 1 lower in elevation
        int curElev = map[cur.x][cur.y];
        if (curElev == 'E') {
            curElev = 'z';
        }
        return next.x >= 0 && next.y >= 0 && next.x < map.length && next.y < map[0].length
            && !visited.contains(next)
            && (map[next.x][next.y] == 'S' ? 'a' - curElev : map[next.x][next.y] - curElev) >= -1;
    }

    static void updateDistances(Map<Point,Integer> distances, Point p) {
        if (!distances.containsKey(p)) {
            distances.put(p, Integer.MAX_VALUE);
        }
        if (p.dist <= distances.get(p)) {
            distances.put(p, p.dist);
        }
    }

    static char[][] parseMap(final List<String> input) {
        char[][] map = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(0).length(); j++) {
                map[i][j] = input.get(i).charAt(j);
            }
        }
        return map;
    }

    static void part1(final List<String> input) {
        final char[][] map = parseMap(input);
        Point end = new Point(0, 0, 0);
outer:
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 'E') {
                    end.x = i;
                    end.y = j;
                    break outer;
                }
            }
        }
        Set<Point> visited = new HashSet<>();
        Map<Point,Integer> distances = new HashMap<>();
        PriorityQueue<Point> queue = new PriorityQueue<>();
        queue.add(end);
        while (!queue.isEmpty()) {
            Point cur = queue.remove();
            if (visited.contains(cur)) {
                continue;
            }
            if (map[cur.x][cur.y] == 'S') {
                System.out.println(distances.get(cur));
                break;
            }
            visited.add(cur);
            Point up = new Point(cur.x - 1, cur.y, cur.dist + 1);
            Point down = new Point(cur.x + 1, cur.y, cur.dist + 1);
            Point right = new Point(cur.x, cur.y + 1, cur.dist + 1);
            Point left = new Point(cur.x, cur.y - 1, cur.dist + 1);
            for (Point p : Arrays.asList(new Point[]{up, down, right, left})) {
                if (isValidMove(map, visited, cur, p)) {
                    updateDistances(distances, p);
                    queue.add(p);
                }
            }
        }
    }

    static void part2(final List<String> input) {
        final char[][] map = parseMap(input);
        Point end = new Point(0, 0, 0);
outer:
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 'E') {
                    end.x = i;
                    end.y = j;
                    break outer;
                }
            }
        }
        Set<Point> visited = new HashSet<>();
        Map<Point,Integer> distances = new HashMap<>();
        PriorityQueue<Point> queue = new PriorityQueue<>();
        queue.add(end);
        while (!queue.isEmpty()) {
            Point cur = queue.remove();
            if (visited.contains(cur)) {
                continue;
            }
            if (map[cur.x][cur.y] == 'a') {
                System.out.println(distances.get(cur));
                break;
            }
            visited.add(cur);
            Point up = new Point(cur.x - 1, cur.y, cur.dist + 1);
            Point down = new Point(cur.x + 1, cur.y, cur.dist + 1);
            Point right = new Point(cur.x, cur.y + 1, cur.dist + 1);
            Point left = new Point(cur.x, cur.y - 1, cur.dist + 1);
            for (Point p : Arrays.asList(new Point[]{up, down, right, left})) {
                if (isValidMove(map, visited, cur, p)) {
                    updateDistances(distances, p);
                    queue.add(p);
                }
            }
        }
    }
}
