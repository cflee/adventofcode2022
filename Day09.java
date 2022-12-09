import java.util.*;

public class Day09 {
    public static void main(final String[] args) throws Exception {
        final List<String> input = Utils.readFile(
                //"09-sample-2.txt"
                "09.txt"
                );
        part2(input);
    }
    /* ^ x
     * |
     * +---> y */
    static Point movePoint(String dir, Point point) {
        if (dir.equals("U")) {
            return new Point(point.x + 1, point.y);
        } else if (dir.equals("D")) {
            return new Point(point.x - 1, point.y);
        } else if (dir.equals("L")) {
            return new Point(point.x, point.y - 1);
        } else if (dir.equals("R")) {
            return new Point(point.x, point.y + 1);
        } else {
            return null;
        }
    }

    static Point resolveTail(Point head, Point tail) {
        int diffX = head.x - tail.x;
        int diffY = head.y - tail.y;
        if (diffX >= -1 && diffX <= 1 && diffY >= -1 && diffY <= 1) {
            return tail;
        } else if (Math.abs(diffY) == 2 && Math.abs(diffX) == 2) {
            return new Point(tail.x + (diffX / 2), tail.y + (diffY / 2));
        } else if (Math.abs(diffY) == 2) {
            return new Point(tail.x + diffX, tail.y + (diffY / 2));
        } else if (Math.abs(diffX) == 2) {
            return new Point(tail.x + (diffX / 2), tail.y + diffY);
        }
        System.out.println("ERR: diffX=" + diffX + ", diffY=" + diffY);
        return tail;
    }

    static void part1(final List<String> input) {
        final Set<Point> visited = new HashSet<>();
        Point head = new Point(0, 0);
        Point tail = new Point(0, 0);
        visited.add(tail);
        for (final String line : input) {
            final String[] lineParts = line.split(" ");
            final String dir = lineParts[0];
            int count = Integer.parseInt(lineParts[1]);
            for (int iter = 1; iter <= count; iter++) {
                final Point nextHead = movePoint(dir, head);
                final Point nextTail = resolveTail(nextHead, tail);
                visited.add(nextTail);
                head = nextHead;
                tail = nextTail;
            }
        }
        System.out.println(visited.size());
    }

    static void part2(final List<String> input) {
        final Set<Point> visited = new HashSet<>();
        Point[] points = new Point[10];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(0, 0);
        }
        visited.add(points[9]);
        for (final String line : input) {
            final String[] lineParts = line.split(" ");
            final String dir = lineParts[0];
            int count = Integer.parseInt(lineParts[1]);
            for (int iter = 1; iter <= count; iter++) {
                points[0] = movePoint(dir, points[0]);
                for (int i = 1; i < points.length; i++) {
                    points[i] = resolveTail(points[i - 1], points[i]);
                }
                visited.add(points[9]);
            }
        }
        System.out.println(visited.size());
    }
}

class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public String toString() {
        return "{" + x + "," + y + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(Integer.valueOf(x), Integer.valueOf(y));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point that = (Point) obj;
            return this.x == that.x && this.y == that.y;
        }
        return false;
    }
}
