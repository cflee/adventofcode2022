import java.util.*;

public class Day18 {
    public static void main(final String[] args) throws Exception {
        final List<String> input = Utils.readFile(
                //"18-sample.txt"
                "18.txt"
                );
        part1(input);
    }

    static class Coord {
        int x;
        int y;
        int z;
        public Coord(final int x, final int y, final int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        @Override public String toString() {
            return "Coord{x=" + x + ",y=" + y + ",z=" + z + "}";
        }
        @Override public int hashCode() {
            return Objects.hash((Integer) x, (Integer) y, (Integer) z);
        }
        @Override public boolean equals(final Object obj) {
            if (obj instanceof Coord) {
                final Coord that = (Coord) obj;
                return x == that.x && y == that.y && z == that.z;
            }
            return false;
        }
    }

    static List<Coord> parse(final List<String> input) {
        final List<Coord> blocks = new ArrayList<>();
        for (final String line : input) {
            String[] parts = line.split(",");
            blocks.add(new Coord(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
        }
        return blocks;
    }

    static List<Coord> transforms() {
        return Arrays.asList(
                new Coord(1, 0, 0),
                new Coord(-1, 0, 0),
                new Coord(0, 1, 0),
                new Coord(0, -1, 0),
                new Coord(0, 0, 1),
                new Coord(0, 0, -1));
    }

    static void part1(final List<String> input) {
        final List<Coord> blocks = parse(input);
        final List<Coord> transforms = transforms();
        final Set<Coord> space = new HashSet<>(blocks.size());
        int surface = 0;
        for (Coord block : blocks) {
            space.add(block);
            surface += 6;
            for (Coord transform : transforms) {
                if (space.contains(new Coord(block.x + transform.x, block.y + transform.y, block.z + transform.z))) {
                    // deduct two as this block counted one, and other block counted one too
                    surface -= 2;
                }
            }
        }
        System.out.println(surface);
    }

    static void part2(final List<String> input) {
    }
}
