import java.util.*;
import java.util.stream.*;

public class Day17 {
    public static void main(final String[] args) throws Exception {
        final List<String> input = Utils.readFile(
                //"17-sample.txt"
                "17.txt"
                );
        part1(input);
    }

    static class Coord {
        int x;
        int y;
        public Coord(final int x, final int y) {
            this.x = x;
            this.y = y;
        }
        @Override public String toString() {
            return "Coord{x=" + x + ",y=" + y + "}";
        }
        @Override public int hashCode() {
            return Objects.hash((Integer) x, (Integer) y);
        }
        @Override public boolean equals(final Object obj) {
            if (obj instanceof Coord) {
                final Coord that = (Coord) obj;
                return x == that.x && y == that.y;
            }
            return false;
        }
    }

    static List<List<Coord>> rockShapes() {
        // a rock is a list of coords that describe the shape relative to a (0,0) that's left/bottom of stencil
        // and x grows up, y grows right
        List<List<Coord>> rockShapes = new ArrayList<>();
        rockShapes.add(Arrays.asList(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0), new Coord(3, 0)));
        rockShapes.add(Arrays.asList(new Coord(1, 0), new Coord(0, 1), new Coord(1, 1), new Coord(2, 1), new Coord(1, 2)));
        rockShapes.add(Arrays.asList(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0), new Coord(2, 1), new Coord(2, 2)));
        rockShapes.add(Arrays.asList(new Coord(0, 0), new Coord(0, 1), new Coord(0, 2), new Coord(0, 3)));
        rockShapes.add(Arrays.asList(new Coord(0, 0), new Coord(1, 0), new Coord(0, 1), new Coord(1, 1)));
        return rockShapes;
    }

    static List<Coord> materialiseRock(final Coord rockPos, final List<Coord> rockShape) {
        return rockShape.stream().map(c -> new Coord(c.x + rockPos.x, c.y + rockPos.y)).collect(Collectors.toList());
    }

    static boolean canFit(final List<Coord> rock, final Map<Coord, Character> space) {
        for (Coord c : rock) {
            if (c.x < 0 || c.x >= 7 || c.y < 0) {
                return false;
            }
            if (space.containsKey(c)) {
                return false;
            }
        }
        return true;
    }

    final static Character ROCK = '#';

    static void part1(final List<String> input) {
        String gases = input.get(0);
        List<List<Coord>> rockShapes = rockShapes();
        Map<Coord, Character> space = new HashMap<>();
        int gasCounter = 0;
        int maxY = -1;
        for (int r = 0; r < 2022; r++) {
            List<Coord> rockShape = rockShapes.get(r % rockShapes.size());
            Coord rockPos = new Coord(2, maxY + 3 + 1);
            while (true) {
                // attempt to impose the gas
                char gas = gases.charAt(gasCounter % gases.length());
                gasCounter += 1;
                if (gas == '>') {
                    Coord newRockPos = new Coord(rockPos.x + 1, rockPos.y);
                    List<Coord> rock = materialiseRock(newRockPos, rockShape);
                    if (canFit(rock, space)) {
                        rockPos = newRockPos;
                    }
                } else if (gas == '<') {
                    Coord newRockPos = new Coord(rockPos.x - 1, rockPos.y);
                    List<Coord> rock = materialiseRock(newRockPos, rockShape);
                    if (canFit(rock, space)) {
                        rockPos = newRockPos;
                    }
                }
                // attempt to fall one unit down
                Coord newRockPos = new Coord(rockPos.x, rockPos.y - 1);
                List<Coord> rock = materialiseRock(newRockPos, rockShape);
                if (canFit(rock, space)) {
                    rockPos = newRockPos;
                } else {
                    break;
                }
            }
            List<Coord> rock = materialiseRock(rockPos, rockShape);
            rock.stream().forEach(c -> space.put(c, ROCK));
            int newMaxY = rock.stream().mapToInt(c -> c.y).max().getAsInt();
            maxY = Math.max(maxY, newMaxY);
        }
        System.out.println(maxY + 1);
    }

    static void part2(final List<String> input) {
    }
}
